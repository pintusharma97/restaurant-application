package pintu_sharma97.com.mrchef;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MAdapter extends RecyclerView.Adapter<MAdapter.MyHolder> {
    private Context context;
    private Integer[] images;

    public MAdapter(Context context, Integer[] images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_more,viewGroup,false);
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
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_popular);
        }
    }
}

/*
* <android.support.v7.widget.CardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="180dp"
    android:layout_height="170dp"
    android:layout_marginLeft="5dp">
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">
        <ImageView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:id="@+id/imageView_popular"/>
    </LinearLayout>
</android.support.v7.widget.CardView>
*
* */