package com.example.demo;

import com.example.demo.model.AdminAddUser;
import com.example.demo.model.AdminUserUpdate;
import com.example.demo.model.Group;
import com.example.demo.model.User;
import com.example.demo.repo.*;
import com.example.demo.service.FileService;
import com.example.demo.service.GroupService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser("admin")
@Transactional
@Rollback
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

	@Test
	void roles() {
		Assertions.assertEquals(roleRepository.findAll().size(), 3);
	}

	@Test

	void addGroup() {
		groupService.addGroup("Praktikanti", null);
		Assertions.assertTrue(groupRepository.findByName("Praktikanti") != null);
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
		groupService.addUserInGroup(7, 10);
		Assertions.assertNotNull(userGroupRepository.findByGroupUser(7, 10));
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

		if(userRepository.findById(14).isPresent()) userService.deleteUser(14);
		Assertions.assertFalse(userRepository.findById(14).isPresent());
	}

	@Test
	public void addUser() throws Exception {
		User user = userRepository.findByUsername("Selma13");
		if(user != null) userRepository.deleteById(user.getId());
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
		userService.updateUser(userUpdate, 13);
		Assertions.assertEquals(userUpdate.getActive(), userRepository.findById(13).get().isActive());
	}
/*
	@Test
	public void resetPassword() {
		Assertions.assertDoesNotThrow(() -> userService.resetPassword("lea55861@gmail.com"));
	}*/

	@Test
	public void deleteNonExistingFile() {
		Assertions.assertThrows(RuntimeException.class, () -> fileService.deleteFile(-1));
	}

	@Test
	public void updateFile() {
		var file = fileRepository.findById(38);
		fileService.updateFile(38, file.get().getTitle(), file.get().getDescription(), file.get().getExpiry(), 20);
		Assertions.assertEquals(20, fileRepository.findById(38).get().getMaxDownload());

	}

	@Test
	@Rollback
	public void addGroupInFile() {
		Group group = new Group();
		group.setName("New");
		fileService.addGroupInFile(38, 7);
		Assertions.assertTrue(fileRepository.findById(38).isPresent());
		Assertions.assertNotNull(fileGroupRepository.findByFileGroup(38, 7));

	}

	@Test
	@Rollback
	public void addUserInFile() {
		fileService.addUserInFile(39, 13);
		Assertions.assertTrue(fileRepository.findById(39).isPresent());
		Assertions.assertNotNull(fileUserRepository.findByFileUser(39, 13));
	}

}
