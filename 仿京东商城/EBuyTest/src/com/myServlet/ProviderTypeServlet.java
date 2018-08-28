package com.myServlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dao.ProviderTypeDao;
import com.entity.ProviderType;
import com.util.PageUtil;

@SuppressWarnings("serial")
public class ProviderTypeServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String op = request.getParameter("op");
		if(op.equals("pType")){
			showUser(request, response);
		}
		if(op.equals("delPro")){
			delProviders(request, response);
		}
		if(op.equals("rname")){
			rname(request, response);
		}
		if(op.equals("addpro")){
			addProvider(request, response);
		}
		if(op.equals("updatapro")){
			updProvider(request, response);
		}
		if(op.equals("upPro")){
			upPro(request, response);
		}
		if(op.equals("lookPro")){
			lookPro(request, response);
		}
		if(op.equals("intopType")){
			intopType(request, response);
		}
	}
	private void intopType(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/Backstage/providerTypeAdd.jsp").forward(request, response);
	}
	public void lookPro(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		ProviderTypeDao cd = new ProviderTypeDao();
		ProviderType providers = cd.getOne(id);
		request.setAttribute("providers", providers);
		request.getRequestDispatcher("/Backstage/providerView.jsp").forward(request, response);
	}
	//跳转修改页面
	public void upPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		ProviderTypeDao cd = new ProviderTypeDao();
		ProviderType providers = cd.getOne(id);
		request.setAttribute("id", id);
		request.setAttribute("providers", providers);
		request.getRequestDispatcher("/Backstage/providerUpdate.jsp").forward(request, response);
	}
	//修改商品
	public void updProvider(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		name = new String(name.getBytes("ISO-8859-1"),"utf-8");
		ProviderTypeDao cd = new ProviderTypeDao();
		if(cd.upProvider(name, id)>0){
			showaAll(request, response);
		}else{
			showaAll(request, response);
		}
	}
	//添加商品
	public void addProvider(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		name = new String(name.getBytes("ISO-8859-1"),"utf-8");
		ProviderTypeDao cd = new ProviderTypeDao();
		if(cd.addPro(name)>0){
			showaAll(request, response);
		}else{
			showaAll(request, response);
		}
	}
	//验证商品类型是否存在
	public void rname(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		name = new String(name.getBytes("ISO-8859-1"),"utf-8");
		ProviderTypeDao cd = new ProviderTypeDao();
		if(cd.rProvider(name)||name==null||name.equals("")){
			out.print("<font color='red'>X已经存在</font>");
		}else{
			out.print("");
		}
	}
	//删除用户
	public void delProviders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		ProviderTypeDao cd = new ProviderTypeDao();
		int dnum = cd.delProvider(id);
		if(dnum>0){
			showaAll(request, response);
		}else{
			showaAll(request, response);
		}
	}
	//不带关键字跳转
	private void showaAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ProviderTypeDao cd = new ProviderTypeDao();
		int pageNo=1;
		int pageSize=8;
		PageUtil<ProviderType> showPageUser = cd.GetPageProviders(pageNo, pageSize);
		//分页集合返回到页面
		if(showPageUser!=null){
			request.setAttribute("page", showPageUser);
			//跳转到用户列表界面
			request.getRequestDispatcher("/Backstage/providerType.jsp").forward(request, response);
		}
	}
	//带关键字跳转
	public void showUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProviderTypeDao cd = new ProviderTypeDao();
		String pageN = request.getParameter("no");
		String word = request.getParameter("word");
		word=new String(word.getBytes("iso-8859-1"),"utf-8");
		int pageNo=1;
		if(pageN!=""){
			pageNo = Integer.parseInt(pageN);
		}
		int pageSize=8;
		//模糊查询
		if(word!=null&&!word.equals("")){
			PageUtil<ProviderType> showPageUser = cd.pageWord(pageNo, pageSize, word);
			if(showPageUser!=null){
				request.setAttribute("page", showPageUser);
				request.setAttribute("word", word);
				//跳转到用户列表界面
				request.getRequestDispatcher("/Backstage/providerType.jsp").forward(request, response);
			}
		//全部查询
		}else{
			PageUtil<ProviderType> showPageUser = cd.GetPageProviders(pageNo, pageSize);
			//分页集合返回到页面
			if(showPageUser!=null){
				request.setAttribute("page", showPageUser);
				//跳转到用户列表界面
				request.getRequestDispatcher("/Backstage/providerType.jsp").forward(request, response);
			}
		}
	}
}
