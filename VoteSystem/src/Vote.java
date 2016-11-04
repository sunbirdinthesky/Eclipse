
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Vote
 */
@WebServlet("/Vote")
public class Vote extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Cookie[] userCookie = request.getCookies();
		String userIP = request.getRemoteHost();
		boolean isVoted = false;

		if (userCookie != null && userCookie[0].getValue().contentEquals(userIP)) {
			isVoted = true;
		}
		
		String ans = request.getParameter("ques1");
		if (ans == null) {
			request.setAttribute("message", "请选择一个答案");
			RequestDispatcher rd = request
					.getRequestDispatcher("vote.jsp");
			rd.forward(request, response);
			return;
		}

		if (!isVoted) {
			File log = new File("d:/log");
			if (!log.exists()) {
				log.createNewFile();
				FileWriter tmp = new FileWriter(log, false);
				tmp.write(0);
				tmp.write(0);
				tmp.write(0);
				tmp.write(0);
				tmp.flush();
				tmp.close();
			}
			
			FileReader fileReader = new FileReader(log);
			int a = fileReader.read();
			int b = fileReader.read();
			int c = fileReader.read();
			int d = fileReader.read();
			fileReader.close();
			
			switch (Integer.valueOf(ans)) {
			case 1:
				a++;
				break;
			case 2:
				b++;
				break;
			case 3:
				c++;
				break;
			case 4:
				d++;
				break;
			default:
				break;
			}
			
			FileWriter fileWriter = new FileWriter(log, false);
			fileWriter.write(a);
			fileWriter.write(b);
			fileWriter.write(c);
			fileWriter.write(d);
			fileWriter.flush();
			fileWriter.close();
			
			Cookie tmp = new Cookie("ip", userIP);
			tmp.setMaxAge(60*60*24);
			response.addCookie(tmp);
			
			response.sendRedirect("Result");
		} else {
			request.setAttribute("message", "这个IP已经提交过了");
			RequestDispatcher rd = request
					.getRequestDispatcher("vote.jsp");
			rd.forward(request, response);
		}
	}

}
