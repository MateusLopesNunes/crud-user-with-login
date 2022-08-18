package br.com.managementSystem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class AmazonFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long amazonUserFileId;
    private String fileUrl;
    
    public AmazonFile() {
    }
    
	public AmazonFile(Long amazonUserFileId, String fileUrl) {
		super();
		this.amazonUserFileId = amazonUserFileId;
		this.fileUrl = fileUrl;
	}
	
	public Long getAmazonUserFileId() {
		return amazonUserFileId;
	}
	public void setAmazonUserFileId(Long amazonUserImageId) {
		this.amazonUserFileId = amazonUserImageId;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String imageUrl) {
		this.fileUrl = imageUrl;
	}
    
    
}
