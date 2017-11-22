package persistence;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class NotePhotoId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
    @JoinColumn(name = "id_photo")
    private PhotoChallenge photo;
	
	
	 @ManyToOne
     @JoinColumn(name = "id_jurry")
     private User jurry;


	public PhotoChallenge getPhoto() {
		return photo;
	}


	public void setPhoto(PhotoChallenge photo) {
		this.photo = photo;
	}


	public User getJurry() {
		return jurry;
	}


	public void setJurry(User jurry) {
		this.jurry = jurry;
	}
    
	 
	 
}
