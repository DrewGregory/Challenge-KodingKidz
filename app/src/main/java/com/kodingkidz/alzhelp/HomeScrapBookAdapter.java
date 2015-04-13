package com.kodingkidz.alzhelp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Drew Gregory on 3/5/2015.
 */
public class HomeScrapBookAdapter extends ArrayAdapter<String>{
    private Context context;
    final public String ALBUM_NAME = "com.kodingkidz.alzhelp.albumName";
    final public int RED = 1, BLUE = 2, GREEN = 3, PINK = 4, PURPLE = 5, YELLOW = 6;

    public HomeScrapBookAdapter(Context context, String[] categories, int[] colors) {
        super(context, R.layout.category_list_view_item, categories);
        this.context = context;
        this.categories = categories;
        this.colors = colors;
    }

    @Override
    public int getCount() {
        return categories.length;
    }

    @Override
    public String getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_list_view_item, parent, false);
            ListAdapterHolderItem item = new ListAdapterHolderItem();
            item.textView = (TextView) convertView.findViewById(R.id.categoryName);
            item.imageView = (ImageView) convertView.findViewById(R.id.scrapBookBorder);
            convertView.setTag(item);
            /*imageView = new ImageView(context);

            imageView.setPadding(8, 8, 8, 8);*/
        }

        ListAdapterHolderItem mItem = (ListAdapterHolderItem) convertView.getTag();
        mItem.textView.setText(categories[position]);
        switch(colors[position]) {
            case RED:
                mItem.imageView.setImageResource(R.drawable.scrapbook_cover); //The original is red
                break;
            case YELLOW:
                mItem.imageView.setImageResource(R.drawable.scrapbook_cover_yellow);
                break;
            case BLUE:
                mItem.imageView.setImageResource(R.drawable.scrapbook_cover_blue);
                break;
            case GREEN:
                mItem.imageView.setImageResource(R.drawable.scrapbook_cover_green);
                break;
            case PINK:
                mItem.imageView.setImageResource(R.drawable.scrapbook_cover_pink);
                break;
            case PURPLE:
                mItem.imageView.setImageResource(R.drawable.scrapbook_cover_purple);
        }
        return convertView;
    }


    private String[] categories;
    private int[] colors;


}
