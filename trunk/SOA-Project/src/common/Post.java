package common;

import java.util.ArrayList;
import java.sql.Date;

public class Post {

	private String mTitle;
	private Date mDate;
	private String mContent;
	private String mAuthor;
	private ArrayList<String> mTags;

	public Post(String pTitle, Date pDate, String pContent, String pAuthor,
			ArrayList<String> pTags) {

		this.mTitle = pTitle;
		this.mDate = pDate;
		this.mContent = pContent;
		this.mAuthor = pAuthor;
		this.mTags = pTags;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date mDate) {
		this.mDate = mDate;
	}

	public String getContent() {
		return mContent;
	}

	public void setContent(String mContent) {
		this.mContent = mContent;
	}

	public String getAuthor() {
		return mAuthor;
	}

	public void setAuthor(String mAuthor) {
		this.mAuthor = mAuthor;
	}

	public ArrayList<String> getTags() {
		return mTags;
	}

	public void setTags(ArrayList<String> mTags) {
		this.mTags = mTags;
	}



}
