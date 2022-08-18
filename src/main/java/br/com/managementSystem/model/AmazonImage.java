package br.com.managementSystem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AmazonImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long amazonUserImageId;
    private String imageUrl;
    
    public AmazonImage() {
    }
    
	public AmazonImage(Long amazonUserImageId, String imageUrl) {
		super();
		this.amazonUserImageId = amazonUserImageId;
		this.imageUrl = imageUrl;
	}
	
	public Long getAmazonUserImageId() {
		return amazonUserImageId;
	}
	public void setAmazonUserImageId(Long amazonUserImageId) {
		this.amazonUserImageId = amazonUserImageId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "AmazonImage [amazonUserImageId=" + amazonUserImageId + ", imageUrl=" + imageUrl + "]";
	}
}
