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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "NHANVIEN")
public class NhanVienEntity {
	@Id
	@GeneratedValue
	@Column(name = "IDNV")
	private Integer ID_NV;
	
	//Foreign Keys
	@OneToMany(mappedBy = "nhanVien", fetch = FetchType.EAGER)
	private Collection<PhieuNhapEntity> phieuNhap;
	
	@Column(name = "CMND")
	@NotBlank(message = "CMND không được để trống")
	private String cmnd;
	
	@Column(name = "HO")
	@NotBlank(message = "Họ không được để trống")
	private String Ho;
	
	@Column(name = "TEN")
	@NotBlank(message = "Tên không được để trống")
	private String Ten;
	
	@Column(name = "SDT")
	@Pattern(regexp = "0[0-9]{9}", message = "SĐT không khả dụng")
	@NotBlank(message = "SĐT không được để trống")
	private String sdt;
	
	@Column(name = "EMAIL")
	@Email(message = "Hãy nhập đúng định dạng email")
	@NotBlank(message = "Email không được để trống")
	private String email;
	
	@Column(name = "NGAYSINH")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "DD/mm/yyyy")	
	@NotNull(message = "Hãy chọn ngày sinh")
	private Date ngaySinh;
	
	@Column(name = "DIACHI")
	@NotBlank(message = "Địa chỉ không được để trống")
	private String diaChi;
	
	@Column(name = "GIOITINH")
	@NotBlank(message = "Giới tính không được để trống")
	private String gioiTinh;
	
	@Column(name = "CHUCVU")
	@NotBlank(message = "Chức vụ không được để trống")
	private String chucVu;
	
	@Column(name = "LUONG")
	@NotNull(message = "Lương không được để trống")
	private BigDecimal luong;
	
	@Column(name = "DANGHI")
	private Boolean daNghi;
	
	@Column(name = "DAGIAODICH")
	private Boolean daGiaoDich;
	
	@OneToOne(mappedBy = "nhanvien", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private TaiKhoanEntity taikhoan;

	public NhanVienEntity() {
		super();
	}

	public NhanVienEntity(Integer iD_NV, Collection<PhieuNhapEntity> phieuNhap,
			String cmnd, String ho, String ten, String sdt, String email, Date ngaySinh, String diaChi, String gioiTinh,
			String chucVu, BigDecimal luong, Boolean daNghi, TaiKhoanEntity taikhoan, Boolean daGiaoDich) {
		super();
		this.ID_NV = iD_NV;
		this.phieuNhap = phieuNhap;
		this.cmnd = cmnd;
		this.Ho = ho;
		this.Ten = ten;
		this.sdt = sdt;
		this.email = email;
		this.ngaySinh = ngaySinh;
		this.diaChi = diaChi;
		this.gioiTinh = gioiTinh;
		this.chucVu = chucVu;
		this.luong = luong;
		this.daNghi = daNghi;
		this.taikhoan = taikhoan;
		this.daGiaoDich = daGiaoDich;
	}

	public Integer getID_NV() {
		return this.ID_NV;
	}

	public void setID_NV(Integer iD_NV) {
		ID_NV = iD_NV;
	}

	public String getCmnd() {
		return cmnd;
	}

	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}

	public String getHo() {
		return this.Ho;
	}

	public void setHo(String ho) {
		this.Ho = ho;
	}

	public String getTen() {
		return this.Ten;
	}

	public void setTen(String ten) {
		this.Ten = ten;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	public BigDecimal getLuong() {
		return luong;
	}

	public void setLuong(BigDecimal luong) {
		this.luong = luong;
	}

	public Boolean getDaNghi() {
		return daNghi;
	}

	public void setDaNghi(Boolean daNghi) {
		this.daNghi = daNghi;
	}

	public TaiKhoanEntity getTaikhoan() {
		return taikhoan;
	}

	public void setTaikhoan(TaiKhoanEntity taikhoan) {
		this.taikhoan = taikhoan;
	}
	
	public Collection<PhieuNhapEntity> getPhieuNhap() {
		return phieuNhap;
	}

	public void setPhieuNhap(Collection<PhieuNhapEntity> phieuNhap) {
		this.phieuNhap = phieuNhap;
	}

	public Boolean getDaGiaoDich() {
		return daGiaoDich;
	}

	public void setDaGiaoDich(Boolean daGiaoDich) {
		this.daGiaoDich = daGiaoDich;
	}

	public String getHoTen() {
		return this.getHo() + " " + this.getTen();
	}
	
	public String getHoTenVaID() {
		return this.getID_NV() + "-" + this.getHo() + " " + this.getTen();
	}
	
	public void reset() {
		this.ID_NV = null;
		this.phieuNhap = null;
		this.cmnd = null;
		this.Ho = null;
		this.Ten = null;
		this.sdt = null;
		this.email = null;
		this.ngaySinh = null;
		this.diaChi = null;
		this.gioiTinh = null;
		this.chucVu = null;
		this.luong = null;
		this.daNghi = null;
		this.daGiaoDich = null;
		this.taikhoan = null;
	}

	@Override
	public String toString() {
		return "NhanVienEntity [ID_NV=" + ID_NV + ", phieuNhap=" + phieuNhap + ", cmnd=" + cmnd + ", Ho=" + Ho
				+ ", Ten=" + Ten + ", sdt=" + sdt + ", email=" + email + ", ngaySinh=" + ngaySinh + ", diaChi=" + diaChi
				+ ", gioiTinh=" + gioiTinh + ", chucVu=" + chucVu + ", luong=" + luong + ", daNghi=" + daNghi
				+ ", daGiaoDich=" + daGiaoDich + ", taikhoan=" + taikhoan + "]";
	}
}
