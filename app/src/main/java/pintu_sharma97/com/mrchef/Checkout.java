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
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Checkout extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;
    private String tableid;
    private ApiInterface apiInterface;
    private CheckoutAdapter checkoutAdapter;
    private Button pay;
    private TextView number_item,sub_Total,tax_textView,totalAmount;
    private int size,subTotal=0;
    private float tax,Amount;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

        sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);
        tableid  = sharedPreferences.getString("tableid","");
        bundle = new Bundle();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        recyclerView.setHasFixedSize(true);
        Call<GetCartData> call = apiInterface.GetCartList(tableid);
        call.enqueue(new Callback<GetCartData>() {
            @Override
            public void onResponse(Call<GetCartData> call, Response<GetCartData> response) {
                GetCartData res = response.body();
                recyclerView.setLayoutManager(new LinearLayoutManager(Checkout.this,LinearLayoutManager.VERTICAL,false));
                checkoutAdapter = new CheckoutAdapter(Checkout.this,res.getCartData());
                recyclerView.setAdapter(checkoutAdapter);
                size = res.getCartData().size();
                number_item.setText(""+size);
                for (int i=0;i<size;i++){
                    subTotal = subTotal + res.getCartData().get(i).getTotal();
                }
                sub_Total.setText(""+subTotal);
                tax = (float) (subTotal*0.125);
                tax_textView.setText(""+tax);
                Amount = subTotal+tax;
                totalAmount.setText(""+Amount);

            }

            @Override
            public void onFailure(Call<GetCartData> call, Throwable t) {
                Toast.makeText(Checkout.this, "Error : "+t, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init() {
        recyclerView = findViewById(R.id.checkoutRecyclerView);
        pay = findViewById(R.id.proceed_pay);
        number_item = findViewById(R.id.number_of_item);
        sub_Total = findViewById(R.id.subTotal);
        tax_textView = findViewById(R.id.tax);
        totalAmount = findViewById(R.id.total_amount);
        pay.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v==pay){
            Intent intent = new Intent(this,Payment.class);
            bundle.putFloat("amount",Amount);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
