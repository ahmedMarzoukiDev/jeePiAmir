package services;

import java.util.List;

import javax.ejb.Remote;

import persistence.NotePhoto;

@Remote
public interface NotePhotoServicesRemote {

	void addNotePhoto(NotePhoto notPhoto);

	void updateNotePhoto(NotePhoto notPhoto);

	void deleteNotePhoto(NotePhoto notPhoto);

	void deleteNotePhotoById(int id);
	List<NotePhoto> findNotePhotoByPhotoId(int id);
	List<NotePhoto> findNotePhotoById(int idPhoto, int idJurry);

}
