

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Result
 */
@WebServlet("/Result")
public class Result extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		File log = new File("d:/log");
		FileReader fileReader = new FileReader(log);
		int a = fileReader.read();
		int b = fileReader.read();
		int c = fileReader.read();
		int d = fileReader.read();
		fileReader.close();
		
		int all = a+b+c+d;
		
		request.setAttribute("a", "c");
		request.setAttribute("aNum", a);
		request.setAttribute("aPercent", a/(float)all*100);
		
		request.setAttribute("b", "c++");
		request.setAttribute("bNum", b);
		request.setAttribute("bPercent", b/(float)all*100);
		
		request.setAttribute("c", "jsp");
		request.setAttribute("cNum", c);
		request.setAttribute("cPercent", c/(float)all*100);
		
		request.setAttribute("d", "c#");
		request.setAttribute("dNum", d);
		request.setAttribute("dPercent", d/(float)all*100);
		
		request.setAttribute("allNum", all);
		
		RequestDispatcher rd = request
				.getRequestDispatcher("result.jsp");
		rd.forward(request, response);
	}
}
