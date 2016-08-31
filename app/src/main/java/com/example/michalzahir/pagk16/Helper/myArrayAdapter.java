package com.example.michalzahir.pagk16.Helper;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.michalzahir.pagk16.R;
import com.example.michalzahir.pagk16.RankingActivity;

import java.util.List;

/**
 * Created by zahirm on 2016-08-29.
 */
public class myArrayAdapter  extends ArrayAdapter<String> {
    public myArrayAdapter(Context context, int resource) {
        super(context, resource);
    }

    public myArrayAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public myArrayAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    public myArrayAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public myArrayAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    public myArrayAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        final View renderer = super.getView(position, convertView, parent);
        if (RankingActivity.Highlighted.contains(position)) {
            getViewByPosition(position, (ListView) renderer).setBackgroundColor(Color.parseColor("#40e0d0"));
        }
        return renderer;
    }
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

}
