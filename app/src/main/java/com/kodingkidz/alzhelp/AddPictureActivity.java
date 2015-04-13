package com.kodingkidz.alzhelp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;

import android.content.DialogInterface;

import android.database.Cursor;

import android.graphics.Bitmap;

import android.graphics.BitmapFactory;

import android.os.Environment;

import android.util.Log;

import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;

import java.io.IOException;

import java.io.OutputStream;


public class AddPictureActivity extends ActionBarActivity {
    final public String SHARED_PREFS = "com.kodingkidz.alzhelp.sharedPreferences";
    final public String ALBUM_NAME = "com.kodingkidz.alzhelp.albumName";
    final public String ALBUM_PICTURE_PATH = "com.kodingkidz.alzhelp.albumPicturePath";
    final public String ALBUM_DESCRIPTION = "com.kodingkidz.alzhelp.albumDescription";
    final public String EDIT_PICTURE = "com.kodingkidz.alzhelp.editPicture";
    String albumName, picturePath;
    boolean edit;
    SharedPreferences prefs;
    int startOfOldPath, startOfOldDescription, pathLength, descriptionLength;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture);
        prefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        albumName = getIntent().getStringExtra(ALBUM_NAME);
        imageButton = (ImageView) findViewById(R.id.addPictureImageView);
        edit = getIntent().getBooleanExtra(EDIT_PICTURE, true);
        Button button = (Button) findViewById(R.id.addPictureButton);
        if (edit) {
            setTitle("Edit Picture");
            button.setText("Edit Picture");
            String oldPath = getIntent().getStringExtra(ALBUM_PICTURE_PATH);
            startOfOldPath = prefs.getString(albumName + ALBUM_PICTURE_PATH, "").indexOf(oldPath);
            String oldDescription = getIntent().getStringExtra(ALBUM_DESCRIPTION);
            startOfOldDescription = prefs.getString(albumName + ALBUM_DESCRIPTION, "").indexOf(oldDescription);
            pathLength = oldPath.length();
            descriptionLength = oldDescription.length();

        }
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                selectImage();

            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_picture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            EditText descriptionInput = (EditText) findViewById(R.id.descriptionEditText);
            String descriptionText = descriptionInput.getText().toString();
            SharedPreferences.Editor editor = prefs.edit();
            String separator = "\t";
            String previousPathContent = prefs.getString(albumName + ALBUM_PICTURE_PATH, "");
            editor.putString(albumName + ALBUM_PICTURE_PATH, previousPathContent + separator + picturePath);
            String previousDescContent = prefs.getString(albumName + ALBUM_DESCRIPTION, "");
            editor.putString(albumName + ALBUM_DESCRIPTION, previousDescContent + separator + descriptionText);
            editor.apply();
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    ImageView imageButton;

    public void addPicture (View v) {
        EditText descriptionInput = (EditText) findViewById(R.id.descriptionEditText);
        String descriptionText = descriptionInput.getText().toString();

        SharedPreferences.Editor editor = prefs.edit();
        String separator = "\t";
        if (!edit) {
            String previousPathContent = prefs.getString(albumName + ALBUM_PICTURE_PATH, "");
            editor.putString(albumName + ALBUM_PICTURE_PATH, previousPathContent + separator + picturePath);
            String previousDescContent = prefs.getString(albumName + ALBUM_DESCRIPTION, "");
            editor.putString(albumName + ALBUM_DESCRIPTION, previousDescContent + separator + descriptionText);
        } else {
            String previousPathContent = prefs.getString(albumName + ALBUM_PICTURE_PATH, "");
            String newPathContent = "";
            if (startOfOldDescription + pathLength < previousPathContent.length()) {
                newPathContent = previousPathContent.substring(0, startOfOldPath) + picturePath + previousPathContent.substring(startOfOldPath
                        + pathLength);
            } else {
                newPathContent = previousPathContent.substring(0, startOfOldPath) + picturePath;
            }
            editor.putString(albumName + ALBUM_PICTURE_PATH, newPathContent);
            String previousDescContent = prefs.getString(albumName + ALBUM_DESCRIPTION, "");
            String newDescContent = "";
            if (startOfOldDescription + descriptionLength < previousDescContent.length()) {
                newDescContent = previousDescContent.substring(0, startOfOldDescription) + descriptionText + previousDescContent.substring(startOfOldDescription
                        + descriptionLength);
            } else {
                newDescContent = previousDescContent.substring(0, startOfOldDescription) + descriptionText;
            }

            editor.putString(albumName + ALBUM_DESCRIPTION, newDescContent);
        }
        editor.commit();
        Intent back = new Intent(this, EditAlbumActivity.class);
        back.putExtra(ALBUM_NAME, albumName);
        startActivity(back);
    }

    public void deletePicture(View v) {
        EditText descriptionInput = (EditText) findViewById(R.id.descriptionEditText);
        String descriptionText = descriptionInput.getText().toString();

        SharedPreferences.Editor editor = prefs.edit();
        String separator = "\t";
        if (edit) {
            String previousPathContent = prefs.getString(albumName + ALBUM_PICTURE_PATH, "");
            String newPathContent = "";
            if (startOfOldDescription + pathLength < previousPathContent.length()) {
                newPathContent = previousPathContent.substring(0, startOfOldPath - 1)+ previousPathContent.substring(startOfOldPath + 1
                        + pathLength);
            } else {
                newPathContent = previousPathContent.substring(0, startOfOldPath - 1);
            }
            editor.putString(albumName + ALBUM_PICTURE_PATH, newPathContent);
            String previousDescContent = prefs.getString(albumName + ALBUM_DESCRIPTION, "");
            String newDescContent = "";
            if (startOfOldDescription + descriptionLength < previousDescContent.length()) {
                newDescContent = previousDescContent.substring(0, startOfOldDescription - 1) + previousDescContent.substring(startOfOldDescription + 1
                        + descriptionLength);
            } else {
                newDescContent = previousDescContent.substring(0, startOfOldDescription - 1) ;
            }

            editor.putString(albumName + ALBUM_DESCRIPTION, newDescContent);
            editor.commit();
        }

        Intent back = new Intent(this, EditAlbumActivity.class);
        back.putExtra(ALBUM_NAME, albumName);
        startActivity(back);
    }

    private void selectImage() {

        /*Add "Take Photo", in the options array if you want to enable the user to take a photo.
        * However, we would need to create a file in a retrievable place.
        * I think it isn't necessary but the if statement is still there
        * :-)
        * */
        final CharSequence[] options = {"Choose from Gallery", "Cancel"};


        AlertDialog.Builder builder = new AlertDialog.Builder(AddPictureActivity.this);

        builder.setTitle("Add Photo");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo"))

                {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

                    startActivityForResult(intent, 1);

                } else if (options[item].equals("Choose from Gallery"))

                {

                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, 2);


                } else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();

    }


    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {

                File f = new File(Environment.getExternalStorageDirectory().toString());

                for (File temp : f.listFiles()) {

                    if (temp.getName().equals("temp.jpg")) {

                        f = temp;

                        break;

                    }

                }

                try {

                    Bitmap bitmap;

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();


                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),

                            bitmapOptions);


                    imageButton.setImageBitmap(bitmap);


                    String path = android.os.Environment

                            .getExternalStorageDirectory()

                            + File.separator

                            + "Phoenix" + File.separator + "default";

                    f.delete();

                    OutputStream outFile = null;

                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");

                    try {

                        outFile = new FileOutputStream(file);

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);

                        outFile.flush();

                        outFile.close();

                    } catch (FileNotFoundException e) {

                        e.printStackTrace();

                    } catch (IOException e) {

                        e.printStackTrace();

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }

            } else if (requestCode == 2) {


                Uri selectedImage = data.getData();

                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

                picturePath = c.getString(columnIndex);

                c.close();

                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                Log.w("path of image:", picturePath + "");

                imageButton.setImageBitmap(thumbnail);

            }

        }

    }

}
