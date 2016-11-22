package org.esiea.praddaude.aura;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    ArrayList<HashMap<String, String>> data;
    private String[] titleArray,subItemArray;
    SharedPreferences prefs;
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        res = getResourcesByLocale(this, prefs.getString("language", "fr"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.action_settings);

        final ListView lv = (ListView)findViewById(R.id.listView);
        final String languageN = this.getString(R.string.language);

        data = new ArrayList<HashMap<String, String>>();
        titleArray=new String[]{languageN};
        subItemArray=new String[]{getResources().getConfiguration().locale.getDisplayLanguage().substring(0, 1).toUpperCase() + getResources().getConfiguration().locale.getDisplayLanguage().substring(1)};
        for(int i=0;i<titleArray.length;i++){
            HashMap<String,String> datum = new HashMap<String, String>();
            datum.put("Setting", titleArray[i]);
            datum.put("State", subItemArray[i]);
            data.add(datum);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, new String[] {"Setting", "State"}, new int[] {android.R.id.text1, android.R.id.text2});
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                HashMap<String,String> map = (HashMap) lv.getItemAtPosition(position);
                AlertDialog levelDialog = null;
                if (map.get("Setting") == languageN)
                {
                    final CharSequence[] day_radio = {"Français", "English"};

                    AlertDialog.Builder builder2 = new AlertDialog.Builder(SettingsActivity.this)
                            .setTitle(R.string.language_question)
                            .setSingleChoiceItems(day_radio, -1, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (day_radio[which].toString())
                                    {
                                        case "Français":
                                            setLocale("fr");
                                            break;
                                        case "English":
                                            setLocale("en");
                                            break;
                                    }
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alertdialog2 = builder2.create();
                    alertdialog2.show();
                }
            }
        });
    }

    Resources getResourcesByLocale(Context context, String localeName ) {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = new Configuration(res.getConfiguration());
        conf.locale = new Locale(localeName);
        res.updateConfiguration(conf, dm);
        return new Resources(res.getAssets(), res.getDisplayMetrics(), conf);
    }

    public void setLocale(String lang) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("language", lang);
        editor.commit();
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        finish();
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
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
