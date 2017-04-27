package com.tanmoy.partnerpa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class BillParking extends AppCompatActivity {

    String bill;
    TextView bill_text;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_parking);

        Intent intent = getIntent();
        bill = intent.getStringExtra("MESSAGE");
        bill_text = (TextView) findViewById(R.id.bill_text);

        bill_text.setText("Your Bill is \n ₹ "+bill);
        back = (Button)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(it);



            }
        });






    }
}
