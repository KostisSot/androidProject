package com.example.project1.ui.quiz;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import java.util.HashMap;
import java.util.List;

/**
 * Displays true/false quiz questions filtered by category, tracks user answers, and navigates to a results screen upon completion.
 * It loads data from a JSON file and uses a custom font for consistent UI styling.
 * @author dimitrasa
 */
public class QuizFragment extends Fragment {

    private String selectedCategory = "ΚΑΡΠΑ";
    private List<QuizQuestion> filteredQuestions;
    private int currentIndex = 0;

    private TextView categoryTitle, questionText, explanationText;
    private Button trueBtn, falseBtn, nextBtn, prevBtn;

    private QuizViewModel quizViewModel;
    private Typeface customFont;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);


        customFont = ResourcesCompat.getFont(requireContext(), R.font.tektur);

        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        categoryTitle = view.findViewById(R.id.categoryTitle);
        questionText = view.findViewById(R.id.questionText);
        explanationText = view.findViewById(R.id.explanationText);
        trueBtn = view.findViewById(R.id.trueBtn);
        falseBtn = view.findViewById(R.id.falseBtn);
        nextBtn = view.findViewById(R.id.nextBtn);
        prevBtn = view.findViewById(R.id.prevBtn);


        applyFont();

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
                Bundle bundle = new Bundle();
                bundle.putSerializable("questions", new ArrayList<>(filteredQuestions));
                bundle.putSerializable("answers", new HashMap<>(quizViewModel.getAllAnswers()));
                Navigation.findNavController(v).navigate(R.id.resultFragment, bundle);
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

    private void applyFont() {
        if (customFont != null) {
            categoryTitle.setTypeface(customFont);
            questionText.setTypeface(customFont);
            explanationText.setTypeface(customFont);
            trueBtn.setTypeface(customFont);
            falseBtn.setTypeface(customFont);
            nextBtn.setTypeface(customFont);
            prevBtn.setTypeface(customFont);
        }
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

        Boolean userAnswer = quizViewModel.getUserAnswer(currentIndex);
        if (userAnswer != null) {
            showAnswer(userAnswer);
        }
    }

    private void handleAnswer(boolean userAnswer) {
        QuizQuestion question = filteredQuestions.get(currentIndex);
        quizViewModel.setUserAnswer(currentIndex, userAnswer, question.isCorrectAnswer());
        showAnswer(userAnswer);
    }

    private void showAnswer(boolean userAnswer) {
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








