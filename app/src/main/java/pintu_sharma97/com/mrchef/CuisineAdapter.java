package pintu_sharma97.com.mrchef;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;

public class CuisineAdapter extends RecyclerView.Adapter<CuisineAdapter.MyHolder> {
    private Context context;
    private ArrayList<CategoryList> categoryList;
    private ArrayList<Integer> menuid;
    private ArrayList<String> title;
    private Bundle bundle;

    public CuisineAdapter(Context context, ArrayList<CategoryList> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        menuid = new ArrayList<>();
        title = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cuisines_layout,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Picasso.with(context).load(categoryList.get(i).getImage()).fit().into(myHolder.cuisineImageView);
        myHolder.cuisineTextView.setText(categoryList.get(i).getCategoryname());
        menuid.add(categoryList.get(i).getMenuid());
        title.add(categoryList.get(i).getCategoryname());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView cuisineImageView;
        TextView cuisineTextView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            cuisineImageView = itemView.findViewById(R.id.cuisineImageView);
            cuisineTextView = itemView.findViewById(R.id.cuisineTextView);

            bundle = new Bundle();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(context,Indian.class);
                            bundle.putInt("menuid",menuid.get(getPosition()));
                            bundle.putString("title",title.get(getPosition()));
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    },200);
                }
            });
        }
    }
}
