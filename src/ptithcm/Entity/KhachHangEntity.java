package ptithcm.Entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "KHACHHANG")
public class KhachHangEntity {
	@Id
	@GeneratedValue
	@Column(name = "IDKH")
	private Integer id;
	
	@Column(name = "HO")
	@NotBlank(message = "Họ không được để trống")
	private String ho;
	
	@Column(name = "TEN")
	@NotBlank(message = "Tên không được để trống")
	private String ten;
	
	@Column(name = "SDT")
	@NotBlank(message = "SĐT không được để trống")
	private String sdt;
	
	@Column(name = "NGAYSINH")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "Hãy chọn ngày sinh")
	private Date ngaySinh;
	
	@Column(name = "GIOITINH")
	@NotBlank(message = "Giới tính không được để trống")
	private String gioiTinh;
	
	@Column(name = "DIACHI")
	@NotBlank(message = "Địa chỉ không được để trống")
	private String diaChi;
	
	@Column(name = "DAGIAODICH")
	private Boolean daGiaoDich;
	
	@OneToMany(mappedBy = "khachHang", fetch = FetchType.LAZY)
	private Collection<HoaDonEntity> hoaDon;
	
	public KhachHangEntity() {
		super();
	}

	public KhachHangEntity(Integer id, String ho, String ten, String sdt, Date ngaySinh, String gioiTinh, String diaChi,
			Boolean daGiaoDich, Collection<HoaDonEntity> hoaDon) {
		super();
		this.id = id;
		this.ho = ho;
		this.ten = ten;
		this.sdt = sdt;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.diaChi = diaChi;
		this.daGiaoDich = daGiaoDich;
		this.hoaDon = hoaDon;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHo() {
		return ho;
	}

	public void setHo(String ho) {
		this.ho = ho;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public Collection<HoaDonEntity> getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(Collection<HoaDonEntity> hoaDon) {
		this.hoaDon = hoaDon;
	}
	
	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public Boolean getDaGiaoDich() {
		return daGiaoDich;
	}

	public void setDaGiaoDich(Boolean daGiaoDich) {
		this.daGiaoDich = daGiaoDich;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getFullDisplay() {
		return "Mã KH: " + this.getId() + " - " + this.getHo() + " " + this.getTen();
	}
	
	public void reset() {
		this.id = null;
		this.ho = null;
		this.ten = null;
		this.sdt = null;
		this.ngaySinh = null;
		this.gioiTinh = null;
		this.diaChi = null;
		this.daGiaoDich = null;
	}

	@Override
	public String toString() {
		return "KhachHangEntity [id=" + id + ", ho=" + ho + ", ten=" + ten + ", sdt=" + sdt + ", ngaySinh=" + ngaySinh
				+ ", gioiTinh=" + gioiTinh + ", diaChi=" + diaChi + ", daGiaoDich=" + daGiaoDich + ", hoaDon=" + hoaDon
				+ "]";
	}
}
