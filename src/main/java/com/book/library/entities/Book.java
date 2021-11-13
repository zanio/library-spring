package com.book.library.entities;

import com.book.library.util.annotation.Isbn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String title;

    @NotNull
    private String author;

    private String downloadUrl;

    @NotNull
    private Integer totalPages;

    private Integer rating;

    @NotNull
    private String isbn;

    private Date publishedDate;

    private String publisherId;

    @OneToOne
    private Category category;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

}
