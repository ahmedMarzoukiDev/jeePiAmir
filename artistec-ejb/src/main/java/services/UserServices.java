package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import persistence.RoleUser;
import persistence.User;

/**
 * Session Bean implementation class UserServices
 */
@Stateless
public class UserServices implements UserServicesRemote, UserServicesLocal {

	@PersistenceContext
	private EntityManager entityManager;
    /**
     * Default constructor. 
     */
    public UserServices() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void addUser(User user) {
		entityManager.persist(user);
		entityManager.flush();

	}

	@Override
	public void updateUser(User user) {
		entityManager.merge(user);
		
	}

	@Override
	public void deleteUser(User user) {
		entityManager.remove(user);
		
	}

	@Override
	public void deleteUserById(int id) {
		entityManager.remove(findUserById(id));
		
	}

	@Override
	public User findUserById(int id) {
		return entityManager.find(User.class, id);
	}

	@Override
	public List<User> findAllUsers() {
		String jpql = "SELECT u FROM User u";
		Query query = entityManager.createQuery(jpql);
		return query.getResultList();
	}

	@Override
	public List<User> findUsersByRole(RoleUser roleUser) {
		String jpql = "SELECT u FROM User u WHERE u.role=:roleUser";
		Query query = entityManager.createQuery(jpql).setParameter("roleUser", roleUser);
		return query.getResultList();
	}

	@Override
	public User authentification(String username, String pwd) {
		String jpql = "SELECT z FROM User z WHERE z.login=:login and z.password=:pwd";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("login", username);
		query.setParameter("pwd", pwd);
		User user= null;
		try {
		  user=(User)query.getSingleResult();

	
			} catch (Exception e) {
				// TODO: handle exception
			}

		return user;
	}

}
