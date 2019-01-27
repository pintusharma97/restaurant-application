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

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private String tabname;
    private ApiInterface apiInterface;
    //private AdapterMenu adapterMenu;
    private FloatingActionButton actionButton;
    private TextView counter;
    private Adapterbreak adapter;
    private SharedPreferences sharedPreferencesCounter;
    private int float_counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

        Bundle bundle = getIntent().getExtras();
        tabname = bundle.getString("tabname");
        setTitle(tabname);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        sharedPreferencesCounter = getSharedPreferences("mycounter",MODE_PRIVATE);
        float_counter = sharedPreferencesCounter.getInt("counter",0);
        counter.setText(""+float_counter);

        recyclerView.setHasFixedSize(true);

        Call<TabItemData> call = apiInterface.getTabItems(tabname);
        call.enqueue(new Callback<TabItemData>() {
            @Override
            public void onResponse(Call<TabItemData> call, Response<TabItemData> response) {
                TabItemData res = response.body();
                recyclerView.setLayoutManager(new GridLayoutManager(MenuActivity.this,2));
                adapter = new Adapterbreak(MenuActivity.this,res.getTabItems(),tabname,counter);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<TabItemData> call, Throwable t) {

            }
        });
        /*
        call.enqueue(new Callback<DataTab>() {
            @Override
            public void onResponse(Call<DataTab> call, Response<DataTab> response) {
                DataTab res = response.body();
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                adapter = new Adapterbreak(MenuActivity.this,res.getTabItems());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<DataTab> call, Throwable t) {
                Toast.makeText(MenuActivity.this, "Error : "+t, Toast.LENGTH_SHORT).show();
            }
        });*/


    }

    private void init() {
        recyclerView = findViewById(R.id.RecyclerViewTabMenu);
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
        if (v==actionButton){
            Intent intent = new Intent(this,OrderList.class);
            startActivity(intent);
        }
    }
}
