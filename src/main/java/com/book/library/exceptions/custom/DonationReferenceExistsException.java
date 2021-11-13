package com.book.library.exceptions.custom;

public class DonationReferenceExistsException extends Throwable {

  public DonationReferenceExistsException(String message) {
    super(message);
  }
}
