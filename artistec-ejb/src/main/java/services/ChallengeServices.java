package services;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import persistence.Challenge;
import persistence.NotePhoto;
import persistence.Participation;
import persistence.PhotoChallenge;
import persistence.User;

/**
 * Session Bean implementation class ChallengeServices
 */
@Stateless
public class ChallengeServices implements ChallengeServicesRemote, ChallengeServicesLocal {

	@PersistenceContext
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public ChallengeServices() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void addChallenge(Challenge challenge) {
		entityManager.persist(challenge);
		entityManager.flush();
		
	}

	@Override
	public void updateChallenge(Challenge challenge) {
		entityManager.merge(challenge);
		
	}

	@Override
	public void deleteChallenge(Challenge challenge) {
		entityManager.remove(challenge);
		
	}

	@Override
	public void deleteChallengeById(int id) {
		entityManager.remove(findChallengeById(id));
		
	}

	@Override
	public Challenge findChallengeById(int id) {
		return entityManager.find(Challenge.class, id);

	}

	@Override
	public List<Challenge> findAllChallenge() {
		String jpql = "SELECT u FROM Challenge u";
		Query query = entityManager.createQuery(jpql);
		return query.getResultList();
	}

	@Override
	public List<Challenge> findChallengePerOwner(User u) {
		String jpql = "SELECT c FROM Challenge c WHERE c.galleryOwner.userId='"+u.getUserId()+"'";
		Query query = entityManager.createQuery(jpql);
		return query.getResultList();
	}

	@Override
	public List<Participation> findUserPerChallenge(Challenge challenge) {
		List<Participation> p = new ArrayList<Participation>();
		String jpql = "SELECT  p FROM Participation p WHERE p.pk.challenge.challengeId='"+challenge.getChallengeId()+"'";
		Query query = entityManager.createQuery(jpql);
		
		p= query.getResultList();
	    return p;
		}

	@Override
	public Map<Challenge, List<User>> findParticipantPerChallengePerOwner(User Owner) {
		Map<Challenge,List<User>> map = new HashMap<Challenge,List<User>>();
		for (Challenge  p: findChallengePerOwner(Owner)){
			
			List<User> lstu = new ArrayList<>();
			for (Participation  u: p.getParticipation()){
			 lstu.add(u.getPk().getParticipant());
			 }
			map.put(p, lstu);
		}

		return map;
	}

	@Override
	public List<Challenge> findChallengePerUser(User user) {
		List<Challenge> p = new ArrayList<Challenge>();
		String jpql = "SELECT  c FROM Challenge c WHERE c.pk.participant.userId='"+user.getUserId()+"'";
		Query query = entityManager.createQuery(jpql);
		
		p= query.getResultList();
	    return p;
	}

	@Override
	public List<Challenge> findChallengePasser() {
		List<Challenge> lst = new ArrayList<Challenge>();
		Date dateSys = Calendar.getInstance().getTime();
		for (Challenge challenge : findAllChallenge()) {
			if (challenge.getEndDate().before(dateSys)) {
				lst.add(challenge);
			}
		}
	    return lst;
	}

	@Override
	public User findWinner(Challenge challenge) {
		User winner = new User();
		ParticipationServices participationservice=new ParticipationServices();
		ChallengePhotoServices photoService = new ChallengePhotoServices();
		Map<User,Float> map = new HashMap<User , Float >();
		
		
		for (Participation participation : participationservice.findParticipatiantsByChallenge(2)) {
			float moyenneC=0;
			for (PhotoChallenge photo : photoService.findPhotoChallengeOfUserParChallenge(participation.getPk().getParticipant().getUserId(), challenge.getChallengeId())) {
				float moyenneP =0;
				for (NotePhoto note : photo.getNotePhotos()) {
					moyenneP=moyenneP+note.getNote();
				}
				moyenneP=moyenneP/photo.getNotePhotos().size();
				moyenneC=moyenneC+moyenneP;
			}
			moyenneC=moyenneC/photoService.findPhotoChallengeOfUserParChallenge(participation.getPk().getParticipant().getUserId(), challenge.getChallengeId()).size();
			map.put(participation.getParticipant(), moyenneC);
		}
		
		Map.Entry<User, Float> maxEntry = null;

		for (Map.Entry<User, Float> entry : map.entrySet())
		{
		    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
		    {
		        maxEntry = entry;
		    }
		}
		winner = maxEntry.getKey();
		
		return winner;
	}
		
	

	

}
