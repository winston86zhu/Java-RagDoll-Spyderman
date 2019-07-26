package com.example.ragdoll;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Pair;

public class L_ARM extends PartView implements IView {

    float rot_limit;
    public Matrix rot_mat = new Matrix();
    private Matrix savedMatrix;
    Pair<Float, Float> pivot;
    public boolean left_right;

    public L_ARM(Context c, PartView pv, boolean left_right) {
        super(c);
        parent = pv;
        pv.sub_views.add(this);
        width = 40;
        height = 100;
        //float left, float top, float right, float bottom
        Oval = new RectF(-20, 0, width / 2,height);
        rot_limit = 30;
        this.left_right = left_right;
        initDegree = parent.initDegree;
        type = 4;

        if(left_right) {
            x_pos = parent.x_pos;
        } else {
            x_pos = parent.x_pos + parent.width;
        }

        /*
         * ********************Set Up init Matrix*******************/
//        savedMatrix = new Matrix();
//        position_mat = new Matrix(parent.position_mat);
//        Matrix l_trans = new Matrix();
//        l_trans.postTranslate(0, parent.height);
//        position_mat.preConcat(l_trans);

        rot_mat = new Matrix();
        pivot = getPivot();

        rot_mat.preTranslate(pivot.first, pivot.second);
        rot_mat.postRotate(initDegree, pivot.first, pivot.second);


        position_mat = rot_mat;


    }


    public Pair<Float,Float> getPivot(){
        Pair<Float, Float> pivotq;
        if(left_right) {
            pivotq = new Pair<>((parent.x_pos + 40) - ((float)(parent.height * Math.tan(Math.toRadians(parent.degree)))),
                    (parent.y_pos - 60) + parent.height);
        } else {
            pivotq = new Pair<>((parent.x_pos - 40)  - ((float)(parent.height * Math.tan(Math.toRadians(parent.degree)))),
                    (parent.y_pos - 60) + parent.height); // neck position
        }
        return pivotq;
    }

    @Override
    public void rotate(float eventx, float eventy) {
        //Refresh Pivot
//        float point[] = transformPoint(eventx, eventy);
//        float dx = 0 - point[0];
//        float dy = point[1] - 0;
//        double rad = Math.atan(dx/dy);
//        float degree = (float)Math.toDegrees(rad);
//
//
//        pivot = getPivot();
//        rot_mat = new Matrix();
//        rot_mat.postRotate(degree, 0 , 0);
//        position_mat.preConcat(rot_mat);

        pivot = getPivot();
        rot_mat = new Matrix();
        rot_mat.preTranslate(pivot.first, pivot.second);


        float dx = eventx - pivot.first;
        float dy = pivot.second - eventy;
        double rad;

        rad = Math.atan(dx / dy);
        float degree;
        if(dy > 0 && dx < 0) {
            //need change
            rad = Math.atan(-dy / dx);
            degree = (float) Math.toDegrees(rad);
            degree += 90;
        } else if (dy > 0 && dx > 0){
            rad = Math.atan(dx / dy);
            degree = (float) Math.toDegrees(rad);
            degree += 180;
        }
        else {
            degree = (float) Math.toDegrees(rad);

        }
        rot_mat.postRotate(degree, pivot.first,pivot.second);
        //position_mat.preConcat(rot_mat);
        position_mat = rot_mat;


    }
}
