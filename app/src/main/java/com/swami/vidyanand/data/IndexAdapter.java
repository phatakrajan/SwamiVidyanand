package com.swami.vidyanand.data;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.swami.vidyanand.R;

import java.util.List;

/**
 * Created by Rajan_Phatak on 10/11/2015.
 */
public class IndexAdapter extends ArrayAdapter<IndexCommon> {

    Context context;

    public IndexAdapter(Context context, int resourceId,
                         List<IndexCommon> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /* private view holder class */
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try {
            ViewHolder holder = null;
            IndexCommon rowItem = getItem(position);

            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.activity_grid_item,
                        null);
                holder = new ViewHolder();
                holder.txtTitle = (TextView) convertView
                        .findViewById(R.id.title);
                holder.imageView = (ImageView) convertView
                        .findViewById(R.id.icon);
                convertView.setTag(holder);
            } else
                holder = (ViewHolder) convertView.getTag();

            holder.txtTitle.setText(rowItem.get_title());
            holder.imageView.setImageDrawable(IndexGroup.getImageDrawable(
                    context, rowItem.getImagePath()));

        } catch (Exception e) {

        }
        return convertView;
    }

}
