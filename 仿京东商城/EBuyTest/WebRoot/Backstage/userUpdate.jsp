<%@page import="com.entity.Customers"%>
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
	Customers c = (Customers)request.getAttribute("customer");
 %>
 <script type="text/javascript">
 	function onlod(){
 		var sex="<%=c.getSex()%>";
 		var vip=<%=c.getVip()%>;
 		if(sex=="男"){
 			document.getElementById("man").selected=true;
 		}else{
 			document.getElementById("woman").selected=true;
 		}
 		var lei =  document.getElementsByName("userlei");
 		if(vip==1){
 			lei[0].checked=true;
 		}else if(vip==2){
 			lei[1].checked=true;
 		}else{
 			lei[2].checked=true;
 		}
 	}
 </script>
<body onload="onlod()">
<!--头部-->
<header class="publicHeader">
    <h1>超市账单管理系统</h1>

    <div class="publicHeaderR">
        <p><span>下午好！</span><span style="color: #fff21b"> <%=name %></span> , 欢迎你！</p>
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
            <span>用户管理页面 >> 用户修改页面</span>
        </div>
        <div class="providerAdd">
            <form action="ebuy?op=updadaCustomer" method="post">
                <!--div的class 为error是验证错误，ok是验证成功-->
                <div>
                    <label for="userName">用户名称：</label>
                    <input type="hidden" name="userid" value="<%=c.getId()%>">
                    <input type="text" name="userName" id="userName" value="<%=c.getName()%>"/>
                    <span >*</span>
                </div>

                <div>
                    <label >用户性别：</label>

                    <select name="sex">
                        <option value="男"  id="man">男</option>
                        <option value="女"  id="woman">女</option>
                    </select>
                </div>
                <div>
                    <label for="userphone">用户电话：</label>
                    <input type="text" name="userphone" id="userphone" value="<%=c.getMovePhone()%>" />
                    <span >*</span>
                </div>
                <div>
                    <label for="userAddress">用户地址：</label>
                    <input type="text" name="userAddress" id="userAddress" value="<%=c.getAddres()%>" />
                </div>
                <div>
                    <label >用户类别：</label>
                    <input type="radio" name="userlei" value="1"/>普通会员
                    <input type="radio" name="userlei" value="2"/>黄金会员
                    <input type="radio" name="userlei" value="3"/>白金会员

                </div>
                <div class="providerAddBtn">
                    <!--<a href="#">保存</a>-->
                    <!--<a href="userList.html">返回</a>-->
                    <input type="submit" value="保存"/>
                    <input type="button" value="返回" onclick="history.go(-1)"/>
                </div>
            </form>
        </div>
    </div>
	<nav aria-label="Page navigation">
		<ul class="pagination">
			<li>
				<a href="#" aria-label="Previous">
					<span aria-hidden="true">&laquo;</span>
				</a>
			</li>
			<li>
				<a href="#">1</a>
			</li>
			<li>
				<a href="#">2</a>
			</li>
			<li>
				<a href="#">3</a>
			</li>
			<li>
				<a href="#">4</a>
			</li>
			<li>
				<a href="#">5</a>
			</li>
			<li>
				<a href="#" aria-label="Next">
					<span aria-hidden="true">&raquo;</span>
				</a>
			</li>
		</ul>
	</nav>
</section>
<footer class="footer">
</footer>
<script src="<%=path %>/Backstage/js/time.js"></script>

</body>
</html>