package com.example.sophie.newsappupdate;

import android.Manifest;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sophie.newsappupdate.data.NewsObject;
import com.example.sophie.newsappupdate.utils.JSONLoader;
import com.example.sophie.newsappupdate.utils.JSONParser;
import com.example.sophie.newsappupdate.utils.NewsAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsObject>> {

    JSONParser myParser = new JSONParser();
    Button update_button;
    NewsAdapter adapter;
    TextView noDataToShow;

    //check if device is connected
    private boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find my views
        update_button = findViewById(R.id.update_button);
        noDataToShow = findViewById(R.id.no_data_message);
        //check permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
        }
        //get my data
        if (!isNetworkAvailable()) {
            noDataToShow.setText(R.string.no_connection);
        }
        loadData();
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

    }

    private void loadData() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1, null, this);
    }


    @Override
    public Loader<List<NewsObject>> onCreateLoader(int id, Bundle args) {
        JSONLoader loader = new JSONLoader(this, getString(R.string.url));
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<NewsObject>> loader, List<NewsObject> data) {
        if (!myParser.testHistory.isEmpty()) {
            adapter = new NewsAdapter(myParser.testHistory);
            noDataToShow.setVisibility(View.GONE);

            RecyclerView myListView = findViewById(R.id.list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

            myListView.setLayoutManager(layoutManager);
            myListView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "wtf", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<NewsObject>> loader) {

    }


}
