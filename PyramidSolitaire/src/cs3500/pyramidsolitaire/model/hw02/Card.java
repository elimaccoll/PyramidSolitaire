package cs3500.pyramidsolitaire.model.hw02;


import java.util.List;

/**
 * Representation of a playing card used in Pyramid Solitaire.
 * Each card has a suit, a value, and an integer representing whether or not the
 * card is exposed on the pyramid.  A card is exposed when it has an exposed value of 0.
 */
public class Card implements Comparable<Card> {
  private final String suit;
  private final int value;
  static final List<String> suits = List.of("♣", "♦", "♥", "♠");
  static final List<Integer> values = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 , 13, 14);

  /**
   * Constructor for Card class that initializes the suit, value and exposed value to 2,
   * which indicates that the card is not exposed.
   * @param suit suit of the card
   * @param value rank of the card
   */
  public Card(String suit, int value) {
    if (suits.contains(suit) && values.contains(value)) {
      this.suit = suit;
      this.value = value;
    }
    else {
      throw new IllegalArgumentException("Invalid Card Arguments");
    }
  }

  /**
   * Returns the suit of the card.
   * @return suit of card
   */
  public String getSuit() {
    return suit;
  }

  /**
   * Returns the value of the card.
   * @return value of card
   */
  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    switch (getValue()) {
      case 1:
        str.append("A").append(suit);
        break;
      case 11:
        str.append("J").append(suit);
        break;
      case 12:
        str.append("Q").append(suit);
        break;
      case 13:
        str.append("K").append(suit);
        break;
      default:
        str.append(value).append(suit);
        break;
    }
    return str.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj instanceof Card) {
      Card other = (Card) obj;
      return (this.suit.equals(other.suit) && this.value == other.value);
    }
    return false;
  }

  @Override
  public int hashCode() {
    if (value > 0) {
      return value + (31 * suits.indexOf(suit));
    }
    else {
      return 0;
    }
  }

  @Override
  public int compareTo(Card o) {
    return this.hashCode() - o.hashCode();
  }
}

