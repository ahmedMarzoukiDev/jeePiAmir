package gui;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import persistence.ArtType;
import persistence.Challenge;
import persistence.NotePhoto;
import persistence.Participation;
import persistence.ParticipationId;
import persistence.PhotoChallenge;
import persistence.RoleUser;
import persistence.User;
import services.ChallengePhotoServices;
import services.ChallengePhotoServicesRemote;
import services.ChallengeServicesRemote;
import services.NotePhotoServicesRemote;
import services.ParticipationServices;
import services.ParticipationServicesRemote;
import services.UserServicesRemote;


public class FirstFrame {

	public static void main(String[] args) throws NamingException {
		// TODO Auto-generated method stub

		Context context = new InitialContext();
		ChallengeServicesRemote csr = (ChallengeServicesRemote) context
				.lookup("artistec-ear/artistec-ejb/ChallengeServices!services.ChallengeServicesRemote");
		
		UserServicesRemote usr = (UserServicesRemote) context
				.lookup("artistec-ear/artistec-ejb/UserServices!services.UserServicesRemote");
		
		
		ParticipationServicesRemote psr = (ParticipationServicesRemote) context
				.lookup("artistec-ear/artistec-ejb/ParticipationServices!services.ParticipationServicesRemote");
		
		
		ChallengePhotoServicesRemote photochsr = (ChallengePhotoServicesRemote) context
				.lookup("artistec-ear/artistec-ejb/ChallengePhotoServices!services.ChallengePhotoServicesRemote");
		
		
		NotePhotoServicesRemote oteRmt = (NotePhotoServicesRemote) context
				.lookup("artistec-ear/artistec-ejb/NotePhotoServices!services.NotePhotoServicesRemote");
		
		
		//Add User
		User partcipant = new User();
		partcipant.setFirstName("Amir");
		partcipant.setLastName("Ben Moussa");
		usr.addUser(partcipant);
		usr.addUser(partcipant);
		usr.addUser(partcipant);
		partcipant = usr.findUserById(3);

		//Add Challenge
		Challenge ch = new Challenge();
//		ch.setTitle("First");
//		csr.addChallenge(ch);
		
		ch = csr.findChallengeById(2);
		/*	System.out.println("------------------------------------------");
		System.out.println("------------------------------------------");
		System.out.println("------------------------------------------");
		System.out.println("------------------------------------------");
		System.out.println(ch.getChallengeId());*/

		//Add Participation
		/*Participation p = new Participation();
		ParticipationId pId = new ParticipationId();
		pId.setChallenge(ch);
		pId.setParticipant(partcipant);
		p.setPk(pId);
		//p.setChallenge(ch);
		//p.setParticipant(partcipant);
		
		if(psr.addParticipation(p))
		{
			System.out.println("SAHA M3alem");
		}else
		{
			System.out.println("l3asba t'ajouti");
		};
		*/
		
		//find Participation 
		
		System.out.println("-----------******************-------------------**********************");
		System.out.println("-----------******************-------------------**********************");
		System.out.println("-----------******************-------------------**********************");
		//System.out.println(psr.findParticipationById(1, 2).getParticipant().getFirstName());
		System.out.println("-----------******************-------------------**********************");
		System.out.println("-----------******************-------------------**********************");
		System.out.println("-----------******************-------------------**********************");
		System.out.println("-----------******************-------------------**********************");
		System.out.println( psr.findAllParticipation());
		
		for (Participation  p: psr.findAllParticipation()){
			
			 System.out.println(p.getPk().getParticipant().getFirstName());
			 System.out.println(p.getPk().getChallenge().getTitle());
		}
		
		System.out.println("-----------***********-------------------**********************");
		System.out.println("-----------***********---CHALLENGE----PAR-----USER-----------**********************");

		Map<Challenge,List<User>> map = new HashMap<Challenge,List<User>>();
		for (Challenge  p: csr.findChallengePerOwner(partcipant)){
			
			 System.out.println("Oner Name : " +p.getGalleryOwner().getFirstName());
			 System.out.println("Challenge : "+p.getTitle());
			
			List<User> lstu = new ArrayList<>();
				
				 for (Participation  u: p.getParticipation()){
					 System.out.println("Participant Name : " +u.getPk().getParticipant().getFirstName());
				 lstu.add(u.getPk().getParticipant());
				 }
		 map.put(p, lstu);
		 
			System.out.println("-----------***********---CHALLENGE----MAPP-----------**********************");
			 for (Challenge  u: map.keySet()){
				 System.out.println(u.getTitle());
			 }

		}
		
		
		
		System.out.println("-----------***********---CHALLENGE----MAPP---FOR-----WAHADHA---**********************");

		for(Entry<Challenge, List<User>> entry : map.entrySet()) {
			Challenge cle = entry.getKey();
			List<User> valeur = entry.getValue();
		    // traitements
			System.out.println(cle.getTitle());
			for (User user : valeur) {
				System.out.println(user.getFirstName()+" "+user.getLastName());

			}
			
		}
		
		
		
//		if (!csr.findUserPerChallenge(ch).isEmpty()) {
//			 for (Participation  u: csr.findUserPerChallenge(ch)){
//				 System.out.println("Participant Name : " +u.getPk().getParticipant().getFirstName());
//			 }
//		 }else {
//			 System.out.println("Participant Name : " );}
//		
		
		
		
		User owner = new User();
		Map<Challenge,List<User>> mapx = new HashMap<Challenge,List<User>>();
		owner = usr.findUserById(3);
		mapx = csr.findParticipantPerChallengePerOwner(owner);
		
		System.out.println("-----------***********---JSF---**********************");

		for (Entry<Challenge, List<User>> entry : mapx.entrySet()) {
			Challenge cle = entry.getKey();
			List<User> valeur = entry.getValue();
		    // traitements
			System.out.println(cle.getTitle());
			for (User user : valeur) {
				System.out.println(user.getFirstName()+" "+user.getLastName());

			}
		}
		
		
		System.out.println("-----------***********---USER---------PROARTISIT---**********************");
		for (User u : usr.findUsersByRole(RoleUser.JURRY)) {
			System.out.println(u.getFirstName());
			System.out.println(RoleUser.JURRY);
			
		}
		
		System.out.println("-----------***********---USER---------PROARTISIT---***PAR-----------TYPEaRT*******************");

		 for (User u : usr.findUsersByRole(RoleUser.JURRY)) {
				
	        	if (u.getArtType().equals(ArtType.PHOTOGRAPHIE)) {
	    			System.out.println("pppppppp");
	    			System.out.println(u.getFirstName());
				}else if (u.getArtType().equals(ArtType.SCULTURE)) {
	    			System.out.println("ssssssssssss");

					System.out.println(u.getFirstName());

				}else if (u.getArtType().equals(ArtType.PINTURE)) {
	    			System.out.println(u.getArtType());
					System.out.println(u.getFirstName());

				}
	        	
			}
		 
		 for (Participation u : psr.findParticipationByUser(usr.findUserById(6).getUserId())) {
			System.out.println("CHallenge ---------------"+u.getPk().getChallenge().getDescription());
		}
		 
		 DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		// Date date = new Date();

	      // display time and date using toString()
	      System.out.println(Calendar.getInstance().getTime());
	     java.util.Date x = Calendar.getInstance().getTime();
	      System.out.println(x);

	      //Add Photo
	      PhotoChallenge chp = new PhotoChallenge();
	      chp.setImageName("sssss");
	      chp.setParticipation(psr.findParticipationById(3,2).get(0));
	      photochsr.addPhotoChallenge(chp);
	      
	      
	      PhotoChallenge chp2 = photochsr.findPhotoChallengeOfUserParChallenge(3, 2).get(0);
	      System.out.println(chp2.getImageName()+"++++++++++++++++++++++++++");

	      
	      System.out.println("+++++++++++User Auhtentification+++++++++++++++");
	      User tn = new User();
	      
	      tn=usr.authentification("amir", "123456789");
	      System.out.println("+++++++++++User Name :"+tn.getFirstName());


	      
	      System.out.println("+++++++++++List Note Photo 1----------------");
	      for (NotePhoto n : oteRmt.findNotePhotoByPhotoId(2)) {
		      System.out.println("+++++++++++"+n.getNote()+"----------"+n.getPk().getPhoto().getImageName());

		}

	      System.out.println("+++++++++++User Winner ----------------");
//	     System.out.println(csr.findWinner(csr.findChallengeById(2)).getFirstName()+"****WINERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
//	      for (Participation p : psr.findParticipatiantsByChallenge(2)) {
//		      System.out.println(p.getChallenge().getDescription());
//
//		}
//	      
	      
	      
	      
	      
	      
	      User winner = new User();
			Map<User,Float> map1 = new HashMap<User , Float >();
			
			
			for (Participation participation : psr.findParticipatiantsByChallenge(2)) {
				float moyenneC=0;
				for (PhotoChallenge photo : photochsr.findPhotoChallengeOfUserParChallenge(participation.getPk().getParticipant().getUserId(), 2)) {
					float moyenneP =0;
					for (NotePhoto note : photo.getNotePhotos()) {
						moyenneP=moyenneP+note.getNote();
					}
					moyenneP=moyenneP/photo.getNotePhotos().size();
					moyenneC=moyenneC+moyenneP;
				}
				moyenneC=moyenneC/photochsr.findPhotoChallengeOfUserParChallenge(participation.getPk().getParticipant().getUserId(),2).size();
				map1.put(participation.getParticipant(), moyenneC);
			}
			
			Map.Entry<User, Float> maxEntry = null;

			for (Map.Entry<User, Float> entry : map1.entrySet())
			{
			    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
			    {
			        maxEntry = entry;
			    }
			}
			winner = maxEntry.getKey();
	      
	      System.out.println(winner.getFirstName()+"----"+winner.getUserId());
	      
	      
	      
	      
	      
	      
	}

}
