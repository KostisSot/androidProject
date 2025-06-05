package com.example.project1.ui.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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

    private String selectedCategory = "ΚΑΡΠΑ"; // Default in case none passed
    private List<QuizQuestion> filteredQuestions;
    private int currentIndex = 0;

    private TextView categoryTitle, questionText, explanationText;
    private Button trueBtn, falseBtn, nextBtn, prevBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        categoryTitle = view.findViewById(R.id.categoryTitle);
        questionText = view.findViewById(R.id.questionText);
        explanationText = view.findViewById(R.id.explanationText);
        trueBtn = view.findViewById(R.id.trueBtn);
        falseBtn = view.findViewById(R.id.falseBtn);
        nextBtn = view.findViewById(R.id.nextBtn);
        prevBtn = view.findViewById(R.id.prevBtn);

        // Get category from arguments
        if (getArguments() != null) {
            selectedCategory = getArguments().getString("category", "ΚΑΡΠΑ");
        }

        categoryTitle.setText("Κατηγορία: " + selectedCategory);

        List<QuizQuestion> questions = loadQuizData();
        filteredQuestions = new ArrayList<>();
        for (QuizQuestion q : questions) {
            if (q.getCategory().equalsIgnoreCase(selectedCategory)) {
                filteredQuestions.add(q);
            }
        }

        displayQuestion();

        trueBtn.setOnClickListener(v -> handleAnswer(true));
        falseBtn.setOnClickListener(v -> handleAnswer(false));

        nextBtn.setOnClickListener(v -> {
            if (currentIndex < filteredQuestions.size() - 1) {
                currentIndex++;
                displayQuestion();
            } else {
                Toast.makeText(getContext(), "Τέλος ερωτήσεων.", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(v).navigate(R.id.categoryFragment);
            }
        });

        prevBtn.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                displayQuestion();
            }
        });

        return view;
    }

    private void displayQuestion() {
        QuizQuestion question = filteredQuestions.get(currentIndex);
        questionText.setText(question.getQuestion());
        explanationText.setText(question.getExplanation());
        explanationText.setVisibility(View.GONE);

        trueBtn.setEnabled(true);
        falseBtn.setEnabled(true);
        trueBtn.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        falseBtn.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        prevBtn.setEnabled(currentIndex > 0);
        nextBtn.setEnabled(true); // Ενεργό πάντα, ώστε να εμφανίζει toast
    }

    private void handleAnswer(boolean userAnswer) {
        QuizQuestion question = filteredQuestions.get(currentIndex);
        boolean correct = userAnswer == question.isCorrectAnswer();

        if (question.isCorrectAnswer()) {
            trueBtn.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            falseBtn.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        } else {
            trueBtn.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            falseBtn.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        }

        explanationText.setVisibility(View.VISIBLE);
        trueBtn.setEnabled(false);
        falseBtn.setEnabled(false);
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



