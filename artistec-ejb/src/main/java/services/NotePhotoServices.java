package services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import persistence.NotePhoto;
import persistence.Participation;

/**
 * Session Bean implementation class NotePhotoServices
 */
@Stateless
@LocalBean
public class NotePhotoServices implements NotePhotoServicesRemote, NotePhotoServicesLocal {

	
	
	@PersistenceContext
	private EntityManager entityManager;
    /**
     * Default constructor. 
     */
    public NotePhotoServices() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void addNotePhoto(NotePhoto notPhoto) {
		entityManager.persist(notPhoto);
		
	}

	@Override
	public void updateNotePhoto(NotePhoto notPhoto) {
		entityManager.merge(notPhoto);
		
	}

	@Override
	public void deleteNotePhoto(NotePhoto notPhoto) {
		entityManager.remove(notPhoto);
		
	}

	@Override
	public void deleteNotePhotoById(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<NotePhoto> findNotePhotoByPhotoId(int id) {
		
		String jpql = "select p FROM NotePhoto p WHERE p.pk.photo.photoChallengeId='"+id+"'";
		Query query = entityManager.createQuery(jpql);
		List<NotePhoto>  resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<NotePhoto> findNotePhotoById(int idPhoto, int idJurry) {

		String jpql = "select p FROM NotePhoto p WHERE p.pk.photo.photoChallengeId='"+idPhoto+"'and p.pk.jurry.userId='"+idJurry+"'";
		Query query = entityManager.createQuery(jpql);
		List<NotePhoto>  resultList = query.getResultList();
		return resultList;
	}

}
