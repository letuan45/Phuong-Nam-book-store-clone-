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
@Table(name = "CHITIETHD")
public class CTHDEntity {
	@Id
	@GeneratedValue
	@Column(name= "ID")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "IDHD")
	private HoaDonEntity hoaDon;
	
	@ManyToOne
	@JoinColumn(name = "IDVPP")
	private VPPEntity vanPhongPham;
	
	@Column(name = "SL")
	private Integer soLuong;
	
	@Column(name = "DONGIA")
	private BigDecimal donGia;

	public CTHDEntity() {
		super();
	}

	public CTHDEntity(Integer id, HoaDonEntity hoaDon, VPPEntity vanPhongPham, Integer soLuong, BigDecimal donGia) {
		super();
		this.id = id;
		this.hoaDon = hoaDon;
		this.vanPhongPham = vanPhongPham;
		this.soLuong = soLuong;
		this.donGia = donGia;
	}
	
	public CTHDEntity(HoaDonEntity hoaDon, VPPEntity vanPhongPham, Integer soLuong, BigDecimal donGia) {
		super();
		this.hoaDon = hoaDon;
		this.vanPhongPham = vanPhongPham;
		this.soLuong = soLuong;
		this.donGia = donGia;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public HoaDonEntity getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDonEntity hoaDon) {
		this.hoaDon = hoaDon;
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

	public BigDecimal getDonGia() {
		return donGia;
	}

	public void setDonGia(BigDecimal donGia) {
		this.donGia = donGia;
	}
}
