package com.kodingkidz.alzhelp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{
    final public String SHARED_PREFS = "com.kodingkidz.alzhelp.sharedPreferences";
    final public String ALBUMS = "com.kodingkidz.alzhelp.albums";
    final public String ALBUM_NAME = "com.kodingkidz.alzhelp.albumName";
    String[] albums;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        /*
            Because SharedPreferences doesn't carry arrays, we are storing a giant string with each element separated by tabs.
         */

        if (prefs.getBoolean("FirstTime", true)) {//If this is first time...
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("FirstTime", false);
            editor.apply();
            Intent toSettings = new Intent(this, SettingsActivity.class);
            startActivity(toSettings); //Make them set up their albums.

        }
        albums = toAlbumArray(prefs.getString(ALBUMS, ""));
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new HomeScrapBookAdapter(this, albums);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this); // I put the listener inside this class
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * We made this method because you can only store key values and not arrays in SharedPreferences.
     * @param albumsInOneLine just pass the string returned from SharedPreferences (with the tabs)
     * @return the Album in its array, without the tabs.
     */
    String[] toAlbumArray (String albumsInOneLine) {
        //
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent openBook = new Intent(this, InsideScrapBook.class);
        openBook.putExtra(ALBUM_NAME, albums[position]);
       startActivity(openBook);
    }
    @Override
    public void onClick(View search) {
        onSearchRequested()
        //https://www.youtube.com/watch?v=EmOqp_uAtUQ
    }
}
