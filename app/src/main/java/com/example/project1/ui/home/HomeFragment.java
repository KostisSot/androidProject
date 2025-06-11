package com.example.project1.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project1.DatabaseHelper;
import com.example.project1.R;
import com.example.project1.databinding.FragmentHomeBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private FragmentHomeBinding binding;
    private GoogleMap mMap;
    private PlacesClient placesClient;
    private DatabaseHelper dbHelper;

    private EditText editTextInput;
    private Button buttonSave, buttonEdit;
    private TextView textViewDisplay;
    private LinearLayout userInfoLayout;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Συνδέσεις views
        editTextInput = root.findViewById(R.id.editTextInput);
        buttonSave = root.findViewById(R.id.buttonSave);
        textViewDisplay = root.findViewById(R.id.textViewDisplay);
        buttonEdit = root.findViewById(R.id.buttonEdit);
        userInfoLayout = root.findViewById(R.id.userInfoLayout);

        dbHelper = new DatabaseHelper(requireContext());

        buttonSave.setOnClickListener(v -> {
            String inputText = editTextInput.getText().toString().trim();
            if (!inputText.isEmpty()) {
                dbHelper.insertText(inputText);
                textViewDisplay.setText(getString(R.string.welcome2) + " " + inputText);
                textViewDisplay.setVisibility(View.VISIBLE);
                editTextInput.setVisibility(View.GONE);
                buttonSave.setVisibility(View.GONE);
                buttonEdit.setVisibility(View.VISIBLE);
                userInfoLayout.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getContext(), "Παρακαλώ εισάγετε όνομα", Toast.LENGTH_SHORT).show();
            }
        });

        buttonEdit.setOnClickListener(v -> {
            String currentText = textViewDisplay.getText().toString();
            String nameOnly = currentText.replace(getString(R.string.welcome2) + " ", "");
            editTextInput.setText(nameOnly);

            textViewDisplay.setVisibility(View.GONE);
            buttonEdit.setVisibility(View.GONE);
            editTextInput.setVisibility(View.VISIBLE);
            buttonSave.setVisibility(View.VISIBLE);
        });

        String lastText = dbHelper.getLastText();
        if (!lastText.isEmpty()) {
            textViewDisplay.setText(getString(R.string.welcome2) + " " + lastText);
            userInfoLayout.setVisibility(View.VISIBLE);
            editTextInput.setVisibility(View.GONE);
            buttonSave.setVisibility(View.GONE);
        } else {
            userInfoLayout.setVisibility(View.GONE);
            editTextInput.setVisibility(View.VISIBLE);
            buttonSave.setVisibility(View.VISIBLE);
        }

        // Initialize Places
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), "AIzaSyByS_0fYwfl-4omaZ-W0P7iEjK5CYT6xm4");
        }
        placesClient = Places.createClient(requireContext());

        // Load map dynamically into FrameLayout
        SupportMapFragment mapFragment = new SupportMapFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.map_container, mapFragment).commit();
        mapFragment.getMapAsync(this);

        return root;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Κεντράρισμα στην Ελλάδα
        LatLng greeceCenter = new LatLng(38.5, 22.5);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(greeceCenter, 5.7f));

        // Νοσοκομεία (30 ενδεικτικά)
        addHospitalMarker("Γενικό Νοσοκομείο Αθηνών Ευαγγελισμός", 37.9755, 23.7461);
        addHospitalMarker("Γενικό Νοσοκομείο Θεσσαλονίκης Ιπποκράτειο", 40.6101, 22.9597);
        addHospitalMarker("Πανεπιστημιακό Νοσοκομείο Λάρισας", 39.6234, 22.4042);
        addHospitalMarker("Πανεπιστημιακό Νοσοκομείο Πατρών", 38.2554, 21.7564);
        addHospitalMarker("Γενικό Νοσοκομείο Ηρακλείου", 35.3387, 25.1442);
        addHospitalMarker("Νοσοκομείο Αγίου Ανδρέα (Πάτρα)", 38.2426, 21.7351);
        addHospitalMarker("Νοσοκομείο Καβάλας", 40.9390, 24.4068);
        addHospitalMarker("Νοσοκομείο Βόλου", 39.3622, 22.9425);
        addHospitalMarker("Νοσοκομείο Ιωαννίνων", 39.6680, 20.8535);
        addHospitalMarker("Νοσοκομείο Ρόδου", 36.4348, 28.2176);
        addHospitalMarker("Νοσοκομείο Χανίων", 35.5120, 24.0180);
        addHospitalMarker("Νοσοκομείο Τρίπολης", 37.5115, 22.3749);
        addHospitalMarker("Νοσοκομείο Καλαμάτας", 37.0380, 22.1144);
        addHospitalMarker("Νοσοκομείο Κοζάνης", 40.3014, 21.7859);
        addHospitalMarker("Νοσοκομείο Σπάρτης", 37.0744, 22.4302);
        addHospitalMarker("Νοσοκομείο Ξάνθης", 41.1362, 24.8867);
        addHospitalMarker("Νοσοκομείο Κέρκυρας", 39.6249, 19.9217);
        addHospitalMarker("Νοσοκομείο Λαμίας", 38.9032, 22.4337);
        addHospitalMarker("Νοσοκομείο Κατερίνης", 40.2692, 22.5026);
        addHospitalMarker("Νοσοκομείο Πύργου", 37.6755, 21.4418);
        addHospitalMarker("Νοσοκομείο Αλεξανδρούπολης", 40.8506, 25.8741);
        addHospitalMarker("Νοσοκομείο Βέροιας", 40.5239, 22.2166);
        addHospitalMarker("Νοσοκομείο Καρδίτσας", 39.3636, 21.9210);
        addHospitalMarker("Νοσοκομείο Κιλκίς", 40.9936, 22.8784);
        addHospitalMarker("Νοσοκομείο Λάρισας", 39.6380, 22.4189);
        addHospitalMarker("Νοσοκομείο Μυτιλήνης", 39.1270, 26.5547);
        addHospitalMarker("Νοσοκομείο Πτολεμαΐδας", 40.5167, 21.6750);
        addHospitalMarker("Νοσοκομείο Σερρών", 41.0858, 23.5453);
        addHospitalMarker("Νοσοκομείο Τρικάλων", 39.5557, 21.7694);
        addHospitalMarker("Νοσοκομείο Χαλκίδας", 38.4633, 23.6020);
    }

    private void addHospitalMarker(String name, double lat, double lng) {
        LatLng position = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(name));
    }

}
