package com.example.practiceudemy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class FeedAdapter<T extends FeedEntry> extends ArrayAdapter {

    private static final String TAG = "FeedAdapter";
    private final int layoutResource;
    private LayoutInflater layoutInflater;
    List<T> applications;


    public FeedAdapter(Context context, int layoutResource, List<T> applications) {
        super(context, layoutResource);
        this.applications = applications;
        this.layoutResource = layoutResource;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return applications.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
         ViewHolder viewHolder;
        if(convertView==null){
            convertView = layoutInflater.inflate(layoutResource,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
           viewHolder=(ViewHolder) convertView.getTag();
        }

        T currentEntry = applications.get(position);

        viewHolder.tvName.setText(currentEntry.getName());
        viewHolder.tvArtist.setText(currentEntry.getArtist());
        viewHolder.tvSummary.setText(currentEntry.getSummery());
return convertView;
    }


    private class ViewHolder {
        final TextView tvName ;
        final TextView tvArtist;
        final TextView tvSummary;

        ViewHolder(View v){
            tvName = v.findViewById(R.id.tvName);
            tvArtist = v.findViewById(R.id.tvArtist);
            tvSummary = v.findViewById(R.id.tvSummary);
        }
    }
}
