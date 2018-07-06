package com.automarks.multitech.smart_gadi.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.automarks.multitech.smart_gadi.Adapter.Question_Adapter;
import com.automarks.multitech.smart_gadi.Pojo.Faq_answer;
import com.automarks.multitech.smart_gadi.Pojo.Faq_question;
import com.automarks.multitech.smart_gadi.Public_url;
import com.automarks.multitech.smart_gadi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Faq_activity extends Fragment {
    View mainView;

    String url = Public_url.Faq;
    ProgressDialog pDilog;
    Faq_answer ans;
    Faq_question qus;
    RecyclerView faq;
    Question_Adapter mAdapter;

    String pish = "<html><head><style type=\"text/css\">@font-face {font-family: 'Raleway';" +
            "src: url(\"file:///android_asset/fonts/CenturyGothicRegular.ttf\")}body {font-family: 'CenturyGothic.';font-size: medium;text-align: justify;}</style></head><body>";
    String pas = "</body></html>";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.faq_layout, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("FAQ");


        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.prgressdilogue);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();

        faq = (RecyclerView) mainView.findViewById(R.id.faq_id);
        faq.setNestedScrollingEnabled(false);

        getData();
        return mainView;
    }

    void getData() {
        final ArrayList<Faq_question> genres = new ArrayList<>();


        final JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {
                pDilog.dismiss();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);

                        List<Faq_answer> list = new ArrayList<>();

                        String body = object.getString("field_faq_answer");
                        String question = object.getString("field_faq_question");


                        list.add(new Faq_answer(body));


                        genres.add(new Faq_question(question, list));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mAdapter = new Question_Adapter(genres);
                    faq.setLayoutManager(new LinearLayoutManager(getContext()));
                    faq.setAdapter(mAdapter);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDilog.dismiss();
                if (isOnline()) {
                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

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
