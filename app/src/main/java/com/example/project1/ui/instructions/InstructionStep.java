package com.example.project1.ui.instructions;

import android.text.Spanned;

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
