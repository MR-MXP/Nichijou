package com.myServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.CustomerDao;
import com.dao.UserDao;
import com.entity.Customers;
import com.util.PageUtil;

@SuppressWarnings("serial")
public class MyServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String op = request.getParameter("op");
		//登陆
		if(op.equals("login")&&op!=null){
			login(request, response);
		//显示用户
		}else if(op.equals("showUser")&&op!=null){
			showUser(request, response);
		//添加页面跳转
		}else if(op.equals("add")&&op!=null){
			add(request, response);
		//添加用户
		}else if(op.equals("addUser")){
			addCustomer(request, response);
		//验证邮箱
		}else if(op.equals("remail")){
			rCustomer(request, response);
		//删除所有
		}else if(op.equals("delAll")){
			delAll(request, response);
		//删除单个
		}else if(op.equals("delOne")){
			delAll(request, response);
		//查看用户详细信息
		}else if(op.equals("lookUser")){
			lookUser(request, response);
		//跳转用户修改界面
		}else if(op.equals("upData")){
			upData(request, response);
		//修改用户信息
		}else if(op.equals("updadaCustomer")){
			updadaCustomer(request, response);
		//跳转修改密码界面
		}else if(op.equals("intoPwd")){
			intoPwd(request, response);
		//跳转退出界面
		}else if(op.equals("exit")){
			exits(request, response);
		}
		
		
	}
	private void exits(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/Backstage/login.jsp").forward(request, response);
	}

	private void intoPwd(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/Backstage/password.jsp").forward(request, response);
	}

	//添加用户
	public void add(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/Backstage/userAdd.jsp").forward(request, response);
	}
	//修改用户信息
	public void upData(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		CustomerDao cd = new CustomerDao();
		Customers customer = cd.getOne(id);
		if(customer!=null){
			request.setAttribute("customer", customer);
			request.getRequestDispatcher("/Backstage/userUpdate.jsp").forward(request, response);
		}else{
			showaAll(request, response);
		}
	}
	//修改用户信息
	public void updadaCustomer(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("userid");
		CustomerDao cd = new CustomerDao();
		Customers customer = cd.getOne(id);
		if(customer!=null){
			customer.setName(request.getParameter("userName"));
			customer.setSex(request.getParameter("sex"));
			customer.setMovePhone(request.getParameter("userphone"));
			customer.setAddres(request.getParameter("userAddress"));
			customer.setVip((int)Integer.parseInt(request.getParameter("userlei")));
			int i = cd.updataC(customer);
			if(i>0){
				showaAll(request, response);
			}else{
				showaAll(request, response);
			}
		}
	}
	//用户详细信息
	public void lookUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		CustomerDao cd = new CustomerDao();
		Customers customer = cd.getOne(id);
		if(customer!=null){
			request.setAttribute("customer", customer);
			request.getRequestDispatcher("/Backstage/userView.jsp").forward(request, response);
		}
	}

	//删除选中用户
	public void delAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String ids = request.getParameter("ids");
		String[] idarr = ids.split(",");
		CustomerDao du = new CustomerDao();
		int dnum = du.delCustomer(idarr);
		if(dnum==idarr.length){
			showaAll(request, response);
		}else{
			showaAll(request, response);
		}
	}

	//登陆
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("username");
		String pwd = request.getParameter("password");
		String noAdmin = "X 账号或密码错误";
		UserDao ud = new UserDao();
		boolean login = ud.login(name, pwd);
		if(login){
			HttpSession session = request.getSession();
			session.setAttribute("name", name);
			request.getRequestDispatcher("/Backstage/index.jsp").forward(request, response);
		}else{
			request.setAttribute("noAdmin", noAdmin);
			request.getRequestDispatcher("/Backstage/login.jsp").forward(request, response);
		}
	}
	//显示全部用户
	public void showaAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CustomerDao cd = new CustomerDao();
		int pageNo=1;
		int pageSize=8;
		PageUtil<Customers> showPageUser = cd.showPageUser(pageNo, pageSize);
		//分页集合返回到页面
		if(showPageUser!=null){
			request.setAttribute("page", showPageUser);
			//跳转到用户列表界面
			request.getRequestDispatcher("/Backstage/userList.jsp").forward(request, response);
		}
	}
	//显示会员界面
	public void showUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CustomerDao cd = new CustomerDao();
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
			PageUtil<Customers> showPageUser = cd.pageWord(pageNo, pageSize, word);
			if(showPageUser!=null){
				request.setAttribute("page", showPageUser);
				request.setAttribute("word", word);
				//跳转到用户列表界面
				request.getRequestDispatcher("/Backstage/userList.jsp").forward(request, response);
			}
		//全部查询
		}else{
			PageUtil<Customers> showPageUser = cd.showPageUser(pageNo, pageSize);
			//分页集合返回到页面
			if(showPageUser!=null){
				request.setAttribute("page", showPageUser);
				//跳转到用户列表界面
				request.getRequestDispatcher("/Backstage/userList.jsp").forward(request, response);
			}
		}
	}
	//验证注册用户是否存在
	public void rCustomer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String email = request.getParameter("email");
		email = new String(email.getBytes("ISO-8859-1"),"utf-8");
		PrintWriter out = response.getWriter();
		CustomerDao cd = new CustomerDao();
		if(cd.rCustomer(email)||email==null||email.equals("")){
			out.print("<font color='red'>X已经存在</font>");
		}else{
			out.print("<font color='green'>√可以使用</font>");
		}
	}
	//添加用户
	public void addCustomer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Customers c = new Customers();
		CustomerDao cd = new CustomerDao();
		String cname = request.getParameter("userName");
		String email = request.getParameter("email");
		String sex = request.getParameter("sex");
		String movePhone = request.getParameter("userphone");
		String adress = request.getParameter("userAddress");
		String pwd = request.getParameter("userpassword");
		String vip = request.getParameter("userlei");
		System.out.println(cname);
		c.setEmail(email);
		c.setName(cname);
		c.setSex(sex);
		c.setMovePhone(movePhone);
		c.setPwd(pwd);
		c.setAddres(adress);
		c.setRegisterTime(cd.getDate());
		c.setAddres(vip);
		if(cd.addCustomer(c)>0){
			showaAll(request, response);
		}else{
			showaAll(request, response);
		}
	}
}
