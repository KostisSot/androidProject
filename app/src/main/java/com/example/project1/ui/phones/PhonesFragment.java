package com.example.project1.ui.phones;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.project1.R;

/**
 * Provides quick access to essential emergency phone numbers and displays informative dialogs when users tap on the info buttons for each service.
 * @author dimitrasa
 * @noinspection ALL
 */
public class  PhonesFragment extends Fragment {

    public PhonesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_numbers, container, false);

        TextView callAmbulance = view.findViewById(R.id.call_ambulance);
        TextView callEmergency = view.findViewById(R.id.call_emergency);
        TextView callPolice = view.findViewById((R.id.call_police));
        TextView callFireDep = view.findViewById((R.id.call_fire_department));

        callAmbulance.setOnClickListener(v -> {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:166"));
            startActivity(dialIntent);
        });

        callEmergency.setOnClickListener(v -> {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:112"));
            startActivity(dialIntent);
        });

        callPolice.setOnClickListener(v -> {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:100"));
            startActivity(dialIntent);
        });

        callFireDep.setOnClickListener(v -> {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:199"));
            startActivity(dialIntent);
        });

        Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.tektur);
        Typeface boldTypeface = Typeface.create(typeface, Typeface.BOLD);

        ImageButton infoBtn = view.findViewById(R.id.info_ambulance);
        infoBtn.setOnClickListener(v -> {
            SpannableString title = new SpannableString("ΕΚΑΒ");
            SpannableString message = new SpannableString("Ο αριθμός 166 καλεί άμεσα ασθενοφόρο για επείγοντα περιστατικά.");

            title.setSpan(new CustomTypefaceSpan("", boldTypeface), 0, title.length(), 0);
            message.setSpan(new CustomTypefaceSpan("", typeface), 0, message.length(), 0);

            new AlertDialog.Builder(getContext())
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("OK", null)
                    .show();
        });

        ImageButton infoBtn2 = view.findViewById(R.id.info_emergency);
        infoBtn2.setOnClickListener(v -> {
            SpannableString title = new SpannableString("Ευρωπαϊκός Αριθμός Έκτακτης Ανάγκης");
            SpannableString message = new SpannableString("Ο αριθμός 112 είναι ο πανευρωπαϊκός αριθμός έκτακτης ανάγκης που χρησιμοποιείται για άμεση επικοινωνία με όλες τις τοπικές υπηρεσίες έκτακτης ανάγκης.");

            title.setSpan(new CustomTypefaceSpan("", boldTypeface), 0, title.length(), 0);
            message.setSpan(new CustomTypefaceSpan("", typeface), 0, message.length(), 0);

            new AlertDialog.Builder(getContext())
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("OK", null)
                    .show();
        });

        ImageButton infoBtn3 = view.findViewById(R.id.info_fire_department);
        infoBtn3.setOnClickListener(v -> {
            SpannableString title = new SpannableString("Πυροσβεστική");
            SpannableString message = new SpannableString("Ο αριθμός 199 καλεί άμεσα την πυροσβεστική για επείγοντα περιστατικά.");

            title.setSpan(new CustomTypefaceSpan("", boldTypeface), 0, title.length(), 0);
            message.setSpan(new CustomTypefaceSpan("", typeface), 0, message.length(), 0);

            new AlertDialog.Builder(getContext())
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("OK", null)
                    .show();
        });

        ImageButton infoBtn4 = view.findViewById(R.id.info_police);
        infoBtn4.setOnClickListener(v -> {
            SpannableString title = new SpannableString("Αστυνομία");
            SpannableString message = new SpannableString("Ο αριθμός 100 καλεί άμεσα την αστυνομία για επείγοντα περιστατικά.");

            title.setSpan(new CustomTypefaceSpan("", boldTypeface), 0, title.length(), 0);
            message.setSpan(new CustomTypefaceSpan("", typeface), 0, message.length(), 0);

            new AlertDialog.Builder(getContext())
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("OK", null)
                    .show();
        });



        return view;
    }
}
