<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
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

function deleteCheck()
  {
   var form = document.deleteform;
   
   if( !form.password.value )
   {
    alert( "비밀번호를 적어주세요" );
    form.password.focus();
    return;
   }
 		form.submit();
  }
 </script>
 <%
 	int idx = Integer.parseInt(request.getParameter("idx"));
 %>
<table>
<form name=deleteform method=post action="delete_ok.jsp?idx=<%=idx%>">
  <tr>
   <td>
    <table width="960" cellpadding="0" cellspacing="0" border="0">
     <tr style="background:url('img/table_mid.gif') repeat-x; text-align:center;">
      <td width="5"><img src="img/table_left.gif" width="5" height="30" /></td>
      <td width="950">삭제</td>
      <td width="5"><img src="img/table_right.gif" width="5" height="30" /></td>
     </tr>
    </table>
   <table width="960">
     <tr>
      <td>&nbsp;</td>
      <td align="center">비밀번호</td>
      <td><input name="password" type="password" size="100" maxlength="100"></td>
      <td>&nbsp;</td>
     </tr>
     <tr height="1" bgcolor="#dddddd"><td colspan="4"></td></tr>
     <tr height="1" bgcolor="#82B5DF"><td colspan="4"></td></tr>
     <tr align="center">
      <td>&nbsp;</td>
      <td colspan="2"><input type=button value="삭제" OnClick="javascript:deleCheck();">
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

