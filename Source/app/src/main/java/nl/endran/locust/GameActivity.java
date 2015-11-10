package nl.endran.locust;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class GameActivity extends AppCompatActivity {

    private StringResourceMap stringResourceMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        stringResourceMap = new StringResourceMap(getResources());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new GameFragmentPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private class GameFragmentPagerAdapter extends FragmentPagerAdapter {

        @NonNull
        private final Fragment[] fragments;

        public GameFragmentPagerAdapter(@NonNull final FragmentManager fm) {
            super(fm);
            fragments = new Fragment[3];
            fragments[0] = new UnitsFragment();
            fragments[1] = new ResourcesFragment();
            fragments[2] = new TerritoryFragment();
        }

        @Override
        public Fragment getItem(final int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(final int position) {
            return stringResourceMap.getString(stringResourceMap.getStringRes(getItem(position)));
        }
    }
}
