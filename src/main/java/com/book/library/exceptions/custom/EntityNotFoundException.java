package com.book.library.exceptions.custom;

/*
 *@author tobi
 * created on 07/05/2020
 *
 */


import com.book.library.util.HelperClass;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(Class clazz, String... searchParamsMap) {
    super(
      HelperClass.generateMessage(
        clazz.getSimpleName(),
        HelperClass.toMap(String.class, String.class, searchParamsMap)
      )
    );
  }
}
