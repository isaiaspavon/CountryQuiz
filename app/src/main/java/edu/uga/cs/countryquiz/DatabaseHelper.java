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

    // Table names
    public static final String TABLE_COUNTRIES = "countries";
    public static final String TABLE_QUIZ_RESULTS = "quiz_results";

    // Column names for countries table
    public static final String COLUMN_COUNTRY_ID = "id";
    public static final String COLUMN_COUNTRY_NAME = "name";
    public static final String COLUMN_CONTINENT = "continent";

    // Column names for quiz_results table
    public static final String COLUMN_QUIZ_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_SCORE = "score";

    // Table creation queries
    private static final String CREATE_TABLE_COUNTRIES =
            "CREATE TABLE IF NOT EXISTS " + TABLE_COUNTRIES + " (" +
                    COLUMN_COUNTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_COUNTRY_NAME + " TEXT, " +
                    COLUMN_CONTINENT + " TEXT);";

    private static final String CREATE_TABLE_QUIZ_RESULTS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_QUIZ_RESULTS + " (" +
                    COLUMN_QUIZ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_SCORE + " INTEGER);";

    // Private constructor to prevent direct instantiation
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Singleton pattern: Get instance
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_COUNTRIES);
        db.execSQL(CREATE_TABLE_QUIZ_RESULTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ_RESULTS);
        onCreate(db);
    }

    // Get random countries for quizzes
    public List<Country> getRandomCountries(int count) {
        List<Country> countryList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_COUNTRIES + " ORDER BY RANDOM() LIMIT ?", new String[]{String.valueOf(count)});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COUNTRY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COUNTRY_NAME));
                String continent = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTINENT));
                countryList.add(new Country(id, name, continent));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return countryList;
    }
}
