package org.esiea.praddaude.aura;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Locale;

public class AvancementActivity extends AppCompatActivity {

    SharedPreferences prefs;
    private Resources res;
    public static final String NEWS_UPDATE = "NEWS_UPDATE";
    private NewsAdapter mAdapter;
    private RecyclerView rv;
    private JSONObject obj=null;
    private boolean enter = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avancement);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        res = getResourcesByLocale(this, prefs.getString("language", "fr"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.menu_12);

        obj = readFileNews(getCacheDir());
        IntentFilter intentFilter = new IntentFilter(NEWS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new NewsUpdate(), intentFilter);

        setDetails();
        mAdapter = new NewsAdapter(obj);
        rv = (RecyclerView)findViewById(R.id.rv_news);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager((RecyclerView.LayoutManager)lm);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(), lm.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
        rv.setAdapter(mAdapter);
    }

    public void services(View v)
    {
        TextView tVmaj = (TextView)findViewById(R.id.tVmaj);
        tVmaj.setTextColor(Color.YELLOW);
        tVmaj.setText(R.string.avanc_msg_23);

        ((Button)findViewById(R.id.button2)).setEnabled(false);
        enter = true;
        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                ((Button)findViewById(R.id.button2)).setEnabled(true);
            }
        }, 5000);

        GetNewsServices.startActionGetAllNews(this);
        obj = readFileNews(getCacheDir());
    }

    public class NewsUpdate extends BroadcastReceiver {
        public static final String tag = "tag";

        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (enter == false) return;
            else enter = false;
            Toast.makeText(getApplicationContext(), getString(R.string.end_dl), Toast.LENGTH_LONG).show();
            try {
                if (prefs.getInt("news_version", 0) == obj.getInt("version"))
                {
                    TextView tVmaj = (TextView)findViewById(R.id.tVmaj);
                    tVmaj.setTextColor(Color.rgb(255,127,0));
                    tVmaj.setText(R.string.avanc_msg_21);
                }
                else
                {
                    TextView tVmaj = (TextView)findViewById(R.id.tVmaj);
                    tVmaj.setTextColor(Color.GREEN);
                    tVmaj.setText(R.string.avanc_msg_22);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                TextView tVmaj = (TextView)findViewById(R.id.tVmaj);
                tVmaj.setTextColor(Color.RED);
                tVmaj.setText(R.string.avanc_msg_24);
            }
            setDetails();
            ((NewsAdapter)rv.getAdapter()).setNewNews(obj);
        }
    }

    public void setDetails()
    {
        try {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("news_version", obj.getInt("version"));
            editor.commit();
            TextView tVperc = (TextView)findViewById(R.id.tVperc);
            tVperc.setText(obj.getString("percent")+"%");
            ProgressBar pB = (ProgressBar)findViewById(R.id.progressBar5);
            pB.setProgress(obj.getInt("percent"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject readFileNews(File cachedir) {
        String filename = cachedir.toString()+"/news.json";
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }

        JSONObject obj = null;
        try {
            obj = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
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
