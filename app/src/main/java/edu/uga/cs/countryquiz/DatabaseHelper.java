package edu.uga.cs.countryquiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.countryquiz.models.Country;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "country_quiz.db";
    private static final int DATABASE_VERSION = 1;

    private static DatabaseHelper instance;

    // Table creation queries
    private static final String CREATE_TABLE_COUNTRIES =
            "CREATE TABLE IF NOT EXISTS countries (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, continent TEXT);";

    private static final String CREATE_TABLE_QUIZ_RESULTS =
            "CREATE TABLE IF NOT EXISTS quiz_results (id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, score INTEGER);";

    // Private constructor to prevent direct instantiation
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    } // DatabaseHelper

    // Singleton pattern: Get instance
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        } // if
        return instance;
    } // DatabaseHelper

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_COUNTRIES);
        db.execSQL(CREATE_TABLE_QUIZ_RESULTS);
    } // onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS countries");
        db.execSQL("DROP TABLE IF EXISTS quiz_results");
        onCreate(db);
    } // onUpgrade

    // Method to initialize the database (called from SplashFragment)
    public void initializeDatabase() {
        SQLiteDatabase db = this.getWritableDatabase(); // This ensures the tables are created
    } // initializeDatabase


    public List<Country> getRandomCountries(int count) {
        List<Country> countryList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM countries ORDER BY RANDOM() LIMIT ?", new String[]{String.valueOf(count)});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String continent = cursor.getString(cursor.getColumnIndexOrThrow("continent"));
                countryList.add(new Country(id, name, continent));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return countryList;
    } // getRandomCountries

} // DatabaseHelper