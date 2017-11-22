package persistence;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
@Table(name="T_USER")
public class User implements Serializable {

	   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private RoleUser role;
	private ArtType artType;
	private String password;
	private String login;
	private String imageUrl;
	
	@OneToMany(mappedBy="galleryOwner")
	private List<Challenge> challenges;
	
	@OneToMany(mappedBy="winner")
	private List<Challenge> challengesWinned;
	
	@OneToMany(mappedBy = "pk.participant", fetch=FetchType.EAGER)
	private Set<Participation> participation = new HashSet<Participation>();
	
	@OneToMany(mappedBy = "pk.jurry")
	private Set<NotePhoto> notePhotos = new HashSet<NotePhoto>();
	
	public Set<NotePhoto> getNotePhotos() {
		return notePhotos;
	}
	public void setNotePhotos(Set<NotePhoto> notePhotos) {
		this.notePhotos = notePhotos;
	}
	public Set<Participation> getParticipation() {
	        return this.participation;
	}
	public void setParticipation(Set<Participation> r) {
	        this.participation = r;
	}
	
	
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}




	private static final long serialVersionUID = 1L;
	
	
	

	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public User() {
		super();
	}   
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}   
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}   
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}   
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
   
	public List<Challenge> getChallenges() {
		return challenges;
	}
	public void setChallenges(List<Challenge> challenges) {
		this.challenges = challenges;
	}
	public RoleUser getRole() {
		return role;
	}
	public void setRole(RoleUser role) {
		this.role = role;
	}
	
	public ArtType getArtType() {
		return artType;
	}
	
	public void setArtType(ArtType artType) {
		this.artType = artType;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public List<Challenge> getChallengesWinned() {
		return challengesWinned;
	}
	public void setChallengesWinned(List<Challenge> challengesWinned) {
		this.challengesWinned = challengesWinned;
	}
	
}
