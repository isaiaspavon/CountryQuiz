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

/**
 * Manages quiz logic, including scoring, tracking progress, and storing quiz results in the database.
 */
public class QuizManager {

    private DatabaseHelper dbHelper;
    private int score = 0;
    private int questionsAnswered = 0;
    private int totalQuestions;

    /**
     * Constructs a QuizManager to manage quiz progress and database operations.
     *
     * @param context The application context.
     * @param totalQuestions The total number of questions in the quiz.
     */
    public QuizManager(Context context, int totalQuestions) {
        this.dbHelper = DatabaseHelper.getInstance(context);
        this.totalQuestions = totalQuestions;
    } // quizManager

    /**
     * Updates the score based on whether the user's answer is correct.
     *
     * @param isCorrect True if the answer is correct, false otherwise.
     */
    public void updateScore(boolean isCorrect) {
        if (isCorrect) {
            score++;
        } // if
        questionsAnswered++;
    } // updateScore

    /**
     * Returns the current score of the quiz.
     *
     * @return The number of correct answers.
     */
    public int getScore() {
        return score;
    } // getScore

    /**
     * Returns the number of questions answered so far (unused).
     *
     * @return The number of answered questions.
     */
    public int getQuestionsAnswered() {
        return questionsAnswered;
    } // getQuestionsAnswered

    /**
     * Checks if the quiz is complete (unused).
     *
     * @return True if all questions have been answered, false otherwise.
     */
    public boolean isQuizComplete() {
        return questionsAnswered >= totalQuestions;
    } // isQuizComplete

    /**
     * Saves the quiz result (date and score) into the database.
     */
    public void saveQuizResult() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        values.put(DatabaseHelper.COLUMN_DATE, currentDate);
        values.put(DatabaseHelper.COLUMN_SCORE, score);

        db.insert(DatabaseHelper.TABLE_QUIZ_RESULTS, null, values);
        db.close();
    } // saveQuizResult

    /**
     * Retrieves past quiz results from the database.
     *
     * @return A list of past quiz results, where each entry contains the date and score.
     */
    public List<String[]> getPastQuizResults() {
        List<String[]> results = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_QUIZ_RESULTS, null);
        while (cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));
            int score = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SCORE));
            results.add(new String[] {date, String.valueOf(score)});
        } // while

        cursor.close();
        return results;
    } // getPastQuizResults

    /**
     * Retrieves a distinct list of all available continents from the database.
     *
     * @return A list of unique continent names.
     */
    public List<String> getAllContinents() {
        List<String> continents = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT DISTINCT continent FROM countries", null);
        while (cursor.moveToNext()) {
            continents.add(cursor.getString(cursor.getColumnIndexOrThrow("continent")));
        } // while

        cursor.close();
        return continents;
    } // getAllContinents

} // QuizManager

