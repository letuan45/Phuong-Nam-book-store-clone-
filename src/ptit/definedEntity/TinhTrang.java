package ptit.definedEntity;

public class TinhTrang {
	private Integer maTT;
	
	private String TinhTrang;
	
	public TinhTrang() {
		super();
	}

	public TinhTrang(Integer maTT, String tinhTrang) {
		super();
		this.maTT = maTT;
		TinhTrang = tinhTrang;
	}

	public Integer getMaTT() {
		return maTT;
	}

	public void setMaTT(Integer maTT) {
		this.maTT = maTT;
	}

	public String getTinhTrang() {
		return TinhTrang;
	}

	public void setTinhTrang(String tinhTrang) {
		TinhTrang = tinhTrang;
	}
}
