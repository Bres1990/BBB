package com.example.Galeria2;

import android.app.Activity;
import android.os.Bundle;

/**
 * Activity containing {@link ToolbarFragment}. This activity is used in portrait mode.
 * It is started when user chooses specified option inside {@link DrawingActivity} (ActionBar/Button?)
 *
 * @author Ala
 * @since 09-05-2015
 */
public class ToolbarActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbaractivity);
    }
}