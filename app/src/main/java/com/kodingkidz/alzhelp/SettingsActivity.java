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


public class SettingsActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{
    final public String SHARED_PREFS = "KODING_KIDZ";
    final public String ALBUMS = "Albums";
    //TODO: Change this static array into a dynamic array obtained from SharedPreferences
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        ListView lv = (ListView) findViewById(R.id.listOfCurrentAlbums);
        String[] albums = prefs.getString(ALBUMS, "").split("\t");
        if (albums[0].equals("")) {
            albums = new String[0];
        }
        lv.setAdapter(new HomeScrapBookAdapter(this,albums));
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
    public void addPicture (View button)
    {
        startActivity(new Intent(this, AddPictureActivity.class));
    }
    public void addDescription (View button)
    {
        startActivity(new Intent(this, AddDescriptionActivity.class));
    }
    public void addAlbum (View button)
    {
        startActivity(new Intent(this, AddAlbumActivity.class));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TODO Direct to an edit of the album.
    }
}
