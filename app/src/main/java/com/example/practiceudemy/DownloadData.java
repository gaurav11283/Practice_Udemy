package com.example.practiceudemy;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadData extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView listApps;
    private String feedURL = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
    private int feedLimit = 10;
    public static final String STATE_URL = "feedURL";
    public static final String STATE_LIMIT = "feedLimit";
    private String feedCacheUrl = "INVALIDATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_data);
        listApps = findViewById(R.id.xmlListView);

        if(savedInstanceState!=null){
            feedURL= savedInstanceState.getString(STATE_URL);
            feedLimit = savedInstanceState.getInt(STATE_LIMIT);
        }
        downloadURL(String.format(feedURL,feedLimit));

    }

    private void downloadURL(String feedURL) {
        if(!feedURL.equalsIgnoreCase(feedCacheUrl)){
            Log.d(TAG, "downloadURL: starting download");
            DownloadInBackground downloadInBackground = new DownloadInBackground();
            downloadInBackground.execute(feedURL);
            feedCacheUrl = feedURL;
        }
        else{
            Log.d(TAG, "downloadURL: notDownloading As url is same");
        }
    }

    private class DownloadInBackground extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadInBackground";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ParseXmlData parseApplications = new ParseXmlData();
            parseApplications.parse(s);

//            ArrayAdapter<FeedEntry> arrayAdapter = new ArrayAdapter<FeedEntry>(
//                    DownloadData.this, R.layout.list_view, parseApplications.getApplications());
//            listApps.setAdapter(arrayAdapter);

            FeedAdapter<FeedEntry> adapter = new FeedAdapter<>(DownloadData.this, R.layout.list_record, parseApplications.getApplications());
            listApps.setAdapter(adapter);
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: starts with " + strings[0]);
            String rssFeed = downloadXML(strings[0]);
            if (rssFeed == null) {
                Log.e(TAG, "doInBackground: Error downloading");
            }
            return rssFeed;
        }

        private String downloadXML(String urlPath) {
            StringBuilder xmlResult = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadXML: The response code was " + response);
//                InputStream inputStream = connection.getInputStream();
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader reader = new BufferedReader(inputStreamReader);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                int charsRead;
                char[] inputBuffer = new char[500];
                while (true) {
                    charsRead = reader.read(inputBuffer);
                    if (charsRead < 0) {
                        break;
                    }
                    if (charsRead > 0) {
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                    }
                }
                reader.close();

                return xmlResult.toString();
            } catch (MalformedURLException e) {
                Log.e(TAG, "downloadXML: Invalid URL " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "downloadXML: IO Exception reading data: " + e.getMessage());
            } catch (SecurityException e) {
                Log.e(TAG, "downloadXML: Security Exception.  Needs permisson? " + e.getMessage());
//                e.printStackTrace();
            }

            return null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app_items, menu);
        if(feedLimit == 10) {
            menu.findItem(R.id.mnu10).setChecked(true);
        } else {
            menu.findItem(R.id.mnu25).setChecked(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.mnuFree:
                feedURL = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
                break;
            case R.id.mnuPaid:
                feedURL = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml";
                break;
            case R.id.mnu10:
            case R.id.mnu25:
                if(!item.isChecked()){
                    item.setChecked(true);
                    feedLimit= 35-feedLimit;
                    Log.d(TAG, "onOptionsItemSelected: FeedLimit Changed to "+feedLimit);
                }
                else
                {
                    Log.d(TAG, "onOptionsItemSelected: FeedLimit not changed");
                }
                break;
            case R.id.mnuRefresh:
                feedCacheUrl = "INVALIDATED";
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        downloadURL(String.format(feedURL,feedLimit));
        return true;
    }

    public DownloadData() {
        super();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(STATE_URL,feedURL);
        outState.putInt(STATE_LIMIT,feedLimit);
        super.onSaveInstanceState(outState);

    }
}


















