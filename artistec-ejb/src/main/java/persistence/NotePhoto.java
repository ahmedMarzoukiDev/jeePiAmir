package persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: NotePhoto
 *
 */
@Entity
public class NotePhoto implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public NotePhoto() {
		super();
	}
   
	@Id
	NotePhotoId pk;
	
	private int note;
	
	public NotePhotoId getPk() {
		return pk;
	}

	public void setPk(NotePhotoId pk) {
		this.pk = pk;
	}

	public int getNote() {
		return note;
	}

	
	public void setNote(int note) {
		this.note = note;
	}

	
}
