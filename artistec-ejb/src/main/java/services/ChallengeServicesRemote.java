package services;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import persistence.Challenge;
import persistence.Participation;
import persistence.User;

@Remote
public interface ChallengeServicesRemote {

	void addChallenge(Challenge challenge);

	void updateChallenge(Challenge challenge);

	void deleteChallenge(Challenge challenge);

	void deleteChallengeById(int id);

	Challenge findChallengeById(int id);

	List<Challenge> findAllChallenge();
	List<Challenge> findChallengePerOwner(User user);
	List<Challenge> findChallengePerUser(User user);

	List<Participation> findUserPerChallenge(Challenge challenge);
	Map<Challenge,List<User>> findParticipantPerChallengePerOwner(User Owner);
	List<Challenge> findChallengePasser();
	User findWinner(Challenge challenge);



}
