package com.example.project1.ui.quiz;

import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores user answers and dynamically tracks the quiz score based on correctness.
 * It ensures score accuracy even when answers are updated.
 * @author dimitrasa
 * @noinspection ALL
 */
public class QuizViewModel extends ViewModel {

    private final Map<Integer, Boolean> userAnswers = new HashMap<>();
    private int score = 0;


    public void setUserAnswer(int index, boolean answer, boolean correctAnswer) {

        if (userAnswers.containsKey(index)) {
            boolean wasCorrect = Boolean.TRUE.equals(userAnswers.get(index)) == correctAnswer;
            if (wasCorrect) score--;
        }

        userAnswers.put(index, answer);

        if (answer == correctAnswer) {
            score++;
        }
    }

    public Boolean getUserAnswer(int index) {
        return userAnswers.get(index);
    }

    public int getScore() {
        return score;
    }

    public void clearAnswers() {
        userAnswers.clear();
        score = 0;
    }

    public Map<Integer, Boolean> getAllAnswers() {
        return userAnswers;
    }

}
