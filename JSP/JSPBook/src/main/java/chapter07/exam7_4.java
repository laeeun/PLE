package chapter07;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/exam7_4")
public class exam7_4 extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("exam7_4 doget 입장");
		
		RequestDispatcher ds = req.getRequestDispatcher("chapter07/fileupload04.jsp");
		ds.forward(req, resp);
	}
	//폼을 처리
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("exam7_4 post 입장");
		HashMap<String,String> text=new HashMap<String,String>();
		String newName=null;
		
		try {
		//Step .2 전처리
		String path = req.getServletContext().getRealPath("resources/images");
		System.out.println(path);
		DiskFileUpload upload = new DiskFileUpload();
		
		upload.setSizeMax(1000000);
		upload.setSizeThreshold(4096);
		upload.setRepositoryPath(path);
		
		List items=upload.parseRequest(req);
		Iterator params=items.iterator();
		System.out.println("while전");
		while(params.hasNext()) {
			System.out.println("while");
			FileItem item=(FileItem)params.next();
			
			if(item.isFormField()) {
				String name=item.getFieldName();
				String value=item.getString("utf-8");
				System.out.println(name+" = "+value+"<br>");
				text.put(name, value);
			}else {
				String fileFieldName=item.getFieldName();//html의 input name=filename
				System.out.println(fileFieldName);
				String fileName=item.getName();//practice.png
				System.out.println(fileName);
				String contentType=item.getContentType();//image/png
				System.out.println(contentType);
				fileName=fileName.substring(fileName.lastIndexOf("."));//.png
				System.out.println(fileName);
				newName= String.valueOf(System.currentTimeMillis());
				newName=newName+fileName;
				System.out.println(newName); //1748334240076.png
				
				
				File file=new File(path+"/"+newName);
				item.write(file);
				
			}
		}		
		}
		catch(Exception e) {}
		
		req.setAttribute("map", text);
		req.setAttribute("image", newName);
		RequestDispatcher ds = req.getRequestDispatcher("chapter07/fileupload04_process.jsp");
		ds.forward(req, resp);
	}

}
