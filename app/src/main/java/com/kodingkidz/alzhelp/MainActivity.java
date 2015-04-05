package com.kodingkidz.alzhelp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
    final public String SHARED_PREFS = "KODING_KIDZ";
    final public String ALBUMS = "Albums";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        /*
            Because SharedPreferences doesn't carry arrays, we are storing a giant string with each element separated by tabs.
         */
        if (prefs.getBoolean("FirstTime", false)) {//If this is first time...
            Intent toSettings = new Intent(this, SettingsActivity.class);
            startActivity(toSettings); //Make them set up their albums.
        }
        char[] albumChars = prefs.getString(ALBUMS, "Album 1").toCharArray();
        String[] albums = new String(albumChars).split("\t");
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new HomeScrapBookAdapter(this, albums);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((android.widget.AdapterView.OnItemClickListener) adapter); // I put the listener inside the adapter.
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
}
