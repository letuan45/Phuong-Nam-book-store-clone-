package ptit.controller;

import java.math.BigDecimal;
import java.security.Principal;
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
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ptit.AjaxEntity.CTPNAjax;
import ptit.AjaxEntity.PhieuNhapAjax;
import ptit.definedEntity.TinhTrang;
import ptit.definedEntity.WebMessage;
import ptithcm.Entity.CTPNEntity;
import ptithcm.Entity.LoaiEntity;
import ptithcm.Entity.NCCEntity;
import ptithcm.Entity.NhanVienEntity;
import ptithcm.Entity.PhieuNhapEntity;
import ptithcm.Entity.ThuongHieuEntity;
import ptithcm.Entity.VPPEntity;

@Controller
@RequestMapping("category/")
public class CategoryController {
	@Autowired
	SessionFactory factory;

	@Autowired
	ServletContext application;

	// Pagination Variables
	int maxLinkedPage = 3;
	int pageSize = 5;

	Integer idPhieuNhap = 0;

	// Tim san pham theo ID
	public VPPEntity getProductByID(Integer id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM VPPEntity vpp WHERE vpp.idVpp = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		VPPEntity nv = (VPPEntity) query.uniqueResult();
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

	// Tim phieu nhap theo id
	public PhieuNhapEntity getPNById(Integer id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhieuNhapEntity pn WHERE pn.ID_PN = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		PhieuNhapEntity pn = (PhieuNhapEntity) query.uniqueResult();
		return pn;
	}

	// Tim Chi tiet phieu nhap theo id
	public CTPNEntity getCTPNById(Integer id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM CTPNEntity ctpn WHERE ctpn.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		CTPNEntity ctpn = (CTPNEntity) query.uniqueResult();
		return ctpn;
	}

	// Tim nha cung cap theo id
	public NCCEntity getNCCById(String id) {
		Session session = factory.getCurrentSession();
		NCCEntity ncc = (NCCEntity) session.get(NCCEntity.class, id);
		return ncc;
	}

	// Lay tat ca san pham ngoai tru sp da ngung kinh doanh
	@Transactional
	@ModelAttribute("products")
	public List<VPPEntity> getProducts() {
		Session session = factory.getCurrentSession();
		String hql = "FROM VPPEntity vpp WHERE vpp.tinhTrang != 3";
		Query query = session.createQuery(hql);
		List<VPPEntity> list = query.list();
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

	// Lay tat ca Nha cung cap
	@Transactional
	@ModelAttribute("nhacungcap")
	public List<NCCEntity> getNCC() {
		Session session = factory.getCurrentSession();
		String hql = "FROM NCCEntity";
		Query query = session.createQuery(hql);
		List<NCCEntity> list = query.list();
		return list;
	}

	// Lay tat ca phieu nhap
	@Transactional
	@ModelAttribute("phieuNhapAll")
	public List<PhieuNhapEntity> getPhieuNhap() {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhieuNhapEntity pn ORDER BY pn.ID_PN DESC";
		Query query = session.createQuery(hql);
		List<PhieuNhapEntity> list = query.list();
		return list;
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
	public List<PhieuNhapEntity> findPhieuNhap(String fromDate, String toDate) {
		// String fromDate, String toDate
		Session session = factory.getCurrentSession();
		String hql = "FROM PhieuNhapEntity pn WHERE pn.ngayLap >= :fromDate AND pn.ngayLap <= :toDate";
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		query.setParameter("fromDate", date1);
		query.setParameter("toDate", date2);
		List<PhieuNhapEntity> list = query.list();
		return list;
	}

	@Transactional
	@RequestMapping("index")
	public String index(@ModelAttribute("pNhap") PhieuNhapEntity phieunhap,
			@ModelAttribute("nhaCungCap") NCCEntity nhaCungCap, ModelMap model, HttpSession session,
			Principal principal) {
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
		model.addAttribute("activeMapping", "sidebar-phieunhap");

		return "category/category";
	}

	@Transactional
	@RequestMapping("index2")
	public String index2(@ModelAttribute("pNhap") PhieuNhapEntity phieunhap,
			@ModelAttribute("nhaCungCap") NCCEntity nhaCungCap, ModelMap model, Principal principal) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date today = new Date();
		String dateNow = formatter.format(today);
		application.setAttribute("dateNow", dateNow);

		// Truy van
		NhanVienEntity nhanvien = this.getStaffByUsername(principal.getName());

		int tkNhanVien = nhanvien.getTaikhoan().getUSER_ROLE().equals("ROLE_STAFF") ? 1 : 0;

		application.setAttribute("tkNhanVien", tkNhanVien);
		application.setAttribute("user", nhanvien);
		model.addAttribute("activeMapping", "sidebar-phieunhap");

		return "category/category";
	}

	@Transactional
	@RequestMapping("details")
	public String product(HttpServletRequest request, Model model, Principal principal, ModelMap modelPage,
			@ModelAttribute("VPP") VPPEntity vpp, @ModelAttribute("loai") LoaiEntity loai,
			@ModelAttribute("thuongHieu") ThuongHieuEntity thuongHieu,
			@ModelAttribute("nhaCungCap") NCCEntity nhaCungCap,
			@ModelAttribute("phieuNhapModel") PhieuNhapEntity phieuNhap, @ModelAttribute("pNhap") PhieuNhapEntity pn,
			HttpSession session) {
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
		model.addAttribute("activeMapping", "sidebar-phieunhap");
		session.removeAttribute("webMessage");

		return "category/categoryDT";
	}

	@Transactional
	@RequestMapping("addCate")
	public String createCate(HttpServletRequest request, ModelMap model, @ModelAttribute("VPP") VPPEntity product,
			Principal principal, @ModelAttribute("loai") LoaiEntity loai,
			@ModelAttribute("phieuNhapModel") PhieuNhapEntity phieunhap, BindingResult errors,
			@ModelAttribute("nhaCungCap") NCCEntity nhaCungCap,
			@ModelAttribute("thuongHieu") ThuongHieuEntity thuongHieu, HttpSession sessionView) {
		sessionView.removeAttribute("webMessage");

		String name = request.getParameter("nhanvien");
		String ngayLap = request.getParameter("ngaylap");

		Date date = null;
		try {
			date = new SimpleDateFormat("dd-MM-yyyy").parse(ngayLap);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		NCCEntity nhacungcap = this.getNCCById(phieunhap.getNhaCungCap().getID_NCC());
		NhanVienEntity nhanvien = this.getStaffByUsername(principal.getName());
		phieunhap.setNhanVien(nhanvien);
		phieunhap.setNgayLap(date);
		phieunhap.setNhaCungCap(nhacungcap);
		phieunhap.setTinhTrang(1);

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(phieunhap);
			idPhieuNhap = phieunhap.getID_PN();
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
		} finally {
			session.close();
			return "redirect:/category/index/" + phieunhap.getID_PN() + ".htm?linkEdit";
		}

		// return "category/categoryDT";

	}

	// Them nha cung cap
	@Transactional
	@RequestMapping(value = "addNCC", method = RequestMethod.POST)
	public String addNhaCungCap(HttpServletRequest request, ModelMap model, Principal principal,
			@Validated @ModelAttribute("nhaCungCap") NCCEntity nhaCungCap, BindingResult errors,
			@ModelAttribute("phieuNhapModel") PhieuNhapEntity phieuNhap,
			@ModelAttribute("pNhap") PhieuNhapEntity phieunhap, HttpSession session) {

		Integer errorCode = this.insertNCC(nhaCungCap);
		WebMessage webMessage = new WebMessage();

		if (errorCode != 0) {
			webMessage.setMessageType("Thành công");
			webMessage.setMessage("Thêm nhà cung cấp thành công !");
		} else {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Thêm nhà cung cấp thất bại !");
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
		model.addAttribute("activeMapping", "sidebar-phieunhap");
		model.addAttribute("errorsCount", errors.getErrorCount());
		if (errors.getErrorCount() > 0) {
			return "category/category";
		}
		return "redirect:/category/index2.htm";
	}

	// Check CTPN co nam trong mang data ?
	public Boolean CheckExistCTPN(ArrayList<CTPNAjax> dataCTPN, CTPNEntity ctpn) {
		for (int i = 0; i < dataCTPN.size(); i++) {
			if (dataCTPN.get(i).getId() == ctpn.getVanPhongPham().getID_VPP()) {
				return true;
			}
		}
		return false;
	}

	// Check CTPNdata co nam trong mang TCPN ?
	public Boolean CheckExistCTPNData(ArrayList<CTPNEntity> ctpnArrExist, CTPNAjax ctpn) {
		for (int i = 0; i < ctpnArrExist.size(); i++) {
			if (ctpnArrExist.get(i).getVanPhongPham().getID_VPP() == ctpn.getId()) {
				return true;
			}
		}
		return false;
	}

	private int errorCodePN = 1;

	// Them chi tiet phieu nhap
	@Transactional
	@RequestMapping(value = "get-ctpn", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getPhieuNhap(@RequestBody PhieuNhapAjax data) throws InterruptedException {
		PhieuNhapEntity phieuNhap = this.getPNById(data.getId());
		boolean themLanDau = false;
		if (data.getCtpn().size() > 0) {
			phieuNhap.setTinhTrang(2); // set tinh trang da nhap hang
			phieuNhap.setTienVAT(data.getVat());
			phieuNhap.setTongTien(data.getTongTien());
			phieuNhap.getNhaCungCap().setDaGiaoDich(true);
			phieuNhap.getNhanVien().setDaGiaoDich(true);
		} else if (phieuNhap.getChiTietPN().size() == 0) {
			phieuNhap.setTinhTrang(1);
		}
		if (data.getVat() == null) data.setVat(BigDecimal.ZERO);

		// ADD CTPN
		Collection<CTPNEntity> ctpnArr = new ArrayList<CTPNEntity>();
		// CTPN da co trong phieu nhap
		ArrayList<CTPNEntity> ctpnArrExist = new ArrayList<CTPNEntity>(phieuNhap.getChiTietPN());
		boolean updated = false;
		int countUpdate = 0;
		int indexCT;
		List<Integer> indexes = new ArrayList<Integer>();
		if(ctpnArrExist.size() > 0 && ctpnArrExist.size() > data.getCtpn().size()) {
			for(int i = 0; i < ctpnArrExist.size(); i++) {
				if(i <= data.getCtpn().size()-1 && 
					CheckExistCTPN(data.getCtpn(), ctpnArrExist.get(i))) {
					indexes.add(i);
				}
				if(CheckExistCTPN(data.getCtpn(), ctpnArrExist.get(i)) && indexes.indexOf(i) < 0) {
					indexes.add(i);
				}
			}
		}
		
		for (int i = 0; i < data.getCtpn().size(); i++) {
			// Cap nhat CTPN
			if (i < ctpnArrExist.size() && ctpnArrExist.size() > 0
					&& CheckExistCTPNData(ctpnArrExist, data.getCtpn().get(i))) {
				indexCT = i;
				if(indexes != null && indexes.size() > 0) {
					indexCT = indexes.get(i);
				}
				
				CTPNEntity CTTonTai = this.getCTPNById(ctpnArrExist.get(indexCT).getId());
				if (CTTonTai.getSoLuong() != data.getCtpn().get(i).getQuantity()) {
					CTTonTai.setSoLuong(data.getCtpn().get(i).getQuantity());				
				}
				if (CTTonTai.getDonGiaNhap() != data.getCtpn().get(i).getGiaNhap()) {
					CTTonTai.setDonGiaNhap(data.getCtpn().get(i).getGiaNhap());
				}
				countUpdate++;
				updated = true;
			} else {
				// Tim san pham bang id
				VPPEntity product = this.getProductByID(data.getCtpn().get(i).getId());
				// Them Chi tiet phieu nhap
				CTPNEntity ctpn = new CTPNEntity(phieuNhap, product, data.getCtpn().get(i).getQuantity(),
						data.getCtpn().get(i).getGiaNhap());
				ctpnArr.add(ctpn);
				//Neu sua phieu nhap
				if(ctpnArrExist.size() > 0)
					phieuNhap.getChiTietPN().add(ctpn);
			}
		}
		// Xu li xoa CTPN: tim trong cac chi tiet cu
		if (updated == true) {
			if (data.getCtpn().size() == 0) {
				return "redirect:/category/index2.htm";
			}
			for (int i = 0; i < ctpnArrExist.size(); i++) {
				if (!CheckExistCTPN(data.getCtpn(), ctpnArrExist.get(i))) {
					CTPNEntity ctpnDelete = this.getCTPNById(ctpnArrExist.get(i).getId());
					phieuNhap.getChiTietPN().remove(ctpnDelete);
				}
			}
		}
		if (updated == false || countUpdate < 1) {
			phieuNhap.setChiTietPN(ctpnArr);
			// Bat loi khong chon san pham nao
			if (data.getCtpn().size() == 0) {
				errorCodePN = 3;
				return "get error";
			} else {
				errorCodePN = 1;
				errorCodePN = this.insertProductUpdate(phieuNhap);
			}
			//Set nha cung cap
			Session session = factory.openSession();
			for (int i = 0; i < data.getCtpn().size(); i++) {
				String hql = "FROM VPPEntity vpp WHERE vpp.idVpp = :id";
				Query query = session.createQuery(hql);
				query.setParameter("id", data.getCtpn().get(i).getId());
				VPPEntity product = (VPPEntity) query.uniqueResult();
				
				if(product.getNhaCungCap() == null) {
					Transaction t = session.beginTransaction();
					product.setNhaCungCap(phieuNhap.getNhaCungCap());
					try {
						session.update(product);
						t.commit();
					} catch (Exception e) {
						e.printStackTrace();
						t.rollback();
					}	
				}
			}
			session.close();
		}
			
		return "transfered data";
	}

	@Transactional
	@RequestMapping(value = "addPN/{id}.htm", params = "linkPN")
	public String getBackPN(HttpSession session, HttpServletRequest request,
			@ModelAttribute("phieuNhapModel") PhieuNhapEntity phieuNhap, @ModelAttribute("pNhap") PhieuNhapEntity pn,
			@ModelAttribute("nhaCungCap") NCCEntity nhaCungCap, @ModelAttribute("loai") LoaiEntity loai, ModelMap model,
			@PathVariable("id") Integer id, Principal principal) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date today = new Date();
		String dateNow = formatter.format(today);
		application.setAttribute("dateNow", dateNow);

		// Truy van
		NhanVienEntity nhanvien = this.getStaffByUsername(principal.getName());

		int tkNhanVien = nhanvien.getTaikhoan().getUSER_ROLE().equals("ROLE_STAFF") ? 1 : 0;

		application.setAttribute("tkNhanVien", tkNhanVien);
		application.setAttribute("user", nhanvien);
		model.addAttribute("activeMapping", "sidebar-phieunhap");

		WebMessage webMessage = new WebMessage();

		if (errorCodePN == 1) {
			webMessage.setMessageType("Thành công");
			webMessage.setMessage("Thao tác với phiếu nhập thành công !");
		} else if (errorCodePN == 3) {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Phiếu nhập phải có ít nhất 1 chi tiết !");
			session.setAttribute("webMessage", webMessage);
			return "redirect:/category/index/" + id + ".htm?linkEdit";
		} else {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Thao tác với phiếu nhập thất bại !");
		}
		model.addAttribute("webMessage", webMessage);

		return "category/category";
	}

	@Transactional
	@RequestMapping(value = "index/{id}.htm", params = "linkEdit")
	public String productLinkEdit(HttpServletRequest request, Model model, Principal principal,
			@ModelAttribute("VPP") VPPEntity vpp, @ModelAttribute("loai") LoaiEntity loai,
			@ModelAttribute("nhaCungCap") NCCEntity nhaCungCap,
			@ModelAttribute("phieuNhapModel") PhieuNhapEntity phieuNhap, @ModelAttribute("pNhap") PhieuNhapEntity pn,
			HttpSession session, @PathVariable("id") Integer id) {
		// Xoa webMessage
		if (errorCodePN != 3)
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

		phieuNhap = this.getPNById(id);
		model.addAttribute("idPhieuNhap", phieuNhap.getID_PN());
		model.addAttribute("idNhaCungCap", phieuNhap.getNhaCungCap().getID_NCC());
		model.addAttribute("tenNhaCungCap", phieuNhap.getNhaCungCap().getTenNCC());

		// Lay tat ca CTPN
		List<CTPNEntity> ctpn = (List<CTPNEntity>) phieuNhap.getChiTietPN();
		model.addAttribute("cacCTPN", ctpn);
		model.addAttribute("phieuNhapModel", phieuNhap);
		model.addAttribute("idHoaDon", id);
		model.addAttribute("deleteCTBtn", "deleteCTBtn");
		model.addAttribute("activeMapping", "sidebar-phieunhap");

		// Neu nhu phieu nhap bi huy, tra hang, khoa cac chuc nang nhat dinh
		if (phieuNhap.getTinhTrang() == 3 || phieuNhap.getTinhTrang() == 4) {
			model.addAttribute("lockBtn", "lockBtn");
			model.addAttribute("lockInput", "lockInput");
			model.addAttribute("displayNone", "d-none");
		}

		// Lay toan bo san pham ngoai tru ngung kinh doanh, chon nhung san pham het hang
		// hoac co cung nha cung cap voi phieu nhap
		NCCEntity nhaCungCapFilter = phieuNhap.getNhaCungCap();
		Collection<VPPEntity> products = this.getProducts();
		products.removeIf(p -> p.getNhaCungCap() != null && p.getNhaCungCap() != nhaCungCapFilter);
		model.addAttribute("products", products);

		return "category/categoryDT";
	}

	@Transactional
	@RequestMapping(value = "index/{id}.htm", params = "linkCancel")
	public String productLinkCancel(HttpServletRequest request, Model model, Principal principal,
			@ModelAttribute("VPP") VPPEntity vpp, @ModelAttribute("loai") LoaiEntity loai,
			@ModelAttribute("nhaCungCap") NCCEntity nhaCungCap,
			@ModelAttribute("phieuNhapModel") PhieuNhapEntity phieuNhap, @ModelAttribute("pNhap") PhieuNhapEntity pn,
			HttpSession session, @PathVariable("id") Integer id) {
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

		phieuNhap = this.getPNById(id);
		// Xu ly huy phieu
		ArrayList<CTPNEntity> ctpnArr = new ArrayList<CTPNEntity>(phieuNhap.getChiTietPN());
		
		//Kiem tra hop le
		for (int i = 0; i < ctpnArr.size(); i++) {
			VPPEntity vppFound = this.getProductByID(ctpnArr.get(i).getVanPhongPham().getID_VPP());
			if(vppFound.getSoLuong() < ctpnArr.get(i).getSoLuong()) {
				webMessage.setMessageType("Thất bại");
				webMessage.setMessage("Phiếu nhập này tồn tại chi tiết sản phẩm " + vppFound.getID_VPP() + " " + vppFound.getTenVPP()
				+ " có số lượng lớn hơn tồn");
				session.setAttribute("webMessage", webMessage);
				return "redirect:/category/index2.htm";
			}
		}
		//Xu li du lieu
		for (int i = 0; i < ctpnArr.size(); i++) {
			VPPEntity vppFound = this.getProductByID(ctpnArr.get(i).getVanPhongPham().getID_VPP());
			vppFound.setSoLuong(vppFound.getSoLuong() - ctpnArr.get(i).getSoLuong());
			if (vppFound.getSoLuong() == 0)
				vpp.setTinhTrang(2);
		}
		phieuNhap.setTinhTrang(3);
		webMessage.setMessageType("Thành công");
		webMessage.setMessage("Hủy phiếu nhập thành công !");
		session.setAttribute("webMessage", webMessage);

		return "redirect:/category/index2.htm";
	}

	// Tim kiem
	@Transactional
	@RequestMapping(value = "/actions", params = "btnSearch")
	public String searchPhieuNhap(HttpServletRequest request, @ModelAttribute("pNhap") PhieuNhapEntity phieunhap,
			@ModelAttribute("nhaCungCap") NCCEntity nhaCungCap, ModelMap model, Principal principal) {
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
		model.addAttribute("activeMapping", "sidebar-phieunhap");

		// Tim kiem phieu
		String fromDate = request.getParameter("from-date");
		String toDate = request.getParameter("to-date");
		String idPhieu = request.getParameter("maPhieu");

		if (idPhieu != "") {
			PhieuNhapEntity phieuNhapFounded = this.getPNById(Integer.parseInt(idPhieu));
			List<PhieuNhapEntity> listFounded = new ArrayList<PhieuNhapEntity>();
			if (phieuNhapFounded != null) {
				listFounded.add(phieuNhapFounded);
			}
			if (listFounded.size() != 0) {
				webMessage.setMessageType("Thành công");
				webMessage.setMessage("Tìm phiếu nhập thành công !");
			} else {
				webMessage.setMessageType("Thất bại");
				webMessage.setMessage("Không tìm thấy phiếu nhập !");
			}

			model.addAttribute("webMessage", webMessage);
			model.addAttribute("phieuNhapAll", listFounded);
			model.addAttribute("idPhieu", idPhieu);
			model.addAttribute("fromDate", "none");
			model.addAttribute("toDate", "none");
			return "category/category";
		}

		if (fromDate == "" && toDate == "" && idPhieu == "")
			return "redirect:/invoice/index2.htm";

		if (fromDate != "" && toDate != "") {
			List<PhieuNhapEntity> listFounded = this.findPhieuNhap(fromDate, toDate);
			if (listFounded.size() != 0) {
				webMessage.setMessageType("Thành công");
				webMessage.setMessage("Tìm phiếu nhập thành công !");
			} else {
				webMessage.setMessageType("Thất bại");
				webMessage.setMessage("Không tìm thấy phiếu nhập nào !");
			}
			model.addAttribute("webMessage", webMessage);
			model.addAttribute("phieuNhapAll", listFounded);
			model.addAttribute("idPhieu", "none");
			model.addAttribute("fromDate", fromDate);
			model.addAttribute("toDate", toDate);
		} else {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Hãy cung cấp đủ 2 ngày cần tra cứu !");
			model.addAttribute("webMessage", webMessage);
			return "category/category";
		}

		return "category/category";
	}

	// Them phieu nhap
	@Transactional
	public Integer insertProduct(PhieuNhapEntity pn) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.save(pn);
			idPhieuNhap = pn.getID_PN();
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

	// Them CT phieu nhap
	@Transactional
	public Integer insertProductDT(CTPNEntity ctpn) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.save(ctpn);
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
	public Integer insertProductUpdate(PhieuNhapEntity pn) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {			
//			List<CTPNEntity> list = new ArrayList(pn.getChiTietPN());
//			for(int i = 0; i < list.size(); i++) {
//				list.get(i).getVanPhongPham().setNhaCungCap(pn.getNhaCungCap());
//			}
			session.update(pn);
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

	// Update CT phieu nhap
	@Transactional
	public Integer updateCTPN(CTPNEntity ct) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.update(ct);
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

	// Xoa CT phieu nhap
	@Transactional
	public Integer deleteCTPN(CTPNEntity ct) {
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

	// Them nha cung cap
	@Transactional
	public Integer insertNCC(NCCEntity ncc) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.save(ncc);
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
