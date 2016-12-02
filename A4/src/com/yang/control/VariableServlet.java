package com.yang.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet( name="VariableServlet",urlPatterns={"/variables"},
 initParams=@WebInitParam(name="variables", value="variables.jsp")
 )
public class VariableServlet extends HttpServlet {

		public VariableServlet() {
		super();
	}

	
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("aaa");
		request.setAttribute("attribute1", "First Value");
		HttpSession session=request.getSession();
		session.setAttribute("attribute2", "Second Value");
		ServletContext application= getServletContext();
		application.setAttribute("attribute3", new java.util.Date());
		request.setAttribute("attribute4", "Request");
		session.setAttribute("attribute4", "Session");
		application.setAttribute("attribute4","ServletContext");
		try {
			RequestDispatcher dispatcher=request.getRequestDispatcher("/variables.jsp");
		    dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

		public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doGet(request, response);
			}

		public void init() throws ServletException {
		// Put your code here
	}

}
