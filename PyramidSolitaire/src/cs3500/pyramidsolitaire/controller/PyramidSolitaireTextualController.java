package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the Textual Controller for Pyramid Solitaire Game.
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {
  private final Readable in;
  private final Appendable out;

  /**
   * Constructor that accepts and stores a Readable input and Appendable output.
   * @param rd Readable input
   * @param ap Appendable output
   * @throws IllegalArgumentException if has null argument
   */
  public PyramidSolitaireTextualController(Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Readable or Appendable are Null");
    }
    this.in = rd;
    this.out = ap;
  }

  private void renderHelper(PyramidSolitaireTextualView view) throws IllegalStateException {
    try {
      view.render();
    } catch (IOException e) {
      throw new IllegalStateException("Render failed");
    }
  }

  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck, boolean shuffle,
      int numRows, int numDraw) {
    if (model == null || deck == null) {
      throw new IllegalArgumentException("Model or Deck is Null");
    }
    try {
      model.startGame(deck,shuffle,numRows,numDraw);
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException("Game cannot be started");
    }

    Scanner scan = new Scanner(this.in);
    PyramidSolitaireTextualView view = new PyramidSolitaireTextualView(model, out);

    while (!model.isGameOver()) {
      renderHelper(view);
      ControllerUtil.append(out,"\nScore: " + String.valueOf(model.getScore()) + "\n");
      String type;
      // Checks if there is input to be taken
      if (scan.hasNext()) {
        type = scan.next();
      }
      // Exits while loop if there is no input
      else {
        break;
      }
      if (type.equalsIgnoreCase("q")) {
        type = "quit";
      }
      IPyramidSolitaireCommand modelCommand = null;
      try {
        switch (type) {
          case "rm1":
            modelCommand = new RemoveOneCommand(model,out);
            break;
          case "rm2":
            modelCommand = new RemoveTwoCommand(model,out);
            break;
          case "rmwd":
            modelCommand = new RemoveWithDrawCommand(model,out);
            break;
          case "dd":
            modelCommand = new DiscardDrawCommand(model,out);
            break;
          case "quit":
            throw new QuitGame();
          default:
            ControllerUtil.append(out,"Invalid move. Play again. Invalid move type.\n");
            break;
        }
      } catch (QuitGame q) {
        break;
      }
      if (modelCommand != null) {
        try {
          modelCommand.executeCommand(scan);
        } catch (QuitGame q) {
          break;
        }
      }
    }
    if (model.isGameOver()) {
      renderHelper(view);
    }
    else {
      ControllerUtil.append(out,"Game quit!" + "\nState of game when quit:\n");
      renderHelper(view);
      ControllerUtil.append(out,"\nScore: " + String.valueOf(model.getScore()));
    }
    scan.close();
  }
}
