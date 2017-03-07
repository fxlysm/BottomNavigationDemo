package com.fxly.unittesting;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CustomBottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.fxly.unittesting.fragment.FavoritesFragment;
import com.fxly.unittesting.fragment.SettingsFragment;
import com.fxly.unittesting.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG_HOME = "home";
    private static final String FRAGMENT_TAG_FAVORITES = "favorites";
    private static final String FRAGMENT_TAG_NEARBY = "nearby";

    private HomeFragment homeFragment;
    private FavoritesFragment favoritesFragment;
    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CustomBottomNavigationView navigationView = (CustomBottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        switchFragment(homeFragment, FRAGMENT_TAG_HOME);
                        break;
                    case R.id.navigation_dashboard:
                        switchFragment(favoritesFragment, FRAGMENT_TAG_FAVORITES);
                        break;
                    case R.id.navigation_notifications:
                        switchFragment(settingsFragment, FRAGMENT_TAG_NEARBY);
                        break;
                }
                return true;
            }
        });

        final FragmentManager manager = getSupportFragmentManager();
        homeFragment = (HomeFragment) manager.findFragmentByTag(FRAGMENT_TAG_HOME);
        favoritesFragment = (FavoritesFragment) manager.findFragmentByTag(FRAGMENT_TAG_FAVORITES);
        settingsFragment = (SettingsFragment) manager.findFragmentByTag(FRAGMENT_TAG_NEARBY);

        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        if (favoritesFragment == null) {
            favoritesFragment = new FavoritesFragment();
        }
        if (settingsFragment == null) {
            settingsFragment = new SettingsFragment();
        }

        if (savedInstanceState == null) {
            switchFragment(homeFragment, FRAGMENT_TAG_HOME);
        }
    }

    private void switchFragment(@NonNull Fragment fragment, String tag) {
        if (fragment.isAdded()) {
            return;
        }

        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction ft = manager.beginTransaction();

        final Fragment currentFragment = manager.findFragmentById(R.id.container);
        if (currentFragment != null) {
            ft.detach(currentFragment);
        }
        if (fragment.isDetached()) {
            ft.attach(fragment);
        } else {
            ft.add(R.id.container, fragment, tag);
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}