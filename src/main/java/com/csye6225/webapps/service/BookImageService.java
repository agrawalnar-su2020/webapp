package com.csye6225.webapps.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.csye6225.webapps.model.Book;
import com.csye6225.webapps.model.BookImages;
import com.csye6225.webapps.repository.BookImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class BookImageService {

    @Autowired
    BookImagesRepository repository;

    @Value("${Bucketname:aDefaultValue}")
    private String bucketName;

    @Value("${Bucketendpoint:aDefaultValue}")
    private String endpoint;

//    @Value("${Profile:aDefaultValue}")
//    String profile;
//    @Value("${Region:aDefaultValue}")
//    String region;
//
//    private AmazonS3  s3client;
//
//    @PostConstruct
//    private void initializeAmazon() {
//        AWSCredentialsProviderChain awsCredentialsProviderChain = new AWSCredentialsProviderChain(
//                new InstanceProfileCredentialsProvider(true),
//                new ProfileCredentialsProvider(profile));
//
//        this.s3client = AmazonS3ClientBuilder.standard()
//                .withCredentials(awsCredentialsProviderChain)
//                .withRegion(region)
//                .build();
//
//    }

    public void save(BookImages image){
        repository.save(image);
    }

    public void uploadeImage (MultipartFile file, Book b){
        System.out.println(bucketName);
        System.out.println(endpoint);

        String filename = new Date().getTime() + StringUtils.cleanPath(file.getOriginalFilename());
        String fileUrl = endpoint + "/" + filename;
        System.out.println(filename);
        System.out.println(fileUrl);
        BookImages image = new BookImages();
        image.setBook(b);
        image.setImageName(filename);
        image.setImageURL(fileUrl);
        save(image);
        uploadToS3(file,filename);

    }

    public void uploadToS3(MultipartFile file, String fileName) {
        System.out.println("in uploadToS3");
        AmazonS3 s3client = AmazonS3ClientBuilder.defaultClient();
        System.out.println("after deafaultclient");
        File covFile = convertMultiPartToFile(file);
        System.out.println("after convert");
        s3client.putObject(new PutObjectRequest(bucketName,fileName, covFile));
        System.out.println("after put");
        covFile.delete();
        System.out.println("after delete");
    }

    private File convertMultiPartToFile(MultipartFile file) {
        System.out.println("in convert");
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException | AmazonServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convFile;
    }

    public void deleteS3Image(String fileName) {
        AmazonS3 s3client = AmazonS3ClientBuilder.defaultClient();
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }

    public List<String> bookImagesURL(Long bookID){
        return repository.bookImagesURL(bookID);
    }

    public List<Long> imagesID(Long bookID){
        return repository.imagesID(bookID);
    }

    public List<String> imagesName(Long bookID){
        return  repository.imagesName(bookID);
    }

    public void deleteDBImage(Long imageID){
        repository.deleteById(imageID);
    }

}

