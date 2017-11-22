package persistence;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import persistence.Participation;

/**
 * Entity implementation class for Entity: PhotoChallenge
 *
 */
@Entity

public class PhotoChallenge implements Serializable {

	   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int photoChallengeId;
	
	@ManyToOne
	private Participation participation;
	private String imageUrl;
	private String imageName;
	private Date addDate;
	
	@OneToMany(mappedBy = "pk.photo",fetch=FetchType.EAGER,cascade = {CascadeType.REMOVE })
	private Set<NotePhoto> notePhotos = new HashSet<NotePhoto>();

	public Set<NotePhoto> getNotePhotos() {
		return notePhotos;
	}
	public void setNotePhotos(Set<NotePhoto> notePhotos) {
		this.notePhotos = notePhotos;
	}

	private static final long serialVersionUID = 1L;

	public PhotoChallenge() {
		super();
	}   
	public int getPhotoChallengeId() {
		return this.photoChallengeId;
	}

	public void setPhotoChallengeId(int photoChallengeId) {
		this.photoChallengeId = photoChallengeId;
	}   
	public Participation getParticipation() {
		return this.participation;
	}

	public void setParticipation(Participation participation) {
		this.participation = participation;
	}   
	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}   
	public String getImageName() {
		return this.imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}   
	public Date getAddDate() {
		return this.addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
   
}
