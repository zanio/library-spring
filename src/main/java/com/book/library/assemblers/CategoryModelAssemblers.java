package com.book.library.assemblers;

import com.book.library.clients.CategoryRestController;
import com.book.library.entities.Category;
import com.book.library.models.BookModel;
import com.book.library.models.CategoryModel;
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
public class CategoryModelAssemblers implements RepresentationModelAssembler<Category, CategoryModel> {
  private final BookModelAssemblers bookModelAssemblers;
  private static final String CATEGORY = "categories";

  public CategoryModelAssemblers(BookModelAssemblers bookModelAssemblers) {
    this.bookModelAssemblers = bookModelAssemblers;
  }

  @SneakyThrows
  @Override
  public CategoryModel toModel(Category entity) {
    CategoryModel categoryEntityModel = new CategoryModel();
    final String id = entity.getId().toString();
    categoryEntityModel.setName(entity.getName());
    categoryEntityModel.setActive(entity.getActive());
    categoryEntityModel.setCreatedAt(entity.getCreatedAt());
    categoryEntityModel.setUpdatedAt(entity.getUpdatedAt());
    Set<BookModel> bookModelSet = new HashSet<>();
    if(entity.getBooks() !=null && entity.getBooks().size()>0){
      entity.getBooks().forEach((it)->{
        bookModelSet.add(bookModelAssemblers.toModel(it));
      });
    }
    categoryEntityModel.setId(entity.getId());
    categoryEntityModel.setBooks(bookModelSet);
    try {
      categoryEntityModel.add(
        WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(CategoryRestController.class).getCategoryById(id))
          .withSelfRel()
      );
      categoryEntityModel.add(
              WebMvcLinkBuilder
                      .linkTo(WebMvcLinkBuilder.methodOn(CategoryRestController.class).getCategories(Pageable.ofSize(1)))
                      .withRel(CATEGORY)
      );
    } catch (Exception e) {
      log.error(
        "THE FOLLOWING ERROR OCCURRED IN BUILDING THE LINKS FOR THE Entity model --> {}",
        e.toString()
      );
    }

    return categoryEntityModel;
  }

  @Override
  public CollectionModel<CategoryModel> toCollectionModel(Iterable<? extends Category> entities) {
    return RepresentationModelAssembler.super.toCollectionModel(entities);
  }
}
