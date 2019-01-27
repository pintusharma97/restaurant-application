package pintu_sharma97.com.mrchef;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyHolder> {
    private Context context;
    private ArrayList<CartData> cartData;
    private ArrayList<String> item_name,item_category,image_product;
    private ArrayList<Integer> item_price;
    private ApiInterface apiInterface;
    private SharedPreferences sharedPreferencesCounter;
    private SharedPreferences.Editor editor;
    private int counter;

    public OrderListAdapter(Context context, ArrayList<CartData> cartData) {
        this.context = context;
        this.cartData = cartData;
        item_name = new ArrayList<>();
        item_category = new ArrayList<>();
        image_product = new ArrayList<>();
        item_price = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_layout,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Picasso.with(context).load(cartData.get(i).getImage()).fit().into(myHolder.imageView);
        myHolder.title.setText(cartData.get(i).getName());
        myHolder.price.setText(""+cartData.get(i).getPrice());
        myHolder.quantity.setText(""+cartData.get(i).getQuantitty());
        myHolder.category.setText(cartData.get(i).getCategory());
        myHolder.total.setText(""+cartData.get(i).getTotal());
        item_name.add(cartData.get(i).getName());
        item_category.add(cartData.get(i).getCategory());
        item_price.add(cartData.get(i).getPrice());
        image_product.add(cartData.get(i).getImage());
    }

    @Override
    public int getItemCount() {
        return cartData.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title,price,quantity,category,total;
        Button remove;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.checkoutImageView);
            title = itemView.findViewById(R.id.checkoutTextView);
            quantity = itemView.findViewById(R.id.checkoutQuantity);
            price = itemView.findViewById(R.id.checkoutPrice);
            category = itemView.findViewById(R.id.checkoutTextViewtwo);
            total = itemView.findViewById(R.id.checkoutTotal);

        }
    }
}
