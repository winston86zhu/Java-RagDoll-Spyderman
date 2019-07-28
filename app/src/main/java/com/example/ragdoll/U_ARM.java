package com.example.ragdoll;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Pair;

public class U_ARM extends PartView implements IView {
    float rot_limit;
    public Matrix rot_mat;
    private Matrix savedMatrix = new Matrix(rot_mat);
    Pair<Float, Float> pivot;
    public boolean left_right;

    public U_ARM(Context c, PartView pv, boolean left_right) {
        super(c);
        parent = pv;
        pv.sub_views.add(this);
        width = 50;
        height = 240;
        //float left, float top, float right, float bottom
        Oval = new RectF(-40, 0, width,height);
        length = (float)Math.sqrt((width * width + height * height));
        rot_limit = 30;
        this.left_right = left_right;
        if(left_right) {
            initDegree = 30;
        } else {
            initDegree = -30;
        }
        degree = initDegree;
        if(left_right) {
            type = 3;
        } else {
            type = -3;
        }

        if(left_right) {
            x_pos = parent.x_pos + 31;
        } else {
            x_pos = parent.x_pos + parent.width - 31;
        }
        y_pos = parent.y_pos + 25;


        /*
         * ********************Set Up init Matrix*******************/
        rot_mat = new Matrix();
        pivot = getPivot();

        rot_mat.postRotate(initDegree, pivot.first, pivot.second);
        rot_mat.preTranslate(pivot.first, pivot.second);

        position_mat = rot_mat;
    }


    public Pair<Float,Float> getPivot(){
        Pair<Float, Float> pivotq;
        if(left_right) {
            pivotq = new Pair<>(parent.x_pos + 31, parent.y_pos + 25); // neck position
        } else {
            pivotq = new Pair<>(parent.x_pos + parent.width - 31, parent.y_pos + 25); // neck position
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
           // float degree;
        if(dy > 0 && dx < 0) {
            //need change
            rad = Math.atan(-dy / dx);
            degree = (float) Math.toDegrees(rad);
            degree += 90;
        } else if (dy > 0 && dx > 0){
            rad = Math.atan(dx / dy);
            degree = (float) Math.toDegrees(rad);
            degree += 180;
        } else if (dy < 0 && dx > 0){
            degree = (float) Math.toDegrees(rad);
            degree += 360;
        }
        else {
            degree = (float) Math.toDegrees(rad);

        }
        System.out.println(degree);
        rot_mat.postRotate(degree, pivot.first,pivot.second);
        //position_mat.preConcat(rot_mat);
        position_mat = rot_mat;
        sub_views.get(0).rotate(degree);

    }

    @Override
    public void rotate(float degree) {

    }
}
