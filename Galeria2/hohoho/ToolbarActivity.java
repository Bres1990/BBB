package com.example.Galeria2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Activity containing {@link ToolbarFragment}. This activity is used in portrait mode.
 * It is started when user chooses specified option inside {@link DrawingActivity} (ActionBar/Button?)
 *
 * @author Ala
 * @since 09-05-2015
 */

public class ToolbarActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    TextView sizeText;
    SeekBar sizeBar;
    int size;
    int color;
    ColorPickerView colorPicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbaractivity);


        sizeBar = (SeekBar) findViewById(R.id.sizeBar);
        colorPicker = (ColorPickerView) findViewById(R.id.view2);
        sizeText = (TextView) findViewById(R.id.size);

        Intent intent = getIntent();
        color = intent.getIntExtra("color", Color.BLACK);
        size = intent.getIntExtra("size", 10);

        sizeBar.setOnSeekBarChangeListener(this);

        sizeBar.setProgress(size - 10);

        colorPicker.setCenterPaint(color);



    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        size = progress+10;
        sizeText.setText(""+size);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2222 && resultCode == RESULT_OK) {
        }
    }
}