package com.andreschnabel.browseandplay;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BrowseActivity extends ListActivity {

    private ArrayAdapter<String> adapter;
    private Map<String, File> entryToFile = new HashMap<String, File>();
    private Song curSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        File cardPath = Environment.getExternalStorageDirectory();
        setListToPath(cardPath);
    }

    @Override
    protected void onPause() {
        super.onPause();
        disposeSong();
    }

    private void disposeSong() {
        if(curSong != null) {
            curSong.dispose();
            curSong = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.browse, menu);
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String selectedEntry = adapter.getItem(position);
        File selectedFile = entryToFile.get(selectedEntry);
        if(selectedFile.isDirectory()) {
            setListToPath(selectedFile);
        } else if(Utils.isMediaFile(selectedFile)) {
            disposeSong();
            try {
                curSong = new Song(selectedFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setListToPath(File path) {
        adapter.clear();
        entryToFile.clear();

        if(!path.equals(Environment.getExternalStorageDirectory())) {
            String entry = "Up to " + path.getParent();
            adapter.add(entry);
            entryToFile.put(entry, path.getParentFile());
        }

        for(File f : path.listFiles()) {
            if(f.isDirectory() || Utils.isMediaFile(f)) {
                String entry = f.getName();
                entryToFile.put(entry, f);
                adapter.add(entry);
            }
        }
    }
}
