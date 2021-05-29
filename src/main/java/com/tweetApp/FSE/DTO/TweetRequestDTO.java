package com.tweetApp.FSE.DTO;

import java.time.LocalDateTime;

public class TweetRequestDTO {
	
	private String tweetDesc;
	
	private String tweetTag;
	
	private String emailId;
	
	private String firstName;
	
	private String time;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((tweetDesc == null) ? 0 : tweetDesc.hashCode());
		result = prime * result + ((tweetTag == null) ? 0 : tweetTag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TweetRequestDTO other = (TweetRequestDTO) obj;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (tweetDesc == null) {
			if (other.tweetDesc != null)
				return false;
		} else if (!tweetDesc.equals(other.tweetDesc))
			return false;
		if (tweetTag == null) {
			if (other.tweetTag != null)
				return false;
		} else if (!tweetTag.equals(other.tweetTag))
			return false;
		return true;
	}

	public String getTweetDesc() {
		return tweetDesc;
	}

	public void setTweetDesc(String tweetDesc) {
		this.tweetDesc = tweetDesc;
	}

	public String getTweetTag() {
		return tweetTag;
	}

	public void setTweetTag(String tweetTag) {
		this.tweetTag = tweetTag;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}



}
