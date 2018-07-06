package com.automarks.multitech.smart_gadi.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.automarks.multitech.smart_gadi.R;

public class Gps_map extends Fragment {
View mainView;
    WebView webView;
    //    SwipeRefreshLayout mySwipeRefreshLayout;
    boolean doubleBackToExitPressedOnce = false;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:{
                    webViewGoBack();
                }break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       mainView=inflater.inflate(R.layout.gps_map,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Gps Map");


        if(!isOnline())
        {
            Toast.makeText(getContext(), "No Internet connection", Toast.LENGTH_LONG).show();
        }


        webView = (WebView)mainView. findViewById(R.id.website_id_k);
        webView.loadUrl("http://128.199.248.188:8082/");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.getJavaScriptEnabled();







        webSettings.setDomStorageEnabled(true);



        webView.setWebViewClient(new WebViewClient() {




            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if( URLUtil.isNetworkUrl(url) ) {
                    return false;
                }

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity( intent );
                return true;
            }






            @Override
            public void onPageFinished(WebView view, String url)
            {

                webView.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('mainNavigationWrapper')[0].style.display='none'; " +
                        "document.getElementsByClassName('breadcrumbContainer')[0].style.display='none'; " +
                        "document.getElementsByClassName('footerWrapper')[0].style.display='none'; " +
                        "document.getElementsByClassName('sideAds')[0].style.display='none'; " +
                        "})()");
            }
        });





        webView.setOnKeyListener(new View.OnKeyListener(){

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && webView.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    return true;
                }

                return false;
            }

        });


        return mainView;
    }


    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }


    private void webViewGoBack(){
        webView.goBack();
    }


}
