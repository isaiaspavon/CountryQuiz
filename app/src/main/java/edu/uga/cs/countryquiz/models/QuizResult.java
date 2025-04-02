package edu.uga.cs.countryquiz.models;

public class QuizResult {
    private int id;
    private String date;
    private int score;

    public QuizResult(int id, String date, int score) {
        this.id = id;
        this.date = date;
        this.score = score;
    } // QuizResult

    public int getId() {
        return id;
    } // getId

    public String getDate() {
        return date;
    } // getDate

    public int getScore() {
        return score;
    } // getScore
} // QuizResults