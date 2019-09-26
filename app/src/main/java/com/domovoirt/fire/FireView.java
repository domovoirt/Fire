package com.domovoirt.fire;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import static java.lang.Math.max;

public class FireView extends View {
    final int DIMENTION = 100;
    final int HOT = 36;
    private Bitmap fireMap = Bitmap.createBitmap(DIMENTION,DIMENTION,Bitmap.Config.RGB_565);
    private int[] colors;
    private byte[][] field;


    private Paint paint = new Paint();
    int width;
    int height;

    public FireView(Context context, @Nullable AttributeSet attributeSet){
        super(context,attributeSet);
        //fireMap = Bitmap.createBitmap(DIMENTION,DIMENTION, Bitmap.Config.RGB_565);
        field = new byte[DIMENTION][DIMENTION];
        colors = new int[HOT];
        burnBottom();
        for (int i = 0; i < HOT; i++) {
            colors[i] = 0xff000000 | 0x00ffffff & ((i*0xff/HOT<<16)+0xffff);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
    }

    @Override
    protected void onDraw(Canvas canvas){
        drawFire();
        for (int x = 0; x < DIMENTION; x++) {
            for (int y = 0 ; y<DIMENTION;y++){
                fireMap.setPixel(x,y, field[x][y]);
            }
        }
        canvas.drawBitmap(fireMap,0,0,paint);
        invalidate();
    }

    private void burnBottom(){
        for (int d = 0; d < DIMENTION; d++) {
            field[d][DIMENTION-1] = (byte) colors[HOT-1];
        }
    }

    private void drawFire(){
        int newX,newY,newColor;
        for (int y = 0; y < DIMENTION-1; y++) {
            for (int x = 0; x < DIMENTION; x++) {
                newX = max(0,Math.min(DIMENTION-1, x+(int)(Math.random()*5-2)));
                newY = Math.min(DIMENTION-1, y+(int)(Math.random()*3));
                int color = field[newX][newY];
                newColor = max(0, color-(int)(Math.random()*2));
                field[x][y] = (byte)newColor;
            }
        }
    }

}
