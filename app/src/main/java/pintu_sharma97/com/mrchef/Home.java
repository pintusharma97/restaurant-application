package pintu_sharma97.com.mrchef;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements TabLayout.OnTabSelectedListener {
    private SliderLayout sliderLayout;
    private String[] name = {"Honey Chilly Potato","Chicken Manchurian","Spring Rolls","Fried Noodles"};
    private RecyclerView cuisineRecyclerView,categoryRecyclerView;
    private String[] cuisineTitle,categoryTitle;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;

    private ApiInterface apiInterface;

    private TextView counter;
    private FloatingActionButton actionButton;
    private SharedPreferences  sharedPreferencesCounter;
    private SharedPreferences.Editor editor;
    //private Integer[] imgs1 = {R.drawable.indian,R.drawable.italian,R.drawable.chinese,R.drawable.thai,R.drawable.korean,R.drawable.mexican,R.drawable.japanese};
    //private Integer[] categoryImages = {R.drawable.burger,R.drawable.cafe,R.drawable.vegetarian,R.drawable.non_vegetarian,R.drawable.pizza_cate,R.drawable.salad};

    private CuisineAdapter cuisineAdapter;
    private CategoryAdapter categoryAdapter;

    private int float_counter;
    public Home() {
        // Required empty public constructor
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        sliderLayout = view.findViewById(R.id.flipper);

        cuisineRecyclerView = view.findViewById(R.id.cuisineRecyclerView);
        categoryRecyclerView = view.findViewById(R.id.recyclerViewFirst);

        actionButton = view.findViewById(R.id.floating_cart);
        counter = view.findViewById(R.id.counter_TextView);
        sharedPreferencesCounter = getContext().getSharedPreferences("mycounter",Context.MODE_PRIVATE);
        float_counter = sharedPreferencesCounter.getInt("counter",0);
        if (float_counter==0){
            counter.setVisibility(View.GONE);
            actionButton.setVisibility(View.GONE);
        }else {
            counter.setVisibility(View.VISIBLE);
            actionButton.setVisibility(View.VISIBLE);
            counter.setText(""+float_counter);
        }


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),OrderList.class);
                startActivity(intent);
            }
        });

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Breakfast"));
        tabLayout.addTab(tabLayout.newTab().setText("Lunch"));
        tabLayout.addTab(tabLayout.newTab().setText("Dinner"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        fragmentAdapter = new FragmentAdapter(getChildFragmentManager(),tabLayout.getTabCount());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setOnTabSelectedListener(this);

        cuisineTitle = getResources().getStringArray(R.array.cuisine_title);
        categoryTitle = getResources().getStringArray(R.array.category_title);

        categoryRecyclerView.setHasFixedSize(true);


        Call<Categorydata> call = apiInterface.getCategoryData();
        call.enqueue(new Callback<Categorydata>() {
            @Override
            public void onResponse(Call<Categorydata> call, Response<Categorydata> response) {
                Categorydata res = response.body();

                cuisineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                cuisineAdapter = new CuisineAdapter(getContext(),res.getCategoryList());
                cuisineRecyclerView.setAdapter(cuisineAdapter);
            }

            @Override
            public void onFailure(Call<Categorydata> call, Throwable t) {
                Toast.makeText(getContext(), "Error : "+t, Toast.LENGTH_SHORT).show();
            }
        });

        Call<Categorydata> call2 = apiInterface.getCategorydatafirst();
        call2.enqueue(new Callback<Categorydata>() {
            @Override
            public void onResponse(Call<Categorydata> call, Response<Categorydata> response) {
                Categorydata response_two = response.body();
                categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                categoryAdapter = new CategoryAdapter(getContext(),response_two.getCategoryList());
                categoryRecyclerView.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Call<Categorydata> call, Throwable t) {
                Toast.makeText(getContext(), "Error : "+t, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onResume() {
        super.onResume();
        sharedPreferencesCounter = getContext().getSharedPreferences("mycounter",Context.MODE_PRIVATE);
        counter.setVisibility(View.VISIBLE);
        actionButton.setVisibility(View.VISIBLE);
        float_counter = sharedPreferencesCounter.getInt("counter",0);
        counter.setText(""+float_counter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //sliderlayout - flipper
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.THIN_WORM);
        sliderLayout.setScrollTimeInSec(2);
        setSlideViews();
        //
    }

    //slider layout - flipper

    private void setSlideViews() {
        for (int i=0;i<=2;i++){
            SliderView sliderView = new SliderView(getContext());

            switch (i){
                case 0: {
                    sliderView.setImageDrawable(R.drawable.honey_chilli_potatoes);
                    sliderView.setDescription("Explore the Snacks section");
                    break;
                }
                case 1: {
                    sliderView.setImageDrawable(R.drawable.chicken_manchurian);
                    sliderView.setDescription("The Best of Non-veg");
                    break;
                }
                case 2: {
                    sliderView.setImageDrawable(R.drawable.spring_rolls);
                    sliderView.setDescription("Delicious Food Cuisine Section");
                    break;
                }
            }
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {

                }
            });
            sliderLayout.addSliderView(sliderView);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    //
}
