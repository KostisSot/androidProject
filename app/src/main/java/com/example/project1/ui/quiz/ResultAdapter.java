package com.example.project1.ui.quiz;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;
import com.example.project1.model.QuizQuestion;

import java.util.List;
import java.util.Map;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private final List<QuizQuestion> questions;
    private final Map<Integer, Boolean> userAnswers;

    public ResultAdapter(List<QuizQuestion> questions, Map<Integer, Boolean> userAnswers) {
        this.questions = questions;
        this.userAnswers = userAnswers;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionText, userAnswerText, correctAnswerText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.questionText);
            userAnswerText = itemView.findViewById(R.id.userAnswerText);
            correctAnswerText = itemView.findViewById(R.id.correctAnswerText);
        }
    }

    @NonNull
    @Override
    public ResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultAdapter.ViewHolder holder, int position) {
        QuizQuestion question = questions.get(position);
        Boolean userAnswer = userAnswers.get(position);

        holder.questionText.setText((position + 1) + ". " + question.getQuestion());

        if (userAnswer != null) {
            holder.userAnswerText.setText("Απάντησες: " + (userAnswer ? "Σωστό" : "Λάθος"));
            if (userAnswer == question.isCorrectAnswer()) {
                holder.userAnswerText.setTextColor(Color.parseColor("#4CAF50")); // Πράσινο
            } else {
                holder.userAnswerText.setTextColor(Color.parseColor("#F44336")); // Κόκκινο
            }
        } else {
            holder.userAnswerText.setText("Δεν απαντήθηκε");
            holder.userAnswerText.setTextColor(Color.GRAY);
        }

        holder.correctAnswerText.setText("Σωστή απάντηση: " + (question.isCorrectAnswer() ? "Σωστό" : "Λάθος"));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}

