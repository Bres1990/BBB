package com.example.Galeria2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.SQLException;

/**
 * Created by Adam on 2015-05-26.
 */
public class DatabaseAdapter {
    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;

    public static final String DEBUG_TAG = "ImageManager";

    public static int DB_VERSION = 1;
    public static final String DB_NAME = "images.db";
    public static final String DB_IMAGE_TABLE = "images";

    // nazwa | adres | rating | latitude | longitude //
    public static final String KEY_ID = "Id";
    public static final String KEY_NAME = "Name";
    public static final String KEY_ADDRESS = "Address";
    public static final String KEY_RATING = "Rating";
    public static final String KEY_LATITUDE = "Latitude";
    public static final String KEY_LONGITUDE = "Longitude";

    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String NAME_OPTIONS = "TEXT NOT NULL";
    public static final String ADDRESS_OPTIONS = "TEXT NOT NULL";
    public static final String RATING_OPTIONS = "REAL DEFAULT 0.0";
    public static final String LATITUDE_OPTIONS = "REAL DEFAULT 0.00";
    public static final String LONGITUDE_OPTIONS = "REAL DEFAULT 0.00";

    public static final int ID_COLUMN = 0;
    public static final int NAME_COLUMN = 1;
    public static final int ADDRESS_COLUMN = 2;
    public static final int RATING_COLUMN = 3;
    public static final int LATITUDE_COLUMN = 4;
    public static final int LONGITUDE_COLUMN = 5;

    public static final String[] columns = {KEY_NAME, KEY_ADDRESS, KEY_RATING, KEY_LATITUDE, KEY_LONGITUDE};

    public static final String DB_CREATE_IMAGE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + DB_IMAGE_TABLE + "("
                    + KEY_ID + " " + ID_OPTIONS + ", "
                    + KEY_NAME + " " + NAME_OPTIONS + ", "
                    + KEY_ADDRESS + " " + ADDRESS_OPTIONS + ", "
                    + KEY_RATING + " " + RATING_OPTIONS + ", "
                    + KEY_LATITUDE + " " + LATITUDE_OPTIONS + ", "
                    + KEY_LONGITUDE + " " + LONGITUDE_OPTIONS + ");";
    public static final String DROP_IMAGE_TABLE =
            "DROP TABLE IF EXISTS " + DB_IMAGE_TABLE;

    public DatabaseAdapter(Context context) {
        this.context = context;
    }

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DB_CREATE_IMAGE_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(DatabaseAdapter.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + DB_IMAGE_TABLE);
        onCreate(database);
    }

    public DatabaseAdapter open() {
        dbHelper = new DatabaseHelper(context);
        //try {
        db = dbHelper.getWritableDatabase();
        //} catch (SQLException e) {
        //db = dbHelper.getReadableDatabase();
        //}

        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insertImage(String name, String address) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues newImageValues = new ContentValues();
        newImageValues.put(KEY_NAME, name);
        newImageValues.put(KEY_ADDRESS, address);
        //db.insert(DB_IMAGE_TABLE, null, newImageValues);

        long insertId = db.insert(DB_IMAGE_TABLE, null, newImageValues);
        Cursor cursor = db.query(DB_IMAGE_TABLE, columns, DatabaseAdapter.ID_COLUMN + " = " + insertId, null, null, null, null);
        if( cursor != null && cursor.moveToFirst() ) {
            ImageTask newImage = cursorToImage(cursor);
            cursor.close();
        }

        System.out.println("\nDodaje "+name+" "+address);
    }

    public void updateImage(ImageTask task) {

        String name = task.getName();
        String address = task.getAddress();
        Float rating = task.getRating();
        Double latitude = task.getLatitude();
        Double longitude = task.getLongitude();

        updateImage(name, address, rating, latitude, longitude);
    }

    public void updateImage(String name, String address, Float rating, Double latitude, Double longitude) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String where = KEY_NAME + " = \"" + name + "\"";
        ContentValues updateImageValues = new ContentValues();
        updateImageValues.put(KEY_NAME, name);
        updateImageValues.put(KEY_ADDRESS, address);
        updateImageValues.put(KEY_RATING, rating);
        updateImageValues.put(KEY_LATITUDE, latitude);
        updateImageValues.put(KEY_LONGITUDE, longitude);


        db.update(DB_IMAGE_TABLE, updateImageValues, where, null);
        System.out.println("Zaktualizowano do postaci: "+name+" "+address+" "+rating+" "+latitude+" "+longitude);


    }

    public boolean deleteImage(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String where = KEY_ID + " = " + id;
        return db.delete(DB_IMAGE_TABLE, where, null) > 0;
    }

    public Cursor getAllImages() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        return db.query(DB_IMAGE_TABLE, columns, null, null, null, null, null);
    }

    public ImageTask getImageByName(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {KEY_ID, KEY_NAME, KEY_ADDRESS, KEY_RATING, KEY_LATITUDE, KEY_LONGITUDE};
        String where = KEY_NAME + " = \"" + name + "\"";
        Cursor cursor = db.query(DB_IMAGE_TABLE, columns, where, null, null, null, null);
        ImageTask task = null;
        if (cursor != null && cursor.moveToFirst()) {
            Long id = cursor.getLong(ID_COLUMN);
            String address = cursor.getString(ADDRESS_COLUMN);
            Float rating = cursor.getFloat(RATING_COLUMN);
            Double latitude = cursor.getDouble(LATITUDE_COLUMN);
            Double longitude = cursor.getDouble(LONGITUDE_COLUMN);
            task = new ImageTask(id, name, address, rating, latitude, longitude);
        }
        cursor.close();

        return task;
    }

    public ImageTask getImageByAddress(String address) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {KEY_ID, KEY_NAME, KEY_ADDRESS, KEY_RATING, KEY_LATITUDE, KEY_LONGITUDE};
        String where = KEY_ADDRESS + " = \"" + address + "\"";
        Cursor cursor = db.query(DB_IMAGE_TABLE, columns, where, null, null, null, null);
        ImageTask task = null;
        if (cursor != null && cursor.moveToFirst()) {
            Long id = cursor.getLong(ID_COLUMN);
            String name = cursor.getString(NAME_COLUMN);
            Float rating = cursor.getFloat(RATING_COLUMN);
            Double latitude = cursor.getDouble(LATITUDE_COLUMN);
            Double longitude = cursor.getDouble(LONGITUDE_COLUMN);
            task = new ImageTask(id, name, address, rating, latitude, longitude);
        }

        cursor.close();

        return task;
    }

    private ImageTask cursorToImage(Cursor cursor) {
        ImageTask imageTask = new ImageTask();
        imageTask.setId(cursor.getLong(ID_COLUMN));
        imageTask.setName(cursor.getString(NAME_COLUMN));
        imageTask.setAddress(cursor.getString(ADDRESS_COLUMN));
        imageTask.setRating(cursor.getFloat(RATING_COLUMN));
        imageTask.setLatitude(cursor.getDouble(LATITUDE_COLUMN));
        imageTask.setLongitude(cursor.getDouble(LONGITUDE_COLUMN));

        cursor.close();

        return imageTask;
    }

    /*public static String execJSfunction(Object functionName) throws ScriptException, FileNotFoundException, NoSuchMethodException
    {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        File functionscript = new File("public/lib/marked.js");
        Reader reader = new FileReader(functionscript);
        engine.eval(reader);

        Invocable invocableEngine = (Invocable) engine;
        Object marked = engine.get("marked");
        Object lexer = invocableEngine.invokeMethod(marked, "lexer", "**hello**");
        Object result = invocableEngine.invokeMethod(marked, "parser", lexer);
        return result.toString();
    }*/
}