package com.example.ragdoll;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.MotionEventCompat;

import java.util.ArrayList;

import static android.view.MotionEvent.INVALID_POINTER_ID;

public class RagdollView extends View implements IView {
    public ArrayList<PartView> view_set;
    Torso torso;
    public float doll_x;
    public float doll_y;
    public PartView selected;
    private int mActivePointerId = INVALID_POINTER_ID;

    public RagdollView(Context context, AttributeSet att) {
        super(context, att);
        view_set = new ArrayList<>();
        torso = new Torso(context);
        view_set.add(torso);
    }



    /*Referred from Android Guideline
    * https://developer.android.com/training/gestures/scale
    * */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (MotionEventCompat.getActionMasked(event)) {
            case MotionEvent.ACTION_DOWN: {
                doll_x = eventX;
                doll_y = eventY;
                final int pointerIndex = MotionEventCompat.getActionIndex(event);
                for (PartView s : view_set) {
                    if (s.pointInside(eventX, eventY)) {
                        selected = s;
                        return true;
                    }
                }

                // Save the ID of this pointer (for dragging)
                mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                //return pointInside(eventX,eventY);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex =
                        MotionEventCompat.findPointerIndex(event, mActivePointerId);
                if (selected.type == 1) {
                    float dx = eventX - doll_x;
                    float dy = eventY - doll_y;
                    ((Torso) selected).translate(dx, dy);

                    invalidate();
                    doll_x = eventX;
                    doll_y = eventY;
                } else {
                    //Rotate
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:
                final int pointerIndex = MotionEventCompat.getActionIndex(event);
                final int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);

                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    doll_x = MotionEventCompat.getX(event, newPointerIndex);
                    doll_y = MotionEventCompat.getY(event, newPointerIndex);
                    mActivePointerId = MotionEventCompat.getPointerId(event, newPointerIndex);
                }
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
