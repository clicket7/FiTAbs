package com.example.student.fitabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by student on 17.18.7.
 */

public class StringAdapter extends ArrayAdapter<String> {
    /**
     * Create a new {@link StringAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param menu   is the list of contacts to be displayed.
     */

    public StringAdapter(Context context, ArrayList<String> menu) {
        super(context, 0, menu);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_string_item, parent, false);
        }
        // Get the string object located at this position in the list
        String currentPosition = getItem(position);
        // Find the TextView in the activity_.xml layout with the ID contactsName.
        TextView stringTextView = (TextView) listItemView.findViewById(R.id.exercisePoint);
        // Get the contacts name from the currentContact object and set this text on
        // the contactsName TextView.
        stringTextView.setText(currentPosition);
        // Find the TextView in the activity_contacts.xml layout with the ID contactsPhone.

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.// Find the View that shows the family category
        return listItemView;
    }
}
