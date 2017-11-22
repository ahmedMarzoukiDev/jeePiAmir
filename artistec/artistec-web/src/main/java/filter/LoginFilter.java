package filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ctr.UserBean;
public class LoginFilter implements Filter {
	 
    /**
     * Checks if user is logged in. If not it redirects to the login.xhtml page.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       // Get the loginBean from session attribute
        UserBean userBean = (UserBean)((HttpServletRequest)request).getSession().getAttribute("userBean");
        System.out.println("ttttttttttttttttttttttttttttttt"+userBean+"tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
    	  
        // For the first application request there is no loginBean in the session so user needs to log in
        // For other requests loginBean is present but we need to check if user has logged in successfully
        if (userBean == null || !userBean.isLogged()) {
        	System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
        	
            String contextPath = ((HttpServletRequest)request).getContextPath();
            ((HttpServletResponse)response).sendRedirect(contextPath + "/User/Login.jsf");
           
        }
         
        else{ 
        	System.out.println("        bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        	 chain.doFilter((HttpServletRequest)request, (HttpServletResponse)response);
        
        } 
        

		
	         
	      
		
             
    }
 
    public void init(FilterConfig config) throws ServletException {
        // Nothing to do here!
    }
 
    public void destroy() {
        // Nothing to do here!
    }   
     
}