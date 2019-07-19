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

public class TopBar implements IView {
    public Context context;
    public View backingView;
    public Model model;

    public TopBar(Context c, Model m) {
        model = m ;
        context = c;
        this.backingView = ((Activity) c).findViewById(R.id.act_bar);

        ImageButton clearButton = (ImageButton)this.backingView.findViewById(R.id.clear);
        ImageButton aboutbutton = (ImageButton)this.backingView.findViewById(R.id.info);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // model.reset_doll();
                Snackbar.make(backingView.getRootView(), "Reset RegDoll", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
               // model.notifyViews();
            }
        });

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
