package com.example.Galeria2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

/**
 @author Joanna
 **/

public class logoAnimation extends Activity {

    private int imageIndex;
    private int TIME_BETWEEN = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_main);

        final ImageView imageBoard = (ImageView) findViewById(R.id.animationView);


        final Handler handler = new Handler();

        //xxx


        final Drawable[] part1 = new Drawable[2];
        part1[0] = getResources().getDrawable(R.drawable.szkic1);
        part1[1] = getResources().getDrawable(R.drawable.szkic2);

        final Drawable[] part2 = new Drawable[2];
        part2[0] = getResources().getDrawable(R.drawable.szkic2);
        part2[1] = getResources().getDrawable(R.drawable.szkic3);

        final Drawable[] part3 = new Drawable[2];
        part3[0] = getResources().getDrawable(R.drawable.szkic3);
        part3[1] = getResources().getDrawable(R.drawable.owl);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TransitionDrawable transitionDrawable = new TransitionDrawable(part1);
                imageBoard.setImageDrawable(transitionDrawable);
                transitionDrawable.startTransition(TIME_BETWEEN);
            }
        },500);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TransitionDrawable transitionDrawable = new TransitionDrawable(part2);
                imageBoard.setImageDrawable(transitionDrawable);
                transitionDrawable.startTransition(TIME_BETWEEN);

            }
        },500);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TransitionDrawable transitionDrawable = new TransitionDrawable(part3);
                imageBoard.setImageDrawable(transitionDrawable);
                transitionDrawable.startTransition(TIME_BETWEEN);
            }
        },500);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                Intent intent = new Intent(logoAnimation.this, StartScreen.class);
                logoAnimation.this.startActivity(intent);
            }
        }, 5000);

    }
}


