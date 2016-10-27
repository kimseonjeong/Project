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
String url = "jdbc:mysql://localhost:3306/bbs";
String id = "USER";
String pw = "PASSWORD";

Class.forName("com.mysql.jdbc.Driver");

int idx = Integer.parseInt(request.getParameter("idx"));

try{

Connection conn = DriverManager.getConnection(url,id,pw);
Statement stmt = conn.createStatement();

String sql = "select title,userName,writeDate,content,filepath from board where num="+idx;
ResultSet rs = stmt.executeQuery(sql);
if(rs.next()){
String title = rs.getString(1);
String userName = rs.getString(2);
String writeDate = rs.getString(3);
String content = rs.getString(4);
String filepath = rs.getString(5);
%>
<table>
<tr>
   <td>
    <table width="960" cellpadding="0" cellspacing="0" border="0">
     <tr style="background:url('img/table_mid.gif') repeat-x; text-align:center;">
      <td width="5"><img src="img/table_left.gif" width="5" height="30" /></td>
      <td width="950">내 용</td>
      <td width="5"><img src="img/table_right.gif" width="5" height="30" /></td>
     </tr>
    </table>
   <table width="960">
     <tr>
      <td width="0">&nbsp;</td>
      <td align="center" width="100">글번호</td>
      <td width="850"><%=idx%></td>
      <td width="0">&nbsp;</td>
	 <tr height="1" bgcolor="#dddddd"><td colspan="4" width="960"></td></tr>
    <tr>
      <td width="0">&nbsp;</td>
      <td align="center" width="76">제목</td>
      <td width="500"><%=title%></td>
      <td width="0">&nbsp;</td>
     </tr>
     <tr height="1" bgcolor="#dddddd"><td colspan="4" width="407"></td></tr>
    <tr>
      <td width="0">&nbsp;</td>
      <td align="center" width="76">작성자</td>
      <td width="319"><%=userName%></td>
      <td width="0">&nbsp;</td>
     </tr>
      <tr height="1" bgcolor="#dddddd"><td colspan="4" width="407"></td></tr>
    <tr>
      <td width="0">&nbsp;</td>
      <td align="center" width="76">작성일자</td>
      <td width="319"><%=writeDate%></td>
      <td width="0">&nbsp;</td>
     </tr>
     <tr height="1" bgcolor="#dddddd"><td colspan="4" width="407"></td></tr>
                <tr>
      <td width="0">&nbsp;</td>
                   <td width="399" colspan="2" height="200"><%=content%><br/><br/><img src="img/<%=filepath%>"  onError="javascript:this.src='img/noimg.gif'" width="900" />
                </tr>
<%
rs.close();
stmt.close();
conn.close();
}
}catch(SQLException e){
}
%>
     <tr height="1" bgcolor="#dddddd"><td colspan="4" width="407"></td></tr>
     <tr height="1" bgcolor="#82B5DF"><td colspan="4" width="407"></td></tr>
     <tr align="center">
      <td width="0">&nbsp;</td>
      <td colspan="2" width="399"><input type=button value="글쓰기" OnClick="window.location='board_write.jsp'">
	<input type=button value="목록" OnClick="window.location='board.jsp'">
	<input type=button value="수정" OnClick="window.location='board_modify.jsp?idx=<%=idx%>'">
	<input type=button value="삭제" OnClick="window.location='board_delete.jsp?idx=<%=idx%>'">
      <td width="0">&nbsp;</td>
     </tr>
    </table>
   </td>
  </tr>
 </table>

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

