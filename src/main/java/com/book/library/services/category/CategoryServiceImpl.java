package com.book.library.services.category;

import com.book.library.entities.Book;
import com.book.library.entities.Category;
import com.book.library.exceptions.custom.EntityNotFoundException;
import com.book.library.repository.BookRepository;
import com.book.library.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepositoryImpl;
    @Autowired
    private BookRepository bookRepositoryImpl;
    @Override
    public Category findOne(Integer categoryId) {
        return categoryRepositoryImpl.findById(categoryId).orElseThrow(
                () -> new EntityNotFoundException(Category.class, "Id", categoryId.toString())
        );
    }

    @Override
    public Category create(String name) {
        Category category = new Category(name);
        return categoryRepositoryImpl.save(category);
    }

    @Override
    public Category update(String name, Integer categoryId) {
        Category category = findOne(categoryId);
        if(name!=null && name.length()>0){
            category.setName(name);
        }
        categoryRepositoryImpl.save(category);
        return category;
    }

    @Override
    @Transactional
    public void deleteCategory(Integer categoryId) {
        Category category = findOne(categoryId);
        Set<Book> books = bookRepositoryImpl.findAllByCategory(category);
        log.info("books ->{}", books);
//        bookRepositoryImpl.

        if(books!=null && books.size()>0){
            books.forEach((book) ->{
                if(book.getCategory()== category){
                    book.setCategory(null);
                    bookRepositoryImpl.save(book);
                    log.info("book ->{}", book);
                }
//
        });
        }
//        category.setBooks(Collections.emptySet());
//        categoryRepositoryImpl.save(category);
        categoryRepositoryImpl.delete(category);
    }

    @Override
    public Page<Category> listCategories(Pageable pageable) {
        return categoryRepositoryImpl.findAll(pageable);
    }


}
