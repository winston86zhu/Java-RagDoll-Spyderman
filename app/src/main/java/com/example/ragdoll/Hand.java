package com.example.ragdoll;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Pair;

public class Hand extends PartView implements IView {
    float rot_limit;
    public Matrix rot_mat;
    private Matrix savedMatrix = new Matrix();
    Pair<Float, Float> pivot;
    public boolean left_right;
    public float rotate_diff;

    public Hand(Context c, PartView pv, boolean left_right) {
        super(c);
        parent = pv;
        pv.sub_views.add(this);
        width = 80;
        height = 80;
        //float left, float top, float right, float bottom
        Oval = new RectF(-40, 0, width / 2,height);
        rot_limit = 135;
        this.left_right = left_right;
        initDegree = parent.initDegree;
        type = 4;
        degree = initDegree;
        rotate_diff = degree - parent.degree;

        if(left_right) {
            x_pos = parent.x_pos;
        } else {
            x_pos = parent.x_pos + parent.width;
        }
    }

    @Override
    public void rotate(float eventx, float eventy) {

    }

    @Override
    public void rotate(float degree) {

    }
}
