package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

/**
 * Servlet implementation class Publicservlet
 */
@WebServlet("/Publicservlet")
public class Publicservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		request.setCharacterEncoding("UTF-8");
		
		String content;
		String userName;
		try {
			content = request.getParameter("contain");
			userName = session.getAttribute("username").toString();
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("/WeiBuo/view/member.jsp");
			return;
		}
		
		if (content == null || content.isEmpty())
			response.sendRedirect("/WeiBuo/view/member.jsp");
		
		User user = new User(userName);
		user.publishBlog(content);
		response.sendRedirect("/WeiBuo/view/member.jsp");
	}

}
