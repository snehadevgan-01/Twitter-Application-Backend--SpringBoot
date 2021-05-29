package com.tweetApp.FSE.Model;

public class Reply {

	private String email;
	
	private int tweetId;
	
	private String replyDesc;
	
	private String date;
	
	private String firstName;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTweetId() {
		return tweetId;
	}

	public void setTweetId(int tweetId) {
		this.tweetId = tweetId;
	}

	public String getReplyDesc() {
		return replyDesc;
	}

	public void setReplyDesc(String replyDesc) {
		this.replyDesc = replyDesc;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String toString() {
		return "Reply [email=" + email + ", tweetId=" + tweetId + ", replyDesc=" + replyDesc + ", date=" + date
				+ ", firstName=" + firstName + "]";
	}
	
	
	
	
	
	
	
	

}
