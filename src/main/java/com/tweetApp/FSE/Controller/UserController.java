package com.tweetApp.FSE.Controller;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tweetApp.FSE.DTO.RegisterDTO;
import com.tweetApp.FSE.Service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.tweetApp.FSE.DTO.UserDTO;
import com.tweetApp.FSE.DTO.UserTweetsDTO;
import com.tweetApp.FSE.DTO.ResetPasswordRequestDTO;
import com.tweetApp.FSE.DTO.ResetPasswordResponseDTO;
import com.tweetApp.FSE.DTO.LoginRequestDTO;
import com.tweetApp.FSE.DTO.LoginResponseDTO;
import io.micrometer.core.annotation.Timed;

@Api(tags = { "TwittersUserManagement" })
@RestController
@RequestMapping(value = "/api/v1.0/tweets")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserService userService;

	
	
	@ApiOperation(value = "User Register", response = RegisterDTO.class, notes = "This API used to Register user.It receives emailId,loginId,firstName, lastName,"
			+ "and return registered user details ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 500, message = "Internal server error") })
    // collects the timely information about this endpoint
    @Timed(
            value = "monitoring.tweet.request",
            histogram = true,
            percentiles = {0.95, 0.99},
            extraTags = {"version", "1.0"}
    )
	@PostMapping("/register")
	public ResponseEntity<RegisterDTO> register(@RequestBody RegisterDTO registerDTO) {
		registerDTO = userService.register(registerDTO);
		LOGGER.info("User Registered");
		return ResponseEntity.ok().body(registerDTO);
	}

	
	
	@ApiOperation(value = "User Login", response = LoginResponseDTO.class, notes = "This API used to Login the user.It receives emailId,password"
			+ "and return logged in user details ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> signUp(@RequestBody LoginRequestDTO loginRequest) {
		LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
		loginResponseDTO = userService.signUp(loginRequest);
		LOGGER.info("User logged In");
		return ResponseEntity.ok().body(loginResponseDTO);
	}

	
	
	@ApiOperation(value = "Reset Password", response = ResetPasswordResponseDTO.class, notes = "This API used to reset the password of loggedin user.It receives new Password and old Password"
			+ "and reset password details ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PutMapping("/reset")
	public ResponseEntity<ResetPasswordResponseDTO> resetpassword(@RequestBody ResetPasswordRequestDTO resetRequest) {
		ResetPasswordResponseDTO resetPasswordResponse = new ResetPasswordResponseDTO();
		resetPasswordResponse = userService.resetpassword(resetRequest);

		return ResponseEntity.ok().body(resetPasswordResponse);
	}

	
	
	@ApiOperation(value = "Get all user", response = UserDTO.class, notes = "This API used to get all users account."
			+ "and return all loggedin users account details ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/getallUsers")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		String response = "getAllUsers" + new Date();
		LOGGER.info("Getting All Users", response);
		return ResponseEntity.ok().body(userService.getAllUsers());
	}

	
	
	@ApiOperation(value = "Get user tweet details", response = UserTweetsDTO.class, notes = "This API used to get loggedin user tweet details."
			+ "and return all loggedin users tweet details ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/UsersTweet")
	public ResponseEntity<List<UserTweetsDTO>> getUsersTweet(@RequestParam("email") String email) {
		LOGGER.info("Getting Users Tweet");
		return ResponseEntity.ok().body(userService.getUsersTweet(email));
	}

	
	@GetMapping("/check")
	public String check(@RequestParam("name") String name) {
		return "check name response";
	}
	
	
}
