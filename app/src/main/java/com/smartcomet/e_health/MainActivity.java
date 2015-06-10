package com.smartcomet.e_health;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;

    private static final String TAG_DOCTORS = "doctors";
    private static final String TAG_FIRST_NAME = "first_name";
    private static final String TAG_LAST_NAME = "last_name";
    private static final String TAG_WORK_LOCATION = "work_location";
    private static final String TAG_PHONE_NUMBER = "phone_number";
    private static final String TAG_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String first_name = intent.getStringExtra(TAG_FIRST_NAME);
        String last_name = intent.getStringExtra(TAG_LAST_NAME);
        String work_location = intent.getStringExtra(TAG_WORK_LOCATION);
        String phone_number = intent.getStringExtra(TAG_PHONE_NUMBER);
        String email= intent.getStringExtra(TAG_EMAIL);

        Bundle bundle = new Bundle();
        bundle.putString(TAG_FIRST_NAME, first_name);
        bundle.putString(TAG_LAST_NAME, last_name);
        bundle.putString(TAG_WORK_LOCATION, work_location);
        bundle.putString(TAG_PHONE_NUMBER, phone_number);
        bundle.putString(TAG_EMAIL, email);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData("Dino Bektas", "dinobektas@gmail.com", BitmapFactory.decodeResource(getResources(), R.drawable.avatar));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment fragment;
        switch (position) {

            case 0: //newsfeed
                fragment = getFragmentManager().findFragmentByTag(NewsFeedFragment.TAG);
                if (fragment == null) {
                    fragment = new NewsFeedFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, NewsFeedFragment.TAG).commit();
                break;

            case 1: //doctors
                fragment = getFragmentManager().findFragmentByTag(DoctorsFragment.TAG);
                if (fragment == null) {
                    fragment = new DoctorsFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, DoctorsFragment.TAG).commit();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}