package ptit.AjaxEntity;

import java.math.BigDecimal;

public class CTPNAjax {
	private Integer id;
	private Integer quantity;
	private BigDecimal giaNhap;
	
	public CTPNAjax() {
		super();
	}

	public CTPNAjax(Integer id, Integer quantity, BigDecimal giaNhap) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.giaNhap = giaNhap;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getGiaNhap() {
		return giaNhap;
	}

	public void setGiaNhap(BigDecimal giaNhap) {
		this.giaNhap = giaNhap;
	}

	@Override
	public String toString() {
		return "CTPNAjax [id=" + id + ", quantity=" + quantity + ", giaNhap=" + giaNhap + "]";
	}
}
