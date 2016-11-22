package org.esiea.praddaude.aura;

import java.util.ArrayList;
import java.util.TreeSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

class CustomAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    private ArrayList<String> mData = new ArrayList<String>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
    private ArrayList<Integer> mImg = new ArrayList<Integer>();

    private LayoutInflater mInflater;

    public CustomAdapter(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final String item, final int res) {
        mData.add(item);
        mImg.add(res);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final String item, final int res) {
        mData.add(item);
        mImg.add(res);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ImageHolder{
        ImageView image;
        TextView title;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageHolder holder = new ImageHolder();
        int rowType = getItemViewType(position);

        if (convertView == null) {
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.snipper_item1, null);
                    holder.title = (TextView) convertView.findViewById(R.id.text);
                    holder.image = (ImageView) convertView.findViewById(R.id.imageView);
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.snipper_item2, null);
                    holder.title = (TextView) convertView.findViewById(R.id.textSeparator);
                    holder.image = (ImageView) convertView.findViewById(R.id.imageView2);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ImageHolder) convertView.getTag();
        }

        holder.title.setText(mData.get(position));
        holder.image.setImageResource(mImg.get(position));
        return convertView;
    }

}