package cs3500.pyramidsolitaire;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import java.io.InputStreamReader;

/**
 * Entry point for the Pyramid Solitaire program.
 * Allows three game types: Basic, Relaxed, and MultiPyramid.
 */
public final class PyramidSolitaire {

  /**
   * Main method used to start and run a game of pyramid solitaire.
   * @param args User's command line input to run the game.
   */
  public static void main(String[] args) {
    PyramidSolitaireCreator factory = new PyramidSolitaireCreator();
    PyramidSolitaireModel<Card> model;
    int numRows = 0;
    int numDraw = 0;
    // Default pyramid size if only the game type is entered.
    if (args.length == 1) {
      numRows = 7;
      numDraw = 3;
    }
    // Set pyramid dimensions equal to user input.
    else if (args.length == 3) {
      numRows = Integer.parseInt(args[1]);
      numDraw = Integer.parseInt(args[2]);
    }
    // Invalid number of arguments for starting a pyramid solitaire game.
    else {
      throw new IllegalArgumentException("Invalid number of board arguments: Enter just gameType,"
          + "or gameType numRows numDraw");
    }
    // Invalid number of rows or draw pile entered.
    if (numRows <= 0 || numDraw < 0) {
      throw new IllegalArgumentException("Invalid row or draw value");
    }
    String gameType = args[0];
    System.out.println(gameType);
    switch (gameType) {
      case "basic":
        model = factory.create(GameType.BASIC);
        if (numRows > 9) {
          throw new IllegalArgumentException("Too many rows for Basic game type.");
        }
        break;
      case "relaxed":
        model = factory.create(GameType.RELAXED);
        if (numRows > 9) {
          throw new IllegalArgumentException("Too many rows for Relaxed game type.");
        }
        break;
      case "multi":
        model = factory.create(GameType.MULTIPYRAMID);
        if (numRows > 8) {
          throw new IllegalArgumentException("Too many rows for MultiPyramid game type.");
        }
        break;
      default:
        throw new IllegalArgumentException("No game type provided.");
    }
    PyramidSolitaireTextualController play =
        new PyramidSolitaireTextualController(new InputStreamReader(System.in),System.out);
    play.playGame(model,model.getDeck(),false,numRows,numDraw);
  }
}
