package ptit.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import ptit.definedEntity.WebMessage;
import ptithcm.Entity.HoaDonEntity;

@Controller
@RequestMapping("turnover/")
@Transactional
public class TurnoverController {
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@InitBinder
	public void customizeBinding(WebDataBinder binder) {
		df.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
	}
	
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
	
	// Lay tat ca hoa don da thanh toan trong ngay
	public List<HoaDonEntity> getHoaDonTrongDate(Date date) {
		Session session = factory.getCurrentSession();
		String hql = "FROM HoaDonEntity hd WHERE hd.ngayLap = :date AND hd.tinhTrang = 2";
		Query query = session.createQuery(hql);
		query.setParameter("date", date);
		List<HoaDonEntity> list = query.list();
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

	@RequestMapping("index")
	public String index(ModelMap model, HttpSession session) {
		int nam = Calendar.getInstance().get(Calendar.YEAR);
		// Array 12 thang, 12 tong hoa don
		Long[] turnoverOnYear = new Long[12];
		for (int i = 0; i < turnoverOnYear.length; i++) {
			turnoverOnYear[i] = getSumTurnover(this.getHoaDonTrongThoiGian(i + 1, nam));
		}

		session.removeAttribute("webMessage");
		model.addAttribute("turnoverOnYear", turnoverOnYear);
		model.addAttribute("activeMapping", "sidebar-doanhthu");
		model.addAttribute("year", nam);
		return "turnover";
	}

	@RequestMapping("search")
	public String Search(ModelMap model, HttpServletRequest request, HttpSession session) {
		int nam = Integer.parseInt(request.getParameter("yearpicker"));
		// Array 12 thang, 12 tong hoa don
		Long[] turnoverOnYear = new Long[12];
		for (int i = 0; i < turnoverOnYear.length; i++) {
			turnoverOnYear[i] = getSumTurnover(this.getHoaDonTrongThoiGian(i + 1, nam));
		}
		
		model.addAttribute("turnoverOnYear", turnoverOnYear);
		model.addAttribute("activeMapping", "sidebar-doanhthu");
		model.addAttribute("year", nam);
		return "turnover";
	}
	
	@RequestMapping("search-by-date")
	public String searchByDate(ModelMap model, HttpServletRequest request, HttpSession session) {
		WebMessage webMessage = new WebMessage();
		if(request.getParameter("ngay") == null) {
			webMessage.setMessageType("Thất bại");
			webMessage.setMessage("Hãy chọn ngày tra cứu !");
		}
		String date = request.getParameter("ngay");
		Date dateOnChoose = null;
		try {
			dateOnChoose = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Long turnoverByDay = this.getSumTurnover(this.getHoaDonTrongDate(dateOnChoose));
		
		int nam = Calendar.getInstance().get(Calendar.YEAR);
		// Array 12 thang, 12 tong hoa don
		Long[] turnoverOnYear = new Long[12];
		for (int i = 0; i < turnoverOnYear.length; i++) {
			turnoverOnYear[i] = getSumTurnover(this.getHoaDonTrongThoiGian(i + 1, nam));
		}
		webMessage.setMessageType("Thành công");
		webMessage.setMessage("Tra cứu thành công");
		
		model.addAttribute("webMessage", webMessage);
		model.addAttribute("dateSearch", date);
		model.addAttribute("turnoverByDay", turnoverByDay);
		model.addAttribute("turnoverOnYear", turnoverOnYear);
		model.addAttribute("activeMapping", "sidebar-doanhthu");
		model.addAttribute("year", nam);
		return "turnover";
	}
}
