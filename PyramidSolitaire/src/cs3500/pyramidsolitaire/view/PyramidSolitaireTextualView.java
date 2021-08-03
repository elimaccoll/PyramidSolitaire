package cs3500.pyramidsolitaire.view;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.IOException;
import java.util.List;

/**
 * Represents the textual view of the current game state of basic pyramid solitaire.
 */
public class PyramidSolitaireTextualView implements PyramidSolitaireView {
  private final PyramidSolitaireModel<?> model;
  private final Appendable out;

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
    this.out = null;
  }

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable ap) {
    this.model = model;
    this.out = ap;
  }

  @Override
  public String toString() {
    StringBuilder out = new StringBuilder();
    StringBuilder layer = new StringBuilder();
    // Case 1 - Game not started
    if (this.model.getNumDraw() == -1) {
      return "";
    }
    // Case 2 - Pyramid is emptied
    else if (this.model.getScore() == 0) {
      return "You win!";
    }
    // Case 3 - No remaining moves
    else if (this.model.isGameOver()) {
      return "Game over. Score: " + this.model.getScore();
    }
    // Case 4 - Otherwise render the game
    else {
      out.delete(0, out.length());
      for (int i = 0; i < model.getNumRows() - 1; i++) {
        layer.append("  ");
      }
      for (int i = 0; i < model.getNumRows(); i++) {
        // Space before the first value being displayed
        out.append(layer);
        for (int j = 0; j < model.getRowWidth(i); j++) {
          Card c = (Card) model.getCardAt(i,j);
          if (c == null) {
            if (j == 0 && i != 0) {
              out.append(".  ");
            }
            else if (j == model.getRowWidth(i) - 1) {
              out.append(" .");
            }
            else {
              out.append(" .  ");
            }
          }
          else {
            String card = c.toString();
            out.append(card);
            if (j != model.getRowWidth(i) - 1) {
              // Space between each value
              if (c.getValue() == 10) {
                out.append(" ");
              }
              else {
                out.append("  ");
              }
            }
          }
        }
        layer.delete(0,2);
        out.append("\n");
      }
      // Draw Pile
      if (model.getDrawCards().isEmpty()) {
        out.append("Draw:");
      }
      else {
        out.append("Draw: ");
        for (int x = 0; x < model.getNumDraw(); x++) {
          List<Card> draw = (List<Card>) model.getDrawCards();
          Card d = draw.get(x);
          out.append(d.toString());
          if (x != draw.size() - 1) {
            out.append(", ");
          }
        }
      }
    }
    return out.toString();
  }

  @Override
  public void render() throws IOException {
    assert this.out != null;
    this.out.append(this.toString());
  }
}