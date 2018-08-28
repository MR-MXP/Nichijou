<%@page import="com.util.PageUtil"%>
<%@page import="com.entity.Providers"%>
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
	PageUtil<Providers> pages = (PageUtil<Providers>)request.getAttribute("page");
	ArrayList<Providers> clist = pages.getListAll();
%>
  <script type="text/javascript">
 function findWord(){
 	var keword =  document.getElementById("wordName").value;
 	if(keword=="" || keword==null){
   		location.href="pList?op=showProvider&no=1&word";
   	}else{
   		location.href="pList?op=showProvider&no=1&word="+keword;
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
            <span>商品管理页面</span>
        </div>
        <div class="search">
            <span>商品名称：</span>
            <input type="text" id="wordName" placeholder="请输入商品的名称" value="<%=word%>"/>
            <input type="button" value="查询" onclick="findWord()"/>
            <a href="pList?op=addProvider">添加商品</a>
        </div>
        <!--供应商操作表格-->
        <table class="providerTable" cellpadding="0" cellspacing="0">
            <tr class="firstTr">
                <th width="10%">商品编号</th>
                <th width="20%">商品名称</th>
                <th width="10%">商品类型</th>
                <th width="10%">商品价格</th>
                <th width="10%">是否新品</th>
                <th width="10%">是否下架</th>
                <th width="30%">操作</th>
            </tr>
           <%
           	if(clist!=null){
               	for(Providers p:clist){
           %>
            <tr>
                <td><%=p.getGoodsId() %></td>
                <td><%=p.getGoosName() %></td>
                <td><%=p.getTypeName() %></td>
                <td><%=p.getPrice() %></td>
            <%if(p.getIsNew()==0){
            %>
             <td>是</td>
            <%
            }else{
            %>
          	 <td>否</td>
            <%
            }
             %>
            <%if(p.getStatuss()==0){
            %>
               <td>是</td>
            <%
            }else{
            %>
            <td>否</td>
            <%
            }
             %>
                <td>
                	<a href="pList?op=addPhoto&id=<%=p.getGoodsId() %>"><img src="<%=path %>/Backstage/img/photo.png" alt="添加图片" title="添加图片"/></a>
                    <a href="pList?op=lookUser&id=<%=p.getGoodsId() %>"><img src="<%=path %>/Backstage/img/read.png" alt="查看" title="查看"/></a>
                    <a href="pList?op=upData&id=<%=p.getGoodsId() %>"><img src="<%=path %>/Backstage/img/xiugai.png" alt="修改" title="修改"/></a>
                    <a href="pList?op=delOne&id=<%=p.getGoodsId() %>" class="removeProvider"><img src="<%=path %>/Backstage/img/schu.png" alt="删除" title="删除"/></a>
                </td>
            </tr>
             <%
              	}
            }
           %>
        </table>
            <div style="text-align: center;margin-top: 15px;">
            <a href="pList?op=showProvider&no=<%=pages.getIndex()%>&word=<%=word%>"><button type="button" >首页</button></a>
            <a href="pList?op=showProvider&no=<%=pages.getUpPage()%>&word=<%=word%>"><button type="button" >上一页</button></a>
            <span>第<%=pages.getPageNo()%>页/总<%=pages.getCountPage() %>页</span>
        	<a href="pList?op=showProvider&no=<%=pages.getDownPage()%>&word=<%=word%>"><button type="button" >下一页</button></a>
        	<a href="pList?op=showProvider&no=<%=pages.getLastIndex()%>&word=<%=word%>"><button type="button" >尾页</button></a> 
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