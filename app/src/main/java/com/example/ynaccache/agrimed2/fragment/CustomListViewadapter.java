package com.example.ynaccache.agrimed2.fragment;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ynaccache.agrimed2.R;

import java.util.List;

/**
 * Created by y.naccache on 02/10/2017.
 */

public class CustomListViewadapter extends ArrayAdapter<RowItem> {

    Context context;

    public CustomListViewadapter(Context context, int resourceId,
                                 List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtId;
        TextView txtTitle;
        TextView txtQuant;
        TextView txtUnit;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_article, null);
            holder = new ViewHolder();
            holder.txtId = (TextView) convertView.findViewById(R.id.num);
            holder.txtQuant = (TextView) convertView.findViewById(R.id.quant);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.titre);
            holder.txtUnit = (TextView) convertView.findViewById(R.id.unite);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.txtId.setText(rowItem.getId());
        holder.txtQuant.setText(rowItem.getQuant());
        holder.txtTitle.setText(rowItem.getTitle());
        holder.txtUnit.setText(rowItem.getUnite());
       // holder.imageView.setImageResource(rowItem.getImageId());

        return convertView;
    }
}
