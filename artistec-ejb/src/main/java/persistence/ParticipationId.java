package persistence;

import java.io.Serializable;
import javax.persistence.*;
import persistence.Challenge;
import persistence.User;

/**
 * Entity implementation class for Entity: ParticipationId
 *
 */
@Embeddable
public class ParticipationId implements Serializable {

	@ManyToOne
    @JoinColumn(name = "userId")
	private User participant;
	
	@ManyToOne
    @JoinColumn(name = "challengeId")
	private Challenge challenge;
	

	
	private static final long serialVersionUID = 1L;

	public ParticipationId() {
		super();
	}   
	public User getParticipant() {
		return this.participant;
	}

	public void setParticipant(User participant) {
		this.participant = participant;
	}   
	public Challenge getChallenge() {
		return this.challenge;
	}

	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}
   
	
}
