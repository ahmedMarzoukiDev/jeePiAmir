package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import persistence.Challenge;
import persistence.Participation;
import persistence.ParticipationState;
import persistence.RoleUser;
import persistence.User;

/**
 * Session Bean implementation class ParticipationServices
 */
@Stateless
public class ParticipationServices implements ParticipationServicesRemote, ParticipationServicesLocal {

	@PersistenceContext
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public ParticipationServices() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public Boolean addParticipation(Participation participation) {
		if(findParticipationById(participation.getParticipant().getUserId(), participation.getChallenge().getChallengeId()).isEmpty())
		{
			if(participation.getParticipant().getRole().equals(RoleUser.ARTIST)){
			participation.setStatusPart(ParticipationState.SIMPLPARTICIPANT);
			}
			else if (participation.getParticipant().getRole().equals(RoleUser.JURRY)) {
				participation.setStatusPart(ParticipationState.JURRY);
				}
			else {return false;

			}
		entityManager.persist(participation);
		return true;
		}
		return false;
	}

	@Override
	public void updateParticipation(Participation participation) {
		entityManager.merge(participation);
		
	}

	@Override
	public void deleteParticipation(Participation participation) {
		entityManager.remove(participation);
		
	}

	@Override
	public void deleteParticipationById(int iduser,int idchallenge) {
		String jpql = "DELETE FROM Participation p WHERE p.pk.participant.userId='"+iduser+"' and p.pk.challenge.challengeId='"+idchallenge+"'";
		
		Query query = entityManager.createQuery(jpql);
		query.executeUpdate();
		
	}

	@Override
	public List<Participation> findParticipationById(int iduser,int idchallenge) {
		String jpql = "select p FROM Participation p WHERE p.pk.participant.userId='"+iduser+"' and p.pk.challenge.challengeId='"+idchallenge+"'";
		Query query = entityManager.createQuery(jpql);
		List<Participation> resultList = query.getResultList();
		return resultList;
		
	}

	@Override
	public List<Participation> findAllParticipation() {
		String jpql = "SELECT u FROM Participation u";
		Query query = entityManager.createQuery(jpql);
		return query.getResultList();
	}

	@Override
	public List<Participation> findParticipatiantsByChallenge(int idchallenge) {
		String jpql = "SELECT p FROM Participation p WHERE p.pk.challenge.challengeId='"+idchallenge+"'";
		Query query = entityManager.createQuery(jpql);
		List<Participation> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public Boolean addJurryParticipation(Participation participation, Challenge challenge) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Participation> findParticipationByUser(int iduser) {
		String jpql = "select p FROM Participation p where p.pk.participant.userId='"+iduser+"'";
		Query query = entityManager.createQuery(jpql);
		List<Participation> resultList = query.getResultList();
		return resultList;
	}

}
