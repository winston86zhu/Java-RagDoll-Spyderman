package com.example.ragdoll;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    public View MainView;
    public TopBar tbar;
    public Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainView = findViewById(R.id.main_layout);
        model = new Model(this);
        tbar = new TopBar(this, model);
        tbar.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // model.reset_doll();
                RagdollView rdv = (RagdollView)findViewById(R.id.ragdollView);
                rdv.reset();
                Snackbar.make(MainView, "Reset RegDoll", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });
    }

}
