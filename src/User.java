
public class User {
	
	private String accountName;
	private String salt;
	private String encPass;
	private String[] fullName = new String[2];
	private String psswdData;
	private boolean psswdCracked;
	
	public User(String accountName, String salt, String encPass, String[] fullName) {
		this.accountName = accountName;
		this.salt = salt;
		this.encPass = encPass;
		this.fullName = fullName;
		this.psswdData = salt + encPass;
		this.psswdCracked = false;
	}
	
	public boolean isPssCracked(){
		return this.psswdCracked;
	}
	
	public void setPssCracked(boolean bool){
		this.psswdCracked = bool;
	}
	
	public String getpsswdData(){
		return this.psswdData;
	}
	public void setpsswdData(String psswdData){
		this.psswdData = psswdData;
	}
	public String getAccountName() {
		return this.accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getEncPass() {
		return encPass;
	}
	public void setEncPass(String encPass) {
		this.encPass = encPass;
	}
	public String[] getFullName() {
		return fullName;
	}
	public void setFullName(String[] fullName) {
		this.fullName = fullName;
	}
	
	@Override
	public String toString(){
		return "User: " + fullName[0] + " " + fullName[1] + ",\nAccount name: " + accountName + ",\nSalt: " + salt + ",\nEncrypted password: " + encPass;
	}

}
