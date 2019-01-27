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
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyHolder> {
    private Context context;
    private ArrayList<CategoryList> categoryList;
    private Bundle bundle;
    //private int  menuiid;
    private ArrayList<Integer>menuid1;
    private ArrayList<String> menuname;

    public CategoryAdapter(Context context, ArrayList<CategoryList> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        menuid1=new ArrayList<>();
        menuname = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_layout,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Picasso.with(context).load(categoryList.get(i).getImage()).fit().into(myHolder.imageView);
        myHolder.textView.setText(categoryList.get(i).getCategoryname());
        //menuiid = categoryList.get(i).getMenuid();
        menuid1.add(categoryList.get(i).getMenuid());
        menuname.add(categoryList.get(i).getCategoryname());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        //int id;
        ImageView imageView;
        TextView textView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.categoryImageView);
            textView = itemView.findViewById(R.id.categoryTextView);
            bundle = new Bundle();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                                    Intent intent = new Intent(context,Snacks.class);

                                    bundle.putInt("id",menuid1.get(getPosition()));
                                    bundle.putString("categoryname",menuname.get(getPosition()));
                                    intent.putExtras(bundle);
                                    context.startActivity(intent);
                        }
                    },500);
                }
            });
        }
    }
}
