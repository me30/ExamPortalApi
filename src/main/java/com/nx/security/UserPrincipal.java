package com.nx.security;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nx.entity.User;

public class UserPrincipal implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String gender;
	private Date dob;

	private Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(Long id, String firstName, String lastName, String email, String password,
			String gender, Date dob, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.dob = dob;
		this.authorities = authorities;
	}

	public static UserPrincipal create(User user) {
		
		List<GrantedAuthority> authority = new ArrayList<>();
		authority.add(new SimpleGrantedAuthority(user.getRole().toString()));
				
		return new UserPrincipal(
				user.getId(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				user.getPassword(),
				user.getGender(),
				user.getDob(),
				authority
				);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Long getId() {
		return id;
	}

	/*public void setId(Long id) {
		this.id = id;
	}*/

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getGender() {
		return gender;
	}

	public Date getDob() {
		return dob;
	}	
	
	//removed setter methods
}
