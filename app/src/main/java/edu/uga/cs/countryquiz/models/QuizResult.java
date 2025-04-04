package edu.uga.cs.countryquiz.models;

/**
 * Represents a quiz result with an ID, date, and score (POJO).
 *
 * <p>Note: This class and its methods were not used in the final implementation.</p>
 */
public class QuizResult {
    private int id;
    private String date;
    private int score;

    /**
     * Constructs a QuizResult object with the specified ID, date, and score.
     *
     * @param id    The unique identifier of the quiz result.
     * @param date  The date when the quiz was taken.
     * @param score The score achieved in the quiz.
     */
    public QuizResult(int id, String date, int score) {
        this.id = id;
        this.date = date;
        this.score = score;
    } // QuizResult

    /**
     * Gets the unique identifier of the quiz result.
     *
     * @return The quiz result ID.
     */
    public int getId() {
        return id;
    } // getId

    /**
     * Gets the date when the quiz was taken.
     *
     * @return The quiz date.
     */
    public String getDate() {
        return date;
    } // getDate

    /**
     * Gets the score achieved in the quiz.
     *
     * @return The quiz score.
     */
    public int getScore() {
        return score;
    } // getScore

} // QuizResults