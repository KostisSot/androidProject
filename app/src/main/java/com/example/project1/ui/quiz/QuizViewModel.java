package com.example.project1.ui.quiz;

import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

public class QuizViewModel extends ViewModel {

    private final Map<Integer, Boolean> userAnswers = new HashMap<>();
    private int score = 0;

    // Αποθήκευση απάντησης με έλεγχο αν είναι σωστή
    public void setUserAnswer(int index, boolean answer, boolean correctAnswer) {
        // Αν αλλάξει απάντηση, αφαιρούμε το προηγούμενο σκορ
        if (userAnswers.containsKey(index)) {
            boolean wasCorrect = userAnswers.get(index) == correctAnswer;
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
