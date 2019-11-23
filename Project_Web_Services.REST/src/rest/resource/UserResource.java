package rest.resource;


import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import rest.service.UserService;
import rest.model.User;



@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    
    UserService userService;
    



	private URI getUriForSelf(User user) {
		return this.uriInfo.getBaseUriBuilder()
				.path(UserResource.class)
				.path(String.valueOf(user.getId_user()))
				.build();
	}
    
    
	private URI getUriForRates(User user) {		
		return this.uriInfo.getBaseUriBuilder()
				.path(UserResource.class)
				.path(UserResource.class, "getRateResource")
				.resolveTemplate("id_user", user.getId_user())
				.build();
	}
    
    
	private URI getUriForComments(User user) {		
		return this.uriInfo.getBaseUriBuilder()
				.path(UserResource.class)
				.path(UserResource.class, "getCommentResource")
				.resolveTemplate("id_user", user.getId_user())
				.build();
	}


	private void addLinks(User user) {
		user.addLink("self", getUriForSelf(user).toString());
		user.addLink("rates", getUriForRates(user).toString());
		user.addLink("comments", getUriForComments(user).toString());
	}
    
    
    

    @GET
    public Response getUsers()
    		throws SQLException {
		this.userService = new UserService();
		
		List<User> users = userService.getAllUsers();
		
		for(User user : users){
			addLinks(user);
		}
		
		
		return Response
				.status(Status.OK)
				.entity(users)
				.build();
    }


    @Path("/{user_id}")
    @GET
    public Response getUser(@PathParam("user_id") long id) 
    		throws SQLException {
		this.userService = new UserService();
		
		User user = userService.getUser(id);

		addLinks(user);
		
        return Response
        		.status(Status.OK)
				.entity(user)
				.build();
    }


    @GET
    @Path("count")
    public Response getCount() 
    		throws SQLException {
		this.userService = new UserService();
		
		
        return Response
        		.status(Status.OK)
        		.entity(userService.getUserCount())
        		.build();
    }

    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postUser(User user) 
    		throws SQLException {
		this.userService = new UserService();
		
		System.out.println(user);
		
		User new_user = userService.addUser(user.getPseudo(), user.getPassword(), user.getEmail());
		addLinks(new_user);
		URI location = getUriForSelf(new_user);
		
		
		return Response
				.created(location)
				.entity(new_user)
				.build();
    }


    @Path("/{user_id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putUser(@PathParam("user_id")Long id, User user)
    		throws SQLException {
		this.userService = new UserService();
		
		
		return Response
				.status(Status.OK) 
				.entity(userService.updateUser(id, user.getPassword(), user.getNew_password(), user.getEmail()))
				.build();
    }

    
    @Path("/{id_user}")
    @DELETE
    public Response deleteUser(@PathParam("id_user")Long id)
    		throws SQLException {
		this.userService = new UserService();
		
		
		return Response.
				status(Status.OK) 
				.entity(userService.removeUser(id))
				.build();
    }


    
    
    @Path("/{id_user}/rates")
    public RateResource getRateResource() {
    	return new RateResource();
    }
    
    
    @Path("/{id_user}/comments")
    public CommentResource getCommentResource() {
    	return new CommentResource();
    }

}