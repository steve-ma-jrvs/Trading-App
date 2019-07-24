package ca.jrvs.apps.trading.dao;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String message) {
    super(message);
  }

  public ResourceNotFoundException(String message, Exception ex) {
    super(message, ex);
  }
}
