package com.tanmoy.partnerpa;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.R.id.message;
import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class RubyFragment extends Fragment {
    private static final String URL = "https://shayongupta.000webhostapp.com/partnerapp/ruby.php";
    private StringRequest request;
    String lat = "";
    String lon = "";
    String no_slots = "";
    String no_empty = "";
    Button button;
    private RequestQueue requestQueue;


    public RubyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ruby, container, false);
        button = (Button) rootView.findViewById(R.id.button_refresh);
        final TextView textLat = (TextView) rootView.findViewById(R.id.lat);
        final TextView textLong = (TextView) rootView.findViewById(R.id.lon);
        final TextView textTotal = (TextView) rootView.findViewById(R.id.total);
        final TextView textEmpty = (TextView) rootView.findViewById(R.id.empty);
        final RequestQueue rq = Volley.newRequestQueue(getActivity().getApplicationContext());
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.names().get(0).equals("lat")){
                                lat = jsonObject.getString("lat");
                                lon = jsonObject.getString("lon");
                                no_slots = jsonObject.getString("total");
                                no_empty = jsonObject.getString("empty");

                                textLat.setText("Latitude:\n"+lat);
                                textLong.setText("Longitude:\n"+lon);
                                textTotal.setText("Total Slots:\n"+no_slots);
                                textEmpty.setText("Empty Slots:\n"+no_empty);
                                //Toast.makeText(getActivity().getApplicationContext(),jsonObject.getString("lat"),Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(getActivity().getApplicationContext(),"Error! Cannot fetch data", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                rq.add(request);


            }
        });
        // Inflate the layout for this fragment

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
