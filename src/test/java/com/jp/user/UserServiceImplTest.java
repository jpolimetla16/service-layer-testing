package com.jp.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceImplTest {
	
	@Mock
	private UserRepository userRepository;
	
	@Spy
	private ModelMapper mapper;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	private UserDetailsRequest request;
	
	private UserDetailsResponse expectedResponse;
	
	private UserEntity userEntity;
	
	@BeforeEach
	void init() {
		 userEntity = new UserEntity(1,"Janardhan Polimetla");
		 request = new UserDetailsRequest("Janardhan Polimetla");
		 expectedResponse = new UserDetailsResponse(1,"Janardhan Polimetla");
	}

	@Order(1)
	@Test
	void testCreateUser() {
		
		when(userRepository.save(Mockito.any())).thenReturn(userEntity);
		UserDetailsResponse actualResponse = userService.createUser(request);
		assertEquals(expectedResponse.getId(),actualResponse.getId());
		
	}

	@Order(2)
	@Test
	void testGetUser() {
		when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(userEntity));
		UserDetailsResponse actualResponse = userService.getUser(1);
		assertEquals(expectedResponse.getFullName(), actualResponse.getFullName());
	}

	@Order(3)
	@Test
	void testGetAllUsers() {
		
		UserEntity userEntity1 = new UserEntity(2,"Vidhya Polimetla");
		List<UserEntity> list = List.of(userEntity,userEntity1);
		when(userRepository.findAll()).thenReturn(list);
		
		List<UserDetailsResponse> actualList = userService.getAllUsers();
		assertThat(actualList).isNotNull();
		assertThat(actualList.size()).isGreaterThan(1);		
	}

	@Order(4)
	@Test
	void testUpdateUser() {
		
		request.setFullName("Janardhan Polimetla Modified");
		when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(userEntity));
		when(userRepository.save(Mockito.any())).thenReturn(userEntity);
		
		UserDetailsResponse actualResposne = userService.updateUser(1, request);
		System.out.println(actualResposne.getFullName());
		assertThat(actualResposne.getFullName()).isEqualTo("Janardhan Polimetla Modified");
		
		
	}

	@Order(5)
	@Test
	void testDeleteUser() {
		
		when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(userEntity));
		doNothing().when(userRepository).deleteById(Mockito.anyInt());
		userService.deleteUser(1);
		
		verify(userRepository,times(1)).deleteById(Mockito.anyInt());
		
		
	}

}
