package com.ws;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

//@Path here defines class level path. Identifies the URI path that 
//a resource class will serve requests for.
@Path("UserInfoService")
public class UserInfo {
	
	private static SessionFactory factory; 
	
	// @GET here defines, this method will method will process HTTP GET
	// requests.
	@GET
	// @Path here defines method level path. Identifies the URI path that a
	// resource class method will serve requests for.
	@Path("/name/{i}")
	// @Produces here defines the media type(s) that the methods
	// of a resource class can produce.
	@Produces(MediaType.TEXT_XML)
	// @PathParam injects the value of URI parameter that defined in @Path
	// expression, into the method.
	public String userName(@PathParam("i") String i) {
		
		String nombre = "";
			
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
		//Session session = factory.openSession();
        
    	Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Usuario");
        List<Usuario> usuarios = query.list();
        
        for(Usuario usuario : usuarios){
        	nombre = usuario.codigo;
        }
        
        //rolling back to save the test data
        tx.commit();
         
        //closing hibernate resources
        session.close();
    	
        System.out.println("testtt " + usuarios);
		
		String name = i;
		return "<User>" + "<Name>" + nombre + "</Name>" + "</User>";
	}

	@GET 
	@Path("/age/{j}") 
	@Produces(MediaType.TEXT_XML)
	public String userAge(@PathParam("j") int j) {
		int age = j;
		return "<User>" + "<Age>" + age + "</Age>" + "</User>";
	}
	
	@GET 
	@Path("/listaUsuarios") 
	@Produces(MediaType.APPLICATION_XML)
	public String listaUsuarios(){
		
		StringBuffer xml = new StringBuffer("<usuarios>");
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        
    	Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Usuario");
        List<Usuario> usuarios = query.list();
        
        for(Usuario usuario : usuarios){
        	xml.append("<usuario>");
        	xml.append("<codigo>"+usuario.codigo+"</codigo>");
        	xml.append("<password>"+usuario.password+"</password>");
        	xml.append("<nombreCompleto>"+usuario.nombreCompleto+"</nombreCompleto>");
        	xml.append("</usuario>");
        }
        
        xml.append("</usuarios>");
        
        tx.commit();
        
        session.close();
        
        return xml.toString();
        
	}
	
	@GET 
	@Path("/login") 
	@Produces(MediaType.APPLICATION_XML + ";charset=utf-8")
	public String login(@QueryParam("codigo") String codigo, @QueryParam("password") String password) {
		
		StringBuffer xml = new StringBuffer("<usuarios>");
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        
    	Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Usuario where codigo = '" + codigo + "' and password = '" + password + "'");
        
        List<Usuario> usuario = query.list();
        
        if(!usuario.isEmpty()){
        	
        	xml.append("<usuario>");
        	xml.append("<codigo>"+usuario.get(0).codigo+"</codigo>");
        	xml.append("<password>"+usuario.get(0).password+"</password>");
        	xml.append("<nombreCompleto>"+usuario.get(0).nombreCompleto+"</nombreCompleto>");
        	xml.append("</usuario>");
        	xml.append("</usuarios>");
        	
        } else {
        	
        	xml.append("<usuario>");
        	xml.append("<codigo>noexiste</codigo>");
        	xml.append("<password>noexiste</password>");
        	xml.append("<nombreCompleto>noexiste</nombreCompleto>");
        	xml.append("</usuario>");
            xml.append("</usuarios>");
            
        }
        
        tx.commit();
        
        session.close();
        
        return xml.toString();
        
	}
	
}
