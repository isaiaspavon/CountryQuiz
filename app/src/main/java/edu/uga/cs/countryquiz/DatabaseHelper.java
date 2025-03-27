package edu.uga.cs.countryquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "country_quiz.db";
    private static final int DATABASE_VERSION = 1;

    // Table: countries
    public static final String TABLE_COUNTRIES = "countries";
    public static final String COLUMN_COUNTRY_ID = "id";
    public static final String COLUMN_COUNTRY_NAME = "name";
    public static final String COLUMN_CONTINENT = "continent";

    // Table: quiz_results
    public static final String TABLE_QUIZ_RESULTS = "quiz_results";
    public static final String COLUMN_QUIZ_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_SCORE = "score";

    // SQL to create the "countries" table
    private static final String CREATE_COUNTRIES_TABLE =
            "CREATE TABLE " + TABLE_COUNTRIES + " (" +
                    COLUMN_COUNTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_COUNTRY_NAME + " TEXT NOT NULL, " +
                    COLUMN_CONTINENT + " TEXT NOT NULL);";

    // SQL to create the "quiz_results" table
    private static final String CREATE_QUIZ_RESULTS_TABLE =
            "CREATE TABLE " + TABLE_QUIZ_RESULTS + " (" +
                    COLUMN_QUIZ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DATE + " TEXT NOT NULL, " +
                    COLUMN_SCORE + " INTEGER NOT NULL);";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the tables when the database is first initialized
        db.execSQL(CREATE_COUNTRIES_TABLE);
        db.execSQL(CREATE_QUIZ_RESULTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // If database schema changes, drop old tables and recreate
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ_RESULTS);
        onCreate(db);
    }

    // Check if database exists to prevent re-creating it
    public boolean doesDatabaseExist(Context context) {
        SQLiteDatabase checkDB = null;
        try {
            String path = context.getDatabasePath(DATABASE_NAME).getPath();
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {
            return false;  // Database does not exist
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return true;  // Database exists
    } // doesDatabaseExist

} // DatabaseHelper
