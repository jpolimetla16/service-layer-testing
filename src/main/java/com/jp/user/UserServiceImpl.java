package com.jp.user;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private ModelMapper modelMapper;
	
	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
		super();
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public UserDetailsResponse createUser(UserDetailsRequest request) {
		UserEntity userEntity = modelMapper.map(request, UserEntity.class);
		return modelMapper.map(userRepository.save(userEntity),UserDetailsResponse.class);
	}

	@Override
	public UserDetailsResponse getUser(Integer id) {
		UserEntity userEntity = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("Invalid Id:"+id));
		return modelMapper.map(userEntity, UserDetailsResponse.class);
	}

	@Override
	public List<UserDetailsResponse> getAllUsers() {
		return userRepository.findAll().stream()
				.map(e->modelMapper.map(e, UserDetailsResponse.class)).toList();
	}

	@Override
	public UserDetailsResponse updateUser(Integer id, UserDetailsRequest request) {
		UserEntity existingUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("Invalid Id:"+id));
		existingUser.setFullName(request.getFullName());
		return modelMapper.map(userRepository.save(existingUser),UserDetailsResponse.class);
	}

	@Override
	public void deleteUser(Integer id) {
		 userRepository.findById(id).orElseThrow(()->new UserNotFoundException("Invalid Id:"+id));
		 userRepository.deleteById(id);
	}

}
