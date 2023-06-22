package com.example.demo;

import com.example.demo.model.AdminAddUser;
import com.example.demo.model.AdminUserUpdate;
import com.example.demo.model.User;
import com.example.demo.repo.*;
import com.example.demo.service.FileService;
import com.example.demo.service.GroupService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.springframework.security.config.http.MatcherType.mvc;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	GroupRepository groupRepository;
	@Autowired
	GroupService groupService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	@Autowired
	FileRepository fileRepository;
	@Autowired
	FileService fileService;
	@Autowired
	FileGroupRepository fileGroupRepository;
	@Autowired
	FileUserRepository fileUserRepository;
	@Autowired
	UserGroupRepository userGroupRepository;
	@Autowired
	private HttpServletRequest request;
/*	@Autowired
	private MockMvc mockMvc;

	void loginAdmin() throws Exception {
		MockHttpSession session = new MockHttpSession();

		Optional<User> user = userRepository.findById(1);

		session.setAttribute("user", user.get());

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/users")
				.session(session);
		this.mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status()
						.isOk());

	}*/
	@Test
	void roles() {
		Assertions.assertEquals(roleRepository.findAll().size(), 3);
	}

	@Test
	void addGroup() {
		groupService.addGroup("Managers", null);
		Assertions.assertTrue(groupRepository.findByName("Managers") != null);
	}


	@Test
	void updateGroupName() {
		var group = groupRepository.findAny();
		var idGrupe = group.getId();
		groupService.updateGroupName("DevOps", idGrupe);
		Assertions.assertEquals("DevOps", groupRepository.findById(idGrupe).get().getName());
	}

	@Test
	public void deletingNonExistingUserFromGroup() {
		Assertions.assertThrows(RuntimeException.class, () -> groupService.deleteUserInGroup(1, -10));
	}

	@Test
	public void addUserInGroup() {
		groupService.addUserInGroup(1, 10);
		Assertions.assertNotNull(userGroupRepository.findByGroupUser(1, 10));
	}

	@Test
	public void deleteNonExistingGroup() {
		Assertions.assertThrows(RuntimeException.class, () -> groupService.deleteGroup(1110));
	}

	@Test
	public void deleteUserFromNonExistingGroup() {
		Assertions.assertThrows(RuntimeException.class, () -> groupService.deleteUserInGroup(-5, 1));
	}

	@Test
	public void deleteNonExistingUser() {
		Assertions.assertThrows(RuntimeException.class, () -> userService.deleteUser(-10));
	}

	@Test
	public void deleteUser() {
		userService.deleteUser(14);
		Assertions.assertFalse(userRepository.findById(14).isPresent());
	}

	@Test
	public void addUser() throws Exception {
		//loginAdmin();
		var userAdd = new AdminAddUser();
		userAdd.setRole(2);
		userAdd.setEmail("lealalalal5543@gmail.com");
		userAdd.setUsername("Selma13");
		userService.addUser(userAdd);
		Assertions.assertEquals(userRepository.findByUsername("Selma13").getUsername(), "Selma13");

	}

	@Test
	public void adminDeactivateUserUpdate() {
		var userUpdate = new AdminUserUpdate();
		userUpdate.setActive(false);
		userUpdate.setEmail("lealealwaws43@gmail.com");
		userUpdate.setPassword("novipassword5");
		userUpdate.setRole(2);
		userUpdate.setUsername("Maja");
		userService.updateUser(userUpdate, 14);
		Assertions.assertEquals(userUpdate.getActive(), userRepository.findById(14).get().isActive());
	}
/*
	@Test
	public void resetPassword() {
		Assertions.assertDoesNotThrow(() -> userService.resetPassword("lea55861@gmail.com"));
	}*/

	@Test
	public void deleteFile() {
		Assertions.assertThrows(RuntimeException.class, () -> fileService.deleteFile(-1));
	}

	@Test
	public void updateFile() {
		var file = fileRepository.findById(34);
		fileService.updateFile(34, file.get().getTitle(), file.get().getDescription(), file.get().getExpiry(), 20);
		Assertions.assertEquals(20, fileRepository.findById(34).get().getMaxDownload());

	}

	@Test
	public void addGroupInFile() {

		fileService.addGroupInFile(34, 7);
		Assertions.assertTrue(fileRepository.findById(34).isPresent());
		Assertions.assertNotNull(fileGroupRepository.findByFileGroup(34, 7));

	}

	@Test
	public void addUserInFile() {

		fileService.addUserInFile(34, 10);
		Assertions.assertTrue(fileRepository.findById(34).isPresent());
		Assertions.assertNotNull(fileUserRepository.findByFileUser(34, 10));

	}

}
