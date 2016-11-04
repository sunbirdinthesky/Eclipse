

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("HelloWorld.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		int score = 0;
		String ansQues1 = request.getParameter("ques1");
		String[] ansQues2 = request.getParameterValues("ques2");
		String ansQues3 = request.getParameter("ques3").trim();
		
		if (ansQues1 != null && ansQues1.equals("yes")) 
			score += 20;
		if (ansQues2 != null && ansQues2.length == 2 && ansQues2[0].equals("java")
				&& ansQues2[1].equals("c#"))
			score += 50;
		if (ansQues3 != null && ansQues3.equals("66666"))
			score += 30;
		
		
		out.println("<html><head>"); 
		out.println("<title>总成绩</title>"); 
		out.println("</head><body>"); 
		out.println("你的得分是" + score); 
		out.println("</body></html>"); 
		
	}

}
