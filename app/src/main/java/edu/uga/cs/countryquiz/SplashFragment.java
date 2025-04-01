package edu.uga.cs.countryquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class SplashFragment extends Fragment {

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        // Find buttons
        Button startQuizButton = view.findViewById(R.id.start_quiz_button);
        Button viewResultsButton = view.findViewById(R.id.view_results_button);

        // Set button listeners
        startQuizButton.setOnClickListener(v -> {
            // Transition to the QuizFragment when "Start Quiz" is clicked
            if (getActivity() != null) {
                ((MainActivity) getActivity()).startQuiz();
            }
        });

        viewResultsButton.setOnClickListener(v -> {
            // Optionally, transition to a ResultsFragment
            transitionToResultsFragment();
        });

        return view;
    }

    private void transitionToQuizFragment() {
        // Replace SplashFragment with QuizFragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new QuizFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void transitionToResultsFragment() {
        // Replace SplashFragment with ResultFragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new ResultFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
