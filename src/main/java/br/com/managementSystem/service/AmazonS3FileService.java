package br.com.managementSystem.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import br.com.managementSystem.dto.AmazonFileDto;
import br.com.managementSystem.dto.AmazonImageDto;
import br.com.managementSystem.model.AmazonFile;
import br.com.managementSystem.model.AmazonImage;
import br.com.managementSystem.repository.AmazonFileRepository;
import br.com.managementSystem.repository.AmazonImageRepository;
import br.com.managementSystem.util.FileUtils;

@Service
public class AmazonS3FileService {

	@Autowired
	private AmazonFileRepository amazonFileRepository;

	@Value("${amazon.s3.bucket-name}")
	private String bucketName;

	@Value("${amazon.s3.endpoint}")
	private String url;

	@Autowired
	private AmazonS3 client;

	// Upload a List of Images to AWS S3.
	public List<AmazonFile> insertImages(List<MultipartFile> files) {
		List<AmazonFile> amazonFiles = new ArrayList<>();
		files.forEach(file -> amazonFiles.add(uploadFileToAmazon(file)));
		return amazonFiles;
	}

	// Upload image to AWS S3.
	public AmazonFile uploadFileToAmazon(MultipartFile multipartFile) {

		if (multipartFile != null) {
			// Valid extensions array, like jpeg/jpg and png.
			List<String> validExtensions = Arrays.asList("pdf", "csv", "docx");

			// Get extension of MultipartFile
			String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

			if (!validExtensions.contains(extension)) {
				// If file have a invalid extension, call an Exception.
				// log.warn(MessageUtil.getMessage("invalid.image.extesion"));
				throw new RuntimeException("Extension " + extension + "is invalid");
			} else {

				// Upload file to Amazon.
				String url = uploadMultipartFile(multipartFile);

				// Save image information on h2 and return them.
				AmazonFile amazonFile = new AmazonFile();
				amazonFile.setFileUrl(url);

				return amazonFileRepository.save(amazonFile);
			}
		}
		return null;
	}

	public void removeImageFromAmazon(AmazonFile amazonFile) {
		String fileName = amazonFile.getFileUrl().substring(amazonFile.getFileUrl().lastIndexOf("/") + 1);
		client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
		// amazonImageRepository.delete(amazonImage);
	}

	public byte[] downloadFile(String fileName) {
		S3Object s3Object = client.getObject(bucketName, fileName);
		S3ObjectInputStream inputStream = s3Object.getObjectContent();
		try {
			byte[] content = IOUtils.toByteArray(inputStream);
			return content;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Make upload to Amazon.
	private String uploadMultipartFile(MultipartFile multipartFile) {
		String fileUrl;

		try {
			// Get the file from MultipartFile.
			File file = FileUtils.convertMultipartToFile(multipartFile);

			// Extract the file name.
			String fileName = FileUtils.generateFileName(multipartFile);

			// Upload file.
			uploadPublicFile(fileName, file);

			// Delete the file and get the File Url.
			file.delete();
			fileUrl = url.concat(fileName);
		} catch (IOException e) {

			// If IOException on conversion or any file manipulation, call exception.
			// log.warn(MessageUtil.getMessage("multipart.to.file.convert.except"), e);
			throw new RuntimeException("Error in convert");
		}

		return fileUrl;
	}

	// Send image to AmazonS3, if have any problems here, the image fragments are
	// removed from amazon.
	// Font:
	// https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/s3/AmazonS3Client.html#putObject%28com.amazonaws.services.s3.model.PutObjectRequest%29
	private void uploadPublicFile(String fileName, File file) {
		client.putObject(
				new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
	}
}
