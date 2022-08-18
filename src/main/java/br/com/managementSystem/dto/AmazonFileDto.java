package br.com.managementSystem.dto;

import br.com.managementSystem.model.AmazonFile;

public class AmazonFileDto {

	private Long amazonFileImageId;
    private String fileUrl;
    
    public AmazonFileDto(AmazonFile amazonImage) {
    	this.amazonFileImageId = amazonImage.getAmazonUserFileId();
    	this.fileUrl = amazonImage.getFileUrl();
    }
    
	public Long getAmazonFileImageId() {
		return amazonFileImageId;
	}
	
	public String getFileUrl() {
		return fileUrl;
	}  
	
	public AmazonFile dtoToModel() {
		return new AmazonFile(amazonFileImageId, fileUrl);
	}
}
