package skar5k_proj2.musicplaylist;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
/*
    Jaskaran Singh
    jsingh10
    670193440
    cs478 - Project 2

    Activity for displaying the selected playlist as a grid. Allows user to select items in the grid
    to open a youtube link, or long press to open a context menu with wikipedia links.
 */

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;


import java.util.ArrayList;

public class gridActivity extends AppCompatActivity {

    private ArrayList<MainActivity.song> songList;                                  //playlist from main activity
    private gridAdapt gadapt;                                                       //grid adapter
    private GridView songView;                                                      //grid View for displaying songs

    //protocol for when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        songView = (GridView) this.findViewById(R.id.gridView);                     //find view
        songList = (ArrayList<MainActivity.song>) MainActivity.playList.clone();    //copy playlist from main activity
        gadapt = new gridAdapt(this.getApplicationContext(),songList);              //set up adapter
        songView.setAdapter(gadapt);
        songView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {              //set behavior when item in grid is clicked
                MainActivity.song s = (MainActivity.song)gadapt.getItem(position);  //gets selected song, and then opens the link
                openLink(s.getVidLink());

            }
        });

        registerForContextMenu(songView);                                           //register grid for context menu. Will open on long press
    }

    //sets up context menu with options
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle(getResources().getString(R.string.options));                                                                 //set up menu options
        menu.add(Menu.NONE,0,0,getResources().getString(R.string.play_song));
        menu.add(Menu.NONE,1,1,getResources().getString(R.string.song_wiki));
        menu.add(Menu.NONE,2,2,getResources().getString(R.string.artist_wiki));
    }

    //called when an item in the context menu is selected
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();   //get info about which menu item was clicked
        int menuItemIndex = item.getItemId();                                                               //get index of selected menu item
        MainActivity.song song = (MainActivity.song) gadapt.getItem(info.position);                         //get selected song

        if(menuItemIndex == 0){                                                                             //open link based on selection
            openLink(song.getVidLink());                                                                    //0 - Play Song
        }                                                                                                   //1 - Song Wiki
        else if(menuItemIndex == 1){                                                                        //2 - Artist Wiki
            openLink(song.wikiLinkSong());
        }
        else if(menuItemIndex == 2){
            openLink(song.getWikiLinkArtist());
        }


        return true;
    }

    //creates an intent to open a link
    private void openLink(String link){
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(link));
        startActivity(intent);
    }

}
