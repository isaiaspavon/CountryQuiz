package edu.uga.cs.countryquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
} // DatabaseHelper