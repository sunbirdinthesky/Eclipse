package com.yang.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.TryCatchFinally;

@WebServlet(urlPatterns = { "/book" }, initParams = {
		@WebInitParam(name = "showBook", value = "showBook.jsp"),
		@WebInitParam(name = "error", value = "error.jsp")

}

)
public class BookQueryServlet extends HttpServlet {
	Connection dbConnection;

	public void init() {
		String driver = "org.gjt.mm.mysql.Driver";
		String dburl = "jdbc:mysql://127.0.0.1/bbsuser?useUnicode=true&characterEncoding=UTF-8";
		// jdbc:postgresql://127.0.0.1:5432/bookstore
		String username = "root";
		String password = "a5018335686";
		try {
			Class.forName(driver);
			dbConnection = DriverManager.getConnection(dburl, username,
					password);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.print("aaa");
		String bookid = request.getParameter("bookid");
		try {
			String sql = "select * from books where bookid=?";
			PreparedStatement pstmt = dbConnection.prepareStatement(sql);
			pstmt.setString(1, bookid);
			ResultSet rst = pstmt.executeQuery();
			if (rst.next()) {
				BookBean book = new BookBean();
				book.setBookid(rst.getString("bookid"));
				book.setTitle(rst.getString("title"));
				book.setAuthor(rst.getString("author"));
				book.setPrice(rst.getDouble("price"));
				book.setPublisher(rst.getString("publisher"));
				request.getSession().setAttribute("book", book);
				RequestDispatcher view = request
						.getRequestDispatcher("showBook.jsp");
				view.forward(request, response);
			} else {
				RequestDispatcher view = request
						.getRequestDispatcher("error.jsp");
				view.forward(request, response);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	@Override
	public void destroy() {
		try {
			dbConnection.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

}
