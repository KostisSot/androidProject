package com.example.project1.ui.directions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;
import com.example.project1.databinding.FragmentDirectionsBinding;
import com.example.project1.model.FirstAidTopic;

import java.util.ArrayList;
import java.util.List;

public class DirectionsFragment extends Fragment {

    private FragmentDirectionsBinding binding;
    private List<FirstAidTopic> topicList;
    private TopicAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDirectionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupRecyclerView();

        return root;
    }

    private void setupRecyclerView() {
        topicList = new ArrayList<>();
        topicList.add(new FirstAidTopic(1, "ΚΑΡΠΑ", R.drawable.baseline_add_24));
        topicList.add(new FirstAidTopic(2, "Εγκαύματα", R.drawable.baseline_add_24));
        topicList.add(new FirstAidTopic(3, "Αιμορραγία", R.drawable.baseline_add_24));

        adapter = new TopicAdapter(topicList, topic -> {
            Bundle bundle = new Bundle();
            bundle.putInt("topicId", topic.getId());
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_directionsFragment_to_instructionsFragment, bundle);
        });

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
