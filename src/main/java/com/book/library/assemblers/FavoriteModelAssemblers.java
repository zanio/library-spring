package com.book.library.assemblers;

import com.book.library.clients.FavoriteRestController;
import com.book.library.entities.Favorite;
import com.book.library.models.BookModel;
import com.book.library.models.FavoriteModel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class FavoriteModelAssemblers implements RepresentationModelAssembler<Favorite, FavoriteModel> {
  private static final String FAVORITES = "favorites";
  private final BookModelAssemblers bookModelAssemblers;

  public FavoriteModelAssemblers(BookModelAssemblers bookModelAssemblers) {
    this.bookModelAssemblers = bookModelAssemblers;
  }

  @SneakyThrows
  @Override
  public FavoriteModel toModel(Favorite entity) {
    FavoriteModel favoriteEntityModel = new FavoriteModel();
    final String id = entity.getId().toString();
    Set<BookModel> bookModelSet = new HashSet<>();
    if(entity.getBooks().size()>0){
      entity.getBooks().forEach((it)->{
        bookModelSet.add(bookModelAssemblers.toModel(it));
      });
    }
    favoriteEntityModel.setId(entity.getId());
    favoriteEntityModel.setBooks(bookModelSet);
    favoriteEntityModel.setCreatedAt(entity.getCreatedAt());
    favoriteEntityModel.setUpdatedAt(entity.getUpdatedAt());
    favoriteEntityModel.setId(entity.getId());

    try {
      favoriteEntityModel.add(
        WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(FavoriteRestController.class).getFavoriteById(id))
          .withSelfRel()
      );
      favoriteEntityModel.add(
        WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(FavoriteRestController.class).getFavorites(Pageable.ofSize(1)))
                .withRel(FAVORITES)
      );
    } catch (Exception e) {
      log.error(
        "THE FOLLOWING ERROR OCCURRED IN BUILDING THE LINKS FOR THE Entity model --> {}",
        e.toString()
      );
    }

    return favoriteEntityModel;
  }

  @Override
  public CollectionModel<FavoriteModel> toCollectionModel(Iterable<? extends Favorite> entities) {
    return RepresentationModelAssembler.super.toCollectionModel(entities);
  }
}
