package com.automarks.multitech.smart_gadi.Fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.automarks.multitech.smart_gadi.Public_url;
import com.automarks.multitech.smart_gadi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AboutUs extends android.support.v4.app.Fragment {
    View mainView;

    String url = Public_url.About_us;
    ProgressDialog pDilog;
    WebView aboutus_webview;

    String title,body;

    String pish = "<html><head><style type=\"text/css\">@font-face {font-family: 'CenturyGothicRegular';" +
            "src: url(\"file:///android_asset/fonts/CenturyGothicRegular.ttf\")}body {font-family: 'CenturyGothicRegular';font-size: Large;text-align: justify;}</style></head><body>";
    String pas = "</body></html>";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.about_us_layout, container, false);
        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.prgressdilogue);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();

        aboutus_webview = (WebView) mainView.findViewById(R.id.aboutus_webview);
        aboutus_webview.setBackgroundColor(Color.TRANSPARENT);


        getData();
        return mainView;
    }


    void getData()

    {

        final JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @RequiresApi(api = Build.VERSION_CODES.M)

            @Override
            public void onResponse(JSONArray response) {
                pDilog.dismiss();

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject object = response.getJSONObject(i);
                        title = object.getString("title");
                        body = object.getString("body");
                        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);


//                        aboutus_webview.loadData(body, "text/html", "UTF-8");

                        String myHtmlString = pish + body + pas;

                        aboutus_webview.loadDataWithBaseURL(null, myHtmlString, "text/html", "UTF-8", null);



                    } catch (JSONException e) {
                        e.printStackTrace();

                        Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (isOnline())
                {
                    Toast.makeText(getContext(), "Server error", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
                }

                pDilog.dismiss();
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);


    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }




}
