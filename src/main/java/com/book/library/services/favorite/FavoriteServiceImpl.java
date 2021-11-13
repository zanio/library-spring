package com.book.library.services.favorite;

import com.book.library.dao.FavoriteDao;
import com.book.library.entities.Book;
import com.book.library.entities.Favorite;
import com.book.library.exceptions.custom.EntityNotFoundException;
import com.book.library.exceptions.custom.FieldIsRequiredException;
import com.book.library.repository.BookRepository;
import com.book.library.repository.FavoriteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class FavoriteServiceImpl implements FavoriteService{
    @Autowired
    private FavoriteRepository favoriteRepositoryImpl;
    @Autowired
    private BookRepository bookRepositoryImpl;
    @Override
    public Favorite create(FavoriteDao favoriteDao) {
        Set<Book> books = new HashSet<>();
        if(favoriteDao.getBooks()!=null && favoriteDao.getBooks().size()>0){
            favoriteDao.getBooks().forEach((it->{
              Optional<Book> bookOptional = bookRepositoryImpl.findById(it);
              if(bookOptional.isPresent()){
                  Book book = bookOptional.get();
                  books.add(book);
              } else {
                 log.error("book id does not exist");
                 throw new EntityNotFoundException(Book.class, "id",it.toString());
              }
            }));
        } else {
            log.error("field is required ==> books");
            throw new FieldIsRequiredException("field {books} is required");
        }
        Favorite favorite = new Favorite();
        favorite.setBooks(books);
        favoriteRepositoryImpl.save(favorite);
        return favorite;
    }

    @Override
    public Page<Favorite> listFavorite(Pageable pageable) {
        return favoriteRepositoryImpl.findAll(pageable);
    }
    

    @Override
    public Favorite findOne(Integer favoriteId) {
        return favoriteRepositoryImpl.findById(favoriteId)
                .orElseThrow(
                        () -> new EntityNotFoundException(Favorite.class, "Id", favoriteId.toString())
                );
    }
}
