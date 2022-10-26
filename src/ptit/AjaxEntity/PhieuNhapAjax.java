package ptit.AjaxEntity;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PhieuNhapAjax {
	private Integer id;
	private ArrayList<CTPNAjax> ctpn;
	private BigDecimal vat;
	private BigDecimal tongTien;
	
	public PhieuNhapAjax() {
		super();
	}

	public PhieuNhapAjax(Integer id, ArrayList<CTPNAjax> ctpn, BigDecimal vat, BigDecimal tongTien) {
		super();
		this.id = id;
		this.ctpn = ctpn;
		this.vat = vat;
		this.tongTien = tongTien;
	}

	@Override
	public String toString() {
		return "PhieuNhapAjax [id=" + id + ", ctpn=" + ctpn + ", vat=" + vat + ", tongTien=" + tongTien + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ArrayList<CTPNAjax> getCtpn() {
		return ctpn;
	}

	public void setCtpn(ArrayList<CTPNAjax> ctpn) {
		this.ctpn = ctpn;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public BigDecimal getTongTien() {
		return tongTien;
	}

	public void setTongTien(BigDecimal tongTien) {
		this.tongTien = tongTien;
	}
}
