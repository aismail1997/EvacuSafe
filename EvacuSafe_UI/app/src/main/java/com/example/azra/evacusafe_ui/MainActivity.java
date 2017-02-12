package com.example.azra.evacusafe_ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;
    Button admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button = (Button)findViewById(R.id.button);
        admin = (Button)findViewById(R.id.admin);
        button.setOnClickListener(this);
        admin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.admin) {
            Intent intent_admin = new Intent(this, LoginActivityadmin.class);
            startActivity(intent_admin);
        }
    }
}