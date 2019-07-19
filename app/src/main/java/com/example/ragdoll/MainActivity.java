package com.example.ragdoll;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public View MainView;
    public TopBar tbar;
    public Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainView = findViewById(R.id.main_layout);
        model = new Model();
        tbar = new TopBar(this, model);
    }

}
