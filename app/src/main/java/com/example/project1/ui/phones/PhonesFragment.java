package com.example.project1.ui.phones;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
        TextView callEmergency = view.findViewById(R.id.call_emergency);
        TextView callPolice = view.findViewById((R.id.call_police));
        TextView callFireDep = view.findViewById((R.id.call_fire_department));

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

        callPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:100"));
                startActivity(dialIntent);
            }
        });

        callFireDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:199"));
                startActivity(dialIntent);
            }
        });

        ImageButton infoBtn = view.findViewById(R.id.info_ambulance);
        infoBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("ΕΚΑΒ")
                    .setMessage("Ο αριθμός 166 καλεί άμεσα ασθενοφόρο για επείγοντα περιστατικά.")
                    .setPositiveButton("OK", null)
                    .show();
        });
        ImageButton infoBtn2 = view.findViewById(R.id.info_emergency);
        infoBtn2.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Ευρωπαϊκός Αριθμός Έκτακτης Ανάγκης")
                    .setMessage("Ο αριθμός 112 είναι ο πανευρωπαϊκός αριθμός έκτακτης ανάγκης που χρησιμοποιείται για άμεση επικοινωνία με όλες τις τοπικές υπηρεσίες έκτακτης ανάγκης.")
                    .setPositiveButton("OK", null)
                    .show();
        });
        ImageButton infoBtn3 = view.findViewById(R.id.info_fire_department);
        infoBtn3.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Πυροσβεστική")
                    .setMessage("Ο αριθμός 199 καλεί άμεσα την πυροσβεστική για επείγοντα περιστατικά.")
                    .setPositiveButton("OK", null)
                    .show();
        });
        ImageButton infoBtn4 = view.findViewById(R.id.info_police);
        infoBtn4.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Αστυνομία")
                    .setMessage("Ο αριθμός 100 καλεί άμεσα την αστυνομία για επείγοντα περιστατικά.")
                    .setPositiveButton("OK", null)
                    .show();
        });


        return view;
    }
}
