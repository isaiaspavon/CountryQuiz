package edu.uga.cs.countryquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QuizManager {

    private DatabaseHelper dbHelper;
    private int score = 0;
    private int questionsAnswered = 0;
    private int totalQuestions;

    public QuizManager(Context context, int totalQuestions) {
        this.dbHelper = DatabaseHelper.getInstance(context);
        this.totalQuestions = totalQuestions;
    }

    public void updateScore(boolean isCorrect) {
        if (isCorrect) {
            score++;
        }
        questionsAnswered++;
    }

    public int getScore() {
        return score;
    }

    public int getQuestionsAnswered() {
        return questionsAnswered;
    }

    public boolean isQuizComplete() {
        return questionsAnswered >= totalQuestions;
    }

    public void saveQuizResult() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        values.put(DatabaseHelper.COLUMN_DATE, currentDate);
        values.put(DatabaseHelper.COLUMN_SCORE, score);

        db.insert(DatabaseHelper.TABLE_QUIZ_RESULTS, null, values);
        db.close();
    }

    public List<String> getPastQuizResults() {
        List<String> results = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_QUIZ_RESULTS, null);
        while (cursor.moveToNext()) {
            int score = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SCORE));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));
            results.add("Score: " + score + " | Date: " + date);
        }

        cursor.close();
        return results;
    }
}

