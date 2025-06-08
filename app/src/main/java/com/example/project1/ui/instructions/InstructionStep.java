package com.example.project1.ui.instructions;

import android.text.Spanned;

/**
 * Represents a single step in a set of instructions.
 * It holds formatted text (Spanned) and a corresponding image resource ID to visually support the step.
 * @author kostissotiriou
 */
public class InstructionStep {
    private final Spanned text;


    private final int imageResId;

    public InstructionStep(Spanned text, int imageResId) {
        this.text = text;
        this.imageResId = imageResId;
    }

    public Spanned getText() {
        return text;
    }

    public int getImageResId() {
        return imageResId;
    }
}
