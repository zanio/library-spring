package com.book.library.assemblers;

import com.book.library.clients.BookRestController;
import com.book.library.entities.Book;
import com.book.library.models.BookModel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookModelAssemblers implements RepresentationModelAssembler<Book, BookModel> {
  private static final String BOOKS = "books";
  private final CategoryModelAssemblers categoryModelAssemblers;

  public BookModelAssemblers(@Lazy CategoryModelAssemblers categoryModelAssemblers) {
    this.categoryModelAssemblers = categoryModelAssemblers;
  }

  @SneakyThrows
  @Override
  public BookModel toModel(Book entity) {
    BookModel bookEntityModel = new BookModel();
    log.info("books ->{}",entity);
    final String id = entity.getId().toString();
    bookEntityModel.setAuthor(entity.getAuthor());
    bookEntityModel.setDownloadUrl(entity.getDownloadUrl());
    bookEntityModel.setIsbn(entity.getIsbn());
    if(entity.getCategory()!=null){
      bookEntityModel.setCategory(categoryModelAssemblers.toModel(entity.getCategory()));
    }

    bookEntityModel.setPublishedDate(entity.getPublishedDate());
    bookEntityModel.setRating(entity.getRating());
    bookEntityModel.setTitle(
      entity.getTitle()
    );
    bookEntityModel.setTotalPages(entity.getTotalPages());
    bookEntityModel.setCreatedAt(entity.getCreatedAt());
    bookEntityModel.setUpdatedAt(entity.getUpdatedAt());
    bookEntityModel.setId(entity.getId());
    bookEntityModel.setPublisherId(entity.getPublisherId());
    try {
      bookEntityModel.add(
        WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(BookRestController.class).getBookById(id))
          .withSelfRel()
      );
      bookEntityModel.add(
        WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(BookRestController.class).getBooks(Pageable.ofSize(1)))
                .withRel(BOOKS)
      );
    } catch (Exception e) {
      log.error(
        "THE FOLLOWING ERROR OCCURRED IN BUILDING THE LINKS FOR THE Entity model --> {}",
        e.toString()
      );
    }

    return bookEntityModel;
  }

  @Override
  public CollectionModel<BookModel> toCollectionModel(Iterable<? extends Book> entities) {
    return RepresentationModelAssembler.super.toCollectionModel(entities);
  }
}
