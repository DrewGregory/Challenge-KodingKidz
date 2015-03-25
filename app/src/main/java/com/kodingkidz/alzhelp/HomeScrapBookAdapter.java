package com.kodingkidz.alzhelp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Drew Gregory on 3/5/2015.
 */
public class HomeScrapBookAdapter extends ArrayAdapter<String> implements AdapterView.OnItemClickListener{
    private Context context;

    public HomeScrapBookAdapter(Context context, String[] categories) {
        super(context, R.layout.category_list_view_item, categories);
        this.context = context;
        this.categories = categories;
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
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_list_view_item, parent, false);
            ListAdapterHolderItem item = new ListAdapterHolderItem();
            item.textView = (TextView) convertView.findViewById(R.id.categoryName);
            convertView.setTag(item);
            /*imageView = new ImageView(context);

            imageView.setPadding(8, 8, 8, 8);*/
        }
        ListAdapterHolderItem mItem = (ListAdapterHolderItem) convertView.getTag();
        mItem.textView.setText(categories[position]);
        return convertView;
    }


    private String[] categories =
            {"Family", "Career", "Childhood", "School"};

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent openBook = new Intent(getContext(), InsideScrapBook.class);
        context.startActivity(openBook);
    }
}
