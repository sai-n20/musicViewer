package com.example.cs478project2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    //Create arrays for various URL's required for functionality
    private ArrayList<String> nameList;
    private ArrayList<String> artistList;
    private ArrayList<String> videoList;
    private ArrayList<String> artistWikiList;
    private ArrayList<String> songWikiList;
    private RVClickListener RVlistener; //listener defined in main activity
    private Context context;

    /*
    passing in the data lists and the listener defined in the main activity
     */

    public MyAdapter(ArrayList<String> songnames, ArrayList<String> artistnames,
                     ArrayList<String> videolinks, ArrayList<String> artistWiki,
                     ArrayList<String> songsWiki, RVClickListener listener){
        nameList = songnames;
        artistList = artistnames;
        videoList = videolinks;
        artistWikiList = artistWiki;
        songWikiList = songsWiki;
        this.RVlistener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Inflate view for individual list/grid item defined in XML spec
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listView = inflater.inflate(R.layout.individual, parent, false);
        ViewHolder viewHolder = new ViewHolder(listView, RVlistener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Bind data items to view dynamically
        holder.song.setText(nameList.get(position));
        holder.artist.setText(artistList.get(position));
        context = holder.artist.getContext();

        //Image resource needs to be picked dynamically using resource id derived from file in res directory. Filename limitations have to be addressed by tailoring song name to image file name
        holder.image.setImageResource(context.getResources().getIdentifier(nameList.get(position).toLowerCase().replaceAll(" ", ""), "drawable", "com.example.cs478project2"));
    }

    //RecyclerView needs this for item generation
    @Override
    public int getItemCount() {
        return nameList.size();
    }




    /*
        This class creates a wrapper object around a view that contains the layout for
         an individual item in the list. It also implements the onClickListener so each ViewHolder in the list is clickable.
        It's onclick method will call the onClick method of the RVClickListener defined in
        the main activity.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{

        //Fields to be populated
        public TextView song;
        public TextView artist;
        public ImageView image;
        private RVClickListener listener;
        private View itemView;


        public ViewHolder(@NonNull View itemView, RVClickListener passedListener) {
            super(itemView);
            song = (TextView) itemView.findViewById(R.id.textView);
            artist = (TextView) itemView.findViewById(R.id.textView2);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            this.itemView = itemView;
            itemView.setOnCreateContextMenuListener(this); //set context menu for each list item (long click)
            this.listener = passedListener;
            itemView.setOnClickListener(this); //set short click listener
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }

        //Context menu to be visible on item long press
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            //inflate menu from xml

            MenuInflater inflater = new MenuInflater(v.getContext());
            inflater.inflate(R.menu.context_menu, menu );

            //Play video which is default action
            menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
                @Override
                public boolean onMenuItemClick(MenuItem item){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoList.get(getAdapterPosition())));
                    v.getContext().startActivity(browserIntent);
                    return true;
                }
            });

            //Navigate to song's wikipedia entry with an implicit intent
            menu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
                @Override
                public boolean onMenuItemClick(MenuItem item){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(songWikiList.get(getAdapterPosition())));
                    v.getContext().startActivity(browserIntent);
                    return true;
                }
            });

            //Navigate to artist's wikipedia entry with an implicit intent
            menu.getItem(2).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
                @Override
                public boolean onMenuItemClick(MenuItem item){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(artistWikiList.get(getAdapterPosition())));
                    v.getContext().startActivity(browserIntent);
                    return true;
                }
            });
        }
    }
}