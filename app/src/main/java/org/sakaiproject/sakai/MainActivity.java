package org.sakaiproject.sakai;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.sakaiproject.api.internet.NetWork;
import org.sakaiproject.helpers.ActionsHelper;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean isConnected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            WelcomeFragment welcomeFragment = new WelcomeFragment();
            ActionsHelper.selectFragment(welcomeFragment, R.id.content_frame, this);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        NetWork.isConnected(this);


        /* if the user press on the session notification but the session has expired
           the user will get that dialog message
        */
        if (getIntent().getBooleanExtra("session_expired", false)) {
            AlertDialog.Builder adb = new AlertDialog.Builder(getSupportActionBar().getThemedContext());

            adb.setTitle(getResources().getString(R.string.session_expired));

            adb.setPositiveButton(getResources().getString(R.string.ok), null);

            Dialog d = adb.show();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            closeApp();
        }
    }

    public void closeApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.welcome:
                WelcomeFragment welcomeFragment = new WelcomeFragment();
                ActionsHelper.selectFragment(welcomeFragment, R.id.content_frame, this);
                break;
            case R.id.about:
                AboutFragment aboutFragment = new AboutFragment();
                ActionsHelper.selectFragment(aboutFragment, R.id.content_frame, this);
                break;
            case R.id.features:
                showMessage();
                break;
            case R.id.training:
                showMessage();
                break;
            case R.id.acknowledgements:
                showMessage();
                break;
            case R.id.help:
                showMessage();
                break;
            case R.id.login:
                showMessage();
                break;
            case R.id.new_user:
                showMessage();
                break;
            case R.id.offline_use:
                OfflineFragment offlineFragment = new OfflineFragment();
                ActionsHelper.selectFragment(offlineFragment, R.id.content_frame, this);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showMessage() {
        Toast.makeText(this, "Offline_Use Demo! visit comalat.eu ", Toast.LENGTH_SHORT).show();
    }
}
