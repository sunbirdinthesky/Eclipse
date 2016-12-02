package com.yang.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(urlPatterns={"/beandemo"},
 initParams={
		@WebInitParam(name="beandemo",value="beandemo.jsp"),
		@WebInitParam(name="jsp",value="Jsp1.jsp")
})
public class CustomerServlet extends HttpServlet {

	public CustomerServlet() {
		super();
	}
	public void service (HttpServletRequest request, HttpServletResponse response){
		
		AddressBean address=new AddressBean("jinzhou","Science and TechnologyRoad","121000");
		CustomerBean customer=new CustomerBean("Hacker","hacker@163.com","8899123",address);
		request.setAttribute("customer", customer); 
	
		RequestDispatcher view=request.getRequestDispatcher("/beandemo.jsp");	
		try {
			view.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
	}

	public void init() throws ServletException {
	
	}

}
