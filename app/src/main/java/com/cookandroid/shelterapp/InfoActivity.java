package com.cookandroid.shelterapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class InfoActivity extends AppCompatActivity {
    //  Toolbar toolbar;
    ImageButton storm,fire,earth,cold;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info1);

        storm=(ImageButton)findViewById(R.id.storm);
        fire=(ImageButton)findViewById(R.id.fire);
        earth=(ImageButton)findViewById(R.id.earth);
        cold=(ImageButton)findViewById(R.id.cold);

        storm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),StromActivity.class);
                startActivity(intent); // 0이상만 쓰면 된다.
            }
        });
        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),FireActivity.class);
                startActivity(intent); // 0이상만 쓰면 된다.
            }
        });
        earth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),EarthActivity.class);
                startActivity(intent); // 0이상만 쓰면 된다.
            }
        });
        cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ColdActivity.class);
                startActivity(intent); // 0이상만 쓰면 된다.
            }
        });



    }
}