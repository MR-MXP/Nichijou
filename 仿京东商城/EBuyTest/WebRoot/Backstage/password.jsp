<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>超市账单管理系统</title>
    <link rel="stylesheet" href="<%=path %>/Backstage/css/public.css"/>
    <link rel="stylesheet" href="<%=path %>/Backstage/css/style.css"/>
</head>
<%
	String name = (String)session.getAttribute("name");
 %>
 <script type="text/javascript">
 	function rOldpassword(){
 		var XMLHttp;
  		if(window.XMLHttpRequest){ //非ie
  			XMLHttp = new XMLHttpRequest();
  		}else if(window.ActiveXObejct){
  			try{
  			XMLHttp = new ActiveXObject("Msxml2.XMLHTTP");
  			}catch(e){
  			XMLHttp = new ActiveXObject("Microsoft.XMLHTTP");
  			}
  		}
  		 var pwd = document.getElementById("oldPassword").value;
  		 var rname = document.getElementById("oldPassword").value;
  		 var url="admin?op=rOldPwd&rpwd="+pwd+"&name=<%=name%>";
  		XMLHttp.onreadystatechange=function(){
  			if (XMLHttp.readyState==4 && XMLHttp.status==200){
  				document.getElementById("oldpwd").innerHTML=XMLHttp.responseText;
  			}
  		}
  		XMLHttp.open("get", url, true);
  		XMLHttp.send(null);
 	}
 	function rsubmit(){
 		var rpwd = document.getElementById("rpwd");
 		var nrpwd = document.getElementById("nrpwd");
 		var nspwd = document.getElementById("newPassword");
 		var rnspwd = document.getElementById("reNewPassword");
 		var npwd = document.getElementById("newPassword").value;
 		var rnpwd = document.getElementById("reNewPassword").value;
 		if(npwd==rnpwd&&npwd!=""&&rnpwd!=""){
 			location.href="admin?op=upPwd&pwd="+npwd+"&name=<%=name%>";
 		}else{
 			rpwd.innerHTML="<font color='red'>X两次密码不一致<font/>";
 			nspwd.focus();
 		}
 	}
 	function rnpwd(){
 		var nrpwd = document.getElementById("nrpwd");
 		var nspwd = document.getElementById("newPassword");
 		var npwd = document.getElementById("newPassword").value;
 		if(npwd==""){
 			nrpwd.innerHTML="<font color='red'>X请输入密码<font/>";
 			nspwd.focus();
 		}else{
 			nrpwd.innerHTML="";
 		}
 	}
 </script>
<body>
<!--头部-->
    <header class="publicHeader">
        <h1>超市账单管理系统</h1>
        <div class="publicHeaderR">
            <p><span>下午好！</span><span style="color: #fff21b"><%=name %></span> , 欢迎你！</p>
            <a href="ebuy?op=exit">退出</a>
        </div>
    </header>
<!--时间-->
    <section class="publicTime">
        <span id="time">2015年1月1日 11:11  星期一</span>
        <a href="#">温馨提示：为了能正常浏览，请使用高版本浏览器！（IE10+）</a>
    </section>
<!--主体内容-->
    <section class="publicMian ">
        <div class="left">
            <h2 class="leftH2"><span class="span1"></span>功能列表 <span></span></h2>
        <nav>
            <ul class="list">
                <li ><a href="<%=path %>/Backstage/billList.jsp">账单管理</a></li>
                <li><a href="ebuypro?op=pType&no=1&word">商品类型</a></li>
                <li><a href="pList?op=showProvider&no=1&word">商品管理</a></li>
                <li><a href="ebuy?op=showUser&no=1&word">用户管理</a></li>
                <li><a href="notice?op=showNotice&no=1&word">公告管理</a></li>
                <li><a href="ebuy?op=intoPwd">密码修改</a></li>
                <li><a href="ebuy?op=exit">退出系统</a></li>
            </ul>
        </nav>
        </div>
        <div class="right">
            <div class="location">
                <strong>你现在所在的位置是:</strong>
                <span>密码修改页面</span>
            </div>
            <div class="providerAdd">
                <form action="#">
                    <!--div的class 为error是验证错误，ok是验证成功-->
                    <div class="">
                        <label for="oldPassword">旧密码：</label>
                        <input type="password" name="oldPassword" id="oldPassword" onblur="rOldpassword()"/>
                        <span>*请输入原密码</span>
                         <span id="oldpwd"></span>
                    </div>
                    <div>
                        <label for="newPassword">新密码：</label>
                        <input type="password" name="newPassword" id="newPassword" onblur="rnpwd()"/>
                        <span >*请输入新密码</span>
                        <span id="nrpwd"></span>
                    </div>
                    <div>
                        <label for="reNewPassword">确认新密码：</label>
                        <input type="password" name="reNewPassword" id="reNewPassword"/>
                        <span >*请输入新确认密码，保证和新密码一致</span>
                        <span id="rpwd"></span>
                    </div>
                    <div class="providerAddBtn">
                        <!--<a href="#">保存</a>-->
                        <input type="button" value="保存" onclick="rsubmit()"/>
                    </div>
                </form>
            </div>
        </div>
    </section>
    <footer class="footer">
    </footer>
<script src="<%=path %>/Backstage/js/time.js"></script>

</body>
</html>