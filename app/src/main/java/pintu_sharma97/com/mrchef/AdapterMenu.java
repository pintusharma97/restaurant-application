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

import java.util.ArrayList;

public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.MyHolder> {
    private Context context;
    private ArrayList<TabItem> tabItems =new ArrayList<>();

    public AdapterMenu(Context context, ArrayList<TabItem> tabItems) {
        this.context = context;
        this.tabItems = tabItems;
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
        myHolder.type.setText(tabItems.get(i).getType());
    }

    @Override
    public int getItemCount() {
        return tabItems.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,category,type;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.breakImageView);
            name = itemView.findViewById(R.id.nameTextView);
            category = itemView.findViewById(R.id.categoryTextView);
            type = itemView.findViewById(R.id.typeTextView);
        }
    }
}
