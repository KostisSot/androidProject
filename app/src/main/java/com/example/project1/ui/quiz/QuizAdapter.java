package com.example.project1.ui.quiz;


import com.example.project1.model.QuizQuestion;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;


import java.util.List;

/**
 * Manages a list of true/false quiz questions in a RecyclerView.
 * It displays each question with two buttons for answers, highlights the correct and incorrect choices when selected, and shows an explanation after the user answers.
 * @author dimitrasa
 * @noinspection ALL
 */
public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {
    private final List<QuizQuestion> questions;

    public QuizAdapter(List<QuizQuestion> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_item, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        holder.bind(questions.get(position));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    static class QuizViewHolder extends RecyclerView.ViewHolder {
        TextView questionText, explanationText;
        Button trueBtn, falseBtn;

        QuizViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.questionText);
            trueBtn = itemView.findViewById(R.id.trueBtn);
            falseBtn = itemView.findViewById(R.id.falseBtn);
            explanationText = itemView.findViewById(R.id.explanationText);
        }

        void bind(QuizQuestion question) {
            questionText.setText(question.getQuestion());
            explanationText.setVisibility(View.GONE);
            trueBtn.setBackgroundColor(Color.LTGRAY);
            falseBtn.setBackgroundColor(Color.LTGRAY);
            trueBtn.setEnabled(true);
            falseBtn.setEnabled(true);

            View.OnClickListener listener = v -> {
                boolean userAnswer = (v == trueBtn);
                boolean correct = userAnswer == question.isCorrectAnswer();

                trueBtn.setBackgroundColor(question.isCorrectAnswer() ? Color.GREEN : Color.RED);
                falseBtn.setBackgroundColor(!question.isCorrectAnswer() ? Color.GREEN : Color.RED);

                explanationText.setVisibility(View.VISIBLE);
                explanationText.setText(question.getExplanation());

                trueBtn.setEnabled(false);
                falseBtn.setEnabled(false);
            };

            trueBtn.setOnClickListener(listener);
            falseBtn.setOnClickListener(listener);
        }
    }
}