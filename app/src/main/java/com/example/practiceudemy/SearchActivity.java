package com.example.practiceudemy;

import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;

public class SearchActivity extends BaseActivityFlickr {

    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        activateToolbar(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_flickr, menu);
        return true;
    }
}
