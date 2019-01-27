package pintu_sharma97.com.mrchef;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CuisineSection extends AppCompatActivity implements View.OnClickListener {
    private String Section_title,section_id;
    private RecyclerView recyclerView;
    private ApiInterface apiInterface;
    private AdapterSnackOne one;
    private SharedPreferences sharedPreferences;
    private String tableid;
    private TextView counter;
    private FloatingActionButton actionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indian_snacks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

        sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);
        tableid = sharedPreferences.getString("tableid","");

        Bundle bundle = getIntent().getExtras();
        Section_title = bundle.getString("sectiontitle");
        section_id = bundle.getString("sectionid");
        setTitle(Section_title);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<MenuData> call = apiInterface.SectionData(section_id);
        call.enqueue(new Callback<MenuData>() {
            @Override
            public void onResponse(Call<MenuData> call, Response<MenuData> response) {
                MenuData res = response.body();
                recyclerView.setLayoutManager(new GridLayoutManager(CuisineSection.this,2));
                if (tableid!="") {
                    one = new AdapterSnackOne(CuisineSection.this, res.getMenuItems(), Section_title,tableid,counter);
                }
                recyclerView.setAdapter(one);
            }

            @Override
            public void onFailure(Call<MenuData> call, Throwable t) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView_indian_snacks);
        actionButton = findViewById(R.id.floating_cart);
        actionButton.setOnClickListener(this);
        counter = findViewById(R.id.counter_TextView);
    }

    @Override
    public void onClick(View v) {
        if (v==actionButton){
            Intent intent = new Intent(this,OrderList.class);
            startActivity(intent);
        }
    }
}
