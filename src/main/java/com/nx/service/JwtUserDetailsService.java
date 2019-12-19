package com.nx.service;

import com.nx.entity.User;

public interface JwtUserDetailsService extends IFinder<User> , IService<User>{
	
	public User loadUserByUsername(String username);

}
