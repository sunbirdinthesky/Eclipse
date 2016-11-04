
import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String id;
	String passwd;
	String loginTime;
	
	public Login() {
		// TODO Auto-generated constructor stub
		
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// // TODO Auto-generated method stub
		// RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
		// request.setAttribute("id", id);
		// request.setAttribute("passwd", passwd);
		// System.out.println(id);
		// rd.forward(request, response);
		HttpSession session = request.getSession(true);
		session.removeAttribute("user");
		session.removeAttribute("loginTime");
		System.out.println("log out");
		response.sendRedirect("login.jsp");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		id = request.getParameter("ID");
		passwd = request.getParameter("PWD");

		

		// ��ȡ�������½��Ϣ

		if (id.equals("") || passwd.equals("")) {// ��֤�Ƿ�Ϊ���ַ�����������Ϣ�ˣ�
			request.setAttribute("message", "please enter all messages");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
			return;
		}
		
		HttpSession session = request.getSession(true);

		try {
			String tmpid = (String) session.getAttribute("user");
			if (tmpid != null) {
				request.setAttribute("message",
						tmpid + " has already login at " + session.getAttribute("loginTime"));
				RequestDispatcher rd = request
						.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("not online");
			
		}

		SqlOperation sql = new SqlOperation();
		try {// sql����
			sql.SqlInit();
			if (!sql.isConnected()) {
				request.setAttribute("message", "server not online");
				RequestDispatcher rd = request
						.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
				return;
			}

			sql.SqlQuery("select passwd from users where id = \"" + id + "\"");
			if (sql.rSet.next()) {// �û�����
				if (sql.rSet.getString("passwd").equals(passwd)) {// ������ȷ
					
					session.setAttribute("user", id);
					session.setAttribute("loginTime", new Date().toString());
					
					RequestDispatcher rd = request
							.getRequestDispatcher("welcomeAndUpload.jsp");
					request.setAttribute("id", id);
					request.setAttribute("loginTime", session.getAttribute("loginTime"));
					rd.forward(request, response);
				} else {// ���벻��ȷ
					request.setAttribute("message", "Wrong password");
					RequestDispatcher rd = request
							.getRequestDispatcher("login.jsp");
					rd.forward(request, response);
					return;
				}

			} else {// �û�������
				request.setAttribute("message", "Wrong user name");
				RequestDispatcher rd = request
						.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}