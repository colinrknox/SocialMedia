package com.revature.utils;

import java.util.List;

import com.revature.model.UserPost;
import com.revature.model.UserProfile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class S3SavePhoto {
	
	private UserProfile user;
	private UserPost post;
	
	public S3SavePhoto(UserProfile user) {
		this.user = user;
		this.post = null;
	}
	
	public S3SavePhoto(UserPost post) {
		this.post = post;
		this.user = null;
	}
	
	/**
	 * Saves a photo to an S3 bucket hosted by AWS
	 * @param img the byte stream of the photo file
	 * @param contentType the type of the photo file (extension)
	 * @return a UserProfile with the photo attribute set to the url
	 * 		of the stored photo
	 */
	public String savePhoto(byte[] img, String contentType) {
		if (user == null) {
			return savePhoto(post.getId(), img, contentType, "post/");
		} else {
			return savePhoto(user.getId(), img, contentType, "profile/");
		}
	}
	
	private String savePhoto(Integer id, byte[] img, String contentType, String keyDir) {
		S3Client client = S3Client.builder()
				.region(Region.US_EAST_2)
				.build();
		
		List<Bucket> buckets = client.listBuckets().buckets();
		if (buckets.isEmpty()) {
			throw new RuntimeException("No AWS buckets found");
		}
		String key = keyDir + String.valueOf(user.getId());
		PutObjectRequest req = PutObjectRequest.builder()
				.bucket(buckets.get(0).name())
				.key(key)
				.contentType(contentType)
				.build();
		
		client.putObject(req, RequestBody.fromBytes(img));
		
		return "https://" + buckets.get(0).name() + ".s3." + Region.US_EAST_2.toString() + ".amazonaws.com/" + key;
	}
	
	public String savePostPhoto(Integer id, byte[] img, String contentType) {
		S3Client client = S3Client.builder()
				.region(Region.US_EAST_2)
				.build();
		
		List<Bucket> buckets = client.listBuckets().buckets();
		if (buckets.isEmpty()) {
			throw new RuntimeException("No AWS buckets found");
		}
		String keyDir = "post/";
		String key = keyDir + String.valueOf(post.getId());
		PutObjectRequest req = PutObjectRequest.builder()
				.bucket(buckets.get(0).name())
				.key(key)
				.contentType(contentType)
				.build();
		
		client.putObject(req, RequestBody.fromBytes(img));
		
		return "https://" + buckets.get(0).name() + ".s3." + Region.US_EAST_2.toString() + ".amazonaws.com/" + key;
	}
}
