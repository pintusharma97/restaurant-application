package pintu_sharma97.com.mrchef;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapterbreak extends RecyclerView.Adapter<Adapterbreak.MyHolder> {
    private Context context;
    ArrayList<TabItem> tabItems;
    private ArrayList<String> item_name,item_image;
    private ArrayList<Integer> item_price;
    private int quantity;
    private String category,tableid;
    private SharedPreferences sharedPreferences,sharedPreferencesCounter;
    private SharedPreferences.Editor editor;
    private ApiInterface apiInterface;
    private  int float_counter;
    private TextView counter;

    public Adapterbreak(Context context, ArrayList<TabItem> tabItems,String category,TextView counter) {
        this.context = context;
        this.tabItems = tabItems;
        this.category = category;
        this.counter = counter;
        item_name = new ArrayList<>();
        item_image = new ArrayList<>();
        item_price = new ArrayList<>();
        counter();
    }

    private void counter() {
        sharedPreferencesCounter = context.getSharedPreferences("mycounter",Context.MODE_PRIVATE);
        float_counter = sharedPreferencesCounter.getInt("counter",0);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_meal,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Picasso.with(context).load(tabItems.get(i).getImage()).fit().into(myHolder.imageView);
        myHolder.name.setText(tabItems.get(i).getName());
        myHolder.category.setText(tabItems.get(i).getCategory());
        myHolder.type.setText("("+tabItems.get(i).getType()+")");
        myHolder.price.setText(""+tabItems.get(i).getPrice());
        item_name.add(tabItems.get(i).getName());
        item_image.add(tabItems.get(i).getImage());
        item_price.add(tabItems.get(i).getPrice());
    }

    @Override
    public int getItemCount() {
        return tabItems.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,category,type,price;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.breakImageView);
            name = itemView.findViewById(R.id.nameTextView);
            category = itemView.findViewById(R.id.categoryTextView);
            type = itemView.findViewById(R.id.typeTextView);
            price = itemView.findViewById(R.id.mealTextViewPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show(item_name.get(getPosition()),item_image.get(getPosition()),item_price.get(getPosition()));
                }
            });
        }
    }
    public void show(final String name, final String image, final Integer price){
        final Dialog dialog = new Dialog(context);
        Button cancel,order,add,sub;
        final TextView product_name,product_price,product_quantity,product_category;
        ImageView product_image;

        quantity = 1;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        dialog.setTitle("Place Order");

        dialog.setContentView(R.layout.dialog);
        cancel = dialog.findViewById(R.id.cancel_button);
        order = dialog.findViewById(R.id.order_button);
        add = dialog.findViewById(R.id.add_button);
        sub = dialog.findViewById(R.id.sub_button);
        sharedPreferences = context.getSharedPreferences("mypref",Context.MODE_PRIVATE);
        //context.getSharedPreferences("mypref",Context.MODE_PRIVATE);
        tableid = sharedPreferences.getString("tableid","");

        product_name = dialog.findViewById(R.id.productnameTextView);
        product_price = dialog.findViewById(R.id.productPriceTextView);
        product_image = dialog.findViewById(R.id.productdialogImageView);
        product_category = dialog.findViewById(R.id.productcategoryTextView);
        product_quantity = dialog.findViewById(R.id.quantity);

        product_quantity.setText(""+quantity);
        product_name.setText(name);
        product_price.setText(""+price);
        product_category.setText(category);
        Picasso.with(context).load(image).fit().into(product_image);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                product_quantity.setText(""+quantity);
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity--;
                if (quantity<1){
                    quantity=1;
                }
                product_quantity.setText(""+quantity);
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
                Call<User> call = apiInterface.OrderCart(tableid,name,image,quantity,price,category);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User res = response.body();
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

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(context, ""+t, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        dialog.show();
    }
}
