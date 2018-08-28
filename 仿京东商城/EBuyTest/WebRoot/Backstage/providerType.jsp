<%@page import="com.util.PageUtil"%>
<%@page import="com.entity.ProviderType"%>
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
	String word;
	String name = (String)session.getAttribute("name");
	Object obj = request.getAttribute("word");
	if(obj==null){
		word = "";
	}else{
		word = obj.toString();
	}
	PageUtil<ProviderType> pages = (PageUtil<ProviderType>)request.getAttribute("page");
	ArrayList<ProviderType> clist = pages.getListAll();
%>
  <script type="text/javascript">
 function findWord(){
 	var keword =  document.getElementById("wordName").value;
 	if(keword=="" || keword==null){
   		location.href="ebuypro?op=pType&no=1&word";
   	}else{
   		location.href="ebuypro?op=pType&no=1&word="+keword;
   	}
 	
 }
 </script>
<body>
<!--头部-->
<header class="publicHeader">
    <h1>超市账单管理系统</h1>

    <div class="publicHeaderR">
        <p><span>下午好！</span><span style="color: #fff21b"><%=name%></span> , 欢迎你！</p>
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
            <span>商品类型管理页面</span>
        </div>
        <div class="search">
            <span>商品类型名称：</span>
            <input type="text" id="wordName" value="<%=word%>" placeholder="请输入商品类型"/>
            <input type="button" value="查询" onclick="findWord()"/>
            <a href="ebuypro?op=intopType">添加商品类型</a>
        </div>
        <!--供应商操作表格-->
        <table class="providerTable" cellpadding="0" cellspacing="0">
            <tr class="firstTr">
                <th width="35%">商品类型编码</th>
                <th width="35%">商品类型名称</th>
                <th width="30%">操作</th>
            </tr>
           <%
           	if(clist!=null){
                          for(ProviderType p:clist){
           %>
            <tr>
                <td><%=p.getId()%></td>
                <td><%=p.getName() %></td>
                <td>
                    <a href="ebuypro?op=lookPro&id=<%=p.getId()%>"><img src="<%=path %>/Backstage/img/read.png" alt="查看" title="查看"/></a>
                    <a href="ebuypro?op=upPro&id=<%=p.getId()%>"><img src="<%=path %>/Backstage/img/xiugai.png" alt="修改" title="修改"/></a>
                    <a href="ebuypro?op=delPro&id=<%=p.getId()%>" class="removeProvider"><img src="<%=path %>/Backstage/img/schu.png" alt="删除" title="删除"/></a>
                </td>
            </tr>
           <%
              	}
            }
           %>
        </table>
            <div style="text-align: center;margin-top: 15px;">
            <a href="ebuypro?op=pType&no=<%=pages.getIndex()%>&word=<%=word%>"><button type="button" >首页</button></a>
            <a href="ebuypro?op=pType&no=<%=pages.getUpPage()%>&word=<%=word%>"><button type="button" >上一页</button></a>
            <span>第<%=pages.getPageNo() %>页/总<%=pages.getCountPage() %>页</span>
        	<a href="ebuypro?op=pType&no=<%=pages.getDownPage()%>&word=<%=word%>"><button type="button" >下一页</button></a>
        	<a href="ebuypro?op=pType&no=<%=pages.getLastIndex()%>&word=<%=word%>"><button type="button" >尾页</button></a> 
        	</div> 
    </div>
</section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeProv">
   <div class="removerChid">
       <h2>提示</h2>
       <div class="removeMain" >
           <p>你确定要删除该供应商吗？</p>
           <a href="#" id="yes">确定</a>
           <a href="#" id="no">取消</a>
       </div>
   </div>
</div>


<footer class="footer">
</footer>

<script src="<%=path %>/Backstage/js/jquery.js"></script>
<script src="<%=path %>/Backstage/js/js.js"></script>
<script src="<%=path %>/Backstage/js/time.js"></script>

</body>
</html>