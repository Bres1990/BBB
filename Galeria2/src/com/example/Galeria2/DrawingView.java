package com.example.Galeria2;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Custom view that can be drawn on. Uses touch detection.
 * @author Ala
 * @since 09-05-2015
 */
public class DrawingView extends View {

    /**
     * Current paint used by {@link #onDraw(Canvas)}.
     * <br> Contains information about colour and shape of the brush.
     */
    private Paint myPaint;

    /**
     * Context of this view.
     */
    private Context context;

    /**
     * How far (in px) user has to move to register next point on his path
     */
    private final float TOUCH_TOLERANCE = 4;

    /**
     * Current position of the brush;
     */
    private float brushX, brushY;

    /**
     * Path drawn by user.
     * <br>
     * Modified by {@link #onTouchEvent(MotionEvent)} and drawn by {@link #onDraw(Canvas)}.
     */
    private Path myPath;

    /**
     * Bitmap containing user drawing.
     */
    private Bitmap drawing;

    /**
     * Custom canvas, enabling to draw on {@link #drawing}
     */
    private Canvas drawingCanvas;

    /**
     * List of drawn shapes
     */
    private ArrayList<Shape> shapes;

    /**
     *  Current stroke;
     */
    private Shape stroke;

    /**
     * Basic constructor
     * @param context Activity or fragment this view is nested in. (handled by AndroidOS)
     * @param attrs A collection of attributes, associated with a tag inside XML document (this is the way this view is embedded).
     *              (handled by AndroidOS)
     */
    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;

        myPaint = new Paint();
        myPath = new Path();

        shapes=new ArrayList<Shape>();


        myPaint.setAntiAlias(true);
        myPaint.setDither(true);
        myPaint.setStrokeJoin(Paint.Join.ROUND);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeCap(Paint.Cap.ROUND);
        myPaint.setStrokeWidth(20);

        myPaint.setColor(Color.BLUE);

    }

    /**
     * Method called by {@link #invalidate()} to update drawing.
     * Draws a path that has been set by user.
     *
     * @param canvas Object that holds "draw" calls (handled by AndroidOS)
     * @see View#onDraw(Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /* Set paint params */
        /* TODO: Get params from toolbar fragment */

        if(myPath!=null){
            canvas.drawPath(myPath, myPaint);
        }

       // canvas.drawBitmap(drawing, 0, 0, myPaint);
        for(Shape s: shapes){
           if(s.getPath()!=null){
               canvas.drawPath(s.getPath(), s.getPaint());
           }
        }
    }

    /**
     * Method called when view size is changed - at the start of {@link DrawingFragment}.
     *
     * @param w New width of the view (current width of the drawing)
     * @param h New height og the view (current height of the drawing)
     * @param oldw Old width (unused).
     * @param oldh Old height (unused).
     *
     * @see View#onSizeChanged(int, int, int, int)
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        
        drawing = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawingCanvas = new Canvas(drawing);
    }

    /**
     *
     * @param event Fired event.
     * @return True if listener (this view) consumed event.
     *         ACTION_DOWN has to consume event to progress to next events - ACTION_MOVE or ACTION_UP.
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        /* Get new coordinates */
        float newBrushX=event.getX();
        float newBrushY=event.getY();


        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                /* User touched the screen - start a new line */
                startPath(newBrushX, newBrushY);
                invalidate();
                return true;

            case MotionEvent.ACTION_UP:
                /* User stopped touching the screen */

                myPath.lineTo(newBrushX, newBrushY);
                stroke.setPath(myPath);
                shapes.add(stroke);
                //drawingCanvas.drawPath(myPath, myPaint);
                //TODO: Use this method to load photos
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                /* User is drawing on the screen */
                moveBrush(newBrushX, newBrushY);
                invalidate();
                break;
        }

        return true;
    }

    private void startPath(float x, float y){
        /* Clear path */
        myPath.reset();

        /* Create new shape */
        stroke = new Shape(myPaint);

        /* Save coordinates (we'll use them when user will move) */
        brushX = x;
        brushY = y;

        /* Set a new beginning of user's path */
        myPath.moveTo(brushX, brushY);
    }

    private void moveBrush(float x, float y){

        float dx = brushX - x;
        float dy = brushY - y;

        /* If user moved at least TOUCH_TOLERANCE px, then draw line and save coordinates */
        if(Math.abs(dx)>=TOUCH_TOLERANCE || Math.abs(dy)>=TOUCH_TOLERANCE){
            myPath.quadTo(brushX, brushY, (brushX+x)/2, (brushY+y)/2);
          //  myPath.lineTo(x,y);
            brushX=x;
            brushY=y;
        }
    }

    public void backButtonPressed(){
        if(!shapes.isEmpty()){
            shapes.remove(shapes.size() - 1);
            myPath.reset();
            invalidate();
        }
    }

}
