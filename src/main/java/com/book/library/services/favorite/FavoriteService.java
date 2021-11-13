package com.book.library.services.favorite;

import com.book.library.dao.FavoriteDao;
import com.book.library.entities.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FavoriteService {
    Favorite create(FavoriteDao favorite);
   Page<Favorite> listFavorite(Pageable pageable);
    Favorite findOne(Integer favoriteId);
}
