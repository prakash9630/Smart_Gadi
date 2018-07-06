package com.automarks.multitech.smart_gadi.Fragment;


import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.Volley;

import com.automarks.multitech.smart_gadi.Pojo.Slider_data;
import com.automarks.multitech.smart_gadi.Public_url;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.automarks.multitech.smart_gadi.Contact_form;
import com.automarks.multitech.smart_gadi.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Dashboard extends android.support.v4.app.Fragment {
    @Nullable
    SliderLayout slider;
    ImageView mGpsmap,mContactform,mAboutus,mFaq,mEmergency;
    String url= Public_url.Slider_img;
    String image,title;
    Slider_data slider_data;
    ArrayList<Slider_data> data;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.dashboard_layout,container,false);
        slider=(SliderLayout)view.findViewById(R.id.dash_slider);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Smart gaadi");

             sliderData();

        mGpsmap=(ImageView) view.findViewById(R.id.click_gps);
        mAboutus=(ImageView) view.findViewById(R.id.click_aboutus);
        mContactform=(ImageView) view.findViewById(R.id.click_contactform);
        mFaq=(ImageView)view.findViewById(R.id.btn_faq);
        mEmergency=(ImageView)view.findViewById(R.id.emergency_contactid) ;

        FragmentManager fragmentmanager =getFragmentManager();

        final FragmentTransaction fragmenttranscation = fragmentmanager.beginTransaction();



        mGpsmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gps_map map= new Gps_map();
                fragmenttranscation.replace(R.id.mainFragment, map);
                fragmenttranscation.addToBackStack("Gps map");
                fragmenttranscation.commit();

            }
        });

        mAboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutUs aboutUs= new AboutUs();
                fragmenttranscation.replace(R.id.mainFragment, aboutUs);
                fragmenttranscation.addToBackStack("About Us");

                fragmenttranscation.commit();
            }
        });
        mContactform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact_form contact= new Contact_form();
                fragmenttranscation.replace(R.id.mainFragment,contact);
                fragmenttranscation.addToBackStack("Contact Form");

                fragmenttranscation.commit();
            }
        });
        mFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Faq_activity faq= new Faq_activity();
                fragmenttranscation.replace(R.id.mainFragment,faq);
                fragmenttranscation.addToBackStack("FAQ");

                fragmenttranscation.commit();

            }
        });

        mEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Emergency_Contact ec= new Emergency_Contact();
                fragmenttranscation.replace(R.id.mainFragment,ec);
                fragmenttranscation.addToBackStack("Emergency contacts");

                fragmenttranscation.commit();


            }
        });




         return view;

    }


    private void sliderData()
    {

        JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                data= new ArrayList<>();



                for (int i=0;i<response.length();i++)
                {
                    try {
                        slider_data=new Slider_data();

                        JSONObject object=response.getJSONObject(i);

                        slider_data.setImage(object.getString("field_image"));
                        slider_data.setTitle(object.getString("title"));

                        data.add(slider_data);



                        TextSliderView textSliderView = new TextSliderView(getContext());
                        // initialize a SliderLayout
                        textSliderView
                                .description(object.getString("title"))
                                .image("http://smart.nuzatech.com"+object.getString("field_image"))
                                .setScaleType(BaseSliderView.ScaleType.Fit);





                        slider.setPresetTransformer(SliderLayout.Transformer.Fade);

                        slider.addSlider(textSliderView);
                        slider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
//                    slider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
                        slider.setDuration(5000);






                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Exception occure", Toast.LENGTH_SHORT).show();
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

            }}
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




