package com.example.ragdoll;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Pair;

public class Upper_Leg extends PartView implements IView {
    float rot_limit;
    public Matrix rot_mat;
    private Matrix savedMatrix = new Matrix(rot_mat);
    Pair<Float, Float> pivot;
    public boolean left_right;

    /*
    Version Two Area*****************************
     */
    public Matrix initialMatrix() {
        Matrix m = new Matrix();
        float pivot[] = get_Pivot();
        m.postTranslate(pivot[0], pivot[1]);
        return m;
    }

    public float[] get_Pivot() {
        float[] pivot = new float[2];
        if (left_right) {
            pivot[0] = parent.x_pos+parent.width/4;
        } else {
            pivot[0] = parent.x_pos+3*parent.width/4;
        }
        pivot[1] = parent.y_pos+parent.height;
        return pivot;
    }

    public Upper_Leg(Context c, PartView pv, boolean left_right) {
        super(c);
        parent = pv;
        pv.sub_views.add(this);
        width = 40;
        height = 200;
        //float left, float top, float right, float bottom
        Oval = new RectF(-40, 0, width,height);
        length = (float)Math.sqrt((width * width + height * height));
        rot_limit = 90;
        this.left_right = left_right;
        if(left_right) {
            initDegree = 10;
        } else {
            initDegree = -10;
        }
        degree = initDegree;
        if(left_right) {
            type = 6;
        }else{
            type = -6;
        }

        if(left_right) {
            x_pos = parent.x_pos + 90;
        } else {
            x_pos = parent.x_pos + parent.width - 90;
        }
        y_pos = parent.y_pos + parent.height - 60;


        /*
         * ********************Set Up init Matrix*******************/
        update_mat();

    }

    @Override
    public void update_mat(){
        rot_mat = new Matrix();
        //height *= scale;
        //length = (float)Math.sqrt((width * width + height * height));
        length *= scale;
        pivot = getPivot();

        rot_mat.postRotate(initDegree, pivot.first, pivot.second);
        rot_mat.preTranslate(pivot.first, pivot.second);
        position_mat = rot_mat;
    }

    public Pair<Float,Float> getPivot(){
        Pair<Float, Float> pivotq;
        if(left_right) {
            pivotq = new Pair<>(parent.x_pos + 90, parent.y_pos + parent.height - 60); // neck position
        } else {
            pivotq = new Pair<>(parent.x_pos + parent.width - 90, parent.y_pos + parent.height - 60); // neck position
        }
        return pivotq;
    }

    @Override
    public void rotate(float eventx, float eventy) {
        pivot = getPivot();
        rot_mat = new Matrix();
        rot_mat.preTranslate(pivot.first, pivot.second);


        float dx = eventx - pivot.first;
        float dy = pivot.second - eventy;
        double rad;

        rad = Math.atan(dx / dy);
        // float degree;
        if (dy < 0 && dx > 0){
            degree = (float) Math.toDegrees(rad);
            degree += 360;
        }
        else if(dy < 0 && dx < 0){
            degree = (float) Math.toDegrees(rad);

        }
        //System.out.println(degree);
        rot_mat.postRotate(degree, pivot.first,pivot.second);
        //position_mat.preConcat(rot_mat);
        position_mat = rot_mat;
        sub_views.get(0).rotate(degree);

    }

    @Override
    public void rotate(float degree) {

    }
}
