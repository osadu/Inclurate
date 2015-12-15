package com.example.inclurate;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsData implements Parcelable{

	
	private String title;
	private String description;
	private String date;
	private String img;

	public NewsData(){}
	protected NewsData(Parcel in) {
		title = in.readString();
		description = in.readString();
		date = in.readString();
		img = in.readString();
	}

	public static final Creator<NewsData> CREATOR = new Creator<NewsData>() {
		@Override
		public NewsData createFromParcel(Parcel in) {
			return new NewsData(in);
		}

		@Override
		public NewsData[] newArray(int size) {
			return new NewsData[size];
		}
	};

	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(title);
		parcel.writeString(description);
		parcel.writeString(date);
		parcel.writeString(img);
	}
}
