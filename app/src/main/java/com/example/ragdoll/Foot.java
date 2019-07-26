package com.example.ragdoll;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Pair;

public class Foot extends PartView implements IView {
    float rot_limit;
    public Matrix rot_mat;
    private Matrix savedMatrix = new Matrix();
    Pair<Float, Float> pivot;
    public boolean left_right;
    public float rotate_diff;


    public Foot(Context c, PartView pv, boolean left_right){
        super(c);
        parent = pv;
        pv.sub_views.add(this);
        width = 40;
        height = 120;

        Oval = new RectF(-20, 0, 20,height);
        rot_limit = 35;
        this.left_right = left_right;
        type = 8;
        if(left_right) {
            initDegree = 90 + parent.degree;
        } else {
            initDegree = 90 + parent.degree;
        }
        degree = initDegree;

        x_pos = (parent.x_pos) - ((float)(parent.length * Math.sin(Math.toRadians(parent.degree))));
        y_pos = parent.y_pos + ((float)(parent.length * Math.cos(Math.toRadians(parent.degree))));


        rot_mat = new Matrix();
        pivot = getPivot();

        rot_mat.preTranslate(pivot.first, pivot.second);
        rot_mat.postRotate(degree, pivot.first, pivot.second);
        position_mat = rot_mat;

        rot_mat = new Matrix();
        rot_mat.postRotate(90, 0,0);
        savedMatrix.postConcat(rot_mat);
    }

    public Pair<Float,Float> getPivot(){
        Pair<Float, Float> pivotq;
        if(!left_right) {
            pivotq = new Pair<>((parent.x_pos) - ((float) (parent.length * Math.sin(Math.toRadians(parent.degree)))) ,
                    parent.y_pos + ((float) (parent.length * Math.cos(Math.toRadians(parent.degree)))));
        } else {
            pivotq = new Pair<>((parent.x_pos) - ((float) (parent.length * Math.sin(Math.toRadians(parent.degree)))),
                    parent.y_pos + ((float) (parent.length * Math.cos(Math.toRadians(parent.degree)))));
        }

        return pivotq;
    }

    @Override
    public void drawseg(Canvas canvas) {
        canvas.setMatrix(position_mat);
        canvas.drawRoundRect(Oval, 160, 200, paint);
    }

    @Override
    public void rotate(float eventx, float eventy) {
        //Refresh Pivot
//        pivot = getPivot();
//        rot_mat = new Matrix();
//        rot_mat.preTranslate(pivot.first, pivot.second);
//
//
//        float dx = eventx - pivot.first;
//        float dy = pivot.second - eventy;
//        double rad;
//        float ori_deg = degree;
//        rad = Math.atan(dx / dy);
//        if(dy > 0 && dx < 0) {
//            //need change
//            rad = Math.atan(-dy / dx);
//            degree = (float) Math.toDegrees(rad);
//            degree += 90;
//        } else if (dy > 0 && dx > 0){
//            rad = Math.atan(dx / dy);
//            degree = (float) Math.toDegrees(rad);
//            degree += 180;
//        } else if (dy < 0 && dx > 0){
//            degree = (float) Math.toDegrees(rad);
//            degree += 360;
//        }
//        else {
//            degree = (float) Math.toDegrees(rad);
//
//        }
//        if(degree > initDegree + rot_limit){
//            degree = initDegree + rot_limit;
//        } else if (degree < initDegree - rot_limit){
//            degree = initDegree - rot_limit;
//        }
//
//        rot_mat.postRotate(degree, pivot.first,pivot.second);
//        position_mat = rot_mat;

        float point[] = transformPoint(eventx, eventy);
        float dx = 0 - point[0];
        float dy = point[1] - 0;
        double rad = Math.atan(dx/dy);
        float add_deg = (float)Math.toDegrees(rad);
        float temp_deg = degree + add_deg;
        System.out.println(add_deg);
        if((temp_deg - initDegree )>= rot_limit || (temp_deg - initDegree ) <= -rot_limit) {
            add_deg = 0;
        }


        degree += add_deg;
        rot_mat = new Matrix();
        rot_mat.postRotate(add_deg, 0,0);
        savedMatrix.postConcat(rot_mat);

        position_mat = new Matrix(parent.position_mat);
        Matrix local = new Matrix();
        local.postTranslate(0, parent.height);
        position_mat.preConcat(local);
        position_mat.preConcat(savedMatrix);

    }

    @Override
    public void rotate(float degree) {
        position_mat = new Matrix(parent.position_mat);
        Matrix local = new Matrix();
        local.postTranslate(0, parent.height);
        position_mat.preConcat(local);
        position_mat.preConcat(savedMatrix);
    }
}
