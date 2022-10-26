package ptit.AjaxEntity;

import java.math.BigDecimal;
import java.util.ArrayList;

public class HoaDonAjax {
	private Integer id;
	private BigDecimal vat;
	private ArrayList<CTHDAjax> cthd;
	private BigDecimal tongTien;
	private BigDecimal tienTra;
	
	public HoaDonAjax() {
		super();
	}

	public HoaDonAjax(Integer id, BigDecimal vat, ArrayList<CTHDAjax> cthd, BigDecimal tongTien, BigDecimal tienTra) {
		super();
		this.id = id;
		this.vat = vat;
		this.cthd = cthd;
		this.tongTien = tongTien;
		this.tienTra = tienTra;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public ArrayList<CTHDAjax> getCthd() {
		return cthd;
	}

	public void setCthd(ArrayList<CTHDAjax> cthd) {
		this.cthd = cthd;
	}

	public BigDecimal getTongTien() {
		return tongTien;
	}

	public void setTongTien(BigDecimal tongTien) {
		this.tongTien = tongTien;
	}

	public BigDecimal getTienTra() {
		return tienTra;
	}

	public void setTienTra(BigDecimal tienTra) {
		this.tienTra = tienTra;
	}

	@Override
	public String toString() {
		return "HoaDonAjax [id=" + id + ", vat=" + vat + ", cthd=" + cthd + ", tongTien=" + tongTien + ", tienTra="
				+ tienTra + "]";
	}
}
