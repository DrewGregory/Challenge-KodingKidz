package com.kodingkidz.alzhelp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.BassBoost;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class AddAlbumActivity extends ActionBarActivity {
    final public String SHARED_PREFS = "com.kodingkidz.alzhelp.sharedPreferences";
    final public String ALBUMS = "com.kodingkidz.alzhelp.albums";
    final public String ALBUM_COLOR = "com.kodingkidz.alzhelp.albumColor";
    final public int RED = 1, BLUE = 2, GREEN = 3, PINK = 4, PURPLE = 5, YELLOW = 6;
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

        switch (id) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void createAlbum(View v) {
        EditText name = (EditText) findViewById(R.id.albumNameText);
        if (name.getText().toString().trim().equals("")) {
            EmptyAlbumDialogFragment fragment = new EmptyAlbumDialogFragment();
            fragment.show(getFragmentManager(), "Empty Album");
            return;
        }
        int color;
        switch (v.getId()) {
            case R.id.redButton: //lets you make the album red
                color = RED;
                break;
            case R.id.blueButton: //lets you make the album blue
                color = BLUE;
                break;
            case R.id.greenButton: //lets you make the album green
                color = GREEN;
                break;
            case R.id.pinkButton: //lets you make the album pink
                color = PINK;
                break;
            case R.id.purpleButton: //lets you make the album purple
                color = PURPLE;
                break;
            case R.id.yellowButton: //lets you make the album yellow
                color = YELLOW;
                break;
            default:
                color = RED;
        }

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        String separator = "\t";
        edit.putString(ALBUMS, prefs.getString(ALBUMS, "") + separator + name.getText().toString());
        edit.putInt(name.getText().toString() + ALBUM_COLOR, color);
        edit.apply();
        Intent backToSettings = new Intent(this, SettingsActivity.class);
        startActivity(backToSettings);
    }

    /**
     * In case they try to add an album that doesn't have a name.
     *
     */
    static public class EmptyAlbumDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("You still haven't written a name for your album!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    }).setTitle("Empty Album Name");
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
