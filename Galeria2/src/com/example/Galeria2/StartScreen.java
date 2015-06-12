package com.example.Galeria2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.Galeria2.PhotoManipulation;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;


public class StartScreen extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int CAMERA_REQUEST = 2345;
    public SharedPreferences prefs;
    private String outputURI;
    DatabaseAdapter myDatabaseAdapter = new DatabaseAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introscreen);
        String TAG = "Typefaces";
        Typeface menuFont = null;
        //powinno ustawiac czcionke

        final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

        synchronized (cache) {
            if (!cache.containsKey("fonts/cambria.ttf")) {
                try {
                    menuFont = Typeface.createFromAsset(this.getAssets(),
                            "fonts/cambria.ttf");
                    cache.put("fonts/cambria.ttf", menuFont);
                } catch (Exception e) {
                    Log.e(TAG, "Could not get typeface '" + "fonts/cambria.ttf"
                            + "' because " + e.getMessage());
                }
            }
        }

        myDatabaseAdapter = new DatabaseAdapter(getApplicationContext());
        myDatabaseAdapter.open();

        prefs = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);

        setPrefs();

        //Typeface menuFont=Typeface.createFromAsset(getAssets(), "fonts/cambria.ttf");
    if(menuFont!=null) {
        Button gallery = (Button) findViewById(R.id.gallery);
        gallery.setTypeface(menuFont);

        Button draw = (Button) findViewById(R.id.draw);
        gallery.setTypeface(menuFont);

        Button takePhoto = (Button) findViewById(R.id.photo);
        gallery.setTypeface(menuFont);
    }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setPrefs();
    }

    private void setPrefs() {

    }


    public void onClick(View view) {

        /*
        opens the gallery
         */
        if (view.getId() == R.id.gallery) {
                Intent selectedIntent = new Intent(StartScreen.this, MainActivity.class);
                startActivityForResult(selectedIntent,2222);


        }

        /*
        opens drawing tool
         */
        if  (view.getId() == R.id.draw){
                Intent selectedIntent = new Intent(StartScreen.this, DrawingActivity.class);
                startActivityForResult(selectedIntent,3333);

        }

        /*
        opens camera
         */
        if (view.getId() == R.id.photo){
            //camera stuff
            Intent imageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            Log.i("CreateDir",Environment.getExternalStorageDirectory().getAbsolutePath());

            //folder stuff
            File imagesFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"DCIM"+File.separator+"Test");
            //imagesFolder.mkdirs();

            if(!imagesFolder.exists() && !imagesFolder.isDirectory())
            {
                // create empty directory
                if (imagesFolder.mkdirs())
                {
                    Log.i("CreateDir","App dir created");
                }
                else
                {
                    Log.w("CreateDir","Unable to create app dir!");
                }
            }
            else
            {
                Log.i("CreateDir","App dir already exists");
            }

            /* MediaScannerConnection indexes the file */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Intent mediaScanIntent = new Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(imagesFolder);
                mediaScanIntent.setData(contentUri);
                sendBroadcast(mediaScanIntent);
            } else {
                sendBroadcast(new Intent(
                        Intent.ACTION_MEDIA_MOUNTED,
                        Uri.parse("file://"
                                + Environment.getExternalStorageDirectory())));
            }

            /* done */
            outputURI = "QR_" + timeStamp + ".jpg";

            String name = outputURI;

            File image = new File(imagesFolder, outputURI);
            Uri uriSavedImage = Uri.fromFile(image);

            outputURI= "/DCIM/Test/"+outputURI;

            myDatabaseAdapter.insertImage(name, outputURI, null, null);

            imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
            startActivityForResult(imageIntent, CAMERA_REQUEST);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            //osobna activity na wyswietlanie zdjecia?
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Intent selectedIntent = new Intent(StartScreen.this, PhotoManipulation.class);
            selectedIntent.putExtra("URI", outputURI);
            StartScreen.this.startActivity(selectedIntent);
        }
    }

    /**
     * Called when a shared preference is changed, added, or removed. This
     * may be called even if a preference is set to its existing value.
     * <p/>
     * <p>This callback will be run on your main thread.
     *
     * @param sharedPreferences The {@link android.content.SharedPreferences} that received
     *                          the change.
     * @param key               The key of the preference that was changed, added, or
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}
