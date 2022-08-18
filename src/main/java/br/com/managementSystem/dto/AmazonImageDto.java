package br.com.managementSystem.dto;

import br.com.managementSystem.model.AmazonImage;

public class AmazonImageDto {

	private Long amazonUserImageId;
    private String imageUrl;
    
    public AmazonImageDto(AmazonImage amazonImage) {
    	this.amazonUserImageId = amazonImage.getAmazonUserImageId();
    	this.imageUrl = amazonImage.getImageUrl();
    }
    
	public Long getAmazonUserImageId() {
		return amazonUserImageId;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}  
	
	public AmazonImage dtoToModel() {
		return new AmazonImage(amazonUserImageId, imageUrl);
	}
}
