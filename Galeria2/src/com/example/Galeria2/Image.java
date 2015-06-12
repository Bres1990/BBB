package com.example.Galeria2;

/**
 * @author Adam Poterałowicz
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Environment;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Class responsible for handling all actions connected with images' handling - saving, deleting, modifying, rating,
 * localizing
 *
 */
public class Image {
    private String imgName;
    private Date imgDate;

    public Image()
    {
        String imgName;
        Date imgDate;
    }

    /**
     * Method enabling user to rename an image
     * @param i -- image
     * @param name -- new name of an image
     */
    public void setName(Image i, String name)
    {
        i.imgName = name;
    }

    /**
     * Method returning an image name - if it's set
     * @param i -- image
     * @return image name
     */
    public String getName(Image i)
    {
        return i.imgName;
    }

    /*public Date getLastModifiedDate(Image i)
    {


        return i.imgDate;
    }*/

    /**
     * Method saving an image in a predefined directory
     * @param finalBitmap -- bitmap of an image to save
     * @param photo_directory -- directory for the photo to be saved at
     */
    public static void SaveImageAtDirectory(Bitmap finalBitmap, String photo_directory) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + photo_directory);
        myDir.mkdirs();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "Image-"+ timeStamp +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        String photo_directory = "/DCIM/Test";
        File myDir = new File(root + photo_directory);
        myDir.mkdirs();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "Image-"+ timeStamp +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mathod establishing image's location.
     * @param ctx Application context
     * @return Image location coordinates
     */
    public static LatLng LocateImage(Context ctx)
    {
        LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);

        /**
        * Loop over the array backwards, and if you get an accurate location,
        * then break out the loop
        */
        Location l = null;

        for (int i = providers.size() - 1; i >= 0; i--)
        {
            l = lm.getLastKnownLocation(providers.get(i));
            if (l != null)
                break;
        }

        return new LatLng(l.getLatitude(), l.getLongitude());
    }


}
