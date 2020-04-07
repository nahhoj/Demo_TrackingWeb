package com.co.johan.server.java.test;

import java.util.ArrayList;

import com.co.johan.server.java.rest.EventDataType;
import com.co.johan.server.java.sql.Reads;
import com.google.gson.Gson;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String IdClient="1";
		ArrayList<EventDataType> arrayEvents=new ArrayList<EventDataType>();
		if (IdClient!=null) {
			//arrayEvents=Reads.GetEvents("T0001",null, null,"43",null);
		}
		Gson jsonclient=new Gson();
		String json=jsonclient.toJson(arrayEvents);
		//System.out.println(json);
		String[] sLogin = Reads.getLogin("bmFoaG9qQGhvdG1haWwuY29tOkpvaGFuODA3Mzk1NjUu");
		System.out.println(sLogin[0] + " - " + sLogin[1]);
		
	}

}
