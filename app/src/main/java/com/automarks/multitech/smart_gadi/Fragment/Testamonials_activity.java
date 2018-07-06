package com.automarks.multitech.smart_gadi.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.automarks.multitech.smart_gadi.Pojo.Testimonial_data;
import com.automarks.multitech.smart_gadi.Public_url;
import com.automarks.multitech.smart_gadi.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Testamonials_activity extends Fragment {
    RecyclerView recyclerView;
    ProgressDialog pDilog;
    String url= Public_url.Testamonials;
    ArrayList<Testimonial_data> list;
    Testimonial_data data;
    TestimonialAdapter adapter;

    View mainView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.testimonials_layout,container,false);
        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.prgressdilogue);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();

        recyclerView=(RecyclerView)mainView.findViewById(R.id.testimonial_id);
        recyclerView.setNestedScrollingEnabled(false);



    getData();


        return mainView;
    }

    void getData()
    {

        JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pDilog.dismiss();
                list=new ArrayList<>();

                for (int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject object=response.getJSONObject(i);
                        String title=object.getString("title");
                        String body=object.getString("body");
                        String field_designation=object.getString("field_designation");
                        String field_full_name=object.getString("field_full_name");

                   data=new Testimonial_data(title,field_full_name,body,"http://smart.nuzatech.com/sites/default/files/2018-07/police.png",field_designation);

                   list.add(data);
                   adapter=new TestimonialAdapter(getContext(),list);
                   recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDilog.dismiss();

                if (isOnline())
                {
                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
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
class TestimonialAdapter extends RecyclerView.Adapter<TestimonialHolder>
{

    Context context;
    ArrayList<Testimonial_data> data;
    LayoutInflater layoutInflater;

    String pish = "<html><head><style type=\"text/css\">@font-face {font-family: 'CenturyGothicRegular';" +
            "src: url(\"file:///android_asset/fonts/CenturyGothicRegular.ttf\")}body {font-family: 'CenturyGothicRegular';font-size: medium;text-align: justify;}</style></head><body>";
    String pas = "</body></html>";


    public TestimonialAdapter(Context context, ArrayList<Testimonial_data> data) {
        this.context = context;
        this.data = data;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public TestimonialHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.testimonials_design,parent,false);
        TestimonialHolder holder=new TestimonialHolder(view,context,data);

        return holder;    }


    @Override
    public void onBindViewHolder(TestimonialHolder holder, int position) {
        Testimonial_data current=data.get(position);

        Picasso.with(context)
                .load(current.getImage())
//                .placeholder(R.drawable.defult)   // optional
                .error(R.drawable.defult)      // optional
                .resize(290,290)
                .into(holder.image);
        holder.name.setText(current.getName());
//        holder.ratingtext.loadData(current.getReviewtext(), "text/html", "utf-8");

        holder.title.setText(current.getTitle());
        holder.description.setText(current.getDesctiption());


        String myHtmlString1 = pish +current.getReviewtext() + pas;
        holder.body.loadDataWithBaseURL(null, myHtmlString1, "text/html", "UTF-8", null);






    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class TestimonialHolder extends RecyclerView.ViewHolder
{
    ImageView image;
    TextView name;
    TextView title;
    WebView body;
    TextView description;
    Typeface type;
    Context context;
    ArrayList<Testimonial_data> data;


    public TestimonialHolder(View item,Context context,ArrayList<Testimonial_data> data) {
        super(item);
        this.context=context;
        this.data=data;
        title=(TextView)item.findViewById(R.id.title_testemanials);
description=(TextView)item.findViewById(R.id.person_description);
        name=(TextView) item.findViewById(R.id.person_name);
        body=(WebView) item.findViewById(R.id.testimonial_text);
        image=(ImageView)item.findViewById(R.id.person_image);

        type = Typeface.createFromAsset(item.getContext().getAssets(),"fonts/CenturyGothicRegular.ttf");
        title.setTypeface(type);
        name.setTypeface(type);
        description.setTypeface(type);
    }

}