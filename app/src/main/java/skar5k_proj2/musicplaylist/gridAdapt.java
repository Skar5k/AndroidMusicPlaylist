/*
    Jaskaran Singh
    jsingh10
    670193440
    cs478 - Project 2

    Adapter for a gridview
 */

package skar5k_proj2.musicplaylist;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.util.ArrayList;


public class gridAdapt extends BaseAdapter {
    Context c;                                                                  //store app context
    ArrayList<MainActivity.song> songList;                                      //store list of songs in grid
    LayoutInflater inf;                                                         //allows inflation of gridview.xml

    public gridAdapt(Context appCont, ArrayList<MainActivity.song> songs){      //constructor. pass in context and list of songs
        c = appCont;
        songList = songs;
        inf = (LayoutInflater.from(appCont));

    }

    //gets number of elements in grid
    @Override
    public int getCount(){
        return songList.size();
    }

    //adds items to the grid
    @Override
    public View getView(final int index, View v, ViewGroup group){
        v = inf.inflate(R.layout.gridview,null);                                //inflate gridview.xml which is just an ImageView
        ImageView img = (ImageView) v.findViewById(R.id.songImageGrid);         //get image
        img.setImageResource(songList.get(index).getResID());                   //set image
        return v;
    }

    //gets song at index i in list
    @Override
    public Object getItem(int i) {
        return songList.get(i);
    }

    //gets item id, not used but needed for BaseAdapter extension
    @Override
    public long getItemId(int i) {
        return 0;
    }

}
