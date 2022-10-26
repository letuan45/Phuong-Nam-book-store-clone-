package ptit.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
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
import ptithcm.Entity.KhachHangEntity;
import ptithcm.Entity.NCCEntity;
import ptithcm.Entity.NhanVienEntity;
import ptithcm.Entity.PhieuNhapEntity;
import ptithcm.Entity.VPPEntity;

@Controller
@RequestMapping("supplier")
public class SupplierController {
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
		query.setParameter("id", String.valueOf(id));
		KhachHangEntity nv = (KhachHangEntity) query.uniqueResult();
		return nv;
	}

	public NCCEntity getSupplierByID(String id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM NCCEntity kh WHERE kh.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		NCCEntity nv = (NCCEntity) query.uniqueResult();
		return nv;
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

	// Lay tat ca phieu nhap nha cung cap
	public List<PhieuNhapEntity> getPNCuaNCC(String id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhieuNhapEntity pn WHERE pn.nhaCungCap.ID_NCC = :idNCC";
		Query query = session.createQuery(hql);
		query.setParameter("idNCC", id);
		List<PhieuNhapEntity> list = query.list();
		return list;
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
			query.setParameter("id", String.valueOf(id));
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

	public List<NCCEntity> findSuppliers(String id, String infor, Integer pageAt) {
		Session session = factory.getCurrentSession();
		int countPara = -1;
		String hql = "FROM NCCEntity kh WHERE ";
		String[] hqlString = new String[2];
		for (int i = 0; i < hqlString.length; i++) {
			hqlString[i] = "";
		}
		if (!id.isEmpty()) {
			hqlString[0] = "kh.id LIKE :id";
			countPara++;
		}
		if (!infor.isEmpty()) {
			hqlString[1] = "kh.tenNCC LIKE :infor OR kh.SDT LIKE :infor OR kh.email LIKE :infor OR kh.diaChi LIKE :infor";
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
		if (!id.isEmpty()) {
			query.setParameter("id", "%" + id + "%");
		}
		if (!infor.isEmpty()) {
			query.setParameter("infor", "%" + infor + "%");
		}
		List<NCCEntity> list;
		try {
			list = query.setFirstResult(pageAt * pageSize).setMaxResults(pageSize).list();
		} catch (ClassCastException ex) {
			list = new ArrayList<NCCEntity>();
		}
		return list;

	}

	public List<KhachHangEntity> getCustomers(Integer pageAt) {
		Session session = factory.getCurrentSession();
		String hql = "FROM KhachHangEntity";
		Query query = session.createQuery(hql);
		List<KhachHangEntity> list = query.setFirstResult(pageAt * pageSize).setMaxResults(pageSize).list();
		return list;
	}

	public List<NCCEntity> getSuppliers(Integer pageAt) {
		Session session = factory.getCurrentSession();
		String hql = "FROM NCCEntity";
		Query query = session.createQuery(hql);
		List<NCCEntity> list = query.setFirstResult(pageAt * pageSize).setMaxResults(pageSize).list();
		return list;
	}

	// tim so phan trang
	public long getPageCount() {
		Session session = factory.getCurrentSession();
		String hql = "select count(*) FROM NCCEntity";
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
			query.setParameter("id", String.valueOf(id));
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

	public long getPageCount(String id, String infor) {
		Session session = factory.getCurrentSession();
		int countPara = -1;
		String hql = "select count(*) FROM NCCEntity kh WHERE ";
		String[] hqlString = new String[2];
		for (int i = 0; i < hqlString.length; i++) {
			hqlString[i] = "";
		}
		if (!id.isEmpty()) {
			hqlString[0] = "kh.id LIKE :id";
			countPara++;
		}
		if (!infor.isEmpty()) {
			hqlString[1] = "kh.tenNCC LIKE :infor OR kh.SDT LIKE :infor OR kh.email LIKE :infor OR kh.diaChi LIKE :infor";
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
		if (!id.isEmpty()) {
			query.setParameter("id", "%" + id + "%");
		}
		if (!infor.isEmpty()) {
			query.setParameter("infor", "%" + infor + "%");
		}
		long count;
		try {
			count = (long) query.uniqueResult();
		} catch (ClassCastException ex) {
			count = 0;
		}
		return count;

	}

	// Them khach hang
	@Transactional
	public Integer insertSupplier(NCCEntity kh) {
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

	public Integer updateSupplier(NCCEntity kh) {
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
			@ModelAttribute("supplier") NCCEntity supplier, BindingResult errors, Principal principal) {
		// id khach hàng
		String idStr = request.getParameter("idInput");
		if (idStr == null) {
			idStr = "";
		}
		// email, sdt, ten, diachi,...
		String infor = request.getParameter("inforInput");
		if (infor == null) {
			infor = "";
		}

		int page = ServletRequestUtils.getIntParameter(request, "p", 0);
		List<NCCEntity> suppliers = this.getSuppliers(page);
		long pageCount = 0;
		if (idStr.isEmpty() && infor.isEmpty()) {
			suppliers = this.getSuppliers(page);
			pageCount = this.getPageCount();
		} else {
			suppliers = this.findSuppliers(idStr, infor, page);
			pageCount = this.getPageCount(idStr, infor);
		}

		PagedListHolder pagedListHolder = new PagedListHolder(suppliers);
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
		model.addAttribute("idInput", idStr);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);
		model.addAttribute("activeMapping", "sidebar-nhacungcap");
		return "supplier/supplier";
	}

	@Transactional
	@RequestMapping("index2")
	public String mainCustomer2(HttpServletRequest request, Model model, HttpSession session,
			@ModelAttribute("supplier") NCCEntity supplier, BindingResult errors, Principal principal) {
		// id khach hàng
		String idStr = request.getParameter("idInput");
		if (idStr == null) {
			idStr = "";
		}
		// email, sdt, ten, diachi,...
		String infor = request.getParameter("inforInput");
		if (infor == null) {
			infor = "";
		}

		int page = ServletRequestUtils.getIntParameter(request, "p", 0);
		List<NCCEntity> suppliers = this.getSuppliers(page);
		long pageCount = 0;
		if (idStr.isEmpty() && infor.isEmpty()) {
			suppliers = this.getSuppliers(page);
			pageCount = this.getPageCount();
		} else {
			suppliers = this.findSuppliers(idStr, infor, page);
			pageCount = this.getPageCount(idStr, infor);
		}

		PagedListHolder pagedListHolder = new PagedListHolder(suppliers);
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
		model.addAttribute("idInput", idStr);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);
		model.addAttribute("activeMapping", "sidebar-nhacungcap");
		return "supplier/supplier";
	}

	// tim kiem khach hang
	@Transactional
	@RequestMapping(value = "/index", params = "btnSearch")
	public String searchCustomer(@ModelAttribute("supplier") NCCEntity supplier, BindingResult errors,
			HttpServletRequest request, Model model, HttpSession session) {
		// id khach hàng
		String idStr = request.getParameter("idInput");
		if (idStr == null) {
			idStr = "";
		}
		// email, sdt, ten, diachi,...
		String infor = request.getParameter("inforInput");
		if (infor == null) {
			infor = "";
		}

		int page = ServletRequestUtils.getIntParameter(request, "p", 0);
		List<NCCEntity> suppliers = this.getSuppliers(page);
		long pageCount = 0;
		if (idStr.isEmpty() && infor.isEmpty()) {
			suppliers = this.getSuppliers(page);
			pageCount = this.getPageCount();
		} else {
			suppliers = this.findSuppliers(idStr, infor, page);
			pageCount = this.getPageCount(idStr, infor);
		}

		PagedListHolder pagedListHolder = new PagedListHolder(suppliers);
		int totalPage = ((int) pageCount) / pageSize;
		if (pageCount % pageSize != 0) {
			totalPage += 1;
		}

		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("inforInput", infor);
		model.addAttribute("idInput", idStr);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);
		model.addAttribute("activeMapping", "sidebar-nhacungcap");
		return "supplier/supplier";
	}

	// Them khach hang tu form
	@Transactional
	@RequestMapping(value = "/addSupplier", params = "btnAddSupplier")
	public String addCustomer(HttpServletRequest request, ModelMap model,
			@Validated @ModelAttribute("supplier") NCCEntity supplier, BindingResult errors, HttpSession session,
			Principal principal) {
		Integer errorCode = this.insertSupplier(supplier);
		WebMessage webMessage = new WebMessage();

		if (errorCode != 0) {
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

		String idStr = request.getParameter("idInput");
		if (idStr == null) {
			idStr = "";
		}
		// email, sdt, ten, diachi,...
		String infor = request.getParameter("inforInput");
		if (infor == null) {
			infor = "";
		}

		int page = ServletRequestUtils.getIntParameter(request, "p", 0);
		List<NCCEntity> suppliers = this.getSuppliers(page);
		long pageCount = 0;
		if (idStr.isEmpty() && infor.isEmpty()) {
			suppliers = this.getSuppliers(page);
			pageCount = this.getPageCount();
		} else {
			suppliers = this.findSuppliers(idStr, infor, page);
			pageCount = this.getPageCount(idStr, infor);
		}

		PagedListHolder pagedListHolder = new PagedListHolder(suppliers);
		int totalPage = ((int) pageCount) / pageSize;
		if (pageCount % pageSize != 0) {
			totalPage += 1;
		}

		// reset
		supplier.reset();
		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("inforInput", infor);
		model.addAttribute("idInput", idStr);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);
		model.addAttribute("activeMapping", "sidebar-nhacungcap");
		model.addAttribute("supplier", supplier);
		model.addAttribute("webMessage", webMessage);
		model.addAttribute("errorsCount", errors.getErrorCount());

		return "supplier/supplier";
	}

	// Chinh sua khach hang tu form
	@Transactional
	@RequestMapping(value = "/updateSupplier", params = "btnUpdateSupplier")
	public String updateCustomer(HttpServletRequest request, ModelMap model,
			@Validated @ModelAttribute("supplier") NCCEntity supplier, BindingResult errors, HttpSession session,
			Principal principal) {
		String tempId = supplier.getID_NCC();
		Integer errorCode = this.updateSupplier(supplier);

		WebMessage webMessage = new WebMessage();

		if (errorCode != 0) {
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
		String idStr = request.getParameter("idInput");
		if (idStr == null) {
			idStr = "";
		}
		// email, sdt, ten, diachi,...
		String infor = request.getParameter("inforInput");
		if (infor == null) {
			infor = "";
		}

		int page = ServletRequestUtils.getIntParameter(request, "p", 0);
		List<NCCEntity> suppliers = this.getSuppliers(page);
		long pageCount = 0;
		if (idStr.isEmpty() && infor.isEmpty()) {
			suppliers = this.getSuppliers(page);
			pageCount = this.getPageCount();
		} else {
			suppliers = this.findSuppliers(idStr, infor, page);
			pageCount = this.getPageCount(idStr, infor);
		}

		PagedListHolder pagedListHolder = new PagedListHolder(suppliers);
		int totalPage = ((int) pageCount) / pageSize;
		if (pageCount % pageSize != 0) {
			totalPage += 1;
		}

		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("inforInput", infor);
		model.addAttribute("idInput", idStr);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);

		model.addAttribute("webMessage", webMessage);
		model.addAttribute("activeMapping", "sidebar-nhacungcap");

		if (errorCode == 0) {
			return "supplier/supplierUpdating";
		}
		// reset
		supplier.reset();
		model.addAttribute("supplier", supplier);

		return "supplier/supplier";
	}

	// chinh sua KH
	@Transactional
	@RequestMapping(value = "/update/{id}.htm")
	public String goToUpdatingView(ModelMap model, @Validated @ModelAttribute("supplier") NCCEntity supplier,
			BindingResult errors, @PathVariable("id") String id, Principal principal) {
		NCCEntity tempSupplier = this.getSupplierByID(id);
		model.addAttribute("supplier", tempSupplier);

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
		model.addAttribute("activeMapping", "sidebar-nhacungcap");
		model.addAttribute("phieuNhapAll", this.getPNCuaNCC(id));
		return "supplier/supplierUpdating";
	}

	// Xoa khach hang khoi danh sach
	@Transactional
	@RequestMapping(value = "/delete/{id}.htm")
	public String deleteCustomerPage(@ModelAttribute("supplier") NCCEntity supplier, BindingResult errors,
			ModelMap model, @PathVariable(value = "id") String id, HttpSession session2) throws ParseException {
		Session session = factory.openSession();
		int errorCode;
		NCCEntity supplierDelete = (NCCEntity) session.get(NCCEntity.class, id);
		Transaction t = session.beginTransaction();
		try {
			session.delete(supplierDelete);
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
		return "redirect:/supplier/index2.htm";
	}
}