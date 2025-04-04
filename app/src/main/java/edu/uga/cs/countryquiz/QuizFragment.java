package edu.uga.cs.countryquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.uga.cs.countryquiz.models.Country;

/**
 * QuizFragment represents a single question in the quiz.
 * It displays a country name and multiple-choice options for selecting the correct continent.
 */
public class QuizFragment extends Fragment {

    private TextView questionText;
    private RadioGroup choicesGroup;
    private Button nextButton;
    private List<Country> quizCountries;
    private String correctAnswer;
    private int currentQuestionIndex = 0;
    private QuizManager quizManager;
    private DatabaseHelper dbHelper;

    /**
     * Default constructor (required for Fragments).
     */
    public QuizFragment() {
        // Required empty constructor
    } // QuizFragment

    /**
     * Creates a new instance of QuizFragment with a specified question index (no usages).
     *
     * @param questionIndex The index of the question to be displayed.
     * @return A new QuizFragment instance.
     */
    public static QuizFragment newInstance(int questionIndex) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putInt("questionIndex", questionIndex);
        fragment.setArguments(args);
        return fragment;
    } // QuizFragment

    /**
     * Initializes the quiz UI and sets up event listeners.
     *
     * @param inflater The LayoutInflater object that can be used to inflate views.
     * @param container The parent view that this fragment will be attached to.
     * @param savedInstanceState The saved state of the fragment.
     * @return The root View of the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        questionText = view.findViewById(R.id.question_text);
        choicesGroup = view.findViewById(R.id.choices_group);
        nextButton = view.findViewById(R.id.next_button);

        dbHelper = DatabaseHelper.getInstance(getActivity());
        quizCountries = dbHelper.getRandomCountries(6);
        quizManager = new QuizManager(getActivity(), quizCountries.size());

        if (getArguments() != null) {
            currentQuestionIndex = getArguments().getInt("questionIndex", 0);
        } // if

        displayQuestion();

        nextButton.setOnClickListener(v -> checkAnswerAndProceed());

        return view;
    } // onCreateView

    /**
     * Displays the current question and its answer choices.
     */
    private void displayQuestion() {
        if (currentQuestionIndex < quizCountries.size()) {
            Country currentCountry = quizCountries.get(currentQuestionIndex);
            correctAnswer = currentCountry.getContinent();
            questionText.setText("Which continent does " + currentCountry.getName() + " belong to?");

            choicesGroup.removeAllViews();
            for (String choice : getRandomChoices(correctAnswer)) {
                RadioButton radioButton = new RadioButton(getActivity());
                radioButton.setText(choice);
                choicesGroup.addView(radioButton);
            } // for
        } else {
            endQuiz();
        } // if

    } // displayQuestion

    /**
     * Checks the user's answer, updates the score, and proceeds to the next question.
     */
    private void checkAnswerAndProceed() {
        int selectedId = choicesGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(getActivity(), "Select an answer!", Toast.LENGTH_SHORT).show();
            return;
        } // if

        RadioButton selectedButton = choicesGroup.findViewById(selectedId);
        String selectedAnswer = selectedButton.getText().toString();

        boolean isCorrect = selectedAnswer.equals(correctAnswer);
        quizManager.updateScore(isCorrect);

        currentQuestionIndex++;
        displayQuestion();
    } // checkAnswerAndProceed

    /**
     * Ends the quiz, saves the result, and transitions to the results page.
     */
    private void endQuiz() {
        quizManager.saveQuizResult();
        Toast.makeText(getActivity(), "Quiz Over! Score: " + quizManager.getScore() + "/" + quizCountries.size(), Toast.LENGTH_LONG).show();

        // Make sure fragment container is visible
        getActivity().findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
        // Hide ViewPager2
        getActivity().findViewById(R.id.viewpager).setVisibility(View.GONE);

        // Transition to results page
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new ResultFragment());
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    } // endQuiz

    /**
     * Generates a list of multiple-choice answers, including the correct answer.
     *
     * @param correctAnswer The correct continent answer.
     * @return A shuffled list of possible answer choices.
     */
    private List<String> getRandomChoices(String correctAnswer) {
        List<String> choices = new ArrayList<>();
        choices.add(correctAnswer);

        // Get all distinct continents from the database
        List<String> allContinents = quizManager.getAllContinents();
        Collections.shuffle(allContinents);

        for (String continent : allContinents) {
            if (!choices.contains(continent) && choices.size() < 4) {
                choices.add(continent);
            } // if
        } // for

        Collections.shuffle(choices);
        return choices;
    } // getRandomChoices

} // QuizFragment