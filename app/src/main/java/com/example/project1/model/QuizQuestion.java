package com.example.project1.model;

public class QuizQuestion {
    private final String category;
    private final String question;
    private final boolean correctAnswer;
    private final String explanation;

    public QuizQuestion(String question, boolean correctAnswer, String explanation, String category) {
        this.category = category;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
    }

    public String getCategory() { return category; }
    public String getQuestion() { return question; }
    public boolean isCorrectAnswer() { return correctAnswer; }
    public String getExplanation() { return explanation; }
}

