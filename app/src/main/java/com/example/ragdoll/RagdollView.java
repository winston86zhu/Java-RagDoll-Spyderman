package com.example.ragdoll;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import static java.lang.Math.min;

public class RagdollView extends View implements IView {
    public ArrayList<PartView> view_set;
    public Torso torso;
    public HeadView head;
    public U_ARM left_arm;
    public U_ARM right_arm;
    public L_ARM left_arm_low;
    public L_ARM right_arm_low;
    public float doll_x;
    public float doll_y;
    public PartView selected;

    public RagdollView(Context context, AttributeSet att) {
        super(context, att);
        view_set = new ArrayList<>();
        torso = new Torso(context);
        head = new HeadView(context,torso);
        left_arm = new U_ARM(context, torso, true);
        right_arm = new U_ARM(context, torso, false);
        left_arm_low = new L_ARM(context, left_arm, true);
        right_arm_low = new L_ARM(context, right_arm, false);



        view_set.add(head);
        view_set.add(left_arm);
        view_set.add(right_arm);
        view_set.add(torso);
        view_set.add(left_arm_low);
        view_set.add(right_arm_low);

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                doll_x = eventX;
                doll_y = eventY;
                for (PartView s : view_set) {
                    if (s.pointInside(eventX, eventY)) {
                        selected = s;
                        return true;
                    }
                }
                //return pointInside(eventX,eventY);
                return false;
            case MotionEvent.ACTION_MOVE:
                if(selected.type == 1) {
                    float dx = eventX - doll_x;

                    float dy = eventY - doll_y;

                    ((Torso)selected).translate(dx, dy);
                    doll_x = eventX;
                    doll_y = eventY;
                    invalidate();
                } else {
                    //Rotate
                    if(selected.pointInside(eventX,eventY)){
                        selected.rotate(eventX,eventY);

                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                doll_x = eventX;
                doll_y = eventY;
                break;
            default:
                return false;
        }
        return true;

    }



    @Override
    public void onDraw(Canvas c) {
        torso.drawseg(c);
    }

    @Override
    public void updateView() {

    }
}
