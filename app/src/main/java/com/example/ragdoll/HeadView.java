package com.example.ragdoll;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Pair;
import android.view.MotionEvent;

public class HeadView extends PartView implements IView {
    float rot_limit;
    public Matrix rot_mat = new Matrix();
    private Matrix savedMatrix = new Matrix(rot_mat);
    Pair<Float, Float> pivot;




    public HeadView(Context c, PartView pv) {
        super(c);
        parent = pv;
        pv.sub_views.add(this);
        width = 150;
        height = 160;
        //float left, float top, float right, float bottom
        Oval = new RectF(0, 0, width, height+10);
        rot_limit = 20;
        initDegree = 0;


        /*
         * ********************Set Up init Matrix*******************/
        rot_mat = new Matrix(parent.position_mat);
        // Distance to translate
        rot_mat.preTranslate((parent.width - width) / 2, -10-height);
        position_mat = rot_mat;

        pivot = new Pair<>(parent.x_pos + parent.width / 2, parent.y_pos);




    }





    @Override
    public void rotate(float eventx, float eventy) {
        float dx = eventx - pivot.first;
        float dy = eventy - pivot.second;
        double rad = getAngle(dx,dy);
        float degree = (float)Math.toDegrees(rad);

        savedMatrix.postRotate(degree, pivot.first,pivot.second);
        position_mat = savedMatrix;

    }

    private double getAngle(double x, double y) {

        switch (getQuadrant(x, y)) {
            case 1:
                return Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 2:
                return 180 - Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 3:
                return 180 + (-1 * Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
            case 4:
                return 360 + Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            default:
                return 0;
        }
    }

    /**
     * @return The selected quadrant.
     */
    private static int getQuadrant(double x, double y) {
        if (x >= 0) {
            return y >= 0 ? 1 : 4;
        } else {
            return y >= 0 ? 2 : 3;
        }
    }
}
