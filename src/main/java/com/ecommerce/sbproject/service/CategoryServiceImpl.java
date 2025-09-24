package com.ecommerce.sbproject.service;

import com.ecommerce.sbproject.exceptions.APIException;
import com.ecommerce.sbproject.exceptions.ResourceNotFoundException;
import com.ecommerce.sbproject.model.Category;
import com.ecommerce.sbproject.payload.CategoryDTO;
import com.ecommerce.sbproject.payload.CategoryResponse;
import com.ecommerce.sbproject.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        List<Category> presentCategory = categoryPage.getContent();
        if(presentCategory.isEmpty()) {
            throw new APIException("No category created now!");
        }
        List<CategoryDTO> categoryDTOS = presentCategory.stream().map(category -> modelMapper.map(category,CategoryDTO.class)).toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());

        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());
        if(categoryFromDb != null){
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists!");
        }
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryID) {
        Category savedCategory = categoryRepository.findById(categoryID)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryID",categoryID));

        categoryRepository.delete(savedCategory);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }


    @Override
    public CategoryDTO update(CategoryDTO categoryDTO, Long categoryID){
        categoryRepository.findById(categoryID)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryID", categoryID));

        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setCategoryID(categoryID);

        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }
}
