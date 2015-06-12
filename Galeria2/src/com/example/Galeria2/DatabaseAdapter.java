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

    private static final String DEBUG_TAG = "ImageManager";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database.db";
    private static final String DB_IMAGE_TABLE = "image";

    // nazwa | adres | rating | latitude | longitude //
    public static final String KEY_NAME = "Name";
    public static final String KEY_ADDRESS = "Address";
    public static final String KEY_RATING = "Rating";
    public static final String KEY_LATITUDE = "Latitude";
    public static final String KEY_LONGITUDE = "Longitude";

    public static final String NAME_OPTIONS = "TEXT NOT NULL";
    public static final String ADDRESS_OPTIONS = "TEXT NOT NULL";
    public static final String RATING_OPTIONS = "FLOAT DEFAULT 0.0";
    public static final String LATITUDE_OPTIONS = "DOUBLE";
    public static final String LONGITUDE_OPTIONS = "DOUBLE";

    public static final int NAME_COLUMN = 0;
    public static final int ADDRESS_COLUMN = 1;
    public static final int RATING_COLUMN = 2;
    public static final int LATITUDE_COLUMN = 3;
    public static final int LONGITUDE_COLUMN = 4;

    private static final String DB_CREATE_IMAGE_TABLE =
            "CREATE TABLE " + DB_IMAGE_TABLE + "("
                    + KEY_NAME + " " + NAME_OPTIONS + ", "
                    + KEY_ADDRESS + " " + ADDRESS_OPTIONS + ", "
                    + KEY_RATING + " " + RATING_OPTIONS + ", "
                    + KEY_LATITUDE + " " + LATITUDE_OPTIONS + ", "
                    + KEY_LONGITUDE + " " + LONGITUDE_OPTIONS + ");";
    private static final String DROP_IMAGE_TABLE =
            "DROP TABLE IF EXISTS" + DB_IMAGE_TABLE;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        /**
         * Create a helper object to create, open, and/or manage a database.
         * This method always returns very quickly.  The database is not actually
         * created or opened until one of {@link #getWritableDatabase} or
         * {@link #getReadableDatabase} is called.
         *
         * @param context to use to open or create the database
         * @param name    of the database file, or null for an in-memory database
         * @param factory to use for creating cursor objects, or null for the default
         * @param version number of the database (starting at 1); if the database is older,
         *                {@link #onUpgrade} will be used to upgrade the database; if the database is
         *                newer, {@link #onDowngrade} will be used to downgrade the database
         */
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        /**
         * Called when the database is created for the first time. This is where the
         * creation of tables and the initial population of the tables should happen.
         *
         * @param db The database.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE_IMAGE_TABLE);

            Log.d(DEBUG_TAG, "Database creating...");
            Log.d(DEBUG_TAG, "Table " + DB_IMAGE_TABLE + " ver. " + DB_VERSION + " created.");
        }

        /**
         * Called when the database needs to be upgraded. The implementation
         * should use this method to drop tables, add tables, or do anything else it
         * needs to upgrade to the new schema version.
         * <p/>
         * <p>
         * The SQLite ALTER TABLE documentation can be found
         * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
         * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
         * you can use ALTER TABLE to rename the old table, then create the new table and then
         * populate the new table with the contents of the old table.
         * </p><p>
         * This method executes within a transaction.  If an exception is thrown, all changes
         * will automatically be rolled back.
         * </p>
         *
         * @param db         The database.
         * @param oldVersion The old database version.
         * @param newVersion The new database version.
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_IMAGE_TABLE);

            Log.d(DEBUG_TAG, "Database updating...");
            Log.d(DEBUG_TAG, "Table" + DB_IMAGE_TABLE + " updated from ver. " + oldVersion + " to ver. " + newVersion);
            Log.d(DEBUG_TAG, "Old data is lost.");

            onCreate(db);
        }
    }

    public DatabaseAdapter(Context context) {
        this.context = context;
    }

    public DatabaseAdapter open() {
        dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
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

    public void insertImage(String name, String address, Double latitude, Double longitude) {
        //ContentValues newImageValues = new ContentValues();
        //newImageValues.put(KEY_NAME, name);
        //newImageValues.put(KEY_ADDRESS, address);
        //newImageValues.put(KEY_LATITUDE, latitude);
        //newImageValues.put(KEY_LONGITUDE, longitude);
        //db.insert(DB_IMAGE_TABLE, null, newImageValues);


    }

    public void updateImage(ImageTask task) {
        //String name = task.getName();
        //String address = task.getAddress();
        //Float rating = task.getRating();
        //Double latitude = task.getLatitude();
        //Double longitude = task.getLongitude();

        //return updateImage(name, address, rating, latitude, longitude);
    }

    public void updateImage(String name, String address, Float rating, Double latitude, Double longitude) {
        //String where = KEY_NAME + " = " + name;
        //ContentValues updateImageValues = new ContentValues();
        //updateImageValues.put(KEY_NAME, name);
        //updateImageValues.put(KEY_ADDRESS, address);
        //updateImageValues.put(KEY_RATING, rating);
        //updateImageValues.put(KEY_LATITUDE, latitude);
        //updateImageValues.put(KEY_LONGITUDE, longitude);

        //return db.update(DB_IMAGE_TABLE, updateImageValues, where, null) > 0;

    }

    public void deleteImage(String name) {
        //String where = KEY_NAME + " = " + name;

        //return db.delete(DB_IMAGE_TABLE, where, null) > 0;
    }

    public void getAllImages() {
        //String[] columns = {KEY_NAME, KEY_ADDRESS, KEY_RATING, KEY_LATITUDE, KEY_LONGITUDE};

        //return db.query(DB_IMAGE_TABLE, columns, null, null, null, null, null);
    }

    public void getImageByName(String name) {
        //String[] columns = {KEY_NAME, KEY_ADDRESS, KEY_RATING, KEY_LATITUDE, KEY_LONGITUDE};
        //String where = KEY_NAME + " = " + name;
        //Cursor cursor = db.query(DB_IMAGE_TABLE, columns, where, null, null, null, null);
        //ImageTask task = null;
        /*if (cursor != null && cursor.moveToFirst()) {
            String address = cursor.getString(ADDRESS_COLUMN);
            Float rating = cursor.getFloat(RATING_COLUMN);
            Double latitude = cursor.getDouble(LATITUDE_COLUMN);
            Double longitude = cursor.getDouble(LONGITUDE_COLUMN);
            task = new ImageTask(name, address, rating, latitude, longitude);
        }

        return task;*/
    }

    public void getImageByAddress(String address) {
        /*String[] columns = {KEY_NAME, KEY_ADDRESS, KEY_RATING, KEY_LATITUDE, KEY_LONGITUDE};
        String where = KEY_ADDRESS + " = \"" + address + "\"";
        Cursor cursor = db.query(DB_IMAGE_TABLE, columns, where, null, null, null, null);
        ImageTask task = null;
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(NAME_COLUMN);
            Float rating = cursor.getFloat(RATING_COLUMN);
            Double latitude = cursor.getDouble(LATITUDE_COLUMN);
            Double longitude = cursor.getDouble(LONGITUDE_COLUMN);
            task = new ImageTask(name, address, rating, latitude, longitude);
        }

        return task;*/
    }

    public static String execJSfunction(Object functionName) throws ScriptException, FileNotFoundException, NoSuchMethodException
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
    }
}