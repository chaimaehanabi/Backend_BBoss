package backend_bboss.demo.Dto;

public class ListCategorie {
	 private String code;
	 private String libelle;
	 private String libcateg;
	 private String ccateg;
	public ListCategorie(String code, String libelle, String libcateg, String ccateg) {

		this.code = code;
		this.libelle = libelle;
		this.libcateg = libcateg;
		this.ccateg = ccateg;
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
	public String getLibcateg() {
		return libcateg;
	}
	public void setLibcateg(String libcateg) {
		this.libcateg = libcateg;
	}
	public String getCcateg() {
		return ccateg;
	}
	public void setCcateg(String ccateg) {
		this.ccateg = ccateg;
	}
	 	 
}
