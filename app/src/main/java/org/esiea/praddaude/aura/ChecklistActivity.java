package org.esiea.praddaude.aura;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import java.util.Locale;

public class ChecklistActivity extends AppCompatActivity {

    SharedPreferences prefs;
    private Resources res;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        res = getResourcesByLocale(this, prefs.getString("language", "fr"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.menu_14);

        mViewPager = (ViewPager) findViewById(R.id.activity_checklist);
        mViewPager.setAdapter(new SamplePagerAdapter(getSupportFragmentManager()));
    }

    Resources getResourcesByLocale(Context context, String localeName ) {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = new Configuration(res.getConfiguration());
        conf.locale = new Locale(localeName);
        res.updateConfiguration(conf, dm);
        return new Resources(res.getAssets(), res.getDisplayMetrics(), conf);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SamplePagerAdapter extends FragmentPagerAdapter {

        public SamplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new ChecklistPage1();
            } else if (position == 1) {
                return new ChecklistPage2();
            } else if (position == 2) {
                return new ChecklistPage3();
            } else if (position == 3) {
                return new ChecklistPage4();
            } else if (position == 4) {
                return new ChecklistPage5();
            } else if (position == 5) {
                return new ChecklistPage6();
            } else if (position == 6) {
                return new ChecklistPage7();
            } else if (position == 7) {
                return new ChecklistPage8();
            } else return new ChecklistPage1();
        }

        @Override
        public int getCount() {
            return 8;
        }
    }
}
