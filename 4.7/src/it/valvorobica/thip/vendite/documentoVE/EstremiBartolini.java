package it.valvorobica.thip.vendite.documentoVE;

import org.json.JSONObject;

import com.thera.thermfw.base.ResourceLoader;
import com.thera.thermfw.persist.Factory;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 05/08/2024
 * <br><br>
 * <b>71XXX	DSSOF3	05/08/2024</b>
 * <p>Prima stesura.<br>
 *  
 * </p>
 */

public class EstremiBartolini {

	protected String URL;

	protected String userID;

	protected String password;

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EstremiBartolini() {

	}

	public static EstremiBartolini estremiBartolini(String res) {
		EstremiBartolini estremi = (EstremiBartolini) Factory.createObject(EstremiBartolini.class);
		estremi.setURL(ResourceLoader.getString(res, "URL"));
		estremi.setUserID(ResourceLoader.getString(res, "userID"));
		estremi.setPassword(ResourceLoader.getString(res, "password"));
		return estremi;
	}

	public JSONObject getAccount() {
		JSONObject account = new JSONObject();
		account.put("userID", getUserID());
		account.put("password", getPassword());
		return account;
	}

}
