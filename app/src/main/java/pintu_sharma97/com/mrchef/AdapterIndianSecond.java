package pintu_sharma97.com.mrchef;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class AdapterIndianSecond extends RecyclerView.Adapter<AdapterIndianSecond.MyHolder> {
    private Context context;
    private Integer[] images;

    public AdapterIndianSecond(Context context, Integer[] images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cafe_card,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Picasso.with(context).load(images[i]).fit().into(myHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_indian);
            textView = itemView.findViewById(R.id.textView_indian);
        }
    }
}
