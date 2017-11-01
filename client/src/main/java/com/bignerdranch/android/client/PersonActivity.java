package com.bignerdranch.android.client;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;

import SharedFiles.EventResult;
import SharedFiles.Family;
import SharedFiles.Pair;
import SharedFiles.PersonResult;

public class PersonActivity extends AppCompatActivity {

    private static final String EVENT_KEY = "event_key";
    private PersonResult person;
    private ArrayList<EventResult> events;
    private ArrayList<Pair> generations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        Intent intent = getIntent();
        String person_id = intent.getStringExtra(EVENT_KEY);
        events = Family.getInstance().getEventsFromPersonID(person_id);
        person = Family.getInstance().getPersonfromId(person_id);

        TextView first_name_text = (TextView) findViewById(R.id.first_name);
        TextView last_name_text = (TextView) findViewById(R.id.last_name);
        TextView gender_text = (TextView) findViewById(R.id.gender);

        first_name_text.setText(person.getFirstname());
        last_name_text.setText(person.getLastname());
        String gender;
        if(person.getGender() == "m")
            gender = "Male";
        else
            gender = "Female";
        gender_text.setText(gender);

        ExpandableListAdapter listAdapter = new ListAdapter(getBaseContext()); //getBaseContext() gets the context from the currently running activity. You just need a context.
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.expandable_view); //ExpandableListView is a built in type from the widget library
        listView.setAdapter(listAdapter); //creates the list view

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            /**
             * Callback method to be invoked when a child in this expandable list has
             * been clicked.
             *
             * @param parent        The ExpandableListView where the click happened
             * @param v             The view within the expandable list/ListView that was clicked
             * @param groupPosition The group position that contains the child that
             *                      was clicked
             * @param childPosition The child position within the group
             * @param id            The row id of the child that was clicked
             * @return True if the click was handled
             */
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String headerName = (String) parent.getExpandableListAdapter().getGroup(groupPosition);
                String childString = (String) parent.getExpandableListAdapter().getChild(groupPosition, childPosition);
                return true;
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.up_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()) {
            case R.id.topItem:
                intent = new Intent(getBaseContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case android.R.id.home:
                this.finish();
                return true;
                
            default:
        }
        return true;
    }
    public class ListAdapter extends BaseExpandableListAdapter {
        private Context context;

        public String[] headers = {"LIFE EVENTS", "FAMILY"};
        private String[] life_events;
        private String[] family_members;
        private String[][] groups;

        //most of these functions are called by the OS
        public ListAdapter(Context context) {
            life_events = new String[events.size()];
            generations = Family.getInstance().getFamilyMembers(person.getPersonID());
            family_members = new String[generations.size()];
            for(int i = 0; i < life_events.length; i++)
            {
                String event_string = events.get(i).getDescription() + ":" + events.get(i).getCity() + ", " + events.get(i).getCountry() + "(" + events.get(i).getYear() + ")\n" + person.getFirstname() + " " + person.getLastname();
                life_events[i] = event_string;
            }
            for(int i = 0; i < generations.size();i++)
            {
                String family_string = generations.get(i).getPerson().getFirstname() + " " + generations.get(i).getPerson().getLastname() + "\n" + generations.get(i).getRelation();
                family_members[i] = family_string;
            }

            this.context = context;

            groups = new String[][] {life_events, family_members};
        }

        /**
         * Gets the number of groups.
         *
         * @return the number of groups
         */
        @Override
        public int getGroupCount() {
            return groups.length;
        }

        /**
         * Gets the number of children in a specified group.
         *
         * @param groupPosition the position of the group for which the children
         *                      count should be returned
         * @return the children count in the specified group
         */
        @Override
        public int getChildrenCount(int groupPosition) {
            return groups[groupPosition].length;
        }

        /**
         * Gets the data associated with the given group.
         *
         * @param groupPosition the position of the group
         * @return the data child for the specified group
         */
        @Override
        public Object getGroup(int groupPosition) {
            return headers[groupPosition];
        }

        /**
         * Gets the data associated with the given child within the given group.
         *
         * @param groupPosition the position of the group that the child resides in
         * @param childPosition the position of the child with respect to other
         *                      children in the group
         * @return the data of the child
         */
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return groups[groupPosition][childPosition];
        }

        /**
         * Gets the ID for the group at the given position. This group ID must be
         * unique across groups. The combined ID (see
         * {@link #getCombinedGroupId(long)}) must be unique across ALL items
         * (groups and all children).
         *
         * @param groupPosition the position of the group for which the ID is wanted
         * @return the ID associated with the group
         */
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        /**
         * Gets the ID for the given child within the given group. This ID must be
         * unique across all children within the group. The combined ID (see
         * {@link #getCombinedChildId(long, long)}) must be unique across ALL items
         * (groups and all children).
         *
         * @param groupPosition the position of the group that contains the child
         * @param childPosition the position of the child within the group for which
         *                      the ID is wanted
         * @return the ID associated with the child
         */
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        /**
         * Indicates whether the child and group IDs are stable across changes to the
         * underlying data.
         *
         * @return whether or not the same ID always refers to the same object
         */
        @Override
        public boolean hasStableIds() {
            return true;
        }

        /**
         * Gets a View that displays the given group. This View is only for the
         * group--the Views for the group's children will be fetched using
         * {@link #getChildView(int, int, boolean, View, ViewGroup)}.
         *
         * @param groupPosition the position of the group for which the View is
         *                      returned
         * @param isExpanded    whether the group is expanded or collapsed
         * @param convertView   the old view to reuse, if possible. You should check
         *                      that this view is non-null and of an appropriate type before
         *                      using. If it is not possible to convert this view to display
         *                      the correct data, this method can create a new view. It is not
         *                      guaranteed that the convertView will have been previously
         *                      created by
         *                      {@link #getGroupView(int, boolean, View, ViewGroup)}.
         * @param parent        the parent that this view will eventually be attached to
         * @return the View corresponding to the group at the specified position
         */
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if(convertView == null) {
                LayoutInflater inflaterInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflaterInflater.inflate(R.layout.big_bold_text_view, null);
            }

            TextView labelListHeader = (TextView) convertView.findViewById(R.id.bigBoldLine); //this inflates the headers
            labelListHeader.setText(headerTitle);

            return convertView;
        }

        /**
         * Gets a View that displays the data for the given child within the given
         * group.
         *
         * @param groupPosition the position of the group that contains the child
         * @param childPosition the position of the child (for which the View is
         *                      returned) within the group
         * @param isLastChild   Whether the child is the last child within the group
         * @param convertView   the old view to reuse, if possible. You should check
         *                      that this view is non-null and of an appropriate type before
         *                      using. If it is not possible to convert this view to display
         *                      the correct data, this method can create a new view. It is not
         *                      guaranteed that the convertView will have been previously
         *                      created by
         *                      {@link #getChildView(int, int, boolean, View, ViewGroup)}.
         * @param parent        the parent that this view will eventually be attached to
         * @return the View corresponding to the child at the specified position
         */
        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            String childString = (String) getChild(groupPosition, childPosition);
            if(convertView == null) {
                LayoutInflater inflaterInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflaterInflater.inflate(R.layout.my_text_view, null);
            }
            Drawable icon = null;

            TextView labelListHeader = (TextView) convertView.findViewById(R.id.textLine); //textline is the inside of the list
            labelListHeader.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    generations = Family.getInstance().getFamilyMembers(person.getPersonID());
                    if(groupPosition == 1) //The user just clicked on a person.
                    {
                        Intent i = new Intent(PersonActivity.this, PersonActivity.class);
                        i.putExtra(EVENT_KEY, generations.get(childPosition).getPerson().getPersonID());
                        startActivity(i);
                    }
                    else //The user just clicked on an event.
                    {
                        Intent i = new Intent(PersonActivity.this, MapsActivity.class);
                        i.putExtra(EVENT_KEY, events.get(childPosition).getEventID());
                        startActivity(i);
                    }
                }
            });


            ImageView img = (ImageView) convertView.findViewById(R.id.icon);
            if(groupPosition == 0)
            {
                icon = new IconDrawable(context, Iconify.IconValue.fa_map_marker).colorRes(R.color.male_icon).sizeDp(40);
            }
            else
            {
                if(generations.get(childPosition).getPerson().getGender().equals("m"))
                    icon = new IconDrawable(context, Iconify.IconValue.fa_male).colorRes(R.color.male_icon).sizeDp(40);
                else
                    icon = new IconDrawable(context, Iconify.IconValue.fa_female).colorRes(R.color.female_icon).sizeDp(40);
            }
            img.setImageDrawable(icon);

            labelListHeader.setText(childString);

            return convertView;
        }

        /**
         * Whether the child at the specified position is selectable.
         *
         * @param groupPosition the position of the group that contains the child
         * @param childPosition the position of the child within the group
         * @return whether the child is selectable.
         */
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
