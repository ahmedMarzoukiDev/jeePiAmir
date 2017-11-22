package ctr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import persistence.NotePhoto;
import persistence.NotePhotoId;
import persistence.Participation;
import persistence.PhotoChallenge;
import persistence.User;
import services.ChallengePhotoServicesLocal;
import services.NotePhotoServices;
import services.ParticipationServicesLocal;
import services.UserServicesLocal;

@ManagedBean
@SessionScoped
public class PhotoChallengeBean {

	@EJB
	private ChallengePhotoServicesLocal chpLocal;
	@EJB
	private UserServicesLocal uslLocal;
	@EJB
	private ParticipationServicesLocal parLocal;
	@EJB
	private NotePhotoServices noteLocal;
	//
	// @ManagedProperty(value="#{challengeB}")
	// private ChallengeManagementBean challengeManagementBean;

	private PhotoChallenge photo;
	String name;
	private UploadedFile file;
	private int note;
	private int idPhoto;
	private List<PhotoChallenge> lstPhoto = new ArrayList<PhotoChallenge>();
	private NotePhoto notePhoto = new NotePhoto();
int a ;

	public int getA() {
	return a;
}

public void setA(int a) {
	this.a = a;
}

	public PhotoChallengeBean() {
		// TODO Auto-generated constructor stub
	}

	public PhotoChallenge getPhoto() {
		return photo;
	}

	public void setPhoto(PhotoChallenge photo) {
		this.photo = photo;
	}

	public int getIdPhoto() {
		return idPhoto;
	}

	public void setIdPhoto(int idPhoto) {
		this.idPhoto = idPhoto;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}

	public List<PhotoChallenge> getLstPhoto() {
		return lstPhoto;
	}

	public void setLstPhoto(List<PhotoChallenge> lstPhoto) {
		this.lstPhoto = lstPhoto;
	}

	public NotePhoto getNotePhoto() {
		return notePhoto;
	}

	public void setNotePhoto(NotePhoto notePhoto) {
		this.notePhoto = notePhoto;
	}

	// ADD PHOTO TO CHALLENGE
	public void doAddPhotoChallenge(String challengeId) throws IOException {
		int idc = Integer.parseInt(challengeId);
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User u = (User) session.getAttribute("user");
		Participation p = parLocal.findParticipationById(u.getUserId(), idc).get(0);
		PhotoChallenge ph = new PhotoChallenge();
		ph.setParticipation(p);
		ph.setAddDate(Calendar.getInstance().getTime());
		ph.setImageUrl(name);
		chpLocal.addPhotoChallenge(ph);
		lstPhoto = chpLocal.findPhotoChallengeOfUserParChallenge(u.getUserId(), idc);

		// try {
		// challengeManagementBean.doRedirectToChallenge(challengeId);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	// NOTER PHOTO
	public void doAddNotePhoto() {
		PhotoChallenge ph = new PhotoChallenge();
		// System.out.println("-------------"+idPhoto);
		ph = chpLocal.findPhotoChallengeById(idPhoto);
		System.out.println("*********************" + ph.getImageName());
		NotePhotoId phid = new NotePhotoId();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User jerry = (User) session.getAttribute("user");
		phid.setJurry(jerry);
		phid.setPhoto(ph);
		if (noteLocal.findNotePhotoById(idPhoto, jerry.getUserId()).isEmpty()) {
			NotePhoto notep = new NotePhoto();
			notep.setNote(a);
			notep.setPk(phid);

			noteLocal.addNotePhoto(notep);
		} else {
			NotePhoto notep = noteLocal.findNotePhotoById(idPhoto, jerry.getUserId()).get(0);
			notep.setNote(a);

			noteLocal.updateNotePhoto(notep);
		}
		notePhoto= new NotePhoto();
		a=0;
		idPhoto=0;
		System.out.println(notePhoto.getNote()+"---------BA33333333-------------");

	}

	// UPDATE NOTE PHOTO
	public void doUpdateNotePhoto() {
		PhotoChallenge ph = new PhotoChallenge();
		// System.out.println("-------------"+idPhoto);
		ph = chpLocal.findPhotoChallengeById(idPhoto);
		// System.out.println("*********************"+ph.getImageName());
		NotePhotoId phid = new NotePhotoId();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User jerry = (User) session.getAttribute("user");
		phid.setJurry(jerry);
		phid.setPhoto(ph);
		NotePhoto notep = new NotePhoto();
		notep.setNote(note);
		notep.setPk(phid);
		noteLocal.addNotePhoto(notep);

	}

	// MOYENNE DE NOTE PAR PHOTO
	public Double doGetNoteParPhoto() {
		Double notePhoto = 0.0;
		List<NotePhoto> lst = new ArrayList<NotePhoto>();
		for (NotePhoto n : lst) {
			notePhoto = notePhoto + n.getNote();
		}
		notePhoto = notePhoto / lst.size();
		return notePhoto;

	}

	// Delete Photo from Chalenge
	public void doDeletPhotoFromCHallenge(String photoId) {
		int idp = Integer.parseInt(photoId);
		
		chpLocal.deletePhotoChallengeById(idp);
		;

	}

	public NotePhoto doGetNotePhoto(int id ) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User jerry = (User) session.getAttribute("user");

		if (noteLocal.findNotePhotoById(id, jerry.getUserId()).isEmpty()) {		
			a=0;
			return null;
		} else {
			notePhoto = noteLocal.findNotePhotoById(id, jerry.getUserId()).get(0);
			a=notePhoto.getNote();
			idPhoto=id;
			return notePhoto;
		}
	}

	// UPLOAD PHOTO
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
