<@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="java.util.*" %>

<%
String savePath = "/var/www/html/dke1/WebContent/img";
int sizeLimit = 10*1024*1024;

MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "utf-8", new DefaultFileRenamePolicy());
try{
	String title = multi.getParameter("title");
	String userName = multi.getParameter("userName");
	String password = multi.getParameter("password");
	String content = multi.getParameter("content");
	String writeDate = multi.getParameter("writeDate");
	String filepath = multi.getFilesystemName("upfile");

	String url = "jdbc:mysql://localhost:3306/bbs";
	String id = "USER";
	String pw = "PASSWORD";

	Class.forName("com.mysql.jdbc.Driver");

	Connection conn = DriverManager.getConnection(url,id,pw);

	String sql = "insert into board(title,userName,content,password,writeDate,filepath) values(?,?,?,?,?,?)";
	PreparedStatement pstmt = conn.prepareStatement(sql);

	pstmt.setString(1,title);
	pstmt.setString(2,userName);
	pstmt.setString(3,content);
	pstmt.setString(4,password);
	pstmt.setString(5,writeDate);
	pstmt.setString(6,filepath);

	pstmt.execute();
	pstmt.close();

	conn.close();
} catch(Exception e){
}

%>
<script language=javascript>
   self.window.alert("success");
   location.href="board.jsp"; 
</script>
