package edu.uga.cs.countryquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A fragment that displays past quiz results in a ListView.
 */
public class ResultFragment extends Fragment {

    private ListView resultsListView;
    private QuizManager quizManager;

    /**
     * Default constructor (required for fragments).
     */
    public ResultFragment() {
        // Required empty public constructor
    } // ResultFragment

    /**
     * Called when the fragment is created.
     * Initializes the QuizManager with a context and sets the total number of questions to 0,
     * as this fragment is only concerned with displaying past quiz results.
     *
     * @param savedInstanceState The saved instance state, or null if this is a new instance.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quizManager = new QuizManager(getActivity(), 0);  // Passing 0 since we aren't concerned with total questions here
    } // onCreate

    /**
     * Inflates the fragment layout and sets up the ListView to display past quiz results.
     * It retrieves past results from the QuizManager, formats the data, and uses a SimpleAdapter
     * to bind the results to the ListView for display.
     *
     * @param inflater The LayoutInflater object used to inflate the view.
     * @param container The parent view that this fragment's UI will be attached to.
     * @param savedInstanceState A bundle containing the fragment's state, or null if none.
     * @return The root view of the fragment's layout.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        // Set up the ListView to show past quiz results
        resultsListView = view.findViewById(R.id.resultsListView);

        // Get past quiz results from QuizManager
        List<String[]> pastResults = quizManager.getPastQuizResults();

        // Prepare data for the ListView
        List<HashMap<String, String>> data = new ArrayList<>();
        for (String[] result : pastResults) {
            HashMap<String, String> map = new HashMap<>();
            map.put("date", result[0]);  // Date
            map.put("score", result[1]);  // Score
            data.add(map);
        } // for

        // Use SimpleAdapter to bind data to ListView
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), data,
                R.layout.result_item,  // You need to create this layout for each row
                new String[] {"date", "score"},
                new int[] {R.id.result_date, R.id.result_score});  // TextView IDs in result_item layout

        resultsListView.setAdapter(adapter);

        return view;
    } // onCreateView

} // ResultFragment