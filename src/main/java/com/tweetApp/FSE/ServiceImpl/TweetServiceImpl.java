package com.tweetApp.FSE.ServiceImpl;

import java.time.LocalDateTime;
import org.springframework.kafka.core.KafkaTemplate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetApp.FSE.DTO.ReplyDTO;
import com.tweetApp.FSE.DTO.TweetRequestDTO;
import com.tweetApp.FSE.DTO.TweetResponseDTO;
import com.tweetApp.FSE.Model.Register;
import com.tweetApp.FSE.Model.Reply;
import com.tweetApp.FSE.Model.Tweet;
import com.tweetApp.FSE.Repository.ReplyRepository;
import com.tweetApp.FSE.Repository.TweetRepository;
import com.tweetApp.FSE.Repository.UserRepository;
import com.tweetApp.FSE.Service.TweetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.kafka.support.SendResult;

@Service
public class TweetServiceImpl implements TweetService {

	@Autowired
	TweetRepository tweetRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ReplyRepository replyRepo;

	@Autowired
	TweetService tweetService;

	@Override
	public String postTweet(TweetRequestDTO tweetRequest) {
		// TODO Auto-generated method stub
		Tweet tweets = convertDTOToEntity(tweetRequest);
		tweetRepo.save(tweets);
		String msg = null;
		if (tweets != null) {
			msg = "Success";
			return msg;
		} else {
			msg = "Internal Server Error Occured";
		}
		return msg;
	}

	private Tweet convertDTOToEntity(TweetRequestDTO tweetRequest) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		Tweet tweets = new Tweet();

		List<Tweet> tweetList = tweetRepo.findByRecordActive('Y');
		if (tweetList == null) {
			tweets.setId(1);
		} else {
			int count = tweetList.size();
			tweets.setId(count + 1);
		}

		tweets.setTweetDescription(tweetRequest.getTweetDesc());
		tweets.setTweetTag(tweetRequest.getTweetTag());
		tweets.setFirstName(tweetRequest.getFirstName());
		tweets.setDate(dtf.format(now));
		Register register = userRepo.findByemail(tweetRequest.getEmailId());
		tweets.setEmail(register.getEmail());
		tweets.setRecordActive('Y');
		return tweets;
	}

	@Override
	public List<TweetResponseDTO> getAllTweets() {
		// TODO Auto-generated method stub

		List<TweetResponseDTO> tweetResponseDTOList = new ArrayList<>();
		List<Tweet> tweetList = tweetRepo.findByRecordActive('Y');
		tweetList.stream().forEach(tweet -> {
			TweetResponseDTO tweetResponseDTO = new TweetResponseDTO();
			tweetResponseDTO.setTweetDesc(tweet.getTweetDescription());
			tweetResponseDTO.setTweetBy(tweet.getEmail());
			tweetResponseDTO.setTweetId(tweet.getId());
			tweetResponseDTO.setFirstName(tweet.getFirstName());
			tweetResponseDTO.setDate(tweet.getDate());
			List<Reply> replyList=replyRepo.findByTweetId(tweet.getId());
			List<ReplyDTO> replyDTOList = new ArrayList<>();
			replyList.stream().forEach(reply ->{
				ReplyDTO replyDTO = new ReplyDTO();
				replyDTO.setEmail(reply.getEmail());
				replyDTO.setReplyDesc(reply.getReplyDesc());
				replyDTO.setFirstName(reply.getFirstName());
				replyDTO.setTweetId(reply.getTweetId());
				replyDTO.setDate(reply.getDate());
				replyDTOList.add(replyDTO);	
			});
			
			
			tweetResponseDTO.setReplyDTOList(replyDTOList);
			tweetResponseDTOList.add(tweetResponseDTO);
		});

		return tweetResponseDTOList;

	}

	@Override
	public String postReply(ReplyDTO replyDTO) {
		// TODO Auto-generated method stub
		Reply reply = converttDTOToReplyEntity(replyDTO);
		replyRepo.save(reply);
		String msg = null;
		if (reply != null) {
			msg = "Success";
			return msg;
		} else {
			msg = "Internal Server Error Occured";
		}
		return msg;
	}

	public Reply converttDTOToReplyEntity(ReplyDTO replyDTO) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		Reply reply = new Reply();
		reply.setEmail(replyDTO.getEmail());
		reply.setTweetId(replyDTO.getTweetId());
		reply.setReplyDesc(replyDTO.getReplyDesc());
		reply.setFirstName(replyDTO.getFirstName());
		reply.setDate(dtf.format(now));

		return reply;
	}

	@Override
	public String deleteTweet(int tweetId) {
		// TODO Auto-generated method stub
		tweetRepo.deleteById(tweetId);
		return "success";
	}
	
	@Service
	public class KafkaProducerService {
		
	    private final Logger LOG = LoggerFactory.getLogger(KafkaProducerService.class);
	    @Autowired
	    private KafkaTemplate<String, TweetRequestDTO> kafkaTemplate;

	    String kafkaTopic = "techgeeknext-topic";

	    public void send(TweetRequestDTO tweet) {
			tweet.setTweetTag("1");
			tweet.setFirstName(tweet.getFirstName());
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
		tweet.setTime(dtf.format(now));
	        System.out.println("Sending User Json Serializer : {}"+ tweet);        
	        ListenableFuture<SendResult<String, TweetRequestDTO>> future =kafkaTemplate.send(kafkaTopic, tweet);
	        System.out.println("future ----->" +future.isDone());
	        if(!future.isDone()){

	    		String message = tweetService.postTweet(tweet);
	    		System.out.println( "going into mongodb");
	        }
	        else {
	        	System.out.println("not going into mongodb");
	        }
	    }
}
}
