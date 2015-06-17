package com.example.Galeria2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Adam on 2015-06-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     */
    public DatabaseHelper(Context context) {
        super(context, DatabaseAdapter.DB_NAME, null, DatabaseAdapter.DB_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        DatabaseAdapter.onCreate(db);

        Log.d(DatabaseAdapter.DEBUG_TAG, "Database creating...");
        Log.d(DatabaseAdapter.DEBUG_TAG, "Table " + DatabaseAdapter.DB_IMAGE_TABLE + " ver. " + DatabaseAdapter.DB_VERSION + " created.");
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
        db.execSQL(DatabaseAdapter.DROP_IMAGE_TABLE);

        Log.d(DatabaseAdapter.DEBUG_TAG, "Database updating...");
        Log.d(DatabaseAdapter.DEBUG_TAG, "Table" + DatabaseAdapter.DB_IMAGE_TABLE + " updated from ver. " + oldVersion + " to ver. " + newVersion);
        Log.d(DatabaseAdapter.DEBUG_TAG, "Old data is lost.");

        DatabaseAdapter.onUpgrade(db, oldVersion, newVersion);
    }
}