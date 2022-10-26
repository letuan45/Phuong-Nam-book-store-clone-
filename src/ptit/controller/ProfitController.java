package ptit.controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import ptithcm.Entity.HoaDonEntity;
import ptithcm.Entity.PhieuNhapEntity;

@Controller
@RequestMapping("profit/")
@Transactional
public class ProfitController {
	@Autowired
	SessionFactory factory;

	// Lay tat ca hoa don da thanh toan trong thang va nam cu the
	public List<HoaDonEntity> getHoaDonTrongThoiGian(int thang, int nam) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HoaDonEntity hd WHERE MONTH(hd.ngayLap) = :thang "
				+ "AND YEAR(hd.ngayLap) = :nam AND hd.tinhTrang = 2";
		Query query = session.createQuery(hql);
		query.setParameter("thang", thang);
		query.setParameter("nam", nam);
		List<HoaDonEntity> list = query.list();
		return list;
	}

	// Lay tat ca phieu nhap da thanh toan trong thang va nam cu the
	public List<PhieuNhapEntity> getPhieuNhapTrongThoiGian(int thang, int nam) {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhieuNhapEntity pn WHERE MONTH(pn.ngayLap) = :thang "
				+ "AND YEAR(pn.ngayLap) = :nam AND pn.tinhTrang = 2";
		Query query = session.createQuery(hql);
		query.setParameter("thang", thang);
		query.setParameter("nam", nam);
		List<PhieuNhapEntity> list = query.list();
		return list;
	}

	// Tinh tong hoa don cua 1 listHoaDon
	Long getSumTurnover(List<HoaDonEntity> listHoaDon) {
		Long sum = (long) 0;
		for (int i = 0; i < listHoaDon.size(); i++) {
			sum += listHoaDon.get(i).getTongTien().longValue();
		}
		return sum;
	}

	// Tinh tong hoa don cua 1 listPhieuNhap
	Long getSumPN(List<PhieuNhapEntity> listPhieuNhap) {
		Long sum = (long) 0;
		for (int i = 0; i < listPhieuNhap.size(); i++) {
			sum += listPhieuNhap.get(i).getTongTien().longValue();
		}
		return sum;
	}

	@RequestMapping("index")
	public String index(ModelMap model, HttpSession session) {
		int nam = Calendar.getInstance().get(Calendar.YEAR);
		// Array 12 thang, 12 tong loi nhuan
		Long[] profitOnYear = new Long[12];
		for (int i = 0; i < profitOnYear.length; i++) {
			profitOnYear[i] = getSumTurnover(this.getHoaDonTrongThoiGian(i + 1, nam))
					- getSumPN(this.getPhieuNhapTrongThoiGian(i + 1, nam));
		}

		session.removeAttribute("webMessage");
		model.addAttribute("profitOnYear", profitOnYear);
		model.addAttribute("activeMapping", "sidebar-loinhuan");
		model.addAttribute("year", nam);

		return "profit";
	}

	@RequestMapping("search")
	public String index(ModelMap model, HttpServletRequest request) {
		int nam = Integer.parseInt(request.getParameter("yearpicker"));
		// Array 12 thang, 12 tong loi nhuan
		Long[] profitOnYear = new Long[12];
		for (int i = 0; i < profitOnYear.length; i++) {
			profitOnYear[i] = getSumTurnover(this.getHoaDonTrongThoiGian(i + 1, nam))
					- getSumPN(this.getPhieuNhapTrongThoiGian(i + 1, nam));
		}

		model.addAttribute("profitOnYear", profitOnYear);
		model.addAttribute("activeMapping", "sidebar-loinhuan");
		model.addAttribute("year", nam);

		return "profit";
	}
}
