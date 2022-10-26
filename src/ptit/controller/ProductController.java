package ptit.controller;

import java.io.File;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javassist.expr.NewArray;
import ptit.bean.UploadFile;
import ptit.definedEntity.TinhTrang;
import ptit.definedEntity.WebMessage;
import ptithcm.Entity.LoaiEntity;
import ptithcm.Entity.NhanVienEntity;
import ptithcm.Entity.ThuongHieuEntity;
import ptithcm.Entity.VPPEntity;

@Controller
@RequestMapping("product/")
public class ProductController {
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

	// Tim san pham theo ten
	public VPPEntity getProductByName(String productName) {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhanVienEntity nv WHERE nv.taikhoan.username = :productName";
		Query query = session.createQuery(hql);
		query.setParameter("productName", productName);
		VPPEntity nv = (VPPEntity) query.uniqueResult();
		return nv;
	}

	// Tim san pham theo ID
	public VPPEntity getProductByID(Integer id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM VPPEntity vpp WHERE vpp.idVpp = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		VPPEntity nv = (VPPEntity) query.uniqueResult();
		return nv;
	}

	// Tim san pham
	public List<VPPEntity> findProduct(Integer id, String tenSP, Integer maLoai, Integer maTinhTrang,Boolean sortByQuantity, Integer pageAt) {
		Session session = factory.getCurrentSession();
		int countPara = -1;
		// Session session = factory.getCurrentSession();
		String hql = "FROM VPPEntity vpp WHERE ";
		String[] hqlString = new String[4];
		for (int i = 0; i < hqlString.length; i++) {
			hqlString[i] = "";
		}
		if (id != 0) {
			hqlString[0] = "vpp.idVpp = :id";
			countPara++;
		}

		if (!tenSP.isEmpty()) {
			hqlString[1] = "vpp.tenVPP LIKE :tenSP";
			countPara++;
		}

		if (maLoai != 0) {
			hqlString[2] = "vpp.loai.ID_Loai = :maLoai";
			countPara++;
		}

		if (maTinhTrang != 0) {
			hqlString[3] = "vpp.tinhTrang = :maTinhTrang";
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

		if (sortByQuantity) {
			hql += " ORDER BY vpp.soLuong";
		}
		Query query = session.createQuery(hql);
		if (id != 0) {
			query.setParameter("id", id);
		}

		if (!tenSP.isEmpty()) {
			query.setParameter("tenSP", "%" + tenSP + "%");
		}

		if (maLoai != 0) {
			query.setParameter("maLoai", maLoai);
		}

		if (maTinhTrang != 0) {
			query.setParameter("maTinhTrang", maTinhTrang);
		}
		List<VPPEntity> list = query.setFirstResult(pageAt * pageSize).setMaxResults(pageSize).list();
		return list;
	}

	// Lay tat ca san pham
	public List<VPPEntity> getProducts(Boolean sortByQuantity, Integer pageAt) {
		Session session = factory.getCurrentSession();
		String hql = "FROM VPPEntity";
		if (sortByQuantity) {
			hql = "FROM VPPEntity vpp ORDER BY vpp.soLuong";
		} else {
			hql = "FROM VPPEntity vpp ORDER BY vpp.idVpp DESC";
		}
		Query query = session.createQuery(hql);
		List<VPPEntity> list = query.setFirstResult(pageAt * pageSize).setMaxResults(pageSize).list();
		return list;
	}

	// Lay so page phan trang
	public long getPageCount() {
		Session session = factory.getCurrentSession();
		String hql = "select count(*) FROM VPPEntity";
		Query query = session.createQuery(hql);
		long count = (long) query.uniqueResult();
		return count;
	}

	// Lay so phan trang theo dieu kien
	public long getPageCount(Integer id, String tenSP, Integer maLoai, Integer maTinhTrang) {
		Session session = factory.getCurrentSession();
		int countPara = -1;
		// Session session = factory.getCurrentSession();
		String hqlCount = "select count(*) FROM VPPEntity vpp WHERE ";
		String[] hqlCountString = new String[4];
		for (int i = 0; i < hqlCountString.length; i++) {
			hqlCountString[i] = "";
		}
		if (id != 0) {
			hqlCountString[0] = "vpp.idVpp = :id";
			countPara++;
		}

		if (!tenSP.isEmpty()) {
			hqlCountString[1] = "vpp.tenVPP LIKE :tenSP";
			countPara++;
		}

		if (maLoai != 0) {
			hqlCountString[2] = "vpp.loai.ID_Loai = :maLoai";
			countPara++;
		}

		if (maTinhTrang != 0) {
			hqlCountString[3] = "vpp.tinhTrang = :maTinhTrang";
			countPara++;
		}

		// Neu chi co 1 tham so khong null
		if (countPara == 0) {
			for (int i = 0; i < hqlCountString.length; i++) {
				hqlCount = hqlCount + hqlCountString[i];
			}
		} else { // Neu nhieu hon 1 tham so khong null
			for (int i = 0; i < hqlCountString.length; i++) {
				if (!hqlCountString[i].equals("")) {
					if (countPara > 0)
						hqlCount = hqlCount + hqlCountString[i] + " AND ";
					if (countPara == 0)
						hqlCount = hqlCount + hqlCountString[i];
					countPara--;
				}
			}
		}
		Query queryhqlCount = session.createQuery(hqlCount);
		if (id != 0) {
			queryhqlCount.setParameter("id", id);
		}

		if (!tenSP.isEmpty()) {
			queryhqlCount.setParameter("tenSP", "%" + tenSP + "%");
		}

		if (maLoai != 0) {
			queryhqlCount.setParameter("maLoai", maLoai);
		}

		if (maTinhTrang != 0) {
			queryhqlCount.setParameter("maTinhTrang", maTinhTrang);
		}
		long count = (long) queryhqlCount.uniqueResult();
		System.out.print("first statement. " + count);
		return count;
	}

	// Lay tat ca Loai san pham
	@Transactional
	@ModelAttribute("loaisp")
	public List<LoaiEntity> getLoai() {
		Session session = factory.getCurrentSession();
		String hql = "FROM LoaiEntity";
		Query query = session.createQuery(hql);
		List<LoaiEntity> list = query.list();
		return list;
	}

	// Tim VPP theo id
	@Transactional
	public VPPEntity getProductById(Integer id) {
		Session session = factory.getCurrentSession();
		VPPEntity product = (VPPEntity) session.get(VPPEntity.class, id);
		return product;
	}

	// Lay tat ca Thuong hieu
	@Transactional
	@ModelAttribute("thuonghieu")
	public List<ThuongHieuEntity> getThuongHieu() {
		Session session = factory.getCurrentSession();
		String hql = "FROM ThuongHieuEntity";
		Query query = session.createQuery(hql);
		List<ThuongHieuEntity> list = query.list();
		return list;
	}

	// Lay tat ca tinh trang
	@Transactional
	@ModelAttribute("tinhtrang")
	public List<TinhTrang> getTinhTrang() {
		List<TinhTrang> list = new ArrayList<>();
		TinhTrang conHang = new TinhTrang(1, "Còn hàng");
		TinhTrang hetHang = new TinhTrang(2, "Hết hàng");
		TinhTrang ngungHang = new TinhTrang(3, "Ngừng kinh doanh");

		list.add(conHang);
		list.add(hetHang);
		list.add(ngungHang);
		return list;
	}

	// Trang chu Quan li SP
	@Transactional
	@RequestMapping("index")
	public String product(HttpServletRequest request, Model model, Principal principal, ModelMap modelPage,
			@ModelAttribute("VPP") VPPEntity vpp, @ModelAttribute("loai") LoaiEntity loai,
			@ModelAttribute("thuongHieu") ThuongHieuEntity thuongHieu, HttpSession session) {
		Integer id;
		session.removeAttribute("webMessage");
		String idStr = request.getParameter("idVPP");
		if (idStr == null) {
			idStr = "";
		}
		if (idStr.isEmpty()) {
			id = 0;
		} else {
			id = Integer.parseInt(idStr);
		}
		Integer tinhtrang;
		String maTinhTrangStr = request.getParameter("tinhtrangID");
		if (maTinhTrangStr == null) {
			maTinhTrangStr = "";
		}
		if (maTinhTrangStr.isEmpty()) {
			tinhtrang = 0;
		} else {
			tinhtrang = Integer.parseInt(maTinhTrangStr);
		}
		Integer maloai;
		String loaisp = request.getParameter("loaiID");
		if (loaisp == null) {
			loaisp = "";
		}
		if (loaisp.isEmpty()) {
			maloai = 0;
		} else {
			maloai = Integer.parseInt(loaisp);
		}
		String ten = request.getParameter("tenVPP");
		if (ten == null) {
			ten = "";
		}
		Boolean sortByQuantity;
		String sortByQuanityParam = request.getParameter("sortByQuantity");
		if (sortByQuanityParam == null) {
			sortByQuanityParam = "";
		}
		if (sortByQuanityParam.isEmpty() || Objects.equals(sortByQuanityParam, "0")) {
			sortByQuantity = false;
			sortByQuanityParam = "0";
		} else {
			sortByQuantity = true;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date today = new Date();
		String dateNow = formatter.format(today);
		application.setAttribute("dateNow", dateNow);

		// Truy van
		NhanVienEntity nhanvien = this.getStaffByUsername(principal.getName());

		int tkNhanVien = nhanvien.getTaikhoan().getUSER_ROLE().equals("ROLE_STAFF") ? 1 : 0;

		application.setAttribute("tkNhanVien", tkNhanVien);
		application.setAttribute("user", nhanvien);

		List<VPPEntity> products = new ArrayList<VPPEntity>();
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);

		long pageCount = 0;
		if (id == 0 && ten.isEmpty() && maloai == 0 && tinhtrang == 0) {
			products = this.getProducts(sortByQuantity, page);
			pageCount = this.getPageCount();
		} else {
			products = this.findProduct(id, ten, maloai, tinhtrang,sortByQuantity, page);
			pageCount = this.getPageCount(id, ten, maloai, tinhtrang);
		}

		PagedListHolder pagedListHolder = new PagedListHolder(products);
		int totalPage = ((int) pageCount) / pageSize;
		if (pageCount % pageSize != 0) {
			totalPage += 1;
		}

		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("activeMapping", "sidebar-product");
		model.addAttribute("btnStatus", "btnAdd");
		model.addAttribute("btnContent", "Thêm mới");
		model.addAttribute("tenVPPInput", ten);
		model.addAttribute("idVPPInput", idStr);
		model.addAttribute("loaiInput", loaisp);
		model.addAttribute("tinhtrangInput", maTinhTrangStr);
		model.addAttribute("sortByQuantity", sortByQuanityParam);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);

		return "product";
	}

	// Trang chu Quan li SP v2 (mapping nay khong co session.removeAttr)
	@Transactional
	@RequestMapping("index2")
	public String product2(HttpServletRequest request, Model model, Principal principal, ModelMap modelPage,
			@ModelAttribute("VPP") VPPEntity vpp, BindingResult errors, @ModelAttribute("loai") LoaiEntity loai,
			@ModelAttribute("thuongHieu") ThuongHieuEntity thuongHieu, HttpSession session) {
		Integer id;
		String idStr = request.getParameter("idVPP");
		if (idStr == null) {
			idStr = "";
		}
		if (idStr.isEmpty()) {
			id = 0;
		} else {
			id = Integer.parseInt(idStr);
		}
		Integer tinhtrang;
		String maTinhTrangStr = request.getParameter("tinhtrangID");
		if (maTinhTrangStr == null) {
			maTinhTrangStr = "";
		}
		if (maTinhTrangStr.isEmpty()) {
			tinhtrang = 0;
		} else {
			tinhtrang = Integer.parseInt(maTinhTrangStr);
		}
		Integer maloai;
		String loaisp = request.getParameter("loaiID");
		if (loaisp == null) {
			loaisp = "";
		}
		if (loaisp.isEmpty()) {
			maloai = 0;
		} else {
			maloai = Integer.parseInt(loaisp);
		}
		String ten = request.getParameter("tenVPP");
		if (ten == null) {
			ten = "";
		}
		Boolean sortByQuantity;
		String sortByQuanityParam = request.getParameter("sortByQuantity");
		if (sortByQuanityParam == null) {
			sortByQuanityParam = "";
		}
		if (sortByQuanityParam.isEmpty() || Objects.equals(sortByQuanityParam, "0")) {
			sortByQuantity = false;
			sortByQuanityParam = "0";
		} else {
			sortByQuantity = true;
		}
		

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date today = new Date();
		String dateNow = formatter.format(today);
		application.setAttribute("dateNow", dateNow);

		// Truy van
		NhanVienEntity nhanvien = this.getStaffByUsername(principal.getName());

		int tkNhanVien = nhanvien.getTaikhoan().getUSER_ROLE().equals("ROLE_STAFF") ? 1 : 0;

		application.setAttribute("tkNhanVien", tkNhanVien);
		application.setAttribute("user", nhanvien);

		List<VPPEntity> products = new ArrayList<VPPEntity>();
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);

		long pageCount = 0;
		if (id == 0 && ten.isEmpty() && maloai == 0 && tinhtrang == 0) {
			products = this.getProducts(sortByQuantity, page);
			pageCount = this.getPageCount();
		} else {
			products = this.findProduct(id, ten, maloai, tinhtrang, sortByQuantity, page);
			pageCount = this.getPageCount(id, ten, maloai, tinhtrang);
		}

		PagedListHolder pagedListHolder = new PagedListHolder(products);
		int totalPage = ((int) pageCount) / pageSize;
		if (pageCount % pageSize != 0) {
			totalPage += 1;
		}

		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("activeMapping", "sidebar-product");
		model.addAttribute("btnStatus", "btnAdd");
		model.addAttribute("btnContent", "Thêm mới");
		model.addAttribute("tenVPPInput", ten);
		model.addAttribute("idVPPInput", idStr);
		model.addAttribute("loaiInput", loaisp);
		model.addAttribute("tinhtrangInput", maTinhTrangStr);
		model.addAttribute("sortByQuantity", sortByQuanityParam);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);

		return "product";
	}

	// Them san pham tu form
	@Transactional
	@RequestMapping(value = "/actions", params = "btnAdd")
	public String addProduct(HttpServletRequest request, ModelMap model,
			@Valid @ModelAttribute("VPP") VPPEntity product, BindingResult errors,
			@ModelAttribute("loai") LoaiEntity loai, @ModelAttribute("thuongHieu") ThuongHieuEntity thuongHieu,
			@RequestParam("file") MultipartFile file, Principal principal, HttpSession session) {
		// set tinh trang het hang, so luong = 0
		product.setTinhTrang(2);
		product.setSoLuong(0);
		WebMessage webMessage = new WebMessage();
		String photoPath = "";
		String fileName = "";

		if (file.isEmpty()) {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Vui lòng chọn hình ảnh");

			session.setAttribute("webMessage", webMessage);
			return "redirect:/product/index2.htm";
		} else {
			String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
			fileName = date + file.getOriginalFilename();
			photoPath = baseUploadFile.getBasePath() + File.separator + fileName;
		}

		product.setHinhAnh("resources/images/" + fileName);
		product.setDaGiaoDich(false);
		
		Integer errorCode = this.insertProduct(product);
		if (errorCode != 0) {
			webMessage.setMessageType("Thành công");
			webMessage.setMessage("Thêm sản phẩm thành công !");
			product.resetProduct();
			try {
				file.transferTo(new File(photoPath));
				Thread.sleep(5000);
			} catch (Exception e) {
				webMessage.setMessageType("Thất bại");
				webMessage.setMessage("Lỗi Upload File hình ảnh");
				model.addAttribute("btnStatus", "btnAdd");
				model.addAttribute("btnContent", "Thêm mới");
				model.addAttribute("activeMapping", "sidebar-product");
			}
		} else {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Thêm sản phẩm thất bại !");

		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date today = new Date();
		String dateNow = formatter.format(today);
		application.setAttribute("dateNow", dateNow);

		// Truy van
		NhanVienEntity nhanvien = this.getStaffByUsername(principal.getName());

		int tkNhanVien = nhanvien.getTaikhoan().getUSER_ROLE().equals("ROLE_STAFF") ? 1 : 0;

		application.setAttribute("tkNhanVien", tkNhanVien);
		application.setAttribute("user", nhanvien);
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);

		List<VPPEntity> products = this.getProducts(false, page);
		long pageCount = this.getPageCount();
		int totalPage = ((int) pageCount) / pageSize;
		if (pageCount % pageSize != 0) {
			totalPage += 1;
		}

		PagedListHolder pagedListHolder = new PagedListHolder(products);
		pagedListHolder.setPage(page);
		pagedListHolder.setMaxLinkedPages(maxLinkedPage);
		pagedListHolder.setPageSize(pageSize);

		model.addAttribute("webMessage", webMessage);
		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("activeMapping", "sidebar-product");
		model.addAttribute("btnStatus", "btnAdd");
		model.addAttribute("btnContent", "Thêm mới");
		model.addAttribute("sortByQuantity", 0);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);
		return "product";
	}

	// Sua san pham tu form
	@Transactional
	@RequestMapping(value = "/actions", params = "btnEdit")
	public String editProduct(HttpServletRequest request, ModelMap model,
			@Valid @ModelAttribute("VPP") VPPEntity product, BindingResult errors,
			@ModelAttribute("loai") LoaiEntity loai, @ModelAttribute("thuongHieu") ThuongHieuEntity thuongHieu,
			@RequestParam("file") MultipartFile file, Principal principal, HttpSession session) {
		WebMessage webMessage = new WebMessage();
		String photoPath = "";
		String fileName = "";
		VPPEntity productOld = new VPPEntity();
		productOld = this.getProductByID(product.getID_VPP());

		if (file.isEmpty()) {
			product.setHinhAnh(productOld.getHinhAnh());
		} else {
			String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
			fileName = date + file.getOriginalFilename();
			photoPath = baseUploadFile.getBasePath() + File.separator + fileName;
			product.setHinhAnh("resources/images/" + fileName);
		}

		if (product.getTinhTrang() == null)
			product.setTinhTrang(productOld.getTinhTrang());
		// Default values
		product.setDaGiaoDich(productOld.getDaGiaoDich());
		product.setSoLuong(productOld.getSoLuong());
		product.setNhaCungCap(productOld.getNhaCungCap());

		Integer errorCode = this.updateProduct(product);
		if (errorCode != 0) {
			webMessage.setMessageType("Thành công");
			webMessage.setMessage("Sửa sản phẩm thành công !");
			product.resetProduct();
			if (!file.isEmpty()) {
				try {
					file.transferTo(new File(photoPath));
					Thread.sleep(5000);
				} catch (Exception e) {
					webMessage.setMessageType("Thất bại");
					webMessage.setMessage("Lỗi Upload File hình ảnh");
				}
			}
		} else {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Sửa sản phẩm thất bại !");
		}
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);

		List<VPPEntity> products = this.getProducts(false, page);
		long pageCount = this.getPageCount();
		int totalPage = ((int) pageCount) / pageSize;
		if (pageCount % pageSize != 0) {
			totalPage += 1;
		}
		PagedListHolder pagedListHolder = new PagedListHolder(products);
		pagedListHolder.setPage(page);
		pagedListHolder.setMaxLinkedPages(maxLinkedPage);
		pagedListHolder.setPageSize(pageSize);

		model.addAttribute("webMessage", webMessage);
		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("activeMapping", "sidebar-product");
		model.addAttribute("sortByQuantity", 0);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);
		
		if(errorCode != 0) {
			model.addAttribute("btnStatus", "btnAdd");
			model.addAttribute("btnContent", "Thêm mới");
		} else {
			model.addAttribute("btnStatus", "btnEdit");
			model.addAttribute("btnContent", "Chỉnh sửa");
		}
		return "product";
	}

	// Khi click vao icon chinh sua
	@Transactional
	@RequestMapping(value = "index/{id}.htm", params = "linkEdit")
	public String editOnclick(HttpServletRequest request, ModelMap model, @ModelAttribute("VPP") VPPEntity product,
			@ModelAttribute("loai") LoaiEntity loai, @ModelAttribute("thuongHieu") ThuongHieuEntity thuongHieu,
			@PathVariable("id") Integer id, Principal principal, HttpSession session) {
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);
		List<VPPEntity> products = this.getProducts(false, page);
		long pageCount = this.getPageCount();
		int totalPage = ((int) pageCount) / pageSize;
		if (pageCount % pageSize != 0) {
			totalPage += 1;
		}
		PagedListHolder pagedListHolder = new PagedListHolder(products);
		pagedListHolder.setPage(page);
		pagedListHolder.setMaxLinkedPages(maxLinkedPage);
		pagedListHolder.setPageSize(pageSize);

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
		VPPEntity productFound = this.getProductById(id);
		if(productFound.getTinhTrang() == 3) {
			model.addAttribute("disableButton", "disable-button");
		}
		
		model.addAttribute("VPP", productFound);
		model.addAttribute("activeMapping", "sidebar-product");
		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("btnStatus", "btnEdit");
		model.addAttribute("btnContent", "Chỉnh sửa");
		model.addAttribute("loai", new LoaiEntity());
		model.addAttribute("sortByQuantity", 0);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);
		session.removeAttribute("webMessage");
		return "product";
	}

	// Khi co webMessage
	@Transactional
	@RequestMapping(value = "index2/{id}.htm", params = "linkEdit")
	public String editOnclick2(HttpServletRequest request, ModelMap model,
			@Validated @ModelAttribute("VPP") VPPEntity product, BindingResult errors,
			@ModelAttribute("loai") LoaiEntity loai, @ModelAttribute("thuongHieu") ThuongHieuEntity thuongHieu,
			@PathVariable("id") Integer id, Principal principal, HttpSession session) {
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);
		List<VPPEntity> products = this.getProducts(false, page);
		long pageCount = this.getPageCount();
		int totalPage = ((int) pageCount) / pageSize;
		if (pageCount % pageSize != 0) {
			totalPage += 1;
		}
		PagedListHolder pagedListHolder = new PagedListHolder(products);
		pagedListHolder.setPage(page);
		pagedListHolder.setMaxLinkedPages(maxLinkedPage);
		pagedListHolder.setPageSize(pageSize);

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
		model.addAttribute("VPP", this.getProductById(id));
		model.addAttribute("activeMapping", "sidebar-product");
		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("btnStatus", "btnEdit");
		model.addAttribute("btnContent", "Chỉnh sửa");
		model.addAttribute("loai", new LoaiEntity());
		model.addAttribute("sortByQuantity", 0);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);
		return "product";
	}

	// Khi click vao icon xoa san pham
	@Transactional
	@RequestMapping(value = "index/{id}.htm", params = "linkDelete")
	public String deleteOnclick(HttpServletRequest request, ModelMap model, @ModelAttribute("VPP") VPPEntity product,
			@ModelAttribute("loai") LoaiEntity loai, @ModelAttribute("thuongHieu") ThuongHieuEntity thuongHieu,
			@PathVariable("id") Integer id, Principal principal) {
		WebMessage webMessage = new WebMessage();

		int errorCode;
		Session session = factory.openSession();
		VPPEntity productDelete = (VPPEntity) session.get(VPPEntity.class, id);
		Transaction t = session.beginTransaction();
		try {
			session.delete(productDelete);
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
			webMessage.setMessage("Xóa sản phẩm thành công !");
		} else {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Xóa sản phẩm thất bại !");
		}
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);

		List<VPPEntity> products = this.getProducts(false, page);
		long pageCount = this.getPageCount();
		int totalPage = ((int) pageCount) / pageSize;
		if (pageCount % pageSize != 0) {
			totalPage += 1;
		}
		PagedListHolder pagedListHolder = new PagedListHolder(products);

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
		model.addAttribute("activeMapping", "sidebar-product");
		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("btnStatus", "btnAdd");
		model.addAttribute("btnContent", "Thêm mới");
		model.addAttribute("webMessage", webMessage);
		model.addAttribute("loai", new LoaiEntity());
		model.addAttribute("sortByQuantity", 0);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageAt", page);

		return "product";
	}

	// Them loai san pham
	@Transactional
	@RequestMapping(value = "addType", method = RequestMethod.POST)
	public String addLoai(HttpServletRequest request, ModelMap model,
			@Validated @ModelAttribute("loai") LoaiEntity loai, BindingResult errors, Principal principal,
			@ModelAttribute("VPP") VPPEntity product, @ModelAttribute("thuongHieu") ThuongHieuEntity thuongHieu,
			HttpSession session) {
		Integer errorCode = this.insertLoai(loai);
		WebMessage webMessage = new WebMessage();

		if (errorCode != 0) {
			webMessage.setMessageType("Thành công");
			webMessage.setMessage("Thêm loại sản phẩm thành công !");
		} else {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Thêm loại sản phẩm thất bại !");
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

		session.setAttribute("webMessage", webMessage);
		model.addAttribute("activeMapping", "sidebar-product");
		model.addAttribute("btnStatus", "btnAdd");
		model.addAttribute("btnContent", "Thêm mới");

		return "redirect:/product/index2.htm";
	}

	// Them thuong hieu
	@Transactional
	@RequestMapping(value = "addBrand", method = RequestMethod.POST)
	public String addThuongHieu(HttpServletRequest request, ModelMap model, @ModelAttribute("loai") LoaiEntity loai,
			BindingResult errors, Principal principal, @ModelAttribute("VPP") VPPEntity product,
			@ModelAttribute("thuongHieu") ThuongHieuEntity thuongHieu, HttpSession session) {

		Integer errorCode = this.insertTH(thuongHieu);
		WebMessage webMessage = new WebMessage();

		if (errorCode != 0) {
			webMessage.setMessageType("Thành công");
			webMessage.setMessage("Thêm thương hiệu thành công !");
		} else {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Thêm thương hiệu thất bại !");
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

		session.setAttribute("webMessage", webMessage);
		model.addAttribute("activeMapping", "sidebar-product");
		model.addAttribute("btnStatus", "btnAdd");
		model.addAttribute("btnContent", "Thêm mới");

		return "redirect:/product/index2.htm";
	}

	// Search
	@Transactional
	@RequestMapping(value = "/index", params = "btnSearch")
	public String searchProduct(HttpServletRequest request, ModelMap model, @ModelAttribute("VPP") VPPEntity product,
			BindingResult errors, Principal principal, @ModelAttribute("loai") LoaiEntity loai,
			@ModelAttribute("thuongHieu") ThuongHieuEntity thuongHieu) {
		Integer id;
		String idStr = request.getParameter("idVPP");
		if (idStr.isEmpty()) {
			id = 0;
		} else {
			id = Integer.parseInt(idStr);
		}
		Integer tinhtrang;
		String maTinhTrangStr = request.getParameter("tinhtrang");
		if (maTinhTrangStr.isEmpty()) {
			tinhtrang = 0;
		} else {
			tinhtrang = Integer.parseInt(maTinhTrangStr);
		}
		Integer maloai;
		String loaisp = request.getParameter("loai");
		if (loaisp.isEmpty()) {
			maloai = 0;
		} else {
			maloai = Integer.parseInt(loaisp);
		}

		String ten = request.getParameter("tenVPP");
		
		Boolean sortByQuantity;
		String sortByQuanityParam = request.getParameter("sortByQuantity");
		if (sortByQuanityParam == null) {
			sortByQuanityParam = "";
		}
		if (sortByQuanityParam.isEmpty() || Objects.equals(sortByQuanityParam, "0")) {
			sortByQuantity = false;
			sortByQuanityParam = "0";
		} else {
			sortByQuantity = true;
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date today = new Date();
		String dateNow = formatter.format(today);
		application.setAttribute("dateNow", dateNow);

		// Truy van
		NhanVienEntity nhanvien = this.getStaffByUsername(principal.getName());

		int tkNhanVien = nhanvien.getTaikhoan().getUSER_ROLE().equals("ROLE_STAFF") ? 1 : 0;

		application.setAttribute("tkNhanVien", tkNhanVien);
		application.setAttribute("user", nhanvien);
		List<VPPEntity> products = new ArrayList<VPPEntity>();
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);

		if (id == 0 && ten.isEmpty() && maloai == 0 && tinhtrang == 0) {
			products = this.getProducts(sortByQuantity, page);
		} else {
			products = this.findProduct(id, ten, maloai, tinhtrang,sortByQuantity, page);
		}

		long pageCount = 0;
		if (id == 0 && ten.isEmpty() && maloai == 0 && tinhtrang == 0) {
			products = this.getProducts(sortByQuantity, page);
			pageCount = this.getPageCount();
		} else {
			products = this.findProduct(id, ten, maloai, tinhtrang,sortByQuantity, page);
			pageCount = this.getPageCount(id, ten, maloai, tinhtrang);
		}

		PagedListHolder pagedListHolder = new PagedListHolder(products);
		int totalPage = ((int) pageCount) / pageSize;
		if (pageCount % pageSize != 0) {
			totalPage += 1;
		}

		model.addAttribute("pagedListHolder", pagedListHolder);
		model.addAttribute("activeMapping", "sidebar-product");
		model.addAttribute("tenVPPInput", ten);
		model.addAttribute("idVPPInput", idStr);
		model.addAttribute("loaiInput", loaisp);
		model.addAttribute("tinhtrangInput", maTinhTrangStr);
		model.addAttribute("sortByQuantity", sortByQuanityParam);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("btnStatus", "btnAdd");
		model.addAttribute("btnContent", "Thêm mới");
		model.addAttribute("pageAt", page);
		return "product";
	}

	// Them san pham
	@Transactional
	public Integer insertProduct(VPPEntity vpp) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.save(vpp);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	// Them Loai sp
	@Transactional
	public Integer insertLoai(LoaiEntity loai) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.save(loai);
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

	// Them thuong hieu
	@Transactional
	public Integer insertTH(ThuongHieuEntity th) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.save(th);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	// Update san pham
	@Transactional
	public Integer updateProduct(VPPEntity vpp) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.update(vpp);
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

	// Update xoa pham
	@Transactional
	public Integer deleteProduct(VPPEntity vpp) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.delete(vpp);
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