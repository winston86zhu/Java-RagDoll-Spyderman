package com.example.ragdoll;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class TopBar extends View implements IView {
    public Context context;
    public View backingView;
    public Model model;
    public ImageButton clearButton;
    public ImageButton aboutbutton;
    public RagdollView rdv;


    public TopBar( Context c, Model m) {
        super(c);
        model = m ;
        context = c;
        this.backingView = ((Activity) c).findViewById(R.id.act_bar);
        rdv = (RagdollView)(findViewById(R.id.ragdollView));
         clearButton = (ImageButton)this.backingView.findViewById(R.id.clear);
         aboutbutton = (ImageButton)this.backingView.findViewById(R.id.info);


        aboutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(backingView.getRootView().getContext());
                dialog.setContentView(R.layout.pop);
                ImageButton iv = (ImageButton) dialog.findViewById(R.id.close_bt);
                TextView tv = (TextView) dialog.findViewById(R.id.txt_block);

                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setCancelable(true);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
                Snackbar.make(backingView.getRootView(), "Show About Info", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

            }
        });

    }


    @Override
    public void updateView() {

    }
}
