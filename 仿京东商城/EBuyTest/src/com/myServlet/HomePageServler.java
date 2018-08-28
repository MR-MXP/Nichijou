package com.myServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.NoticeDao;
import com.dao.ProvidersDao;
import com.entity.GoodsCar;
import com.entity.GoodsInfo;
import com.entity.Notice;
import com.entity.Providers;
import com.util.PageUtil;

@SuppressWarnings("serial")
public class HomePageServler extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String op = request.getParameter("op");
		if(op.equals("showProvider")){
			showProvider(request,response);
		}
		if(op.equals("ViewProduct")){
			ViewProduct(request,response);
		}
		if(op.equals("addShoppingCar")){
			addShoppingCar(request,response);
		}
		if(op.equals("upShoppingCar")){
			upShoppingCar(request,response);
		}
		if(op.equals("showCar")){
			showCar(request,response);
		}
		if(op.equals("login")){
			login(request,response);
		}
		if(op.equals("account")){
			account(request,response);
		}
	}
	//跳转结算界面
	private void account(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pids = request.getParameter("pids");
		String[] pidarr = pids.split(",");
		HttpSession session = request.getSession();
		//创建一个新的购物车
		GoodsCar ncar = new GoodsCar();
		//获取旧购物车
		GoodsCar car= (GoodsCar)session.getAttribute("car");
		HashMap<Integer, GoodsInfo> ocar = car.getCar();
		//把选中的商品放入新的购物车
		for(int i=0;i<pidarr.length;i++){
			int id = Integer.parseInt(pidarr[i]);
			GoodsInfo goods = ocar.get(id);
			//添加到新的购物车
			ncar.addGoods(goods);
			//从旧的购物车中删除待付款商品
			//ocar.remove(id);
		}
		//删除session中旧的购物车,上传新的购物车
/*		session.removeAttribute("car");
		session.setAttribute("car",ocar);*/
		if(ncar!=null){
			//待付款新的购物车新的购物车
			session.setAttribute("ncar", ncar);
			//跳转支付界面
			request.getRequestDispatcher("/home/pay.jsp").forward(request, response);
		}
	}
	//跳转登录界面
	private void login(HttpServletRequest request, HttpServletResponse response) throws 
	ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/home/login.jsp").forward(request, response);
	}
	//异步修改商品数量
	private void upShoppingCar(HttpServletRequest request,
			HttpServletResponse response) {
		GoodsCar car = null;
		HttpSession session = request.getSession();
		int id = Integer.parseInt(request.getParameter("id"));
		int num = Integer.parseInt(request.getParameter("num"));
		car = (GoodsCar)session.getAttribute("car");
		car.changGoodsNum(id, num);
		//清除上次的session
		session.removeAttribute("car");
		//新建session
		session.setAttribute("car",car);
	}
	//跳转购物车界面
	private void showCar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//检查用户是否登录
		String name = request.getParameter("name");
		name=new String(name.getBytes("iso-8859-1"),"utf-8");
		HttpSession session = request.getSession();
		session.setAttribute("username",name);
		request.getRequestDispatcher("/home/shopcart.jsp").forward(request, response);
	}
	@SuppressWarnings("unused")
	private void addShoppingCar(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		//商品id
		String id = request.getParameter("id");
		//购买数量
		int num = Integer.parseInt(request.getParameter("num"));
		ProvidersDao pd = new ProvidersDao();
		//获取商品
		Providers providers = pd.getOne(id);
		//购物商品实体
		GoodsInfo gi = new GoodsInfo();
		GoodsCar car = null;
		gi.setP(providers);
		gi.setpPum(num);
		//获取购物车列表
		HttpSession session = request.getSession();
		car = (GoodsCar)request.getSession().getAttribute("car");
		if(car==null){
			car = new GoodsCar();
		}
		HashMap<Integer, GoodsInfo> cmap = car.getCar();
		//查看map中的条数
		System.out.println(cmap.size());
		//添加到购物车
		car.addGoods(gi);
		session.setAttribute("car",car);
		out.print("succeed");
	}
	//商品详情界面
	private void ViewProduct(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		ProvidersDao  cd = new ProvidersDao();
		Providers provider = cd.getOne(id);
		if(provider!=null){
			request.setAttribute("providersone", provider);
			request.getRequestDispatcher("/home/introduction.jsp").forward(request, response);
		}
	}
	//显示4个
	private void showProvider(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ProvidersDao  cd = new ProvidersDao();
		NoticeDao  nd = new NoticeDao();
		int pageNo=1;
		int pageSize=4;
		PageUtil<Providers> showPageUser = cd.showPageUser(pageNo, pageSize);
		PageUtil<Notice> Notice = nd.GetPageProviders(pageNo, 3);
		PageUtil<Providers> shopTitle  = cd.showsome(7, 4);
		//分页集合返回到页面
		if(showPageUser!=null){
			request.setAttribute("page", showPageUser);
			request.setAttribute("notice", Notice);
			request.setAttribute("shopTitle", shopTitle);
			//跳转到用户列表界面
			request.getRequestDispatcher("/home/home.jsp").forward(request, response);
		}
	}

}
