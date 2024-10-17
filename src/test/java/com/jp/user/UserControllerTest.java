package com.jp.user;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = {UserController.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class UserControllerTest {
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private UserDetailsRequest request;
	
	private UserDetailsResponse  response;
	
	@BeforeEach
	void setup() {
	  request = new UserDetailsRequest("Janardhan Polimetla");
	  response = new UserDetailsResponse(1, "Janardhan Polimetla");
	}
	
	@Order(1)
	@Test
	void testCreateUser() throws JsonProcessingException, Exception {
		
		when(userService.createUser(Mockito.any(UserDetailsRequest.class))).thenReturn(response);
		
		ResultActions result = mockMvc.perform(post("/users")
				  .contentType(MediaType.APPLICATION_JSON_VALUE)
				  .accept(MediaType.APPLICATION_JSON_VALUE)
				  .content(objectMapper.writeValueAsString(request))
				);
		
		System.out.println("JP:"+result.andReturn().getResponse().getContentAsString());
		
		result.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id",is(response.getId())))
				.andExpect(jsonPath("$.fullName",is(response.getFullName())));
		
		Mockito.verify(userService,times(1)).createUser(Mockito.any(UserDetailsRequest.class));
		
	}

	@Order(2)
	@Test
	void testGetUser() throws Exception {
		
		when(userService.getUser(Mockito.anyInt())).thenReturn(response);
		
		ResultActions result = mockMvc.perform(get("/users/{id}",1));
		result.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(response.getId())))
			.andExpect(jsonPath("$.fullName", is(response.getFullName())));
		
		Mockito.verify(userService,times(1)).getUser(Mockito.anyInt());
		
	}

	@Order(3)
	@Test
	void testGetAllUsers() throws Exception {
		UserDetailsResponse response1 = new UserDetailsResponse(2, "Vidhya Polimetla");
		List<UserDetailsResponse> list = List.of(response,response1);
		when(userService.getAllUsers()).thenReturn(list);
		
		ResultActions mvcResult = mockMvc.perform(get("/users"));
		mvcResult.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()", is(list.size())));
		
		Mockito.verify(userService,times(1)).getAllUsers();
	}

	@Order(4)
	@Test
	void testUpdateUser() throws JsonProcessingException, Exception {
		
		   when(userService.updateUser(Mockito.anyInt(), Mockito.any())).thenReturn(response);
			
			ResultActions mvcResult = mockMvc.perform(put("/users/{id}",1)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(request)));
			
			mvcResult.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id",is(response.getId())))
					.andExpect(jsonPath("$.fullName",is(response.getFullName())));
			
			Mockito.verify(userService,times(1)).updateUser(Mockito.anyInt(), Mockito.any());
					
				
	}

	@Order(5)
	@Test
	void testDeleteUser() throws Exception {
		ResultActions mvcResult = mockMvc.perform(delete("/users/{id}",1));
		mvcResult.andDo(print())
				.andExpect(status().isNoContent());
				
	}

}
