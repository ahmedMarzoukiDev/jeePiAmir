package services;

import java.util.List;

import javax.ejb.Local;

import persistence.PhotoChallenge;



@Local
public interface ChallengePhotoServicesLocal {

	void addPhotoChallenge(PhotoChallenge photoChallenge);

	void updatePhotoChallenge(PhotoChallenge photoChallenge);

	void deletePhotoChallenge(PhotoChallenge photoChallenge);

	void deletePhotoChallengeById(int id);

	PhotoChallenge findPhotoChallengeById(int id);

	List<PhotoChallenge> findAllPhotoChallenge();
	
	List<PhotoChallenge> findPhotoChallengeOfUserParChallenge(int userId, int challengeId);

	List<PhotoChallenge> findAllPhotoChallengeByChallenge(int idchallenge);

	
	
}
