package ptit.controller;

import java.security.Principal;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ptit.definedEntity.WebMessage;
import ptithcm.Entity.HoaDonEntity;
import ptithcm.Entity.KhachHangEntity;
import ptithcm.Entity.NhanVienEntity;
import ptithcm.Entity.VPPEntity;

@Controller
@RequestMapping("customer")
public class CustomerController {
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@InitBinder
	public void customizeBinding(WebDataBinder binder) {
		df.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
	}

	@Autowired
	SessionFactory factory;

	@Autowired
	ServletContext application;

	int pageSize = 5;

	// Tim khach hang theo ID
	public KhachHangEntity getCustomerByID(Integer id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM KhachHangEntity kh WHERE kh.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		KhachHangEntity nv = (KhachHangEntity) query.uniqueResult();
		return nv;
	}

	// Lay tat ca hoa don cua khach hang
	public List<HoaDonEntity> getHoaDonCuaKH(int idKhachHang) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HoaDonEntity hd WHERE hd.khachHang.id = :id ORDER BY hd.id DESC";
		Query query = session.createQuery(hql);
		query.setParameter("id", idKhachHang);
		List<HoaDonEntity> list = query.list();
		return list;
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

	// tim khach hang theo filter
	public List<KhachHangEntity> findCustomers(Integer id, String infor, String maGioiTinh, Boolean daGiaoDich,
			Integer pageAt) {
		Session session = factory.getCurrentSession();
		int countPara = -1;
		String hql = "FROM KhachHangEntity kh WHERE ";
		String[] hqlString = new String[4];
		for (int i = 0; i < hqlString.length; i++) {
			hqlString[i] = "";
		}
		if (id != 0) {
			hqlString[0] = "kh.id = :id";
			countPara++;
		}
		if (!infor.isEmpty()) {
			hqlString[1] = "kh.ten LIKE :infor OR kh.ho LIKE :infor OR kh.sdt LIKE :infor OR kh.diaChi LIKE :infor";
			countPara++;
		}
		if (!maGioiTinh.isEmpty()) {
			hqlString[2] = "kh.gioiTinh LIKE :maGioiTinh";
			countPara++;
		}
		if (daGiaoDich != null) {
			hqlString[3] = "kh.daGiaoDich = :daGiaoDich";
			countPara++;
		}

		// Neu chi co 1 tham so khong null
		if (countPara == 0) {
			for (int i = 0; i < hqlString.length; i++) {
				hql = hql + hqlString[i];
			}
		} else { // Neu nhieu hon 1 tham so khong null
			for (int i = 0; i < hqlString.length; i++) {
				if (!hqlString[i].equals("")) {
					if (countPara > 0)
						hql = hql + hqlString[i] + " AND ";
					if (countPara == 0)
						hql = hql + hqlString[i];
					countPara--;
				}
			}
		}
		Query query = session.createQuery(hql);
		if (id != 0) {
			query.setParameter("id", id);
		}
		if (!infor.isEmpty()) {
			query.setParameter("infor", "%" + infor + "%");
		}
		if (!maGioiTinh.isEmpty()) {
			query.setParameter("maGioiTinh", "%" + maGioiTinh + "%");
		}
		if (daGiaoDich != null) {
			query.setParameter("daGiaoDich", daGiaoDich);
		}
		List<KhachHangEntity> list = query.setFirstResult(pageAt * pageSize).setMaxResults(pageSize).list();
		return list;

	}

	public List<KhachHangEntity> getCustomers(Integer pageAt) {
		Session session = factory.getCurrentSession();
		String hql = "FROM KhachHangEntity kh ORDER BY kh.id DESC";
		Query query = session.createQuery(hql);
		List<KhachHangEntity> list = query.setFirstResult(pageAt * pageSize).setMaxResults(pageSize).list();
		return list;
	}

	// tim so phan trang
	public long getPageCount() {
		Session session = factory.getCurrentSession();
		String hql = "select count(*) FROM KhachHangEntity";
		Query query = session.createQuery(hql);
		long count = (long) query.uniqueResult();
		return count;
	}

	public long getPageCount(Integer id, String infor, String maGioiTinh, Boolean daGiaoDich) {
		Session session = factory.getCurrentSession();
		int countPara = -1;
		String hql = "select count(*) FROM KhachHangEntity kh WHERE ";
		String[] hqlString = new String[4];
		for (int i = 0; i < hqlString.length; i++) {
			hqlString[i] = "";
		}
		if (id != 0) {
			hqlString[0] = "kh.id = :id";
			countPara++;
		}
		if (!infor.isEmpty()) {
			hqlString[1] = "kh.ten LIKE :infor OR kh.ho LIKE :infor OR kh.sdt LIKE :infor OR kh.diaChi LIKE :infor";
			countPara++;
		}
		if (!maGioiTinh.isEmpty()) {
			hqlString[2] = "kh.gioiTinh LIKE :maGioiTinh";
			countPara++;
		}
		if (daGiaoDich != null) {
			hqlString[3] = "kh.daGiaoDich = :daGiaoDich";
			countPara++;
		}

		// Neu chi co 1 tham so khong null
		if (countPara == 0) {
			for (int i = 0; i < hqlString.length; i++) {
				hql = hql + hqlString[i];
			}
		} else { // Neu nhieu hon 1 tham so khong null
			for (int i = 0; i < hqlString.length; i++) {
				if (!hqlString[i].equals("")) {
					if (countPara > 0)
						hql = hql + hqlString[i] + " AND ";
					if (countPara == 0)
						hql = hql + hqlString[i];
					countPara--;
				}
			}
		}
		Query query = session.createQuery(hql);
		if (id != 0) {
			query.setParameter("id", id);
		}
		if (!infor.isEmpty()) {
			query.setParameter("infor", "%" + infor + "%");
		}
		if (!maGioiTinh.isEmpty()) {
			query.setParameter("maGioiTinh", "%" + maGioiTinh + "%");
		}
		if (daGiaoDich != null) {
			query.setParameter("daGiaoDich", daGiaoDich);
		}
		long count = (long) query.uniqueResult();
		return count;
	}

	// Them khach hang
	@Transactional
	public Integer insertClient(KhachHangEntity kh) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(kh);
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

	// Update khach hang
	@Transactional
	public Integer updateCustomer(KhachHangEntity kh) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.update(kh);
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

	// Xoa khach hang
	@Transactional
	public Integer deleteCustomerByID(int id) {
		Session session = factory.getCurrentSession();
		Transaction t = session.getTransaction();
		KhachHangEntity kh = this.getCustomerByID(id);
		try {
			session.delete(kh);
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

	@Transactional
	@RequestMapping("index")
	public String mainCustomer(HttpServletRequest request, Model model, HttpSession session,
			@ModelAttribute("khachHang") KhachHangEntity khachHang, BindingResult errors, Principal principal) {
		// id khach hàng
		Integer id;
		String idStr = request.getParameter("idCustomerSearch");
		if (idStr == null) {
			idStr = "";
		}
		if (idStr.isEmpty()) {
			id = 0;
		} else {
			id = Integer.parseInt(idStr);
		}
		// ho, ten, diachi,...
		String infor = request.getParameter("inforCustomerSearch");
		if (infor == null) {
			infor = "";
		}
		// gioi tinh và đã gd
		String gioiTinh = "";
		String maGioiTinh = request.getParameter("gioiTinhSearch");
		if (maGioiTinh != null) {
			if (!maGioiTinh.isEmpty()) {
				if (Integer.parseInt(maGioiTinh) == 1) {
					gioiTinh = "Nữ";
				} else if (Integer.parseInt(maGioiTinh) == 0) {
					gioiTinh = "Nam";
				}
			}
		} else {
			maGioiTinh = "";
		}

		Boolean daGiaoDich = null;
		String maGiaoDich = request.getParameter("daMuaHangSearch");
		if (maGiaoDich != null) {
			if (!maGiaoDich.isEmpty()) {
				if (Integer.parseInt(maGiaoDich) == 1) {
					daGiaoDich = true;
				} else if (Integer.parseInt(maGiaoDich) == 0) {
					daGiaoDich = false;
				}
			}
		} else {
			maGiaoDich = "";
		}

		int page = ServletRequestUtils.getIntParameter(request, "p", 0);
		List<KhachHangEntity> customers = this.getCustomers(page);
		long pageCount = 0;
		if (id == 0 && infor.isEmpty() && maGioiTinh.isEmpty() && daGiaoDich == null) {
			customers = this.getCustomers(page);
			pageCount = this.getPageCount();
		} else {
			customers = this.findCustomers(id, infor, gioiTinh, daGiaoDich, page);
			pageCount = this.getPageCount(id, infor, maGioiTinh, daGiaoDich);
		}

		PagedListHolder pagedListHolder = new PagedListHolder(customers);
		int totalPage = ((int) pageCount) / pageSize;
		if (pageCount % pageSize != 0) {
			totalPage += 1;
		}

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

		session.removeAttribute("webMessage");
		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("inforInput", infor);
		model.addAttribute("idInput", id);
		model.addAttribute("gioiTinhInput", maGioiTinh);
		model.addAttribute("daGiaoDichInput", maGiaoDich);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);
		model.addAttribute("activeMapping", "sidebar-khachhang");
		return "customer/customerManage";
	}

	@Transactional
	@RequestMapping("index2")
	public String mainCustomer2(HttpServletRequest request, Model model, HttpSession session,
			@ModelAttribute("khachHang") KhachHangEntity khachHang, BindingResult errors, Principal principal) {
		// id khach hàng
		Integer id;
		String idStr = request.getParameter("idCustomerSearch");
		if (idStr == null) {
			idStr = "";
		}
		if (idStr.isEmpty()) {
			id = 0;
		} else {
			id = Integer.parseInt(idStr);
		}
		// ho, ten, diachi,...
		String infor = request.getParameter("inforCustomerSearch");
		if (infor == null) {
			infor = "";
		}
		// gioi tinh và đã gd
		String gioiTinh = "";
		String maGioiTinh = request.getParameter("gioiTinhSearch");
		if (maGioiTinh != null) {
			if (!maGioiTinh.isEmpty()) {
				if (Integer.parseInt(maGioiTinh) == 1) {
					gioiTinh = "Nữ";
				} else if (Integer.parseInt(maGioiTinh) == 0) {
					gioiTinh = "Nam";
				}
			}
		} else {
			maGioiTinh = "";
		}

		Boolean daGiaoDich = null;
		String maGiaoDich = request.getParameter("daMuaHangSearch");
		if (maGiaoDich != null) {
			if (!maGiaoDich.isEmpty()) {
				if (Integer.parseInt(maGiaoDich) == 1) {
					daGiaoDich = true;
				} else if (Integer.parseInt(maGiaoDich) == 0) {
					daGiaoDich = false;
				}
			}
		} else {
			maGiaoDich = "";
		}

		int page = ServletRequestUtils.getIntParameter(request, "p", 0);
		List<KhachHangEntity> customers = this.getCustomers(page);
		long pageCount = 0;
		if (id == 0 && infor.isEmpty() && maGioiTinh.isEmpty() && daGiaoDich == null) {
			customers = this.getCustomers(page);
			pageCount = this.getPageCount();
		} else {
			customers = this.findCustomers(id, infor, gioiTinh, daGiaoDich, page);
			pageCount = this.getPageCount(id, infor, maGioiTinh, daGiaoDich);
		}

		PagedListHolder pagedListHolder = new PagedListHolder(customers);
		int totalPage = ((int) pageCount) / pageSize;
		if (pageCount % pageSize != 0) {
			totalPage += 1;
		}

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

		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("inforInput", infor);
		model.addAttribute("idInput", id);
		model.addAttribute("gioiTinhInput", maGioiTinh);
		model.addAttribute("daGiaoDichInput", maGiaoDich);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);
		model.addAttribute("activeMapping", "sidebar-khachhang");
		return "customer/customerManage";
	}

	// tim kiem khach hang
	@Transactional
	@RequestMapping(value = "/index", params = "btnSearch")
	public String searchCustomer(@ModelAttribute("khachHang") KhachHangEntity khachHang, BindingResult errors,
			HttpServletRequest request, Model model, HttpSession session) {
		// id khach hàng
		Integer id;
		String idStr = request.getParameter("idCustomerSearch");
		if (idStr == null) {
			idStr = "";
		}
		if (idStr.isEmpty()) {
			id = 0;
		} else {
			id = Integer.parseInt(idStr);
		}
		// ho, ten, diachi,...
		String infor = request.getParameter("inforCustomerSearch");
		if (infor == null) {
			infor = "";
		}
		// gioi tinh và đã gd
		String gioiTinh = "";
		String maGioiTinh = request.getParameter("gioiTinhSearch");
		if (maGioiTinh != null) {
			if (!maGioiTinh.isEmpty()) {
				if (Integer.parseInt(maGioiTinh) == 1) {
					gioiTinh = "Nữ";
				} else if (Integer.parseInt(maGioiTinh) == 0) {
					gioiTinh = "Nam";
				}
			}
		} else {
			maGioiTinh = "";
		}

		Boolean daGiaoDich = null;
		String maGiaoDich = request.getParameter("daMuaHangSearch");
		if (maGiaoDich != null) {
			if (!maGiaoDich.isEmpty()) {
				if (Integer.parseInt(maGiaoDich) == 1) {
					daGiaoDich = true;
				} else if (Integer.parseInt(maGiaoDich) == 0) {
					daGiaoDich = false;
				}
			}
		} else {
			maGiaoDich = "";
		}

		int page = ServletRequestUtils.getIntParameter(request, "p", 0);
		List<KhachHangEntity> customers = this.getCustomers(page);
		long pageCount = 0;
		if (id == 0 && infor.isEmpty() && maGioiTinh.isEmpty() && daGiaoDich == null) {
			customers = this.getCustomers(page);
			pageCount = this.getPageCount();
		} else {
			customers = this.findCustomers(id, infor, gioiTinh, daGiaoDich, page);
			pageCount = this.getPageCount(id, infor, maGioiTinh, daGiaoDich);
		}

		PagedListHolder pagedListHolder = new PagedListHolder(customers);
		int totalPage = ((int) pageCount) / pageSize;
		if (pageCount % pageSize != 0) {
			totalPage += 1;
		}

		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("inforInput", infor);
		model.addAttribute("idInput", id);
		model.addAttribute("gioiTinhInput", maGioiTinh);
		model.addAttribute("daGiaoDichInput", maGiaoDich);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);
		model.addAttribute("activeMapping", "sidebar-khachhang");
		return "customer/customerManage";
	}

	// Them khach hang tu form
	@Transactional
	@RequestMapping(value = "/addCustomer", params = "btnAddCustomer")
	public String addCustomer(HttpServletRequest request, ModelMap model,
			@Validated @ModelAttribute("khachHang") KhachHangEntity khachHang, BindingResult errors,
			HttpSession session, Principal principal) {
		khachHang.setDaGiaoDich(false);
		Integer errorCode = this.insertClient(khachHang);
		WebMessage webMessage = new WebMessage();

		if (errorCode != 0) {
			// reset
			khachHang.reset();
			webMessage.setMessageType("Thành công");
			webMessage.setMessage("Thêm khách hàng thành công !");
		} else {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Thêm khách hàng thất bại !");
		}

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

		// id khach hàng
		Integer id;
		String idStr = request.getParameter("idCustomerSearch");
		if (idStr == null) {
			idStr = "";
		}
		if (idStr.isEmpty()) {
			id = 0;
		} else {
			id = Integer.parseInt(idStr);
		}
		// ho, ten, diachi,...
		String infor = request.getParameter("inforCustomerSearch");
		if (infor == null) {
			infor = "";
		}
		// gioi tinh và đã gd
		String gioiTinh = "";
		String maGioiTinh = request.getParameter("gioiTinhSearch");
		if (maGioiTinh != null) {
			if (!maGioiTinh.isEmpty()) {
				if (Integer.parseInt(maGioiTinh) == 1) {
					gioiTinh = "Nữ";
				} else if (Integer.parseInt(maGioiTinh) == 0) {
					gioiTinh = "Nam";
				}
			}
		} else {
			maGioiTinh = "";
		}

		Boolean daGiaoDich = null;
		String maGiaoDich = request.getParameter("daMuaHangSearch");
		if (maGiaoDich != null) {
			if (!maGiaoDich.isEmpty()) {
				if (Integer.parseInt(maGiaoDich) == 1) {
					daGiaoDich = true;
				} else if (Integer.parseInt(maGiaoDich) == 0) {
					daGiaoDich = false;
				}
			}
		} else {
			maGiaoDich = "";
		}

		int page = ServletRequestUtils.getIntParameter(request, "p", 0);
		List<KhachHangEntity> customers = this.getCustomers(page);
		long pageCount = 0;
		if (id == 0 && infor.isEmpty() && maGioiTinh.isEmpty() && daGiaoDich == null) {
			customers = this.getCustomers(page);
			pageCount = this.getPageCount();
		} else {
			customers = this.findCustomers(id, infor, gioiTinh, daGiaoDich, page);
			pageCount = this.getPageCount(id, infor, maGioiTinh, daGiaoDich);
		}

		PagedListHolder pagedListHolder = new PagedListHolder(customers);
		int totalPage = ((int) pageCount) / pageSize;
		if (pageCount % pageSize != 0) {
			totalPage += 1;
		}
		
		model.addAttribute("khachHang", khachHang);
		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("inforInput", infor);
		model.addAttribute("idInput", id);
		model.addAttribute("gioiTinhInput", maGioiTinh);
		model.addAttribute("daGiaoDichInput", maGiaoDich);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);
		model.addAttribute("webMessage", webMessage);
		model.addAttribute("activeMapping", "sidebar-khachhang");
		model.addAttribute("errorsCount", errors.getErrorCount());

		return "customer/customerManage";
	}

	// Chinh sua khach hang tu form
	@Transactional
	@RequestMapping(value = "/updateCustomer", params = "btnUpdateCustomer")
	public String updateCustomer(HttpServletRequest request, ModelMap model,
			@Validated @ModelAttribute("khachHang") KhachHangEntity khachHang, BindingResult errors,
			HttpSession session, Principal principal) {
		khachHang.setDaGiaoDich(khachHang.getDaGiaoDich());
		Integer errorCode = this.updateCustomer(khachHang);

		WebMessage webMessage = new WebMessage();

		if (errorCode != 0) {
			// reset
			khachHang.reset();
			webMessage.setMessageType("Thành công");
			webMessage.setMessage("Cập nhật thông tin khách hàng thành công !");
		} else {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Cập nhật thông tin khách hàng thất bại, vui lòng kiểm tra lại!");
		}

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

		// id khach hàng
		Integer id;
		String idStr = request.getParameter("idCustomerSearch");
		if (idStr == null) {
			idStr = "";
		}
		if (idStr.isEmpty()) {
			id = 0;
		} else {
			id = Integer.parseInt(idStr);
		}
		// ho, ten, diachi,...
		String infor = request.getParameter("inforCustomerSearch");
		if (infor == null) {
			infor = "";
		}
		// gioi tinh và đã gd
		String gioiTinh = "";
		String maGioiTinh = request.getParameter("gioiTinhSearch");
		if (maGioiTinh != null) {
			if (!maGioiTinh.isEmpty()) {
				if (Integer.parseInt(maGioiTinh) == 1) {
					gioiTinh = "Nữ";
				} else if (Integer.parseInt(maGioiTinh) == 0) {
					gioiTinh = "Nam";
				}
			}
		} else {
			maGioiTinh = "";
		}

		Boolean daGiaoDich = null;
		String maGiaoDich = request.getParameter("daMuaHangSearch");
		if (maGiaoDich != null) {
			if (!maGiaoDich.isEmpty()) {
				if (Integer.parseInt(maGiaoDich) == 1) {
					daGiaoDich = true;
				} else if (Integer.parseInt(maGiaoDich) == 0) {
					daGiaoDich = false;
				}
			}
		} else {
			maGiaoDich = "";
		}

		int page = ServletRequestUtils.getIntParameter(request, "p", 0);
		List<KhachHangEntity> customers = this.getCustomers(page);
		long pageCount = 0;
		if (id == 0 && infor.isEmpty() && maGioiTinh.isEmpty() && daGiaoDich == null) {
			customers = this.getCustomers(page);
			pageCount = this.getPageCount();
		} else {
			customers = this.findCustomers(id, infor, gioiTinh, daGiaoDich, page);
			pageCount = this.getPageCount(id, infor, maGioiTinh, daGiaoDich);
		}

		PagedListHolder pagedListHolder = new PagedListHolder(customers);
		int totalPage = ((int) pageCount) / pageSize;
		if (pageCount % pageSize != 0) {
			totalPage += 1;
		}

		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("inforInput", infor);
		model.addAttribute("idInput", id);
		model.addAttribute("gioiTinhInput", maGioiTinh);
		model.addAttribute("daGiaoDichInput", maGiaoDich);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);

		model.addAttribute("webMessage", webMessage);
		model.addAttribute("activeMapping", "sidebar-khachhang");

		if (errorCode == 0) {
			model.addAttribute("khachHang", this.getCustomerByID(khachHang.getId()));
			return "customer/customerUpdating";
		}
		model.addAttribute("khachHang", khachHang);

		return "customer/customerManage";
	}

	// chinh sua KH
	@Transactional
	@RequestMapping(value = "/update/{id}.htm")
	public String goToUpdatingView(ModelMap model, @Validated @ModelAttribute("khachHang") KhachHangEntity khachHang,
			BindingResult errors, @PathVariable("id") int id, Principal principal) {
		KhachHangEntity tempCustomer = this.getCustomerByID(id);
		List<HoaDonEntity> hoaDon = this.getHoaDonCuaKH(id);
		model.addAttribute("khachHang", tempCustomer);
		model.addAttribute("hoaDon", hoaDon);

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
		model.addAttribute("activeMapping", "sidebar-khachhang");

		return "customer/customerUpdating";
	}

	// Xoa khach hang khoi danh sach
	@Transactional
	@RequestMapping(value = "/delete/{id}.htm")
	public String deleteCustomerPage(@ModelAttribute("khachHang") KhachHangEntity khachHang, BindingResult errors,
			ModelMap model, @PathVariable(value = "id") int id, HttpSession session2) throws ParseException {
		Session session = factory.openSession();
		int errorCode;
		KhachHangEntity customerDelete = (KhachHangEntity) session.get(KhachHangEntity.class, id);
		Transaction t = session.beginTransaction();
		try {
			session.delete(customerDelete);
			t.commit();
			errorCode = 1;
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
			errorCode = 0;
		} finally {
			session.close();
		}

		WebMessage webMessage = new WebMessage();

		if (errorCode != 0) {
			webMessage.setMessageType("Thành công");
			webMessage.setMessage("Xóa khách hàng thành công !");
		} else {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Xóa khách hàng thất bại !");
		}
		session2.setAttribute("webMessage", webMessage);
		return "redirect:/customer/index2.htm";
	}

}