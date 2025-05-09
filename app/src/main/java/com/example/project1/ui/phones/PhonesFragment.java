package com.example.project1.ui.phones;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project1.R;

public class  PhonesFragment extends Fragment {

    public PhonesFragment() {
        // Απαραίτητο empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_numbers, container, false);

        TextView callAmbulance = view.findViewById(R.id.call_ambulance);
        TextView callEmergency = view.findViewById(R.id.call_general_emergency);

        callAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:166"));
                startActivity(dialIntent);
            }
        });

        callEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:112"));
                startActivity(dialIntent);
            }
        });

        return view;
    }
}
