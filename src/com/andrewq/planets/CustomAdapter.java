package com.andrewq.planets;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Andrew Quebe on 4/14/14.
 */
public class CustomAdapter extends BaseAdapter {

    Context context;
    List<RowItem> rowItem;

    CustomAdapter(Context context, List<RowItem> rowItem) {
        this.context = context;
        this.rowItem = rowItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.drawable.ic_launcher);
            holder.title = (TextView) convertView.findViewById(R.string.app_name);

            RowItem row_pos = rowItem.get(position);
            // setting the image resource and title
            holder.icon.setImageResource(row_pos.getIcon());
            holder.title.setText(row_pos.getTitle());
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;

    }

    @Override
    public int getCount() {
        return rowItem.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItem.indexOf(getItem(position));
    }

    private class ViewHolder {
        ImageView icon;
        TextView title;
    }

}
