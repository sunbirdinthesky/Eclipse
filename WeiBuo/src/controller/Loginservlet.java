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
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		session.removeAttribute("username");
		RequestDispatcher rd = request.getRequestDispatcher("/view/index.jsp");
		rd.forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("username");
		String passwd = request.getParameter("passwd");

		if (id.equals("") || passwd.equals("")) {// ��֤�Ƿ�Ϊ���ַ�����������Ϣ�ˣ�
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
					request.setAttribute("message", "�û�������");
					RequestDispatcher rd = request.getRequestDispatcher("/view/index.jsp");
					rd.forward(request, response);
					return;
				}
				
				sql.SqlQuery("select passwd from users where username = \""
						+ id + "\"");
				if (sql.rSet.next() 
						&& sql.rSet.getString("passwd").equals(passwd)) {
					
					HttpSession session = request.getSession(true);
					session.setAttribute("username", id);
					response.sendRedirect("/WeiBuo/view/member.jsp");
					return;
				}
				else {
					request.setAttribute("message", "�������");
					RequestDispatcher rd = request.getRequestDispatcher("/view/index.jsp");
					rd.forward(request, response);
					return;
				}
			} else {
				request.setAttribute("message", "����ʧ��");
				RequestDispatcher rd = request.getRequestDispatcher("/view/index.jsp");
				rd.forward(request, response);
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}	
	}
}
