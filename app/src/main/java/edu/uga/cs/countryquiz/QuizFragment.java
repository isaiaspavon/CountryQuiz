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

public class QuizFragment extends Fragment {

    private TextView questionText;
    private RadioGroup choicesGroup;
    private Button nextButton;
    private List<Country> quizCountries;
    private String correctAnswer;
    private int currentQuestionIndex = 0;
    private QuizManager quizManager;
    private DatabaseHelper dbHelper;

    public QuizFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        questionText = view.findViewById(R.id.question_text);
        choicesGroup = view.findViewById(R.id.choices_group);
        nextButton = view.findViewById(R.id.next_button);

        dbHelper = DatabaseHelper.getInstance(getActivity());
        quizCountries = dbHelper.getRandomCountries(6);
        quizManager = new QuizManager(getActivity(), quizCountries.size());

        displayQuestion();

        nextButton.setOnClickListener(v -> checkAnswerAndProceed());

        return view;
    }

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
            }
        } else {
            endQuiz();
        }
    }

    private void checkAnswerAndProceed() {
        int selectedId = choicesGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(getActivity(), "Select an answer!", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedButton = choicesGroup.findViewById(selectedId);
        String selectedAnswer = selectedButton.getText().toString();

        boolean isCorrect = selectedAnswer.equals(correctAnswer);
        quizManager.updateScore(isCorrect);

        currentQuestionIndex++;
        displayQuestion();
    }

    private void endQuiz() {
        quizManager.saveQuizResult();
        Toast.makeText(getActivity(), "Quiz Over! Score: " + quizManager.getScore() + "/" + quizCountries.size(), Toast.LENGTH_LONG).show();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new ResultFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private List<String> getRandomChoices(String correctAnswer) {
        List<String> choices = new ArrayList<>();
        choices.add(correctAnswer);

        // Get all distinct continents from the database
        List<String> allContinents = quizManager.getAllContinents();
        Collections.shuffle(allContinents);

        for (String continent : allContinents) {
            if (!choices.contains(continent) && choices.size() < 4) {
                choices.add(continent);
            }
        }

        Collections.shuffle(choices);
        return choices;
    }
}
