package cs3500.hw04;

import static org.junit.Assert.assertEquals;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Deck;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import org.junit.Test;

/**
 * Test Class for MultiPyramidSolitaire created with PyramidSolitaireCreator.
 */
public class MultiPyramidSolitaireCreatorTests {

  // MultiPyramidSolitaire Factory Tests
  @Test
  public void testCreateMultiPyramidSolitaire() {
    assertEquals(new MultiPyramidSolitaire(),
        PyramidSolitaireCreator.create(GameType.MULTIPYRAMID));
  }

  PyramidSolitaireModel model = PyramidSolitaireCreator.create(GameType.MULTIPYRAMID);
  Reader in = new StringReader("");
  StringBuilder out = new StringBuilder();
  PyramidSolitaireTextualController controller =
      new PyramidSolitaireTextualController(in, out);

  @Test (expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    Reader r = new StringReader("");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(r, null);
    controller.playGame(model,model.getDeck(),false,3,1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullReadable() {
    StringBuilder r = new StringBuilder();
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(null, r);
    controller.playGame(model,model.getDeck(),false,3,1);
  }

  @Test
  public void testCommandQuit() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("q");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,2,1);
    assertEquals("  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "Game quit!\n" + "State of game when quit:\n" + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n"
        + "Draw: 4♣\n" + "Score: 16", out.toString());
  }

  @Test
  public void testMultiPyramidTextualViewNoGaps() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,2,1);
    assertEquals("  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "Game quit!\n" + "State of game when quit:\n" + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n"
        + "Draw: 4♣\n" + "Score: 16", out.toString());
  }

  @Test
  public void testMultiPyramidTextualViewGaps() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,4,1);
    assertEquals("      A♣   .  A♣   .  2♣\n" + "    2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "  5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n" + "Draw: K♣\n"
        + "Score: 156\n" + "Game quit!\n" + "State of game when quit:\n"
        + "      A♣   .  A♣   .  2♣\n" + "    2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "  5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n" + "Draw: K♣\n"
        + "Score: 156", out.toString());
  }

  // playGame tests
  @Test (expected = IllegalArgumentException.class)
  public void testPlayGameNullModel() {
    controller.playGame(null, model.getDeck(), false, 7, 1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPlayGameNullDeck() {
    controller.playGame(model, null, false, 7, 1);
  }

  @Test (expected = IllegalStateException.class)
  public void testPlayGameCannotStartInvalidDeckNoShuffle() {
    new Deck();
    List<Card> d = model.getDeck();
    d.remove(1);
    controller.playGame(model,d,false,7,1);
  }

  @Test (expected = IllegalStateException.class)
  public void testPlayGameCannotStartInvalidDeckShuffle() {
    new Deck();
    List<Card> d = model.getDeck();
    d.remove(1);
    controller.playGame(model,d,true,7,1);
  }

  @Test (expected = IllegalStateException.class)
  public void testPlayGameCannotStartLowNumRows() {
    controller.playGame(model,model.getDeck(),true,0,1);
  }

  @Test (expected = IllegalStateException.class)
  public void testPlayGameCannotStartNotEnoughCards() {
    controller.playGame(model,model.getDeck(),true,10,1);
  }

  @Test (expected = IllegalStateException.class)
  public void testPlayGameCannotStartNegativeNumDraw() {
    controller.playGame(model,model.getDeck(),true,7,-1);
  }

  @Test (expected = IllegalStateException.class)
  public void testPlayGameCannotStartLargeNumDraw() {
    controller.playGame(model,model.getDeck(),true,1,104);
  }

  @Test (expected = IllegalStateException.class)
  public void testPlayGameCannotStartInvalidDeck3DuplicateCards() {
    List<Card> dup = model.getDeck();
    dup.remove(0);
    dup.add(dup.get(0));
    dup.add(dup.get(0));
    controller.playGame(model,dup,true,7,1);
  }

  // rm1 tests
  @Test
  public void testRm1() {
    model.startGame(model.getDeck(),false,5,1);
    model.remove(4,0);
    assertEquals(null,model.getCardAt(4,0));
  }

  @Test
  public void testRm1Textual() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm1 5 1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,5,1);
    assertEquals("        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 198\n" + "        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + ".  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 185\n" + "Game quit!\n" + "State of game when quit:\n"
        + "        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + ".  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 185", out.toString());
  }

  @Test
  public void testRm1InvalidRowIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm1 B 5 1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,5,1);
    assertEquals("        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 198\n" + "B is an invalid input.\n"
        + "        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + ".  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 185\n" + "Game quit!\n" + "State of game when quit:\n"
        + "        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + ".  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 185", out.toString());
  }

  @Test
  public void testRm1InvalidCardIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm1 5 B 1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,5,1);
    assertEquals("        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 198\n" + "B is an invalid input.\n"
        + "        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + ".  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 185\n" + "Game quit!\n" + "State of game when quit:\n"
        + "        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + ".  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 185", out.toString());
  }

  @Test
  public void testRm1InvalidNotThirteen() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm1 5 3");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,5,1);
    assertEquals("        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 198\n" + "Invalid move. Play again. Invalid inputs for rm1.\n"
        + "        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 198\n" + "Game quit!\n" + "State of game when quit:\n"
        + "        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 198", out.toString());
  }

  @Test
  public void testRm1InvalidUnexposed() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm1 1 1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,5,1);
    assertEquals("        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 198\n" + "Invalid move. Play again. Invalid inputs for rm1.\n"
        + "        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 198\n" + "Game quit!\n" + "State of game when quit:\n"
        + "        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 198", out.toString());
  }

  @Test
  public void testRm1InvalidLargeIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm1 7 1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,5,1);
    assertEquals("        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 198\n" + "Invalid move. Play again. Invalid inputs for rm1.\n"
        + "        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 198\n" + "Game quit!\n" + "State of game when quit:\n"
        + "        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 198", out.toString());
  }

  @Test
  public void testRm1InvalidNegativeIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm1 5 -1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,5,1);
    assertEquals("        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 198\n" + "Invalid move. Play again. Invalid inputs for rm1.\n"
        + "        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 198\n" + "Game quit!\n" + "State of game when quit:\n"
        + "        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 198", out.toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRm1UnexposedThirteen() {
    model.startGame(model.getDeck(),false,6,1);
    model.remove(3,6);
  }

  @Test
  public void testRm1InvalidCardIndexQuit() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm1 2 B q");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,2,1);
    assertEquals("  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "B is an invalid input.\n" + "Game quit!\n" + "State of game when quit:\n"
        + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16", out.toString());
  }

  @Test
  public void testRm1InvalidRowIndexQuit() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm1 B q 3");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,2,1);
    assertEquals("  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "B is an invalid input.\n" + "Game quit!\n" + "State of game when quit:\n"
        + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16", out.toString());
  }

  // rm2 tests
  @Test
  public void testRm2() {
    model.startGame(model.getDeck(),false,8,1);
    model.remove(7,2,7,6);
    assertEquals(null,model.getCardAt(7,2));
    assertEquals(null,model.getCardAt(7,6));
  }

  @Test
  public void testRm2InvalidRowIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm2 B 2 1 2 2");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,2,1);
    assertEquals("  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "B is an invalid input.\n" + "Invalid move. Play again. Invalid inputs for rm2.\n"
        + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "Game quit!\n" + "State of game when quit:\n" + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n"
        + "Draw: 4♣\n" + "Score: 16", out.toString());
  }

  @Test
  public void testRm2InvalidCardIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm2 B 2 1 2 2");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,2,1);
    assertEquals("  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "B is an invalid input.\n" + "Invalid move. Play again. Invalid inputs for rm2.\n"
        + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "Game quit!\n" + "State of game when quit:\n" + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n"
        + "Draw: 4♣\n" + "Score: 16", out.toString());
  }

  @Test
  public void testRm2UnexposedCard() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm2 1 1 2 2");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,2,1);
    assertEquals("  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "Invalid move. Play again. Invalid inputs for rm2.\n"
        + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "Game quit!\n" + "State of game when quit:\n" + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n"
        + "Draw: 4♣\n" + "Score: 16", out.toString());
  }

  @Test
  public void testRm2UnexposedThirteen() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm2 3 2 3 4");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,5,1);
    assertEquals("        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 198\n" + "Invalid move. Play again. Invalid inputs for rm2.\n"
        + "        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 198\n" + "Game quit!\n" + "State of game when quit:\n"
        + "        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 198", out.toString());
  }

  @Test
  public void testRm2NotThirteen() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm2 2 1 2 2");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,2,1);
    assertEquals("  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "Invalid move. Play again. Invalid inputs for rm2.\n"
        + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "Game quit!\n" + "State of game when quit:\n" + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n"
        + "Draw: 4♣\n" + "Score: 16", out.toString());
  }

  @Test
  public void testRm2SameCardTwice() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm2 2 1 2 1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,2,1);
    assertEquals("  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "Invalid move. Play again. Invalid inputs for rm2.\n"
        + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "Game quit!\n" + "State of game when quit:\n" + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n"
        + "Draw: 4♣\n" + "Score: 16", out.toString());
  }

  @Test
  public void testRm2NegativeRowIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm2 -2 1 2 2");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,2,1);
    assertEquals("  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "Invalid move. Play again. Invalid inputs for rm2.\n"
        + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "Game quit!\n" + "State of game when quit:\n" + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n"
        + "Draw: 4♣\n" + "Score: 16", out.toString());
  }

  @Test
  public void testRm2NegativeCardIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm2 2 -1 2 2");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,2,1);
    assertEquals("  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "Invalid move. Play again. Invalid inputs for rm2.\n"
        + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "Game quit!\n" + "State of game when quit:\n" + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n"
        + "Draw: 4♣\n" + "Score: 16", out.toString());
  }

  @Test
  public void testRm2GreaterRowIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm2 7 1 2 2");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,2,1);
    assertEquals("  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "Invalid move. Play again. Invalid inputs for rm2.\n"
        + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "Game quit!\n" + "State of game when quit:\n" + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n"
        + "Draw: 4♣\n" + "Score: 16", out.toString());
  }

  @Test
  public void testRm2GreaterCardIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm2 2 8 2 2");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,2,1);
    assertEquals("  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "Invalid move. Play again. Invalid inputs for rm2.\n"
        + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "Game quit!\n" + "State of game when quit:\n" + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n"
        + "Draw: 4♣\n" + "Score: 16", out.toString());
  }

  @Test
  public void testRm2InvalidCardIndexQuit() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm2 2 B Q");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,2,1);
    assertEquals("  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "B is an invalid input.\n" + "Game quit!\n" + "State of game when quit:\n"
        + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16", out.toString());
  }

  @Test
  public void testRm2InvalidRowIndexQuit() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm2 B Q");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,2,1);
    assertEquals("  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "B is an invalid input.\n" + "Game quit!\n" + "State of game when quit:\n"
        + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16", out.toString());
  }

  // rmwd tests
  @Test
  public void testRmwd() {
    model.startGame(model.getDeck(),false,3,1);
    model.removeUsingDraw(0,2,3);
    assertEquals(null,model.getCardAt(2,3));
  }

  @Test
  public void testRmwdTextual() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd 1 3 4");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n"
        + "Draw: 7♣\n" + "Score: 42\n"
        + "    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣   .  6♣\n" + "Draw: 7♣\n"
        + "Score: 36\n" + "Game quit!\n" + "State of game when quit:\n" + "    A♣  A♣  2♣\n"
        + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣   .  6♣\n" + "Draw: 7♣\n"
        + "Score: 36", out.toString());
  }

  @Test
  public void testRmwdUnexposedCard() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd 1 2 4");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n"
        + "Draw: 7♣\n" + "Score: 42\n" + "Invalid move. Play again. Invalid inputs for rmwd.\n"
        + "    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42\n" + "Game quit!\n" + "State of game when quit:\n" + "    A♣  A♣  2♣\n"
        + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42", out.toString());
  }

  @Test
  public void testRmwdUnexposedThirteen() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("dd 1 rmwd 1 3 6");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,5,1);
    assertEquals("        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 4♦\n"
        + "Score: 198\n"
        + "        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 5♦\n"
        + "Score: 198\n" + "Invalid move. Play again. Invalid inputs for rmwd.\n"
        + "        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 5♦\n"
        + "Score: 198\n" + "Game quit!\n" + "State of game when quit:\n"
        + "        A♣   .  A♣   .  2♣\n" + "      2♣  3♣  3♣  4♣  4♣  5♣\n"
        + "    5♣  6♣  6♣  7♣  7♣  8♣  8♣\n" + "  9♣  9♣  10♣ 10♣ J♣  J♣  Q♣  Q♣\n"
        + "K♣  K♣  A♦  A♦  2♦  2♦  3♦  3♦  4♦\n" + "Draw: 5♦\n"
        + "Score: 198", out.toString());
  }

  @Test
  public void testRmwdNotThirteen() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd 1 2 1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n"
        + "Draw: 7♣\n" + "Score: 42\n" + "Invalid move. Play again. Invalid inputs for rmwd.\n"
        + "    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42\n" + "Game quit!\n" + "State of game when quit:\n" + "    A♣  A♣  2♣\n"
        + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42", out.toString());
  }

  @Test
  public void testRmwdNegativeRowIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd 1 -2 1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n"
        + "Draw: 7♣\n" + "Score: 42\n" + "Invalid move. Play again. Invalid inputs for rmwd.\n"
        + "    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42\n" + "Game quit!\n" + "State of game when quit:\n" + "    A♣  A♣  2♣\n"
        + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42", out.toString());
  }

  @Test
  public void testRmwdNegativeCardIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd 1 2 -1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n"
        + "Draw: 7♣\n" + "Score: 42\n" + "Invalid move. Play again. Invalid inputs for rmwd.\n"
        + "    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42\n" + "Game quit!\n" + "State of game when quit:\n" + "    A♣  A♣  2♣\n"
        + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42", out.toString());
  }

  @Test
  public void testRmwdGreaterRowIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd 1 5 1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n"
        + "Draw: 7♣\n" + "Score: 42\n" + "Invalid move. Play again. Invalid inputs for rmwd.\n"
        + "    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42\n" + "Game quit!\n" + "State of game when quit:\n" + "    A♣  A♣  2♣\n"
        + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42", out.toString());
  }

  @Test
  public void testRmwdGreaterCardIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd 1 2 6");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n"
        + "Draw: 7♣\n" + "Score: 42\n" + "Invalid move. Play again. Invalid inputs for rmwd.\n"
        + "    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42\n" + "Game quit!\n" + "State of game when quit:\n" + "    A♣  A♣  2♣\n"
        + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42", out.toString());
  }

  @Test
  public void testRmwdNegativeDrawIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd -1 2 1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n"
        + "Draw: 7♣\n" + "Score: 42\n" + "Invalid move. Play again. Invalid inputs for rmwd.\n"
        + "    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42\n" + "Game quit!\n" + "State of game when quit:\n" + "    A♣  A♣  2♣\n"
        + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42", out.toString());
  }

  @Test
  public void testRmwdGreaterDrawIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd 4 2 1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n"
        + "Draw: 7♣\n" + "Score: 42\n" + "Invalid move. Play again. Invalid inputs for rmwd.\n"
        + "    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42\n" + "Game quit!\n" + "State of game when quit:\n" + "    A♣  A♣  2♣\n"
        + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42", out.toString());
  }

  @Test
  public void testRmwdInvalidDrawIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd B 1 2 1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n"
        + "Draw: 7♣\n" + "Score: 42\n" + "B is an invalid input.\n"
        + "Invalid move. Play again. Invalid inputs for rmwd.\n"
        + "    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42\n" + "Game quit!\n" + "State of game when quit:\n" + "    A♣  A♣  2♣\n"
        + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42", out.toString());
  }

  @Test
  public void testRmwdInvalidRowIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd 1 B 2 1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n"
        + "Draw: 7♣\n" + "Score: 42\n" + "B is an invalid input.\n"
        + "Invalid move. Play again. Invalid inputs for rmwd.\n"
        + "    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42\n" + "Game quit!\n" + "State of game when quit:\n" + "    A♣  A♣  2♣\n"
        + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42", out.toString());
  }

  @Test
  public void testRmwdInvalidCardIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd 1 2 B 1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n"
        + "Draw: 7♣\n" + "Score: 42\n" + "B is an invalid input.\n"
        + "Invalid move. Play again. Invalid inputs for rmwd.\n"
        + "    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42\n" + "Game quit!\n" + "State of game when quit:\n" + "    A♣  A♣  2♣\n"
        + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42", out.toString());
  }

  @Test
  public void testRmwdInvalidDrawIndexQuit() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd B Q 2 1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n"
        + "Draw: 7♣\n" + "Score: 42\n" + "B is an invalid input.\n"
        + "Game quit!\n" + "State of game when quit:\n" + "    A♣  A♣  2♣\n"
        + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42", out.toString());
  }

  @Test
  public void testRmwdInvalidRowIndexQuit() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd 1 B Q 1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n"
        + "Draw: 7♣\n" + "Score: 42\n" + "B is an invalid input.\n"
        + "Game quit!\n" + "State of game when quit:\n" + "    A♣  A♣  2♣\n"
        + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42", out.toString());
  }

  @Test
  public void testRmwdInvalidCardIndexQuit() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd 1 2 B Q");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n"
        + "Draw: 7♣\n" + "Score: 42\n" + "B is an invalid input.\n"
        + "Game quit!\n" + "State of game when quit:\n" + "    A♣  A♣  2♣\n"
        + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 42", out.toString());
  }

  // dd test
  @Test
  public void testDd() {
    model.startGame(model.getDeck(), false, 2, 1);
    model.discardDraw(0);
    Card c = new Card("♣",5);
    assertEquals(c, model.getDrawCards().get(0));
  }

  @Test
  public void testDdTextual() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("dd 1 dd 1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n"
        + "Draw: 7♣\n" + "Score: 42\n"
        + "    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n"
        + "Draw: 7♣\n" + "Score: 42\n"
        + "    A♣  A♣  2♣\n" + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n"
        + "Draw: 8♣\n" + "Score: 42\n"
        + "Game quit!\n" + "State of game when quit:\n" + "    A♣  A♣  2♣\n"
        + "  2♣  3♣  3♣  4♣\n" + "4♣  5♣  5♣  6♣  6♣\n" + "Draw: 8♣\n"
        + "Score: 42", out.toString());
  }

  @Test
  public void testDdInvalidGreaterIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("dd 2");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,2,1);
    assertEquals("  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n"
        + "Draw: 4♣\n" + "Score: 16\n" + "Invalid move. Play again. Invalid input for dd.\n"
        + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "Game quit!\n" + "State of game when quit:\n" + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n"
        + "Draw: 4♣\n" + "Score: 16", out.toString());
  }

  @Test
  public void testDdInvalidNegativeIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("dd -1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,2,1);
    assertEquals("  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n"
        + "Draw: 4♣\n" + "Score: 16\n" + "Invalid move. Play again. Invalid input for dd.\n"
        + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n" + "Score: 16\n"
        + "Game quit!\n" + "State of game when quit:\n" + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n"
        + "Draw: 4♣\n" + "Score: 16", out.toString());
  }

  @Test
  public void testDDInvalidDrawIndex() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("dd B 1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,2,1);
    assertEquals("  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n"
        + "Draw: 4♣\n" + "Score: 16\n" + "B is an invalid input.\n"
        + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n"
        + "Draw: 5♣\n" + "Score: 16\n" + "Game quit!\n" + "State of game when quit:\n"
        + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 5♣\n"
        + "Score: 16", out.toString());
  }

  @Test
  public void testDdInvalidDrawIndexQuit() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("dd B Q");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,2,1);
    assertEquals("  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n"
        + "Draw: 4♣\n" + "Score: 16\n" + "B is an invalid input.\n"
        + "Game quit!\n" + "State of game when quit:\n"
        + "  A♣  A♣  2♣\n" + "2♣  3♣  3♣  4♣\n" + "Draw: 4♣\n"
        + "Score: 16", out.toString());
  }

  @Test
  public void testGameOverWin() {
    StringBuilder winOut = new StringBuilder();
    Reader win = new StringReader("rmwd 22 1 1");
    PyramidSolitaireTextualController winController =
        new PyramidSolitaireTextualController(win, winOut);
    winController.playGame(model,model.getDeck(),false,1,22);
    assertEquals("A♣\n" + "Draw: A♣, 2♣, 2♣, 3♣, 3♣, 4♣, 4♣, 5♣, 5♣, 6♣, 6♣, 7♣, 7♣, 8♣,"
        + " 8♣, 9♣, 9♣, 10♣, 10♣, J♣, J♣, Q♣\n" + "Score: 1\n"
        + "You win!", winOut.toString());
  }

  @Test
  public void testGameOverLoss() {
    StringBuilder loseOut = new StringBuilder();
    Reader lose = new StringReader("dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1"
        + " dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1"
        + " dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1"
        + " dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1"
        + " dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1"
        + " dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1"
        + " dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1"
        + " dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1");
    PyramidSolitaireTextualController loseController =
        new PyramidSolitaireTextualController(lose, loseOut);
    loseController.playGame(model,model.getDeck(),false,1,1);
    assertEquals("A♣\n" + "Draw: A♣\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 2♣\n" + "Score: 1\n" + "A♣\n" + "Draw: 2♣\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 3♣\n" + "Score: 1\n" + "A♣\n" + "Draw: 3♣\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 4♣\n" + "Score: 1\n" + "A♣\n" + "Draw: 4♣\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 5♣\n" + "Score: 1\n" + "A♣\n" + "Draw: 5♣\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 6♣\n" + "Score: 1\n" + "A♣\n" + "Draw: 6♣\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 7♣\n" + "Score: 1\n" + "A♣\n" + "Draw: 7♣\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 8♣\n" + "Score: 1\n" + "A♣\n" + "Draw: 8♣\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 9♣\n" + "Score: 1\n" + "A♣\n" + "Draw: 9♣\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 10♣\n" + "Score: 1\n" + "A♣\n" + "Draw: 10♣\n" + "Score: 1\n"
        + "A♣\n" + "Draw: J♣\n" + "Score: 1\n" + "A♣\n" + "Draw: J♣\n" + "Score: 1\n"
        + "A♣\n" + "Draw: Q♣\n" + "Score: 1\n" + "A♣\n" + "Draw: Q♣\n" + "Score: 1\n"
        + "A♣\n" + "Draw: K♣\n" + "Score: 1\n" + "A♣\n" + "Draw: K♣\n" + "Score: 1\n"
        + "A♣\n" + "Draw: A♦\n" + "Score: 1\n" + "A♣\n" + "Draw: A♦\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 2♦\n" + "Score: 1\n" + "A♣\n" + "Draw: 2♦\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 3♦\n" + "Score: 1\n" + "A♣\n" + "Draw: 3♦\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 4♦\n" + "Score: 1\n" + "A♣\n" + "Draw: 4♦\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 5♦\n" + "Score: 1\n" + "A♣\n" + "Draw: 5♦\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 6♦\n" + "Score: 1\n" + "A♣\n" + "Draw: 6♦\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 7♦\n" + "Score: 1\n" + "A♣\n" + "Draw: 7♦\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 8♦\n" + "Score: 1\n" + "A♣\n" + "Draw: 8♦\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 9♦\n" + "Score: 1\n" + "A♣\n" + "Draw: 9♦\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 10♦\n" + "Score: 1\n" + "A♣\n" + "Draw: 10♦\n" + "Score: 1\n"
        + "A♣\n" + "Draw: J♦\n" + "Score: 1\n" + "A♣\n" + "Draw: J♦\n" + "Score: 1\n"
        + "A♣\n" + "Draw: Q♦\n" + "Score: 1\n" + "A♣\n" + "Draw: Q♦\n" + "Score: 1\n"
        + "A♣\n" + "Draw: K♦\n" + "Score: 1\n" + "A♣\n" + "Draw: K♦\n" + "Score: 1\n"
        + "A♣\n" + "Draw: A♥\n" + "Score: 1\n" + "A♣\n" + "Draw: A♥\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 2♥\n" + "Score: 1\n" + "A♣\n" + "Draw: 2♥\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 3♥\n" + "Score: 1\n" + "A♣\n" + "Draw: 3♥\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 4♥\n" + "Score: 1\n" + "A♣\n" + "Draw: 4♥\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 5♥\n" + "Score: 1\n" + "A♣\n" + "Draw: 5♥\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 6♥\n" + "Score: 1\n" + "A♣\n" + "Draw: 6♥\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 7♥\n" + "Score: 1\n" + "A♣\n" + "Draw: 7♥\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 8♥\n" + "Score: 1\n" + "A♣\n" + "Draw: 8♥\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 9♥\n" + "Score: 1\n" + "A♣\n" + "Draw: 9♥\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 10♥\n" + "Score: 1\n" + "A♣\n" + "Draw: 10♥\n" + "Score: 1\n"
        + "A♣\n" + "Draw: J♥\n" + "Score: 1\n" + "A♣\n" + "Draw: J♥\n" + "Score: 1\n"
        + "A♣\n" + "Draw: Q♥\n" + "Score: 1\n" + "A♣\n" + "Draw: Q♥\n" + "Score: 1\n"
        + "A♣\n" + "Draw: K♥\n" + "Score: 1\n" + "A♣\n" + "Draw: K♥\n" + "Score: 1\n"
        + "A♣\n" + "Draw: A♠\n" + "Score: 1\n" + "A♣\n" + "Draw: A♠\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 2♠\n" + "Score: 1\n" + "A♣\n" + "Draw: 2♠\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 3♠\n" + "Score: 1\n" + "A♣\n" + "Draw: 3♠\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 4♠\n" + "Score: 1\n" + "A♣\n" + "Draw: 4♠\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 5♠\n" + "Score: 1\n" + "A♣\n" + "Draw: 5♠\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 6♠\n" + "Score: 1\n" + "A♣\n" + "Draw: 6♠\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 7♠\n" + "Score: 1\n" + "A♣\n" + "Draw: 7♠\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 8♠\n" + "Score: 1\n" + "A♣\n" + "Draw: 8♠\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 9♠\n" + "Score: 1\n" + "A♣\n" + "Draw: 9♠\n" + "Score: 1\n"
        + "A♣\n" + "Draw: 10♠\n" + "Score: 1\n" + "A♣\n" + "Draw: 10♠\n" + "Score: 1\n"
        + "A♣\n" + "Draw: J♠\n" + "Score: 1\n" + "A♣\n" + "Draw: J♠\n" + "Score: 1\n"
        + "A♣\n" + "Draw: Q♠\n" + "Score: 1\n" + "A♣\n" + "Draw: Q♠\n" + "Score: 1\n"
        + "A♣\n" + "Draw: K♠\n" + "Score: 1\n" + "A♣\n" + "Draw: K♠\n" + "Score: 1\n"
        + "Game over. Score: 1", loseOut.toString());
  }
}
