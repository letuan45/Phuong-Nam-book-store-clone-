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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "HOADON")
public class HoaDonEntity {
	@Id
	@GeneratedValue
	@Column(name = "IDHD")
	private Integer id;
	
	//Foreign keys
	@ManyToOne
	@JoinColumn(name = "IDNV")
	private NhanVienEntity nhanVien;
	
	@OneToMany(mappedBy = "hoaDon", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	private Collection<CTHDEntity> chiTietHD;
	
	@ManyToOne
	@JoinColumn(name = "IDKH")
	private KhachHangEntity khachHang;
	
	@Column(name = "NGAYLAP")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date ngayLap;
	
	@Column(name = "TIENKHACHTRA")
	private BigDecimal tienTra;
	
	@Column(name = "VAT")
	private BigDecimal tienVAT;
	
	@Column(name = "TONGTIEN")
	private BigDecimal tongTien;
	
	@Column(name = "TINHTRANG")
	private Integer tinhTrang;

	public HoaDonEntity() {
		super();
	}

	public HoaDonEntity(Integer id, NhanVienEntity nhanVien, Collection<CTHDEntity> chiTietHD,
			KhachHangEntity khachHang, Date ngayLap, BigDecimal tienTra, BigDecimal tienVAT, BigDecimal tongTien,
			Integer tinhTrang) {
		super();
		this.id = id;
		this.nhanVien = nhanVien;
		this.chiTietHD = chiTietHD;
		this.khachHang = khachHang;
		this.ngayLap = ngayLap;
		this.tienTra = tienTra;
		this.tienVAT = tienVAT;
		this.tongTien = tongTien;
		this.tinhTrang = tinhTrang;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public NhanVienEntity getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVienEntity nhanVien) {
		this.nhanVien = nhanVien;
	}

	public Collection<CTHDEntity> getChiTietHD() {
		return chiTietHD;
	}

	public void setChiTietHD(Collection<CTHDEntity> chiTietHD) {
		this.chiTietHD = chiTietHD;
	}

	public KhachHangEntity getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHangEntity khachHang) {
		this.khachHang = khachHang;
	}

	public Date getNgayLap() {
		return ngayLap;
	}

	public void setNgayLap(Date ngayLap) {
		this.ngayLap = ngayLap;
	}

	public BigDecimal getTienTra() {
		return tienTra;
	}

	public void setTienTra(BigDecimal tienTra) {
		this.tienTra = tienTra;
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
}
