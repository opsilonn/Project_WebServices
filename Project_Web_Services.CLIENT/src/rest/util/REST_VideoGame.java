package rest.util;

import java.io.IOException;
import java.util.ArrayList;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import WebServices.util.ServiceAuthorization;
import rest.model.Film;
import rest.model.VideoGame;



public class REST_VideoGame extends REST_Utils
{
	/** Transform a JSON list of {@link VideoGame} into a JAVA ArrayList of {@link VideoGame}
	 * 
	 * @param JSON_string JSON list of {@link VideoGame}
	 * @return a JAVA list of {@link VideoGame}
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static ArrayList<VideoGame> REST_VideoGames_GET()
			throws JsonParseException, JsonMappingException, IOException
	{
		service = REST_GetService();
		
		Response resp = ServiceAuthorization.
				getWebTarget( service.path("rest/v1/videoGames").request() ).
				accept(MediaType.APPLICATION_JSON).
	    		get();

		return resp.readEntity( new GenericType<ArrayList<VideoGame>>() {} );
	}	
	
	
	
	
	/** Adds a new {@link VideoGame} to the Database
	 * 
	 * @param newUser {@link VideoGame} to add to the database
	 */
	public static VideoGame REST_VideoGame_POST(VideoGame newVideoGame)
	{
		// Add it to the database
		service = REST_GetService();
		
		Response resp = ServiceAuthorization.
				getWebTarget( service.path("rest/v1/videoGames").request() ).
				accept(MediaType.APPLICATION_JSON).
	    		post(Entity.entity(newVideoGame, MediaType.APPLICATION_JSON), Response.class);
		
		return resp.readEntity( VideoGame.class );
	}		
	
	
	
	
	/** Modifies a given {@link VideoGame} in the Database
	 * 
	 * @param newUser {@link VideoGame} to modify
	 */
	public static void REST_VideoGame_PUT(VideoGame newVideoGame)
	{
		// Add it to the database
		service = REST_GetService();
		
		Response resp = ServiceAuthorization.
				getWebTarget( service.path("rest/v1/videoGame").request() ).
				accept(MediaType.APPLICATION_JSON).
	    		put(Entity.entity(newVideoGame, MediaType.APPLICATION_JSON), Response.class);
	}
}