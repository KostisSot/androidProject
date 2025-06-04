package com.example.project1.ui.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.quizRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String category = getArguments() != null ? getArguments().getString("category", "") : "";
        List<QuizQuestion> questions = loadQuizData(category);
        recyclerView.setAdapter(new QuizAdapter(questions));

        return view;
    }

    private List<QuizQuestion> loadQuizData(String selectedCategory) {
        try {
            InputStream is = getContext().getAssets().open("quiz_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, StandardCharsets.UTF_8);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<QuizQuestion>>() {}.getType();
            List<QuizQuestion> allQuestions = gson.fromJson(json, listType);

            List<QuizQuestion> filteredQuestions = new ArrayList<>();
            for (QuizQuestion q : allQuestions) {
                if (q.getCategory().equalsIgnoreCase(selectedCategory)) {
                    filteredQuestions.add(q);
                }
            }
            return filteredQuestions;

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

