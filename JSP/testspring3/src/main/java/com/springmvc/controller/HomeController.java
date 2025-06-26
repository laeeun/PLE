package com.springmvc.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.springmvc.domain.BookDTO;

@Controller
public class HomeController {
	Gson g = new Gson();
	
	@RequestMapping("/")
	public String main() {
		return "home";
	}
	@GetMapping(value="/objtojson",produces="application/json; charset=UTF-8")
	@ResponseBody
	public String objtojson() {
		String json = null;
		BookDTO dto = new BookDTO("자바",21000,"에이콘",670);		
		json = g.toJson(dto);
		return json;
	}
	
	@GetMapping("/geo")
	   public String geo(@RequestParam String address,HttpServletRequest request,Model model) {
	      System.out.println(address);
	      //API 요청을 위한 STEP별 코드
	      String imagename=null;
	      try {
	      
	         //Step 1 : 전송할 텍스트에 한글이 있으므로 깨지지않게 인코딩을 실시: 모든 전송에 필수적으로 필요함
	         String addr=URLEncoder.encode(address, "UTF-8");
	      
	         //Step 2 : 네이버로 전송할 URL을 작성한다.
	         String client_id = "4ibvf7a7s4";
	         String client_secret = "pcfd4vM5IVLhIpr1dgfWZyqVDIQZoCD6o3tCkQwx";
	         
	         String reqUrl="https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query="+addr;
	         URL url=new URL(reqUrl);
	         HttpURLConnection con=(HttpURLConnection) url.openConnection();
	         con.setRequestMethod("GET");
	         con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
	         con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);
	         
	         //Step 3 : 네이버에게 요청을 실시한다.
	         InputStreamReader getData = new InputStreamReader(con.getInputStream(), "UTF-8");
	         BufferedReader br = new BufferedReader(getData); //토큰(데이터)의 단위를 라인으로 변경
	         
	         //Step 4 : 문자열 데이터로 변환
	         String line;
	         StringBuffer response = new StringBuffer(); // JSON //한줄씩 읽어서 응답클래스 안에 한줄씩 입력함
	         while((line=br.readLine())!=null) 
	         { 
	            System.out.println("while동작하는중 : "+line);
	            response.append(line);
	         } 
	         
	         
	         //Step 5 : 자바의 문자열을 JSON 기호를 인식하여 필요한 데이터를 추출하기 
	         JSONTokener tokener=new JSONTokener(response.toString()); //문자열을 인식하여 JSON배열, JSON객체를 인식할 수 있도록 도움
	         JSONObject json=new JSONObject(tokener); //JSON 객체로 생성 {...}
	         System.out.println(json.toString());
	         
	         // 이후 원하는 데이터를 가져올수 있음 아래와 같이 3가지 메서드를 사용가능
	         // 1. getJSONObject("키값")
	         // 2. getJSONArray("키값")
	         // 3. get("키값"); :get은 반드시 저장하는 데이터(JSONObject,JSONArray,String)로 캐스팅해야됨
	         
	         String x;
	         JSONArray arr = json.getJSONArray("addresses");
	         JSONObject tmp = arr.getJSONObject(0);
	         x = (String)tmp.get("x");
	         
	         String y= (String)tmp.getString("y");
	         String eng;
	         String postcode;
	         
	         
	      imagename=image(x,y,addr,request);
	         
	      }catch(Exception e) {}   
	      model.addAttribute("img",imagename);
	      return "images";
	   }

	
	 public String image(String x, String y, String address,HttpServletRequest request) {
	      System.out.println(x);
	      System.out.println(y);
	      System.out.println(address);
	      String tempname=null;
	      try 
	      {
	         //Step 1 : 전송할 텍스트에 한글이 있으므로 깨지지않게 인코딩을 실시: 모든 전송에 필수적으로 필요함
	         String URL_STATICMAP = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
	         String pos=URLEncoder.encode(x + " " + y, "UTF-8");
	         String url = URL_STATICMAP;
	         url += "center=" + x + "," + y;
	         url += "&level=16&w=700&h=500";
	         url += "&markers=type:t|size:mid|pos:"+pos+"|label:"+address;
	         
	         //Step 2 : 네이버로 전송할 URL을 작성한다.
	         URL u = new URL(url);
	         HttpURLConnection con = (HttpURLConnection)u.openConnection();
	         con.setRequestMethod("GET");
	         con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "4ibvf7a7s4");
	         con.setRequestProperty("X-NCP-APIGW-API-KEY","pcfd4vM5IVLhIpr1dgfWZyqVDIQZoCD6o3tCkQwx");
	         
	         //Step 3 : 네이버에게 요청을 실시한다.
	         int responseCode = con.getResponseCode();
	         BufferedReader br;
	         InputStream is=null;
	         if(responseCode==200) { // 정상 호출
	             is = con.getInputStream();
	         }
	         else {
	            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();
	            while ((inputLine = br.readLine()) != null) { response.append(inputLine);
	            } br.close();
	            System.out.println(response.toString());
	         }
	         //이미지저장

	         
	         //Step 4 : 문자열 데이터로 변환
	         tempname = Long.valueOf(new Date().getTime()).toString(); //파일의 이름생성
	         String path = request.getRealPath("resources/images");
	         tempname = tempname+".jpg";
	         File f = new File(path+"/"+tempname); // 빈파일을 생성
	         f.createNewFile(); //실제 파일생성 명령어
	         
	         int read = 0;
	         byte[] bytes = new byte[1024];
	         OutputStream outputStream = new FileOutputStream(f);
	         while ((read =is.read(bytes)) != -1) { 
	            outputStream.write(bytes, 0, read);
	         } 
	         is.close();
	         outputStream.close();
	         
	         System.out.println("이미지 저장 완료: " + f.getAbsolutePath());
	      }catch(Exception e) {}
	      
	      return tempname;
	   }
	
	
	@GetMapping("/jsontoobj")
	public String jsontoobj() {
		String json ="{'title': '자바','price': 21000,'company': '에이콘','page': 670}";
		BookDTO dto1 = g.fromJson(json,BookDTO.class);
		System.out.println(dto1);
		return "home";
	}
	@GetMapping(value="/listtojson",produces="application/json; charset=UTF-8")
	@ResponseBody
	public String listtojson() {
		List<BookDTO> lst = new ArrayList<BookDTO>();
		lst.add(new BookDTO("자바1",21000,"에이콘1",570));
		lst.add(new BookDTO("자바2",31000,"에이콘2",670));
		lst.add(new BookDTO("자바3",41000,"에이콘3",770));
		String json = g.toJson(lst);
		System.out.println(json);
		return json;
	}
	@GetMapping("/jsontolist")
	public String jsontolist() {
		String list ="[{'title':'자바1','price':21000,'company':'에이콘1','page':570},{'title':'자바2','price':31000,'company':'에이콘2','page':670},{'title':'자바3','price':11000,'company':'에이콘3','page':370}]";
		List<BookDTO> list1 = g.fromJson(list, new TypeToken<List<BookDTO>>() {}.getType());
		for (BookDTO vo : list1) {
			System.out.println(vo);
		}
		return "home";
	}
	
	
	
	
}
