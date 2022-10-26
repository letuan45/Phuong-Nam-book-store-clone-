package ptithcm.Entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ROLES")
public class RoleEntity {
	@Id
	@Column(name = "USER_ROLE")
	private String role;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
	private Collection<TaiKhoanEntity> taiKhoan;

	@Override
	public String toString() {
		return "RoleEntity [role=" + role + ", description=" + description + "]";
	}

	public RoleEntity() {
		super();
	}

	public RoleEntity(String role, String description, Collection<TaiKhoanEntity> taiKhoan) {
		super();
		this.role = role;
		this.description = description;
		this.taiKhoan = taiKhoan;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collection<TaiKhoanEntity> getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(Collection<TaiKhoanEntity> taiKhoan) {
		this.taiKhoan = taiKhoan;
	}
	
	
}
