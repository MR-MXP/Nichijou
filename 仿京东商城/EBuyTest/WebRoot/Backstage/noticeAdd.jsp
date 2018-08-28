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
    <link rel="stylesheet" href="<%=path%>/Backstage/css/public.css"/>
    <link rel="stylesheet" href="<%=path%>/Backstage/css/style.css"/>
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
            <span>公告管理页面 >> 公告添加页面</span>
        </div>
        <div class="providerAdd">
            <form action="notice?op=addNotice" method="post">
                <!--div的class 为error是验证错误，ok是验证成功-->
                <div class="">
                    <label for="noticeTitle">公告标题：</label>
                    <input type="text" name="noticeTitle" id="noticeTitle"/>
                    <span>*请输入公告标题</span>
                    <span id="spro"></span>
                </div>
                	<div class="">
                    <label for="noticeUser">发布者：</label>
                    <input type="text" value="<%=name %>" name="noticeUser" id="noticeUser" readonly="readonly"/>
                    <span id="spro"></span>
                </div>
                 <label for="noticeText">公告内容：</label>
                <div class="" style="margin-top: -12px;">
                <label for="noticeText"></label>
                <textarea name="noticeText" id="noticeText" rows="6" cols="50"></textarea>
                 <span>*请输入公告标题</span>
                </div>

                <div class="providerAddBtn">
                    <!--<a href="#">保存</a>-->
                    <!--<a href="providerList.html">返回</a>-->
                    <input type="submit" value="保存" />
                    <input type="button" value="返回" onclick="history.back(-1)"/>
                </div>
            </form>
        </div>

    </div>
</section>
<footer class="footer">
</footer>
<script src="<%=path%>/Backstage/js/time.js"></script>

</body>
</html>