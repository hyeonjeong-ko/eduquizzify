package edu.skku.EduQuizzify;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.skku.EduQuizzify.src.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
