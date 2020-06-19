package com.csye6225.webapps.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.csye6225.webapps.model.Book;
import com.csye6225.webapps.model.BookImages;
import com.csye6225.webapps.repository.BookImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class BookImageService {

    @Autowired
    BookImagesRepository repository;

    @Value("${Bucketname:aDefaultValue}")
    private String bucketName;

    public void save(BookImages image){
        repository.save(image);
    }

    public void uploadeImage (MultipartFile file, Book b){

        String filename = new Date().getTime() + StringUtils.cleanPath(file.getOriginalFilename());
        BookImages image = new BookImages();
        image.setBook(b);
        image.setImageName(filename);
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

    public String viewImage(String key){
        String base64 ="";
        try {

        AmazonS3 s3client = AmazonS3ClientBuilder.defaultClient();
        S3Object o = s3client.getObject(bucketName, key);
        BufferedImage imgBuf = ImageIO.read(o.getObjectContent());
        base64 = encodeBase64URL(imgBuf);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64.isEmpty() ? null : base64;
    }

    public String encodeBase64URL(BufferedImage imgBuf) throws IOException {
        String base64;

        if (imgBuf == null) {
            base64 = null;
        } else {

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            ImageIO.write(imgBuf, "PNG", out);

            byte[] bytes = out.toByteArray();
            base64 = "data:image/png;base64," + new String(Base64.getEncoder().encode(bytes), "UTF-8");
        }

        return base64;
    }

//    public List<String> bookImagesURL(Long bookID){
//        return repository.bookImagesURL(bookID);
//    }

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

