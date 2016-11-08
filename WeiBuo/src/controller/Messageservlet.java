package controller;

import java.io.IOException;
import java.io.Writer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Blog;
import model.User;

/**
 * Servlet implementation class Messageservlet
 */
@WebServlet("/Messageservlet")
public class Messageservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String userName;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Writer jsp = response.getWriter();
		
		HttpSession session = request.getSession(true);
		try {
			userName = session.getAttribute("username").toString();
		} catch (Exception e) {
			return;
		}
		User user = new User(userName);
		
		Vector<Blog> blogs = user.getBlogs();
		for (Blog tmp:blogs) {
			writeBlog(tmp, jsp, userName);
		}
	}
	
	void writeBlog (Blog blog, Writer jsp, String user) throws IOException {
		jsp.append("<table>");
		
		jsp.append("<tr>");
		jsp.append("<td>");
		jsp.append(blog.publishTime.toString());
		jsp.append("</tr>");
		
		jsp.append("<tr>");
		jsp.append("<td>");
		jsp.append(blog.content);
		jsp.append("</tr>");
		
		jsp.append("<tr>");
		jsp.append("<td>");
		jsp.append("<a href=\"/WeiBuo/Deleteservlet?id=" + blog.id + "\"> ɾ�� </a>");
		jsp.append("</tr>");
		
		jsp.append("</table>");
	}

}
