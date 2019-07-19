package com.example.ragdoll;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;

public class Model extends View implements Serializable{
    public transient ArrayList<IView> observers;
    public transient Context context;

    Model model;
    ArrayList<PartView> all_parts;
    Torso torso;

    public Model(Context c){
        super(c);
        all_parts = new ArrayList<>();
        torso = new Torso(c);
        all_parts.add(torso);

    }

    @Override
    public void onDraw(Canvas c) {
        torso.drawseg(c);
    }

}
