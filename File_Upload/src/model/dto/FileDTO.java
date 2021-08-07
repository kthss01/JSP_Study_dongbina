package model.dto;

public class FileDTO {
	String fileName;
	String fileRealName;
	int downloadCount;

	public FileDTO() {
	}

	public FileDTO(String fileName, String fileRealName, int downloodCount) {
		super();
		this.fileName = fileName;
		this.fileRealName = fileRealName;
		this.downloadCount = downloodCount;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileRealName() {
		return fileRealName;
	}

	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
	}

	public int getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}

}
