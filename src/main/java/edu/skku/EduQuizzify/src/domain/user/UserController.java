package edu.skku.EduQuizzify.src.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.skku.EduQuizzify.src.domain.user.entity.User;
import edu.skku.EduQuizzify.src.domain.user.service.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private UserService userService;

	// 간단한 회원가입 API
	@PostMapping("/signup")
	public String registerUser(@RequestBody User user) {
		return userService.createUser(user);
	}
}