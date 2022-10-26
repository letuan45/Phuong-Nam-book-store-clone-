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
@Table(name = "LOAI")
public class LoaiEntity {
	@Id
	@Column(name = "IDLOAI")
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer ID_Loai;
	
	@Column(name = "TENLOAI")
	private String tenLoai;
	
	@OneToMany(mappedBy = "loai", fetch = FetchType.EAGER)
	private Collection<VPPEntity> vanPhongPham;

	public LoaiEntity() {
		super();
	}

	public LoaiEntity(Integer iD_Loai, String tenLoai, Collection<VPPEntity> vanPhongPham) {
		super();
		this.ID_Loai = iD_Loai;
		this.tenLoai = tenLoai;
		this.vanPhongPham = vanPhongPham;
	}

	public Integer getID_Loai() {
		return ID_Loai;
	}

	public void setID_Loai(Integer iD_Loai) {
		ID_Loai = iD_Loai;
	}

	public String getTenLoai() {
		return tenLoai;
	}

	public void setTenLoai(String tenLoai) {
		this.tenLoai = tenLoai;
	}

	public Collection<VPPEntity> getVanPhongPham() {
		return vanPhongPham;
	}

	public void setVanPhongPham(Collection<VPPEntity> vanPhongPham) {
		this.vanPhongPham = vanPhongPham;
	}
	
	public String getFullDisplay() {
		return "Mã loại: " + this.getID_Loai() + " - " + this.getTenLoai();
	}
}
