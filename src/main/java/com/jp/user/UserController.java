package com.jp.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserDetailsResponse createUser(@RequestBody UserDetailsRequest request) {
		return userService.createUser(request);
	}

	@GetMapping("/{id}")
	public UserDetailsResponse getUser(@PathVariable Integer id) {
		return userService.getUser(id);
	}

	@GetMapping
	public List<UserDetailsResponse> getAllUsers() {
		return userService.getAllUsers();
	}

	@PutMapping("/{id}")
	public UserDetailsResponse updateUser(@PathVariable  Integer id, @RequestBody  UserDetailsRequest request) {
		return userService.updateUser(id, request);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable Integer id) {
		userService.deleteUser(id);
	}

}
