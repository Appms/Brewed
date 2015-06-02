package com.example.ams.brewed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ams.brewed.data.SearchElement;

import java.util.List;

/**
 * Created al264101 on 02/06/15.
 */
public class SearchAdapter extends ArrayAdapter<SearchElement> {

    public SearchAdapter(Context context, int resource) {
        super(context, resource);
    }

    public SearchAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public SearchAdapter(Context context, int resource, SearchElement[] objects) {
        super(context, resource, objects);
    }

    public SearchAdapter(Context context, int resource, int textViewResourceId, SearchElement[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public SearchAdapter(Context context, int resource, List<SearchElement> objects) {
        super(context, resource, objects);
    }

    public SearchAdapter(Context context, int resource, int textViewResourceId, List<SearchElement> objects) {
        super(context, resource, textViewResourceId, objects);
    }


    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Context context = getContext();

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.search_element, null);
        }

        SearchElement searchElement = getItem(position);
        if (searchElement != null) {
            TextView name = (TextView) view.findViewById(R.id.elementName);
            ImageView icon = (ImageView) view.findViewById(R.id.elementIcon);

            if (name != null) {
                name.setText(searchElement.getName());
            }
            if (icon != null) {
                icon.setImageBitmap(searchElement.getLabel_icon());
            }
        }
        return view;
    }
}
