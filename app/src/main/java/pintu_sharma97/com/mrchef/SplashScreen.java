package pintu_sharma97.com.mrchef;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String tableid,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tableid = sharedPreferences.getString("tableid","");
                password = sharedPreferences.getString("password","");


                if (tableid!=""&&password!=""){
                    Intent intent = new Intent(SplashScreen.this,NavigationActivityMain .class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashScreen.this,NavigationActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },1500);
    }
}



















/*
* <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="200dp"
            android:layout_margin="8dp"
            android:orientation="horizontal">
            <android.support.v7.widget.CardView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                app:cardCornerRadius="10dp"
                android:elevation="200dp">
                <com.smarteist.autoimageslider.SliderLayout
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:id="@+id/flipper"/>
            </android.support.v7.widget.CardView>
        </LinearLayout>
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_margin="8dp"
            android:orientation="vertical">
           <android.support.v7.widget.RecyclerView
               android:layout_width="match_parent"
               android:layout_height="match_parent"
               android:id="@+id/recyclerView"/>
        </LinearLayout>
* */