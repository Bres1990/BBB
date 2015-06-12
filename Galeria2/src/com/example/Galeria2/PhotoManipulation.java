package com.example.Galeria2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import com.example.Galeria2.DatabaseAdapter;

/**
 * Created by Asia on 14.05.2015.
 */
public class PhotoManipulation extends Activity{

    Bitmap photo;
    File photoFile;
    Intent intent;
    String ExternalStorageDirectoryPath, targetPath;
    static  final int REQUEST_TAKE_PHOTO=1;
    private String URI;
    private File newPicture;

    LatLng imagePosition;

    DatabaseAdapter myDatabaseAdapter = new DatabaseAdapter(this);  //SharedPreferences?
    ImageTask currentImage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photomanipulation);

        intent = getIntent();
       // intent.getData()
       // photo= (Bitmap) intent.getParcelableExtra("BitmapImage");

        ImageView pictureTaken = (ImageView) findViewById(R.id.photoView);

        //pictureTaken.setImageBitmap(photo);
        URI = intent.getStringExtra("URI");
        newPicture = new File(Environment.getExternalStorageDirectory()+URI);
        Bitmap image = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + URI);

        pictureTaken.setImageBitmap(image);

        Button save = (Button) findViewById(R.id.save);
        Button delete = (Button) findViewById(R.id.delete);

        myDatabaseAdapter.open();
        currentImage = myDatabaseAdapter.getImageByAddress(URI);//z jakiegos powodu zwraca nulla
    }


    public void onClick(View view) {

        if (view.getId() == R.id.save) {
            imagePosition = Image.LocateImage(getApplicationContext());
            currentImage.setLatitude(imagePosition.latitude);
            currentImage.setLongitude(imagePosition.longitude);
            Toast.makeText(getApplicationContext(), imagePosition.toString(), Toast.LENGTH_LONG).show();
        }
        myDatabaseAdapter.updateImage(currentImage);

        finish();

        if (view.getId() == R.id.delete) {

            boolean deleted = newPicture.delete();

            Log.i("DeleteFile", ""+deleted);

            finish();
        }
    }

}
