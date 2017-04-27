package com.tanmoy.partnerpa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GariahatParkingLot extends AppCompatActivity {

    private static final String URL_SHOW_BOOK = "https://shayongupta.000webhostapp.com/partnerapp/show.php";
    private static final String URL_DEL = "https://shayongupta.000webhostapp.com/partnerapp/end.php";
    ListView lv;
    Byte id=3;
    Button Gariahat_btn;
    ArrayAdapter<String> adapter;
    List<String> arr;
    String[] language = {};
    private RequestQueue requestQueue;
    private StringRequest request;
    private Handler mHandler = new Handler();
    String billing;
    private RequestQueue requestQueue_del;
    private StringRequest request_del;
    public static final String EXTRA_MESSAGE = "MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gariahat_parking_lot);

        Gariahat_btn = (Button) findViewById(R.id.Gariahat_btn);

        lv = (ListView) findViewById(R.id.list_item);
        arr = new ArrayList<String>(Arrays.asList(language));
        adapter = new ArrayAdapter<String>(this, R.layout.list_row, arr);
        lv.setAdapter(adapter);
        requestQueue = Volley.newRequestQueue(this);



        Gariahat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add new Items to List
                request = new StringRequest(Request.Method.POST, URL_SHOW_BOOK, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                                int i=0;
                                while(!jsonObject.getString(String.valueOf(i)).equals("end")){

                                    arr.add(jsonObject.getString(String.valueOf(i)));
                                    //Toast.makeText(getApplicationContext(), jsonObject.getString(String.valueOf(i)), Toast.LENGTH_SHORT).show();
                                    i++;


                                }
                                adapter.notifyDataSetChanged();
                                // Toast.makeText(getApplicationContext(), jsonObject.getString("0"), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("id",String.valueOf(id));
                        //  hashMap.put("id", session.getuserId());
                        //  Toast.makeText(getApplicationContext(), "Successfull", Toast.LENGTH_SHORT).show();
                        //fab.setEnabled(false);


                        return hashMap;
                    }
                };

                requestQueue.add(request);

                adapter.notifyDataSetChanged();
                Gariahat_btn.setEnabled(false);

            }


        });


        requestQueue_del = Volley.newRequestQueue(this);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int position, long arg3) {
                final String car_no = arr.get(position).toString();

                //  Toast.makeText(getApplicationContext(), arr.get(position).toString(), Toast.LENGTH_SHORT).show();

                request_del = new StringRequest(Request.Method.POST, URL_DEL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                                //  adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                // Toast.makeText(getApplicationContext(), jsonObject.getString("bill"), Toast.LENGTH_SHORT).show();
                                billing = jsonObject.getString("bill");





                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("car_no",car_no);
                        hashMap.put("id",String.valueOf(id));
                        //  hashMap.put("id", session.getuserId());
                        //  Toast.makeText(getApplicationContext(), "Successfull", Toast.LENGTH_SHORT).show();
                        //fab.setEnabled(false);


                        return hashMap;
                    }
                };

                requestQueue_del.add(request_del);

                adapter.notifyDataSetChanged();




                removeItemFromList(position);

                return true;
            }
        });





    }

    protected void removeItemFromList(int position) {
        final int deletePosition = position;

        requestQueue_del = Volley.newRequestQueue(this);

        AlertDialog.Builder alert = new AlertDialog.Builder(
                GariahatParkingLot.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want to end this trip?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub

                mHandler.postDelayed(mUpdateTimeTask,5000);

                // main code on after clicking yes
                arr.remove(deletePosition);
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();

            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();

    }
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            // do what you need to do here after the delay
            Intent it = new Intent(GariahatParkingLot.this, BillParking.class).putExtra(EXTRA_MESSAGE,billing);
            startActivity(it);

        }
    };
}
