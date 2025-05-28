package ch07;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/fileupload01")
public class fileupload01 extends HttpServlet
{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		System.out.println("fileupload01 doget 접속 완료");
		RequestDispatcher ds = req.getRequestDispatcher("ch07/fileupload01.jsp");
		ds.forward(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		System.out.println("fileupload01 dopost 접속 완료");
		// 전처리
		// 파라미터 5개 (리퀘스트, 저장폴더, 사이즈, 인코딩, 이름관련객체)
		String save = req.getServletContext().getRealPath("resources/images");
		int max = 1024*1024*5;
		String encoding = "utf-8";
		DefaultFileRenamePolicy rename = new DefaultFileRenamePolicy();
		
		MultipartRequest multi = new MultipartRequest(req,save,max,encoding,rename);
		
		// 텍스트 가져오기 case 1
		String name = multi.getParameter("name");
		String subject = multi.getParameter("subject");
		System.out.println(name);
		System.out.println(subject);
		
		// 텍스트 가져오기 case 2
		Enumeration keys = multi.getParameterNames();
		HashMap<String,String> hm = new HashMap<String,String>();
		while(keys.hasMoreElements())
		{
			String key = (String) keys.nextElement();
			String value = multi.getParameter(key);
			hm.put(key, value);
			System.out.println("키 값은 "+key+", value 값은 "+value);
		}
		
		//이미지 이름 가져오기
		String imageName1 = multi.getOriginalFileName("filename"); //저장 전 파일 이름
		String imageName2 = multi.getFilesystemName("filename"); //저장 후 파일 이름
		System.out.println(imageName1);
		System.out.println(imageName2);
		
		// 모델 이동
		// 뷰 이동
		req.setAttribute("name", name);
		req.setAttribute("subject", subject);
		req.setAttribute("filename", imageName1);
		RequestDispatcher ds = req.getRequestDispatcher("ch07/fileupload01_process.jsp");
		ds.forward(req,resp);
	}
	
}
