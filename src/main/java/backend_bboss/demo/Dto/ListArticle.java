package backend_bboss.demo.Dto;

public class ListArticle {
	 private long id;
	 private String code;
	 private String libelle;
	 private String scateg;
	 private String categ;
	 private double pa;
	 private double pv;
	 private int tva;
	 private int stock;
	 private String four;
	 private int codef;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getScateg() {
		return scateg;
	}
	public void setScateg(String scateg) {
		this.scateg = scateg;
	}
	public String getCateg() {
		return categ;
	}
	public void setCateg(String categ) {
		this.categ = categ;
	}
	public double getPa() {
		return pa;
	}
	public void setPa(double pa) {
		this.pa = pa;
	}
	public double getPv() {
		return pv;
	}
	public void setPv(double pv) {
		this.pv = pv;
	}
	public int getTva() {
		return tva;
	}
	public void setTva(int tva) {
		this.tva = tva;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getFour() {
		return four;
	}
	public void setFour(String four) {
		this.four = four;
	}
	public int getCodef() {
		return codef;
	}
	public void setCodef(int codef) {
		this.codef = codef;
	}
	public ListArticle(long id, String code, String libelle, String scateg, String categ, double pa, double pv, int tva,
			int stock, String four, int codef) {
		super();
		this.id = id;
		this.code = code;
		this.libelle = libelle;
		this.scateg = scateg;
		this.categ = categ;
		this.pa = pa;
		this.pv = pv;
		this.tva = tva;
		this.stock = stock;
		this.four = four;
		this.codef = codef;
	}
	
	
	
}
