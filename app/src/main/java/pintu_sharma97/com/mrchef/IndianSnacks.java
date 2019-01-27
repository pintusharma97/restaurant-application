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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndianSnacks extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private AdapterSnackOne adapterSnackOne;
    private ApiInterface apiInterface;
    private int itemid;
    private String subcategorytitle,tableid;
    private SharedPreferences sharedPreferences;
    private FloatingActionButton actionButton;
    private TextView counter;


    //private String[] titlemenu;
    //private Integer[] imgs = {R.drawable.dumplings,R.drawable.spring,R.drawable.fried_noodles,R.drawable.honey_chillipotatoes,R.drawable.shushi,R.drawable.chicken};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indian_snacks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        Bundle bundle = getIntent().getExtras();
        itemid = bundle.getInt("id");
        subcategorytitle = bundle.getString("subcategoryname");
        setTitle(subcategorytitle);

        sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);
        tableid = sharedPreferences.getString("tableid","");

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

       // titlemenu = getResources().getStringArray(R.array.menu_item);

        recyclerView.setHasFixedSize(true);
        Call<MenuData> call = apiInterface.CategorySecond(itemid);
        call.enqueue(new Callback<MenuData>() {
            @Override
            public void onResponse(Call<MenuData> call, Response<MenuData> response) {
                MenuData res = response.body();
                recyclerView.setLayoutManager(new GridLayoutManager(IndianSnacks.this,2));
                if (tableid!="") {
                    adapterSnackOne = new AdapterSnackOne(IndianSnacks.this, res.getMenuItems(), subcategorytitle,tableid,counter);
                }
                recyclerView.setAdapter(adapterSnackOne);
            }

            @Override
            public void onFailure(Call<MenuData> call, Throwable t) {

            }
        });
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView_indian_snacks);
        actionButton = findViewById(R.id.floating_cart);
        actionButton.setOnClickListener(this);
        counter = findViewById(R.id.counter_TextView);
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
