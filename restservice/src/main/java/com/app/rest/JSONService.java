package com.app.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.app.Track;
import com.google.gson.Gson;

@Path("/json/track")
public class JSONService {
	
		
	@GET
	@Path("/get")
	//@Produces(MediaType.APPLICATION_JSON)
	public String getTrackInJSON() {
		Gson gson = new Gson();
		List<Track> tracks = new ArrayList<Track>();
		Track track = new Track();
		track.setTitle("t");
		track.setSinger("s");
		tracks.add(track);
		Track track1 = new Track();
		track1.setTitle("t1");
		track1.setSinger("s1");
		tracks.add(track1);
		return gson.toJson(tracks);
	}

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTrackInJSON(Track track) {
		System.out.println(track.toString());
		return Response.status(201).entity(track).build();
		
	}
	
}