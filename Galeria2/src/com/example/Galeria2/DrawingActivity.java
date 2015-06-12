package com.example.Galeria2;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Drawing activity is a simple container activity. It works differently when phone orientation changes.
 *<br>
 *     In <b>landscape mode</b> activity contains two fragments - {@link ToolbarFragment} and {@link DrawingFragment}.
 *     Those two fragments communicate through this activity.
 *<br>
 *     In <b>portrait mode</b> activity consists of a single {@link DrawingFragment}.
 *     Upon clicking on specified option in ActionBar or a button (to be decided) an intent starts {@link ToolbarActivity}.
 *     (Optional: swipe)
 * @author Ala
 * @since 08-05-2015
 *
 */
public class DrawingActivity extends FragmentActivity implements ActionBar.OnNavigationListener {

    DrawingFragment drawing;
    final Context context = this;
    Image image;
    Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawingactivity);

        ActionBar drawingActionBar = getActionBar();
        drawingActionBar.setTitle(getIntent().getStringExtra("fileName"));

        FragmentManager manager = getSupportFragmentManager();
        drawing = (DrawingFragment) manager.findFragmentById(R.id.drawingFragment);
    }

    @Override
    public void onBackPressed() {
        drawing.passBackButton();

    }

    /**
     * This method is called whenever a navigation item in your action bar
     * is selected.
     *
     * @param itemPosition Position of the item clicked.
     * @param itemId       ID of the item clicked.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawing_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            //jeśli ktoś wybrał toolbara to urucham
            case R.id.toolbar: {

                Intent intent=new Intent(DrawingActivity.this, ToolbarActivity.class);
                DrawingActivity.this.startActivity(intent);

                return true;}
            //jesli chce zapisać, pokaż okienko z doborem nazwy
            case R.id.save: {
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_layout);
                TextView text = (TextView) dialog.findViewById(R.id.text);
                text.setText("Choose directory");
                final EditText name = (EditText) dialog.findViewById(R.id.name);

                image=new Image();
                Button exButton = (Button) dialog.findViewById(R.id.dialogButtonE);
                exButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String drawingName = String.valueOf(name.getText());
                        if(!drawingName.equals("")) {
                            //skąd pobrać Bitmapę?
                            //image.SaveImageAtDirectory(, drawingName);
                        }
                        dialog.dismiss();
                    }
                });

                Button insButton = (Button) dialog.findViewById(R.id.dialogButtonI);
                // if button is clicked, close the custom dialog
                insButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //image.SaveImage();
                        dialog.dismiss();
                    }
                });

                return true;}
            default:
                return super.onOptionsItemSelected(item);


            //dialog.show();
        }
    }

}
