package pintu_sharma97.com.mrchef;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.MyHolder> {
    private Context context;
    private ArrayList<CartData> cartData;
    private int subTotal=0;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public CheckoutAdapter(Context context, ArrayList<CartData> cartData) {
        this.context = context;
        this.cartData = cartData;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.checkout_layout,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Picasso.with(context).load(cartData.get(i).getImage()).fit().into(myHolder.imageView);
        myHolder.title.setText(cartData.get(i).getName());
        myHolder.price.setText(""+cartData.get(i).getPrice());
        myHolder.quantity.setText(""+cartData.get(i).getQuantitty());
        myHolder.total.setText(""+cartData.get(i).getTotal());
    }

    @Override
    public int getItemCount() {
        return cartData.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title,price,quantity,total;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.checkoutImageView);
            title = itemView.findViewById(R.id.checkoutTextView);
            quantity = itemView.findViewById(R.id.checkoutQuantity);
            price = itemView.findViewById(R.id.checkoutPrice);
            total = itemView.findViewById(R.id.checkoutTotal);
        }
    }
}
