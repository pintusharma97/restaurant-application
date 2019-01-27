package pintu_sharma97.com.mrchef;

import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText tableId,password;
    private String id,passwrd;
    private Button loginButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ApiInterface apiInterface;
   // private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Login");

        sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);
        //bundle = new Bundle();
        init();
        loginButton.setOnClickListener(this);
    }

    private void init() {
        tableId = findViewById(R.id.tableID);
        password = findViewById(R.id.tablePassword);
        loginButton = findViewById(R.id.login_button);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    public void login(){

        id = tableId.getText().toString().trim();
        passwrd = password.getText().toString().trim();
        if (id.isEmpty())tableId.setError("Please Fill the Fields");
        else if (passwrd.isEmpty())password.setError("Please Fill the Fields");
        else {
            Call<User> call = apiInterface.login(id,passwrd);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User res = response.body();
                    if (res!=null) {
                        String resp = res.getResponse();
                            Toast.makeText(Login.this, resp, Toast.LENGTH_SHORT).show();
                            switch (resp){
                                case "Login Successfull":{
                                    editor = sharedPreferences.edit();
                                    editor.putString("tableid", id);
                                    editor.putString("password", passwrd);
                                    editor.commit();
                                    Intent intent = new Intent(Login.this,NavigationActivityMain.class);
                                    //bundle.putString("tableid",id);
                                    //intent.putExtras(bundle);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                    break;
                                }
                            }
                            /*if (res.getResponse().equals("Login Successfull")) {
                                editor = sharedPreferences.edit();
                                editor.putString("tableid", id);
                                editor.putString("password", passwrd);
                                editor.commit();
                                startActivity(new Intent(getApplicationContext(), NavigationActivityMain.class));
                                finish();
                            }*/
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(Login.this, "Error : "+t, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (v==loginButton)
            login();
    }
}
