package com.book.library.models;

import com.book.library.entities.Category;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "books", itemRelation = "book")
public class BookModel extends RepresentationModel<BookModel> {
  private Integer id;

  private String title;

  private String author;

  private String downloadUrl;

  private Integer totalPages;

  private Integer rating;

  private String isbn;

  private Date publishedDate;

  private String publisherId;

  private CategoryModel category;

  private Date createdAt;

  private Date updatedAt;
}
