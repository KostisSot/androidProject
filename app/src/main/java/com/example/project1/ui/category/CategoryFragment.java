package com.example.project1.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;

import java.util.Arrays;
import java.util.List;

public class CategoryFragment extends Fragment implements CategoryAdapter.OnCategoryClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.categoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<String> categories = Arrays.asList("Βασικές Γνώσεις", "ΚΑΡΠΑ", "Πνιγμονή από Ξένο Σώμα", "Αιμορραγία", "Περιποίηση Τράυματος", "Αλλεργία/Αναφυλλακτικό ΣΟΚ", "Έγκαυμα", "Λιποθυμία", "Έμφραγμα", "Εγκεφαλικό", "Δηλητηρίαση", "Επιληψία", "Κουτί Πρώτων Βοηθειών", "Χρήση Αυτόματου Απινιδωτή");
        recyclerView.setAdapter(new CategoryAdapter(categories, this));

        return view;
    }

    @Override
    public void onCategoryClick(String category, View view) {
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        Navigation.findNavController(view).navigate(R.id.action_categoryFragment_to_quizFragment, bundle);
    }
}



