package com.bignerdranch.android.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private List<String> people;
    private List<String> events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
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

//    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.SearchViewHolder> {
//
//        // Provide a reference to the views for each data item
//        // Complex data items may need more than one view per item, and
//        // you provide access to all the views for a data item in a view holder
//        public class SearchViewHolder extends RecyclerView.ViewHolder {
//            // each data item is just a string in this case
//            public TextView mTextView;
//            public SearchViewHolder(TextView v) {
//                super(v);
//                mTextView = (TextView)v.findViewById(R.id.textLine);
//            }
//        }
//
//        // Provide a suitable constructor (depends on the kind of dataset)
//        public MyAdapter(List<String> lines) {
//            this.lines = lines;
//        }
//
//        // Create new views (invoked by the layout manager)
//        @Override
//        public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            // create a new view
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
//            // set the view's size, margins, paddings and layout parameters
//            //...
//            SearchViewHolder vh = new SearchViewHolder((TextView) v);
//            return vh;
//        }
//
//        // Replace the contents of a view (invoked by the layout manager). What actaully displays the views.
//        @Override
//        public void onBindViewHolder(SearchViewHolder holder, int position) {
//            // - get element from your dataset at this position
//            // - replace the contents of the view with that element
//            holder.mTextView.setText(lines.get(position));
//        }
//
//        // Return the size of your dataset (invoked by the layout manager)
//        @Override
//        public int getItemCount() {
//            return lines.size();
//        }
//
//
//        @Override
//        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//            super.onAttachedToRecyclerView(recyclerView);
//        }
//
//        public void addLine(String line) {
//            lines.add(line);
//            notifyDataSetChanged();
//        }
//
//        public void removeLine(int lineNumber) {
//            lines.remove(lineNumber);
//        }
}
