package com.book.library.validator;


import com.book.library.util.HelperClass;
import com.book.library.util.annotation.Isbn;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@Slf4j
public class IsbnValidator implements ConstraintValidator<Isbn, String> {

  @Override
  public void initialize(Isbn constraintAnnotation) {}

  @Override
  public boolean isValid(String isbn, ConstraintValidatorContext context) {
    log.info("isbn -> {}", isbn);
    return HelperClass.isValidISBN(isbn);
  }
}
