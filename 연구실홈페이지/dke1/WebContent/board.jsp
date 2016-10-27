<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Data and Knowledge Engineering Lab</TITLE>
<link rel="shortcut icon" type="image/x-icon" href="http://dkelab.sookmyung.ac.kr/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="common.css">
</head>
<body>

	<div>
		<div id="imgLayer">
			<a href="home.jsp">
			<img src="img/banner.png" border="0"/></a>
		</div>
	</div>
	<header>
	<nav class="nav">
	<ul class="gnb">
		<li><a href="home.jsp">Home</a></li>
		<li><a href="members.jsp">Members</a></li>
		<li><a href="research.jsp">Research</a></li>
		<li><a href="publication.jsp">Publication</a></li>
		<li><a href="projects.jsp">Projects</a></li>
		<li><a href="lab.jsp">Lab</a></li>
		<li><a href="board.jsp">Board</a></li>
	</ul>
	</nav>
	</header>
	<br/>
<%
Connection conn = null;
String url = "jdbc:mysql://localhost:3306/bbs";
String id = "USER";
String pw = "PASSWORD";
Class.forName("com.mysql.jdbc.Driver");
int total = 0;
try{
conn = DriverManager.getConnection(url,id,pw);
Statement stmt = conn.createStatement();
String sqlCount = "select count(*) from board";
ResultSet rs = stmt.executeQuery(sqlCount);
if(rs.next()){
total = rs.getInt(1);
}
rs.close();
out.print("총 게시물:"+total+"개");
String listdb = "select num, title, userName, writeDate, content from board order by num desc";
rs = stmt.executeQuery(listdb);
%>
<table width="960" cellpadding="0" cellspacing="0" border="0">
<tr height="5"><td width="5"></td></tr>
<tr>
<td width="5"></td>
<td width="73">번호</td>
<td width="379">제목</td>
<td width="73">작성자</td>
<td width="164">작성일</td>
<td width="7"></td>
<tr height="1" bgcolor="#82B5DF"><td colspan="6" width="752"></td></tr>
</tr>
<%
if(total==0) {
%>
<tr align="center" bgcolor="#FFFFFF" height="30">
<td colspan="6">등록된 글이 없습니다.</td>
</tr>
<%
} else {
while(rs.next()) {
int idx = rs.getInt(1);
String title = rs.getString(2);
String userName = rs.getString(3);
String writeDate = rs.getString(4);
%>
<tr height="25" align="center">
<td>&nbsp;</td>
<td align="left"><%=idx %></td>
<td align="left"><a href="board_view.jsp?idx=<%=idx%>"><%=title %></td>
<td align="left"><%=userName %></td>
<td align="left"><%=writeDate %></td>
<td>&nbsp;</td>
</tr>
<tr height="1" bgcolor="#D2D2D2"><td colspan="6"></td></tr>
<%
}
}
rs.close();
stmt.close();
conn.close();
} catch(SQLException e) {
out.println( e.toString() );
}
%>
<tr height="1" bgcolor="#82B5DF"><td colspan="6" width="752"></td></tr>
</table>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<tr><td colspan="4" height="5"></td></tr>
<tr align="center">
<td><input type=button value="글쓰기" OnClick="window.location='board_write.jsp'"></td>
</tr>
</table>

	<br/>

<footer>
<p>
@ 2016 SOOKMYUNG DKE Lab. All Rights Reserved.
</p>
<p>
Data and Knowledge Engineering Laboratory<br/>
Division of Computer Science, Sookmyung Women's University<br/>
Cheongpa-ro 47-gil 100, Yongsan-gu, Seoul 04310, Republic of Korea<br/>
Tel: +82-2-2077-7583, Fax: +82-2-710-9296
</p>
</footer>
</body>
</html>
