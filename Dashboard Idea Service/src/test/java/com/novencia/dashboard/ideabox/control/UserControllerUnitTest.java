package com.novencia.dashboard.ideabox.control;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.text.AbstractDocument.Content;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novencia.dashboard.ideabox.domain.User;
import com.novencia.dashboard.ideabox.exception.SequenceException;
import com.novencia.dashboard.ideabox.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerUnitTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	UserService userService;

	/*
	 * converts a Java object into JSON representation
	 */
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testGetAllUser() throws IOException, Exception {

		List<User> users = Arrays.asList(new User("login","toto", "TOTO"), new User("login","lulu", "LULU"));
		Mockito.when(this.userService.findAllUsers()).thenReturn(users);
		mvc.perform(get("/users")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(asJsonString(users)));
		Mockito.verify(userService, Mockito.times(1)).findAllUsers();
		Mockito.verifyNoMoreInteractions(userService);

	}

	@Test
	public void testGetUser() throws IOException, Exception {

		User user = new User("login","toto", "TOTO");
		Mockito.when(this.userService.findById(1)).thenReturn(user);
		mvc.perform(get("/users/{id}", 1)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(asJsonString(user)));
		Mockito.verify(userService, Mockito.times(1)).findById(1);
		Mockito.verifyNoMoreInteractions(userService);

	}

	@Test
	public void testGetUserNoFound() throws IOException, Exception {

		Mockito.when(this.userService.findById(1)).thenReturn(null);
		mvc.perform(get("/users/{id}", 1)).andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().string("User with id 1 not found"));
		Mockito.verify(userService, Mockito.times(1)).findById(1);
		Mockito.verifyNoMoreInteractions(userService);
	}

	@Test
	public void testDeleteUser() throws IOException, Exception {
		User user = new User("login", "toto", "TOTO");
		Mockito.when(this.userService.findById(1)).thenReturn(user);
		Mockito.doNothing().when(userService).deleteUserById(1);
		mvc.perform(delete("/users/{id}", 1)).andExpect(status().isOk());
		Mockito.verify(userService, Mockito.times(1)).deleteUserById(1);
	}

	@Test
	public void testDeleteAllUser() throws IOException, Exception {
		Mockito.doNothing().when(userService).deleteAllUsers();
		mvc.perform(delete("/users")).andExpect(status().isOk());
		Mockito.verify(userService, Mockito.times(1)).deleteAllUsers();
		Mockito.verifyNoMoreInteractions(userService);
	}

	@Test
	public void testCreateUserSuccess() throws IOException, Exception {
		User user = new User("login", "toto", "TOTO");
		Mockito.doNothing().when(userService).createUser(user);
		mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
				.andExpect(status().isCreated());
		Mockito.verify(userService, Mockito.times(1)).createUser(user);
		Mockito.verifyNoMoreInteractions(userService);
	}

	@Test
	public void testCreateUserFailed() throws IOException, Exception {
		User user = new User("login", "toto", "TOTO");
		Mockito.doThrow(new SequenceException("user")).when(userService).createUser(user);
		mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
				.andExpect(status().isPreconditionFailed());
		Mockito.verify(userService, Mockito.times(1)).createUser(user);
		Mockito.verifyNoMoreInteractions(userService);
	}
	
	@Test
	public void testUpdateUserSuccess() throws IOException, Exception {
		User user = new User("login", "toto", "TOTO");
		Mockito.doNothing().when(userService).updateUser(1,user);
		mvc.perform(put("/users/{id}",1).contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
				.andExpect(status().isOk());
		Mockito.verify(userService, Mockito.times(1)).updateUser(1,user);
		Mockito.verifyNoMoreInteractions(userService);
	}

	@Test
	public void testUpdateUserFailed() throws IOException, Exception {
		User user = new User("login", "toto", "TOTO");
		Mockito.doThrow(new Exception("error message")).when(userService).updateUser(1,user);
		mvc.perform(put("/users/{id}",1).contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
				.andExpect(status().isConflict())
				.andExpect(MockMvcResultMatchers.content().string("Unable to update user.error message"));
		Mockito.verify(userService, Mockito.times(1)).updateUser(1,user);
		Mockito.verifyNoMoreInteractions(userService);
	}

}
