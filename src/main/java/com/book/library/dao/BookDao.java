package com.book.library.dao;

import com.book.library.util.annotation.Isbn;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Setter
@Getter
@Builder
@ToString
public class BookDao {


    @NotNull
    @Size(min = 2)
    private String title;

    @NotBlank
    @Size(min = 2)
    private String author;

    private String downloadUrl;

    @NotNull
    private Integer totalPages;

    @NotNull
    @Isbn
    private String isbn;

    @NotNull
    @Past
    private Date publishedDate;

    @NotBlank
    @Size(min = 2)
    private String publisherId;

}
