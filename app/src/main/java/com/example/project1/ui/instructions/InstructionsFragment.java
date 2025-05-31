package com.example.project1.ui.instructions;

import com.example.project1.ui.instructions.InstructionsSlideAdapter;
import android.text.Html;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.project1.R;

import java.util.ArrayList;
import java.util.List;

public class InstructionsFragment extends Fragment {

    public InstructionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_instructions, container, false);

        Bundle args = getArguments();
        if (args != null) {
            String title = args.getString("title");

            Log.d("InstructionsFragment", "title: " + title);

            TextView titleTextView = root.findViewById(R.id.instructions_title);
            titleTextView.setText(title);

            // Slideshow (ViewPager2)
            ViewPager2 viewPager = root.findViewById(R.id.instructions_pager);
            List<InstructionStep> steps = new ArrayList<>();

            // Παράδειγμα θεμάτων - προσάρμοσε όπως χρειαστεί
            if ("Βασικές Γνώσεις".equals(title)) {
                steps.add(new InstructionStep(Html.fromHtml("<b>Βασικές Οδηγίες και Γνώσεις.</b> <br><br>" +
                        " Προσέγγιση Θύματος, θέση ασφαλείας και αξιολόγηση της κατάστασης"), R.drawable.first_slide_basic));
                steps.add(new InstructionStep(Html.fromHtml("<b>Ασφαλής Προσέγγιση Ασθενούς</b><br><br>1. Φροντίστε να είστε ασφαλείς και να προστατεύετε τον τραυματισμένο από περαιτέρω κινδύνους.<br>" +
                        "2. Αν ο τραυματισμός είναι σοβαρός, καλέστε το ΕΚΑΒ (166) ή το 112.<br>" +
                        "3. Αποφύγετε την επαφή με τα σωματικά υγρά του θύματος. Χρησιμοποιείστε γάντια.<br>"), R.drawable.second_slide_basic));
                steps.add(new InstructionStep(Html.fromHtml("<b>Εκτίμηση Πάσχοντα</b><br><br>Η εκτίμηση του πάσχοντα γίνεται σύμφωνα με μια σειρά ενεργειών, οι οποίες είναι ίδιες σε όλο τον κόσμο:" +
                        "<br>1. Έλεγχος του αεραγωγού." +
                        "<br>2. Έλεγχος για την ύπαρξη αναπνοής." +
                        "<br>3. Έλεγχος της κυκλοφορίας." +
                        "<br>3. Νευρολογικός Έλεγχος" +
                        "<br>4. Έκθεση και προστασία από το περιβάλλον"), R.drawable.second_slide_basic));
                steps.add(new InstructionStep(Html.fromHtml("<b>Κλήση στο ΕΚΑΒ</b><br><br>Τι πρέπει να συμπεριλάβουμε στην κλήση στο ΕΚΑΒ:" +
                        "<br>1. Το τηλέφωνο και το όνομά μας." +
                        "<br>2. Τι περιστατικό έχει συμβεί." +
                        "<br>3. Πού έγινε το περιστατικό." +
                        "<br>3. Πόσα είναι τα θύματα." +
                        "<br>4. Πότε έγινε." +
                        "<br>5. Άλλες σημαντικές πληροφορίες." +
                        "<br><b>Κλέινουμε πάντα τελευταίοι το τηλέφωνο!</b>"), R.drawable.fourth_slide_basic));
            } else if ("Αιμορραγία".equals(title)) {
                steps.add(new InstructionStep(Html.fromHtml("Βασικές Οδηγίες και Γνώσεις.\n Προσέγγιση Θύματος, θέση ασφαλείας και αξιολόγηση της κατάστασης"), R.drawable.first_slide_basic));
                steps.add(new InstructionStep(Html.fromHtml("Βασικές Οδηγίες και Γνώσεις.\n Προσέγγιση Θύματος, θέση ασφαλείας και αξιολόγηση της κατάστασης"), R.drawable.first_slide_basic));
                steps.add(new InstructionStep(Html.fromHtml("Βασικές Οδηγίες και Γνώσεις.\n Προσέγγιση Θύματος, θέση ασφαλείας και αξιολόγηση της κατάστασης"), R.drawable.first_slide_basic));
            } else {
                steps.add(new InstructionStep(Html.fromHtml("Βασικές Οδηγίες και Γνώσεις.\n Προσέγγιση Θύματος, θέση ασφαλείας και αξιολόγηση της κατάστασης"), R.drawable.first_slide_basic));
            }

            InstructionsSlideAdapter adapter = new InstructionsSlideAdapter(steps);
            viewPager.setAdapter(adapter);
        }

        return root;
    }
}
