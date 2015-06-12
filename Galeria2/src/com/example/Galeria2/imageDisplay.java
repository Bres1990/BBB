package com.example.Galeria2;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.polites.android.GestureImageView;

import java.util.Date;


/**
 * Activity handling the display of selected image
 *
 * @author Adam
 * @since 08-05-2015
 */
public class imageDisplay extends Activity implements ActionBar.OnNavigationListener, OnMapReadyCallback {
    ImageView image;
    Intent mainIntent;
    Dialog ratingDialog, mapDialog;
    RatingBar ratingBar;
    String name;
    Float userRankValue;
    Integer filePosition;
    Boolean saved;
    Bundle imageState;
    MapFragment mMapFragment;
    GoogleMap googleMap;
    LatLng imageLocation;
    DatabaseAdapter myDatabaseAdapter = new DatabaseAdapter(this);
    ImageTask currentImage;

    @Override
    public void onCreate(Bundle outState) {
        if (outState != null) {
            saved = getIntent().getBooleanExtra("saved", false);
            //if (outState != null) {
            if (saved == false) {
                filePosition = getIntent().getIntExtra("filePosition", 0);
            } else {
                filePosition = getIntent().getIntExtra("filePosition", 0);
                userRankValue = getIntent().getFloatExtra("userRankValue", 0);
            }
        }
        super.onCreate(outState);
        setContentView(R.layout.image);


        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        GestureImageView view = new GestureImageView(this);

        image = (GestureImageView) findViewById(R.id.full_image);
        Uri imageURI = Uri.parse(getIntent().getStringExtra("ImageURI"));
        image.setImageURI(imageURI);

        String uriString = imageURI.toString();

        myDatabaseAdapter.open();
        currentImage = myDatabaseAdapter.getImageByAddress(uriString);

        view.setLayoutParams(params);

        mainIntent = new Intent();

        ActionBar imageActionBar = getActionBar();
        imageActionBar.setTitle(getIntent().getStringExtra("fileName"));


    }

    @Override
    public void onBackPressed() {
        mainIntent.putExtra("userRankValue2", userRankValue);
        mainIntent.putExtra("filePosition2", filePosition);
        mainIntent.putExtra("saved2", true);
        mainIntent.putExtra("imageState", imageState);

        setResult(Activity.RESULT_OK, mainIntent);
        finish();
    }

    public void imageClick(View view) {
        showDetails();
    }

    public void showDetails() {
        long modifiedData = getIntent().getLongExtra("last_modified", 0);
        Date modifiedDate = new Date(modifiedData);
        String modifiedDateString = String.valueOf(modifiedDate);
        Toast.makeText(getApplicationContext(), modifiedDateString, Toast.LENGTH_LONG).show();
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
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.rating_item:
                ratingDialog = new Dialog(this);
                ratingDialog.setContentView(R.layout.ratingdialog);
                ratingDialog.setCancelable(true);
                ratingBar = (RatingBar)ratingDialog.findViewById(R.id.dialog_ratingbar);

                if (userRankValue != null) {
                    ratingBar.setRating(userRankValue);
                }

                TextView text = (TextView)ratingDialog.findViewById(R.id.rank_dialog_text1);
                name = "Image rating";
                text.setText(name);

                Button updateButton = (Button)ratingDialog.findViewById(R.id.rank_dialog_button);
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userRankValue = ratingBar.getRating();
                        currentImage.setRating(userRankValue);
                        myDatabaseAdapter.updateImage(currentImage);
                        ratingDialog.dismiss();
                    }
                });

                if (userRankValue != null) {
                    ratingBar.setRating(userRankValue);
                }

                //now that the dialog is set up, it's time to show it
                ratingDialog.show();

                return true;
            case R.id.locating_item:

                if (currentImage.getName().contains("QR_")) {
                    mapDialog = new Dialog(this);
                    mapDialog.setContentView(R.layout.mapdialog);
                    mapDialog.setCancelable(true);

                    mMapFragment = MapFragment.newInstance();
                    FragmentTransaction fragmentTransaction =
                            getFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.frameLayout, mMapFragment);
                    fragmentTransaction.commit();
                    mMapFragment.getMapAsync(this);
                    googleMap = mMapFragment.getMap();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Impossible!", Toast.LENGTH_LONG).show();
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        imageLocation = new LatLng(currentImage.getLatitude(), currentImage.getLongitude());
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(imageLocation, 13));

        googleMap.addMarker(new MarkerOptions()
                .title("Wroclaw")
                        //.snippet("The coolest city in Wroclaw.")
                .position(imageLocation));
    }

}
