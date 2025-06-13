package com.example.project1.ui.quiz;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.project1.R;
import com.example.project1.model.QuizQuestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dynamically generates a detailed quiz summary using cards, displaying each question, the user's answer, the correct answer, and an explanation, along with the total score and a return button to the category selection.
 * @author dimitrasa
 * @noinspection ALL
 */
public class ResultFragment extends Fragment {

    private List<QuizQuestion> questions = new ArrayList<>();
    private Map<Integer, Boolean> userAnswers = new HashMap<>();

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_result, container, false);
        LinearLayout containerLayout = view.findViewById(R.id.resultsContainer);


        Typeface customFont = ResourcesCompat.getFont(requireContext(), R.font.tektur);

        if (getArguments() != null) {
            questions = (List<QuizQuestion>) getArguments().getSerializable("questions");
            userAnswers = (Map<Integer, Boolean>) getArguments().getSerializable("answers");
        }

        int score = 0;

        for (int i = 0; i < questions.size(); i++) {
            QuizQuestion question = questions.get(i);
            Boolean userAnswer = userAnswers.get(i);

            boolean correct = userAnswer != null && userAnswer == question.isCorrectAnswer();
            if (correct) score++;

            // Δημιουργία κάρτας
            CardView card = new CardView(requireContext());
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            cardParams.setMargins(0, 16, 0, 0);
            card.setLayoutParams(cardParams);
            card.setRadius(16);
            card.setCardElevation(8);
            card.setCardBackgroundColor(Color.WHITE);

            LinearLayout cardContent = new LinearLayout(requireContext());
            cardContent.setOrientation(LinearLayout.VERTICAL);
            cardContent.setPadding(32, 32, 32, 32);

            TextView questionText = new TextView(requireContext());
            questionText.setText("Ερώτηση " + (i + 1) + ": " + question.getQuestion());
            questionText.setTextSize(16f);
            questionText.setTextColor(Color.BLACK);
            questionText.setTypeface(customFont);

            TextView userAnswerText = new TextView(requireContext());
            if (userAnswer != null) {
                userAnswerText.setText("Απάντησες: " + (userAnswer ? "Σωστό" : "Λάθος"));
                userAnswerText.setTextColor(correct ? Color.parseColor("#4CAF50") : Color.parseColor("#F44336"));
            } else {
                userAnswerText.setText("Δεν απάντησες");
                userAnswerText.setTypeface(null, Typeface.BOLD);
                userAnswerText.setTextColor(Color.DKGRAY);
            }
            userAnswerText.setTypeface(customFont);

            TextView correctAnswerText = new TextView(requireContext());
            correctAnswerText.setText("Σωστή απάντηση: " + (question.isCorrectAnswer() ? "Σωστό" : "Λάθος"));
            correctAnswerText.setTextColor(Color.DKGRAY);
            correctAnswerText.setTypeface(customFont);

            TextView explanationText = new TextView(requireContext());
            explanationText.setText("Εξήγηση: " + question.getExplanation());
            explanationText.setTextSize(14f);
            explanationText.setTextColor(Color.parseColor("#555555"));
            explanationText.setTypeface(customFont);

            cardContent.addView(questionText);
            cardContent.addView(userAnswerText);
            cardContent.addView(correctAnswerText);
            cardContent.addView(explanationText);

            card.addView(cardContent);
            containerLayout.addView(card);
        }

        // Τελικό σκορ
        TextView scoreText = new TextView(requireContext());
        scoreText.setText("\nΣκορ: " + score + " / " + questions.size());
        scoreText.setTextSize(20f);
        scoreText.setTextColor(Color.BLACK);
        scoreText.setTypeface(customFont, Typeface.BOLD);
        scoreText.setPadding(0, 32, 0, 16);
        scoreText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        containerLayout.addView(scoreText);

        Button backButton = new Button(requireContext());
        backButton.setText("ΕΠΙΣΤΡΟΦΗ ΣΤΙΣ ΚΑΤΗΓΟΡΙΕΣ");
        backButton.setTextColor(Color.WHITE);
        backButton.setTypeface(customFont);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor("#8C0306"));
        drawable.setCornerRadius(10);
        backButton.setBackground(drawable);
        backButton.setPadding(48, 24, 48, 24);

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        buttonParams.setMargins(0, 32, 0, 64);
        buttonParams.gravity = Gravity.CENTER;

        backButton.setLayoutParams(buttonParams);

        backButton.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.categoryFragment)
        );

        containerLayout.addView(backButton);

        return view;
    }
}
