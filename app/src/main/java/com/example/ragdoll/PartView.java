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
    public Bitmap kangoo;
    public Bitmap uram_left;
    public Bitmap lram_left;
    public Bitmap lhand;
    public Bitmap uram_right;
    public Bitmap lram_right;
    public Bitmap rhand;
    public Bitmap l_thign;
    public Bitmap l_calf;
    public Bitmap r_thign;
    public Bitmap r_calf;

    public float scale = 1;




    public PartView (Context c){
        super(c);
        context = c;
        sub_views = new ArrayList<>();
        degree = initDegree;
        paint= new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        kangoo = BitmapFactory.decodeResource(getResources(),
                R.mipmap.face);
        uram_left = BitmapFactory.decodeResource(getResources(),
                R.mipmap.uarm_left);
        lram_left = BitmapFactory.decodeResource(getResources(),
                R.mipmap.larm_left);
        lhand = BitmapFactory.decodeResource(getResources(),
                R.mipmap.hand_left);
        uram_right = BitmapFactory.decodeResource(getResources(),
                R.mipmap.uarm_right);
        lram_right = BitmapFactory.decodeResource(getResources(),
                R.mipmap.larm_right);
        rhand = BitmapFactory.decodeResource(getResources(),
                R.mipmap.hand_right);
        l_thign = BitmapFactory.decodeResource(getResources(),
                R.mipmap.lthign);
        l_calf = BitmapFactory.decodeResource(getResources(),
                R.mipmap.lcalf);
        r_thign = BitmapFactory.decodeResource(getResources(),
                R.mipmap.rthigh);
        r_calf = BitmapFactory.decodeResource(getResources(),
                R.mipmap.rcalf);
    }

    public void update_mat(){

    }

    public void drawseg(Canvas canvas) {
        canvas.setMatrix(position_mat);
        //canvas.scale(1, scale);



        if(type == 2){
            canvas.drawBitmap(kangoo,null, Oval,paint);
        } else if (type == 3){
            canvas.drawBitmap(uram_left,null, Oval,paint);
        }else if (type == 4){
            canvas.drawBitmap(lram_left,null, Oval,paint);
        }else if (type == 5){
            canvas.drawBitmap(lhand,null, Oval,paint);
        }else if (type == 6){
            canvas.drawBitmap(l_thign,null, Oval,paint);
        }else if (type == 7){
            canvas.drawBitmap(l_calf,null, Oval,paint);
        } else if (type == -6){
            canvas.drawBitmap(r_thign,null, Oval,paint);
        }else if (type == -7){
            canvas.drawBitmap(r_calf,null, Oval,paint);
        }else if (type == -3){
            canvas.drawBitmap(uram_right,null, Oval,paint);
        }else if (type == -4){
            canvas.drawBitmap(lram_right,null, Oval,paint);
        }else if (type == -5){
            canvas.drawBitmap(rhand,null, Oval,paint);
        }
        else {
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

    public void handle_scale(float distance){
       // height += distance / 10;

    };



    @Override
    public void updateView() {

    }
}
