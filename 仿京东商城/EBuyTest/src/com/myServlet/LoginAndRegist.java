package com.myServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.CustomerDao;

@SuppressWarnings("serial")
public class LoginAndRegist extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String op = request.getParameter("op");
		if(op.equals("login")){
			login(request,response);
		}
	}
	//登录校验
	private void login(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String name = request.getParameter("user");
		String pwd = request.getParameter("password");
		CustomerDao cd = new CustomerDao();
		boolean rc = cd.rCusertomer(name, pwd);
		String hint="X账号或密码错误!";
		if(rc){
			session.removeAttribute("username");
			session.setAttribute("username", name);
			request.getRequestDispatcher("/home/shopcart.jsp").forward(request, response);
		}else{
			request.setAttribute("mistake",hint);
			request.getRequestDispatcher("/home/login.jsp").forward(request, response);
		}
	}
}
