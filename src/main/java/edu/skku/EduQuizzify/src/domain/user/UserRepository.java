package edu.skku.EduQuizzify.src.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.skku.EduQuizzify.src.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);

	boolean existsByUsername(String username);
}
