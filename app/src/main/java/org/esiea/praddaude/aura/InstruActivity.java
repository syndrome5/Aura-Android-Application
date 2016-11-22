package org.esiea.praddaude.aura;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.TabHost;

import java.util.Locale;

public class InstruActivity extends AppCompatActivity {

    SharedPreferences prefs;
    private Resources res;
    private TabHost host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instru);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        res = getResourcesByLocale(this, prefs.getString("language", "fr"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.menu_23);

        host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec(this.getString(R.string.instru_tab_1));
        spec.setContent(R.id.tab1);
        spec.setIndicator(this.getString(R.string.instru_tab_1));
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec(this.getString(R.string.instru_tab_2));
        spec.setContent(R.id.tab2);
        spec.setIndicator(this.getString(R.string.instru_tab_2));
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec(this.getString(R.string.instru_tab_3));
        spec.setContent(R.id.tab3);
        spec.setIndicator(this.getString(R.string.instru_tab_3));
        host.addTab(spec);

        try {
            int i = savedInstanceState.getInt("LastTab");
            host.setCurrentTab(i);
        } catch (NullPointerException e) {}
    }

    @Override
    protected void onSaveInstanceState (Bundle outState){
        outState.putInt("LastTab", host.getCurrentTab());
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
}
