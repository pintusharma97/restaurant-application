package pintu_sharma97.com.mrchef;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Indian extends AppCompatActivity implements View.OnClickListener {
    private SliderLayout sliderLayout_indian;
    private String[] name = {"Honey Chilly Potato","Chicken Manchurian","Spring Rolls","Fried Noodles"};
    private RecyclerView category,section_recyclerView;
    //private Integer[] images = {R.drawable.indian_bread,R.drawable.indian_curries,R.drawable.indian_desserts,R.drawable.soupandsalad};
    private MAdapter adapter;
    private IndianCategory categoryadapter;
    //private String[] categoryname;
    private AdapterIndianSecond indianSecond;
    private AdapterIndianSpc indianSpc;
    private String title,tableid;
    private ApiInterface apiInterface;
    private int menuid,float_counter;
    private AdapterSection adapterSection;
    private SharedPreferences sharedPreferences,sharedPreferencesCounter;
    private TextView counter;
    private FloatingActionButton actionButton;

    private TextView one,two,three,four;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indian);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

        Bundle bundle = getIntent().getExtras();
        menuid = bundle.getInt("menuid");
        title = bundle.getString("title");
        setTitle(title);

        category.setHasFixedSize(true);

        sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);
        tableid = sharedPreferences.getString("tableid","");

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<CategoryFirstData> call = apiInterface.CategoryFirst(menuid);
        call.enqueue(new Callback<CategoryFirstData>() {
            @Override
            public void onResponse(Call<CategoryFirstData> call, Response<CategoryFirstData> response) {
                CategoryFirstData res = response.body();
                category.setLayoutManager(new LinearLayoutManager(Indian.this,LinearLayoutManager.HORIZONTAL,false));
                categoryadapter = new IndianCategory(Indian.this,res.getDataCategory());
                category.setAdapter(categoryadapter);
            }

            @Override
            public void onFailure(Call<CategoryFirstData> call, Throwable t) {

            }
        });

        sliderLayout_indian.setIndicatorAnimation(SliderLayout.Animations.THIN_WORM);
        sliderLayout_indian.setScrollTimeInSec(2);
        setSlideViews();

        section_recyclerView.setHasFixedSize(true);

        Call<SectionData> sectionDataCall = apiInterface.CuisineSection(menuid);
        sectionDataCall.enqueue(new Callback<SectionData>() {
            @Override
            public void onResponse(Call<SectionData> call, Response<SectionData> response) {
                SectionData res = response.body();
                section_recyclerView.setLayoutManager(new LinearLayoutManager(Indian.this,LinearLayoutManager.VERTICAL,false));
                if (tableid!="") {
                    adapterSection = new AdapterSection(Indian.this, res.getSectionLists(),tableid,counter);
                }
                section_recyclerView.setAdapter(adapterSection);
            }

            @Override
            public void onFailure(Call<SectionData> call, Throwable t) {

            }
        });
        /*Call<SectionData> sectionDataCall = apiInterface.CuisineSection(menuid);
        sectionDataCall.enqueue(new Callback<SectionData>() {
            @Override
            public void onResponse(Call<SectionData> call, Response<SectionData> response) {
                SectionData res = response.body();
                one.setText(res.getSectionLists().get(0).getSectionname());
                two.setText(res.getSectionLists().get(1).getSectionname());
                three.setText(res.getSectionLists().get(2).getSectionname());
                four.setText(res.getSectionLists().get(3).getSectionname());
            }

            @Override
            public void onFailure(Call<SectionData> call, Throwable t) {

            }
        });*/

        //mostpopular.setHasFixedSize(true);

        //recyclerViewTwo.setHasFixedSize(true);
        //recyclerViewThree.setHasFixedSize(true);

        //categoryname = getResources().getStringArray(R.array.indianCattegory);

        //mostpopular.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        //recyclerViewTwo.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        //recyclerViewThree.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        /*adapter = new MAdapter(this,images);
        indianSecond = new AdapterIndianSecond(this,images);
        indianSpc = new AdapterIndianSpc(this,images);

        mostpopular.setAdapter(adapter);
        recyclerViewTwo.setAdapter(indianSecond);
        recyclerViewThree.setAdapter(indianSpc);*/
    }

    public void main(){
        sharedPreferencesCounter = getSharedPreferences("mycounter",MODE_PRIVATE);
        float_counter = sharedPreferencesCounter.getInt("counter",0);
        counter.setText(""+float_counter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        main();
    }


    private void setSlideViews() {
        SliderView sliderView = new SliderView(this);
        for(int i=0;i<=3;i++){
            switch (i){
                case 0:
                    sliderView.setImageDrawable(R.drawable.honey_chilli_potatoes);
                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.chicken_manchurian);
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.spring_rolls);
                    break;
                case 3:
                    sliderView.setImageDrawable(R.drawable.fried_noodels);
                    break;
            }
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    
                }
            });
            sliderLayout_indian.addSliderView(sliderView);
        }
    }

    private void init() {
        sliderLayout_indian = findViewById(R.id.flipper_indian);
        category = findViewById(R.id.recyclerView_indian);
        section_recyclerView = findViewById(R.id.recyclerView_cuisineSection);
        counter = findViewById(R.id.counter_TextView);
        actionButton = findViewById(R.id.floating_cart);
        actionButton.setOnClickListener(this);
        //mostpopular = findViewById(R.id.recyclerViewOne);
        //recyclerViewTwo = findViewById(R.id.recyclerViewTwo);
        //recyclerViewThree = findViewById(R.id.recyclerViewThree);
        //one = findViewById(R.id.textView_one);
        //two = findViewById(R.id.tectView_two);
        //three = findViewById(R.id.textView_three);
        //four = findViewById(R.id.textViw_four);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v==actionButton){
            Intent intent = new Intent(this,OrderList.class);
            startActivity(intent);
        }
    }
}
