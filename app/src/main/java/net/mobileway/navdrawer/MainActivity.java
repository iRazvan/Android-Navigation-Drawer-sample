package net.mobileway.navdrawer;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.mobileway.navdrawer.fragments.AboutFragment;
import net.mobileway.navdrawer.fragments.HomeFragment;
import net.mobileway.navdrawer.fragments.NavigationDrawerFragment;
import net.mobileway.navdrawer.fragments.PlanetFragment;
import net.mobileway.navdrawer.utils.Constants;
import net.mobileway.navdrawer.utils.LogUtils;


public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String TAG = MainActivity.class.getSimpleName();

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Fragment mContent = null;
    private View mContentFrame;
    private int mPrevPosition = -1;
    private String mTitle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContentFrame = findViewById(R.id.content_frame);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        if (mContent == null) {
            mContent = new HomeFragment();
            mTitle = Constants.HOME;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mContent).commit();

        setUpNavigationDrawerFragment();
        mContentFrame.setVisibility(View.VISIBLE);


    }


    public void switchFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void switchFragment(int position) {

        // update the main content by replacing fragments
        switch (position) {
            case 0:
                mContent = new HomeFragment();
                mTitle = Constants.HOME;
                break;
            case 1:
                mContent = new PlanetFragment();
                Bundle bun = new Bundle();
                bun.putInt(PlanetFragment.ARG_PLANET_NUMBER, 2);
                mContent.setArguments(bun);
                mTitle = Constants.PLANETS;
                break;
            case 2:
                // about page
                mContent = new AboutFragment();
                mTitle = Constants.ABOUT;
                break;
            case 3:
                LogUtils.LOGE(TAG, "Clicked on: DEFAULT");
                // TODO


                break;
            default:
                mContent = new HomeFragment();
                mTitle = Constants.HOME;
                break;
        }
        switchFragment(mContent);
    }

    public void setUpNavigationDrawerFragment() {
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(final int position) {

        //for the first time we enter the app those are null
        if (mNavigationDrawerFragment == null || mNavigationDrawerFragment.getDrawerLayout() == null) {
            switchFragment(position);
            return;
        }


        //don't hide the content frame if we select the same fragment
        if (mPrevPosition != position) {
            mContentFrame.setVisibility(View.GONE);
        }

        //switch to the selected fragment only when the drawer is closed => avoiding drawer glitches
        mNavigationDrawerFragment.getDrawerLayout().setDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View view, float v) {
                        mNavigationDrawerFragment.getDrawerToggle().onDrawerSlide(view, v);
                    }

                    @Override
                    public void onDrawerOpened(View view) {
                        mNavigationDrawerFragment.getDrawerToggle().onDrawerOpened(view);
                    }

                    @Override
                    public void onDrawerClosed(View view) {
                        if (mPrevPosition != position) {
                            switchFragment(position);
                        }
                        mPrevPosition = position;
                        mContentFrame.setVisibility(View.VISIBLE);
                        mNavigationDrawerFragment.getDrawerToggle().onDrawerClosed(view);
                    }

                    @Override
                    public void onDrawerStateChanged(int i) {

                    }
                }
        );
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
//            getMenuInflater().inflate(R.menu.main_vm, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
