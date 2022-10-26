package ptit.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import ptit.definedEntity.WebMessage;
import ptithcm.Entity.NhanVienEntity;
import ptithcm.Entity.TaiKhoanEntity;

@Controller
@Transactional
public class AccountController {
	@Autowired
	SessionFactory factory;

	@Autowired
	ServletContext application;

	// Tim tai khoan theo username
	public TaiKhoanEntity getAcccountByUsername(String username) {
		Session session = factory.getCurrentSession();
		String hql = "FROM TaiKhoanEntity tk WHERE tk.username = :username";
		Query query = session.createQuery(hql);
		query.setParameter("username", username);
		TaiKhoanEntity tk = (TaiKhoanEntity) query.uniqueResult();
		return tk;
	}

	// Tim nhan vien theo username
	public NhanVienEntity getStaffByUsername(String username) {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhanVienEntity nv WHERE nv.taikhoan.username = :username";
		Query query = session.createQuery(hql);
		query.setParameter("username", username);
		NhanVienEntity nv = (NhanVienEntity) query.uniqueResult();
		return nv;
	}

	@RequestMapping("useraccount/index")
	public String index(HttpServletRequest request, Principal principal, ModelMap model) {
		// Ngay thang nam va nhan vien
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date today = new Date();
		String dateNow = formatter.format(today);
		application.setAttribute("dateNow", dateNow);
		// Truy van
		NhanVienEntity nhanvien = this.getStaffByUsername(principal.getName());
		int tkNhanVien = nhanvien.getTaikhoan().getUSER_ROLE().equals("ROLE_STAFF") ? 1 : 0;
		application.setAttribute("tkNhanVien", tkNhanVien);
		application.setAttribute("user", nhanvien);
		// End ngay thang nam, nhan vien

		model.addAttribute("username", principal.getName());
		return "UserAccount";
	}

	@RequestMapping(value = "useraccount/submit-change")
	public String submitChange(HttpServletRequest request, Principal principal, ModelMap model) {
		// Ngay thang nam va nhan vien
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date today = new Date();
		String dateNow = formatter.format(today);
		application.setAttribute("dateNow", dateNow);
		// Truy van
		NhanVienEntity nhanvien = this.getStaffByUsername(principal.getName());
		int tkNhanVien = nhanvien.getTaikhoan().getUSER_ROLE().equals("ROLE_STAFF") ? 1 : 0;
		application.setAttribute("tkNhanVien", tkNhanVien);
		application.setAttribute("user", nhanvien);
		// End ngay thang nam, nhan vien
		WebMessage webMessage = new WebMessage();

		// Cac value tu form
		String oldPassword = request.getParameter("old-password");
		String newPassword = request.getParameter("new-password");
		String confirmPassword = request.getParameter("confirm-password");

		if (oldPassword == "" || newPassword == "" || confirmPassword == "") {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Vui lòng điền đầy đủ thông tin !");

			model.addAttribute("webMessage", webMessage);
			model.addAttribute("oldPassword", oldPassword);
			model.addAttribute("newPassword", newPassword);
			model.addAttribute("confirmPassword", confirmPassword);
			return "UserAccount";
		}
		if(!newPassword.equals(confirmPassword)) {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Mật khẩu nhập lại không trùng !");

			model.addAttribute("webMessage", webMessage);
			model.addAttribute("oldPassword", oldPassword);
			model.addAttribute("newPassword", newPassword);
			model.addAttribute("confirmPassword", confirmPassword);
			return "UserAccount";
		}
		TaiKhoanEntity taiKhoan = this.getAcccountByUsername(principal.getName());
		
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		if(!bcrypt.matches(oldPassword, taiKhoan.getPassword())) {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Mật khẩu cũ của bạn không đúng !");
			
			model.addAttribute("webMessage", webMessage);
			model.addAttribute("oldPassword", oldPassword);
			model.addAttribute("newPassword", newPassword);
			model.addAttribute("confirmPassword", confirmPassword);
			return "UserAccount";
		}
		taiKhoan.setPassword(bcrypt.encode(newPassword));
		taiKhoan.setConfirmPassword(bcrypt.encode(newPassword));
		webMessage.setMessageType("Thành công");
		webMessage.setMessage("Chỉnh sửa tài khoản thành công !");
		
		model.addAttribute("webMessage", webMessage);
		model.addAttribute("usernameValue", principal.getName());
		return "redirect:/logout";
	}

	// Update tai khoan
	@Transactional
	public Integer updateAccountDB(TaiKhoanEntity tk) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(tk);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}
}
