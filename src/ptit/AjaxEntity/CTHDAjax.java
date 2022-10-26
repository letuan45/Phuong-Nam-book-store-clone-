package ptit.AjaxEntity;

public class CTHDAjax {
	private Integer id;
	private Integer quantity;
	
	public CTHDAjax() {
		super();
	}
	
	public CTHDAjax(Integer id, Integer quantity) {
		super();
		this.id = id;
		this.quantity = quantity;
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

	@Override
	public String toString() {
		return "CTHDAjax [id=" + id + ", quantity=" + quantity + "]";
	}
}
