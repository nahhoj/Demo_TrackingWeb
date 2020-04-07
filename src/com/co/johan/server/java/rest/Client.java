package com.co.johan.server.java.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.co.johan.server.java.sql.Reads;
import com.google.gson.Gson;

/**
 * Servlet implementation class Client
 */
@WebServlet("/Client")
public class Client extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Client() {
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
			ClientDataType client=new ClientDataType();			
			client=Reads.GetClient(idClient);			
			Gson jsonclient=new Gson();
			String json=jsonclient.toJson(client);
			response.setContentType("application/json");
			response.getWriter().print(json);
		}		
		else
			response.getWriter().print("[]");
	}

}
