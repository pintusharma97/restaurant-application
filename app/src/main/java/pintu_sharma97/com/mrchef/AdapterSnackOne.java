package pintu_sharma97.com.mrchef;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterSnackOne extends RecyclerView.Adapter<AdapterSnackOne.MyHolder> {
    private Context context;
    ArrayList<MenuItems> menuItems;
    private ArrayList<String> item_name,item_image;
    private ArrayList<Integer> item_price;
    private int p_qty;
    private String category,tableid;
    private ApiInterface apiInterface;
    private TextView counter;
    private SharedPreferences sharedPreferencesCounter;
    private SharedPreferences.Editor editor;
    private  int float_counter;
    public AdapterSnackOne(Context context, ArrayList<MenuItems> menuItems,String category,String tableid,TextView counter) {
        this.counter =counter;
        this.context = context;
        this.menuItems = menuItems;
        this.tableid = tableid;
        item_name = new ArrayList<>();
        item_image = new ArrayList<>();
        item_price = new ArrayList<>();
        this.category = category;
        counter();
    }

    private void counter() {
        sharedPreferencesCounter = context.getSharedPreferences("mycounter",Context.MODE_PRIVATE);
        float_counter = sharedPreferencesCounter.getInt("counter",0);
        counter.setText(""+float_counter);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_item_layout,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Picasso.with(context).load(menuItems.get(i).getImage()).fit().into(myHolder.imageView);
        myHolder.textView.setText(menuItems.get(i).getName());
        myHolder.price.setText(""+menuItems.get(i).getPrice());
        item_name.add(menuItems.get(i).getName());
        item_image.add(menuItems.get(i).getImage());
        item_price.add(menuItems.get(i).getPrice());

    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView,price;
        Button order;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.menuImageView);
            textView = itemView.findViewById(R.id.menuTextView);
            price = itemView.findViewById(R.id.menuTextViewPrice);
            order = itemView.findViewById(R.id.menuButton);

            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show(item_name.get(getPosition()),item_image.get(getPosition()),item_price.get(getPosition()));
                }
            });

        }
    }

    public void show(final String name, final String image, final Integer price){
        final Dialog dialog = new Dialog(context);
        final Button cancel,order,add,sub;
        final TextView product_name,product_price,product_category,product_quantity;
        final ImageView product_image;


        p_qty = 1;
        dialog.setTitle("Place Order");

        dialog.setContentView(R.layout.dialog);
        cancel = dialog.findViewById(R.id.cancel_button);
        order = dialog.findViewById(R.id.order_button);
        add = dialog.findViewById(R.id.add_button);
        sub = dialog.findViewById(R.id.sub_button);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        product_name = dialog.findViewById(R.id.productnameTextView);
        product_price = dialog.findViewById(R.id.productPriceTextView);
        product_image = dialog.findViewById(R.id.productdialogImageView);
        product_category = dialog.findViewById(R.id.productcategoryTextView);
        product_quantity = dialog.findViewById(R.id.quantity);

        product_quantity.setText(""+p_qty);
        product_name.setText(name);
        product_price.setText(""+price);
        product_category.setText(category);
        Picasso.with(context).load(image).fit().into(product_image);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p_qty++;
                product_quantity.setText(""+p_qty);
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p_qty--;
                if (p_qty<1){
                    p_qty=1;
                }
                product_quantity.setText(""+p_qty);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<User> call = apiInterface.OrderCart(tableid,name,image,p_qty,price,category);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User res = response.body();
                        if (res!=null){
                            Toast.makeText(context, ""+res.getResponse(), Toast.LENGTH_SHORT).show();
                            if (res.getResponse().endsWith("Added to cart")){
                                sharedPreferencesCounter = context.getSharedPreferences("mycounter",Context.MODE_PRIVATE);
                                editor = sharedPreferencesCounter.edit();
                                float_counter = sharedPreferencesCounter.getInt("counter",0);
                                float_counter++;

                                counter.setText(""+float_counter);
                                editor.putInt("counter",float_counter);
                                editor.commit();
                                dialog.dismiss();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(context, ""+t, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        dialog.show();
        //Spinner quantity;
        //quantity = dialog.findViewById(R.id.spinner);
        //Integer[] product_qty = {1,2,3,4,5};

        /*ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(context,android.R.layout.simple_spinner_item,product_qty){
            @Override
            public boolean isEnabled(int position) {
                if (position>0)
                    return true;
                else
                    return false;
            }
        };
        //quantity.setAdapter(adapter);
       /* quantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    p_qty = (int) parent.getItemAtPosition(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dialog.show();*/
    }

}
