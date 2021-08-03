package cs3500.hw02;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Deck;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Contains tests for the BasicPyramidSolitaire game and its related methods.
 */
public class HW02Tests {
  PyramidSolitaireModel<Card> model = new BasicPyramidSolitaire();
  PyramidSolitaireTextualView view = new PyramidSolitaireTextualView(model);

  // start game tests
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidDeckStartNoShuffle() {
    new Deck();
    List<Card> d = model.getDeck();
    d.remove(13);
    model.startGame(d,false,7,1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidNumRowsStart() {
    model.startGame(model.getDeck(),true,-1,1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidDeckStartShuffle() {
    new Deck();
    List<Card> d = model.getDeck();
    d.remove(13);
    model.startGame(d,true,7,1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidStartNotEnoughCards() {
    model.startGame(model.getDeck(),true,10,1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidStartInvalidNumDraw() {
    model.startGame(model.getDeck(),true,7,-1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidStartDeckDuplicateCard() {
    List<Card> dup = model.getDeck();
    dup.remove(0);
    dup.add(dup.get(0));
    model.startGame(dup,true,7,1);
  }

  @Test
  public void testDealDoesNotDestroyDeckStartNoShuffle() {
    model.startGame(model.getDeck(), false, 5, 3);
    assertEquals(52, model.getDeck().size());
  }

  @Test
  public void testStartGame() {
    model.startGame(model.getDeck(),false,7,1);
    assertEquals(7,model.getNumRows());
    assertEquals(1,model.getNumDraw());
    Card expected = new Card("♥",3);
    assertEquals(expected, model.getDrawCards().get(0));
  }

  // remove tests (two cards from board)
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRemove2UnexposedCard() {
    model.startGame(model.getDeck(),true,7,1);
    model.remove(0,0,6,1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRemove2NotThirteen() {
    model.startGame(model.getDeck(),false,7,1);
    model.remove(6,0,6,1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRemove2SameCardTwice() {
    model.startGame(model.getDeck(),true,7,1);
    model.remove(6,0,6,0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRemove2NegativeIndex() {
    model.startGame(model.getDeck(),true,7,1);
    model.remove(6,0,6,-1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRemove2GreaterIndex() {
    model.startGame(model.getDeck(),true,7,1);
    model.remove(6,0,6,7);
  }

  @Test (expected = IllegalStateException.class)
  public void testInvalidRemove2GameNotStarted() {
    model.remove(6,0,6,1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRemove2ThirteenUnexposed() {
    model.startGame(model.getDeck(),false,7,1);
    model.remove(0,0,6,4);
  }

  @Test
  public void testRemove2() {
    model.startGame(model.getDeck(),false,7,1);
    model.remove(6,3,6,5);
    //Card used = new Card("♠",-1);
    assertEquals(null,model.getCardAt(6,3));
    assertEquals(null,model.getCardAt(6,5));
  }

  // remove tests (1 from board)
  @Test (expected = IllegalStateException.class)
  public void testInvalidRemove1GameNotStarted() {
    model.remove(6,0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRemove1NotThirteen() {
    model.startGame(model.getDeck(),false,7,1);
    model.remove(6,0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRemove1UnexposedCard() {
    model.startGame(model.getDeck(),true,7,1);
    model.remove(0,0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRemove1ThirteenUnexposed() {
    model.startGame(model.getDeck(),false,7,1);
    model.remove(4,2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRemove1NegativeIndex() {
    model.startGame(model.getDeck(),true,7,1);
    model.remove(6,-1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRemove1GreaterIndex() {
    model.startGame(model.getDeck(),true,7,1);
    model.remove(6,9);
  }

  @Test
  public void testRemove1() {
    model.startGame(model.getDeck(),false,7,1);
    model.remove(6,4);
    //Card used = new Card("♠",-1);
    assertEquals(null,model.getCardAt(6,4));
  }

  // removeUsingDraw tests
  @Test (expected = IllegalStateException.class)
  public void testInvalidRemoveUsingDrawGameNotStarted() {
    model.removeUsingDraw(0,6,0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRemoveUsingDrawUnexposedCard() {
    model.startGame(model.getDeck(),false,7,1);
    model.removeUsingDraw(0,3,0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRemoveUsingDrawNotThirteen() {
    model.startGame(model.getDeck(),false,7,1);
    model.removeUsingDraw(0,6,2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRemoveUsingDrawNegativeBoardIndex() {
    model.startGame(model.getDeck(),false,7,1);
    model.removeUsingDraw(0,6,-1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRemoveUsingDrawGreaterBoardIndex() {
    model.startGame(model.getDeck(),false,7,1);
    model.removeUsingDraw(0,6,7);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRemoveUsingDrawNegativeDrawIndex() {
    model.startGame(model.getDeck(),false,7,1);
    model.removeUsingDraw(-1,6,1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRemoveUsingDrawGreaterDrawIndex() {
    model.startGame(model.getDeck(),false,7,1);
    model.removeUsingDraw(1,6,1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRemoveUsingDrawEmptyDrawPile() {
    model.startGame(model.getDeck(),false,7,1);
    while (model.getNumDraw() > 0) {
      model.discardDraw(0);
    }
    model.removeUsingDraw(0,6,1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRemoveUsingDrawThirteenUnexposed() {
    model.startGame(model.getDeck(),false,7,1);
    model.removeUsingDraw(0,3,3);
  }

  @Test
  public void testRemoveUsingDraw() {
    model.startGame(model.getDeck(),false,7,1);
    model.removeUsingDraw(0,6,1);
    Card nextDraw = new Card("♥",4);
    //Card used = new Card("♠",-1);
    assertEquals(nextDraw, model.getDrawCards().get(0));
    assertEquals(null, model.getCardAt(6,1));
  }

  // discardDraw tests
  @Test (expected = IllegalStateException.class)
  public void testDiscardDrawGameNotStarted() {
    model.discardDraw(0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testDiscardDrawGreaterIndex() {
    model.startGame(model.getDeck(),false,7,4);
    model.discardDraw(5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testDiscardDrawNegativeIndex() {
    model.startGame(model.getDeck(),false,7,4);
    model.discardDraw(-1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testDiscardDrawNoCardPresent() {
    model.startGame(model.getDeck(),false,7,1);
    while (model.getNumDraw() > 0) {
      model.discardDraw(0);
    }
    model.discardDraw(0);
  }

  @Test
  public void testDiscardDraw() {
    model.startGame(model.getDeck(),false,7,1);
    model.discardDraw(0);
    List<Card> newDrawPile = model.getDrawCards();
    Card expected = new Card("♥",4);
    assertEquals(expected, newDrawPile.get(0));
  }

  // getNumRows tests
  @Test
  public void testGetNumRows() {
    model.startGame(model.getDeck(),true,7,1);
    assertEquals(7, model.getNumRows());
  }

  @Test
  public void testGetNumRowsGameNotStarted() {
    assertEquals(-1, model.getNumRows());
  }

  // getNumDraw tests
  @Test
  public void testGetNumDraw() {
    model.startGame(model.getDeck(),true,7,1);
    assertEquals(1, model.getNumDraw());
  }

  @Test
  public void testGetNumDrawGameNotStarted() {
    assertEquals(-1, model.getNumDraw());
  }

  @Test
  public void testGetNumDrawAfterEmptyStock() {
    model.startGame(model.getDeck(),true,7,1);
    while (model.getNumDraw() > 0) {
      model.discardDraw(0);
    }
    assertEquals(0, model.getNumDraw());
  }

  // getRowWidth tests
  @Test
  public void testGetRowWidth() {
    model.startGame(model.getDeck(),true,7,1);
    assertEquals(7, model.getRowWidth(6));
  }

  @Test (expected = IllegalStateException.class)
  public void testGetRowWidthGameNotStarted() {
    model.getRowWidth(6);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidGetRowWidth() {
    model.startGame(model.getDeck(),true,7,1);
    model.getRowWidth(9);
  }

  // isGameOver tests
  @Test
  public void testIsGameOverFalse() {
    model.startGame(model.getDeck(),false,7,1);
    assertFalse(model.isGameOver());
  }

  @Test
  public void testIsGameOverTrueLost() {
    model.startGame(model.getDeck(), false, 1, 51);
    while (model.getDrawCards().size() != 0) {
      model.discardDraw(0);
    }
    assertTrue(model.isGameOver());
  }

  @Test
  public void testIsGameOverTrueWon() {
    model.startGame(model.getDeck(), false, 1, 51);
    model.removeUsingDraw(10,0,0);
    assertTrue(model.isGameOver());
  }

  @Test (expected = IllegalStateException.class)
  public void testIsGameOverGameNotStarted() {
    model.isGameOver();
  }

  // getScore tests
  @Test
  public void testGetScoreGameWon() {
    model.startGame(model.getDeck(), false, 1, 51);
    model.removeUsingDraw(10,0,0);
    assertEquals(0,model.getScore());
  }

  @Test (expected = IllegalStateException.class)
  public void testGetScoreGameNotStarted() {
    model.getScore();
  }

  @Test
  public void testGetScore() {
    model.startGame(model.getDeck(),false,7,1);
    assertEquals(185,model.getScore());
  }

  // getCardAt tests
  @Test (expected = IllegalStateException.class)
  public void testGetCardAtGameNotStarted() {
    model.getCardAt(0,0);
  }

  @Test
  public void testGetCardAt() {
    model.startGame(model.getDeck(),false,7,1);
    Card c = new Card("♣",1);
    assertEquals(c, model.getCardAt(0,0));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetCardAtGreaterCoordinates() {
    model.startGame(model.getDeck(),false,7,1);
    model.getCardAt(8,1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetCardAtNegativeCoordinates() {
    model.startGame(model.getDeck(),false,7,1);
    model.getCardAt(-1,1);
  }

  // getDrawCards tests
  @Test (expected = IllegalStateException.class)
  public void testGetDrawCardsGameNotStarted() {
    model.getDrawCards();
  }

  @Test
  public void testGetDrawCards() {
    model.startGame(model.getDeck(),false,7,1);
    Card c = new Card("♥",3);
    List<Card> check = new ArrayList<Card>();
    check.add(c);
    assertEquals(check, model.getDrawCards());
  }

  @Test
  public void testGetDrawCardsEmptyDrawPile() {
    model.startGame(model.getDeck(),false,7,1);
    while (model.getNumDraw() > 0) {
      model.discardDraw(0);
    }
    List<Card> empty = new ArrayList<Card>();
    assertEquals(empty,model.getDrawCards());
  }

  // TextualView tests
  @Test
  public void testTextualViewGameNotStarted() {
    assertEquals("",view.toString());
  }

  @Test
  public void testTextualViewGameWon() {
    model.startGame(model.getDeck(), false, 1, 51);
    model.removeUsingDraw(10,0,0);
    assertEquals("You win!",view.toString());
  }

  @Test
  public void testTextualViewGameLost() {
    model.startGame(model.getDeck(), false, 1, 51);
    while (model.getDrawCards().size() != 0) {
      model.discardDraw(0);
    }
    assertEquals("Game over. Score: 1",view.toString());
  }

  @Test
  public void testTextualViewRenderGame() {
    model.startGame(model.getDeck(), false, 7, 1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥", this.view.toString());
  }

  @Test
  public void testTextualViewEmptyDrawPile() {
    model.startGame(model.getDeck(), false, 1, 0);
    assertEquals("A♣\n" + "Draw:",view.toString());
  }

  // Card class tests
  @Test
  public void testCardToStringLetterValue() {
    model.startGame(model.getDeck(), false, 7, 1);
    Card c = new Card("♣",1);
    assertEquals(c.toString(), model.getCardAt(0,0).toString());
  }

  @Test
  public void testCardToStringNumValue() {
    model.startGame(model.getDeck(), false, 7, 1);
    Card c = new Card("♣",2);
    assertEquals(c.toString(), model.getCardAt(1,0).toString());
  }

  @Test
  public void testCardEqualsTrue() {
    Card c1 = new Card("♣",2);
    Card c2 = new Card("♣",2);
    assertTrue(c1.equals(c2));
  }

  @Test
  public void testCardEqualsFalse() {
    Card c1 = new Card("♣",3);
    Card c2 = new Card("♣",2);
    assertFalse(c1.equals(c2));
  }

  @Test
  public void testCardEqualsWrongObject() {
    Card c1 = new Card("♣",3);
    assertFalse(c1.equals("♣3"));
  }

  @Test
  public void testCardHashCodeCorrect() {
    Card c = new Card("♣",2);
    assertEquals(2,c.hashCode());
  }

  @Test
  public void testCardHashCodeIncorrect() {
    Card c = new Card("♣",2);
    assertFalse(3 == c.hashCode());
  }

  /*
  @Test
  public void testCardHashCodeCardUsed() {
    model.startGame(model.getDeck(), false, 7, 1);
    model.remove(6,4);
    assertEquals(0,model.getCardAt(6,4).hashCode());
  }
  */

  // Tests I missed
  @Test
  public void testDealCorrectlyNoShuffle() {
    model.startGame(model.getDeck(), false, 3, 1);
    assertEquals("    A♣\n" + "  2♣  3♣\n"
        + "4♣  5♣  6♣\n" + "Draw: 7♣", this.view.toString());
  }

  @Test
  public void testTextualViewAfterRemove() {
    model.startGame(model.getDeck(), false, 3, 1);
    model.removeUsingDraw(0,2,2);
    assertEquals("    A♣\n" + "  2♣  3♣\n"
        + "4♣  5♣   .\n" + "Draw: 8♣", this.view.toString());
  }

  @Test
  public void testTextualViewEmptyRow() {
    model.startGame(model.getDeck(), false, 3, 1);
    model.removeUsingDraw(0,2,2);
    model.removeUsingDraw(0,2,1);
    model.removeUsingDraw(0,2,0);
    assertEquals("    A♣\n" + "  2♣  3♣\n"
        + ".   .   .\n" + "Draw: 10♣", this.view.toString());
  }

  @Test
  public void testShuffleWorksStart() {
    List<Card> unshuffled = new ArrayList<>(model.getDeck());
    List<Card> shuffled = new ArrayList<>(model.getDeck());
    model.startGame(shuffled,true,3,1);
    boolean worked = true;
    if (unshuffled.size() != shuffled.size()) {
      worked = false;
    }
    Card top = new Card("♣",1);
    Card d = new Card("♣",7);
    if (model.getDrawCards().get(0) == d && model.getCardAt(0,0) == top) {
      worked = false;
    }
    assertTrue(worked);
  }

  // might have to add exception here
  @Test (expected = IllegalArgumentException.class)
  public void testCardInvalidSuit() {
    Card invalidSuit = new Card("suit",8);
    assertNotEquals("suit",invalidSuit.getSuit());
    assertNotEquals(8,invalidSuit.getValue());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCardInvalidGreaterValue() {
    Card invalidSuit = new Card("♣",15);
    assertNotEquals(15,invalidSuit.getValue());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCardInvalidNegativeValue() {
    Card invalidSuit = new Card("♣",-3);
    assertNotEquals(-3,invalidSuit.getValue());
  }

  @Test
  public void testCreateCardNum() {
    Card c = new Card("♣",2);
    assertEquals(2,c.getValue());
    assertEquals("♣",c.getSuit());
  }

  @Test
  public void testCreateCardFaceCardAndAce() {
    Card face = new Card("♣",11);
    Card ace = new Card("♣",1);
    assertEquals(11,face.getValue());
    assertEquals("♣",face.getSuit());
    assertEquals(1,ace.getValue());
    assertEquals("♣",ace.getSuit());
  }

  @Test
  public void testRestartGameInProgress() {
    model.startGame(model.getDeck(),false,7,1);
    assertEquals(7,model.getNumRows());
    assertEquals(1,model.getNumDraw());
    Card expected = new Card("♥",3);
    assertEquals(expected, model.getDrawCards().get(0));

    model.startGame(model.getDeck(),false,7,1);
    assertEquals(7,model.getNumRows());
    assertEquals(1,model.getNumDraw());
    assertEquals(expected, model.getDrawCards().get(0));
  }
}