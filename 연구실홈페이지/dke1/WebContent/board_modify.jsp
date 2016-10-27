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
<script language = "javascript">  // 자바 스크립트 시작

function modifyCheck()
  {
   var form = document.modifyform;
   
   if( !form.password.value )
   {
    alert( "비밀번호를 적어주세요" );
    form.password.focus();
    return;
   }
   
  if( !form.title.value )
   {
    alert( "제목을 적어주세요" );
    form.title.focus();
    return;
   }
 
if(!form.userName.value)
{
alert("작성자를 적어주세요");
form.userName.focus();
return;
}

  if( !form.content.value )
   {
    alert( "내용을 적어주세요" );
    form.content.focus();
    return;
   }  
 		form.submit();
  } 
</script>
<%
	request.setCharacterEncoding("utf-8");
	Class.forName("com.mysql.jdbc.Driver");
	String url = "jdbc:mysql://localhost:3306/bbs";
	String id = "USER";
	String pass = "PASSWORD";
	
	String userName = "";
	String password = "";
	String title = "";
	String content = "";
	String filepath = "";
	int idx = Integer.parseInt(request.getParameter("idx"));
	
	try {
		
		Connection conn = DriverManager.getConnection(url,id,pass);
		Statement stmt = conn.createStatement();
		
		String sql = "select title,userName,content,password,filepath from board where num=" + idx;
		ResultSet rs = stmt.executeQuery(sql);

		
		if(rs.next()){
			
			title = rs.getString(1);
			userName = rs.getString(2);
			content = rs.getString(3);
			password = rs.getString(4);
			filepath = rs.getString(5);
		}
		
		rs.close();
		stmt.close();
		conn.close();

	}catch(SQLException e) {
		out.println( e.toString() );
	}
	 
%>
<table>
<form name=modifyform method=post enctype="multipart/form-data" action="modify_ok.jsp?idx=<%=idx%>">
  <tr>
   <td>
    <table width="960" cellpadding="0" cellspacing="0" border="0">
     <tr style="background:url('img/table_mid.gif') repeat-x; text-align:center;">
      <td width="5"><img src="img/table_left.gif" width="5" height="30" /></td>
      <td width="950">수정</td>
      <td width="5"><img src="img/table_right.gif" width="5" height="30" /></td>
     </tr>
    </table>
   <table>
     <tr>
      <td>&nbsp;</td>
      <td align="center">제목</td>
      <td><input name="title" size="120" maxlength="120"  value="<%=title%>"></td>
      <td>&nbsp;</td>
     </tr>
     <tr height="1" bgcolor="#dddddd"><td colspan="4"></td></tr>
    <tr>
      <td>&nbsp;</td>
      <td align="center">작성자</td>
      <td><input name="userName" size="120" maxlength="120" value="<%=userName%>"></td>
      <td>&nbsp;</td>
     </tr>
      <tr height="1" bgcolor="#dddddd"><td colspan="4"></td></tr>
	  <tr>
      <td>&nbsp;</td>
      <td align="center">사진등록</td>
      <td><input type="file"  name="upfile" size="110" maxlength="50" value="<%=filepath%>"></td>
      <td>&nbsp;</td>
     </tr>
	<tr height="1" bgcolor="#dddddd"><td colspan="4"></td></tr>
    <tr>
      <td>&nbsp;</td>
      <td align="center">비밀번호</td>
      <td><input type=password name="password" size="50" maxlength="50"></td>
      <td>&nbsp;</td>
     </tr>
     <tr height="1" bgcolor="#dddddd"><td colspan="4"></td></tr>
     <tr>
      <td>&nbsp;</td>
      <td align="center">내용</td>
      <td><textarea name="content" cols="120" rows="20"><%=content%></textarea></td>
      <td>&nbsp;</td>
     </tr>
     <tr height="1" bgcolor="#dddddd"><td colspan="4"></td></tr>
     <tr height="1" bgcolor="#82B5DF"><td colspan="4"></td></tr>
     <tr align="center">
      <td>&nbsp;</td>
      <td colspan="2"><input type=button value="수정" OnClick="javascript:modifyCheck();">
       <input type=button value="취소" OnClick="javascript:history.back(-1)">
      <td>&nbsp;</td>
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

