package com.book.library.exceptions.custom;

/*
 * created on 07/05/2020
 *
 */

import com.book.library.util.HelperClass;

public class EntityAlreadyExistException extends RuntimeException {

  public EntityAlreadyExistException(Class clazz, String... searchParamsMap) {
    super(
      HelperClass.generateMessage(
        clazz.getSimpleName(),
        HelperClass.toMap(String.class, String.class, searchParamsMap),
        "Already exist for parameter"
      )
    );
  }
}
