package ptit.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import ptit.bean.UploadFile;
import ptit.definedEntity.TinhTrang;
import ptit.definedEntity.WebMessage;
import ptithcm.Entity.HoaDonEntity;
import ptithcm.Entity.LoaiEntity;
import ptithcm.Entity.NhanVienEntity;
import ptithcm.Entity.PhieuNhapEntity;
import ptithcm.Entity.RoleEntity;
import ptithcm.Entity.TaiKhoanEntity;

@Controller
@RequestMapping("employee/")
@Transactional
public class EmployeeController {
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@InitBinder
	public void customizeBinding(WebDataBinder binder) {
		df.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
	}

	@Autowired
	SessionFactory factory;

	@Autowired
	@Qualifier("uploadfile")
	UploadFile baseUploadFile;

	@Autowired
	ServletContext application;

	// Pagination Variables
	int maxLinkedPage = 3;
	int pageSize = 5;

	// Tim nhan vien theo username
	public NhanVienEntity getStaffByUsername(String username) {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhanVienEntity nv WHERE nv.taikhoan.username = :username";
		Query query = session.createQuery(hql);
		query.setParameter("username", username);
		NhanVienEntity nv = (NhanVienEntity) query.uniqueResult();
		return nv;
	}

	// Tim nhan vien theo id
	public NhanVienEntity getStaffByID(int id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhanVienEntity nv WHERE nv.ID_NV = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		NhanVienEntity nv = (NhanVienEntity) query.uniqueResult();
		return nv;
	}

	// Lay tat ca nhan vien o trang cu the
	public List<NhanVienEntity> getNhanVien(int pageAt) {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhanVienEntity nv ORDER BY nv.ID_NV DESC";
		Query query = session.createQuery(hql);
		List<NhanVienEntity> list = query.setFirstResult(pageAt * pageSize).setMaxResults(pageSize).list();
		return list;
	}

	// tim so phan trang
	public long getPageCount() {
		Session session = factory.getCurrentSession();
		String hql = "select count(*) FROM NhanVienEntity";
		Query query = session.createQuery(hql);
		long count = (long) query.uniqueResult();
		return count;
	}

	// tim nhan vien theo filter
	public List<NhanVienEntity> findEmployees(Integer id, String infor, String maGioiTinh, Boolean daGiaoDich,
			Integer pageAt) {
		Session session = factory.getCurrentSession();
		int countPara = -1;
		String hql = "FROM NhanVienEntity nv WHERE ";
		String[] hqlString = new String[4];
		for (int i = 0; i < hqlString.length; i++) {
			hqlString[i] = "";
		}
		if (id != 0) {
			hqlString[0] = "nv.ID_NV = :id";
			countPara++;
		}
		if (!infor.isEmpty()) {
			hqlString[1] = "nv.Ten LIKE :infor OR nv.Ho LIKE :infor OR nv.sdt LIKE :infor OR nv.diaChi LIKE :infor";
			countPara++;
		}
		if (!maGioiTinh.isEmpty()) {
			hqlString[2] = "nv.gioiTinh LIKE :maGioiTinh";
			countPara++;
		}
		if (daGiaoDich != null) {
			hqlString[3] = "nv.daGiaoDich = :daGiaoDich";
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
		List<NhanVienEntity> list = query.setFirstResult(pageAt * pageSize).setMaxResults(pageSize).list();
		return list;

	}

	public long getPageCount(Integer id, String infor, String maGioiTinh, Boolean daGiaoDich) {
		Session session = factory.getCurrentSession();
		int countPara = -1;
		String hql = "select count(*) FROM NhanVienEntity nv WHERE ";
		String[] hqlString = new String[4];
		for (int i = 0; i < hqlString.length; i++) {
			hqlString[i] = "";
		}
		if (id != 0) {
			hqlString[0] = "nv.id = :id";
			countPara++;
		}
		if (!infor.isEmpty()) {
			hqlString[1] = "nv.Ten LIKE :infor OR nv.Ho LIKE :infor OR nv.sdt LIKE :infor OR nv.diaChi LIKE :infor";
			countPara++;
		}
		if (!maGioiTinh.isEmpty()) {
			hqlString[2] = "nv.gioiTinh LIKE :maGioiTinh";
			countPara++;
		}
		if (daGiaoDich != null) {
			hqlString[3] = "nv.daGiaoDich = :daGiaoDich";
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

	// Trang chu Quan li SP
	@Transactional
	@RequestMapping("index")
	public String product(HttpServletRequest request, Model model, Principal principal,
			@ModelAttribute("nhanVien") NhanVienEntity nhanVien, HttpSession session) {
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
		List<NhanVienEntity> employees = this.getNhanVien(page);
		long pageCount = 0;
		if (id == 0 && infor.isEmpty() && maGioiTinh.isEmpty() && daGiaoDich == null) {
			employees = this.getNhanVien(page);
			pageCount = this.getPageCount();
		} else {
			employees = this.findEmployees(id, infor, gioiTinh, daGiaoDich, page);
			pageCount = this.getPageCount(id, infor, maGioiTinh, daGiaoDich);
		}

		PagedListHolder pagedListHolder = new PagedListHolder(employees);
		int totalPage = ((int) pageCount) / pageSize;
		if (pageCount % pageSize != 0) {
			totalPage += 1;
		}

		session.removeAttribute("webMessage");
		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("inforInput", infor);
		model.addAttribute("idInput", id);
		model.addAttribute("gioiTinhInput", maGioiTinh);
		model.addAttribute("daGiaoDichInput", maGiaoDich);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);

		model.addAttribute("activeMapping", "sidebar-nhanvien");
		model.addAttribute("btnStatus", "btnAdd");
		model.addAttribute("btnContent", "Thêm mới");
		model.addAttribute("disabled", "disabledInputQuit");
		return "employee/employee";
	}

	// Trang chu Quan li SP2
	@Transactional
	@RequestMapping("index2")
	public String product2(HttpServletRequest request, Model model, Principal principal,
			@ModelAttribute("nhanVien") NhanVienEntity nhanVien, HttpSession session) {
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
		List<NhanVienEntity> employees = this.getNhanVien(page);
		long pageCount = 0;
		if (id == 0 && infor.isEmpty() && maGioiTinh.isEmpty() && daGiaoDich == null) {
			employees = this.getNhanVien(page);
			pageCount = this.getPageCount();
		} else {
			employees = this.findEmployees(id, infor, gioiTinh, daGiaoDich, page);
			pageCount = this.getPageCount(id, infor, maGioiTinh, daGiaoDich);
		}

		PagedListHolder pagedListHolder = new PagedListHolder(employees);
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

		model.addAttribute("activeMapping", "sidebar-nhanvien");
		model.addAttribute("btnStatus", "btnAdd");
		model.addAttribute("btnContent", "Thêm mới");
		model.addAttribute("disabled", "disabledInputQuit");
		return "employee/employee";
	}

	// Them nhan vien tu form
	@Transactional
	@RequestMapping(value = "/actions", params = "btnAdd")
	public String addCustomer(HttpServletRequest request, ModelMap model,
			@Validated @ModelAttribute("nhanVien") NhanVienEntity nhanVien, BindingResult errors, HttpSession session,
			Principal principal) {
		nhanVien.setDaGiaoDich(false);
		nhanVien.setDaNghi(false);
		Integer errorCode = this.insertEmployee(nhanVien);
		WebMessage webMessage = new WebMessage();

		if (errorCode != 0) {
			// reset
			nhanVien.reset();
			webMessage.setMessageType("Thành công");
			webMessage.setMessage("Thêm nhân viên thành công !");
		} else {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Thêm nhân viên thất bại !");
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
		List<NhanVienEntity> employees = this.getNhanVien(page);
		long pageCount = 0;
		if (id == 0 && infor.isEmpty() && maGioiTinh.isEmpty() && daGiaoDich == null) {
			employees = this.getNhanVien(page);
			pageCount = this.getPageCount();
		} else {
			employees = this.findEmployees(id, infor, gioiTinh, daGiaoDich, page);
			pageCount = this.getPageCount(id, infor, maGioiTinh, daGiaoDich);
		}

		PagedListHolder pagedListHolder = new PagedListHolder(employees);
		int totalPage = ((int) pageCount) / pageSize;
		if (pageCount % pageSize != 0) {
			totalPage += 1;
		}

		model.addAttribute("nhanVien", nhanVien);
		model.addAttribute("disabled", "disabledInput");
		model.addAttribute("webMessage", webMessage);
		model.addAttribute("activeMapping", "sidebar-nhanvien");
		model.addAttribute("errorsCount", errors.getErrorCount());
		model.addAttribute("btnStatus", "btnAdd");
		model.addAttribute("btnContent", "Thêm mới");

		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("inforInput", infor);
		model.addAttribute("idInput", id);
		model.addAttribute("gioiTinhInput", maGioiTinh);
		model.addAttribute("daGiaoDichInput", maGiaoDich);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);

		return "employee/employee";
	}

	// Khi click nut chinh sua
	@Transactional
	@RequestMapping(value = "/update/{id}.htm")
	public String goToUpdatingView(HttpServletRequest request, ModelMap model,
			@ModelAttribute("nhanVien") NhanVienEntity nhanVien, BindingResult errors, @PathVariable("id") int id,
			Principal principal, HttpSession session) {
		NhanVienEntity nhanVienFounded = this.getStaffByID(id);

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
		Integer idKH;
		String idStr = request.getParameter("idCustomerSearch");
		if (idStr == null) {
			idStr = "";
		}
		if (idStr.isEmpty()) {
			idKH = 0;
		} else {
			idKH = Integer.parseInt(idStr);
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
		List<NhanVienEntity> employees = this.getNhanVien(page);
		long pageCount = 0;
		if (idKH == 0 && infor.isEmpty() && maGioiTinh.isEmpty() && daGiaoDich == null) {
			employees = this.getNhanVien(page);
			pageCount = this.getPageCount();
		} else {
			employees = this.findEmployees(idKH, infor, gioiTinh, daGiaoDich, page);
			pageCount = this.getPageCount(idKH, infor, maGioiTinh, daGiaoDich);
		}

		PagedListHolder pagedListHolder = new PagedListHolder(employees);
		int totalPage = ((int) pageCount) / pageSize;
		if (pageCount % pageSize != 0) {
			totalPage += 1;
		}

		// Vo hieu chuc vu va luong
		if (nhanVienFounded.getDaNghi() == true) {
			model.addAttribute("disableInput", "disable-input");
			model.addAttribute("disableButton", "disable-button");
		}

		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("inforInput", infor);
		model.addAttribute("idInput", idKH);
		model.addAttribute("gioiTinhInput", maGioiTinh);
		model.addAttribute("daGiaoDichInput", maGiaoDich);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);

		session.removeAttribute("webMessage");
		model.addAttribute("nhanVien", nhanVienFounded);
		model.addAttribute("btnStatus", "btnEdit");
		model.addAttribute("btnContent", "Chỉnh sửa");
		model.addAttribute("activeMapping", "sidebar-nhanvien");
		return "employee/employee";
	}

	// Chinh sua nhan vien tu form
	@Transactional
	@RequestMapping(value = "/actions", params = "btnEdit")
	public String updateEmployee(HttpServletRequest request, ModelMap model,
			@Validated @ModelAttribute("nhanVien") NhanVienEntity nhanVien, BindingResult errors, HttpSession session,
			Principal principal) {
		nhanVien.setDaGiaoDich(nhanVien.getDaGiaoDich());
		nhanVien.setDaNghi(nhanVien.getDaNghi());

		TaiKhoanEntity account = this.getStaffByID(nhanVien.getID_NV()).getTaikhoan();

		int errorCode;
		Session sessionDB = factory.openSession();
		Transaction t = sessionDB.beginTransaction();

		try {
			if (account != null) {
				// Neu nhan vien nghi viec, vo hieu hoa tai khoan
				if (nhanVien.getDaNghi() == true && account != null && account.getEnabled() == true) {
					account.setEnabled(false);
				} else {
					account.setEnabled(true);

				}
				account.setConfirmPassword(account.getPassword());
				sessionDB.update(account);
			}
			sessionDB.update(nhanVien);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
			errorCode = 0;
		} finally {
			sessionDB.close();
			errorCode = 1;
		}

		WebMessage webMessage = new WebMessage();

		if (errorCode != 0) {
			// reset
			nhanVien.reset();
			webMessage.setMessageType("Thành công");
			webMessage.setMessage("Cập nhật thông tin nhân viên thành công !");
			model.addAttribute("btnStatus", "btnAdd");
			model.addAttribute("btnContent", "Thêm mới");
		} else {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Cập nhật thông tin nhân viên thất bại, vui lòng kiểm tra lại!");
			model.addAttribute("btnStatus", "btnEdit");
			model.addAttribute("btnContent", "Chỉnh sửa");
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
		Integer idKH;
		String idStr = request.getParameter("idCustomerSearch");
		if (idStr == null) {
			idStr = "";
		}
		if (idStr.isEmpty()) {
			idKH = 0;
		} else {
			idKH = Integer.parseInt(idStr);
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
		List<NhanVienEntity> employees = this.getNhanVien(page);
		long pageCount = 0;
		if (idKH == 0 && infor.isEmpty() && maGioiTinh.isEmpty() && daGiaoDich == null) {
			employees = this.getNhanVien(page);
			pageCount = this.getPageCount();
		} else {
			employees = this.findEmployees(idKH, infor, gioiTinh, daGiaoDich, page);
			pageCount = this.getPageCount(idKH, infor, maGioiTinh, daGiaoDich);
		}

		PagedListHolder pagedListHolder = new PagedListHolder(employees);
		int totalPage = ((int) pageCount) / pageSize;
		if (pageCount % pageSize != 0) {
			totalPage += 1;
		}

		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("inforInput", infor);
		model.addAttribute("idInput", idKH);
		model.addAttribute("gioiTinhInput", maGioiTinh);
		model.addAttribute("daGiaoDichInput", maGiaoDich);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);

		model.addAttribute("nhanVien", nhanVien);
		model.addAttribute("disabled", "disabledInput");
		model.addAttribute("webMessage", webMessage);
		model.addAttribute("activeMapping", "sidebar-nhanvien");

		return "employee/employee";
	}

	// Xoa nhan vien khoi danh sach
	@Transactional
	@RequestMapping(value = "/delete/{id}.htm")
	public String deleteCustomerPage(@ModelAttribute("nhanVien") NhanVienEntity nhanVien, BindingResult errors,
			ModelMap model, @PathVariable(value = "id") int id, HttpSession session2) {
		Session session = factory.openSession();
		WebMessage webMessage = new WebMessage();
		int errorCode;
		NhanVienEntity employeeDelete = (NhanVienEntity) session.get(NhanVienEntity.class, id);
		if(employeeDelete.getTaikhoan() != null) {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Xóa nhân viên thất bại ! Nhân viên đang có tài khoản");
			session2.setAttribute("webMessage", webMessage);
			return "redirect:/employee/index2.htm";
		}
		
		Transaction t = session.beginTransaction();
		try {
			session.delete(employeeDelete);
			t.commit();
			errorCode = 1;
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
			errorCode = 0;
		} finally {
			session.close();
		}

		
		if (errorCode != 0) {
			webMessage.setMessageType("Thành công");
			webMessage.setMessage("Xóa nhân viên thành công !");
		} else {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Xóa nhân viên thất bại !");
		}

		session2.setAttribute("webMessage", webMessage);
		return "redirect:/employee/index2.htm";
	}

	// Them nhan vien
	@Transactional
	public Integer insertEmployee(NhanVienEntity nv) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(nv);
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

	// Update nhan vien
	@Transactional
	public Integer updateEmployeeDB(NhanVienEntity nv) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(nv);
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

	// * QUẢN LÍ TÀI KHOẢN

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	PasswordEncoder passwordEncoder;

	// Lay tat ca cac quyen
	@Transactional
	@ModelAttribute("roles")
	public List<RoleEntity> getLoai() {
		Session session = factory.getCurrentSession();
		String hql = "FROM RoleEntity";
		Query query = session.createQuery(hql);
		List<RoleEntity> list = query.list();
		return list;
	}

	// Lay tat ca hoa don cua nhan vien
	public List<HoaDonEntity> getHoaDonCuaNV(int idNhanVien) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HoaDonEntity hd WHERE hd.nhanVien.ID_NV = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", idNhanVien);
		List<HoaDonEntity> list = query.list();
		return list;
	}

	// Lay tat ca phieu nhap cua nhan vien
	public List<PhieuNhapEntity> getPNCuaNV(int idNhanVien) {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhieuNhapEntity pn WHERE pn.nhanVien.ID_NV = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", idNhanVien);
		List<PhieuNhapEntity> list = query.list();
		return list;
	}

	@RequestMapping(value = "/account/{id}.htm")
	public String accountMainPage(@ModelAttribute("taiKhoan") TaiKhoanEntity taiKhoan, ModelMap model,
			Principal principal, @PathVariable("id") int id) {
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

		NhanVienEntity nhanvienFounded = this.getStaffByID(id);
		// Neu nhan vien co tai khoan
		if (nhanvienFounded.getTaikhoan() != null) {
			model.addAttribute("taiKhoan", nhanvienFounded.getTaikhoan());
			model.addAttribute("hideRegister", "hideRegister");
			TaiKhoanEntity taiKhoanFounded = nhanvienFounded.getTaikhoan();
			taiKhoanFounded.setConfirmPassword(nhanvienFounded.getTaikhoan().getPassword());
			model.addAttribute("taiKhoan", taiKhoanFounded);
			model.addAttribute("nhanVien", nhanvienFounded);
			model.addAttribute("hoaDon", this.getHoaDonCuaNV(nhanvienFounded.getID_NV()));
			model.addAttribute("phieuNhapAll", this.getPNCuaNV(nhanvienFounded.getID_NV()));
			if(nhanvienFounded.getDaNghi() == true)
				model.addAttribute("disableBtn", "disabled-btn");
		} else {
			model.addAttribute("hideAccountForm", "hideAccountForm");
		}
		
		if(nhanvienFounded.getDaNghi() == true) 
			model.addAttribute("disableBtn", "disabledInput");

		model.addAttribute("employee", nhanvienFounded);
		model.addAttribute("activeMapping", "sidebar-nhanvien");
		return "employee/account";
	}

	@RequestMapping(value = "/action/{id}.htm", params = "btnAdd")
	public String registerAccount(@Validated @ModelAttribute("taiKhoan") TaiKhoanEntity taiKhoan, BindingResult errors,
			ModelMap model, HttpSession sessionView, Principal principal, @PathVariable("id") int id) {
		// Ngay thang nam va nhan vien
		WebMessage webMessage = new WebMessage();
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

		// Tim nhan vien
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		String hql = "FROM NhanVienEntity nv WHERE nv.ID_NV = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		NhanVienEntity nhanvienFounded = (NhanVienEntity) query.uniqueResult();

		model.addAttribute("employee", nhanvienFounded);
		model.addAttribute("activeMapping", "sidebar-nhanvien");

		if (!taiKhoan.getConfirmPassword().equals(taiKhoan.getPassword())) {
			model.addAttribute("hideAccountForm", "hideAccountForm");
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Mật khẩu nhập lại không trùng !");
			model.addAttribute("webMessage", webMessage);
			session.close();
			return "employee/account";
		}

		if (this.getStaffByUsername(taiKhoan.getUsername()) != null) {
			model.addAttribute("hideAccountForm", "hideAccountForm");
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Username này đã tồn tại !");
			model.addAttribute("webMessage", webMessage);
			session.close();
			return "employee/account";
		}

		taiKhoan.setEnabled(true);
		taiKhoan.setPassword(passwordEncoder.encode(taiKhoan.getPassword())); // Ma hoa MK
		taiKhoan.setNhanvien(nhanvienFounded);

		int errorCode = 0;
		try {
			session.save(taiKhoan);
			t.commit();
			errorCode = 1;
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
			errorCode = 0;
		} finally {
			session.close();
		}

		if (errorCode != 0) {
			webMessage.setMessageType("Thành công");
			webMessage.setMessage("Cấp tài khoản thành công !");
		} else {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Cấp tài khoản thất bại !");
			model.addAttribute("hideAccountForm", "hideAccountForm");
			model.addAttribute("webMessage", webMessage);
			return "employee/account";
		}
		sessionView.setAttribute("webMessage", webMessage);
		return "redirect:/employee/account/" + id + ".htm";
	}

	@RequestMapping(value = "/action/{id}.htm", params = "btnEdit")
	public String editAccount(@Validated @ModelAttribute("taiKhoan") TaiKhoanEntity taiKhoan, BindingResult errors,
			ModelMap model, HttpSession sessionView, Principal principal, @PathVariable("id") int id) {
		// Ngay thang nam va nhan vien
		WebMessage webMessage = new WebMessage();
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

		// Tim nhan vien
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		String hql = "FROM NhanVienEntity nv WHERE nv.ID_NV = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		NhanVienEntity nhanvienFounded = (NhanVienEntity) query.uniqueResult();
		TaiKhoanEntity taiKhoanGoc = nhanvienFounded.getTaikhoan();

		model.addAttribute("employee", nhanvienFounded);
		model.addAttribute("activeMapping", "sidebar-nhanvien");

		if (this.getStaffByUsername(taiKhoan.getUsername()) != null
				&& !taiKhoanGoc.getUsername().equals(taiKhoan.getUsername())) {
			model.addAttribute("hideRegister", "hideRegister");
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Username này đã tồn tại !");
			model.addAttribute("webMessage", webMessage);
			session.close();
			return "employee/account";
		}

		taiKhoanGoc.setUsername(taiKhoan.getUsername());
		taiKhoanGoc.setRole(taiKhoan.getRole());
		taiKhoanGoc.setEnabled(taiKhoan.getEnabled());
		taiKhoanGoc.setConfirmPassword(taiKhoan.getConfirmPassword());

		int errorCode = 0;
		try {
			session.update(taiKhoanGoc);
			t.commit();
			errorCode = 1;
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
			errorCode = 0;
		} finally {
			session.close();
		}

		if (errorCode != 0) {
			webMessage.setMessageType("Thành công");
			webMessage.setMessage("Sửa tài khoản thành công !");
		} else {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Sửa tài khoản thất bại !");
			model.addAttribute("hideRegister", "hideRegister");
			model.addAttribute("webMessage", webMessage);
			return "employee/account";
		}
		sessionView.setAttribute("webMessage", webMessage);
		return "redirect:/employee/account/" + id + ".htm";
	}
}
