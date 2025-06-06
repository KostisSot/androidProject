package com.example.project1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project1.databinding.ActivityMainBinding;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);


        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_phones);
                binding.navView.setCheckedItem(R.id.nav_phones);
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.directionsFragment,
                R.id.resultFragment,
                R.id.nav_phones,
                R.id.categoryFragment
        )
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.nav_phones) {
                binding.appBarMain.fab.setVisibility(View.GONE); // Κρύβει το FAB
            } else {
                binding.appBarMain.fab.setVisibility(View.VISIBLE); // Δείχνει το FAB
            }
        });
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            boolean handled = false;

            if (id == R.id.nav_home) {
                navController.popBackStack(R.id.nav_home, false);
                navController.navigate(R.id.nav_home);
                handled = true;
            } else if (id == R.id.directionsFragment) {
                navController.popBackStack(R.id.directionsFragment, false);
                navController.navigate(R.id.directionsFragment);
                handled = true;
            } else if (id == R.id.nav_slideshow) {
                navController.popBackStack(R.id.nav_slideshow, false);
                navController.navigate(R.id.categoryFragment);
                handled = true;
            } else if (id == R.id.nav_phones) {
                navController.popBackStack(R.id.nav_phones, false);
                navController.navigate(R.id.nav_phones);
                handled = true;
            }

            if (handled) {
                item.setChecked(true);
                drawer.closeDrawers();
            }

            return handled;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}