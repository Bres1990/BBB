package com.example.Galeria2;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static android.widget.AdapterView.*;
import static android.widget.AdapterView.OnItemLongClickListener;

public class MainActivity extends Activity {
    public ArrayList<String> itemList = new ArrayList<String>();
    ImageView imageView;
    GridView gridview;
    ImageAdapter myImageAdapter;
    String ExternalStorageDirectoryPath, fileName, imageURI, targetPath;
    File[] files;
    File f, targetDirector;
    Intent imageIntent;
    Bitmap[] bitmaps;
    Float[] userRankArray;
    Float userRankValue;
    long last_modified;
    int filePosition;
    boolean saved;
    private Notification note;
    DatabaseAdapter myDatabaseAdapter;


    public class ImageAdapter extends BaseAdapter {

        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        void add(String path){
            itemList.add(path);
        }

        void clear() {
            itemList.clear();
        }

        void remove(int index){
            itemList.remove(index);
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(220, 220));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }



            //Bitmap bm = decodeSampledBitmapFromUri(itemList.get(position), 220, 220);
            //Use the path as the key to LruCache
            final String imageKey = itemList.get(position);
            final Bitmap bm = getBitmapFromMemCache(imageKey);

            if (bm == null) {
                BitmapWorkerTask task = new BitmapWorkerTask(imageView);
                task.execute(imageKey);
            }

            imageView.setImageBitmap(bm);
            return imageView;
        }

        public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {

            Bitmap bm = null;
            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeFile(path, options);

            return bm;
        }

        public int calculateInSampleSize(

                BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {
                if (width > height) {
                    inSampleSize = Math.round((float)height / (float)reqHeight);
                } else {
                    inSampleSize = Math.round((float)width / (float)reqWidth);
                }
            }

            return inSampleSize;
        }

        class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

            private final WeakReference<ImageView> imageViewReference;

            public BitmapWorkerTask(ImageView imageView) {
                // Use a WeakReference to ensure the ImageView can be garbage collected
                imageViewReference = new WeakReference<ImageView>(imageView);
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                final Bitmap bitmap = decodeSampledBitmapFromUri(params[0], 200, 200);
                addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (imageViewReference != null && bitmap != null) {
                    final ImageView imageView = (ImageView)imageViewReference.get();
                    if (imageView != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        }

    }

    OnItemClickListener myOnItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    /*
                    * We're passing the URI to prevent it from exceeding the binder transaction buffer
                    **/
            imageURI = itemList.get(position);
            imageIntent.putExtra("ImageURI", imageURI);

            f = new File(itemList.get(position));
            last_modified = f.lastModified();
            imageIntent.putExtra("last_modified", last_modified);

            fileName = f.getName();
            imageIntent.putExtra("fileName", fileName);

            if (saved == false) {
               imageIntent.putExtra("filePosition", filePosition);
               imageIntent.putExtra("saved", saved);
            } else {
                imageIntent.putExtra("filePosition", filePosition);
                System.out.println("Saved: "+saved);
                imageIntent.putExtra("userRankValue", userRankArray[filePosition]);
            }


            startActivityForResult(imageIntent, 0);
        }
    };

    OnItemLongClickListener myOnItemLongClickListener = new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            File file = new File(itemList.get(position));
            Log.i("OnLongClickRemoval", file.getAbsolutePath());
            boolean deleted = file.delete();
            Log.i("OnLongClickRemoval", "DELETED: "+deleted);

            /* Inform MediaScannerConnector that this file is absent */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Intent mediaScanIntent = new Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(file);
                mediaScanIntent.setData(contentUri);
                sendBroadcast(mediaScanIntent);
            } else {
                sendBroadcast(new Intent(
                        Intent.ACTION_MEDIA_MOUNTED,
                        Uri.parse("file://"
                                + Environment.getExternalStorageDirectory())));
            }

            itemList.remove(itemList.get(position));
            myImageAdapter.notifyDataSetChanged();
            return false;
        }
    };

    private LruCache<String, Bitmap> mMemoryCache;


    @Override
    public void onCreate(Bundle mainState) {
        super.onCreate(mainState);
        setContentView(R.layout.main);

        gridview = (GridView) findViewById(R.id.gridview);
        myImageAdapter = new ImageAdapter(getApplicationContext());
        myDatabaseAdapter = new DatabaseAdapter(getApplicationContext());

        gridview.setAdapter(myImageAdapter);

        ExternalStorageDirectoryPath = Environment
                .getExternalStorageDirectory()
                .getAbsolutePath();
        targetPath = ExternalStorageDirectoryPath + "/DCIM/Test/";

        //Toast.makeText(getApplicationContext(), targetPath, Toast.LENGTH_LONG).show();
        targetDirector = new File(targetPath);
        myDatabaseAdapter.open();
        try {
            files = targetDirector.listFiles();
            for (File file : files){
                myImageAdapter.add(file.getAbsolutePath());
                myDatabaseAdapter.insertImage(file.getName(), file.getAbsolutePath(), null, null);
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

        // Get memory class of this device, exceeding this amount will throw an
        // OutOfMemory exception.
        final int memClass
                = ((ActivityManager)getSystemService(Context.ACTIVITY_SERVICE))
                .getMemoryClass();

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = 1024 * 1024 * memClass / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {

            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in bytes rather than number of items.
                return bitmap.getByteCount();
            }
        };

        bitmaps = new Bitmap[gridview.getChildCount()];

        gridview.setOnItemClickListener(myOnItemClickListener);
        gridview.setOnItemLongClickListener(myOnItemLongClickListener);

        saved = false;

        imageIntent = new Intent(this, imageDisplay.class);

    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return (Bitmap) mMemoryCache.get(key);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, imageIntent);

        if (resultCode == Activity.RESULT_OK)
        {
            switch (requestCode)
            {
                case 0:
                {

                }
                break;
            }
        }
    }

}