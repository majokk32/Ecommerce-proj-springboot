package com.ecommerce.sbproject.controller;

import com.ecommerce.sbproject.config.AppConstants;
import com.ecommerce.sbproject.payload.CategoryDTO;
import com.ecommerce.sbproject.payload.CategoryResponse;
import com.ecommerce.sbproject.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api")  // Set common path prefix for all endpoints in this controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

//    @GetMapping("/echo")
//    public ResponseEntity<String> echoMessage(@RequestParam(name = "message", required = false) String message){
//        return new ResponseEntity<>("Echoed message: " + message,HttpStatus.OK);
//    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORY_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ){
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryID}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryID){
        CategoryDTO deletedDTO =categoryService.deleteCategory(categoryID);
        return new ResponseEntity<>(deletedDTO, HttpStatus.OK);
    }

    @PutMapping("/public/categories/{categoryID}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                      @PathVariable Long categoryID){
        CategoryDTO savedCategoryDTO = categoryService.update(categoryDTO, categoryID);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
    }
}
