package pintu_sharma97.com.mrchef;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyHolder> {
    private Context context;
    private String[] titlemenu;
    private Integer[] imgs;

    public MenuAdapter(Context context, String[] titlemenu, Integer[] imgs) {
        this.context = context;
        this.titlemenu = titlemenu;
        this.imgs = imgs;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_item_layout,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Picasso.with(context).load(imgs[i]).fit().into(myHolder.menuImageView);
        myHolder.menuTextView.setText(titlemenu[i]);
    }

    @Override
    public int getItemCount() {
        return titlemenu.length;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView menuImageView;
        TextView menuTextView;
        Button menuButton;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            menuImageView = itemView.findViewById(R.id.menuImageView);
            menuTextView = itemView.findViewById(R.id.menuTextView);
            menuButton = itemView.findViewById(R.id.menuButton);
        }
    }
}
