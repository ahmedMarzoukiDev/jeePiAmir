package Tools;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import persistence.Challenge;
import persistence.NotePhoto;
import persistence.NotePhotoId;
import persistence.Participation;
import persistence.ParticipationId;
import persistence.PhotoChallenge;
import persistence.RoleUser;
import persistence.ArtType;
import persistence.User;
import services.ChallengePhotoServicesLocal;
import services.ChallengeServicesLocal;
import services.NotePhotoServicesLocal;
import services.ParticipationServicesLocal;
import services.UserServicesLocal;

@Singleton
@LocalBean
@Startup
public class ArtistecTool {
/*
	@EJB
	private ChallengeServicesLocal challengeServicesLocal;
	@EJB
	private ChallengePhotoServicesLocal challengePhotoServicesLocal;
	@EJB
	private ParticipationServicesLocal participationServicesLocal;
	@EJB
	private UserServicesLocal userServicesLocal;
	@EJB
	private NotePhotoServicesLocal notePhotoServicesLocal;

	
	public ArtistecTool() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init() {
		//Add Users 
		User partcipant = new User();
		partcipant.setFirstName("Amir");
		partcipant.setLastName("Ben Moussa");
		partcipant.setRole(RoleUser.GALLERYOWNER);
		partcipant.setLogin("amir");
		partcipant.setPassword("123456789");
		userServicesLocal.addUser(partcipant);
		
		
		User partcipant2 = new User();
		partcipant2.setFirstName("Mohamed");
		partcipant2.setLastName("Ben Issa");
		partcipant2.setRole(RoleUser.JURRY);
		partcipant2.setLogin("amir2");
		partcipant2.setPassword("123456789");
		partcipant2.setArtType(ArtType.PHOTOGRAPHIE);

		userServicesLocal.addUser(partcipant2);
		
		User partcipant3 = new User();
		partcipant3.setFirstName("Ali");
		partcipant3.setLastName("Khaled");
		partcipant3.setRole(RoleUser.JURRY);
		partcipant3.setLogin("amir3");
		partcipant3.setPassword("123456789");
		partcipant3.setArtType(ArtType.PINTURE);

		userServicesLocal.addUser(partcipant3);
		
		User partcipant4 = new User();
		partcipant4.setFirstName("Ali");
		partcipant4.setLastName("Khaled");
		partcipant4.setRole(RoleUser.JURRY);
		partcipant4.setArtType(ArtType.PHOTOGRAPHIE);

		userServicesLocal.addUser(partcipant4);
		
		User partcipant5 = new User();
		partcipant5.setFirstName("Ali");
		partcipant5.setLastName("Khaled");
		partcipant5.setRole(RoleUser.ARTIST);

		partcipant5.setArtType(ArtType.PHOTOGRAPHIE);

		userServicesLocal.addUser(partcipant5);
		
		User partcipant6 = new User();
		partcipant6.setFirstName("Ali");
		partcipant6.setLastName("Khaled");
		partcipant6.setRole(RoleUser.ARTIST);

		userServicesLocal.addUser(partcipant6);
		
		//Add Challenge
		Challenge ch = new Challenge();
		ch.setTitle("First");
		ch.setDescription("Description1");
		User galleryOwner1 = userServicesLocal.findUserById(1);
		ch.setGalleryOwner(galleryOwner1);
		ch.setEndDate(Calendar.getInstance().getTime());

		ch.setType(ArtType.PHOTOGRAPHIE);
		
		challengeServicesLocal.addChallenge(ch);
		
		Challenge ch2 = new Challenge();
		ch2.setTitle("Second");
		ch2.setDescription("Description2");
		User galleryOwner = userServicesLocal.findUserById(1);
		ch2.setGalleryOwner(galleryOwner);
		ch2.setType(ArtType.PINTURE);
		ch2.setEndDate(Calendar.getInstance().getTime());
		challengeServicesLocal.addChallenge(ch2);
		
		
		Challenge ch3 = new Challenge();
		ch3.setTitle("Second");
		ch3.setDescription("Description2");
		User galleryOwner2 = userServicesLocal.findUserById(4);
		ch3.setGalleryOwner(galleryOwner2);
		ch3.setType(ArtType.PINTURE);
		ch3.setEndDate(Calendar.getInstance().getTime());

		challengeServicesLocal.addChallenge(ch3);
		
		//Add Participation
		
		ch = challengeServicesLocal.findChallengeById(1);
		partcipant = userServicesLocal.findUserById(5);
		Participation p = new Participation();
		ParticipationId pId = new ParticipationId();
		pId.setChallenge(ch);
		pId.setParticipant(partcipant);
		p.setPk(pId);
		p.setInscripDate(Calendar.getInstance().getTime());
		if(participationServicesLocal.addParticipation(p))
		{
			System.out.println("SAHA M3alem");
		}else
		{
			System.out.println("l3asba t'ajouti");
		};
		
		ch2 = challengeServicesLocal.findChallengeById(2);
		partcipant2 = userServicesLocal.findUserById(6);
		Participation p2 = new Participation();
		ParticipationId pId2 = new ParticipationId();
		pId2.setChallenge(ch2);
		pId2.setParticipant(partcipant2);
		p2.setPk(pId2);
		if(participationServicesLocal.addParticipation(p2))
		{
			System.out.println("SAHA M3alem");
		}else
		{
			System.out.println("l3asba t'ajouti");
		};
		
		
		Challenge ch8 = challengeServicesLocal.findChallengeById(2);
		User partcipant8 = userServicesLocal.findUserById(3);
		Participation p3 = new Participation();
		ParticipationId pid3 =new ParticipationId();
		pid3.setChallenge(ch8);
		pid3.setParticipant(partcipant8);
		p3.setPk(pid3);
		if(participationServicesLocal.addParticipation(p3))
		{
			System.out.println("SAHA M3alem");
		}else
		{
			System.out.println("l3asba t'ajouti");
		};
		
		
		
		
		//Add PhotoChallenge 
		 //Add Photo
	      PhotoChallenge chp = new PhotoChallenge();
	      chp.setImageName("sssss");
	      chp.setParticipation(participationServicesLocal.findParticipationById(6,2).get(0));
	      challengePhotoServicesLocal.addPhotoChallenge(chp);
		
	      PhotoChallenge chp2 = new PhotoChallenge();
	      chp2.setImageName("photo 2 ");
	      chp2.setParticipation(participationServicesLocal.findParticipationById(6,2).get(0));
	      challengePhotoServicesLocal.addPhotoChallenge(chp2);
	      
	      PhotoChallenge chp3 = new PhotoChallenge();
	      chp3.setImageName("photo 3");
	      chp3.setParticipation(participationServicesLocal.findParticipationById(6,2).get(0));
	      challengePhotoServicesLocal.addPhotoChallenge(chp3);
	      
	      PhotoChallenge chp4 = new PhotoChallenge();
	      chp4.setImageName("sssss");
	      chp4.setParticipation(participationServicesLocal.findParticipationById(3,2).get(0));
	      challengePhotoServicesLocal.addPhotoChallenge(chp4);
		
	      PhotoChallenge chp5 = new PhotoChallenge();
	      chp5.setImageName("photo 2 ");
	      chp5.setParticipation(participationServicesLocal.findParticipationById(3,2).get(0));
	      challengePhotoServicesLocal.addPhotoChallenge(chp5);
	      
	      PhotoChallenge chp6 = new PhotoChallenge();
	      chp6.setImageName("photo 3");
	      chp6.setParticipation(participationServicesLocal.findParticipationById(3,2).get(0));
	      challengePhotoServicesLocal.addPhotoChallenge(chp6);
	      //Add Note photo 
	      NotePhoto n1=new NotePhoto();
	      n1.setNote(10);
	      NotePhotoId npk1 =new NotePhotoId();
	      npk1.setJurry(partcipant2);
	      npk1.setPhoto(chp);
	      n1.setPk(npk1);
	      notePhotoServicesLocal.addNotePhoto(n1);
	      
	      NotePhoto n2=new NotePhoto();
	      n2.setNote(10);
	      NotePhotoId npk2 =new NotePhotoId();
	      npk2.setJurry(partcipant);
	      npk2.setPhoto(chp);
	      n2.setPk(npk2);
	      notePhotoServicesLocal.addNotePhoto(n2);
	}
	*/
}
