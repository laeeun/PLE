package ch07;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/fileupload04")
public class fileupload04 extends HttpServlet
{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		System.out.println("fileupload04 doget 접속 완료");
		RequestDispatcher ds = req.getRequestDispatcher("ch07/fileupload04.jsp");
		ds.forward(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		System.out.println("fileupload04 dopost 접속 완료");
		// 전처리
		HashMap<String, String> textMap = new HashMap<String,String>();
		String newName = null;
		
		try
		{
		String path= req.getServletContext().getRealPath("resources/images");
		DiskFileUpload upload = new DiskFileUpload();
		
		upload.setSizeMax(1000000);
		upload.setSizeThreshold(4096);
		upload.setRepositoryPath(path);
		
		List items = upload.parseRequest(req);
		Iterator params = items.iterator();
		
		while(params.hasNext())
		{
			FileItem item = (FileItem) params.next();
			
			if(item.isFormField())
			{
				String name = item.getFieldName();
				String value = item.getString("utf-8");
				System.out.println(name);
				textMap.put(name, value);
			}
			else
			{
				String fileFieldName = item.getFieldName();
				System.out.println(fileFieldName);
				
				String fileName = item.getName();
				System.out.println(fileName);
				
				String contentType = item.getContentType();
				System.out.println(contentType);
				
				fileName = fileName.substring(fileName.lastIndexOf("."));
				System.out.println(fileName);
				
				newName = String.valueOf(System.currentTimeMillis());
				newName = newName+fileName;
				System.out.println(newName);
				
				File file = new File(path+"/"+newName);
				item.write(file);
			}
		}
	}
	catch(Exception e){}
	
	req.setAttribute("map", textMap);
	req.setAttribute("image", newName);
		
	RequestDispatcher ds = req.getRequestDispatcher("ch07/fileupload04_process.jsp");
	ds.forward(req,resp);
	

  }
}
