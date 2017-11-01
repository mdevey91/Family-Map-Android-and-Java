package com.bignerdranch.android.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import SharedFiles.EventResult;
import SharedFiles.Family;
import SharedFiles.FiltersAndSettings;
import SharedFiles.RecycleModel;

public class FilterActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<RecycleModel> mItems = new ArrayList<>();
    private FiltersAndSettings settings = FiltersAndSettings.getInstance();

    private Switch father_side_switch;
    private Switch mother_side_switch;
    private Switch female_switch;
    private Switch male_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_filter);

        father_side_switch = (Switch)findViewById(R.id.father_side_switch);
        father_side_switch.setChecked(settings.isFatherSideBool());
        father_side_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FiltersAndSettings.getInstance().setFatherSideBool(isChecked);
            }
        });

        mother_side_switch = (Switch)findViewById(R.id.mother_side_switch);
        mother_side_switch.setChecked(settings.isMotherSideBool());
        mother_side_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FiltersAndSettings.getInstance().setMotherSideBool(isChecked);
            }
        });

        female_switch = (Switch)findViewById(R.id.female_switch);
        female_switch.setChecked(settings.isFemaleBool());
        female_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FiltersAndSettings.getInstance().setFemaleBool(isChecked);
            }
        });

        male_switch = (Switch)findViewById(R.id.male_switch);
        male_switch.setChecked(settings.isMaleBool());
        male_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FiltersAndSettings.getInstance().setMaleBool(isChecked);
            }
        });


        ArrayList<String> descriptions = new ArrayList<>();
        if(!FiltersAndSettings.getInstance().getFilters().isEmpty()) {
            for (EventResult event : Family.getInstance().getAllEvents()) {
                RecycleModel model = new RecycleModel(settings.getBoolFromEventType(event.getDescription().toLowerCase()), event);
                if(!descriptions.contains(event.getDescription().toLowerCase()))
                {
                    descriptions.add(event.getDescription().toLowerCase());
                    mItems.add(model);
                }
            }
        }
        else
        {
            for (EventResult event : Family.getInstance().getAllEvents()){
                if(!FiltersAndSettings.getInstance().getFilters().containsKey(event.getDescription().toLowerCase()))
                {
                    mItems.add(new RecycleModel(true, event));
                    FiltersAndSettings.getInstance().setFilter(true, event.getDescription().toLowerCase());
                }
            }
        }

        mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager)mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
        }
        return true;
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.FilterViewHolder> {
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class FilterViewHolder extends RecyclerView.ViewHolder {

            protected TextView mBoldText = null;
            protected TextView mSmallText = null;
            protected Switch mSwitch = null;
            // each data item is just a string in this case
            public FilterViewHolder(View v) {
                super(v);
                mBoldText = (TextView)v.findViewById(R.id.bold_text);
                mSmallText = (TextView)v.findViewById(R.id.small_text);
                mSwitch = (Switch)v.findViewById(R.id.switch_holder);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public RecyclerAdapter() {}

        // Create new views (invoked by the layout manager)
        @Override
        public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_piece, parent, false);
            // set the view's size, margins, paddings and layout parameters
            //...
            FilterViewHolder vh = new FilterViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager). What actaully displays the views.
        @Override
        public void onBindViewHolder(FilterViewHolder holder, final int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.mSwitch.setChecked(settings.getBoolFromEventType(mItems.get(position).getEvent().getDescription().toLowerCase()));
            holder.mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //set the map to isChecked;
                    FiltersAndSettings.getInstance().setFilter(isChecked, mItems.get(position).getEvent().getDescription().toLowerCase());
                }
            });
            holder.mBoldText.setText(mItems.get(position).getBoldText());
            holder.mSmallText.setText(mItems.get(position).getSmallText());
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mItems.size();
        }


        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        public void addLine(RecycleModel line) {
            mItems.add(line);
            notifyDataSetChanged();
        }

        public void removeLine(int lineNumber) {
            mItems.remove(lineNumber);
        }


    }

}
