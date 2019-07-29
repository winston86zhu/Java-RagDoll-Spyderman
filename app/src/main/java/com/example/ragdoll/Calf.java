package com.example.ragdoll;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Pair;

public class Calf extends PartView implements IView {
    float rot_limit;
    public Matrix rot_mat;
    private Matrix savedMatrix = new Matrix();
    Pair<Float, Float> pivot;
    public boolean left_right;
    public float rotate_diff;


    public Calf(Context c,PartView pv, boolean left_right) {
        super(c);
        parent = pv;
        pv.sub_views.add(this);
        width = 60;
        height = 150;
        //float left, float top, float right, float bottom
        Oval = new RectF(-30, 0, 30,height);
        rot_limit = 90;
        this.left_right = left_right;
        initDegree = parent.initDegree;
        if(left_right) {
            type = 7;
        }else {
            type = -7;
        }
        degree = initDegree;
        length = (float)Math.sqrt((width * width + height * height));

        update_mat();
    }

    @Override
    public void update_mat(){
//        height *= scale;
//        length = (float)Math.sqrt((width * width + height * height));
        x_pos = (parent.x_pos ) - ((float)(parent.length * Math.sin(Math.toRadians(parent.degree))));
        y_pos = parent.y_pos + ((float)(parent.length * Math.cos(Math.toRadians(parent.degree))));
        rot_mat = new Matrix();
        pivot = getPivot();

        rot_mat.preTranslate(pivot.first, pivot.second);
        rot_mat.postRotate(degree, pivot.first, pivot.second);
        //rot_mat.postScale(0,scale);
        position_mat = rot_mat;
        invalidate();

    }

    public Pair<Float,Float> getPivot(){
        Pair<Float, Float> pivotq;
        pivotq = new Pair<>((parent.x_pos ) - ((float)(parent.length * Math.sin(Math.toRadians(parent.degree))))
                ,parent.y_pos + ((float)(parent.length * Math.cos(Math.toRadians(parent.degree)))));
        return pivotq;
    }

    @Override
    public void rotate(float eventx, float eventy) {
        float point[] = transformPoint(eventx, eventy);
        float dx = 0 - point[0];
        float dy = point[1] - 0;
        double rad = Math.atan(dx/dy);
        float add_deg = (float)Math.toDegrees(rad);
        float temp_deg = degree + add_deg;
        //System.out.println(temp_deg);
        if((temp_deg - initDegree )>= rot_limit ||
                (temp_deg - initDegree ) <= -rot_limit) {
            add_deg = 0;
        }


        degree += add_deg;
        rotate_diff = degree - parent.degree;
        rot_mat = new Matrix();
        rot_mat.postRotate(add_deg, 0,0);
        savedMatrix.postConcat(rot_mat);

        position_mat = new Matrix(parent.position_mat);
        Matrix local = new Matrix();
        local.postTranslate(0, parent.height);
        position_mat.preConcat(local);
        position_mat.preConcat(savedMatrix);
        sub_views.get(0).rotate(degree);

    }

    @Override
    public void rotate(float degree) {
        position_mat = new Matrix(parent.position_mat);
        Matrix local = new Matrix();
        local.postTranslate(0, parent.height);
        position_mat.preConcat(local);
        position_mat.preConcat(savedMatrix);
        sub_views.get(0).rotate(degree);
    }
}
