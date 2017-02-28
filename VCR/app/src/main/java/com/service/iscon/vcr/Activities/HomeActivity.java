package com.service.iscon.vcr.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.service.iscon.vcr.Handler.MyDBHelper;
import com.service.iscon.vcr.Model.UserInfo;
import com.service.iscon.vcr.R;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView nav_name, nav_total_round, nav_last_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);

        nav_name = (TextView)header.findViewById(R.id.nav_name);
        nav_total_round = (TextView)header.findViewById(R.id.nav_total_round);
        nav_last_login = (TextView)header.findViewById(R.id.nav_last_login);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        MyDBHelper db = new MyDBHelper(HomeActivity.this);
        UserInfo mUserInfo = db.getUserInfo();
        if (mUserInfo != null) {
            if (mUserInfo.getFullName() != null)
                nav_name.setText(mUserInfo.getFullName());
            if (mUserInfo.getLastLogin() != null)
                nav_last_login.setText("Last Login: " + mUserInfo.getLastLogin());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent i = new Intent();
        if (id == R.id.nav_home) {
            // Handle the camera action
            i = new Intent(this, RegistrationActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_history) {
            i = new Intent(this, HistoryActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_about_us) {
            i = new Intent(this, AboutActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_setting) {
            i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_sign_out) {
            //call service to logout

            //clearing local storage
            MyDBHelper db = new MyDBHelper(this);
            db.clearUser();

            i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
