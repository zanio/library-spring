package com.book.library.repository;

import com.book.library.entities.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

}
