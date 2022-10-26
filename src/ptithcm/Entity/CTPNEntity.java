package ptithcm.Entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CHITIETPN")
public class CTPNEntity {
	@Id
	@GeneratedValue
	@Column(name= "ID")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "IDPN")
	private PhieuNhapEntity phieuNhap;
	
	@ManyToOne
	@JoinColumn(name = "IDVPP")
	private VPPEntity vanPhongPham;
	
	@Column(name = "SL")
	private Integer soLuong;
	
	@Column(name = "DONGIANHAP")
	private BigDecimal donGiaNhap;
	
	public CTPNEntity() {
		super();
	}

	public CTPNEntity(Integer id, PhieuNhapEntity phieuNhap, VPPEntity vanPhongPham, Integer soLuong,
			BigDecimal donGiaNhap) {
		super();
		this.id = id;
		this.phieuNhap = phieuNhap;
		this.vanPhongPham = vanPhongPham;
		this.soLuong = soLuong;
		this.donGiaNhap = donGiaNhap;
	}
	
	public CTPNEntity(PhieuNhapEntity phieuNhap, VPPEntity vanPhongPham, Integer soLuong,
			BigDecimal donGiaNhap) {
		super();
		this.phieuNhap = phieuNhap;
		this.vanPhongPham = vanPhongPham;
		this.soLuong = soLuong;
		this.donGiaNhap = donGiaNhap;
	}

	public PhieuNhapEntity getPhieuNhap() {
		return phieuNhap;
	}

	public void setPhieuNhap(PhieuNhapEntity phieuNhap) {
		this.phieuNhap = phieuNhap;
	}

	public VPPEntity getVanPhongPham() {
		return vanPhongPham;
	}

	public void setVanPhongPham(VPPEntity vanPhongPham) {
		this.vanPhongPham = vanPhongPham;
	}

	public Integer getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(Integer soLuong) {
		this.soLuong = soLuong;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getDonGiaNhap() {
		return donGiaNhap;
	}

	public void setDonGiaNhap(BigDecimal donGiaNhap) {
		this.donGiaNhap = donGiaNhap;
	}

	@Override
	public String toString() {
		return "CTPNEntity [id=" + id + ", vanPhongPham=" + vanPhongPham + ", soLuong=" + soLuong + ", donGiaNhap="
				+ donGiaNhap + "]";
	}
}
