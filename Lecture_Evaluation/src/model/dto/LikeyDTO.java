package model.dto;

public class LikeyDTO {

	private String userID;
	private int evaluationID;
	private String userIP;

	public LikeyDTO() {
	}

	public LikeyDTO(String userID, int evaluationID, String userIP) {
		super();
		this.userID = userID;
		this.evaluationID = evaluationID;
		this.userIP = userIP;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public int getEvaluationID() {
		return evaluationID;
	}

	public void setEvaluationID(int evaluationID) {
		this.evaluationID = evaluationID;
	}

	public String getUserIP() {
		return userIP;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

}
