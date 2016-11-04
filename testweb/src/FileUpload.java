

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;


/**
 * Servlet implementation class FileUpload
 */
@WebServlet("/FileUpload")
@MultipartConfig(location = "D:\\", fileSizeThreshold = 1024)
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = this.getServletContext().getRealPath("/");
		String idNumber = request.getParameter("idnumber");
		Part p = request.getPart("filename");
		
		System.out.println(path);
		
		String message = new String();
		if (p.getSize() > 1024*1024) {
			p.delete();
			message = "file is too big, operaction abort";
		}else {
			path = path + "\\member\\" + idNumber;
			File file = new File(path);
			if (!file.exists())
				file.mkdirs();
		}
		
		String h = p.getHeader("content-disposition");
		
		String fName = h.substring(h.lastIndexOf("\\") + 1, h.length() -1);
		p.write(path + "\\" + fName);
		message = "success";
		
		request.setAttribute("message", message);
		RequestDispatcher rd = request.getRequestDispatcher("upload.jsp");
		rd.forward(request, response);
	}

}
