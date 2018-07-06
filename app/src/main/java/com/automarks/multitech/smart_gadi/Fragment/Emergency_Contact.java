package com.automarks.multitech.smart_gadi.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.automarks.multitech.smart_gadi.Pojo.Emergency_data;
import com.automarks.multitech.smart_gadi.Public_url;
import com.automarks.multitech.smart_gadi.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Emergency_Contact extends Fragment {
    View mainView;
    String url = Public_url.Emergency_contacts;
    String title, number, image;
    Emergency_data data;
    ArrayList<Emergency_data> a_data;
    EmergencyAdapter mAdapter;
    RecyclerView recyclerView;
    ProgressDialog pDilog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.emergency_contact_layout, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Emergency contacts");

        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.prgressdilogue);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();

        recyclerView = (RecyclerView) mainView.findViewById(R.id.emergency_lists_no);


        getData();

        return mainView;
    }

    void getData() {

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pDilog.dismiss();
                a_data = new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {
                    try {

                        data = new Emergency_data();

                        JSONObject object = response.getJSONObject(i);
                        title = object.getString("title");
                        number = object.getString("body");
                        image = object.getString("field_emergency_image");

                        data.setTitle(title);
                        data.setImage(image);
                        data.setNumber(number);

                        a_data.add(data);

                        mAdapter = new EmergencyAdapter(getContext(), a_data);
                        recyclerView.setAdapter(mAdapter);
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));


                    } catch (JSONException e) {
                        e.printStackTrace();

                        Toast.makeText(getContext(), "Exception occure "+e, Toast.LENGTH_LONG).show();
                    }

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

class EmergencyAdapter extends RecyclerView.Adapter<EmergencyHolder> {

    Context context;
    ArrayList<Emergency_data> data;
    LayoutInflater layoutInflater;
    Typeface face;


    public EmergencyAdapter(Context context, ArrayList<Emergency_data> data) {
        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public EmergencyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.emergency_design, parent, false);
        EmergencyHolder holder = new EmergencyHolder(view, context, data);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmergencyHolder holder, int position) {
        Emergency_data current = data.get(position);
        face = Typeface.createFromAsset(context.getAssets(),"fonts/CenturyGothicRegular.ttf");

        Picasso.with(context)
                .load("http://smart.nuzatech.com" + current.getImage())
//                .placeholder(R.drawable.defult)   // optional
//                .error(R.drawable.defult)      // optional
                .resize(100, 100)
                .into(holder.image);
//        holder.event_name.loadData(current.getTitle(), "text/html", "UTF-8");
        holder.title.setTypeface(face);
        holder.title.setText(current.getTitle());


    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}

class EmergencyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView image;
    TextView title;
    Context context;
    ArrayList<Emergency_data> data;


    public EmergencyHolder(View item, Context context, ArrayList<Emergency_data> data) {
        super(item);
        this.context = context;
        this.data = data;
        item.setOnClickListener((View.OnClickListener) this);

        title = (TextView) item.findViewById(R.id.emergency_title);
        image = (ImageView) item.findViewById(R.id.emergency_image);


    }

    @Override
    public void onClick(final View v) {


        int positon = getAdapterPosition();

        Emergency_data current = data.get(positon);

        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + current.getNumber()));
        context.startActivity(i);


    }
}