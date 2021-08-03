package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.Card;

/**
 * Representation of the game variant RelaxedPyramidSolitaire with the removing half exposed cards.
 */
public class RelaxedPyramidSolitaire extends AbstractPyramidSolitaireModel {

  @Override
  public boolean isGameOver() throws IllegalStateException {
    super.isStarted();
    if (super.isGameOver()) {
      for (int i = 0; i < this.board.size() - 1; i++) {
        for (int j = 0; j < this.board.get(i).size() - 1; j++) {
          if (this.isHalfExposed(i, j)) {
            if (super.isExposed(i + 1,j) && board.get(i).get(j).getValue()
                + board.get(i + 1).get(j).getValue() == 13) {
              return false;
            }
            if (super.isExposed(i + 1,j + 1) && board.get(i).get(j).getValue()
                + board.get(i + 1).get(j + 1).getValue() == 13) {
              return false;
            }
          }
        }
      }
    }
    return super.isGameOver();
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException {
    super.isStarted();
    try {
      super.remove(row1,card1,row2,card2);
    } catch (IllegalArgumentException e) {
      checkValidRemoveRelaxed(row1,card1,row2,card2);

      Card c1 = board.get(row1).get(card1);
      Card c2 = board.get(row2).get(card2);
      if (c1.getValue() + c2.getValue() != 13) {
        throw new IllegalArgumentException("Selected cards do not add to 13.");
      }
      super.removeHelper(row1,card1);
      super.removeHelper(row2,card2);
    }
  }

  /**
   * Helper method that checks if the inputted indexes are valid for a remove2 Relaxed command.
   * @param row1 Row of card 1.
   * @param card1 Position in row of card 1.
   * @param row2 Row of card 2.
   * @param card2 Position in row of card 2.
   * @throws IllegalArgumentException if it is an invalid remove2 according to Relaxed rules.
   */
  private void checkValidRemoveRelaxed(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException {
    if (row1 < 0 || row1 >= getNumRows() || row2 < 0 || row2 >= getNumRows() || card1 < 0
        || card1 >= board.get(row1).size() || card2 < 0 || card2 >= board.get(row2).size()) {
      throw new IllegalArgumentException("Invalid position selected");
    }
    if ((row1 == row2) && (card1 == card2)) {
      throw new IllegalArgumentException("Selected same card twice");
    }
    if (!((row1 == row2 + 1) && (card1 == card2 || card1 == card2 + 1)
        || (row2 == row1 + 1) && (card1 == card2 || card2 == card1 + 1))) {
      throw new IllegalArgumentException("Not a valid pair for a relaxed remove.");
    }
    if (!(this.isExposed(row1,card1) || this.isExposed(row2,card2))) {
      throw new IllegalArgumentException("Neither card is exposed");
    }
    if (!(this.isHalfExposed(row1,card1) || this.isHalfExposed(row2,card2))) {
      throw new IllegalArgumentException("Neither card is half exposed");
    }
  }

  /**
   * Helper method for the RelaxedPyramidSolitaire version of remove, which allows
   * for removing half exposed cards.  Checks if the entered card position is valid
   * and whether or not it is half exposed.
   * @param row Row of card position.
   * @param card Position of the card in the row.
   * @return true if the card is half exposed, false if not.
   */
  private boolean isHalfExposed(int row, int card) throws IllegalArgumentException {
    if (row < 0 || row >= getNumRows() || card < 0 || card >= board.get(row).size()) {
      throw new IllegalArgumentException("Invalid card coordinates");
    }
    // Already used
    if (board.get(row).get(card) == null) {
      return false;
    }
    // On bottom row of pyramid
    else if (row == getNumRows() - 1) {
      return false;
    }
    // Check if half exposed, return true if yes
    else {
      return checkRelaxedRemovePair(board.get(row + 1).get(card) == null,
          board.get(row + 1).get(card + 1) == null);
    }
  }

  /**
   * Helper method for isHalfExposed that does the check
   * to see if the card is half exposed.
   * @param one the card in the same position but one row down.
   * @param two the card in one row down and one position greater.
   * @return true if the card is half exposed, false if not.
   */
  private boolean checkRelaxedRemovePair(boolean one, boolean two) {
    return (one && !two) || (two && !one);
  }
}