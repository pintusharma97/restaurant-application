package pintu_sharma97.com.mrchef;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class NavigationActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment fragment = null;
    private FragmentManager manager;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String tableid;
    private TextView id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        //tableid = bundle.getString("tableid");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);

        tableid = sharedPreferences.getString("tableid","");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.inflateHeaderView(R.layout.nav_header_navigation_activity_main);
        id = view.findViewById(R.id.navTableNumber);

        if (tableid!=""){
            id.setText(tableid);
        }

        navigationView.setNavigationItemSelectedListener(this);
    }

    public void fragmentHome(){
        fragment = new Home();
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment,fragment).commit();
    }

    public void myOrders(){
        Intent intent = new Intent(this,OrderList.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();
        }else {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            fragmentMyOrders();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragmentHome();
        } else if (id == R.id.nav_my_order) {
            myOrders();
        } else if (id==R.id.nav_exit){
            editor = sharedPreferences.edit();
            editor.clear().commit();
            Intent intent = new Intent(getApplicationContext(),NavigationActivity.class);
            startActivity(intent);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
