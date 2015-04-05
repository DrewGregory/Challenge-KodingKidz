package com.kodingkidz.alzhelp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.BassBoost;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class AddAlbumActivity extends ActionBarActivity {
    final public String SHARED_PREFS = "KODING_KIDZ";
    final public String ALBUMS = "Albums";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_album);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    public void createAlbum (View v) {
        EditText name = (EditText) findViewById(R.id.albumNameText);
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(ALBUMS, prefs.getString(ALBUMS, "") + "\t" + name.getText().toString());
        edit.apply();
        Intent backToSettings = new Intent(this, SettingsActivity.class);
        startActivity(backToSettings);
    }
}
