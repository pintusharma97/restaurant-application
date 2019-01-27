package pintu_sharma97.com.mrchef;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Payment extends AppCompatActivity implements View.OnClickListener {
    private TextView amount;
    private float total_amount;
    private RadioGroup payment_methods;
    private Button payment_button;
    private SharedPreferences sharedPreferences,sharedPreferencesCounter;
    private SharedPreferences.Editor editor;
    private ApiInterface apiInterface;
    private String table_id;


    private static PayPalConfiguration configuration = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId("AYZh4xmWb1AE4j66Pez2KPdOjbPIXisBdHkh2k8fcgrXQcfRahbvRR6sGj9V0RdakIBJjwICd3gOf4sa");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

        Bundle bundle = getIntent().getExtras();
        total_amount = bundle.getFloat("amount");
        amount.setText(""+total_amount);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);
        table_id = sharedPreferences.getString("tableid","");
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    private void init() {
        amount = findViewById(R.id.amount);
        payment_button = findViewById(R.id.continue_pay);
        payment_button.setOnClickListener(this);
        payment_methods = findViewById(R.id.radioGroup);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v==payment_button){
            int id = payment_methods.getCheckedRadioButtonId();

            if (id==R.id.cashPayment){
                String status = "Pending";
                Call<User> call = apiInterface.OrderStatus(table_id,status);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User res = response.body();
                        if (res!=null){
                            Toast.makeText(Payment.this, ""+res.getResponse(), Toast.LENGTH_SHORT).show();
                            if (res.getResponse().equals("Waiter will be coming in minutes")) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(Payment.this, ""+t, Toast.LENGTH_SHORT).show();
                    }
                });

            }
            if (id==R.id.onlinePayment){

                Intent intent = new Intent(this, PayPalService.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);

                startService(intent);

                int amout = (int) total_amount;
                BigDecimal a = BigDecimal.valueOf(amout);
                PayPalPayment payment = new PayPalPayment(a,"USD","Item",PayPalPayment.PAYMENT_INTENT_SALE);
                Intent intent1 = new Intent(Payment.this, PaymentActivity.class);
                intent1.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,configuration);
                intent1.putExtra(PaymentActivity.EXTRA_PAYMENT,payment);
                startActivityForResult(intent1,0);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK){
            PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if(confirmation != null){
                Toast.makeText(this, "Paid Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,NavigationActivityMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                killactivity();
            }
        }else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void killactivity() {
        sharedPreferencesCounter = getSharedPreferences("mycounter",MODE_PRIVATE);
        editor = sharedPreferencesCounter.edit();
        int counter=0;
        editor.putInt("counter",counter);
        editor.commit();


        Call<User> call = apiInterface.ConfirmPay(table_id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User res = response.body();
                if (res!=null){
                    Intent intent = new Intent(Payment.this,Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
        this.finish();
    }
}
