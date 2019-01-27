package pintu_sharma97.com.mrchef;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderList extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences,sharedPreferencesCounter;
    private SharedPreferences.Editor editor;
    private String tableid;
    private ApiInterface apiInterface;
    private OrderListAdapter checkoutAdapter;
    private TextView empptyView;
    private Button checkout;
    private int float_counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

        sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);
        sharedPreferencesCounter = getSharedPreferences("mycounter",MODE_PRIVATE);

        editor = sharedPreferencesCounter.edit();

        tableid  = sharedPreferences.getString("tableid","");

        empptyView = findViewById(R.id.emptyView);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        recyclerView.setHasFixedSize(true);


        Call<GetCartData> call = apiInterface.GetCartList(tableid);
        call.enqueue(new Callback<GetCartData>() {
            @Override
            public void onResponse(Call<GetCartData> call, Response<GetCartData> response) {
                GetCartData res = response.body();
                recyclerView.setLayoutManager(new LinearLayoutManager(OrderList.this,LinearLayoutManager.VERTICAL,false));
                checkoutAdapter = new OrderListAdapter(OrderList.this,res.getCartData());
                if (checkoutAdapter !=null && empptyView!=null){
                    if (checkoutAdapter.getItemCount()==0){
                        empptyView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        checkout.setVisibility(View.GONE);
                        float_counter = 0;
                        editor.putInt("counter",float_counter);
                        editor.commit();

                    }else {
                        empptyView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        checkout.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(checkoutAdapter);
                        float_counter = checkoutAdapter.getItemCount();
                        editor.putInt("counter",float_counter);
                        editor.commit();

                    }
                }

            }

            @Override
            public void onFailure(Call<GetCartData> call, Throwable t) {
            }
        });

    }

    private void init() {
        recyclerView = findViewById(R.id.order_ListRecyclerView);
        checkout = findViewById(R.id.checkout);
        checkout.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v==checkout){
            Intent intent = new Intent(this,Checkout.class);
            startActivity(intent);
        }
    }
}
