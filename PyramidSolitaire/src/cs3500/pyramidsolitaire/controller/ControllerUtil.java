package cs3500.pyramidsolitaire.controller;

import java.io.IOException;

/**
 * Utility class for handling appendable errors.
 */
public class ControllerUtil {
  static void append(Appendable out, String message) throws IllegalStateException {
    if (out == null || message == null) {
      throw new IllegalStateException("Out or message is null.");
    }
    try {
      out.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Couldn't write to appendable.");
    }
  }
}
