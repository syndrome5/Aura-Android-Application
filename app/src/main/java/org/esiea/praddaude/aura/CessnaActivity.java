package org.esiea.praddaude.aura;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class CessnaActivity extends AppCompatActivity {

    SharedPreferences prefs;
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cessna);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        res = getResourcesByLocale(this, prefs.getString("language", "fr"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.menu_21);

        Button button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "https://en.wikipedia.org/wiki/Cessna_182_Skylane";
                if (prefs.getString("language", "fr").equals("fr")) str="https://fr.wikipedia.org/wiki/Cessna_182";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(str));
                startActivity(browserIntent);
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
