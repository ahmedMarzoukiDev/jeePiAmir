package services;

import java.util.List;

import javax.ejb.Remote;

import persistence.PhotoChallenge;

@Remote
public interface ChallengePhotoServicesRemote {
	void addPhotoChallenge(PhotoChallenge photoChallenge);

	void updatePhotoChallenge(PhotoChallenge photoChallenge);

	void deletePhotoChallenge(PhotoChallenge photoChallenge);

	void deletePhotoChallengeById(int id);

	PhotoChallenge findPhotoChallengeById(int id);

	List<PhotoChallenge> findAllPhotoChallenge();
	List<PhotoChallenge> findPhotoChallengeOfUserParChallenge(int userId, int challengeId);
	List<PhotoChallenge> findAllPhotoChallengeByChallenge(int idchallenge);

}
