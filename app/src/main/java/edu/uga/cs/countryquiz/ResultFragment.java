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

public class ResultFragment extends Fragment {

    private ListView resultsListView;
    private QuizManager quizManager;

    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quizManager = new QuizManager(getActivity(), 0);  // Passing 0 since we aren't concerned with total questions here
    }

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
        }

        // Use SimpleAdapter to bind data to ListView
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), data,
                R.layout.result_item,  // You need to create this layout for each row
                new String[] {"date", "score"},
                new int[] {R.id.result_date, R.id.result_score});  // TextView IDs in result_item layout

        resultsListView.setAdapter(adapter);

        return view;
    }
}
