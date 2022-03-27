package com.example.petgps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public CardView vmap,vinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vmap = (CardView) findViewById(R.id.map);
        vinfo= (CardView) findViewById(R.id.info);

        vmap.setOnClickListener(this);
        vinfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch(view.getId()){
            case R.id.map :
                i = new Intent(this,MapsActivity.class);
                startActivity(i);
                break;
        }

        switch (view.getId()){
            case R.id.info :
                i = new Intent(this,InfoActivity.class);
                startActivity(i);
                break;
        }

    }

}