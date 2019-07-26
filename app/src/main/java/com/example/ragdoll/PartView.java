package com.example.ragdoll;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class PartView extends View implements IView {
    public Matrix position_mat;
    public Paint paint;
    public float x_pos;
    public float y_pos;
    public float width;
    public float height;
    public float length;
    RectF Oval;
    public float initDegree = 0;
    public float degree;
    public float curDegree = 0;
    public ArrayList<PartView> sub_views;
    public PartView parent = null;
    public Context context;
    public int type;


    public PartView (Context c){
        super(c);
        context = c;
        sub_views = new ArrayList<>();
        degree = initDegree;
        paint= new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
    }

    public void drawseg(Canvas canvas) {
        canvas.setMatrix(position_mat);
        Bitmap kangoo = BitmapFactory.decodeResource(getResources(),
                R.mipmap.dora_head);
        if(type == 2){
            canvas.drawBitmap(kangoo,null, Oval,paint);
        } else {
            canvas.drawOval(Oval, paint);
        }
        for (PartView sub : sub_views) {
            sub.drawseg(canvas);
        }
    }

    public void add_children(PartView p){
        sub_views.add(p);
        p.parent = this;
    }

    public void translate(Matrix m) {
        position_mat.postConcat(m);
        for (PartView c : sub_views) {
            c.translate(m);
        }
    }

    public void reset_part(){
        position_mat = new Matrix();
        position_mat.postRotate(initDegree);

        for(PartView pv : sub_views){
            pv.reset_part();
        }
    }


    /*Modify*/
    public float[] transformPoint(float x, float y) {
        float point[] = {x, y};
        Matrix inverse = new Matrix(position_mat);
        inverse.invert(inverse);
        inverse.mapPoints(point);
        return point;
    }

    public boolean pointInside(float eventX, float eventY) {
        float point[] = transformPoint(eventX, eventY);
        return Oval.contains(point[0], point[1]);
    }


    /*Modify*/
    public abstract void rotate(float eventx, float eventy);

    public abstract void rotate(float degree);



    @Override
    public void updateView() {

    }
}
