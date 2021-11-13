package com.book.library.dao;

import com.book.library.util.annotation.Isbn;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class CategoryDao {
    private String nameOfCategory;
}
