package controller;

import java.io.IOException;
import java.io.Writer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Blog;
import model.User;

/**
 * Servlet implementation class Messageservlet
 */
@WebServlet("/Messageservlet")
public class Messageservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Writer jsp = response.getWriter();
//		jsp.append(request.getAttribute("username").toString());
		User user = new User(request.getAttribute("username").toString());
		Vector<Blog> blogs = user.getBlogs();
		for (Blog tmp:blogs) {
			writeBlog(tmp, jsp);
		}
		
	}
	
	void writeBlog (Blog blog, Writer jsp) throws IOException {
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
		jsp.append("<input type=\"button\" value=\"É¾³ý\" onclick=\"jump(" + blog.id + ")\"/> ");
		jsp.append("</tr>");
		
		jsp.append("</table>");
	}

}
