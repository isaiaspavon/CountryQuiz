package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.uga.cs.countryquiz.models.Country;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment {

    private List<Country> quizCountries; // Stores selected 6 countries
    private int currentQuestionIndex = 0; // Tracks current question
    private int score = 0; // Tracks score
    private String correctAnswer = ""; // Stores correct answer for current question

    private TextView questionTextView;
    private RadioGroup choicesGroup;
    private Button nextButton;

    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        // Initialize UI components
        questionTextView = view.findViewById(R.id.question_text);
        choicesGroup = view.findViewById(R.id.choices_group);
        nextButton = view.findViewById(R.id.next_button);

        // Load 6 random countries from the database
        dbHelper = new DatabaseHelper(getActivity());
        quizCountries = dbHelper.getRandomCountries(6);

        // Display the first question
        displayQuestion();

        // Set next button click listener
        nextButton.setOnClickListener(v -> checkAnswerAndProceed());

        return view;
    }

    private void displayQuestion() {
        if (currentQuestionIndex >= quizCountries.size()) {
            endQuiz();
            return;
        }

        Country currentCountry = quizCountries.get(currentQuestionIndex);
        correctAnswer = currentCountry.getContinent(); // Store correct continent

        // Generate random choices (1 correct + 2 random wrong ones)
        List<String> choices = generateChoices(correctAnswer);

        // Update UI
        questionTextView.setText("Which continent is " + currentCountry.getName() + " located in?");
        choicesGroup.removeAllViews();

        for (String choice : choices) {
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setText(choice);
            choicesGroup.addView(radioButton);
        }
    }

    private List<String> generateChoices(String correctContinent) {
        List<String> allContinents = Arrays.asList("Africa", "Asia", "Europe", "North America", "South America", "Oceania", "Antarctica");
        List<String> choices = new ArrayList<>();

        choices.add(correctContinent); // Add the correct answer

        // Randomly pick two other wrong answers
        Random random = new Random();
        while (choices.size() < 3) {
            String randomContinent = allContinents.get(random.nextInt(allContinents.size()));
            if (!choices.contains(randomContinent)) {
                choices.add(randomContinent);
            }
        }

        Collections.shuffle(choices); // Randomize order
        return choices;
    }

    private void checkAnswerAndProceed() {
        int selectedId = choicesGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(getActivity(), "Select an answer!", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedButton = choicesGroup.findViewById(selectedId);
        String selectedAnswer = selectedButton.getText().toString();

        if (selectedAnswer.equals(correctAnswer)) {
            score++; // Increment score if correct
        }

        currentQuestionIndex++; // Move to the next question
        displayQuestion();
    }

    private void endQuiz() {
        Toast.makeText(getActivity(), "Quiz Over! Score: " + score + "/" + quizCountries.size(), Toast.LENGTH_LONG).show();

        // Navigate to result screen
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new ResultFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
