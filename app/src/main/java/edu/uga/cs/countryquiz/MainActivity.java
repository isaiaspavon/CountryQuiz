package edu.uga.cs.countryquiz;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

import edu.uga.cs.countryquiz.models.Country;

public class MainActivity extends AppCompatActivity {

    private List<Country> quizCountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Set the padding for the layout based on system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the quiz countries from the database
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
        quizCountries = dbHelper.getRandomCountries(6); // Adjust the number as per your requirement

        // Set up ViewPager2 to manage swiping between quiz questions
        ViewPager2 pager = findViewById(R.id.viewpager);
        QuizAdapter quizAdapter = new QuizAdapter(this, quizCountries);
        pager.setAdapter(quizAdapter);
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        // Load the splash screen and transition to the quiz after
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SplashFragment())
                    .commit();
        }
    }

    // Method to hide the splash screen and show the ViewPager2
    public void startQuiz() {
        findViewById(R.id.viewpager).setVisibility(View.VISIBLE);
        findViewById(R.id.fragment_container).setVisibility(View.GONE);
    }


}
