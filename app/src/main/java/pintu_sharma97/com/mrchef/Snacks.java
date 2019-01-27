package pintu_sharma97.com.mrchef;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Snacks extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    //private Integer[] images = {R.drawable.mocktail,R.drawable.tea,R.drawable.coffee,R.drawable.smoothie,R.drawable.shakes,R.drawable.juice};
    //private String[] title = {"Indian","Italian","Chinese","Mexican","Japanese","French"};
    private AdapterSnacks adapter;
    private ApiInterface apiInterface;
    private int id;
    private String categoryname;
    private FloatingActionButton actionButton;
    private SharedPreferences sharedPreferencesCounter;
    private TextView counter;
    private int float_counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snacks);
        main();
    }

    public void main(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        categoryname = bundle.getString("categoryname");
        setTitle(categoryname);

        sharedPreferencesCounter = getSharedPreferences("mycounter",MODE_PRIVATE);
        float_counter = sharedPreferencesCounter.getInt("counter",0);
        counter.setText(""+float_counter);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        recyclerView.setHasFixedSize(true);

        Call<CategoryFirstData> call = apiInterface.CategoryFirst(id);
        call.enqueue(new Callback<CategoryFirstData>() {
            @Override
            public void onResponse(Call<CategoryFirstData> call, Response<CategoryFirstData> response) {
                CategoryFirstData res = response.body();
                recyclerView.setLayoutManager(new GridLayoutManager(Snacks.this,2));
                adapter = new AdapterSnacks(Snacks.this,res.getDataCategory(),counter);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<CategoryFirstData> call, Throwable t) {
                Toast.makeText(Snacks.this, "Error : "+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        main();
    }

    private void init() {
        recyclerView = findViewById(R.id.snack_RecyclerView);
        actionButton = findViewById(R.id.floating_cart);
        counter = findViewById(R.id.counter_TextView);
        actionButton.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v== actionButton){
            Intent intent = new Intent(this,OrderList.class);
            startActivity(intent);
        }
    }
}
