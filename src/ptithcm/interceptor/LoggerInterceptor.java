package ptithcm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoggerInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getName().equals("anonymousUser")) {
			response.sendRedirect(request.getContextPath() + "/login.htm");
			return false;
		}
		return true;
	}
}
