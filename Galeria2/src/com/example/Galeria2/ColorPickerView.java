package com.example.Galeria2;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Ala on 31/05/2015.
 */
public class ColorPickerView extends View {

    private Context context;
    private Paint gradientPaint;
    private Paint centerPaint;

    /* MEASURES */

    private static int CENTER_X =100;
    private static int CENTER_Y=100;
    private static int CENTER_RADIUS = 25;




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

        gradientPaint.setStrokeWidth(25);
        gradientPaint.setStyle(Paint.Style.STROKE);

        centerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerPaint.setColor(Color.WHITE);
        centerPaint.setStrokeWidth(5);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(CENTER_X*2, CENTER_Y*2);


    }

    @Override
    protected void onDraw(Canvas canvas) {

        float radius = CENTER_X - gradientPaint.getStrokeWidth()*0.5f;


        canvas.translate(CENTER_X, CENTER_X);
        canvas.drawOval(new RectF(-radius, -radius, radius, radius), gradientPaint);
        canvas.drawCircle(0, 0, CENTER_RADIUS, centerPaint);


        super.onDraw(canvas);
    }
}
