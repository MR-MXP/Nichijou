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
<script type="text/javascript">
//用户名校验
	function nameverify() {
		username = document.getElementById("userName").value;
		var n = document.getElementById("rname");
		var namereg = /^[a-zA-Z0-9|\u4e00-\u9fa5]{2,10}/;
//		var namereg = /^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]|[a-zA-Z0-9]){2,5}/;
		if(namereg.test(username)) {
			n.innerHTML="<font color='green'>√用户名合法</font>";
		}else{
			n.innerHTML="<font color='red'>×用户名非法</font>"
			document.getElementById("userName").focus();
		}
	}
//密码校验
	function passwordverify(){
		pwd = document.getElementById("userpassword").value;
		var p = document.getElementById("rpwd");
		var pwdreg = /^(\w){6,20}$/;
		if(pwdreg.test(pwd)){
			p.innerHTML="<font color='green'>√密码合法</font>"
		}else{
			p.innerHTML="<font color='red'>×密码非法</font>"
			document.getElementById("userpassword").focus();
		}
	}
	function rpasswordverify(){
		pwd = document.getElementById("userpassword").value;
		rpwd = document.getElementById("userRemi").value;
		var p = document.getElementById("rrpwd");
		if(pwd==rpwd){
			p.innerHTML="<font color='green'></font>"
		}else{
			p.innerHTML="<font color='red'>×两次密码不一致</font>"
		}
	}
function remail(){
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
  		var femail = document.getElementById("email");
  		var email = document.getElementById("email").value;
  		var url = "ebuy?op=remail&email="+email;
  		XMLHttp.onreadystatechange=function(){
  		var mreg = /^[a-zA-Z0-9]+([a-zA-Z0-9][._-]+)*@([a-zA-Z0-9]+[.-])+[a-zA-Z]{2,5}$/;
  		if(mreg.test(email)){
  			if (XMLHttp.readyState==4 && XMLHttp.status==200){
  				document.getElementById("semail").innerHTML=XMLHttp.responseText;
  			}
  		}else{
  			document.getElementById("semail").innerHTML="<font color='red'>×请输入正确的邮箱格式</font>";
  			femail.focus();
  			}
  		}
  		XMLHttp.open("get", url, true);
  		XMLHttp.send(null);
}
function submi(){
	 var userpassword = document.getElementById("userpassword");
	 var userRemi = document.getElementById("userRemi");
	 location.href=""
}
</script>
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
            <span>用户管理页面 >> 用户添加页面</span>
        </div>
        <div class="providerAdd">
            <form action="ebuy?op=addUser" method="post">
                <!--div的class 为error是验证错误，ok是验证成功-->
<!--                 <div class="">
                    <label for="userId">用户编码：</label>
                    <input type="text" name="userId" id="userId"/>
                    <span>*请输入用户编码，且不能重复</span>
                </div> -->
                <div>
                    <label for="userName">用户名称：</label>
                    <input type="text" name="userName" id="userName" onblur="nameverify()"/>
                    <span >*请输入用户名称</span>
                    <span id="rname"></span>
                </div>
                <div>
                    <label for="userpassword">用户密码：</label>
                    <input type="text" name="userpassword" id="userpassword" onblur="passwordverify()"/>
                    <span>*密码长度必须大于6位小于20位</span>
					<span id="rpwd"></span>
                </div>
                <div>
                    <label for="userRemi">确认密码：</label>
                    <input type="text" name="userRemi" id="userRemi" onblur="rpasswordverify()"/>
                    <span>*请输入确认密码</span>
                    <span id="rrpwd"></span>
                </div>
                <div>
                    <label >用户性别：</label>

                    <select name="sex">
                        <option value="男">男</option>
                        <option value="女">女</option>
                    </select>
                    <span></span>
                </div>
                <div>
                    <label for="data">用户邮箱：</label>
                    <input type="text" name="email" id="email" onblur="remail()"/>
                    <span>*请输入用户邮箱，此邮箱为用户唯一登陆账号</span>
                    <span id="semail"></span>
                </div>
                <div>
                    <label for="userphone">用户电话：</label>
                    <input type="text" name="userphone" id="userphone"/>
                    <span >*</span>
                </div>
                <div>
                    <label for="userAddress">用户地址：</label>
                    <input type="text" name="userAddress" id="userAddress"/>
                </div>
                <div>
                    <label >用户类别：</label>
                    <input type="radio" name="userlei" value="1"/>普通会员
                    <input type="radio" name="userlei" value="2"/>黄金会员
                    <input type="radio" name="userlei" value="3"/>铂金会员

                </div>
                <div class="providerAddBtn">
                    <!--<a href="#">保存</a>-->
                    <!--<a href="userList.html">返回</a>-->
                    <input type="submit" value="保存" />
                    <input type="button" value="返回" onclick="javascript:history.go(-1)"/>
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