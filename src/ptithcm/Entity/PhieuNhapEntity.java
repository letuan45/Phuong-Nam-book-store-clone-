package ptithcm.Entity;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "PHIEUNHAP")
public class PhieuNhapEntity {
	@Id
	@GeneratedValue
	@Column(name = "IDPN")
	private Integer ID_PN;
	
	//Foreign keys
	@ManyToOne
	@JoinColumn(name = "IDNV")
	private NhanVienEntity nhanVien;
	
	@OneToMany(mappedBy = "phieuNhap", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	private Collection<CTPNEntity> chiTietPN;
	
	@ManyToOne
	@JoinColumn(name = "IDNCC")
	private NCCEntity nhaCungCap;
	
	//@NotNull(message = "")
	@Column(name = "NGAYLAP")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date ngayLap;
	
	@Column(name = "VAT")
	private BigDecimal tienVAT;
	
	@Column(name = "TONGTIEN")
	private BigDecimal tongTien;
	
	@Column(name = "TINHTRANG")
	private Integer tinhTrang;

	public PhieuNhapEntity() {
		super();
	}

	public PhieuNhapEntity(Integer iD_PN, NhanVienEntity nhanVien, Collection<CTPNEntity> chiTietPN,
			NCCEntity nhaCungCap, Date ngayLap, BigDecimal tienVAT, BigDecimal tongTien,
			Integer tinhTrang) {
		super();
		this.ID_PN = iD_PN;
		this.nhanVien = nhanVien;
		this.chiTietPN = chiTietPN;
		this.nhaCungCap = nhaCungCap;
		this.ngayLap = ngayLap;
		this.tienVAT = tienVAT;
		this.tongTien = tongTien;
		this.tinhTrang = tinhTrang;
	}

	public Integer getID_PN() {
		return ID_PN;
	}

	public void setID_PN(Integer iD_PN) {
		ID_PN = iD_PN;
	}

	public NhanVienEntity getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVienEntity nhanVien) {
		this.nhanVien = nhanVien;
	}

	public Collection<CTPNEntity> getChiTietPN() {
		return chiTietPN;
	}

	public void setChiTietPN(Collection<CTPNEntity> chiTietPN) {
		this.chiTietPN = chiTietPN;
	}

	public NCCEntity getNhaCungCap() {
		return nhaCungCap;
	}

	public void setNhaCungCap(NCCEntity nhaCungCap) {
		this.nhaCungCap = nhaCungCap;
	}

	public Date getNgayLap() {
		return ngayLap;
	}

	public void setNgayLap(Date ngayLap) {
		this.ngayLap = ngayLap;
	}

	public BigDecimal getTienVAT() {
		return tienVAT;
	}

	public void setTienVAT(BigDecimal tienVAT) {
		this.tienVAT = tienVAT;
	}

	public BigDecimal getTongTien() {
		return tongTien;
	}

	public void setTongTien(BigDecimal tongTien) {
		this.tongTien = tongTien;
	}

	public Integer getTinhTrang() {
		return tinhTrang;
	}

	public void setTinhTrang(Integer tinhTrang) {
		this.tinhTrang = tinhTrang;
	}

	@Override
	public String toString() {
		return "PhieuNhapEntity [ID_PN=" + ID_PN + ", nhanVien=" + nhanVien + ", nhaCungCap=" + nhaCungCap
				+ ", ngayLap=" + ngayLap  + ", tienVAT=" + tienVAT + ", tongTien=" + tongTien
				+ ", tinhTrang=" + tinhTrang + "]";
	}
}
