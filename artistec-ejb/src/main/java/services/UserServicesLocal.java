package services;

import java.util.List;

import javax.ejb.Local;

import persistence.RoleUser;
import persistence.User;

@Local
public interface UserServicesLocal {

	void addUser(User user);

	void updateUser(User user);

	void deleteUser(User user);

	void deleteUserById(int id);

	User findUserById(int id);

	List<User> findAllUsers();
	List<User> findUsersByRole(RoleUser roleUser);
	User authentification(String username, String pwd);
}
