package com.example.inclurate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.squareup.picasso.Picasso;
 
public class MyBaseAdapter extends BaseAdapter {
 
    ArrayList myList = new ArrayList();
    LayoutInflater inflater;
    Context context;
    String baseUrl = "http://fs.royal.kz/640x480xc/";
 
 
    public MyBaseAdapter(Context context, ArrayList myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }
 
    @Override
    public int getCount() {
        return myList.size();
    }
 
    @Override
    public NewsData getItem(int position) {
        return (NewsData) myList.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;
 
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
 
        NewsData currentListData = getItem(position);
 
        mViewHolder.newsTitle.setText(currentListData.getTitle());
        mViewHolder.newsDesc.setText(currentListData.getDescription());
        mViewHolder.newsDate.setText(currentListData.getDate());
 
        
       
        Picasso.with(context)
        .load(baseUrl+currentListData.getImg())
        .into(mViewHolder.newsImg);
       
        
        return convertView;
    }
 
    private class MyViewHolder {
        TextView newsTitle, newsDesc,newsDate;
        ImageView newsImg;
 
        public MyViewHolder(View item) {
            newsTitle = (TextView) item.findViewById(R.id.title);
            newsDesc = (TextView) item.findViewById(R.id.description);
            newsDate = (TextView) item.findViewById(R.id.date);
            newsImg = (ImageView) item.findViewById(R.id.image);
        }
    }
}