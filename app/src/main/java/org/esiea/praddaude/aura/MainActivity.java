package org.esiea.praddaude.aura;

// Toast.makeText(SettingsActivity.this, map.get("State"), Toast.LENGTH_SHORT).show();

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static org.esiea.praddaude.aura.R.id.navList;
import static org.esiea.praddaude.aura.R.id.textView2;

public class MainActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private CustomAdapter mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private SharedPreferences prefs;
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        res = getResourcesByLocale(this, prefs.getString("language", "fr"));
        TextView home_msg_1 = (TextView)findViewById(R.id.textView2);
        home_msg_1.setText(res.getString(R.string.home_msg_1));
        TextView home_msg_2 = (TextView)findViewById(R.id.textView);
        home_msg_2.setText(res.getString(R.string.home_msg_2));
        TextView home_msg_3 = (TextView)findViewById(R.id.textView5);
        home_msg_3.setText(res.getString(R.string.home_msg_3));

        mDrawerList = (ListView)findViewById(navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    Resources getResourcesByLocale( Context context, String localeName ) {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = new Configuration(res.getConfiguration());
        conf.locale = new Locale(localeName);
        res.updateConfiguration(conf, dm);
        return new Resources(res.getAssets(), res.getDisplayMetrics(), conf);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_about) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.about_title);
            builder.setMessage(R.string.about);
            builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
            builder.show();
            return true;
        }
        else if (id == R.id.action_repair)
        {
            GetNewsServices.startActionGetAllNews(this);
            return true;
        }

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addDrawerItems() {
        final String menu_11 = this.getString(R.string.menu_11);
        final String menu_12 = this.getString(R.string.menu_12);
        final String menu_13 = this.getString(R.string.menu_13);
        final String menu_14 = this.getString(R.string.menu_14);
        final String menu_21 = this.getString(R.string.menu_21);
        final String menu_22 = this.getString(R.string.menu_22);
        final String menu_23 = this.getString(R.string.menu_23);
        final String menu_24 = this.getString(R.string.menu_24);

        mAdapter = new CustomAdapter(this);
        mAdapter.addSectionHeaderItem(this.getString(R.string.menu_1), R.drawable.menu_1);
        mAdapter.addItem(menu_11, R.drawable.menu_11);
        mAdapter.addItem(menu_12, R.drawable.menu_12);
        mAdapter.addItem(menu_13, R.drawable.menu_13);
        mAdapter.addItem(menu_14, R.drawable.menu_14);
        mAdapter.addSectionHeaderItem(this.getString(R.string.menu_2), R.drawable.menu_2);
        mAdapter.addItem(menu_21, R.drawable.menu_21);
        mAdapter.addItem(menu_22, R.drawable.menu_22);
        mAdapter.addItem(menu_23, R.drawable.menu_23);
        mAdapter.addItem(menu_24, R.drawable.menu_24);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String n = (String) parent.getItemAtPosition(position);
                if (n == menu_11) {
                    Intent intent = new Intent(MainActivity.this, ProjetActivity.class);
                    startActivity(intent);
                } else if (n == menu_12) {
                    Intent intent = new Intent(MainActivity.this, AvancementActivity.class);
                    startActivity(intent);
                } else if (n == menu_13) {
                    Intent intent = new Intent(MainActivity.this, MediasActivity.class);
                    startActivity(intent);
                } else if (n == menu_14) {
                    Intent intent = new Intent(MainActivity.this, ChecklistActivity.class);
                    startActivity(intent);
                } else if (n == menu_21) {
                    Intent intent = new Intent(MainActivity.this, CessnaActivity.class);
                    startActivity(intent);
                } else if (n == menu_22) {
                    Intent intent = new Intent(MainActivity.this, MoteurActivity.class);
                    startActivity(intent);
                } else if (n == menu_23) {
                    Intent intent = new Intent(MainActivity.this, InstruActivity.class);
                    startActivity(intent);
                } else if (n == menu_24) {
                    Intent intent = new Intent(MainActivity.this, RadioActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
}