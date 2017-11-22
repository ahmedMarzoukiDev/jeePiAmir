package ctr;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import persistence.RoleUser;
import persistence.User;
import services.UserServicesLocal;

@ManagedBean
@SessionScoped
public class UserBean implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public UserBean() {
		// TODO Auto-generated constructor stub
	}
	
	@EJB
	private UserServicesLocal uslLocal ;
	
	private User user = new User();
	private boolean isLogged;
	private Boolean loggedInAsOwner = false;
	private Boolean loggedInAsArtist = false;
	private Boolean loggedInAsJurry = false;

	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	public boolean isLogged() {
		return isLogged;
	}
	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}
	public Boolean getLoggedInAsOwner() {
		return loggedInAsOwner;
	}
	public void setLoggedInAsOwner(Boolean loggedInAsOwner) {
		this.loggedInAsOwner = loggedInAsOwner;
	}
	public Boolean getLoggedInAsArtist() {
		return loggedInAsArtist;
	}
	public void setLoggedInAsArtist(Boolean loggedInAsArtist) {
		this.loggedInAsArtist = loggedInAsArtist;
	}
	public Boolean getLoggedInAsJurry() {
		return loggedInAsJurry;
	}
	public void setLoggedInAsJurry(Boolean loggedInAsJurry) {
		this.loggedInAsJurry = loggedInAsJurry;
	}
	
	//LOGOUT 
	public String logout() {
		isLogged = false;
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/User/Login?faces-redirect=true";
	}
	
	//LOGIN 
	public String doLogin() {
		String navigateTo = "";
		User userLoggedIn = uslLocal.authentification(user.getLogin(), user.getPassword());
		if (userLoggedIn != null) {
			isLogged = true;
			user = userLoggedIn;
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("user", userLoggedIn);
			if (userLoggedIn.getRole().equals(RoleUser.ARTIST)) {
				loggedInAsArtist=true;
				navigateTo = "/pages/Challenge/listChallenge?faces-redirect=true";
			} else if (userLoggedIn.getRole().equals(RoleUser.GALLERYOWNER)) {
				loggedInAsOwner=true;
				navigateTo = "/pages/Challenge/Owner/listChallengeOwner?faces-redirect=true";
			} else {
				loggedInAsJurry = true;
				navigateTo = "/pages/Challenge/Jurry/listChallengeJurry?faces-redirect=true";
			
				}
		} else {
			
			navigateTo = "erreur?faces-redirect=true";
		
			System.err.println("not");
		}
		return navigateTo;
	}


	
}
