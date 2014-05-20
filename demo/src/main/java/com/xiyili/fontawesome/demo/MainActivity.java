package com.xiyili.fontawesome.demo;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.xiyili.fontawesome.FontDrawable;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(new FontAwesomeCharAdapter(getApplicationContext()));
    }

    public static class FontAwesomeCharAdapter extends ArrayAdapter<Character> {
        public static int mFontStart = 0xf032;
        public static int mFontEnd = 0xf1e2;
        public static char mCharStart = '\uf032';
        public static Character[] chars = {'\uf032','\uf033','\uf034','\uf035','\uf036','\uf037'};


        public FontAwesomeCharAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_1,chars);
        }

        @Override
        public int getCount() {
            return chars.length;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new ImageView(getContext());
            }
            Character ch = getItem(position);
            FontDrawable dr = new FontDrawable(getContext().getResources(), ch);
            ImageView imageView = (ImageView)convertView;
            imageView.setImageDrawable(dr);
            return convertView;
        }
    }

}
