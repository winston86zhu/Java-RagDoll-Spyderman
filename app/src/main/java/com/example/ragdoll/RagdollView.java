package com.example.ragdoll;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
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
    public Hand left_hand;
    public Hand right_hand;
    public Upper_Leg left_leg_up;
    public Upper_Leg right_leg_up;
    public Calf left_calf;
    public Calf right_calf;
    public Foot left_foot;
    public Foot right_foot;
    public float doll_x;
    public float doll_y;
    public PartView selected;
    public Context context;

    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;

    public RagdollView(Context context, AttributeSet att) {
        super(context, att);
        init_all(context);

        add_all();
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    public void reset(){
        view_set.clear();
        init_all(context);
        add_all();
        invalidate();
    }

    public void init_all(Context context){
        view_set = new ArrayList<>();
        torso = new Torso(context);
        head = new HeadView(context,torso);
        left_arm = new U_ARM(context, torso, true);
        right_arm = new U_ARM(context, torso, false);
        left_arm_low = new L_ARM(context, left_arm, true);
        right_arm_low = new L_ARM(context, right_arm, false);
        left_hand = new Hand(context, left_arm_low, true);
        right_hand = new Hand(context, right_arm_low, false);
        left_leg_up = new Upper_Leg(context, torso, true);
        right_leg_up = new Upper_Leg(context,torso, false);
        left_calf = new Calf (context,left_leg_up, true);
        right_calf = new Calf(context,right_leg_up, false);
        left_foot = new Foot(context,left_calf, true);
        right_foot = new Foot(context,right_calf, false);
        this.context = context;
    }

    public void add_all(){
        view_set.add(left_foot);
        view_set.add(right_foot);
        view_set.add(left_calf);
        view_set.add(right_calf);
        view_set.add(left_hand);
        view_set.add(right_hand);
        view_set.add(left_arm_low);
        view_set.add(right_arm_low);
        view_set.add(head);
        view_set.add(left_arm);
        view_set.add(right_arm);
        view_set.add(left_leg_up);
        view_set.add(right_leg_up);
        view_set.add(torso);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        mScaleDetector.onTouchEvent(event);

        //Scale responds to type 6 and 7

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
                    selected.rotate(eventX,eventY);
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

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
           // mScaleFactor = selected.scale;
            System.out.println("The sclae factr is" + detector.getScaleFactor());
            mScaleFactor = mScaleFactor * detector.getScaleFactor();
            System.out.println("Mscale factr is" + mScaleFactor);

            if(selected == null){
                return false;
            }
            if(selected.type == -6 || selected.type == 6){
                selected.scale = mScaleFactor;

                selected.update_mat();
                selected.sub_views.get(0).update_mat();

               // selected.sub_views.get(0).scale = mScaleFactor;
                //selected.sub_views.get(0).sub_views.get(0).scale = 1 / mScaleFactor;
                //selected.sub_views.get(0).update_mat();

                invalidate();
            } else if (selected.type == 7 || selected.type == -7){
                selected.scale = mScaleFactor;
                selected.sub_views.get(0).scale = mScaleFactor;
                invalidate();
            }
            return true;

        }
        @Override
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector){
            if(selected == null){
                return false;
            }
            if(selected.type == 6 || selected.type == -6
                    || selected.type == 7 || selected.type == -7){
                return true;
            }
            return false;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            super.onScaleEnd(detector);
            mScaleFactor = 1.f;
            //mScaleFactor = selected.scale;
            //selected.update_mat();
        }
    }

    @Override
    public void onDraw(Canvas c) {
        c.scale(mScaleFactor, mScaleFactor);
        torso.drawseg(c);
        c.save();
    }
    @Override
    public void updateView() {

    }
}
