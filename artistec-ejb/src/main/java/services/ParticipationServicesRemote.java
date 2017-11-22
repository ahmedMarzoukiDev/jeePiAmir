package services;

import java.util.List;

import javax.ejb.Remote;

import persistence.Challenge;
import persistence.Participation;
import persistence.User;

@Remote
public interface ParticipationServicesRemote {

	Boolean addParticipation(Participation participation);
	Boolean addJurryParticipation(Participation participation , Challenge challenge);


	void updateParticipation(Participation participation);

	void deleteParticipation(Participation participation);

	void deleteParticipationById(int iduser,int idchallenge);

	List<Participation> findParticipationById(int iduser,int idchallenge);

	List<Participation> findAllParticipation();
	
	List<Participation> findParticipatiantsByChallenge(int idchallenge);
	List<Participation> findParticipationByUser(int iduser);

}
