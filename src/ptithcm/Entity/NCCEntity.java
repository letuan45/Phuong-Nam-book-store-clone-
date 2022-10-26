package ptithcm.Entity;

import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "NHACUNGCAP")
public class NCCEntity {
	@Id
	@Column(name = "IDNCC")
	@NotBlank(message = "Id không được để trống")
	private String ID_NCC;

	@Column(name = "TENNCC")
	@NotBlank(message = "Tên không được để trống")
	private String tenNCC;

	@Column(name = "SDT")
	@NotBlank(message = "SĐT không được để trống")
	private String SDT;

	@Column(name = "EMAIL")
	@NotBlank(message = "Email không được để trống")
	private String email;

	@Column(name = "DIACHI")
	@NotBlank(message = "Địa chỉ không được để trống")
	private String diaChi;

	@OneToMany(mappedBy = "nhaCungCap", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Collection<PhieuNhapEntity> phieuNhap;
	
	@OneToMany(mappedBy = "nhaCungCap", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Collection<VPPEntity> vpp;

	@Column(name = "DAGIAODICH")
	private Boolean daGiaoDich;

	public NCCEntity() {
		super();
		this.daGiaoDich = false;
	}

	public NCCEntity(String iD_NCC, String tenNCC, String sDT, String email, String diaChi,
			Collection<PhieuNhapEntity> phieuNhap, Collection<VPPEntity> vpp, Boolean daGiaoDich) {
		super();
		this.ID_NCC = iD_NCC;
		this.tenNCC = tenNCC;
		this.SDT = sDT;
		this.email = email;
		this.diaChi = diaChi;
		this.phieuNhap = phieuNhap;
		this.vpp = vpp;
		this.daGiaoDich = daGiaoDich;
	}

	public String getID_NCC() {
		return ID_NCC;
	}

	public void setID_NCC(String iD_NCC) {
		ID_NCC = iD_NCC;
	}

	public String getTenNCC() {
		return tenNCC;
	}

	public void setTenNCC(String tenNCC) {
		this.tenNCC = tenNCC;
	}

	public String getSDT() {
		return SDT;
	}

	public void setSDT(String sDT) {
		SDT = sDT;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public Boolean getDaGiaoDich() {
		return daGiaoDich;
	}

	public void setDaGiaoDich(Boolean daGiaoDich) {
		this.daGiaoDich = daGiaoDich;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public Collection<PhieuNhapEntity> getPhieuNhap() {
		return phieuNhap;
	}

	public void setPhieuNhap(Collection<PhieuNhapEntity> phieuNhap) {
		this.phieuNhap = phieuNhap;
	}

	public String getFullDisplay() {
		return "Mã NCC: " + this.getID_NCC() + " - " + this.getTenNCC();
	}
	
	public Collection<VPPEntity> getVpp() {
		return vpp;
	}

	public void setVpp(Collection<VPPEntity> vpp) {
		this.vpp = vpp;
	}

	@Override
	public String toString() {
		return "NCCEntity [ID_NCC=" + ID_NCC + ", tenNCC=" + tenNCC + ", SDT=" + SDT + ", email=" + email + ", diaChi="
				+ diaChi + "]";
	}

	public void reset() {
		this.ID_NCC = null;
		this.email = null;
		this.SDT = null;
		this.tenNCC = null;
		this.diaChi = null;
		this.daGiaoDich = false;
	}
}