package com.example.project1.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project1.databinding.FragmentHomeBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.project1.DatabaseHelper;
import com.example.project1.R;
import android.widget.LinearLayout;

/**
 * Is the main screen of the app, displaying a personalized welcome message and a Google Map that marks emergency hospitals across Greece using the Places API.
 * It also allows users to save and edit their name locally using a database.
 * @author kostissotiriou, psarrasd
 *
 */
public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private FragmentHomeBinding binding;
    private GoogleMap mMap;
    private PlacesClient placesClient;
    private static final String TAG = "HomeFragment";
    private EditText editTextInput;
    private Button buttonSave;
    private TextView textViewDisplay;
    private DatabaseHelper dbHelper;
    private Button buttonEdit;
    private LinearLayout userInfoLayout;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Views
        EditText editTextInput = root.findViewById(R.id.editTextInput);
        Button buttonSave = root.findViewById(R.id.buttonSave);
        TextView textViewDisplay = root.findViewById(R.id.textViewDisplay);
        Button buttonEdit = root.findViewById(R.id.buttonEdit);
        LinearLayout userInfoLayout = root.findViewById(R.id.userInfoLayout);

        // Database helper
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
            String nameOnly = currentText.replace(getString(R.string.welcome2) + " ", ""); // Αφαιρεί το "Καλώς ήρθες "
            editTextInput.setText(nameOnly);

            textViewDisplay.setVisibility(View.GONE);
            buttonEdit.setVisibility(View.GONE);
            editTextInput.setVisibility(View.VISIBLE);
            buttonSave.setVisibility(View.VISIBLE);
        });

        String lastText = dbHelper.getLastText(); // Αυτό πρέπει να είναι ΜΟΝΟ το όνομα

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

        //Places API
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), "AIzaSyByS_0fYwfl-4omaZ-W0P7iEjK5CYT6xm4");
        }
        placesClient = Places.createClient(requireContext());

        // Load map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(com.example.project1.R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return root;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;


        LatLng gre = new LatLng(30.9, 23.5);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gre, 5.5f));
        searchHospitalsInGreece();
    }

    private void searchHospitalsInGreece() {
        // Όρια για την Ελλάδα
        RectangularBounds greeceBounds = RectangularBounds.newInstance(
                new LatLng(34.0, 19.0),   // Southwest corner
                new LatLng(41.0, 29.0)    // Northeast corner
        );

        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setQuery("Εφημερεύοντα νοσοκομεία")
                .setLocationBias(greeceBounds)
                .build();

        placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener((FindAutocompletePredictionsResponse response) -> {
                    List<AutocompletePrediction> predictions = response.getAutocompletePredictions();
                    for (AutocompletePrediction prediction : predictions) {
                        String placeId = prediction.getPlaceId();


                        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG);
                        FetchPlaceRequest fetchRequest = FetchPlaceRequest.builder(placeId, placeFields).build();

                        placesClient.fetchPlace(fetchRequest)
                                .addOnSuccessListener(fetchResponse -> {
                                    Place place = fetchResponse.getPlace();
                                    LatLng latLng = place.getLatLng();
                                    if (latLng != null) {
                                        mMap.addMarker(new MarkerOptions()
                                                .position(latLng)
                                                .title(place.getName()));
                                    }
                                })
                                .addOnFailureListener(e -> Log.e(TAG, "Σφάλμα στο fetchPlace: " + e.getMessage()));
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Σφάλμα στην αναζήτηση: " + e.getMessage()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
