/*
    Jaskaran Singh
    jsingh10
    670193440
    cs478 - Project 2

    Adapter for listView to be displayed.
    I didn't know there was already a built in one, so I made a custom :(
 */

package skar5k_proj2.musicplaylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.ArrayList;



public class listAdapt extends BaseAdapter {
    Context c;                                              //store context of the activity, the song list, and an inflater for listview.xml
    ArrayList<MainActivity.song> songList;
    LayoutInflater inf;

    public listAdapt(Context appCont, ArrayList<MainActivity.song> songs){      //constructor. Pass in app context and song list of all songs
        c = appCont;
        songList = songs;
        inf = (LayoutInflater.from(appCont));
    }


    @Override
    public int getCount(){
        return songList.size();
    }

    @Override
    public View getView(int index, View v, ViewGroup group){
        v = inf.inflate(R.layout.listview,null);                                    //inflate the xml layout
        CheckBox songTitleBox = (CheckBox) v.findViewById(R.id.songCheckBox);       //get UI Elements
        TextView artistText = (TextView) v.findViewById(R.id.songArtistSubtext);
        songTitleBox.setText(songList.get(index).getName());                        //set text on checkbox and textview
        artistText.setText("           "+songList.get(index).getArtists());
        MainActivity.checkBoxes.add(songTitleBox);                                  //add checkbox to list
        return v;
    }

    //getItem and getItemId are not used, but required for BaseAdapter
    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
