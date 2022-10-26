package ptit.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ptit.definedEntity.WebMessage;

@Controller
public class LoginController {
	@RequestMapping("login")
	public String loginMaping() {
		return "Login/login";
	}
	
	@RequestMapping()
	public String login() {
		return "redirect:/login.htm";
	}
	
	// Dang xuat tai khoan quay tro lai view login
	@RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
	public String logoutSuccessfulPage(Model model) {
		return "Login/login";
	}
//
//	@RequestMapping(value = "/manager", method = RequestMethod.GET)
//	public String adminPage(Model model) {
//		return "admin";
//	}
//
//	@RequestMapping(value = "/staff", method = RequestMethod.GET)
//	public String userPage(Model model) {
//		return "user";
//	}
//
	
	@RequestMapping(value = "login", params = "failed")
	public String loginFailed(Model model, HttpServletRequest request) {
		WebMessage webMessage = new WebMessage();
		webMessage.setMessageType("Thất bại");
		webMessage.setMessage("Tài khoản hoặc mật khẩu không đúng, vui lòng kiểm tra lại !");
		model.addAttribute("webMessage", webMessage);
		
		return "Login/login";
	}
	
	@RequestMapping(value = "/j_spring_security_check", method = RequestMethod.POST)
	public String login(Model model, HttpServletRequest request,
			@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String user) {
		
		return "Login/login";
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {
		return "404Page";
	}
}
