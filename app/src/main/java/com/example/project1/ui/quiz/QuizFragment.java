package com.example.project1.ui.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;
import com.example.project1.ui.quiz.QuizAdapter;
import com.example.project1.model.QuizQuestion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class QuizFragment extends Fragment {

    private static final String SELECTED_CATEGORY = "Βασικές Γνώσεις";  // Μπορεί να γίνει δυναμικό στο μέλλον

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.quizRecyclerView);
        TextView categoryTitle = view.findViewById(R.id.categoryTitle);
        categoryTitle.setText("Κατηγορία: " + SELECTED_CATEGORY);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<QuizQuestion> questions = loadQuizData();
        List<QuizQuestion> filtered = new ArrayList<>();
        for (QuizQuestion q : questions) {
            if (q.getCategory().equalsIgnoreCase(SELECTED_CATEGORY)) {
                filtered.add(q);
            }
        }

        recyclerView.setAdapter(new QuizAdapter(filtered));
        return view;
    }

    private List<QuizQuestion> loadQuizData() {
        try {
            InputStream is = getContext().getAssets().open("quiz_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, StandardCharsets.UTF_8);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<QuizQuestion>>() {}.getType();
            return gson.fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
