package edu.skku.EduQuizzify.src.domain.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.skku.EduQuizzify.src.domain.user.UserRepository;
import edu.skku.EduQuizzify.src.domain.user.entity.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}

	public String createUser(User user) {
		// 중복된 이메일 또는 사용자 이름 검사
		if (userRepository.existsByEmail(user.getEmail())) {
			return "Email is already taken.";
		}

		if (userRepository.existsByUsername(user.getUsername())) {
			return "Username is already taken.";
		}

		// 유저 정보 저장
		userRepository.save(user);
		return "User registered successfully.";
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
