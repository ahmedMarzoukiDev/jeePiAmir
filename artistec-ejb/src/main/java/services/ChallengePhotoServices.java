package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import persistence.Challenge;
import persistence.Participation;
import persistence.PhotoChallenge;

/**
 * Session Bean implementation class ChallengePhotoServices
 */
@Stateless
@LocalBean
public class ChallengePhotoServices implements ChallengePhotoServicesRemote, ChallengePhotoServicesLocal {

   
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
     * Default constructor. 
     */
    public ChallengePhotoServices() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void addPhotoChallenge(PhotoChallenge photoChallenge) {
		entityManager.persist(photoChallenge);
		entityManager.flush();	
	}

	@Override
	public void updatePhotoChallenge(PhotoChallenge photoChallenge) {
		entityManager.merge(photoChallenge);
		
	}

	@Override
	public void deletePhotoChallenge(PhotoChallenge photoChallenge) {
		entityManager.remove(photoChallenge);
		
	}

	@Override
	public void deletePhotoChallengeById(int id) {
		entityManager.remove(findPhotoChallengeById(id));
		
	}

	@Override
	public PhotoChallenge findPhotoChallengeById(int id) {
		return entityManager.find(PhotoChallenge.class, id);
		
	}

	@Override
	public List<PhotoChallenge> findAllPhotoChallenge() {
		String jpql = "SELECT u FROM PhotoChallenge u";
		Query query = entityManager.createQuery(jpql);
		return query.getResultList();
	}

	@Override
	public List<PhotoChallenge> findPhotoChallengeOfUserParChallenge(int userId, int challengeId) {
		String jpql = "select p FROM PhotoChallenge p where p.participation.pk.participant.userId='"+userId+"' and p.participation.pk.challenge.challengeId='"+challengeId+"'";
		Query query = entityManager.createQuery(jpql);
		List<PhotoChallenge> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<PhotoChallenge> findAllPhotoChallengeByChallenge(int idchallenge) {
		String jpql = "select p FROM PhotoChallenge p where p.participation.pk.challenge.challengeId='"+idchallenge+"'";
		Query query = entityManager.createQuery(jpql);
		List<PhotoChallenge> resultList = query.getResultList();
		return resultList;
	}

}
