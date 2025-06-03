package com.example.project1.ui.instructions;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;

import java.util.List;

public class InstructionsSlideAdapter extends RecyclerView.Adapter<InstructionsSlideAdapter.ViewHolder> {

        private final List<InstructionStep> steps;

        public InstructionsSlideAdapter(List<InstructionStep> steps) {
            this.steps = steps;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView text;
            ImageView image;

            public ViewHolder(View view) {
                super(view);
                text = view.findViewById(R.id.instruction_text);
                image = view.findViewById(R.id.instruction_image);
            }
        }

        @NonNull
        @Override
        public InstructionsSlideAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_instruction_slide, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull InstructionsSlideAdapter.ViewHolder holder, int position) {
            InstructionStep step = steps.get(position);
            holder.text.setText(step.getText());
            holder.image.setImageResource(step.getImageResId());

            Log.d("Adapter", "Binding position: " + position);

        }

        @Override
        public int getItemCount() {
            return steps.size();
        }
}
