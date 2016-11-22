package org.esiea.praddaude.aura;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder>
{
    private JSONArray news;

    NewsAdapter(JSONObject news) {
        try {
            this.news = news.getJSONArray("news");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class NewsHolder extends RecyclerView.ViewHolder
    {
        public TextView name, date, contenu;

        public NewsHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.rv_news_element_name);
            date = (TextView) view.findViewById(R.id.rv_news_element_date);
            contenu = (TextView) view.findViewById(R.id.rv_news_element_contenu);
        }
    }

    public void setNewNews(JSONObject j)
    {
        try {
            news = j.getJSONArray("news");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(NewsHolder h, int p)
    {
        try {
            h.name.setText(news.getJSONObject(p).getString("title"));
            h.date.setText(news.getJSONObject(p).getString("date"));
            h.contenu.setText(news.getJSONObject(p).getString("msg"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount()
    {
        return news.length();
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_news_element, parent, false);

        return new NewsHolder(itemView);
    }
}
