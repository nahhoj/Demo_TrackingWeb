package com.co.johan.server.java.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

import javax.servlet.http.HttpSession;

import com.co.johan.server.java.rest.ClientDataType;
import com.co.johan.server.java.rest.EventDataType;
import com.co.johan.server.java.rest.UnitDataType;

public class Reads {
	private static Connection connection;
	
	private static String server="localhost";
	private static String port="5432";
	private static String db="DemoTracking";
	private static String user="postgres";
	private static String passwd="A12345.";
	
	public static void open() throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		if (connection==null)
			connection = DriverManager.getConnection("jdbc:postgresql://" + server + ":" + port + "/" + db, user, passwd);
	}
	
	public static void close() throws SQLException{
		if (connection!=null && connection.isClosed()==false)
		connection.close();
	}

	public static boolean Insert(String sql) throws SQLException, ClassNotFoundException {
		open();
		Statement statement = connection.createStatement();
		//close();
		return statement.execute(sql);
	}

	public static ResultSet Select(String query) throws SQLException, ClassNotFoundException {
		open();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		//close();
		return rs;
	}
	
	public static boolean Update(String sql) throws SQLException, ClassNotFoundException {
		open();
		Statement statement = connection.createStatement();
		int rs = statement.executeUpdate(sql);
		//close();
		if (rs>0)
			return true;
		else
			return false;
	}

	public static ClientDataType GetClient(String idClient) {
		ClientDataType client = new ClientDataType();
		String query = "SELECT * FROM public.clients where idclient = " + idClient + "";
		System.out.println(query);
		try {
			ResultSet rs = Select(query);
			while (rs.next()) {
				client.setId(rs.getInt("idclient"));
				client.setAddress(rs.getString("address"));
				client.setDocument(rs.getString("document"));
				client.setEmail(rs.getString("email"));
				client.setName(rs.getString("name"));
				client.setPhone(rs.getString("document"));
				client.setStatus(rs.getBoolean("status"));
			}
		} catch (Exception e) {
			System.out.println("GetClient: " +  e.getMessage());
		}
		return client;
	}
	
	public static ArrayList<UnitDataType> GetUnits(String IdClient) {
		ArrayList<UnitDataType> ArrayUnit = new ArrayList<UnitDataType>();
		String query = "SELECT * FROM public.units where idclient = " + IdClient + " order by idunit asc;";
		System.out.println(query);
		try {
			ResultSet rs = Select(query);
			
			while (rs.next()) {
				UnitDataType unit=new UnitDataType();
				unit.setBrand(rs.getString("brand"));
				unit.setIdClient(rs.getInt("idclient"));
				unit.setIdUnit(rs.getString("idunit"));
				unit.setReference(rs.getString("reference"));
				unit.setStatus(rs.getBoolean("status"));
				unit.setIcon(rs.getString("icon"));
				unit.setType(rs.getString("type"));
				ArrayUnit.add(unit);
			}
		} catch (Exception e) {
			System.out.println("GetUnits: " + e.getMessage());
		}
		return ArrayUnit;
	}
	
	public static ArrayList<EventDataType> GetEvents(String idClient,String IdUnit, String dStart, String dEnd, String Speed,String Event) {
 		ArrayList<EventDataType> ArrayEvent = new ArrayList<EventDataType>();
 		String query = "SELECT * FROM public.\"Events\" where idclient = " + idClient; 		
 		if (IdUnit!=null){ 			
 			query+=" and"; 			
 			IdUnit=IdUnit.replace("'", "''");
 			query+=" idunit like '" + IdUnit + "'";
 		}
 		if (dStart!=null && dEnd==null){ 			
 			query+=" and"; 			
 			query+=" datetime > '" + dStart + "'";
 		}
 		if (dStart==null && dEnd!=null){ 			
 			query+=" and"; 			
 			query+=" datetime < '" + dEnd + "'";
 		}
 		if (dStart!=null && dEnd!=null){ 			
 			query+=" and"; 			
 			query+=" (datetime between '" + dStart + "' and '" + dEnd + "')";
 		}
 		if (Speed!=null){
 			query+=" and"; 			
 			query+=" speed >= " + Speed;
 		}
 		if (Event!=null){ 			
 			query+=" and"; 			
 			query+=" event like '" + Event + "'";
 		}
 		query+=" order by idevent desc";
 		System.out.println(query);
		try {
			ResultSet rs = Select(query);
			while (rs.next()) {
				EventDataType event=new EventDataType();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				event.setDateTime(formatter.parse(rs.getString("datetime")));
				event.setEvent(rs.getString("event"));
				event.setIdEvent(rs.getInt("idevent"));
				event.setIdUnit(rs.getString("idunit"));
				event.setIP(rs.getString("ip"));
				event.setLat(rs.getString("latitude"));
				event.setLon(rs.getString("longitude"));
				event.setSpeed(rs.getInt("speed"));
				ArrayEvent.add(event);
			}
		} catch (Exception e) {
			System.out.println("GetEvents: " + e.getMessage());
		}
		return ArrayEvent;
	}
	
	public static ArrayList<EventDataType> GetLastEvent(String IdClient){
		ArrayList<EventDataType> ArrayEvent = new ArrayList<EventDataType>();
		String query="SELECT t2.idevent, t2.idunit, t2.ip, t2.latitude, t2.longitude,t2.speed, t2.datetime, t2.event" + 
				"	FROM public.\"clients\" as t1 inner join public.\"Events\" as t2 on t2.idclient = t1.idclient" + 
				"	where t1.idclient = " + IdClient + " and t2.idevent in (select max(idevent) from public.\"Events\" where idclient = " + IdClient + "group by idunit) order by idevent desc";
		System.out.println(query);
		try {
			ResultSet rs = Select(query);
			while (rs.next()) {
				EventDataType event=new EventDataType();
		        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				event.setDateTime(formatter.parse(rs.getString("datetime")));
				event.setEvent(rs.getString("event"));
				event.setIdEvent(rs.getInt("idevent"));
				event.setIdUnit(rs.getString("idunit"));
				event.setIP(rs.getString("ip"));
				event.setLat(rs.getString("latitude"));
				event.setLon(rs.getString("longitude"));
				event.setSpeed(rs.getInt("speed"));
				ArrayEvent.add(event);
			}
		} catch (Exception e) {
			System.out.println("GetLastEvent: " + e.getMessage());
		}
		return ArrayEvent;
	}
	
	public static String[] getLogin(String login) {
		//login=Base64.getEncoder().encodeToString(login.getBytes());
		String sLogin[]=new String[3];		
		String Hex="";
		login=new String(Base64.getDecoder().decode(login));
		String user[]=login.split(":");
		String query="select idclient FROM public.clients where email like  '" + user[0] + "' and passwd like '" + user[1] + "'" ;
		System.out.println(query);
		try {
			ResultSet rs = Select(query);
			if (rs.next()) {
				int idClient=rs.getInt("idclient");
				Random random = new Random();
		        long val = random.nextLong();
		         Hex=Long.toHexString(val);
		        query="UPDATE public.clients SET session='" + Hex + "' WHERE idclient=" + idClient;
		        sLogin[0]=""+idClient;
		        sLogin[1]=Hex;
		        sLogin[2]=user[0];
		        if (!Update(query)) {
		        	sLogin[0]=null;
		        	sLogin[1]=null;
		        	sLogin[2]=null;
		        }
			}
		} catch (Exception e) {
			System.out.println("getLogin: " + e.getMessage());
		}
		return sLogin;
	}
	
	public static boolean validateSession(HttpSession session){		
		String[] login=new String[3];
		if (session.getAttribute("user")!=null && session.getAttribute("session")!=null && session.getAttribute("client")!=null) {
			login[0]=session.getAttribute("client").toString();
			login[1]=session.getAttribute("session").toString();
			login[2]=session.getAttribute("user").toString();
		}
		else
			return false;
		String query="SELECT count(idClient) FROM public.clients "  + 
					 "where idclient=" + login[0] + " and email like '" + login[2] +"' and session like '" + login[1] + "'";
		System.out.println(query);
		ResultSet rs;
		try {
			rs = Select(query);
			if (rs.next()) {
				int count=rs.getInt("count");
				if (count>0)
					return true;
			}
		} catch (Exception e) {
			System.out.println("validateSession: " + e.getMessage());
		}		
		return false;
	}
}
