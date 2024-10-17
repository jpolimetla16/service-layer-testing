package com.jp.user;

import java.util.List;

public interface UserService {
	
	public UserDetailsResponse createUser(UserDetailsRequest request);
	public UserDetailsResponse getUser(Integer id);
	public List<UserDetailsResponse> getAllUsers();
	public UserDetailsResponse updateUser(Integer id,UserDetailsRequest request);
	public void deleteUser(Integer id);
	

}
