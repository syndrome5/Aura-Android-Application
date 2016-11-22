package org.esiea.praddaude.aura;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetNewsServices extends IntentService {
    private static final String ACTION_GET_ALL_NEWS = "GET_ALL_NEWS";
    public static final String tag = "tag";

    public GetNewsServices() {
        super("GetNewsServices");
    }

    public static void startActionGetAllNews(Context context) {
        Intent intent = new Intent(context, GetNewsServices.class);
        intent.setAction(ACTION_GET_ALL_NEWS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_ALL_NEWS.equals(action)) {
                handleActionGetAllNews();
            }
        }
    }

    private void copyInputStreamToFile(InputStream in, File file)
    {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len=in.read(buf))> 0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleActionGetAllNews() {
        Log.d(tag, "Thread service name:" + Thread.currentThread().getName());
        URL url = null;
        try {
            url = new URL("http://elan.pe.hu/aura/news-fr.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                copyInputStreamToFile(conn.getInputStream(), new File(getCacheDir(), "news.json"));
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(AvancementActivity.NEWS_UPDATE));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}