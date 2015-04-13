package com.kodingkidz.alzhelp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class SettingsActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    final public String SHARED_PREFS = "com.kodingkidz.alzhelp.sharedPreferences";
    final public String ALBUMS = "com.kodingkidz.alzhelp.albums";
    final public String ALBUM_NAME = "com.kodingkidz.alzhelp.albumName";
    final public String ALBUM_COLOR = "com.kodingkidz.alzhelp.albumColor";
    String[] albums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        ListView lv = (ListView) findViewById(R.id.listOfCurrentAlbums);
        albums = toAlbumArray(prefs.getString(ALBUMS, ""));
        int[] albumColors = new int[albums.length];
        for (int index = 0; index < albumColors.length; index++) {
            albumColors[index] = prefs.getInt(albums[index] + ALBUM_COLOR, 1);
        }
        lv.setAdapter(new HomeScrapBookAdapter(this, albums, albumColors));
        lv.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void addAlbum(View button) {
        startActivity(new Intent(this, AddAlbumActivity.class));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent toEditAlbumActivity = new Intent(this, EditAlbumActivity.class);
        toEditAlbumActivity.putExtra(ALBUM_NAME, albums[position]);
        startActivity(toEditAlbumActivity);
    }


    /**
     * We made this method because you can only store key values and not arrays in SharedPreferences.
     * @param albumsInOneLine just pass the string returned from SharedPreferences (with the tabs)
     * @return the Album in its array, without the tabs.
     */
    String[] toAlbumArray (String albumsInOneLine) {

        String[] albumsInArray = albumsInOneLine.split("\t");
        //Because it adds a tab before the first album in the createAlbum() method, we have to remove the first element
        String[] tempAlbums = new String[albumsInArray.length - 1];
        if (albumsInArray.length > 0) {
            for (int index = 1; index < albumsInArray.length; index++) {
                tempAlbums[index - 1] = albumsInArray[index];
            }
        }
        return tempAlbums;
    }
}
