package com.example.project1;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.project1.ui.phones.CustomTypefaceSpan;
import com.google.android.material.navigation.NavigationView;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project1.databinding.ActivityMainBinding;


/**
 * Sets up the navigation structure and drawer layout for the Android app.
 * It initializes navigation components, configures menu item font styling, and manages the Floating Action Button’s visibility based on the current screen.
 * @author kostissotiriou
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(
                androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);


        binding.appBarMain.fab.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_phones);
            binding.navView.setCheckedItem(R.id.nav_phones);
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
                binding.appBarMain.fab.setVisibility(View.GONE);
            } else {
                binding.appBarMain.fab.setVisibility(View.VISIBLE);
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
        Typeface typeface = ResourcesCompat.getFont(this, R.font.tektur);
        Typeface boldTypeface = Typeface.create(typeface, Typeface.BOLD);

        Menu navMenu = navigationView.getMenu();
        for (int i = 0; i < navMenu.size(); i++) {
            MenuItem menuItem = navMenu.getItem(i);

            SpannableString spanString = new SpannableString(menuItem.getTitle());
            spanString.setSpan(new CustomTypefaceSpan("", boldTypeface), 0, spanString.length(), 0);
            menuItem.setTitle(spanString);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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