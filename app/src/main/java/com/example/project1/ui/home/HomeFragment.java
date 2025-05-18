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

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private FragmentHomeBinding binding;
    private GoogleMap mMap;
    private PlacesClient placesClient;
    private static final String TAG = "HomeFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize Places API
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), "ΤΟ_API_KEY_ΣΟΥ");
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
        LatLng gre = new LatLng(38.9, 22.5);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gre, 6.5f));
        searchHospitalsInGreece();
    }

    private void searchHospitalsInGreece() {
        // Όρια για ολόκληρη την Ελλάδα
        RectangularBounds greeceBounds = RectangularBounds.newInstance(
                new LatLng(34.0, 19.0),   // Southwest corner
                new LatLng(41.0, 29.0)    // Northeast corner
        );

        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setQuery(" Hospitals in Greece")
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
