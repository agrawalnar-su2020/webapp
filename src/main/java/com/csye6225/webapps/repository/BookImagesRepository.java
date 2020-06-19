package com.csye6225.webapps.repository;

import com.csye6225.webapps.model.Book;
import com.csye6225.webapps.model.BookImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookImagesRepository extends JpaRepository<BookImages, Long> {
//
//    @Query(value="Select imageurl From book_images i where i.book_bookid  = :bookID",nativeQuery = true)
//    List<String> bookImagesURL(Long bookID);

    @Query(value="Select imageid From book_images i where i.book_bookid  = :bookID",nativeQuery = true)
    List<Long> imagesID(Long bookID);

    @Query(value="Select image_name From book_images i where i.book_bookid  = :bookID",nativeQuery = true)
    List<String> imagesName(Long bookID);
}
