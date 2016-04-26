/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.smassive.daggerworkshopgdg.data.exception;

/**
 * Exception throw by the application when a Comic search can't return a valid result.
 */
public class ComicNotFoundException extends Exception {

  public ComicNotFoundException() {
    super();
  }

  public ComicNotFoundException(final String message) {
    super(message);
  }

  public ComicNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ComicNotFoundException(final Throwable cause) {
    super(cause);
  }
}
