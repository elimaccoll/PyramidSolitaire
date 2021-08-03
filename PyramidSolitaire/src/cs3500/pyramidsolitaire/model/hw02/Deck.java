package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representation of a deck of playing cards from class Card used in the game.
 * A deck is initialized with 52 cards in order.
 */
public class Deck {

  protected static List<Card> deck;

  List<String> suits = List.of("♣", "♦", "♥", "♠");

  /**
   * Constructor that initializes a deck of cards into a list.
   * Goes from Ace to King for each suit, Club to Spade.
   */
  public Deck() {
    deck = new ArrayList<>();

    for (int s = 0; s < suits.size(); s++) {
      for (int v = 1; v < 14; v++) {
        deck.add(new Card(suits.get(s), v));
      }
    }
  }

  /**
   * Shuffles the given deck of cards.
   * @param d deck of cards
   */
  public static void shuffle(List d) {
    Collections.shuffle(d);
  }

  /**
   * Returns a deck of cards as a list.
   * @return deck of cards in a list
   */
  public static List<Card> getDeck() {
    return deck;
  }

  /**
   * Returns the card at the top of the deck and removes it from the deck.
   * @return next card in the deck
   */
  public static Card getTop(List<Card> d) {
    return d.remove(0);
  }

  @Override
  public String toString() {
    StringBuilder d = new StringBuilder();
    for (Card c : deck) {
      d.append(c + "\n");
    }
    String out = d.toString();
    return out;
  }
}

