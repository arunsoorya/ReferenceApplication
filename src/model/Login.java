/*@(#)Login.java
 */
package model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Login implements Serializable{

	
	/** The serialVersionUID is declared for keeping .*/
	private static final long serialVersionUID = 1L;
	
	@Expose
	@SerializedName("member_email")
	private String memberEmail;
	@Expose
	@SerializedName("password")
	private String password;
	@SerializedName("keep_logged_in")
	private boolean keepLoggedIn;
	
	/**
	 * <p>This method can be used for getting the memberEmail.</p>
	 * @return The memberEmail.
	 */
	public String getMemberEmail() {
		return memberEmail;
	}
	/**
	 * <p>This method can be used for setting the memberEmail.</p>
	 * @param memberEmail The memberEmail to set.
	 */
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
//	/**
//	 * <p>This method can be used for getting the memberId.</p>
//	 * @return The memberId.
//	 */
//	public String getMemberId() {
//		return memberId;
//	}
//	/**
//	 * <p>This method can be used for setting the memberId.</p>
//	 * @param memberId The memberId to set.
//	 */
//	public void setMemberId(String memberId) {
//		this.memberId = memberId;
//	}
	/**
	 * <p>This method can be used for getting the password.</p>
	 * @return The password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * <p>This method can be used for setting the password.</p>
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * <p>This method can be used for getting the keepLoggedIn.</p>
	 * @return The keepLoggedIn.
	 */
	public boolean isKeepLoggedIn() {
		return keepLoggedIn;
	}
	/**
	 * <p>This method can be used for setting the keepLoggedIn.</p>
	 * @param keepLoggedIn The keepLoggedIn to set.
	 */
	public void setKeepLoggedIn(boolean keepLoggedIn) {
		this.keepLoggedIn = keepLoggedIn;
	}
//	/**
//	 * <p>This method can be used for getting the request.</p>
//	 * @return The request.
//	 */
//	public BaseRequest getRequest() {
//		return request;
//	}
//	/**
//	 * <p>This method can be used for setting the request.</p>
//	 * @param request The request to set.
//	 */
//	public void setRequest(BaseRequest request) {
//		this.request = request;
//	}
}
