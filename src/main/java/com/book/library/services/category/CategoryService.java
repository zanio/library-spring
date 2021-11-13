package com.book.library.services.category;

import com.book.library.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CategoryService {
    Category findOne(Integer categoryId);
    Category create(String name);
    Category update(String name,Integer bookId) ;
    void deleteCategory(Integer categoryId);
    Page<Category> listCategories(Pageable pageable);
}
