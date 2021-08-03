package cs3500.pyramidsolitaire.controller;

/**
 * Represents a quit game exception.
 */
public class QuitGame extends IllegalStateException {

  /**
   * Constructor for quit game exception.
   */
  public QuitGame() {
    // Used to break the while loop in play game if a 'q' or 'Q' is inputted.
  }
}
