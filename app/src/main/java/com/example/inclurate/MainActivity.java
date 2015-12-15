package com.example.inclurate;

import android.support.v7.app.ActionBarActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class MainActivity extends ActionBarActivity {


    ListView lv;
    private ProgressDialog pDialog;
    String baseUrl = "http://fs.royal.kz/640x480xc/";

    final String TITLE = "TITLE";
    final String DESCRIPTION = "DESCRIPTION";
    final String DATE = "DATE";
    final String IMG = "IMG";
    ArrayList newsData = new ArrayList();
    Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lv = (ListView) findViewById(R.id.lvSimple);

        if (savedInstanceState == null)
            new ParseTask().execute();
        else newsData = savedInstanceState.getParcelableArrayList("newsData");

    }


    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";


        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL("http://api.royal.kz/soc/news");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }


        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);

            if (pDialog.isShowing())
                pDialog.dismiss();

            JSONObject dataJsonObj = null;
            String secondName = "";

            try {
                dataJsonObj = new JSONObject(strJson);
                JSONArray news = dataJsonObj.getJSONArray("models");


                for (int i = 0; i < news.length(); i++) {

                    JSONObject jsonObject = news.getJSONObject(i);
                    NewsData n = new NewsData();

                    n.setTitle(jsonObject.getString("news_title"));
                    n.setDescription(jsonObject.getString("news_cat"));
                    n.setDate(jsonObject.getString("news_created_date"));
                    n.setImg(jsonObject.getString("news_image_file"));


                    newsData.add(n);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            lv.setAdapter(new MyBaseAdapter(context, newsData));


        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("newsData", newsData);
    }
}