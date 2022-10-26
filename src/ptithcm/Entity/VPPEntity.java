package ptithcm.Entity;

import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "VANPHONGPHAM")
public class VPPEntity {
	@Id
	@Column(name = "IDVPP")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idVpp;

	// Start Foreign Keys
	@ManyToOne
	@JoinColumn(name = "IDLOAI")
	private LoaiEntity loai;

	@ManyToOne
	@JoinColumn(name = "IDTH")
	private ThuongHieuEntity thuongHieu;

	@OneToMany(mappedBy = "vanPhongPham")
	private Collection<CTPNEntity> chiTietPNs;
	
	@ManyToOne
	@JoinColumn(name = "IDNCC")
	private NCCEntity nhaCungCap;

	// End Foreign Keys

	@Column(name = "TENVPP")
	@NotBlank(message = "Tên không được để trống")
	private String tenVPP;

	@Column(name = "TINHTRANG")
	private Integer tinhTrang;

	@Column(name = "GIABAN")
	@NotNull(message = "Giá không được để trống")
	@Min(value = 0, message = "Giá bán không hợp lệ")
	private BigDecimal giaBan;

	@Column(name = "SOLUONG")
	private Integer soLuong;

	@Column(name = "DONVITINH")
	@NotBlank(message = "ĐVT không được để trống")
	private String donViTinh;

	@Column(name = "HINHANH")
	private String hinhAnh;

	@Column(name = "DAGIAODICH")
	private Boolean daGiaoDich;

	public VPPEntity() {
		super();
	}

	public VPPEntity(Integer iD_VPP, LoaiEntity loai, ThuongHieuEntity thuongHieu, Collection<CTPNEntity> chiTietPNs,
			String tenVPP, Integer tinhTrang, BigDecimal giaBan, Integer soLuong, String donViTinh,
			String hinhAnh, Boolean daGiaoDich) {
		super();
		this.idVpp = iD_VPP;
		this.loai = loai;
		this.thuongHieu = thuongHieu;
		this.chiTietPNs = chiTietPNs;
		this.tenVPP = tenVPP;
		this.tinhTrang = tinhTrang;
		this.giaBan = giaBan;
		this.soLuong = soLuong;
		this.donViTinh = donViTinh;
		this.hinhAnh = hinhAnh;
		this.daGiaoDich = daGiaoDich;
	}

	public Integer getID_VPP() {
		return idVpp;
	}

	public void setID_VPP(Integer iD_VPP) {
		this.idVpp = iD_VPP;
	}

	public LoaiEntity getLoai() {
		return loai;
	}

	public void setLoai(LoaiEntity loai) {
		this.loai = loai;
	}

	public ThuongHieuEntity getThuongHieu() {
		return thuongHieu;
	}

	public void setThuongHieu(ThuongHieuEntity thuongHieu) {
		this.thuongHieu = thuongHieu;
	}

	public String getTenVPP() {
		return tenVPP;
	}

	public void setTenVPP(String tenVPP) {
		this.tenVPP = tenVPP;
	}

	public Integer getTinhTrang() {
		return tinhTrang;
	}

	public void setTinhTrang(Integer tinhTrang) {
		this.tinhTrang = tinhTrang;
	}

	public BigDecimal getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(BigDecimal giaBan) {
		this.giaBan = giaBan;
	}

	public Integer getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(Integer soLuong) {
		this.soLuong = soLuong;
	}

	public String getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}

	public String getDonViTinh() {
		return donViTinh;
	}

	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}

	public Boolean getDaGiaoDich() {
		return daGiaoDich;
	}

	public void setDaGiaoDich(Boolean daGiaoDich) {
		this.daGiaoDich = daGiaoDich;
	}

	public Collection<CTPNEntity> getChiTietPNs() {
		return chiTietPNs;
	}

	public void setChiTietPNs(Collection<CTPNEntity> chiTietPNs) {
		this.chiTietPNs = chiTietPNs;
	}

	public NCCEntity getNhaCungCap() {
		return nhaCungCap;
	}

	public void setNhaCungCap(NCCEntity nhaCungCap) {
		this.nhaCungCap = nhaCungCap;
	}

	public void resetProduct() {
		this.setID_VPP(null);
		this.setTenVPP(null);
		this.setHinhAnh(null);
		this.setGiaBan(null);
		this.setDonViTinh(null);
	}

	@Override
	public String toString() {
		return "VPPEntity [idVpp=" + idVpp + ", loai=" + loai + ", thuongHieu=" + thuongHieu + ", tenVPP=" + tenVPP
				+ ", tinhTrang=" + tinhTrang + ", giaBan=" + giaBan  + ", soLuong=" + soLuong
				+ ", donViTinh=" + donViTinh + ", hinhAnh=" + hinhAnh + ", daGiaoDich=" + daGiaoDich + ", nhaCungCap" + nhaCungCap + "]";
	}

	public String toJSON() {
		Integer id = this.getID_VPP();
		String ten = "\"" + this.getTenVPP() + "\"";
		String hinhAnh = "\"" + this.getHinhAnh();
		Integer soLuong = this.getSoLuong();
		BigDecimal giaBan = this.getGiaBan();
		String loai = "\"" + this.getLoai().getTenLoai() + "\"";
		Integer tinhTrang = this.getTinhTrang();
		String thuongHieu = "\"" + this.getThuongHieu().getTenTH() + "\"";

		return "{\"id\":" + id + "," + "\"ten\":" + ten + "," + "\"hinhAnh\":" + hinhAnh + "\"" + "," + "\"soLuong\":"
				+ soLuong + "," + "\"giaBan\":" + giaBan + "," + "\"loai\":" + loai
				+ "," + "\"tinhTrang\":" + tinhTrang + "," + "\"thuongHieu\":" + thuongHieu + "}";
	}
}
