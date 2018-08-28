package com.myServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.AdminDao;

public class adminServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String op = request.getParameter("op");
		if(op.equals("rOldPwd")){
			rOldPwd(request,response);
		}
		if(op.equals("upPwd")){
			upPwd(request,response);
		}
	}
	private void upPwd(HttpServletRequest request, HttpServletResponse response) throws
	ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		name=new String(name.getBytes("iso-8859-1"),"utf-8");
		String rpwd = request.getParameter("pwd");
		AdminDao ad = new AdminDao();
		if(ad.upPwd(name, rpwd)>0){
			request.getRequestDispatcher("/Backstage/index.jsp").forward(request, response);
		}else{
			request.getRequestDispatcher("/Backstage/index.jsp").forward(request, response);
		}
	}
	//验证旧密码
	private void rOldPwd(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		String rpwd = request.getParameter("rpwd");
		AdminDao ad = new AdminDao();
		String pwd = ad.rpwd(name);
		PrintWriter out = response.getWriter();
		if(rpwd.equals(pwd)){
			out.print("<font color='green'>√密码正确</font>");
		}else{
			out.print("<font color='red'>X密码错误</font>");
		}
	}
}
