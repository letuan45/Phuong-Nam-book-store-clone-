package ptithcm.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "TAIKHOAN")
public class TaiKhoanEntity {	
	@Id
	@Column(name = "USERNAME")
	@Pattern(regexp = "[a-zA-Z0-9]{0,25}$", message = "Sai định dạng")
	private String username;
	
	@Column(name = "PASSWORD")
	@NotBlank(message = "Mật khẩu không thể trống")
	private String password;
	
	@Transient
	@NotBlank(message = "Mật khẩu không thể trống")
	private String confirmPassword;
	
//	@Column(name = "USER_ROLE")
//	private String USER_ROLE;
	
	@ManyToOne
	@JoinColumn(name = "USER_ROLE")
	private RoleEntity role;
	
	@Column(name = "ENABLED")
	private Boolean enabled;
	
	@OneToOne
	@JoinColumn(name = "IDNV")
	private NhanVienEntity nhanvien;

	public TaiKhoanEntity() {
		super();
	}

	public TaiKhoanEntity(String username, String password, String confirmPassword,
			Boolean enabled, NhanVienEntity nhanvien) {
		super();
		this.username = username;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.enabled = enabled;
		this.nhanvien = nhanvien;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUSER_ROLE() {
		if(this.role != null)
			return this.role.getRole();
		return "";
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public NhanVienEntity getNhanvien() {
		return nhanvien;
	}

	public void setNhanvien(NhanVienEntity nhanvien) {
		this.nhanvien = nhanvien;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "TaiKhoanEntity [username=" + username + ", password=" + password + ", confirmPassword="
				+ confirmPassword + ", role=" + role + ", enabled=" + enabled + "]";
	}
}
