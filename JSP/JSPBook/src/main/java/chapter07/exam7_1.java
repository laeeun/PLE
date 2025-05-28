package chapter07;
	
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
	
@WebServlet("/exam7_1")
public class exam7_1 extends HttpServlet{
	//폼을 제공
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	System.out.println("exam7_1 doget 입장");
	
	RequestDispatcher ds = req.getRequestDispatcher("chapter07/fileupload01.jsp");
	ds.forward(req, resp);
	
	}
	//폼을 처리
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	System.out.println("exam7_1 dopost 입장");
	
	//Step 2. 전처리
	// String id = req.getParameter("id);
	//멀티파트리퀘스트는 파라미터 5개를 줘야된다.(리퀘스트,저장폴더,사이즈,인코딩,이름관련객체)
	// 파일 저장 경로 설정 - 실제 서버 경로를 구함 (resources/images 폴더 안)
	String save = req.getServletContext().getRealPath("resources/images");
	System.out.println(save);
	// 파일 최대 크기 설정 - 5MB로 제한 (1024byte * 1024 = 1MB)
	int max = 1024*1024*5;// 5MB
	// 문자 인코딩 설정 - 한글 처리를 위해 UTF-8
	String encoding= "utf-8";
	// 이름이 겹칠 경우 자동으로 이름을 바꿔주는 객체
	DefaultFileRenamePolicy rename = new DefaultFileRenamePolicy();
	// MultipartRequest를 생성할 때 5개의 인자(요소)를 줘야 함:
	// (1) 요청객체, (2) 저장 경로, (3) 파일 최대크기, (4) 인코딩, (5) 이름 겹침 방지 정책
	// 파일 업로드 요청을 처리하는 MultipartRequest 객체 생성
	MultipartRequest multi = new MultipartRequest(req,save,max,encoding,rename);
	
	
	// case 1: form에서 넘어온 텍스트 데이터를 직접 꺼내는 방식
	// text 가져오기 case 1
	String name = multi.getParameter("name1");
	String subject = multi.getParameter("subject1");
	System.out.println(name);
	System.out.println(subject);
	
	//  case 2: 파라미터를 자동으로 다 꺼내서 HashMap에 넣는 방식
	// test 가져오기 case 2
	Enumeration keys = multi.getParameterNames();// 모든 파라미터 이름들 가져오기
	HashMap<String,String> hm = new HashMap<String,String>();// (이름, 값)을 저장할 맵 생성
	while(keys.hasMoreElements()) {
	String key = (String) keys.nextElement();
	String value = multi.getParameter(key);
	hm.put(key, value);
	System.out.println("키값은 : "+key+", value 값은 :"+value);
	}
	
	
	// 업로드된 파일의 원래 이름과 서버에 저장된 이름을 가져옴
	// 이미지 이름 가져오기
	String imageName1_1 = multi.getOriginalFileName("filename1_1");//저장전 파일이름
	String imageName1_2 = multi.getFilesystemName("filename1_2");//저장후 파일이름
	String imageName2_1 = multi.getOriginalFileName("filename2_1");//저장전 파일이름
	String imageName2_2 = multi.getFilesystemName("filename2_2");//저장후 파일이름

	String imageName3_1 = multi.getOriginalFileName("filename2_1");//저장전 파일이름
	String imageName3_2 = multi.getFilesystemName("filename2_2");//저장후 파일이름

	String imageName4_1 = multi.getOriginalFileName("filename2_1");//저장전 파일이름
	String imageName4_2 = multi.getFilesystemName("filename2_2");//저장후 파일이름
	
	System.out.println(imageName1_2);
	System.out.println(imageName2_2);
	System.out.println(imageName3_2);
	System.out.println(imageName4_2);
	
	//Step 3. 모델이동
	
	
	//Step 4. 뷰이동
	
	
	req.setAttribute("name1", name);// JSP에서 ${name1}로 출력 가능
	req.setAttribute("subject1", subject);
	req.setAttribute("image1_2", imageName1_2);
	req.setAttribute("image2_2", imageName2_2);
	req.setAttribute("image3_2", imageName3_2);
	req.setAttribute("image4_2", imageName4_2);
	
	// JSP 페이지로 이동
	RequestDispatcher ds = req.getRequestDispatcher("chapter07/fileupload01_process.jsp");
	ds.forward(req, resp);// forward 방식: 같은 요청 내에서 화면 전환 (URL 안 바뀜)
	
	
	}
	
}