package com.co.johan.server.java.rest;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.co.johan.server.java.sql.Reads;
import com.google.gson.Gson;

/**
 * Servlet implementation class Event
 */
@WebServlet("/Event")
public class Event extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Event() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (Reads.validateSession(session)) {
			String idClient=session.getAttribute("client").toString();
			String IdUnit=request.getParameter("IdUnit");
			String dStart=request.getParameter("dStart");
			String dEnd=request.getParameter("dEnd");
			String Speed=request.getParameter("Speed");
			String Event=request.getParameter("Event");
			ArrayList<EventDataType> arrayEvents=new ArrayList<EventDataType>();
			arrayEvents=Reads.GetEvents(idClient,IdUnit,dStart,dEnd,Speed,Event);
			Gson jsonclient=new Gson();
			String json=jsonclient.toJson(arrayEvents);
			response.getWriter().print(json);
		}
		else
			response.getWriter().print("[]");
	}

}
