/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.smassive.daggerworkshopgdg.data.exception;

import com.smassive.daggerworkshopgdg.domain.exception.ErrorBundle;

/**
 * Wrapper around Exceptions used to manage errors in the repository.
 */
public class RepositoryErrorBundle implements ErrorBundle {

  private final Exception exception;

  public RepositoryErrorBundle(Exception exception) {
    this.exception = exception;
  }

  public Exception getException() {
    return exception;
  }

  public String getErrorMessage() {
    String message = "";
    if (this.exception != null) {
      this.exception.getMessage();
    }
    return message;
  }
}
