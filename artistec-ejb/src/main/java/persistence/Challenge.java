package persistence;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import persistence.User;

/**
 * Entity implementation class for Entity: Challenge
 *
 */
@Entity
public class Challenge implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int challengeId;
	private String title;
	private String description;
	private Date addDate;
	private Date startDate;
	private Date endDate;
	private ArtType type;
	private String image;
	@ManyToOne
	private User galleryOwner;
	@ManyToOne
	private User winner;
	
	@OneToMany(mappedBy = "pk.challenge", fetch=FetchType.EAGER,cascade = {CascadeType.ALL })
	private Set<Participation> participation = new HashSet<Participation>();
	
	public Set<Participation> getParticipation() {
	        return this.participation;
	}
	public void setParticipation(Set<Participation> r) {
	        this.participation = r;
	}
	
	private static final long serialVersionUID = 1L;

	public Challenge() {
		super();
	}   
	public int getChallengeId() {
		return this.challengeId;
	}

	public void setChallengeId(int challengeId) {
		this.challengeId = challengeId;
	}   
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}   
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}   
	public Date getAddDate() {
		return this.addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}   
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}   
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}   
	public User getGalleryOwner() {
		return this.galleryOwner;
	}

	public void setGalleryOwner(User galleryOwner) {
		this.galleryOwner = galleryOwner;
	}
	
	public ArtType getType() {
		return type;
	}
	
	public void setType(ArtType type) {
		this.type = type;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public User getWinner() {
		return winner;
	}
	public void setWinner(User winner) {
		this.winner = winner;
	}
	
   
}
