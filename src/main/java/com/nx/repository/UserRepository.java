package com.nx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import com.nx.entity.User;

import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<Long>{

	@Query("Select u From User u where username = :username and password = :password")
	public User getUserbyUsernameandPassword(@Param("username")String username,@Param("password")String password);
	
	@Query("Select u From User u where email = :email ")
	public User findByEmail(@Param("email")String email);
	
	@Query("Select u From User u where username = :username")
	public Optional<User> getUserByusername(@Param("username") String userName);
	
	@Query("Select u From User u where username = ?1")
	public UserDetails loadUserByUsername(String username);
	
	Optional<User> findByUsernameOrEmail(String username, String email);
		
	UserDetails findByUsername(String username);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
		
}
