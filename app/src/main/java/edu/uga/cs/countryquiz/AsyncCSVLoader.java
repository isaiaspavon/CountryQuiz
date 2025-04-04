package edu.uga.cs.countryquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * An asynchronous task that loads country and continent data from a CSV file
 * into the SQLite database. It ensures that the database is not repopulated
 * if data already exists.
 */
public class AsyncCSVLoader extends AsyncTask<Void, Void, Void> {

    private Context context;
    private DatabaseHelper dbHelper;

    /**
     * Constructs an AsyncCSVLoader instance.
     *
     * @param context The application context used for accessing resources and the database.
     */
    public AsyncCSVLoader(Context context) {
        this.context = context;
        this.dbHelper = DatabaseHelper.getInstance(context);
    } // AsyncCSVLoader

    /**
     * Loads the CSV data into the database in the background.
     * If the database is already populated, it skips the loading process.
     *
     * @param voids Unused parameter.
     * @return null
     */
    @Override
    protected Void doInBackground(Void... voids) {
        // Check if database already has data
        if (isDatabasePopulated()) {
            Log.d("AsyncCSVLoader", "Database already populated. Skipping CSV load.");
            return null;
        } // if

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            // Open CSV file from res/raw
            InputStream is = context.getResources().openRawResource(R.raw.country_continent);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");  // Split by comma

                if (tokens.length == 2) {  // Ensure valid data
                    String country = tokens[0].trim();
                    String continent = tokens[1].trim();

                    // Insert data into database
                    String sql = "INSERT INTO " + DatabaseHelper.TABLE_COUNTRIES + " (" +
                            DatabaseHelper.COLUMN_COUNTRY_NAME + ", " +
                            DatabaseHelper.COLUMN_CONTINENT + ") VALUES (?, ?)";
                    db.execSQL(sql, new Object[]{country, continent});
                } // if

            } // while
            reader.close();
            Log.d("AsyncCSVLoader", "CSV data inserted successfully.");

        } catch (Exception e) {
            Log.e("AsyncCSVLoader", "Error reading CSV", e);
        } finally {
            db.close();
        } // try-catch

        return null;
    } // doInBackground

    /**
     * Checks whether the database is already populated with country data.
     *
     * @return true if the database contains country data, false otherwise.
     */
    private boolean isDatabasePopulated() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_COUNTRIES, null);
        boolean populated = false;

        if (cursor != null && cursor.moveToFirst()) {
            populated = cursor.getInt(0) > 0;
        } // if

        if (cursor != null) {
            cursor.close();
        } // if

        db.close();
        return populated;
    } // isDatabasePopulated

} // AsyncCSVLoader