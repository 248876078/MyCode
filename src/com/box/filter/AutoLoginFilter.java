package com.box.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;

import com.box.dao.UserDao;
import com.box.dao.impl.UserDaoImpl;
import com.box.domain.UserBean;
import com.box.util.CookieUtil;

/**
 * Servlet Filter implementation class AutoLoginFilter
 */
public class AutoLoginFilter implements Filter {

	Logger logger = Logger.getLogger(this.getClass());
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		/**
		 * 拦截器的大思路：下次访问网站的时候
				1、过滤器拦截任意请求 url-pattern 内容是  /*
			  	2、判断session是否持续有效
			  		2.1如果session有效，
			  			直接放行
			  		2.2如果session失效了，判断有无指定的cookie
						2.2.1如果没有cookie，就是第一次登录，
							直接放行（进入servlet执行登录）
						2.2.2如果有cookie,获取用户名和密码（先获取）
							调用完成登录操作,返回user（再执行）
							再放行
				
				额外建议（也可以不做这个）：当user不为空的时候将user放入session中，
						这样的话，只要session有效，就不用去找cookie了。
		 */
		
		try {
//			1、先把ServletRequest强转为httpServletRequest
			HttpServletRequest request = (HttpServletRequest) req;
//			2、先判断，现在session中还有没有那个userBean.
			UserBean userBean = (UserBean) request.getSession().getAttribute("userBean");
			//2.1如果session有效，
			if(userBean != null){
				logger.info("第一个if判断，session还有效，直接放行");
//				System.out.println("判断一下，session还有效，直接放行");
				chain.doFilter(request, response);
			}else{
				logger.info("第一个else判断，session已经失效");
//			2.2如果session失效了，判断有无指定的cookie
				//代表session失效了。
//				System.out.println("进入到filter的session失效中");
				//2.2.1如果没有cookie，就是第一次登录，
				//来请求的时候，先从请求里面取出cookie , 但是cookie有很多的key-value
				Cookie[] cookies = request.getCookies();
				//从一堆的cookie里面找出我们以前给浏览器发的那个cookie
//				Cookie cookie = CookieUtil.findCookie(cookies, "auto_login");
				Cookie cookie =null;
				if(cookies != null){
					for (Cookie cookie1 : cookies) {
						if("auto_login".equals(cookie1.getName())){
//							return cookie;
							cookie=cookie1;
//							System.out.println("找到了auto_login.equals(cookie1.getName())");
						}
					}
				}
				//第一次来，直接放行，去servlet执行登录
				if(cookie  == null){
					logger.info("第二个if判断，有cookie");
					chain.doFilter(request, response);
				}else{
//				2.2.2如果有cookie,获取用户名和密码（先获取）
					//不是第一次。表示有我想要的cookie
					logger.info("第二个else判断，有cookie");
//					System.out.println("不是第一次登录，有cookie");
					String value = cookie.getValue();
					String username = value.split("#box#")[0];
					String password = value.split("#box#")[1];
//System.out.println("获取到cookie.getValue()是"+value);
					//完成登录
					UserBean user = new UserBean();
					user.setUsername(username);
					user.setPassword(password);

					UserDao dao = new UserDaoImpl();
					userBean = dao.login(user);
					
					//使用session存这个值到域中，方便下一次未过期前还可以用。
					request.getSession().setAttribute("userBean", userBean);
//					最后放行
					chain.doFilter(request, response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			chain.doFilter(req, response);
		}
	}

	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
//		有可能在这里面执行10分钟的耗时
	}

}
