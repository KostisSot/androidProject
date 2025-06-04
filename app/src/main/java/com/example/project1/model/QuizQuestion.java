package com.example.project1.model;

public class QuizQuestion {
    private String category;
    private String question;
    private boolean correctAnswer;
    private String explanation;

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

