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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
public class RubyFragment extends Fragment implements OnMapReadyCallback{
    MapView mMapView;
    private GoogleMap googleMap;

    private static final String URL = "https://shayongupta.000webhostapp.com/partnerapp/ruby.php";
    private StringRequest request;
    String lat = "";
    String lon = "";
    String no_slots = "";
    String no_empty = "";
    Button button;
    double latitude;
    double longitude;
    Button ruby_btn;



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
        ruby_btn = (Button) rootView.findViewById(R.id.ruby_btn);

        ruby_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), RubyParkingLot.class);
                getActivity().startActivity(myIntent);




            }
        });


        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);


        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }



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

                                RubyFragment obj=new RubyFragment();
                                obj.setVariables(Double.parseDouble(lat), Double.parseDouble(lon));
                                mMapView.getMapAsync(obj);

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


    public void setVariables(double lat, double lon){
        latitude=lat;
        longitude=lon;
    }

    @Override
    public void onMapReady(GoogleMap map) {
//DO WHATEVER YOU WANT WITH GOOGLEMAP
        LatLng location = new LatLng(latitude,longitude);
        map.addMarker(new MarkerOptions().position(location).title("Marker in Parking Lot"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location,17));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
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
