package com.example.student.fitabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sandra Bogusa on 17.14.7.
 */

public class ContactsAdapter  extends ArrayAdapter<User> {

    /**
     * Create a new {@link ContactsAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param users is the list of contacts to be displayed.
     */
    public ContactsAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the user object located at this position in the list
        User currentContact = getItem(position);

        // Find the TextView in the activity_contacts.xml layout with the ID contactsName.
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.contactsName);
        // Get the contacts name from the currentContact object and set this text on
        // the contactsName TextView.
        nameTextView.setText(currentContact.getName());

        // Find the TextView in the activity_contacts.xml layout with the ID contactsPhone.
        TextView phoneTextView = (TextView) listItemView.findViewById(R.id.contactsPhone);
        // Get the contactsPhone from the currentContact object and set this text on
        // the contactsPhone TextView.
        phoneTextView.setText(currentContact.getTelnumber());

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}