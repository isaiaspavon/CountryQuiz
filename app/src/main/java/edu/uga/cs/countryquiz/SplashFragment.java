package edu.uga.cs.countryquiz;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

        // Load SQLite database in the background (only if needed)
        new LoadDatabaseTask(getActivity()).execute();

        // Set button listeners
        startQuizButton.setOnClickListener(v -> {
            // Replace SplashFragment with QuizFragment
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new QuizFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        viewResultsButton.setOnClickListener(v -> {
            // Replace SplashFragment with ResultFragment
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new ResultFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }

    // AsyncTask to load SQLite database in the background
    private class LoadDatabaseTask extends AsyncTask<Void, Void, Void> {
        private Context context;

        public LoadDatabaseTask(Context context) {
            this.context = context;
        } // LoadDatabaseTask

        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseHelper db = DatabaseHelper.getInstance(context);
            db.initializeDatabase();
            return null;
        } // doInBackground
    } // LoadDatabaseTask



} // SplashFragment
