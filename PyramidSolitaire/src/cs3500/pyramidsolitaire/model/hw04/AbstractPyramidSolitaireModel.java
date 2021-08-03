package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Deck;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class for Pyramid Solitaire games that contains the default functionality for
 * a game of Basic Pyramid Solitaire.
 */
public abstract class AbstractPyramidSolitaireModel implements PyramidSolitaireModel<Card> {
  protected List<ArrayList<Card>> board = new ArrayList<ArrayList<Card>>();
  protected List<Card> draw = new ArrayList<Card>();
  protected List<Card> stock;
  protected int numRows = -1;
  protected boolean started = false;

  @Override
  public List<Card> getDeck() {
    new Deck();
    return Deck.getDeck();
  }

  /**
   * Checks if the deck is valid.
   * @param deck  the deck to be used in the game
   * @return      true if the deck is valid and false otherwise
   */
  protected boolean validDeck(List<Card> deck) {
    List<Card> check = this.getDeck();
    List<Card> in = new ArrayList<>(deck);
    Collections.sort(check);
    Collections.sort(in);
    if (in.size() != check.size()) {
      return false;
    }
    for (int i = 0; i < in.size(); i++) {
      if (!in.get(i).equals(check.get(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Initializes and updates the stock when necessary.
   * @param numDraw number of cards in stock
   */
  private void drawPile(List<Card> stock, int numDraw) {
    for (int x = 0; x < numDraw; x++) {
      if (!stock.isEmpty()) {
        Card c = Deck.getTop(stock);
        draw.add(c);
      }
    }
    this.stock = stock;
  }

  /**
   * Initializes the board for the game.
   * @param deck deck used in game
   */
  protected List<Card> buildBoard(List<Card> deck) {
    List<Card> copy = new ArrayList<>(deck);
    for (int i = 0; i < this.numRows; i++) {
      ArrayList<Card> in = new ArrayList<>();
      for (int j = 0; j <= i; j++) {
        Card c = Deck.getTop(copy);
        in.add(c);
      }
      board.add(in);
    }
    return copy;
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException {
    if (numRows <= 0 || numRows > 9 || numDraw < 0) {
      throw new IllegalArgumentException("Invalid board inputs");
    }
    else if (!validDeck(deck) || deck == null) {
      throw new IllegalArgumentException("Invalid deck");
    }
    else if (started) {
      board.clear();
      stock.clear();
      draw.clear();
      started = false;
    }
    this.board = new ArrayList<ArrayList<Card>>();
    this.draw = new ArrayList<>();
    this.stock = new ArrayList<>();
    this.numRows = numRows;
    List<Card> copy = new ArrayList<>(deck);
    if (shuffle) {
      Deck.shuffle(copy);
    }
    copy = buildBoard(copy);
    if (numDraw > copy.size()) {
      throw new IllegalArgumentException("Invalid. numDraw too large");
    }
    drawPile(copy, numDraw);
    started = true;
  }

  /**
   * Helper method that checks if the game has started.
   * @throws IllegalStateException is game has not started
   */
  protected void isStarted() {
    if (!started) {
      throw new IllegalStateException("Game has not yet been started");
    }
  }

  /**
   * Helper method that sets removed cards to USED.
   * @param row row of card that was removed
   * @param card card's position in row
   */
  protected void removeHelper(int row, int card) {
    board.get(row).set(card,null);
  }

  /**
   * Helper method that checks if the inputted indexes are valid for a remove2 command.
   * @param row1 Row of card 1.
   * @param card1 Position in row of card 1.
   * @param row2 Row of card 2.
   * @param card2 Position in row of card 2.
   * @throws IllegalArgumentException if it is an invalid remove2.
   */
  protected void checkValidRemove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException {
    if (row1 < 0 || row1 >= getNumRows() || row2 < 0 || row2 >= getNumRows() || card1 < 0
        || card1 >= board.get(row1).size() || card2 < 0 || card2 >= board.get(row2).size()) {
      throw new IllegalArgumentException("Invalid position selected");
    }
    if ((row1 == row2) && (card1 == card2)) {
      throw new IllegalArgumentException("Selected same card twice");
    }
    if (!isExposed(row1,card1) && !isExposed(row2,card2)) {
      throw new IllegalArgumentException("Both selected cards are not exposed");
    }
    if (!isExposed(row1,card1) || !isExposed(row2,card2)) {
      throw new IllegalArgumentException("A selected card is not exposed");
    }
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException {
    isStarted();
    checkValidRemove(row1,card1,row2,card2);

    Card c1 = board.get(row1).get(card1);
    Card c2 = board.get(row2).get(card2);

    if (c1.getValue() + c2.getValue() != 13) {
      throw new IllegalArgumentException("Selected cards do not add to 13");
    }
    else {
      removeHelper(row1,card1);
      removeHelper(row2,card2);
    }
  }

  @Override
  public void remove(int row, int card) throws IllegalArgumentException, IllegalStateException {
    isStarted();
    if (row < 0 || row >= getNumRows()
        || card < 0 || card >= board.get(row).size()) {
      throw new IllegalArgumentException("Invalid position selected");
    }
    if (!isExposed(row,card)) {
      throw new IllegalArgumentException("Selected card is not exposed");
    }
    Card c1 = board.get(row).get(card);
    if (c1.getValue() != 13) {
      throw new IllegalArgumentException("Selected card was not a King");
    }
    removeHelper(row,card);
  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    isStarted();
    if (row < 0 || row >= numRows || card < 0 || card >= board.get(row).size()
        || drawIndex >= draw.size() || drawIndex < 0) {
      throw new IllegalArgumentException("Invalid index selected");
    }
    if (!isExposed(row,card)) {
      throw new IllegalArgumentException("Selected card is not exposed");
    }
    if (draw.isEmpty()) {
      throw new IllegalArgumentException("Draw pile is empty");
    }
    Card c1 = board.get(row).get(card);
    if (c1.getValue() + draw.get(drawIndex).getValue() != 13) {
      throw new IllegalArgumentException("Selected cards do not add to 13");
    }
    removeHelper(row,card);
    draw.remove(drawIndex);
    if (draw.isEmpty() && stock.isEmpty()) {
      draw.clear();
    }
    else {
      drawPile(stock,1);
    }
  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalArgumentException, IllegalStateException {
    isStarted();
    if (draw.isEmpty()) {
      throw new IllegalArgumentException("Draw pile is empty.");
    }
    if (drawIndex < 0 || drawIndex > draw.size() - 1) {
      throw new IllegalArgumentException("Index is invalid.");
    }
    draw.remove(drawIndex);
    drawPile(stock,1);
  }

  @Override
  public int getNumRows() {
    if (!started) {
      return -1;
    }
    else {
      return this.numRows;
    }
  }

  @Override
  public int getNumDraw() {
    if (!started) {
      return -1;
    }
    else {
      return draw.size();
    }
  }

  @Override
  public int getRowWidth(int row) throws IllegalArgumentException, IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Game hasn't been started yet");
    }
    else if (row < 0 || row >= numRows) {
      throw new IllegalArgumentException("Coordinates are invalid");
    }
    else {
      return board.get(row).size();
    }
  }

  /**
   * Helper method that checks if the card at the entered index is exposed.
   * @param row row of card that is being checked
   * @param card card's position in row
   * @return true if the card is exposed, false if not
   */
  protected boolean isExposed(int row, int card) {
    if (row < 0 || row >= getNumRows() || card < 0 || card >= board.get(row).size()) {
      throw new IllegalArgumentException("Invalid card coordinates");
    }
    // Already used
    if (board.get(row).get(card) == null) {
      return false;
    }
    // On bottom row of pyramid
    else if (row == getNumRows() - 1) {
      return true;
    }
    else {
      return board.get(row + 1).get(card) == null && board.get(row + 1).get(card + 1) == null;
    }
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    isStarted();
    if (getScore() == 0) {
      return true;
    }
    if (draw.size() != 0 && stock.size() != 0) {
      return false;
    }
    int v1;
    int v2;

    List<Card> exposed = new ArrayList<>();
    for (int i = 0; i < board.size(); i++) {
      for (int j = 0; j < board.get(i).size(); j++) {
        if (isExposed(i,j)) {
          exposed.add(board.get(i).get(j));
        }
      }
    }

    exposed.addAll(draw);
    exposed.addAll(stock);
    for (int i = 0; i < exposed.size(); i++) {
      for (int j = 0; j < exposed.size(); j++) {
        if (i != j) {
          v1 = exposed.get(i).getValue();
          v2 = exposed.get(j).getValue();
          if (v1 + v2 == 13 || v1 == 13 || v2 == 13) {
            exposed.clear();
            return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public int getScore() throws IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Game hasn't been started yet");
    }
    else {
      int score = 0;
      for (ArrayList<Card> cards : board) {
        for (Card card : cards) {
          if (card != null) {
            score += card.getValue();
          }
        }
      }
      return score;
    }
  }

  @Override
  public Card getCardAt(int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Game hasn't been started yet");
    }
    if (row < 0 || row >= numRows || card < 0 || card >= getRowWidth(row)) {
      throw new IllegalArgumentException("Coordinates are invalid");
    }
    if (board.get(row).get(card) != null) {
      return board.get(row).get(card);
    }
    else {
      return null;
    }
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Game hasn't been started yet");
    }
    else {
      return draw;
    }
  }
}
