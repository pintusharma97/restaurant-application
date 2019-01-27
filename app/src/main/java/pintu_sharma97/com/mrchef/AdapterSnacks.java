package pintu_sharma97.com.mrchef;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class AdapterSnacks extends RecyclerView.Adapter<AdapterSnacks.MyHolder> {
    private Context context;
    private ArrayList<DataCategory> dataCategory;
    private ArrayList<Integer> id;
    private Bundle bundle;
    private ArrayList<String> subcategoryname;
    private SharedPreferences sharedPreferencesCounter;
    private TextView counter;
    public AdapterSnacks(Context context, ArrayList<DataCategory> dataCategory,TextView counter) {
        this.counter = counter;
        this.context = context;
        this.dataCategory = dataCategory;
        id = new ArrayList<>();
        subcategoryname = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_mrchef,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Picasso.with(context).load(dataCategory.get(i).getSubcatimage()).fit().into(myHolder.imageView);
        myHolder.textView.setText(dataCategory.get(i).getSubcatname());
        id.add(dataCategory.get(i).getSubcatid());
        subcategoryname.add(dataCategory.get(i).getSubcatname());
    }

    @Override
    public int getItemCount() {
        return dataCategory.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        int float_counter;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewChef);
            textView = itemView.findViewById(R.id.textViewChef);
            bundle = new Bundle();
            sharedPreferencesCounter = context.getSharedPreferences("mycounter",Context.MODE_PRIVATE);
            float_counter = sharedPreferencesCounter.getInt("counter",0);
            counter.setText(""+float_counter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,IndianSnacks.class);
                    bundle.putInt("id",id.get(getPosition()));
                    bundle.putString("subcategoryname",subcategoryname.get(getPosition()));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }
}
