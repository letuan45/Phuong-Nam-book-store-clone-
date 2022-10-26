package ptit.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ptit.AjaxEntity.CTHDAjax;
import ptit.AjaxEntity.HoaDonAjax;
import ptit.definedEntity.TinhTrang;
import ptit.definedEntity.WebMessage;
import ptithcm.Entity.CTHDEntity;
import ptithcm.Entity.HoaDonEntity;
import ptithcm.Entity.KhachHangEntity;
import ptithcm.Entity.LoaiEntity;
import ptithcm.Entity.NCCEntity;
import ptithcm.Entity.NhanVienEntity;
import ptithcm.Entity.HoaDonEntity;
import ptithcm.Entity.ThuongHieuEntity;
import ptithcm.Entity.VPPEntity;

@Controller
@RequestMapping("invoice/")
public class InvoiceController {
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	SessionFactory factory;

	@Autowired
	ServletContext application;

	// Pagination Variables
	int maxLinkedPage = 3;
	int pageSize = 5;

	Integer idHoaDon = 0;
	private int errorCodeHD = 1;

	@InitBinder
	public void customizeBinding(WebDataBinder binder) {
		df.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
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

	// Tim Chi tiet hoa don theo id
	public CTHDEntity getCTHDById(Integer id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM CTHDEntity cthd WHERE cthd.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		CTHDEntity cthd = (CTHDEntity) query.uniqueResult();
		return cthd;
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

	// Lay tat ca san pham con hang
	@Transactional
	@ModelAttribute("products")
	public List<VPPEntity> getProducts() {
		Session session = factory.getCurrentSession();
		String hql = "FROM VPPEntity vpp WHERE vpp.tinhTrang = 1";
		Query query = session.createQuery(hql);
		List<VPPEntity> list = query.list();
		return list;
	}

	// Lay tat ca khach hang
	@Transactional
	@ModelAttribute("khachhang")
	public List<KhachHangEntity> getKhachHang() {
		Session session = factory.getCurrentSession();
		String hql = "FROM KhachHangEntity";
		Query query = session.createQuery(hql);
		List<KhachHangEntity> list = query.list();
		return list;
	}

	// Lay tat ca hoa don
	@Transactional
	@ModelAttribute("hoaDonAll")
	public List<HoaDonEntity> getHoaDon(Principal principal) {
		// Truy van
		NhanVienEntity nhanvien = this.getStaffByUsername(principal.getName());
		String employeeRole = nhanvien.getTaikhoan().getRole().getRole();

		Session session = factory.getCurrentSession();
		String hql = "FROM HoaDonEntity hd ORDER BY hd.id DESC";
		if (employeeRole.equals("ROLE_STAFF")) {
			hql = "FROM HoaDonEntity hd WHERE hd.nhanVien.ID_NV = :staffID ORDER BY hd.id DESC";
		}
		Query query = session.createQuery(hql);
		if (employeeRole.equals("ROLE_STAFF")) {
			query.setParameter("staffID", nhanvien.getID_NV());
		}
		List<HoaDonEntity> list = query.list();
		return list;
	}

	// Lay tat ca hoa don cua nhan vien
	@Transactional
	public List<HoaDonEntity> getHoaDonOfStaff(int staffID) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HoaDonEntity hd WHERE hd.nhanVien.ID_NV = :staffID";
		Query query = session.createQuery(hql);
		query.setParameter("staffID", staffID);
		List<HoaDonEntity> list = query.list();
		return list;
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

	// Tim khach hang theo id
	public KhachHangEntity getKHById(Integer id) {
		Session session = factory.getCurrentSession();
		KhachHangEntity kh = (KhachHangEntity) session.get(KhachHangEntity.class, id);
		return kh;
	}

	// Tim hoa don theo id
	public HoaDonEntity getHDById(Integer id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HoaDonEntity hd WHERE hd.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		HoaDonEntity hd = (HoaDonEntity) query.uniqueResult();
		return hd;
	}

	// Tim hoa don theo id
	public HoaDonEntity getHDByIdPrinciple(Integer id, Principal principal) {
		NhanVienEntity nhanvien = this.getStaffByUsername(principal.getName());
		String employeeRole = nhanvien.getTaikhoan().getRole().getRole();

		Session session = factory.getCurrentSession();
		String hql = "FROM HoaDonEntity hd WHERE hd.id = :id";
		if (employeeRole.equals("ROLE_STAFF")) {
			hql = hql + " AND hd.nhanVien.ID_NV = :staffID";
		}
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		if (employeeRole.equals("ROLE_STAFF")) {
			query.setParameter("staffID", nhanvien.getID_NV());
		}
		HoaDonEntity hd = (HoaDonEntity) query.uniqueResult();
		return hd;
	}

	// Lay tat ca tinh trang | Khong lay tinh trang ngung kinh doanh
	@Transactional
	@ModelAttribute("tinhtrang")
	public List<TinhTrang> getTinhTrang() {
		List<TinhTrang> list = new ArrayList<>();
		TinhTrang conHang = new TinhTrang(1, "Còn hàng");
		TinhTrang hetHang = new TinhTrang(2, "Hết hàng");

		list.add(conHang);
		list.add(hetHang);
		return list;
	}

	// Tim Phieu theo ngay
	public List<HoaDonEntity> findHoaDon(String fromDate, String toDate, Principal principal) {
		// String fromDate, String toDate
		// Truy van
		NhanVienEntity nhanvien = this.getStaffByUsername(principal.getName());
		String employeeRole = nhanvien.getTaikhoan().getRole().getRole();

		Session session = factory.getCurrentSession();
		String hql = "FROM HoaDonEntity hd WHERE hd.ngayLap >= :fromDate " + "AND hd.ngayLap <= :toDate";
		if (employeeRole.equals("ROLE_STAFF")) {
			hql = hql + " AND hd.nhanVien.ID_NV = :staffID";
		}
		Query query = session.createQuery(hql);
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			date2 = new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		query.setParameter("fromDate", date1);
		query.setParameter("toDate", date2);
		if (employeeRole.equals("ROLE_STAFF")) {
			query.setParameter("staffID", nhanvien.getID_NV());
		}
		List<HoaDonEntity> list = query.list();
		return list;
	}

	@Transactional
	@RequestMapping("index")
	public String index(ModelMap model, HttpSession session, Principal principal,
			@ModelAttribute("khachHang") KhachHangEntity khachHang) {
		session.removeAttribute("webMessage");
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date today = new Date();
		String dateNow = formatter.format(today);
		application.setAttribute("dateNow", dateNow);
		// Truy van
		NhanVienEntity nhanvien = this.getStaffByUsername(principal.getName());
		int tkNhanVien = nhanvien.getTaikhoan().getUSER_ROLE().equals("ROLE_STAFF") ? 1 : 0;

		if (nhanvien.getTaikhoan().getRole().getRole().equals("ROLE_STAFF")) {
			List<HoaDonEntity> hoaDonAll = this.getHoaDonOfStaff(nhanvien.getID_NV());
		}

		application.setAttribute("tkNhanVien", tkNhanVien);
		application.setAttribute("user", nhanvien);
		model.addAttribute("activeMapping", "sidebar-hoadon");

		return "invoice/invoice";
	}

	@Transactional
	@RequestMapping("index2")
	public String index2(ModelMap model, HttpSession session, Principal principal,
			@ModelAttribute("khachHang") KhachHangEntity khachHang) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date today = new Date();
		String dateNow = formatter.format(today);
		application.setAttribute("dateNow", dateNow);

		// Truy van
		NhanVienEntity nhanvien = this.getStaffByUsername(principal.getName());

		int tkNhanVien = nhanvien.getTaikhoan().getUSER_ROLE().equals("ROLE_STAFF") ? 1 : 0;

		application.setAttribute("tkNhanVien", tkNhanVien);
		application.setAttribute("user", nhanvien);
		model.addAttribute("activeMapping", "sidebar-hoadon");

		return "invoice/invoice";
	}

	@Transactional
	@RequestMapping("details")
	public String product(HttpServletRequest request, Model model, Principal principal, ModelMap modelPage,
			@ModelAttribute("VPP") VPPEntity vpp, @ModelAttribute("loai") LoaiEntity loai, HttpSession session) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date today = new Date();
		String dateNow = formatter.format(today);
		application.setAttribute("dateNow", dateNow);

		// Truy van
		NhanVienEntity nhanvien = this.getStaffByUsername(principal.getName());

		int tkNhanVien = nhanvien.getTaikhoan().getUSER_ROLE().equals("ROLE_STAFF") ? 1 : 0;

		application.setAttribute("tkNhanVien", tkNhanVien);
		application.setAttribute("user", nhanvien);

		List<VPPEntity> products = this.getProducts();

		model.addAttribute("products", products);
		model.addAttribute("btnStatus", "btnAdd");
		model.addAttribute("btnContent", "Thêm mới");
		model.addAttribute("activeMapping", "sidebar-hoadon");
		session.removeAttribute("webMessage");

		return "invoice/invoice";
	}

	// Them khach hang
	@Transactional
	@RequestMapping(value = "addClient", method = RequestMethod.POST)
	public String addKhachHang(HttpServletRequest request, ModelMap model, Principal principal,
			@Validated @ModelAttribute("khachHang") KhachHangEntity khachHang, BindingResult errors,
			@ModelAttribute("hoaDonModel") HoaDonEntity hoaDon, HttpSession session) {
		khachHang.setDaGiaoDich(false);
		Integer errorCode = this.insertClient(khachHang);
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

		session.setAttribute("webMessage", webMessage);
		model.addAttribute("activeMapping", "sidebar-hoadon");
		model.addAttribute("errorsCount", errors.getErrorCount());
		if (errors.getErrorCount() > 0) {
			return "invoice/invoice";
		}
		return "redirect:/invoice/index2.htm";
	}

	@Transactional
	@RequestMapping("addInvoice")
	public String createInvoice(HttpServletRequest request, ModelMap model, @ModelAttribute("VPP") VPPEntity product,
			Principal principal, @ModelAttribute("loai") LoaiEntity loai,
			@ModelAttribute("hoaDonModel") HoaDonEntity hoaDon, BindingResult errors,
			@ModelAttribute("khachHang") KhachHangEntity khachHang, HttpSession session) {
		WebMessage webMessage = new WebMessage();
		session.removeAttribute("webMessage");

		String name = request.getParameter("nhanvien");
		String ngayLap = request.getParameter("ngaylap");
		String id = request.getParameter("khachHang");
		Date date = null;
		try {
			date = new SimpleDateFormat("dd-MM-yyyy").parse(ngayLap);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		KhachHangEntity khachHangFounded;
		if (id != "")
			khachHangFounded = this.getKHById(Integer.parseInt(id));
		else
			khachHangFounded = null;

		String idDisplay;
		String hoTenKH;
		if (khachHangFounded != null) {
			idDisplay = id;
			hoTenKH = khachHangFounded.getHo() + " " + khachHangFounded.getTen();
		} else {
			idDisplay = "ID none";
			hoTenKH = "Khách lẻ";
		}
		// System.out.println(khachHangFounded.toString());
		NhanVienEntity nhanvien = this.getStaffByUsername(principal.getName());
		hoaDon.setNhanVien(nhanvien);
		hoaDon.setNgayLap(date);
		hoaDon.setKhachHang(khachHangFounded);
		hoaDon.setTinhTrang(1);

		Integer errorCode = this.insertInvoiceDB(hoaDon);
		if (errorCode != 0) {
			webMessage.setMessageType("Thành công");
			webMessage.setMessage("Thêm hóa đơn tạm thành công !");
		} else {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Thêm hóa đơn tạm thất bại !");
			return "invoice/invoiceDT";
		}

		model.addAttribute("idHoaDon", idHoaDon);
		model.addAttribute("idKhachHang", idDisplay);
		model.addAttribute("tenKhachHang", hoTenKH);
		model.addAttribute("activeMapping", "sidebar-hoadon");

		return "redirect:/invoice/index/" + hoaDon.getId() + ".htm?linkEdit";
	}

	@Transactional
	@RequestMapping(value = "index/{id}.htm", params = "linkEdit")
	public String productLinkEdit(HttpServletRequest request, Model model, Principal principal,
			@ModelAttribute("VPP") VPPEntity vpp, @ModelAttribute("loai") LoaiEntity loai,
			@ModelAttribute("khachHang") KhachHangEntity khachHang, BindingResult errors,
			@ModelAttribute("hoaDonModel") HoaDonEntity hoaDon, HttpSession session, @PathVariable("id") Integer id) {
		// Xoa webMessage
		if (errorCodeHD != 3 && errorCodeHD != 4)
			session.removeAttribute("webMessage");

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date today = new Date();
		String dateNow = formatter.format(today);
		application.setAttribute("dateNow", dateNow);

		// Truy van
		NhanVienEntity nhanvien = this.getStaffByUsername(principal.getName());

		int tkNhanVien = nhanvien.getTaikhoan().getUSER_ROLE().equals("ROLE_STAFF") ? 1 : 0;

		application.setAttribute("tkNhanVien", tkNhanVien);
		application.setAttribute("user", nhanvien);

		hoaDon = this.getHDById(id);
		String idDisplay;
		String hoTenKH;
		if (hoaDon.getKhachHang() == null) {
			idDisplay = "ID none";
			hoTenKH = "Khách lẻ";
		} else {
			idDisplay = hoaDon.getKhachHang().getId() + "";
			hoTenKH = hoaDon.getKhachHang().getHo() + " " + hoaDon.getKhachHang().getTen();
		}
		model.addAttribute("idHoaDon", hoaDon.getId());
		model.addAttribute("idHoaDon", id);
		model.addAttribute("idKhachHang", idDisplay);
		model.addAttribute("tenKhachHang", hoTenKH);

		// Lay tat ca CTPN
		List<CTHDEntity> cthd = (List<CTHDEntity>) hoaDon.getChiTietHD();
		model.addAttribute("cacCTHD", cthd);
		model.addAttribute("hoaDonModel", hoaDon);
		model.addAttribute("deleteCTBtn", "deleteCTBtn");
		model.addAttribute("activeMapping", "sidebar-hoadon");

		// Neu nhu phieu nhap bi huy, tra hang, khoa cac chuc nang nhat dinh
		if (hoaDon.getTinhTrang() == 3 || hoaDon.getTinhTrang() == 4 || (tkNhanVien == 1 && hoaDon.getTinhTrang() != 1)) {
			model.addAttribute("lockBtn", "lockBtn");
			model.addAttribute("lockInput", "lockInput");
			model.addAttribute("displayNone", "d-none");
		}
		
		//Render san pham ao
		List<Integer> soLuongTon = new ArrayList<Integer>();
		int idSanPham;
		for(int i = 0; i < cthd.size(); i++) {
			idSanPham = cthd.get(i).getVanPhongPham().getID_VPP();
			soLuongTon.add(this.getProductByID(idSanPham).getSoLuong());
		}
		
		model.addAttribute("soLuongTon", soLuongTon);
		return "invoice/invoiceDT";
	}

	// Check CTHD co nam trong mang data ?
	public Boolean CheckExistCTHD(ArrayList<CTHDAjax> dataCTHD, CTHDEntity cthd) {
		for (int i = 0; i < dataCTHD.size(); i++) {
			if (dataCTHD.get(i).getId() == cthd.getVanPhongPham().getID_VPP()) {
				return true;
			}
		}
		return false;
	}

	// Check CTPNdata co nam trong mang TCHD ?
	public Boolean CheckExistCTHDData(ArrayList<CTHDEntity> cthdArrExist, CTHDAjax cthd) {
		for (int i = 0; i < cthdArrExist.size(); i++) {
			if (cthdArrExist.get(i).getVanPhongPham().getID_VPP() == cthd.getId()) {
				return true;
			}
		}
		return false;
	}

	// Them chi tiet hoa don
	@Transactional
	@RequestMapping(value = "get-cthd", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getHoaDon(@RequestBody HoaDonAjax data) throws InterruptedException {
		HoaDonEntity hoaDon = this.getHDById(data.getId());

		if (data.getCthd().size() > 0) {
			hoaDon.setTinhTrang(2); // set tinh trang da thanh toan
			hoaDon.setTienVAT(data.getVat());
			hoaDon.setTongTien(data.getTongTien());
			hoaDon.setTienTra(data.getTienTra());
			hoaDon.getNhanVien().setDaGiaoDich(true);
			if (hoaDon.getKhachHang() != null)
				hoaDon.getKhachHang().setDaGiaoDich(true);
		} else if (hoaDon.getChiTietHD().size() == 0) {
			hoaDon.setTinhTrang(1);
		}

		// ADD CTPN
		Collection<CTHDEntity> cthdArr = new ArrayList<CTHDEntity>();

		// CTPN da co trong phieu nhap
		ArrayList<CTHDEntity> cthdArrExist = new ArrayList<CTHDEntity>(hoaDon.getChiTietHD());
		boolean updated = false;
		int countUpdate = 0;
		int indexCT;
		List<Integer> indexes = new ArrayList<Integer>();
		if(cthdArrExist.size() > 0 && cthdArrExist.size() > data.getCthd().size()) {
			for(int i = 0; i < cthdArrExist.size(); i++) {
				if(i <= data.getCthd().size()-1 && 
					CheckExistCTHD(data.getCthd(), cthdArrExist.get(i))) {
					indexes.add(i);
				}
				if(CheckExistCTHD(data.getCthd(), cthdArrExist.get(i)) && indexes.indexOf(i) < 0) {
					indexes.add(i);
				}
			}
		}
		
		for (int i = 0; i < data.getCthd().size(); i++) {
			// Cap nhat CTHD
			if (i < cthdArrExist.size() && cthdArrExist.size() > 0
					&& CheckExistCTHDData(cthdArrExist, data.getCthd().get(i))) {
				indexCT = i;
				if(indexes != null && indexes.size() > 0) {
					indexCT = indexes.get(i);
				}
				
				CTHDEntity CTTonTai = this.getCTHDById(cthdArrExist.get(indexCT).getId());
				VPPEntity product = this.getProductByID(CTTonTai.getVanPhongPham().getID_VPP());		
				if (product.getSoLuong() < data.getCthd().get(i).getQuantity()) {
					errorCodeHD = 4;
					return "get error";
				}			
				if (CTTonTai.getSoLuong() != data.getCthd().get(i).getQuantity()) {
					CTTonTai.setSoLuong(data.getCthd().get(i).getQuantity());
				}
				countUpdate++;
				updated = true;
			} else {
				// Tim san pham bang id
				VPPEntity product = this.getProductByID(data.getCthd().get(i).getId());
				// Them Chi tiet hoa don
				CTHDEntity cthd = new CTHDEntity(hoaDon, product, data.getCthd().get(i).getQuantity(),
						product.getGiaBan());
				if(product.getSoLuong() < cthd.getSoLuong()) {
					errorCodeHD = 4;
					return "get error";
				}
				cthdArr.add(cthd);
				//Neu sua hoa don
				if(cthdArrExist.size() > 0)
					hoaDon.getChiTietHD().add(cthd);
			}
		}
		// Xu li xoa CTHD: tim trong cac chi tiet cu
		if (updated == true) {
			if (data.getCthd().size() == 0) {
				return "redirect:/invoice/index2.htm";
			}
			for (int i = 0; i < cthdArrExist.size(); i++) {
				if (!CheckExistCTHD(data.getCthd(), cthdArrExist.get(i))) {
					CTHDEntity cthdDelete = this.getCTHDById(cthdArrExist.get(i).getId());
					hoaDon.getChiTietHD().remove(cthdDelete);
				}
			}
		}
		// Bat loi khong chon san pham nao
		if (updated == false || countUpdate < 1) {
			hoaDon.setChiTietHD(cthdArr);
			if (data.getCthd().size() == 0) {
				errorCodeHD = 3;
				return "get error";
			} else {
				errorCodeHD = 1;
				errorCodeHD = this.insertHoaDonUpdate(hoaDon);
			}
		}

		return "transfered data";
	}

	@Transactional
	@RequestMapping(value = "addHD/{id}.htm", params = "linkHD")
	public String getBackPN(HttpSession session, @ModelAttribute("hoaDonModel") HoaDonEntity hoaDon,
			@ModelAttribute("khachHang") KhachHangEntity khachHang, @ModelAttribute("loai") LoaiEntity loai,
			ModelMap model, @PathVariable("id") Integer id, Principal principal) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date today = new Date();
		String dateNow = formatter.format(today);
		application.setAttribute("dateNow", dateNow);

		// Truy van
		NhanVienEntity nhanvien = this.getStaffByUsername(principal.getName());

		int tkNhanVien = nhanvien.getTaikhoan().getUSER_ROLE().equals("ROLE_STAFF") ? 1 : 0;

		application.setAttribute("tkNhanVien", tkNhanVien);
		application.setAttribute("user", nhanvien);
		model.addAttribute("activeMapping", "sidebar-hoadon");

		WebMessage webMessage = new WebMessage();

		if (errorCodeHD == 1) {
			webMessage.setMessageType("Thành công");
			webMessage.setMessage("Thao tác với hóa đơn thành công !");
		} else if (errorCodeHD == 3) {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Hóa đơn phải có ít nhất 1 chi tiết !");
			session.setAttribute("webMessage", webMessage);
			return "redirect:/invoice/index/" + id + ".htm?linkEdit";
		} else if (errorCodeHD == 4) {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Số lượng tồn không đủ !");
			session.setAttribute("webMessage", webMessage);
			return "redirect:/invoice/index/" + id + ".htm?linkEdit";
		}
		
		else {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Thao tác với hóa đơn thất bại !");
		}
		model.addAttribute("webMessage", webMessage);

		return "invoice/invoice";
	}

	@Transactional
	@RequestMapping(value = "index/{id}.htm", params = "linkCancel")
	public String productLinkCancel(HttpServletRequest request, Model model, Principal principal,
			@ModelAttribute("VPP") VPPEntity vpp, @ModelAttribute("loai") LoaiEntity loai,
			@ModelAttribute("khachHang") KhachHangEntity khachHang, BindingResult errors,
			@ModelAttribute("hoaDonModel") HoaDonEntity hoaDon, HttpSession session, @PathVariable("id") Integer id) {
		session.removeAttribute("webMessage");
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

		hoaDon = this.getHDById(id);

		// Xu li huy phieu
		hoaDon.setTinhTrang(3);
		ArrayList<CTHDEntity> cthdArr = new ArrayList<CTHDEntity>(hoaDon.getChiTietHD());
		for (int i = 0; i < cthdArr.size(); i++) {
			VPPEntity vppFound = this.getProductByID(cthdArr.get(i).getVanPhongPham().getID_VPP());
			vppFound.setSoLuong(vppFound.getSoLuong() + cthdArr.get(i).getSoLuong());
		}

		webMessage.setMessageType("Thành công");
		webMessage.setMessage("Hủy hóa đơn thành công !");
		session.setAttribute("webMessage", webMessage);

		return "redirect:/invoice/index2.htm";
	}

	// Tim kiem
	@Transactional
	@RequestMapping(value = "/actions", params = "btnSearch")
	public String searchPhieuNhap(HttpServletRequest request, @ModelAttribute("hoaDonModel") HoaDonEntity hoaDon,
			@ModelAttribute("khachHang") KhachHangEntity khachHang, ModelMap model, Principal principal) {
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
		model.addAttribute("activeMapping", "sidebar-hoadon");

		// Tim kiem phieu
		String fromDate = request.getParameter("from-date");
		String toDate = request.getParameter("to-date");
		String idPhieu = request.getParameter("maPhieu");

		if (idPhieu != "") {
			HoaDonEntity hoaDonFounded = this.getHDByIdPrinciple(Integer.parseInt(idPhieu), principal);
			List<HoaDonEntity> listFounded = new ArrayList<HoaDonEntity>();
			if(hoaDonFounded != null) {
				listFounded.add(hoaDonFounded);
			}
			if(listFounded.size() != 0) {
				webMessage.setMessageType("Thành công");
				webMessage.setMessage("Tìm hóa đơn thành công !");
			} else {
				webMessage.setMessageType("Thất bại");
				webMessage.setMessage("Không tìm thấy hóa đơn !");
			}
				
			model.addAttribute("webMessage", webMessage);
			model.addAttribute("hoaDonAll", listFounded);
			model.addAttribute("idPhieu", idPhieu);
			model.addAttribute("fromDate", "none");
			model.addAttribute("toDate", "none");
			return "invoice/invoice";
		}

		if (fromDate == "" && toDate == "" && idPhieu == "")
			return "redirect:/invoice/index2.htm";

		if (fromDate != "" && toDate != "") {
			List<HoaDonEntity> listFounded = this.findHoaDon(fromDate, toDate, principal);
			if(listFounded.size() != 0) {
				webMessage.setMessageType("Thành công");
				webMessage.setMessage("Tìm hóa đơn thành công !");
			} else {
				webMessage.setMessageType("Thất bại");
				webMessage.setMessage("Không tìm thấy hóa đơn nào !");
			}	
			model.addAttribute("webMessage", webMessage);
			model.addAttribute("hoaDonAll", listFounded);
			model.addAttribute("idPhieu", "none");
			model.addAttribute("fromDate", fromDate);
			model.addAttribute("toDate", toDate);
		} else {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Hãy cung cấp đủ 2 ngày cần tra cứu !");
			model.addAttribute("webMessage", webMessage);
			return "invoice/invoice";
		}

		return "invoice/invoice";
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

	// Them hoa don
	@Transactional
	public Integer insertInvoiceDB(HoaDonEntity hd) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.save(hd);
			t.commit();
			idHoaDon = hd.getId();
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	// Xoa CT hoa don
	@Transactional
	public Integer deleteCTHD(CTHDEntity ct) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.delete(ct);
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

	// Update phieu nhap
	@Transactional
	public Integer insertHoaDonUpdate(HoaDonEntity hd) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.update(hd);
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
