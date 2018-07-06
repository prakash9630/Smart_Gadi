package com.automarks.multitech.smart_gadi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by prakash on 9/11/2017.
 */

public class Splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1300;

    ImageView mSplash_icon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        mSplash_icon = (ImageView) findViewById(R.id.splash_logo);

                Animation anim = AnimationUtils.loadAnimation(this, R.anim.mytransformation);

        mSplash_icon.setAnimation(anim);


        anim.setDuration(1000);
        anim.start();


        mainPage();

    }


    void mainPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                Intent mainIntent = new Intent(Splash.this,IntroActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();


            }
        }, SPLASH_DISPLAY_LENGTH);
    }



}
