package ptit.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ptithcm.Entity.HoaDonEntity;
import ptithcm.Entity.KhachHangEntity;
import ptithcm.Entity.NhanVienEntity;
import ptithcm.Entity.PhieuNhapEntity;
import ptithcm.Entity.VPPEntity;

@Controller
public class MainPageController {
	@Autowired
	SessionFactory factory;

	@Autowired
	ServletContext application;

	// Tim nhan vien theo username
	public NhanVienEntity getStaffByUsername(String username) {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhanVienEntity nv WHERE nv.taikhoan.username = :username";
		Query query = session.createQuery(hql);
		query.setParameter("username", username);
		NhanVienEntity nv = (NhanVienEntity) query.uniqueResult();
		return nv;
	}

	@Transactional
	@RequestMapping(value = "/mainPage", method = RequestMethod.GET)
	public String userInfo(Model model, Principal principal) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date today = new Date();
		String dateNow = formatter.format(today);
		application.setAttribute("dateNow", dateNow);

		// Truy van
		NhanVienEntity nhanvien = this.getStaffByUsername(principal.getName());

		int tkNhanVien = nhanvien.getTaikhoan().getUSER_ROLE().equals("ROLE_STAFF") ? 1 : 0;

		application.setAttribute("tkNhanVien", tkNhanVien);
		application.setAttribute("user", nhanvien);
		model.addAttribute("hoaDon", this.getHoaDonCuaNV(nhanvien.getID_NV(), today));
		return "main";
	}

	// Dem tong van phong pham
	@Transactional
	@ModelAttribute("soVPP")
	public Long getProductQuan() {
		Session session = factory.getCurrentSession();
		String hql = "SELECT COUNT (*) FROM VPPEntity";
		Query query = session.createQuery(hql);
		Long quantity = (Long) query.uniqueResult();
		return quantity;
	}

	@Transactional
	@ModelAttribute("soPN")
	public Long getPNQuan() {
		Session session = factory.getCurrentSession();
		String hql = "SELECT COUNT (*) FROM PhieuNhapEntity";
		Query query = session.createQuery(hql);
		Long quantity = (Long) query.uniqueResult();
		return quantity;
	}

	@Transactional
	@ModelAttribute("soHD")
	public Long getHDQuan() {
		Session session = factory.getCurrentSession();
		String hql = "SELECT COUNT (*) FROM HoaDonEntity";
		Query query = session.createQuery(hql);
		Long quantity = (Long) query.uniqueResult();
		return quantity;
	}

	@Transactional
	@ModelAttribute("soKH")
	public Long getCustomerQuan() {
		Session session = factory.getCurrentSession();
		String hql = "SELECT COUNT (*) FROM KhachHangEntity";
		Query query = session.createQuery(hql);
		Long quantity = (Long) query.uniqueResult();
		return quantity;
	}

	// Lay tat ca hoa don cua nhan vien trong hom nay
	@Transactional
	public List<HoaDonEntity> getHoaDonCuaNV(int idNhanVien, Date date) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HoaDonEntity hd WHERE hd.nhanVien.ID_NV = :id AND hd.ngayLap = :date";
		Query query = session.createQuery(hql);
		query.setParameter("id", idNhanVien);
		query.setParameter("date", date);
		List<HoaDonEntity> list = query.list();
		return list;
	}

	@RequestMapping("product")
	public String product(Model model, Principal principal, ModelMap modelPage, @ModelAttribute("VPP") VPPEntity vpp) {
		return "product";
	}
}
