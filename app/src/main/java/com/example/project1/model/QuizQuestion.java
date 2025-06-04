package com.example.project1.model;

public class QuizQuestion {
    private String question;
    private boolean correctAnswer;
    private String explanation;
    private String category;

    public QuizQuestion(String question, boolean correctAnswer, String explanation, String category) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getCategory() {
        return category;
    }
}
