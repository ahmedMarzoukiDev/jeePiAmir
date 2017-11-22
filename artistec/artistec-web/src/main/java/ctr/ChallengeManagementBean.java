package ctr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;

import com.sun.mail.smtp.SMTPTransport;

import persistence.Challenge;
import persistence.NotePhoto;
import persistence.Participation;
import persistence.ParticipationId;
import persistence.ParticipationState;
import persistence.PhotoChallenge;
import persistence.RoleUser;
import persistence.User;
import services.ChallengePhotoServicesLocal;
import services.ChallengeServicesLocal;
import services.ParticipationServicesLocal;
import services.UserServicesLocal;

@ManagedBean
@SessionScoped
public class ChallengeManagementBean {

	@EJB
	private ChallengeServicesLocal challengeServicesLocal;
	@EJB
	private UserServicesLocal uslLocal;
	@EJB
	private ParticipationServicesLocal parLocal;
	@EJB
	private ChallengePhotoServicesLocal chpLocal;
	// private Map<Challenge,List<User>> map = new
	// HashMap<Challenge,List<User>>();

	private Boolean formVisibility = false;

	private Challenge challenge = new Challenge();
	private User participant;
	private User userConnected = new User();
	private Participation part = new Participation();
	private String selection;
	private Date sysDate;
	private List<PhotoChallenge> lstPhoto = new ArrayList<PhotoChallenge>();
	private List<User> lstJurry;
	private List<String> JurryId;
	String name;
	private int chid; // id challenge for findparticipationby challenge
	private int chidpart;// challengeid for pk to find photos
	private int paridpart;// partid for pk to find photos
	private BarChartModel barModel;
	
	
	public Participation getPart() {
		return part;
	}

	public void setPart(Participation part) {
		this.part = part;
	}

	public User getUserConnected() {
		return userConnected;
	}

	public void setUserConnected(User userConnected) {
		this.userConnected = userConnected;
	}

	public User getParticipant() {
		return participant;
	}

	public void setParticipant(User participant) {
		this.participant = participant;
	}

	public Date getSysDate() {
		return sysDate;
	}

	public void setSysDate(Date sysDate) {
		this.sysDate = sysDate;
	}

	public int getChid() {
		return chid;
	}

	public void setChid(int chid) {
		this.chid = chid;
	}

	public List<PhotoChallenge> getLstPhoto() {
		return lstPhoto;
	}

	public void setLstPhoto(List<PhotoChallenge> lstPhoto) {
		this.lstPhoto = lstPhoto;
	}

	public List<User> getLstJurry() {
		return lstJurry;
	}

	public void setLstJurry(List<User> lstJurry) {
		this.lstJurry = lstJurry;
	}

	public List<String> getJurryId() {
		return JurryId;
	}

	public void setJurryId(List<String> jurryId) {
		JurryId = jurryId;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	private UploadedFile file;

	public Boolean getFormVisibility() {
		return formVisibility;
	}

	public void setFormVisibility(Boolean formVisibility) {
		this.formVisibility = formVisibility;
	}

	public Challenge getChallenge() {
		return challenge;
	}

	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}

	public int getChidpart() {
		return chidpart;
	}

	public void setChidpart(int chidpart) {
		this.chidpart = chidpart;
	}

	public int getParidpart() {
		return paridpart;
	}

	public void setParidpart(int paridpart) {
		this.paridpart = paridpart;
	}

	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}

	
	public BarChartModel getBarModel() {
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}
	
	



	@PostConstruct
	public void init() {

		lstJurry = new ArrayList<>();
		lstJurry = uslLocal.findUsersByRole(RoleUser.JURRY);
		sysDate = Calendar.getInstance().getTime();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		userConnected = (User) session.getAttribute("user");
		createBarModels();
	}

	// ADD CHALLENGE
	public void doAddChallenge() throws IOException {
		User galleryOwner = new User();
		// galleryOwner.setUserId(1);
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		galleryOwner = (User) session.getAttribute("user");
		challenge.setAddDate(Calendar.getInstance().getTime());
		challenge.setGalleryOwner(galleryOwner);
		challenge.setImage(name);

		if (challenge.getEndDate().before(challenge.getStartDate())
				|| challenge.getStartDate().before(challenge.getAddDate())) {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "please check your dates "));

		} else {
			challengeServicesLocal.addChallenge(challenge);

			for (String s : JurryId) {

				int id = Integer.parseInt(s);

				Challenge ch = challengeServicesLocal.findChallengeById(challenge.getChallengeId());
				User j = uslLocal.findUserById(id);

				Participation par = new Participation();
				ParticipationId pid = new ParticipationId();
				pid.setChallenge(ch);
				pid.setParticipant(j);
				par.setPk(pid);
				par.setStatusPart(ParticipationState.JURRY);
				if (parLocal.addParticipation(par)) {
					System.out.println("SAHA M3alem");
				} else {
					System.out.println("Mahh2e t'ajouti");
				}
				;
			}
			FacesContext.getCurrentInstance().getExternalContext().redirect("listChallengeOwner.jsf");
		}
		// formVisibility = false;
	}

	// LIST CHALLENGE
	public List<Challenge> doGetChallenges() {

		List<Challenge> challenges = challengeServicesLocal.findAllChallenge();
		System.out.println(userConnected.getFirstName() + "-------" + userConnected.getLogin());
		List<Participation> lstp = parLocal
				.findParticipationByUser(uslLocal.findUserById(userConnected.getUserId()).getUserId());
		for (Challenge ch : new ArrayList<>(challenges)) {
			for (Participation participation : lstp) {
				if (participation.getChallenge().getChallengeId() == ch.getChallengeId()) {
					challenges.remove(ch);
					System.out.println(ch.getChallengeId() + "-------" + ch.getTitle());

				}
			}
		}

		return challenges;
	}

	//NBR PARTICIPATION PAR CHALLENGE
	public int doGetNbrParticipationParChallenge(int chid){
		List<Participation> participations = new ArrayList<Participation>();
		List<Participation> lst = new ArrayList<Participation>();
		participations = parLocal.findParticipatiantsByChallenge(chid);

		for (Participation p : participations) {
			if (p.getStatusPart().equals(ParticipationState.SIMPLPARTICIPANT)) {
				lst.add(p);

			}
		}		
		return lst.size();
	}
	
	// LIST CHALLENGE PAR OWNER EnCOURS
	public List<Challenge> doGetChallengesPerOwnerEnCours() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User galleryOwner = (User) session.getAttribute("user");
		List<Challenge> challenges = challengeServicesLocal.findChallengePerOwner(galleryOwner);
		List<Challenge> lst = new ArrayList<Challenge>();
		for (Challenge challenge : challenges) {
			if (challenge.getStartDate().before(sysDate) && challenge.getEndDate().after(sysDate)) {
				lst.add(challenge);
			}
		}
		
		return lst;
	}
	
	

	// LIST CHALLENGE PAR OWNER Passer
	public List<Challenge> doGetChallengesPerOwnerPassed() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User galleryOwner = (User) session.getAttribute("user");
		List<Challenge> challenges = challengeServicesLocal.findChallengePerOwner(galleryOwner);
		List<Challenge> lst = new ArrayList<Challenge>();
		for (Challenge challenge : challenges) {
			if (challenge.getEndDate().before(sysDate)) {
				lst.add(challenge);
			}
		}

		return lst;
	}

	// LIST CHALLENGE PAR OWNER COMMING
	public List<Challenge> doGetChallengesPerOwnerComming() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User galleryOwner = (User) session.getAttribute("user");
		List<Challenge> challenges = challengeServicesLocal.findChallengePerOwner(galleryOwner);
		List<Challenge> lst = new ArrayList<Challenge>();
		for (Challenge challenge : challenges) {
			if (challenge.getStartDate().after(sysDate)) {
				lst.add(challenge);
			}
		}

		return lst;
	}

	// UPDATE CHALLENGE
	public String doUpdateChallenge() {
		challengeServicesLocal.updateChallenge(challenge);
		return "listChallengeOwner?faces-redirect=true";
	}

	// DELETE CHALLENGE
	public String doDeleteChallenge() {
		challengeServicesLocal.deleteChallengeById(challenge.getChallengeId());

		return "listChallengeOwner?faces-redirect=true";
	}

	// LIST PARTICIPANT PAR CHALLENGE DE OWNER
	public List<Challenge> doGetParticipantPerChallengePerOwner() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User galleryOwner = (User) session.getAttribute("user");
		List<Challenge> lstc = challengeServicesLocal.findChallengePerOwner(galleryOwner);
		return lstc;
	}

	// LIST PARTICIPATION PAR USER
	public List<Participation> doGetParticipantPerUser() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User user = (User) session.getAttribute("user");

		List<Participation> lstp = parLocal
				.findParticipationByUser(uslLocal.findUserById(user.getUserId()).getUserId());

		return lstp;
	}

	// LIST PARTICIPATION PAR USER Passed
	public List<Participation> doGetParticipantPerUserPassed() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User user = (User) session.getAttribute("user");

		List<Participation> lstp = parLocal
				.findParticipationByUser(uslLocal.findUserById(user.getUserId()).getUserId());

		List<Participation> lst = new ArrayList<Participation>();
		for (Participation p : lstp) {
			if (p.getChallenge().getEndDate().before(sysDate)) {
				lst.add(p);
			}
		}
		return lst;
	}

	// LIST PARTICIPATION PAR USER COMMING
	public List<Participation> doGetParticipantPerUserComming() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User user = (User) session.getAttribute("user");

		List<Participation> lstp = parLocal
				.findParticipationByUser(uslLocal.findUserById(user.getUserId()).getUserId());

		List<Participation> lst = new ArrayList<Participation>();
		for (Participation p : lstp) {
			if (p.getChallenge().getStartDate().after(sysDate)) {
				lst.add(p);
			}
		}
		return lst;
	}

	// LIST PARTICIPATION PAR USER EN COURS
	public List<Participation> doGetParticipantPerJurryEnCours() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User user = (User) session.getAttribute("user");

		List<Participation> lstp = parLocal
				.findParticipationByUser(uslLocal.findUserById(user.getUserId()).getUserId());

		List<Participation> lst = new ArrayList<Participation>();
		for (Participation p : lstp) {
			if (p.getChallenge().getStartDate().before(sysDate) && p.getChallenge().getEndDate().after(sysDate)) {

				lst.add(p);
			}
		}
		return lst;
	}

	// REDIRECTION TO CHALLENGE
	public void doRedirectToChallenge(String idChallenge) throws IOException {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User user = (User) session.getAttribute("user");
		System.out.println(user.getFirstName() + "-----------------*********");

		int id = Integer.parseInt(idChallenge);
		lstPhoto = chpLocal.findPhotoChallengeOfUserParChallenge(user.getUserId(), id);

		challenge = challengeServicesLocal.findChallengeById(id);
		System.out.println(challenge.getChallengeId() + "++++++++++++++++++");
		FacesContext.getCurrentInstance().getExternalContext().redirect("challengeDetails.jsf");

	}

	
	// LIST PARTICPATION PAR CHALLENGE
	public List<Participation> doGetParticipationByChallenges() {

		System.out.println("----------FFFFFFFFFFFFFFFFFF-------*********" + chid);
		List<Participation> participations = new ArrayList<Participation>();
		List<Participation> lst = new ArrayList<Participation>();
		participations = parLocal.findParticipatiantsByChallenge(chid);

		for (Participation p : participations) {
			if (p.getStatusPart().equals(ParticipationState.SIMPLPARTICIPANT)) {
				lst.add(p);

			}
		}
		return lst;
	}

	// LIST PHOTO PAR CHALLENGE PAR USER
	public List<PhotoChallenge> doGetPhotoChallengeByUserChallenge() {

		System.out.println("----------FFFFFFFFFFFFFFFFFF-------*********" + chid);
		List<PhotoChallenge> participations = new ArrayList<PhotoChallenge>();

		participations = chpLocal.findPhotoChallengeOfUserParChallenge(paridpart, chidpart);
		for (PhotoChallenge p : participations) {
			System.out.println(p.getImageName() + "-----------------*********");
		}
		return participations;
	}

	// LIST Challenge Passer
	public List<Challenge> doGetChallengePasser() {

		System.out.println("----------FFFFFFFFFFFFFFFFFF-------*********" + chid);
		List<Challenge> lstch = new ArrayList<Challenge>();

		lstch = challengeServicesLocal.findChallengePasser();
		for (Challenge p : lstch) {
			System.out.println(p.getDescription() + "-----------------*********");
		}
		return lstch;
	}

	// WINNER OF CHALLENGE
	public User doGetWinnerParChallenge(int idCh) throws AddressException, MessagingException {
		User winner = new User();
		Map<User, Float> map1 = new HashMap<User, Float>();
		Challenge c = new Challenge();
		c = challengeServicesLocal.findChallengeById(idCh);
		if (c.getWinner() == null) {
			for (Participation participation : parLocal.findParticipatiantsByChallenge(idCh)) {
				float moyenneC = 0;
				for (PhotoChallenge photo : chpLocal.findPhotoChallengeOfUserParChallenge(
						participation.getPk().getParticipant().getUserId(), idCh)) {
					float moyenneP = 0;
					for (NotePhoto note : photo.getNotePhotos()) {
						moyenneP = moyenneP + note.getNote();
					}
					moyenneP = moyenneP / photo.getNotePhotos().size();
					moyenneC = moyenneC + moyenneP;
				}
				moyenneC = moyenneC / chpLocal
						.findPhotoChallengeOfUserParChallenge(participation.getPk().getParticipant().getUserId(), idCh)
						.size();
				map1.put(participation.getParticipant(), moyenneC);
			}

			Map.Entry<User, Float> maxEntry = null;

			for (Map.Entry<User, Float> entry : map1.entrySet()) {
				if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
					maxEntry = entry;
				}
			}
			winner = maxEntry.getKey();
//			c.setWinner(winner);
//			challengeServicesLocal.updateChallenge(c);
		//	sendMailWinner();
			return winner;
		} else {
			return c.getWinner();
		}
	}
	
	
	//Stat Owner 
	
	
	 private BarChartModel initBarModel() {
	        BarChartModel model = new BarChartModel();
	 
	        ChartSeries chBar = new ChartSeries();
	        chBar.setLabel("Challenges");
	        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			User galleryOwner = (User) session.getAttribute("user");
			List<Challenge> challenges = challengeServicesLocal.findChallengePerOwner(galleryOwner);
			for (Challenge challenge : challenges) {
				chBar.set(challenge.getTitle(), doGetNbrParticipationParChallenge(challenge.getChallengeId()));

			}
	       
	 
	        
	 
	        model.addSeries(chBar);
	         
	        return model;
	    }
	     
	    private void createBarModels() {
	        createBarModel();
	        
	    }
	     
	    private void createBarModel() {
	        
	    	barModel = initBarModel();
	         
	        barModel.setTitle("Bar Chart");
	        barModel.setLegendPosition("ne");
	         
	        Axis xAxis = barModel.getAxis(AxisType.X);
	        xAxis.setLabel("Gender");
	         
	        Axis yAxis = barModel.getAxis(AxisType.Y);
	        yAxis.setLabel("Births");
	        yAxis.setMin(0);
	        yAxis.setMax(10);
	    }
	
	    //Statistique Challenge 
	    
	 
	    public PieChartModel createPieModel2() {
	    	PieChartModel pieModel2;
	        pieModel2 = new PieChartModel();
	        int x=0;
	        int y=0;
	       // List<PhotoChallenge> lstPhoto = 
	        for (PhotoChallenge photo : chpLocal.findAllPhotoChallengeByChallenge(chid)) {
				for (NotePhoto note : photo.getNotePhotos()) {
					if(note.getNote()>10){
						x++;
					}else{
						y++;
					}
				}
				
			}
	        
	        pieModel2.set("Nteo > 10", x);
	        pieModel2.set("Note < 10", y);
	        
	         
	        pieModel2.setTitle("Custom Pie");
	        pieModel2.setLegendPosition("e");
	        pieModel2.setFill(false);
	        pieModel2.setShowDataLabels(true);
	        pieModel2.setDiameter(150);
	        return pieModel2;
	    }
	
	//Send Mail 
	    public void sendMailWinner() throws AddressException, MessagingException{
	    	 Properties props = System.getProperties();
	         props.put("mail.smtps.host","smtp.gmail.com");
	         props.put("mail.smtps.auth","true");
	         Session session = Session.getInstance(props, null);
	         Message msg = new MimeMessage(session);
	         msg.setFrom(new InternetAddress("amir.benmoussa@esprit.tn"));;
	         msg.setRecipients(Message.RecipientType.TO,
	         InternetAddress.parse("jihen.bach@gmail.com", false));
	         msg.setSubject("Heisann "+System.currentTimeMillis());
	         msg.setText("GOOD MORNING, WEEEEEEEEEEEEEEEEEEEEEEEE YOU ARE THE WINNER !!!");
	         msg.setHeader("X-Mailer", "Tov Are's program");
	         msg.setSentDate(new Date());
	         SMTPTransport t =
	             (SMTPTransport)session.getTransport("smtps");
	         t.connect("smtp.gmail.com", "amir.benmoussa@esprit.tn", "amir20180293");
	         t.sendMessage(msg, msg.getAllRecipients());
	         System.out.println("Response: " + t.getLastServerResponse());
	         t.close();
	    }

	// UPLOAD IMAGE
	public void handleFileUpload(FileUploadEvent event) {
		name = event.getFile().getFileName();
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);

		String localPath = "C:" + File.separator + "Users" + File.separator + "hp" + File.separator + "workspace"
				+ File.separator + "artistec" + File.separator + "artistec-web" + File.separator + "src"
				+ File.separator + "main" + File.separator + "webapp" + File.separator + "resources" + File.separator
				+ "imagesChallenge" + File.separator + name;

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String filepath = externalContext.getRealPath("") + File.separator + "resources" + File.separator
				+ "imagesChallenge" + File.separator + name;

		try (

				InputStream input = event.getFile().getInputstream()) {
			OutputStream os = new FileOutputStream(localPath);
			OutputStream osServer = new FileOutputStream(localPath);
			byte[] b = new byte[2048];
			int length;

			while ((length = input.read(b)) != -1) {
				os.write(b, 0, length);
				osServer.write(b, 0, length);
			}

			input.close();
			os.close();
			osServer.close();

		} catch (IOException e) {
			// Show faces message?
		}

	}

}
