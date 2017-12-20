package com.example.rockhopper.mobiletest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rockhopper on 19-12-17.
 */

public class FeedAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<Feed> items; //data source of the list adapter

    //public constructor
    public FeedAdapter(Context context, ArrayList<Feed> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.feed_layout, parent, false);
        }

        // get current item to be displayed
        Feed currentItem = (Feed) getItem(position);

        // get the TextView for item name and item description
        TextView textViewTitle = (TextView)
                convertView.findViewById(R.id.title);
        TextView textViewAuthor = (TextView)
                convertView.findViewById(R.id.author);
        TextView textViewDate =  (TextView)
                convertView.findViewById(R.id.date);

        //sets the text for item name and item description from the current item object
        textViewTitle.setText(currentItem.getTitle());
        textViewAuthor.setText(currentItem.getAuthor());
        textViewDate.setText(currentItem.getDate());
        // returns the view for the current row
        return convertView;
    }
}