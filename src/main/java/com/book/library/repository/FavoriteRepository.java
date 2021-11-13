package com.book.library.repository;

import com.book.library.entities.Book;
import com.book.library.entities.Favorite;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FavoriteRepository extends PagingAndSortingRepository<Favorite, Integer> {

}
