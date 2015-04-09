package com.kodingkidz.alzhelp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class EditAlbumActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    final public String SHARED_PREFS = "com.kodingkidz.alzhelp.sharedPreferences";
    final public String ALBUM_NAME = "com.kodingkidz.alzhelp.albumName";
    final public String ALBUM_DESCRIPTION = "com.kodingkidz.alzhelp.albumDescription";
    final public String ALBUM_PICTURE_PATH = "com.kodingkidz.alzhelp.albumPicturePath";
    final public String EDIT_PICTURE = "com.kodingkidz.alzhelp.editPicture";
    SharedPreferences prefs;
    String albumName;
    Picture[] pictures;
    String[] imagePaths, descriptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_album);
        prefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        albumName = getIntent().getStringExtra(ALBUM_NAME);
        setTitle("Edit " + albumName);
        descriptions = toStringArray(prefs.getString(albumName + ALBUM_DESCRIPTION, ""));
        imagePaths = toStringArray(prefs.getString( albumName + ALBUM_PICTURE_PATH, ""));
        pictures = new Picture[Math.min(descriptions.length, imagePaths.length)];
        for (int index = 0; index < pictures.length; index++) {
            pictures[index] = new Picture();
            pictures[index].description = descriptions[index];
            pictures[index].imageBitmap = BitmapFactory.decodeFile(imagePaths[index]);
        }
        ListView lv = (ListView) findViewById(R.id.currentPicturesListView);
        lv.setAdapter(new EditAlbumAdapter(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_album, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void addPicture (View v) {
        Intent toAddPicture = new Intent(this, AddPictureActivity.class);
        toAddPicture.putExtra(EDIT_PICTURE, false); //This means that we are adding a new picture instead of editing one already.
        toAddPicture.putExtra(ALBUM_NAME, albumName);
        startActivity(toAddPicture);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent toEditPicture = new Intent(this, AddPictureActivity.class);
        toEditPicture.putExtra(EDIT_PICTURE, true); //This means that we are editing a picture already present in the album.
        toEditPicture.putExtra(ALBUM_NAME, albumName);
        toEditPicture.putExtra(ALBUM_PICTURE_PATH, imagePaths[position]);
        toEditPicture.putExtra(ALBUM_DESCRIPTION, descriptions[position]);
    }


    class EditAlbumAdapter extends ArrayAdapter<Picture> {
        Context context;
        @Override
        public int getCount() {
            return pictures.length;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.picture_list_view_item, parent, false);
                ListAdapterHolderItem item = new ListAdapterHolderItem();
                item.textView = (TextView) convertView.findViewById(R.id.descriptionListViewItemTextView);
                item.imageView = (ImageView) convertView.findViewById(R.id.pictureListViewItemImageView);
                convertView.setTag(item);
            }
            ListAdapterHolderItem mItem = (ListAdapterHolderItem) convertView.getTag();
            mItem.textView.setText(pictures[position].description);
            mItem.imageView.setImageBitmap(pictures[position].imageBitmap);
            return convertView;
        }

        public EditAlbumAdapter(Context context) {
            super(context, R.layout.picture_list_view_item, pictures);
            this.context = context;
        }
    }


    /**
     * We made this method because you can only store key values and not arrays in SharedPreferences.
     * @param stringInOneLine just pass the string returned from SharedPreferences (with the tabs)
     * @return the Album in its array, without the tabs.
     */
    String[] toStringArray (String stringInOneLine) {
        String[] albumsInArray = stringInOneLine.split("\t");
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
