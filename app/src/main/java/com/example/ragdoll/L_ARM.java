package com.example.ragdoll;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Pair;

public class L_ARM extends PartView implements IView {

    float rot_limit;
    public Matrix rot_mat;
    private Matrix savedMatrix = new Matrix();
    Pair<Float, Float> pivot;
    public boolean left_right;
    public float rotate_diff;

    public L_ARM(Context c, PartView pv, boolean left_right) {
        super(c);
        parent = pv;
        pv.sub_views.add(this);
        width = 60;
        height = 150;
        //float left, float top, float right, float bottom
        Oval = new RectF(-30, 0, 30,height);
        rot_limit = 135;
        this.left_right = left_right;
        initDegree = parent.initDegree;
        if(left_right) {
            type = 4;
        } else {
            type = -4;
        }
        degree = initDegree;
        rotate_diff = degree - parent.degree;
        length = (float)Math.sqrt((width * width + height * height));

        x_pos = (parent.x_pos ) - ((float)(parent.length * Math.sin(Math.toRadians(parent.degree)))) ;
        y_pos = parent.y_pos + ((float)(parent.length * Math.cos(Math.toRadians(parent.degree)))) ;

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
        rot_mat.postRotate(degree, pivot.first, pivot.second);
        position_mat = rot_mat;
    }


    public Pair<Float,Float> getPivot(){
        Pair<Float, Float> pivotq;
            pivotq = new Pair<>((parent.x_pos ) - ((float)(parent.length * Math.sin(Math.toRadians(parent.degree))))
                    ,parent.y_pos + ((float)(parent.length * Math.cos(Math.toRadians(parent.degree)))) );
        return pivotq;
    }

    @Override
    public void rotate(float eventx, float eventy) {
        //Refresh Pivot
        float point[] = transformPoint(eventx, eventy);
        float dx = 0 - point[0];
        float dy = point[1] - 0;
        double rad = Math.atan(dx/dy);
        float add_deg = (float)Math.toDegrees(rad);
        float temp_deg = degree + add_deg;
        System.out.println(temp_deg);
        if((temp_deg - initDegree )>= rot_limit || (temp_deg - initDegree ) <= -rot_limit) {
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
    public void rotate(float deg) {
        position_mat = new Matrix(parent.position_mat);
        Matrix local = new Matrix();
        local.postTranslate(0, parent.height);
        position_mat.preConcat(local);
        position_mat.preConcat(savedMatrix);
        sub_views.get(0).rotate(degree);
    }
}
