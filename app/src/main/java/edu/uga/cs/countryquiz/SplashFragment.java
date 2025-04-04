package edu.uga.cs.countryquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * SplashFragment displays the introductory screen with buttons for starting the quiz or viewing results.
 * It handles the navigation to either the QuizFragment or ResultFragment based on user interaction.
 */
public class SplashFragment extends Fragment {

    /**
     * Default constructor for the SplashFragment.
     * Required empty public constructor.
     */
    public SplashFragment() {
        // Required empty public constructor
    }

    /**
     * Inflates the layout for the SplashFragment and sets up the button listeners for user interaction.
     * It provides the functionality to start the quiz or view the past quiz results.
     *
     * @param inflater The LayoutInflater object used to inflate the view.
     * @param container The parent view that this fragment's UI will be attached to.
     * @param savedInstanceState A bundle containing the fragment's state, or null if none.
     * @return The root view of the fragment's layout.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_splash, container, false);


        Button startQuizButton = view.findViewById(R.id.start_quiz_button);
        Button viewResultsButton = view.findViewById(R.id.view_results_button);


        startQuizButton.setOnClickListener(v -> {

            if (getActivity() != null) {
                ((MainActivity) getActivity()).startQuiz();
            } // if
        });

        viewResultsButton.setOnClickListener(v -> {

            transitionToResultsFragment();
        });

        return view;
    } // onCreateView

    /**
     * Replaces the SplashFragment with the QuizFragment when transitioning to the quiz (unused).
     * This method uses a FragmentTransaction to replace the fragment and add it to the back stack.
     */
    private void transitionToQuizFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new QuizFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    } // transitionToQuizFragment

    /**
     * Replaces the SplashFragment with the ResultFragment when transitioning to the results screen.
     * This method uses a FragmentTransaction to replace the fragment and add it to the back stack.
     */
    private void transitionToResultsFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new ResultFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    } // transitionToResultsFragment

} // SplashFragment