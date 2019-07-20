package com.example.ragdoll;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;

public class Model implements Serializable{
    public transient ArrayList<IView> observers;
    public transient Context context;

    Model model;
    //ArrayList<PartView> all_parts;
    RagdollView rdv;


    public Model(Context c){
        //all_parts = new ArrayList<>();

        //rdv = new RagdollView(c);
        //all_parts.add(rdv);

    }

    public void reset_model(){

    }

}
