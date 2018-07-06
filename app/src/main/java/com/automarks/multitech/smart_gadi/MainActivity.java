package com.automarks.multitech.smart_gadi;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.automarks.multitech.smart_gadi.Firebase.Constancs;
import com.automarks.multitech.smart_gadi.Fragment.AboutUs;
import com.automarks.multitech.smart_gadi.Fragment.Dashboard;
import com.automarks.multitech.smart_gadi.Fragment.Testamonials_activity;
import com.automarks.multitech.smart_gadi.Helper.GPSTracker;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    GPSTracker gps;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NotificationManager mNotificationmanager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel=new NotificationChannel(Constancs.CHANNEL_ID,Constancs.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);

        mChannel.setDescription(Constancs.CHANNEL_DESCRIPTION);
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,400});

        mNotificationmanager.createNotificationChannel(mChannel);

        }


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }


        if(Build.VERSION.SDK_INT>21)
        {
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);






//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        final FragmentManager fragmentmanager = getSupportFragmentManager();

        final FragmentTransaction fragmenttranscation = fragmentmanager.beginTransaction();
        Dashboard dashboard = new Dashboard();
        fragmenttranscation.replace(R.id.mainFragment, dashboard);
        fragmenttranscation.commit();




    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    void getLatLong() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }
        else {

            gps = new GPSTracker(this, this);

            // Check if GPS enabled
            if (gps.canGetLocation()) {

                latitude = gps.getLatitude();
                longitude = gps.getLongitude();


                if (latitude == 0.0 && longitude == 0.0) {

                    Toast.makeText(this, "Could't get location coordinate please try again", Toast.LENGTH_SHORT).show();


                } else {

                    String uri = "http://maps.google.com/maps?saddr=" + latitude + "," + longitude;

                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);

                    sharingIntent.setType("text/plain");

                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, uri);
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }


            } else {
                // Can't get location.
                // GPS or network is not enabled.
                // Ask user to enable GPS/network in settings.
                gps.showSettingsAlert();
            }
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_phone) {

            Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:9779851000038"));
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_share) {

            if (isOnline()) {


                getLatLong();


            } else {
                Toast.makeText(this, "Check your Internet connection", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.nav_call) {

            Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:9779851000038"));
            startActivity(i);
        } else if (id == R.id.nav_messenger) {


            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.me/retreatapartment"));
            startActivity(intent);




        }
        else if (id == R.id.nav_email) {

                        Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_EMAIL,
                    new String[]{"sgaadi2007@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "subject");
            i.putExtra(Intent.EXTRA_TEXT, "message");
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MainActivity.this,
                        "There are no email clients installed.",
                        Toast.LENGTH_SHORT).show();

            }

        }
        else if (id == R.id.nav_testamonials) {

            Testamonials_activity testamonials_activity=new Testamonials_activity();
            fragmentTransaction.replace(R.id.mainFragment, testamonials_activity);
            fragmentTransaction.addToBackStack("Smart gaadi");




        }






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        fragmentTransaction.commit();
        return true;
    }
}
