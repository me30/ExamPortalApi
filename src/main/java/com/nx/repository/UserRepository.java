package com.nx.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import com.nx.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{
	
	@Query("select u From User u where u.userName = ?1 or u.email = ?1 or u.firstName = ?1 or u.lastName = ?1")
	Page<User> search(Pageable pageable, String queriableText);
	
	Optional<User> findByUserNameOrEmail(String username, String email);
	
	User findByEmail(String email);
	
	@Query("select u From User u where u.userName = ?1")
	UserDetails loadUserByUsername(String username);
	
	Boolean existsByUserName(String username);
	
	Boolean existsByEmail(String email);

	User findByResetToken(String token);
	
	@Query("select u From User u where u.id = ?1")
	User retrieveUserById(Long id);
}
