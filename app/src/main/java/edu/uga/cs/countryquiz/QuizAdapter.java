package edu.uga.cs.countryquiz;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;
import edu.uga.cs.countryquiz.models.Country;

/**
 * QuizAdapter is a ViewPager2 adapter that manages the quiz questions.
 * It creates a new QuizFragment for each question in the quiz.
 */
public class QuizAdapter extends FragmentStateAdapter {

    private List<Country> quizCountries;

    /**
     * Constructs a QuizAdapter.
     *
     * @param activity The MainActivity that hosts the ViewPager2.
     * @param quizCountries The list of Country objects used for the quiz.
     */
    public QuizAdapter(MainActivity activity, List<Country> quizCountries) {
        super(activity);
        this.quizCountries = quizCountries;
    } // QuizAdapter

    /**
     * Creates a QuizFragment for the given position (question index).
     *
     * @param position The index of the question.
     * @return A new instance of QuizFragment with the question index set as an argument.
     */
    @Override
    public Fragment createFragment(int position) {
        // Create a new QuizFragment instance for the current position (question index)
        Bundle args = new Bundle();
        args.putInt("questionIndex", position);
        QuizFragment fragment = new QuizFragment();
        fragment.setArguments(args);
        return fragment;
    } // createFragment

    /**
     * Returns the total number of questions in the quiz.
     *
     * @return The number of quiz questions.
     */
    @Override
    public int getItemCount() {
        return quizCountries.size();
    } // getItemCount

} // QuizAdapter
