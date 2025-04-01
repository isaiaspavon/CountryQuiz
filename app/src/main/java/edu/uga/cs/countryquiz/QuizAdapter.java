package edu.uga.cs.countryquiz;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;
import edu.uga.cs.countryquiz.models.Country;

public class QuizAdapter extends FragmentStateAdapter {

    private List<Country> quizCountries;

    public QuizAdapter(MainActivity activity, List<Country> quizCountries) {
        super(activity);
        this.quizCountries = quizCountries;
    }

    @Override
    public Fragment createFragment(int position) {
        // Create a new QuizFragment instance for the current position (question index)
        Bundle args = new Bundle();
        args.putInt("questionIndex", position);
        QuizFragment fragment = new QuizFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return quizCountries.size();
    }
}
