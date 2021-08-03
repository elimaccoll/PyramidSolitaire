package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Deck;
import java.util.ArrayList;
import java.util.List;

/**
 * Representation of the game variant MultiPyramidSolitaire with three pyramids.
 */
public class MultiPyramidSolitaire extends AbstractPyramidSolitaireModel {

  @Override
  protected List<Card> buildBoard(List<Card> deck) {
    List<Card> copy = new ArrayList<>(deck);
    int space = multiBoardHelper(numRows / 2);
    int numGaps = numRows;
    // If MultiPyramid is one row, then there will be 3 cards.
    if (numRows == 1) {
      numGaps = 1;
    }
    // If the MultiPyramid has an even number of rows add .'s on an extra row.
    if (numRows % 2 == 0) {
      numGaps++;
    }

    Card c;
    // Building the MultiPyramid
    for (int i = 0; i < numRows; i++) {
      ArrayList<Card> in = new ArrayList<>();
      // Each row in the pyramid has a number of cards equal to one less than row + numGaps.
      for (int j = 0; j < i + numGaps; j++) {
        if (j % (space + i) < i + 1) {
          c = Deck.getTop(copy);
        }
        else {
          c = null;
        }
        in.add(c);
      }
      space = multiBoardHelper(space - 1);
      board.add(in);
    }
    return copy;
  }

  /**
   * Helper function for buildBoard in the MultiPyramid game type that helps establish
   * where the gaps between the multiple pyramids by determining when to truncate a row.
   * @param x integer that is being determined if it needs to be truncated.
   * @return Either returns x or returns 1 if x is less than 2.
   */
  private int multiBoardHelper(int x) {
    if (x < 2) {
      return 1;
    }
    return x;
  }

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<Card>();
    List<String> suits = List.of("♣", "♦", "♥", "♠");
    for (String s: suits) {
      for (int i = 1; i < 14; i++) {
        deck.add(new Card(s,i));
        deck.add(new Card(s,i));
      }
    }
    return deck;
  }
}
