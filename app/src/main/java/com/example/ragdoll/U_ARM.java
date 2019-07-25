package com.example.ragdoll;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Pair;

public class U_ARM extends PartView implements IView {
    float rot_limit;
    public Matrix rot_mat = new Matrix();
    private Matrix savedMatrix = new Matrix(rot_mat);
    Pair<Float, Float> pivot;

    public U_ARM(Context c, PartView pv, boolean left_right) {
        super(c);
        parent = pv;
        pv.sub_views.add(this);
        width = 40;
        height = 160;
        //float left, float top, float right, float bottom
        Oval = new RectF(0, 0, width,height);
        rot_limit = 30;
        if(left_right) {
            initDegree = 30;
        } else {
            initDegree = -30;
        }
        type = 3;

        /*
         * ********************Set Up init Matrix*******************/
        rot_mat = new Matrix();
        if(left_right == true) {
            pivot = new Pair<>(parent.x_pos, parent.y_pos + 20); // neck position
        } else {
            pivot = new Pair<>(parent.x_pos + parent.width, parent.y_pos + 20); // neck position
        }

    }

    @Override
    public void rotate(float eventx, float eventy) {

    }
}
