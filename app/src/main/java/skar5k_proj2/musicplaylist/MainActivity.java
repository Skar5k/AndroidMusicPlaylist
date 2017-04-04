/*
    Jaskaran Singh
    jsingh10
    670193440
    cs478 - Project 2

    Starting window on which user can select songs to create a playlist. Also includes menu
    to select all, select none, invert selections, and create playlist
 */

package skar5k_proj2.musicplaylist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;
import java.io.Serializable;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<song> songList;                                               //list of all songs
    private ListView songView;                                                      //List where songs are displayed
    private listAdapt adapter;                                                      //adapter to allow songs to be displayed
    static public ArrayList<CheckBox> checkBoxes;                                   //array of checkboxes used for playlist creation
    private Button playlistButton;                                                  //button for creating playlist
    static public ArrayList<song> playList;                                         //songs currently in playlist


    //protocol when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkBoxes = new ArrayList<>();                                              //initialize lists
        songList = new ArrayList<>();
        populateSongs();                                                             //add hard coded song list to array
        songView = (ListView) this.findViewById(R.id.listV);                         //find UI elements
        playlistButton = (Button) this.findViewById(R.id.createplaybutton);
        adapter = new listAdapt(getApplicationContext(),songList);                   //set up adapter
        songView.setAdapter(adapter);

        playlistButton.setOnClickListener(create_playlist);                          //set a listener for the button
    }

    //call createPlaylist() when button is pressed
    private View.OnClickListener create_playlist = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createPlaylist();
        }
    };

    //allows overflow menu to be opened
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();                                  //inflate menu_bar.xml
            inflater.inflate(R.menu.menu_bar, menu);
            return true;
        }

    //when an item in the menu is selected, call the appropriate function
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){                                                  //get id of which menu item was selected and call function based on that
            case R.id.create_playlist:
                createPlaylist();
                return true;
            case R.id.check_all:
                checkAllOptions();
                return true;
            case R.id.clear_selections:
                unCheckAllOptions();
                return true;
            case R.id.invert_all:
                invertAllOptions();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createPlaylist(){                                                  //create playlist from playList arraylist.
        playList = new ArrayList<>();                                               //create new every time to avoid repeats
        for(CheckBox c : checkBoxes){                                               //for each checkbox, if checked, add to playlist
            if(c.isChecked()){
                String name = c.getText().toString();
                for(song s : songList){                                             //find song object associated with the checkbox
                    if(s.getName().equals(name)){
                        playList.add(s);
                        break;
                    }
                }
            }
        }

        if(playList.size() == 0) {                                                  //if no songs were added, display toast
            Context c = this.getApplicationContext();
            Toast t = Toast.makeText(c, getResources().getString(R.string.selectMore), Toast.LENGTH_LONG);
            t.show();
        }
        else {                                                                      //else open gridActivity
            Intent i = new Intent(MainActivity.this, gridActivity.class);
            startActivity(i);
        }
    }

    private void checkAllOptions(){                                                 //go through checkboxes and check it if unchecked
        for(CheckBox c : checkBoxes){
            if(!c.isChecked()){
                c.setChecked(true);
            }
        }
    }

    private void unCheckAllOptions(){                                               //go through checkboxes and uncheck if checked.
        for(CheckBox c : checkBoxes){
            if(c.isChecked()){
                c.setChecked(false);
            }
        }
    }

    private void invertAllOptions(){                                                //go through checkboxes and invert them all
        for(CheckBox c : checkBoxes){
            if(c.isChecked())
                c.setChecked(false);
            else
                c.setChecked(true);
        }
    }

    //adds 6 songs to the songList. This is hardcoded in
    private void populateSongs(){
        songList.add(new song(getResources().getString(R.string.song1name),getResources().getString(R.string.song1artist), getResources().getString(R.string.song1wikisong), getResources().getString(R.string.song1vid), getResources().getString(R.string.song1wikiart), R.drawable.bullsonparade));
        songList.add(new song(getResources().getString(R.string.song2name),getResources().getString(R.string.song2artist), getResources().getString(R.string.song2wikisong), getResources().getString(R.string.song2vid), getResources().getString(R.string.song2wikiart), R.drawable.centuries));
        songList.add(new song(getResources().getString(R.string.song3name),getResources().getString(R.string.song3artist), getResources().getString(R.string.song3wikisong), getResources().getString(R.string.song3vid), getResources().getString(R.string.song3wikiart), R.drawable.fade));
        songList.add(new song(getResources().getString(R.string.song4name),getResources().getString(R.string.song4artist), getResources().getString(R.string.song4wikisong), getResources().getString(R.string.song4vid), getResources().getString(R.string.song4wikiart), R.drawable.everlong));
        songList.add(new song(getResources().getString(R.string.song5name),getResources().getString(R.string.song5artist), getResources().getString(R.string.song5wikisong), getResources().getString(R.string.song5vid), getResources().getString(R.string.song5wikiart), R.drawable.stairway));
        songList.add(new song(getResources().getString(R.string.song6name),getResources().getString(R.string.song6artist), getResources().getString(R.string.song6wikisong), getResources().getString(R.string.song6vid), getResources().getString(R.string.song6wikiart), R.drawable.loseyourself));
    }

    //song object. Should probably be in another java file... stores info about a song (name, artist, links)
    public class song implements Serializable{
        private String name;
        private String artists;
        private String wikiLinkArtist;
        private String wikiLinkSong;
        private String vidLink;
        private int resID;

        public song(String songName, String artist, String wiki, String vid, String wikiLinkArt, int id){
            name = songName;
            artists = artist;
            wikiLinkSong = wiki;
            wikiLinkArtist = wikiLinkArt;
            vidLink = vid;
            resID = id;
        }

        //getters for all fields
        public String getArtists() {
            return artists;
        }

        public String getName(){return name;}

        public String getVidLink() {
            return vidLink;
        }

        public String wikiLinkSong() {
            return wikiLinkSong;
        }

        public String getWikiLinkArtist() {
            return wikiLinkArtist;
        }

        public int getResID() {return resID;}
    }


}
