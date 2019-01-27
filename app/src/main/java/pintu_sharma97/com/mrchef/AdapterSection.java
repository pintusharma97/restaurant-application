package pintu_sharma97.com.mrchef;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterSection extends RecyclerView.Adapter<AdapterSection.MyHolder> {
    private Context context;
    private ArrayList<SectionList> sectionLists;
    private ArrayList<String> section_id,section_name;
    private ApiInterface apiInterface;
    private AdapterSnackOne adapterSnackOne;
    private Bundle bundle;
    private String tableid;
    private TextView counter;

    public AdapterSection(Context context, ArrayList<SectionList> sectionLists,String tableid,TextView counter) {
        this.counter = counter;
        this.context = context;
        this.sectionLists = sectionLists;
        this.tableid = tableid;
        section_id = new ArrayList<>();
        section_name = new ArrayList<>();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.section_layout,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {
        myHolder.section_title.setText(sectionLists.get(i).getSectionname());
        section_id.add(sectionLists.get(i).getSectionid());
        section_name.add(sectionLists.get(i).getSectionname());
        Call<MenuData> call = apiInterface.SectionData(section_id.get(i));
        call.enqueue(new Callback<MenuData>() {
            @Override
            public void onResponse(Call<MenuData> call, Response<MenuData> response) {
                MenuData res = response.body();
                adapterSnackOne = new AdapterSnackOne(context,res.getMenuItems(),sectionLists.get(i).getSectionname(),tableid,counter);
                myHolder.recyclerView_section.setAdapter(adapterSnackOne);
            }

            @Override
            public void onFailure(Call<MenuData> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return sectionLists.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView section_title;
        RecyclerView recyclerView_section;
        Button button_navigation;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            section_title = itemView.findViewById(R.id.sectionTitle_textView);
            recyclerView_section = itemView.findViewById(R.id.recyclerViewSection);
            button_navigation = itemView.findViewById(R.id.section_navigation);
            bundle = new Bundle();
            recyclerView_section.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            button_navigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,CuisineSection.class);
                    bundle.putString("sectiontitle",section_name.get(getPosition()));
                    bundle.putString("sectionid",section_id.get(getPosition()));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }
}
