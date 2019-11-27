package rest.resource;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import rest.model.Comment;
import rest.model.Film;
import rest.model.Multimedia;
import rest.model.Rate;
import rest.service.FilmService;
import rest.service.MultimediaService;

@Path("/films")
@Produces(MediaType.APPLICATION_JSON)
public class FilmResource
{
    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo

	 @Context
	 UriInfo uriInfo;
	 @Context
	 Request request;
	
	 FilmService filmService;
	    



	private URI getUriForSelf(Film film) {
		return this.uriInfo.getBaseUriBuilder()
				.path(FilmResource.class)
				.path(String.valueOf(film.getId_film()))
				.build();
	}
    
    
	private URI getUriForUploader(Film film) {		
		return this.uriInfo.getBaseUriBuilder()
				.path(UserResource.class)
				.path(String.valueOf(film.getID_uploader()))
				.build();
	}


	private void addLinks(Film film) {
		film.addLink("self", getUriForSelf(film).toString());
		film.addLink("uploader", getUriForUploader(film).toString());
	}
	 

	
	
    /** Returns all the {@Film} rows from the database
     * 
     * @return All the {@Film} rows from the database
     * @throws SQLException
     */
	 @GET
	 public Response getFilms(@QueryParam("start")int start, @QueryParam("end")int end, @QueryParam("filtre")String filtre) 
			 throws SQLException{
		 this.filmService = new FilmService();
		 
		 
		 List<Film> films;
		 
		 List<Film> result;
		 
		 System.out.println(start);
		 System.out.println(end);
		 
		 if(filtre!=null){
			films = filmService.searchFilm(filtre); 
		 }
		 else{
			 films = filmService.getFilms();
		 }
		
		 if(start >0 && end>0 && end>=start){
			 result = films.subList(start, end);
		 }else{
			 result = films;
		 }
		 
		 for(Film film : result)
			 addLinks(film);		 
		 
		 return Response
				 .status(Status.OK)
				 .entity(result)
				 .build();
	 }
	 
	 
    /** Returns a given {@Film} from the database
     * 
     * @param id ID of the {@Film} we are returning
     * @return a specific {@Film} row
     * @throws SQLException
     */
	 @Path("/{film_id}")
	 @GET
	 public Response getFilm(@PathParam("film_id") long id) 
			 throws SQLException {
		this.filmService = new FilmService();
		
		Film film = filmService.getFilm(id);
		
		addLinks(film);
		
		 return Response.status(Status.OK)
	        		.entity(film)
	        		.build();
	 }
	 

	 @Path("/{film_id}/rates")
	 @GET
	 public Response getRates(@PathParam("film_id") long id) throws SQLException{
		 this.filmService = new FilmService();
		 
		 List<Rate> rates = filmService.getRates(id);
		 
		 return Response.status(Status.OK).entity(rates).build();
	 }
	 
	 @Path("/{film_id}/comments")
	 @GET
	 public Response getComment(@PathParam("film_id") long id) throws SQLException{
		 this.filmService = new FilmService();
		 
		 List<Comment> comments = filmService.getComments(id);
		 
		 return Response.status(Status.OK).entity(comments).build();
	 }
	 
	 /** Creates a new instance of the table {@Film}
	  * 
	  * @param film Instance to add to the database
	  * @return
	  * @throws SQLException
	  */
	 @POST
	 @Consumes(MediaType.APPLICATION_JSON)
	 public Response PostFilm(Film film)
			 throws SQLException
	 {	 
		 this.filmService = new FilmService();
		 
		 Film new_film = filmService.addFilm(film);
		 
		 addLinks(new_film);
		 
		 return Response
				 .status(Status.OK)
				 .entity(new_film)
				 .build();
	 }
	 
	 
	 @Path("/{film_id}")
	 @DELETE
	 public Response DeleteFilm(@PathParam("film_id") long id) throws SQLException{
		 
		 this.filmService = new FilmService();
		 
		 return Response
				 .status(Status.OK)
				 .entity(filmService.removeFilm(id))
				 .build();
	 }
	 
	 @Path("/{film_id}")
	 @PUT
	 @Consumes(MediaType.APPLICATION_JSON)
	 public Response UpdateFilm(@PathParam("film_id") long id, Film film) throws SQLException{
		 
		 this.filmService = new FilmService();
		 
		 return Response.status(Status.OK)
				 .entity(filmService.UpdateFilm(film.getDescription(), film.getLanguage(), film.getGenre(),film.getStatus(), film.getDirector(), film.getProductor(), film.getMainCast(), id))
				 .build();
	 }
	 
}
