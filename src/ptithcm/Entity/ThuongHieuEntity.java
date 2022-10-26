package ptithcm.Entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "THUONGHIEU")
public class ThuongHieuEntity {
	@Id
	@Column(name = "IDTH")
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer ID_TH;
	
	@Column(name = "TENTH")
	private String tenTH;
	
	@OneToMany(mappedBy = "thuongHieu", fetch = FetchType.EAGER)
	private Collection<VPPEntity> vanPhongPham;
	
	public ThuongHieuEntity() {
		super();
	}

	public ThuongHieuEntity(Integer iD_TH, String tenTH, Collection<VPPEntity> vanPhongPham) {
		super();
		this.ID_TH = iD_TH;
		this.tenTH = tenTH;
		this.vanPhongPham = vanPhongPham;
	}

	public Integer getID_TH() {
		return ID_TH;
	}

	public void setID_TH(Integer iD_TH) {
		ID_TH = iD_TH;
	}

	public String getTenTH() {
		return tenTH;
	}

	public void setTenTH(String tenTH) {
		this.tenTH = tenTH;
	}

	public Collection<VPPEntity> getVanPhongPham() {
		return vanPhongPham;
	}

	public void setVanPhongPham(Collection<VPPEntity> vanPhongPham) {
		this.vanPhongPham = vanPhongPham;
	}
	
	public String getFullDisplay() {
		return "Mã TH: " + this.getID_TH() + " - " + this.getTenTH();
	}
}
