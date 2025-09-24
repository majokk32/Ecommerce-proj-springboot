package com.ecommerce.sbproject.repositories;

import com.ecommerce.sbproject.model.Category;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {


    Category findByCategoryName(@NotBlank String categoryName);
}
