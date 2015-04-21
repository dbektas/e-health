package com.smartcomet.e_health;

import android.app.Fragment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            case 0: //people
                fragment = getFragmentManager().findFragmentByTag(PeopleFragment.TAG);
                if (fragment == null) {
                    fragment = new PeopleFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, PeopleFragment.TAG).commit();
                break;
            case 1: //favorites
                fragment = getFragmentManager().findFragmentByTag(FavoritesFragment.TAG);
                if (fragment == null) {
                    fragment = new FavoritesFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, FavoritesFragment.TAG).commit();
                break;
            case 2: //chat
                fragment = getFragmentManager().findFragmentByTag(ChatFragment.TAG);
                if (fragment == null) {
                    fragment = new ChatFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, ChatFragment.TAG).commit();
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