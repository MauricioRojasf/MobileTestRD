package com.example.rockhopper.mobiletest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONArray;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;


public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeContainer;
    private String urlJson = "https://hn.algolia.com/api/v1/search_by_date?query=android";
    private static String TAG = MainActivity.class.getSimpleName();
    private static  ArrayList <Feed> Feedlist;
    public final static String URLNAME = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Only ever call `setContentView` once right at the top
        setContentView(R.layout.activity_main);
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                makeFeedRequest(urlJson);
                swipeContainer.setRefreshing(false);

            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        makeFeedRequest(urlJson);

        ListView lv = (ListView) findViewById(R.id.feed_listview);
        lv.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent , View view, int position, long id ){
                Feed feed = (Feed) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this,WebActivity.class);
                intent.putExtra(URLNAME,feed.getUrl());
                Toast.makeText(view.getContext(),feed.getUrl(),Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        lv,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {

                                    Feedlist.remove(position);
                                    //FeedAdapter.notifyDataSetChanged();

                                }


                            }
                        });
        lv.setOnTouchListener(touchListener);

    }

    /**
     * Method to make json object request where json response starts wtih {
     * */
    private void makeFeedRequest(String urlJson) {
        Feedlist = new ArrayList<>();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJson, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    JSONArray hits = response.getJSONArray("hits");
                    int size = hits.length();

                    for (int i = 0; i <size ; i++){
                        JSONObject feed = (JSONObject) hits.get(i);

                                String title = feed.getString("title");
                                if (title == "null")
                                    title = feed.getString("story_title");
                                String author = feed.getString("author");
                                String url = feed.getString("url");
                                if (url == "null")
                                    url = feed.getString("story_url");
                                String date = feed.getString("created_at");
                                Feedlist.add(new Feed(title,author,url,date));
                    }
                    startAdapter(Feedlist);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void startAdapter(ArrayList<Feed> Feedlist){
        FeedAdapter adapter = new FeedAdapter(this, Feedlist);

        // get the ListView and attach the adapter
        ListView itemsListView  = (ListView) findViewById(R.id.feed_listview);
        itemsListView.setAdapter(adapter);
    }


}