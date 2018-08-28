<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<%=path %>
<%=basePath %>
<head lang="en">
    <meta charset="UTF-8">
    <title>系统登录 - 超市账单管理系统</title>
    <link rel="stylesheet" href="<%=path%>/Backstage/css/style.css"/>
</head>
<%
	String noAdmin = (String)request.getAttribute("noAdmin");
 %>
 <script type="text/javascript">
 	function onlod(){
 		var s = document.getElementById("stest");
 		var noAdmin = "<%=noAdmin%>";
 		if(noAdmin==null&&noAdmin!=""){
 			s.innerHTML="<font color='red'><%=noAdmin%></font>";
 		}else{
 			s.innerHTML=" ";
 		}
 	}
 </script>
<body class="login_bg" onload="onlod()">
    <section class="loginBox">
        <header class="loginHeader">
            <h1>超市账单管理系统</h1>
        </header>
        <section class="loginCont">
            <form class="loginForm" action="ebuy?op=login" method="post">
            <span id="stest"></span>
                <div class="inputbox">
                    <label for="user">用户名：</label>
                    <input id="user" type="text" name="username" placeholder="请输入用户名" required/>
                </div>
                <div class="inputbox">
                    <label for="mima">密码：</label>
                    <input id="mima" type="password" name="password" placeholder="请输入密码" required/>
                </div>
                <div class="subBtn">
                    <input type="submit" value="登录" />
                    <input type="reset" value="重置"/>
                </div>

            </form>
        </section>
    </section>

</body>
</html>