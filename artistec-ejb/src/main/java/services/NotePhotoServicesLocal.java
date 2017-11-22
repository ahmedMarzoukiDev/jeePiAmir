package services;

import java.util.List;

import javax.ejb.Local;

import persistence.NotePhoto;


@Local
public interface NotePhotoServicesLocal {

	void addNotePhoto(NotePhoto notPhoto);

	void updateNotePhoto(NotePhoto notPhoto);

	void deleteNotePhoto(NotePhoto notPhoto);

	void deleteNotePhotoById(int id);
	List<NotePhoto> findNotePhotoByPhotoId(int id);
	List<NotePhoto> findNotePhotoById(int idPhoto, int idJurry);
	
}
