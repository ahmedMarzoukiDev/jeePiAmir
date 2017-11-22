package persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import persistence.ParticipationId;

/**
 * Entity implementation class for Entity: Participation
 *
 */
@Entity
public class Participation implements Serializable {

	@Id
	private ParticipationId pk;
	private Date inscripDate;
	@OneToMany( cascade = {CascadeType.REMOVE },mappedBy="participation" )
	private List<PhotoChallenge> photos;
	private ParticipationState statusPart;
	private static final long serialVersionUID = 1L;

	public Participation() {
		super();
	}   
	public ParticipationId getPk() {
		return this.pk;
	}

	public void setPk(ParticipationId pk) {
		this.pk = pk;
	}   
	public Date getInscripDate() {
		return this.inscripDate;
	}

	public void setInscripDate(Date inscripDate) {
		this.inscripDate = inscripDate;
	}
	
	public User getParticipant() {
        return getPk().getParticipant();
		}

	public void setParticipant(User participant) {
        getPk().setParticipant(participant);;
	}
	
	
	
	public Challenge getChallenge() {
        return getPk().getChallenge();
		}

	public void setChallenge(Challenge challenge) {
        getPk().setChallenge(challenge);;
	}
	
   
	public List<PhotoChallenge> getPhotos() {
		return photos;
	}
	
	public void setPhotos(List<PhotoChallenge> photos) {
		this.photos = photos;
	}
	
	public ParticipationState getStatusPart() {
		return statusPart;
	}
	
	public void setStatusPart(ParticipationState statusPart) {
		this.statusPart = statusPart;
	}
}
