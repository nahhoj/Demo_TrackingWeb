package com.co.johan.server.java.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.co.johan.server.java.sql.Reads;

/**
 * Servlet implementation class Client
 */
@WebServlet("/Session")
public class HandlerSession extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HandlerSession() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String login = request.getHeader("login");
		String logout = request.getHeader("logout");
		if (login!=null) {
			String[] sLogin = Reads.getLogin(login);
			if (sLogin[0]!=null && sLogin[1]!=null && sLogin[2]!=null) {
				HttpSession session = request.getSession(true);
				session.setAttribute("client", sLogin[0]);
				session.setAttribute("session", sLogin[1]);
				session.setAttribute("user", sLogin[2]);
				session.setMaxInactiveInterval(600);
				response.getWriter().print("Success");
			} else
				response.getWriter().print("Error");
		}
		else if (logout.contentEquals("true")) {
			HttpSession session = request.getSession(false);
			session.removeAttribute("client");
			session.removeAttribute("session");
			session.removeAttribute("user");
			response.getWriter().print("Success");
		}
		else
			response.getWriter().print("Error");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.getWriter().print(Reads.validateSession(session));		
	}	
}