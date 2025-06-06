package com.example.project1.ui.home;

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
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Βρες τα views από το layout
        EditText editTextInput = root.findViewById(R.id.editTextInput);
        Button buttonSave = root.findViewById(R.id.buttonSave);
        TextView textViewDisplay = root.findViewById(R.id.textViewDisplay);
        Button buttonEdit = root.findViewById(R.id.buttonEdit);
        LinearLayout userInfoLayout = root.findViewById(R.id.userInfoLayout);

        // Βοηθός βάσης δεδομένων
        dbHelper = new DatabaseHelper(requireContext());



        buttonSave.setOnClickListener(v -> {
            String inputText = editTextInput.getText().toString().trim();

            if (!inputText.isEmpty()) {
                dbHelper.insertText(inputText);
                textViewDisplay.setText("Καλωσήρθες, "+ inputText);
                textViewDisplay.setVisibility(View.VISIBLE);
                editTextInput.setVisibility(View.GONE);
                buttonSave.setVisibility(View.GONE);
                buttonEdit.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getContext(), "Παρακαλώ εισάγετε όνομα", Toast.LENGTH_SHORT).show();
            }
        });
        buttonEdit.setOnClickListener(v -> {
            String currentText = textViewDisplay.getText().toString();
            editTextInput.setText(currentText);

            textViewDisplay.setVisibility(View.GONE);
            buttonEdit.setVisibility(View.GONE);
            editTextInput.setVisibility(View.VISIBLE);
            buttonSave.setVisibility(View.VISIBLE);
        });
        String lastText = dbHelper.getLastText();

        if (!lastText.isEmpty()) {
            textViewDisplay.setText(lastText);
            userInfoLayout.setVisibility(View.VISIBLE);     // δείξε textView + κουμπί
            editTextInput.setVisibility(View.GONE);
            buttonSave.setVisibility(View.GONE);
        } else {
            userInfoLayout.setVisibility(View.GONE);// κρύψε το textView + κουμπί
            editTextInput.setVisibility(View.VISIBLE);
            buttonSave.setVisibility(View.VISIBLE);
        }

        // Initialize Places API
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), "b13034974f7517a5c2f465fd7ea0e7cc-f3238714-ac8e7226");
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

        // Εστίαση στην ελλαδα
        LatLng gre = new LatLng(30.9, 23.5);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gre, 5.5f));
        searchHospitalsInGreece();
    }

    private void searchHospitalsInGreece() {
        // Όρια για ολόκληρη την Ελλάδα
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

                        // Ορισμός τι πεδία θέλουμε
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
