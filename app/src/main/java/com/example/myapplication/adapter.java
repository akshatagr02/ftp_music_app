package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {

    private String[] localDataSet;
    private click listener;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView listtext;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            listtext = (TextView) view.findViewById(R.id.title_music);


        }

        public TextView getTextView() {
            return listtext;
        }

    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public adapter(String[] dataSet,click onClickListener ) {
        this.localDataSet = dataSet;
        this.listener = onClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card, viewGroup, false);



        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(localDataSet[position].replace(".mp4",""));
        viewHolder.itemView.setOnClickListener(view->{
            listener.onClick(localDataSet[position]);
        });
        viewHolder.listtext.setSelected(true);
    }
    public interface click {
        void onClick(String data);
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

}
