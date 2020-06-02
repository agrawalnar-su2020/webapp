package com.csye6225.webapps.repository;

import com.csye6225.webapps.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
