package org.esiea.praddaude.aura;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import java.util.Locale;

public class MediasActivity extends AppCompatActivity {

    SharedPreferences prefs;
    private Resources res;
    WebView wb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medias);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        res = getResourcesByLocale(this, prefs.getString("language", "fr"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.menu_13);

        wb = (WebView)findViewById(R.id.wb);
        wb.setInitialScale(1);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.getSettings().setUseWideViewPort(true);
    }

    public void load(View v)
    {
        wb.loadUrl("http://elan.pe.hu/aura/");
        ((Button)findViewById(R.id.button3)).setEnabled(false);

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                ((Button)findViewById(R.id.button3)).setEnabled(true);
                mBuilder.setSmallIcon(R.drawable.ic_stat_plane);
                mBuilder.setContentTitle("Pleins de photos");
                mBuilder.setContentText("Mise à jour des médias disponible !");
                NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                nm.notify(0, mBuilder.build());
            }
        }, 10000);
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
