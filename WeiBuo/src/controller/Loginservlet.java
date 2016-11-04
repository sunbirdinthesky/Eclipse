package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sqlOperation.SqlOperation;

/**
 * Servlet implementation class Loginservlet
 */
@WebServlet("/Loginservlet")
public class Loginservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("username");
		String passwd = request.getParameter("passwd");

		if (id.equals("") || passwd.equals("")) {// 验证是否为空字符（忘了填信息了）
			request.setAttribute("message", "please enter all messages");
			RequestDispatcher rd = request.getRequestDispatcher("/view/index.jsp");
			rd.forward(request, response);
			return;
		}
		
		SqlOperation sql = new SqlOperation();
		try {
			sql.SqlInit();
			if (sql.isConnected()) {
				sql.SqlQuery("select * from users where username = \""
						+ id + "\"");
				if (!sql.rSet.next()) {
					request.setAttribute("message", "用户不存在");
					RequestDispatcher rd = request.getRequestDispatcher("/view/index.jsp");
					rd.forward(request, response);
					return;
				}
				
				sql.SqlQuery("select passwd from users where username = \""
						+ id + "\"");
				if (sql.rSet.next() 
						&& sql.rSet.getString("passwd").equals(passwd)) {
					
					request.setAttribute("username", id);
					RequestDispatcher rd = request.getRequestDispatcher("/view/member.jsp");
					rd.forward(request, response);
					return;
				}
				else {
					request.setAttribute("message", "密码错误");
					RequestDispatcher rd = request.getRequestDispatcher("/view/index.jsp");
					rd.forward(request, response);
					return;
				}
			} else {
				request.setAttribute("message", "连接失败");
				RequestDispatcher rd = request.getRequestDispatcher("/view/index.jsp");
				rd.forward(request, response);
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}	
	}
}
