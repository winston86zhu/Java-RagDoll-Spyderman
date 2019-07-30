package com.example.ragdoll;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.core.view.MotionEventCompat;

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
    public boolean multi;
    private SparseArray<PointF> mActivePointers;

    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;

    public RagdollView(Context context, AttributeSet att) {
        super(context, att);
        init_all(context);
        mActivePointers = new SparseArray<PointF>();
        add_all();
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
        left_calf.rotate(0);
        right_calf.rotate(0);
        left_foot.rotate(0);
        right_foot.rotate(0);
        right_hand.rotate(0);
        left_hand.rotate(0);
        this.context = context;
    }

    public void add_all(){
        view_set.add(left_arm);
        view_set.add(right_arm);
        view_set.add(left_arm_low);
        view_set.add(right_arm_low);
        view_set.add(left_hand);
        view_set.add(right_hand);
        view_set.add(left_foot);
        view_set.add(right_foot);
        view_set.add(left_calf);
        view_set.add(right_calf);
        view_set.add(head);
        view_set.add(left_leg_up);
        view_set.add(right_leg_up);
        view_set.add(torso);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
       // mScaleDetector.onTouchEvent(event);

        //Scale responds to type 6 and 7
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        int pointer_num = event.getPointerCount();
        multi = false;
        if(pointer_num > 1){
            multi = true;
        }

        // get pointer index from the event object
        int pointerIndex = event.getActionIndex();
        // get pointer ID
        int pointerId = event.getPointerId(pointerIndex);
        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();



        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN:
                doll_x = eventX;
                doll_y = eventY;
                PointF f = new PointF();
                f.x = event.getX(pointerIndex);
                f.y = event.getY(pointerIndex);
                mActivePointers.put(pointerId, f);
                for (PartView s : view_set) {
                    if (s.pointInside(eventX, eventY)) {
                        selected = s;
                        return true;
                    }
                }
                return false;
            case MotionEvent.ACTION_POINTER_DOWN: {
                // We have a new pointer. Lets add it to the list of pointers
                PointF f1 = new PointF();
                f1.x = event.getX(pointerIndex);
                f1.y = event.getY(pointerIndex);
                mActivePointers.put(pointerId, f1);
                break;
            }
            case MotionEvent.ACTION_MOVE:
                if(!multi) {
                    if (selected.type == 1) {
                        float dx = eventX - doll_x;
                        float dy = eventY - doll_y;
                        ((Torso) selected).translate(dx, dy);
                        doll_x = eventX;
                        doll_y = eventY;
                    } else {
                        selected.rotate(eventX, eventY);
                    }
                    break;
                } else {
                    float prev_deg = selected.degree;
                    PointF point1 = mActivePointers.get(event.getPointerId(0));
                    if (point1 != null) {
                        point1.x = event.getX(0);
                        point1.y = event.getY(0);
                    }
                    PointF point2 = mActivePointers.get(event.getPointerId(1));
                    if (point2 != null) {
                        point2.x = event.getX(1);
                        point2.y = event.getY(1);
                    }
                    float distance;
                    float slope = (float)Math.toDegrees(Math.atan(Math.abs((point2.x - point1.x)/ (point2.y - point1.y))));
                    if(Math.abs(Math.abs(slope - Math.abs(selected.degree)) % 360) >20){
                        break;
                    }

                    if(point2.y >= point1.y) {
                        distance = (float) Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2));
                    } else {
                        distance = -(float) Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2));
                    }
                    System.out.println("The system has distance" + distance);
                    if(selected.type == 6 || selected.type == -6){
                        if(selected.height + distance / 20 >= 450){
                            selected.height = 450;
                        }else if(selected.height + distance / 20 <= 150){
                            selected.height = 150;
                        } else {
                            selected.height += distance / 20;
                        }
                        selected.update_mat();
                        if(selected.sub_views.get(0).height + distance / 20 >= 300){
                            selected.sub_views.get(0).height = 300;
                        }else if(selected.sub_views.get(0).height + distance / 20 <= 75){
                            selected.sub_views.get(0).height = 75;
                        } else {
                            selected.sub_views.get(0).height += distance / 20;
                        }
                        selected.sub_views.get(0).update_mat();
                        selected.degree = prev_deg;
                        selected.sub_views.get(0).degree = prev_deg;
                        selected.sub_views.get(0).sub_views.get(0).update_mat();
                    } else if (selected.type == 7 || selected.type == -7){
                        if(selected.height + distance / 20 >= 300){
                            selected.height = 300;
                        }else if(selected.height + distance / 20 <= 75){
                            selected.height = 75;
                        } else {
                            selected.height += distance / 20;
                        }
                        selected.height += distance / 20;
                        selected.update_mat();
                        selected.sub_views.get(0).update_mat();
                        selected.degree = prev_deg;
                        selected.sub_views.get(0).sub_views.get(0).update_mat();
                    }
                }
            case MotionEvent.ACTION_UP:
                doll_x = eventX;
                doll_y = eventY;
                break;
            case MotionEvent.ACTION_CANCEL: {
                mActivePointers.remove(pointerId);
                break;
            }
            default:
                return false;
        }
        invalidate();
        return true;
    }




    @Override
    public void onDraw(Canvas c) {
       // c.scale(mScaleFactor, mScaleFactor);
        torso.drawseg(c);
        //right_arm.drawseg(c);
        c.save();
    }
    @Override
    public void updateView() {

    }
}
