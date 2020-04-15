package com.box.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.box.dao.UserDao;
import com.box.dao.impl.UserDaoImpl;
import com.box.domain.UserBean;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private Logger log=Logger.getLogger(this.getClass());
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		System.out.println("进入到了servlet中");
		
		/**
		 * 只有在“既没有session，也没有cookie”的情况下，也就是第一次登录，才需要执行servlet的登录操作
		 */
//		1、先看session中有没有Userbean,有的话，表示还处于登录状态，从filter放行后，根本不需要servlet做什么
		UserBean userBean =(UserBean) request.getSession().getAttribute("userBean");
		
		if (userBean!=null) {
		log.info("进入第一个if判断，session中还有userbean,但是没有执行跳转");	
		System.out.println(userBean.getId()+"===="+userBean.getUsername()+"===="+userBean.getPassword());
		}else if (userBean==null) {
//		2、userbean不存在，表示session失效，
		log.info("进入第一个else if判断,userbean为空，表示cookie中和seesion都没查到，开始执行首次的servlet中登录操作");
//			要么在filter中查cookie执行登录，要么就在servlet中查cookie执行登录，二选一即可
			String userName = request.getParameter("username");
			String password = request.getParameter("password");
			String autoLogin = request.getParameter("auto_login");
			//如果勾选了，获取到的autoLogin就是"on",如果没有勾选，获取的就是null
//			System.out.println("获取到的autoLogin值是："+autoLogin);
			UserBean user = new UserBean();
			user.setUsername(userName);
			user.setPassword(password);
//			System.out.println("userName+password+autoLogin分别是："+userName+"****"+password+"****"+autoLogin);
			
			//加入JDBC，就是去执行DAO层的内容
			UserDao dao = new UserDaoImpl();
			userBean = dao.login(user);
//			System.out.println("userBean.getId()是：：："+userBean.getId());
			if(userBean != null){
			log.info("进入servlet中第二个if判断，从数据库中查到了userbean");
			/**
			 * 以下是自动登录最核心的代码部分===============================	==============	
			 */
				//页面提交上来的时候，是否选择了自动登录，如果选了，就生成1个cookie
				if("on".equals(autoLogin)){
					log.info("进入servlet中第三个if判断，勾选了autoLogin，创建cookie");
					//发送cookie给客户端
					//这里的#box#只是一个分隔符而已，没其他的意思
					Cookie cookie = new Cookie("auto_login", userName+"#box#"+password);
					cookie.setMaxAge(60*60*24*7);//7天有效期，这样cookie就保存在了浏览器中
//					cookie.setPath("/AutoLoginDemo");
					//cookie.setPath(request.getContextPath());
					//这一步就是把服务器端生成的cookie写入浏览器中
					response.addCookie(cookie);
//					System.out.println("已经添加了cookie");
				}
			/**
			 * 以上是自动登录最核心的代码部分===============================	==============	
			 */	
				//成功了，进入首页
//				以前把Attribute存到request域中，所以，一定要用request.getRequestDispatcher().forward(req,resp);
				//现在把Attribute存到Session域中,所以就不一定要请求转发了，重定向也是可以的
				request.getSession().setAttribute("userBean", userBean);
//				request.getSession().setMaxInactiveInterval(10);
				response.sendRedirect("index.jsp");//index.jsp就是登陆成功后的页面
			}else{
				//不成功...重新回到了登陆的界面
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
