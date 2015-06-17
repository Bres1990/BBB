package com.example.Galeria2;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Ala on 31/05/2015.
 */
public class ColorPickerView extends View {

    private Context context;
    private Paint gradientPaint;
    private Paint centerPaint;

    /* MEASURES */

    private static int CENTER_X = 150;
    private static int CENTER_Y = 150;
    private static int CENTER_RADIUS = 45;
    private static final float PI = 3.1415926f;




    private final int [] shaderColors = new int[] {
            0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00,
            0xFFFFFF00, 0xFFFF0000
    };

    public ColorPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        /* Shader that draws circle around certain point [here: (0,0)] */
        Shader tmp = new SweepGradient(0, 0, shaderColors, null);

        gradientPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        /* Paint now takes colours straight from shader */
        gradientPaint.setShader(tmp);

        gradientPaint.setStrokeWidth(35);
        gradientPaint.setStyle(Paint.Style.STROKE);

        centerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerPaint.setColor(Color.WHITE);
        centerPaint.setStrokeWidth(15);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        /* resize view */
        setMeasuredDimension(CENTER_X * 2, CENTER_Y * 2);
    }


    private int ave(int src, int dst, float percentage) {
        return src + java.lang.Math.round(percentage * (dst - src));
    }

    public void setCenterPaint(int color){
        centerPaint.setColor(color);
        invalidate();
    }

    private int interpolate(float unit){
        if (unit <= 0) {
            return shaderColors[0];
        }
        if (unit >= 1) {
            return shaderColors[shaderColors.length - 1];
        }

        /* split length*unit into two parts - fraction and integer */
        float fractionalPart = unit * (shaderColors.length - 1);
        int integerPart = (int) fractionalPart;
        fractionalPart -= integerPart;

        /* mix two colors that are next to each other in appropriate percentage */
        /* fractional part - percentage */

        int c0 = shaderColors[integerPart];
        int c1 = shaderColors[integerPart+1];
        int a = ave(Color.alpha(c0), Color.alpha(c1), fractionalPart);
        int r = ave(Color.red(c0), Color.red(c1), fractionalPart);
        int g = ave(Color.green(c0), Color.green(c1), fractionalPart);
        int b = ave(Color.blue(c0), Color.blue(c1), fractionalPart);

        return Color.argb(a, r, g, b);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        float radius = CENTER_X - gradientPaint.getStrokeWidth()*0.5f;

        canvas.translate(CENTER_X, CENTER_X);
        canvas.drawOval(new RectF(-radius, -radius, radius, radius), gradientPaint);
        canvas.drawCircle(0, 0, CENTER_RADIUS, centerPaint);


        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float currentX = event.getX() - CENTER_X;
        float currentY = event.getY() - CENTER_Y;

        switch (event.getAction()){

            case MotionEvent.ACTION_MOVE:
                /* oblicz dlugosc luku - jednostke */
                float angle = (float)java.lang.Math.atan2(currentY, currentX);
                // need to turn angle [-PI ... PI] into unit [0....1]
                float unit = angle/(2*PI);
                if (unit < 0) {
                    unit += 1;
                }

                Log.v("ColorPicker", "unit: "+unit);
                centerPaint.setColor(interpolate(unit));
                invalidate();
                break;

        }

        return true;
    }


}