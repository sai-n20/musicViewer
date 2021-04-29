package com.example.cs478project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Create arrays for various URL's required for functionality
    ArrayList<String> songList;
    ArrayList<String> artistList;
    ArrayList<String> videoList;
    ArrayList<String> songWiki;
    ArrayList<String> artistWiki;

    //Flag to indicate layout used before config change
    String currentLayout = "List";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView nameView = (RecyclerView) findViewById(R.id.recycler_view);

        //Feed values into arrays
        List<String> songNames = Arrays.asList("Blinding lights", "Beautiful people", "Dancing with a stranger", "Eastside", "Silence", "Better", "Wolves",
                "Without Me", "Levitating");

        List<String> artistNames = Arrays.asList("The Weeknd", "Ed Sheeran", "Sam Smith", "Benny Blanco", "Marshmellow", "Khalid", "Selena Gomez",
                "Halsey", "Dua Lipa");

        List<String> videoLinks = Arrays.asList("https://www.youtube.com/watch?v=fHI8X4OXluQ",
                "https://www.youtube.com/watch?v=mj0XInqZMHY", "https://www.youtube.com/watch?v=av5JD1dfj_c",
                "https://www.youtube.com/watch?v=KFof8aaUvGY", "https://www.youtube.com/watch?v=4oXgCJf4hf8",
                "https://www.youtube.com/watch?v=x3bfa3DZ8JM", "https://www.youtube.com/watch?v=cH4E_t3m3xM",
                "https://www.youtube.com/watch?v=Tk7WFyHUr1E",
                "https://www.youtube.com/watch?v=TUVcZfQe-Kw");

        List<String> songWikiList = Arrays.asList("https://en.wikipedia.org/wiki/Blinding_Lights",
                "https://en.wikipedia.org/wiki/Beautiful_People_(Ed_Sheeran_song)",
                "https://en.wikipedia.org/wiki/Dancing_with_a_Stranger",
                "https://en.wikipedia.org/wiki/Eastside_(song)", "https://en.wikipedia.org/wiki/Silence_(Marshmello_song)",
                "https://en.wikipedia.org/wiki/Better_(Khalid_song)", "https://en.wikipedia.org/wiki/Wolves_(Selena_Gomez_and_Marshmello_song)",
                "https://en.wikipedia.org/wiki/Without_Me_(Halsey_song)", "https://en.wikipedia.org/wiki/Levitating_(song)");

        List<String> artistWikiList = Arrays.asList("https://en.wikipedia.org/wiki/The_Weeknd",
                "https://en.wikipedia.org/wiki/Ed_Sheeran", "https://en.wikipedia.org/wiki/Sam_Smith",
                "https://en.wikipedia.org/wiki/Benny_Blanco", "https://en.wikipedia.org/wiki/Marshmello",
                "https://en.wikipedia.org/wiki/Khalid_(singer)", "https://en.wikipedia.org/wiki/Selena_Gomez",
                "https://en.wikipedia.org/wiki/Halsey_(singer)", "https://en.wikipedia.org/wiki/Dua_Lipa");

        songList = new ArrayList<>();
        songList.addAll(songNames);

        artistList = new ArrayList<>();
        artistList.addAll(artistNames);

        videoList = new ArrayList<>();
        videoList.addAll(videoLinks);

        songWiki = new ArrayList<>();
        songWiki.addAll(songWikiList);

        artistWiki = new ArrayList<>();
        artistWiki.addAll(artistWikiList);


        //Define the listener with a lambda and access the position of the list item from the view to open relevant video link
        RVClickListener listener = (view,position)->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoList.get(position)));
            startActivity(browserIntent);
        };

        //Pass lists to adapter class for context menu selections
        MyAdapter adapter = new MyAdapter(songList, artistList, videoList, artistWiki, songWiki, listener);
        nameView.setHasFixedSize(true);
        nameView.setAdapter(adapter);

        //Check if activity has been restarted or started fresh
        if(savedInstanceState != null) {

            //If restarted, restore previous layout
            if(savedInstanceState.getString("layout") == "List") {
                nameView.setLayoutManager(new LinearLayoutManager(this));
                currentLayout = "List";
            }
            else {
                nameView.setLayoutManager(new GridLayoutManager(this,2));
                currentLayout = "Grid";
            }
        }
        else {
            nameView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    //Save layout state to bundle during reinstantiation
    @Override
    public void onSaveInstanceState(Bundle outstate) {
        outstate.putString("layout", currentLayout);
        super.onSaveInstanceState(outstate);
    }

    //Inflate options menu from XML spec
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.layout_menu, menu);
        return true;
    }

    //Handle options menu selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get recyclerview resource to change layout as necessary
        RecyclerView nameView = (RecyclerView) findViewById(R.id.recycler_view);

        //Switch case between list view and grid view options
        switch (item.getItemId()) {
            case R.id.menu1:
                if(currentLayout != "List") {
                    nameView.setLayoutManager(new LinearLayoutManager(this));
                    currentLayout = "List";
                }
                return true;
            case R.id.menu2:
                if(currentLayout != "Grid") {
                    nameView.setLayoutManager(new GridLayoutManager(this,2));
                    currentLayout = "Grid";
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}