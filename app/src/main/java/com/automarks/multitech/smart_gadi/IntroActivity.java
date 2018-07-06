package com.automarks.multitech.smart_gadi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {

    TextView descText,title;
    ImageView img;
    Typeface face;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);








        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (!isFirstRun) {
            //show start activity

            startActivity(new Intent(this, MainActivity.class));
            this.finish();

        }





        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();


        setSkipTextTypeface("fonts/CenturyGothicRegular.ttf");
        setDoneTextTypeface("fonts/CenturyGothicRegular.ttf");


        addSlide(AppIntroFragment.newInstance("Real Time Tracking","fonts/CenturyGothicRegular.ttf","The GPS location of your vehicle is displayed on a map along with vehicle speed if the vehicle is moving.","fonts/CenturyGothicRegular.ttf", R.drawable.realtimetrack, ContextCompat.getColor(getApplicationContext(),R.color.slidercolor1)));
        addSlide(AppIntroFragment.newInstance("Alerts","fonts/CenturyGothicRegular.ttf","Using vehicle tracking system features like over speeding alert, start of day alert, route deviation alert and excessive stoppage alert.","fonts/CenturyGothicRegular.ttf", R.drawable.alert, ContextCompat.getColor(getApplicationContext(),R.color.slidercolor2)));
        addSlide(AppIntroFragment.newInstance("Anytime Anywhere Access", "fonts/CenturyGothicRegular.ttf"," Now every information is available at your fingertips , it is imperative invest in a  vehicle tracking system that is accessible on web and on smart phone.","fonts/CenturyGothicRegular.ttf", R.drawable.anywhere, ContextCompat.getColor(getApplicationContext(),R.color.slidercolor3)));

    }



    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }


}
