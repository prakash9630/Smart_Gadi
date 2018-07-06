package com.automarks.multitech.smart_gadi;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Contact_form extends android.support.v4.app.Fragment {
    View mainView;

    WebView webView;
    SwipeRefreshLayout mySwipeRefreshLayout;

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.contact_form,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Contact form");



        webView = (WebView)mainView.findViewById(R.id.contact_form);
        webView.loadUrl("http://smart.nuzatech.com/form/contact-us");


        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setDomStorageEnabled(true);

        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.clearCache(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        webView.getSettings().setBuiltInZoomControls(true);






        webView.setWebViewClient(new WebViewClient() {
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




        webView.setWebChromeClient(new WebChromeClient() {
            private ProgressDialog mProgress;

            @Override
            public void onProgressChanged(WebView view, int progress) {

                if (mProgress == null) {
                    mProgress = new ProgressDialog(getContext());
                    mProgress.show();
                }
                mProgress.setMessage("Loading " + String.valueOf(progress) + "%");
                if (progress == 100) {
                    mProgress.dismiss();
                    mProgress = null;
                }

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

    private void webViewGoBack(){
        webView.goBack();
    }


}
