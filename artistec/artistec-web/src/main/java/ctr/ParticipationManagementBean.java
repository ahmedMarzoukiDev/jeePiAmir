package ctr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import persistence.Challenge;
import persistence.Participation;
import persistence.ParticipationId;
import persistence.ParticipationState;
import persistence.RoleUser;
import persistence.User;
import services.ChallengeServicesLocal;
import services.ParticipationServicesLocal;
import services.UserServicesLocal;

@ManagedBean
@ViewScoped
public class ParticipationManagementBean {

	@EJB
	private ParticipationServicesLocal participationServicesLocal;
	@EJB
	private ChallengeServicesLocal chs  ;
	@EJB
	private UserServicesLocal usl  ;
	Participation participation = new Participation();
	private Boolean participate;
	
	public ParticipationManagementBean() {
		// TODO Auto-generated constructor stub
	}
	
	public Participation getParticipation() {
		return participation;
	}
	
	public void setParticipation(Participation participation) {
		this.participation = participation;
	}
	
	public ParticipationServicesLocal getParticipationServicesLocal() {
		return participationServicesLocal;
	}
	
	public void setParticipationServicesLocal(ParticipationServicesLocal participationServicesLocal) {
		this.participationServicesLocal = participationServicesLocal;
	}
	
	
	public Boolean getParticipate() {
		return participate;
	}

	public void setParticipate(Boolean participate) {
		this.participate = participate;
	}
	
	

	 @PostConstruct
	    public void init() {
		
			
			
	 }
	
	

	 //PARTICIPATE TO CHALLENGE 
	public String doAddParticipation(int chid ){

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User participant = (User)session.getAttribute("user");
		
		System.out.println(participant.getFirstName()+"------*****/////*****---------");
		System.out.println(chid+"------*****/////*****---------");
		Challenge ch =chs.findChallengeById(chid);
		ParticipationId pId = new ParticipationId();
		pId.setParticipant(participant);
		pId.setChallenge(ch);
		participation.setInscripDate(Calendar.getInstance().getTime());
		participation.setPk(pId);
		
		
		if(participationServicesLocal.addParticipation(participation)){
			return "listChallenge?faces-redirect=true";}
		else{
		return "listChallenge?faces-redirect=true";}
	}
	
	
	//ANNULATE PARTICIPATION
	public void annulateParticipation(int chid) throws IOException{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User participant = (User)session.getAttribute("user");
		System.out.println(participant.getUserId()+"--------------"+chid);
		participationServicesLocal.deleteParticipationById(participant.getUserId(), chid);
		FacesContext.getCurrentInstance().getExternalContext().redirect("listChallengeArtist.jsf");

	}
	
	
	//IS Participated 
	public boolean isParticipated(int chid){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		User participant = (User)session.getAttribute("user");
		if(participationServicesLocal.findParticipationById(participant.getUserId(), chid).isEmpty()){
			return false;
		}else if (participationServicesLocal.findParticipationById(participant.getUserId(), chid).get(0).getStatusPart().equals(ParticipationState.JURRY)) {
			return false;
		}else{
			return true ;
		}
	}
	
	
	
}
