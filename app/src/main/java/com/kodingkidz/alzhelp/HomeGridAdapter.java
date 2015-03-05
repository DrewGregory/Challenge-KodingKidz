package com.kodingkidz.alzhelp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Drew Gregory on 3/5/2015.
 */
public class HomeGridAdapter extends BaseAdapter {
    private Context context;

    public HomeGridAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.category_list_view_item, parent);
            ListAdapterHolderItem item = new ListAdapterHolderItem();
            item.imageView = (ImageView) convertView.findViewById(R.id.scrapBookBorder);
            item.imageView.setImageResource(R.drawable.temp_scrapbook_border);
            item.textView = (TextView) convertView.findViewById(R.id.categoryName);
            convertView.setTag(item);
            /*imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);*/
        }
        ListAdapterHolderItem mItem = (ListAdapterHolderItem) convertView.getTag();
        mItem.textView.setText(categories[position]);
        return convertView;
    }

    private String[] categories =
            { "Family", "Friends", "Career", "Childhood"  };
}
