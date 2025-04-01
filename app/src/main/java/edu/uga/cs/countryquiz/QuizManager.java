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

import edu.uga.cs.countryquiz.models.QuizResult;

public class QuizManager {

    private DatabaseHelper dbHelper;
    private int score = 0;
    private int questionsAnswered = 0;
    private int totalQuestions;

    public QuizManager(Context context, int totalQuestions) {
        this.dbHelper = DatabaseHelper.getInstance(context);
        this.totalQuestions = totalQuestions;
    } // quizManager

    public void updateScore(boolean isCorrect) {
        if (isCorrect) {
            score++;
        } // if
        questionsAnswered++;
    } // updateScore

    public int getScore() {
        return score;
    } // getScore

    public int getQuestionsAnswered() {
        return questionsAnswered;
    } // getQuestionsAnswered

    public boolean isQuizComplete() {
        return questionsAnswered >= totalQuestions;
    } // isQuizComplete

    public void saveQuizResult() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        values.put(DatabaseHelper.COLUMN_DATE, currentDate);
        values.put(DatabaseHelper.COLUMN_SCORE, score);

        db.insert(DatabaseHelper.TABLE_QUIZ_RESULTS, null, values);
        db.close();
    } // saveQuizResult


    public List<QuizResult> getPastQuizResults() {
        List<QuizResult> results = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_QUIZ_RESULTS, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_QUIZ_ID));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));
            int score = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SCORE));
            results.add(new QuizResult(id, date, score));
        }

        cursor.close();
        return results;
    } // getPastQuizResult

    public List<String> getAllContinents() {
        List<String> continents = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT DISTINCT continent FROM countries", null);
        while (cursor.moveToNext()) {
            continents.add(cursor.getString(cursor.getColumnIndexOrThrow("continent")));
        }

        cursor.close();
        return continents;
    }

} // QuizManager

