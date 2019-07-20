package com.example.ragdoll;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

public class Torso extends PartView implements IView {
    float initX = 435;
    float initY = 330;

    public Torso(Context c) {
        super(c);
        x_pos = initX;
        y_pos = initY;
        width = 200;
        height = 350;
        type = 1;
        Oval = new RectF(100, 100, width, height);
        position_mat = new Matrix();
        position_mat.postTranslate(x_pos,y_pos);
    }


    public void translate(float dx, float dy) {
        Matrix translate = new Matrix();
        translate.postTranslate(dx, dy);
        position_mat.postConcat(translate); //update self position
        for (PartView child : sub_views) {
            child.translate(translate); //update children position
        }
        x_pos += dx;
        y_pos += dy;
    }

    @Override
    public void drawseg(Canvas canvas) {
        canvas.setMatrix(position_mat);
        canvas.drawRoundRect(Oval, 70, 70, paint);
        //float: The x-radius of the oval used to round the corners
        // float: The y-radius of the oval used to round the corners

        for (PartView c : sub_views) {
            c.drawseg(canvas);
        }
    }






}