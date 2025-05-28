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

@WebServlet("/fileupload02")
public class fileupload02 extends HttpServlet
{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		System.out.println("fileupload02 doget 접속 완료");
		RequestDispatcher ds = req.getRequestDispatcher("ch07/fileupload02.jsp");
		ds.forward(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		System.out.println("fileupload02 dopost 접속 완료");
		// 전처리
		// 파라미터 5개 (리퀘스트, 저장폴더, 사이즈, 인코딩, 이름관련객체)
		String save = req.getServletContext().getRealPath("resources/images");
		int max = 1024*1024*5;
		String encoding = "utf-8";
		DefaultFileRenamePolicy rename = new DefaultFileRenamePolicy();
		
		MultipartRequest multi = new MultipartRequest(req,save,max,encoding,rename);
		
		// 텍스트 가져오기 case 1
		String name1 = multi.getParameter("name1");
		String name2 = multi.getParameter("name2");
		String name3 = multi.getParameter("name3");
		String subject1 = multi.getParameter("subject1");
		String subject2 = multi.getParameter("subject2");
		String subject3 = multi.getParameter("subject3");
		System.out.println(name1);
		System.out.println(subject1);
		System.out.println(name2);
		System.out.println(subject2);
		System.out.println(name3);
		System.out.println(subject3);
		
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
		String imageName1 = multi.getFilesystemName("filename1");
		String imageName2 = multi.getFilesystemName("filename2");
		String imageName3 = multi.getFilesystemName("filename3");//저장 후 파일 이름
		System.out.println(imageName1);
		System.out.println(imageName2);
		System.out.println(imageName3);
		
		// 모델 이동
		// 뷰 이동
		req.setAttribute("name1", name1);
		req.setAttribute("name2", name2);
		req.setAttribute("name3", name3);
		req.setAttribute("subject1", subject1);
		req.setAttribute("subject2", subject2);
		req.setAttribute("subject3", subject3);
		req.setAttribute("filename1", imageName1);
		req.setAttribute("fileName2", imageName2);
		req.setAttribute("fileName3", imageName3);
		RequestDispatcher ds = req.getRequestDispatcher("ch07/fileupload02_process.jsp");
		ds.forward(req,resp);
	}
	
}
