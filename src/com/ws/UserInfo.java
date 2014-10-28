package com.ws;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.HibernateException;
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
		
		try {
			System.out.println("here");
			Session session=factory.getCurrentSession();
			List<Usuario> list = session.createQuery("from Usuario").list();
			System.out.println(list);
			System.out.println("nombreCompleto    " + list.get(0).nombreCompleto.toString());
			nombre = list.get(0).nombreCompleto.toString();
			
		} catch(Exception e) {
			System.out.println("Error   " + e);
		}
		
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
	
}
