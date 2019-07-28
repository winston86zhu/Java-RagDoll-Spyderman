package com.example.ragdoll;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

public class Torso extends PartView implements IView {
    float initX = 600;
    float initY = 600;

    public Torso(Context c) {
        super(c);
        x_pos = initX;
        y_pos = initY;
        width = 300;
        height = 350;
        type = 1;
        Oval = new RectF(0, 0, width, height);
        position_mat = new Matrix();
        position_mat.postTranslate(x_pos,y_pos);
        paint.setStyle(Paint.Style.FILL);
    }


    public void translate(float dx, float dy) {
        Matrix translate = new Matrix();
        translate.postTranslate(dx, dy);
        position_mat.postConcat(translate); //update self position
        for (PartView child : sub_views) {
            child.translate(translate); //update children position
            child.x_pos += dx;
            child.y_pos += dy;
        }
        x_pos += dx;
        y_pos += dy;
    }

    @Override
    public void drawseg(Canvas canvas) {
        canvas.setMatrix(position_mat);
        Bitmap body_image = BitmapFactory.decodeResource(getResources(),
                R.mipmap.body);

        canvas.drawBitmap(body_image,null, Oval,paint);
        //canvas.drawRoundRect(Oval, 160, 200, paint);
        //float: The x-radius of the oval used to round the corners
        // float: The y-radius of the oval used to round the corners
        for (PartView c : sub_views) {
            c.drawseg(canvas);
        }
    }

    @Override
    public void rotate(float eventx, float eventy) {

    }

    @Override
    public void rotate(float degree) {

    }


}
