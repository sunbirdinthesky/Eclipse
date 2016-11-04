package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sqlOperation.SqlOperation;

/**
 * Servlet implementation class Registerservlet
 */
@WebServlet("/Registerservlet")
public class Registerservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("username");
		String passwd = request.getParameter("passwd");
		String confirmPasswd = request.getParameter("confirmpasswd");

		if (id.equals("") || passwd.equals("") || confirmPasswd.equals("")) {// ��֤�Ƿ�Ϊ���ַ�����������Ϣ�ˣ�
			request.setAttribute("message", "please enter all messages");
			RequestDispatcher rd = request
					.getRequestDispatcher("/view/register.jsp");
			rd.forward(request, response);
			return;
		}

		try {
			SqlOperation sql = new SqlOperation();
			sql.SqlInit();
			if (sql.isConnected()) {
				sql.SqlQuery(
						"select * from users where username = \"" + id + "\"");
				if (!sql.rSet.next()) {
					if (passwd.equals(confirmPasswd)) {
						if (sql.SqlUpdate(
								"insert into users values("
										+ "\'" + id + "\'," + "\'" + passwd
										+ "\')")) {
							sql.SqlUpdate(
									"CREATE TABLE" + id + "(`id` INT NOT NULL auto_increment,`publishTime` DATETIME NOT NULL,`content` TEXT(282) NULL,PRIMARY KEY (`id`))");
							request.setAttribute("message", "ע��ɹ�");
							RequestDispatcher rd = request
									.getRequestDispatcher("/view/index.jsp");
							rd.forward(request, response);
						} else {
							request.setAttribute("message", "ע��ʧ�ܣ��������ڲ�����");
							RequestDispatcher rd = request
									.getRequestDispatcher("/view/register.jsp");
							rd.forward(request, response);
						}
						return;
					} else {
						request.setAttribute("message", "�������벻ͬ");
						RequestDispatcher rd = request
								.getRequestDispatcher("/view/register.jsp");
						rd.forward(request, response);
						return;
					}
				} else {
					request.setAttribute("message", "�û����Ѵ���");
					RequestDispatcher rd = request
							.getRequestDispatcher("/view/register.jsp");
					rd.forward(request, response);
					return;
				}
			} else {
				request.setAttribute("message", "����ʧ��");
				RequestDispatcher rd = request
						.getRequestDispatcher("/view/register.jsp");
				rd.forward(request, response);
				return;
			}
		} catch (Exception e) {
			request.setAttribute("message", "����ʧ��");
			RequestDispatcher rd = request
					.getRequestDispatcher("/view/register.jsp");
			rd.forward(request, response);
			return;
		}
	}

}
