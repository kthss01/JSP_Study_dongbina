package model.vo;

public class BBS {

	private int bbsID;
	private String bbsTitle;
	private String userID;
	private String bbsDate;
	private String bbsContent;
	private int bbsAvailable;

	public int getBbsID() {
		return bbsID;
	}

	public void setBbsID(int bbsID) {
		this.bbsID = bbsID;
	}

	public String getBbsTitle(boolean isSafe) {
		if (isSafe)
			return bbsTitle.replaceAll(" ", "&nbsp;")
					.replaceAll("<", "&lt;")
					.replaceAll(">", "&gt;")
					.replaceAll("\n", "<br>");
		else
			return bbsTitle;
	}

	public void setBbsTitle(String bbsTitle) {
		this.bbsTitle = bbsTitle;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getBbsDate() {
		return bbsDate;
	}

	public void setBbsDate(String bbsDate) {
		this.bbsDate = bbsDate;
	}

	public String getBbsContent(boolean isSafe) {
		if (isSafe)
			return bbsContent.replaceAll(" ", "&nbsp;")
				.replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;")
				.replaceAll("\n", "<br>");
		else
			return bbsContent;
	}

	public void setBbsContent(String bbsContent) {
		this.bbsContent = bbsContent;
	}

	public int getBbsAvailable() {
		return bbsAvailable;
	}

	public void setBbsAvailable(int bbsAvailable) {
		this.bbsAvailable = bbsAvailable;
	}

	public String getDate() {
//		StringBuilder sb = new StringBuilder();
//		
//		sb.append(bbsDate.substring(0, 11)).append(" ")
//			.append(bbsDate.substring(11, 13)).append("시 ")
//			.append(bbsDate.substring(14, 16)).append("분");
//		
//		return sb.toString();
		
		return bbsDate;
	}
}
