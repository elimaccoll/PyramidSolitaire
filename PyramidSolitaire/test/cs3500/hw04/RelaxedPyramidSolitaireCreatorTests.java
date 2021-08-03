package cs3500.hw04;

import static org.junit.Assert.assertEquals;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Deck;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import org.junit.Test;

/**
 * Test Class for RelaxedPyramidSolitaire created with PyramidSolitaireCreator.
 */
public class RelaxedPyramidSolitaireCreatorTests {

  // RelaxedPyramidSolitaire Factory Tests
  @Test
  public void testCreateRelaxedPyramidSolitaire() {
    assertEquals(new RelaxedPyramidSolitaire(),
        PyramidSolitaireCreator.create(GameType.RELAXED));
  }

  PyramidSolitaireModel model = PyramidSolitaireCreator.create(GameType.RELAXED);
  Reader in = new StringReader("");
  StringBuilder out = new StringBuilder();
  PyramidSolitaireTextualController controller =
      new PyramidSolitaireTextualController(in, out);

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
    controller.playGame(model,model.getDeck(),true,1,52);
  }

  @Test (expected = IllegalStateException.class)
  public void testPlayGameCannotStartInvalidDeckDuplicateCard() {
    List<Card> dup = model.getDeck();
    dup.remove(0);
    dup.add(dup.get(0));
    controller.playGame(model,dup,true,7,1);
  }

  // rm1 tests
  @Test
  public void testRm1() {
    StringBuilder rm1Out = new StringBuilder();
    Reader rm1 = new StringReader("rm1 7 5");
    PyramidSolitaireTextualController rm1Controller =
        new PyramidSolitaireTextualController(rm1, rm1Out);
    rm1Controller.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦   .  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 172\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦   .  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 172", rm1Out.toString());
  }

  @Test
  public void testRm1InvalidRowIndex() {
    StringBuilder rm1Out = new StringBuilder();
    Reader rm1 = new StringReader("rm1 B 7 1");
    PyramidSolitaireTextualController rm1Controller =
        new PyramidSolitaireTextualController(rm1, rm1Out);
    rm1Controller.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "B is an invalid input.\n" + "Invalid move. Play again. Invalid inputs for rm1.\n"
        + "            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rm1Out.toString());
  }

  @Test
  public void testRm1InvalidCardIndex() {
    StringBuilder rm1Out = new StringBuilder();
    Reader rm1 = new StringReader("rm1 7 B 1");
    PyramidSolitaireTextualController rm1Controller =
        new PyramidSolitaireTextualController(rm1, rm1Out);
    rm1Controller.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "B is an invalid input.\n" + "Invalid move. Play again. Invalid inputs for rm1.\n"
        + "            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rm1Out.toString());
  }

  @Test
  public void testRm1InvalidNotThirteen() {
    StringBuilder rm1Out = new StringBuilder();
    Reader rm1 = new StringReader("rm1 3 1");
    PyramidSolitaireTextualController rm1Controller =
        new PyramidSolitaireTextualController(rm1, rm1Out);
    rm1Controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 21\n" + "Invalid move. Play again. Invalid inputs for rm1.\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n" + "Game quit!\n"
        + "State of game when quit:\n" + "    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 21", rm1Out.toString());
  }

  @Test
  public void testRm1InvalidUnexposed() {
    StringBuilder rm1Out = new StringBuilder();
    Reader rm1 = new StringReader("rm1 2 1");
    PyramidSolitaireTextualController rm1Controller =
        new PyramidSolitaireTextualController(rm1, rm1Out);
    rm1Controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n"
        + "Invalid move. Play again. Invalid inputs for rm1.\n" + "    A♣\n" + "  2♣  3♣\n"
        + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n" + "Game quit!\n"
        + "State of game when quit:\n" + "    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 21", rm1Out.toString());
  }

  @Test
  public void testRm1InvalidLargeIndex() {
    StringBuilder rm1Out = new StringBuilder();
    Reader rm1 = new StringReader("rm1 4 1");
    PyramidSolitaireTextualController rm1Controller =
        new PyramidSolitaireTextualController(rm1, rm1Out);
    rm1Controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n"
        + "Invalid move. Play again. Invalid inputs for rm1.\n" + "    A♣\n" + "  2♣  3♣\n"
        + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n" + "Game quit!\n"
        + "State of game when quit:\n" + "    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 21", rm1Out.toString());
  }

  @Test
  public void testRm1InvalidNegativeIndex() {
    StringBuilder rm1Out = new StringBuilder();
    Reader rm1 = new StringReader("rm1 3 -1");
    PyramidSolitaireTextualController rm1Controller =
        new PyramidSolitaireTextualController(rm1, rm1Out);
    rm1Controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n"
        + "Invalid move. Play again. Invalid inputs for rm1.\n" + "    A♣\n" + "  2♣  3♣\n"
        + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n" + "Game quit!\n"
        + "State of game when quit:\n" + "    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 21", rm1Out.toString());
  }

  @Test
  public void testRm1UnexposedThirteen() {
    StringBuilder rm1Out = new StringBuilder();
    Reader rm1 = new StringReader("rm1 5 3");
    PyramidSolitaireTextualController rm1Controller =
        new PyramidSolitaireTextualController(rm1, rm1Out);
    rm1Controller.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "Invalid move. Play again. Invalid inputs for rm1.\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rm1Out.toString());
  }

  @Test
  public void testRm1InvalidCardIndexQuit() {
    StringBuilder rm1Out = new StringBuilder();
    Reader rm1 = new StringReader("rm1 3 B Q");
    PyramidSolitaireTextualController rm1Controller =
        new PyramidSolitaireTextualController(rm1, rm1Out);
    rm1Controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n"
        + "B is an invalid input.\n" + "Game quit!\n" + "State of game when quit:\n"
        + "    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 21", rm1Out.toString());
  }

  @Test
  public void testRm1InvalidRowIndexQuit() {
    StringBuilder rm1Out = new StringBuilder();
    Reader rm1 = new StringReader("rm1 B Q 1");
    PyramidSolitaireTextualController rm1Controller =
        new PyramidSolitaireTextualController(rm1, rm1Out);
    rm1Controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n"
        + "B is an invalid input.\n" + "Game quit!\n" + "State of game when quit:\n"
        + "    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 21", rm1Out.toString());
  }

  // rm2 tests
  @Test
  public void testRm2() {
    StringBuilder rm2Out = new StringBuilder();
    Reader rm2 = new StringReader("rm2 7 4 7 6");
    PyramidSolitaireTextualController rm2Controller =
        new PyramidSolitaireTextualController(rm2, rm2Out);
    rm2Controller.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦   .  K♦   .  2♥\n"
        + "Draw: 3♥\n" + "Score: 172\n" + "Game quit!\n" + "State of game when quit:\n"
        + "            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦   .  K♦   .  2♥\n" + "Draw: 3♥\n" + "Score: 172", rm2Out.toString());
  }

  @Test
  public void testRm2InvalidRowIndex() {
    StringBuilder rm2Out = new StringBuilder();
    Reader rm2 = new StringReader("rm2 B 7 1 B 7 2");
    PyramidSolitaireTextualController rm2Controller =
        new PyramidSolitaireTextualController(rm2, rm2Out);
    rm2Controller.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "B is an invalid input.\n" + "B is an invalid input.\n"
        + "Invalid move. Play again. Invalid inputs for rm2.\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rm2Out.toString());
  }

  @Test
  public void testRm2InvalidCardIndex() {
    StringBuilder rm2Out = new StringBuilder();
    Reader rm2 = new StringReader("rm2 7 B 1 7 B 2");
    PyramidSolitaireTextualController rm2Controller =
        new PyramidSolitaireTextualController(rm2, rm2Out);
    rm2Controller.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "B is an invalid input.\n" + "B is an invalid input.\n"
        + "Invalid move. Play again. Invalid inputs for rm2.\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rm2Out.toString());
  }

  @Test
  public void testRm2UnexposedCard() {
    StringBuilder rm2Out = new StringBuilder();
    Reader rm2 = new StringReader("rm2 1 1 7 6");
    PyramidSolitaireTextualController rm2Controller =
        new PyramidSolitaireTextualController(rm2, rm2Out);
    rm2Controller.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "Invalid move. Play again. Invalid inputs for rm2.\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rm2Out.toString());
  }

  @Test
  public void testRm2UnexposedThirteen() {
    StringBuilder rm2Out = new StringBuilder();
    Reader rm2 = new StringReader("rm2 5 2 7 6");
    PyramidSolitaireTextualController rm2Controller =
        new PyramidSolitaireTextualController(rm2, rm2Out);
    rm2Controller.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "Invalid move. Play again. Invalid inputs for rm2.\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rm2Out.toString());
  }

  @Test
  public void testRm2NotThirteen() {
    StringBuilder rm2Out = new StringBuilder();
    Reader rm2 = new StringReader("rm2 7 1 7 2");
    PyramidSolitaireTextualController rm2Controller =
        new PyramidSolitaireTextualController(rm2, rm2Out);
    rm2Controller.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "Invalid move. Play again. Invalid inputs for rm2.\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rm2Out.toString());
  }

  @Test
  public void testRm2SameCardTwice() {
    StringBuilder rm2Out = new StringBuilder();
    Reader rm2 = new StringReader("rm2 7 1 7 1");
    PyramidSolitaireTextualController rm2Controller =
        new PyramidSolitaireTextualController(rm2, rm2Out);
    rm2Controller.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "Invalid move. Play again. Invalid inputs for rm2.\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rm2Out.toString());
  }

  @Test
  public void testRm2NegativeIndex() {
    StringBuilder rm2Out = new StringBuilder();
    Reader rm2 = new StringReader("rm2 7 -1 7 2");
    PyramidSolitaireTextualController rm2Controller =
        new PyramidSolitaireTextualController(rm2, rm2Out);
    rm2Controller.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "Invalid move. Play again. Invalid inputs for rm2.\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rm2Out.toString());
  }

  @Test
  public void testRm2GreaterIndex() {
    StringBuilder rm2Out = new StringBuilder();
    Reader rm2 = new StringReader("rm2 7 13 7 2");
    PyramidSolitaireTextualController rm2Controller =
        new PyramidSolitaireTextualController(rm2, rm2Out);
    rm2Controller.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "Invalid move. Play again. Invalid inputs for rm2.\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rm2Out.toString());
  }

  @Test
  public void testRm2InvalidCardIndexQuit() {
    StringBuilder rm2Out = new StringBuilder();
    Reader rm2 = new StringReader("rm2 3 B Q 3 2");
    PyramidSolitaireTextualController rm2Controller =
        new PyramidSolitaireTextualController(rm2, rm2Out);
    rm2Controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n"
        + "B is an invalid input.\n" + "Game quit!\n" + "State of game when quit:\n"
        + "    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 21", rm2Out.toString());
  }

  @Test
  public void testRm2InvalidRowIndexQuit() {
    StringBuilder rm2Out = new StringBuilder();
    Reader rm2 = new StringReader("rm2 B Q 1 3 2");
    PyramidSolitaireTextualController rm2Controller =
        new PyramidSolitaireTextualController(rm2, rm2Out);
    rm2Controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n"
        + "B is an invalid input.\n" + "Game quit!\n" + "State of game when quit:\n"
        + "    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 21", rm2Out.toString());
  }

  // rmwd tests
  @Test
  public void testRmwd() {
    StringBuilder rmwdOut = new StringBuilder();
    Reader rmwd = new StringReader("rmwd 1 7 2");
    PyramidSolitaireTextualController rmwdController =
        new PyramidSolitaireTextualController(rmwd, rmwdOut);
    rmwdController.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦   .  J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 4♥\n" + "Score: 175\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦   .  J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 4♥\n"
        + "Score: 175", rmwdOut.toString());
  }

  @Test
  public void testRmwdUnexposedCard() {
    StringBuilder rmwdOut = new StringBuilder();
    Reader rmwd = new StringReader("rmwd 1 1 1");
    PyramidSolitaireTextualController rmwdController =
        new PyramidSolitaireTextualController(rmwd, rmwdOut);
    rmwdController.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "Invalid move. Play again. Invalid inputs for rmwd.\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rmwdOut.toString());
  }

  @Test
  public void testRmwdUnexposedThirteen() {
    StringBuilder rmwdOut = new StringBuilder();
    Reader rmwd = new StringReader("rmwd 1 4 4");
    PyramidSolitaireTextualController rmwdController =
        new PyramidSolitaireTextualController(rmwd, rmwdOut);
    rmwdController.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "Invalid move. Play again. Invalid inputs for rmwd.\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rmwdOut.toString());
  }

  @Test
  public void testRmwdNotThirteen() {
    StringBuilder rmwdOut = new StringBuilder();
    Reader rmwd = new StringReader("rmwd 1 7 1");
    PyramidSolitaireTextualController rmwdController =
        new PyramidSolitaireTextualController(rmwd, rmwdOut);
    rmwdController.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "Invalid move. Play again. Invalid inputs for rmwd.\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rmwdOut.toString());
  }

  @Test
  public void testRmwdNegativeBoardIndex() {
    StringBuilder rmwdOut = new StringBuilder();
    Reader rmwd = new StringReader("rmwd 1 7 -1");
    PyramidSolitaireTextualController rmwdController =
        new PyramidSolitaireTextualController(rmwd, rmwdOut);
    rmwdController.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "Invalid move. Play again. Invalid inputs for rmwd.\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rmwdOut.toString());
  }

  @Test
  public void testRmwdGreaterBoardIndex() {
    StringBuilder rmwdOut = new StringBuilder();
    Reader rmwd = new StringReader("rmwd 1 7 12");
    PyramidSolitaireTextualController rmwdController =
        new PyramidSolitaireTextualController(rmwd, rmwdOut);
    rmwdController.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "Invalid move. Play again. Invalid inputs for rmwd.\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rmwdOut.toString());
  }

  @Test
  public void testRmwdNegativeDrawIndex() {
    StringBuilder rmwdOut = new StringBuilder();
    Reader rmwd = new StringReader("rmwd -1 7 1");
    PyramidSolitaireTextualController rmwdController =
        new PyramidSolitaireTextualController(rmwd, rmwdOut);
    rmwdController.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "Invalid move. Play again. Invalid inputs for rmwd.\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rmwdOut.toString());
  }

  @Test
  public void testRmwdGreaterDrawIndex() {
    StringBuilder rmwdOut = new StringBuilder();
    Reader rmwd = new StringReader("rmwd 2 7 1");
    PyramidSolitaireTextualController rmwdController =
        new PyramidSolitaireTextualController(rmwd, rmwdOut);
    rmwdController.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "Invalid move. Play again. Invalid inputs for rmwd.\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rmwdOut.toString());
  }

  @Test
  public void testRmwdInvalidDrawIndex() {
    StringBuilder rmwdOut = new StringBuilder();
    Reader rmwd = new StringReader("rmwd B 1 7 1");
    PyramidSolitaireTextualController rmwdController =
        new PyramidSolitaireTextualController(rmwd, rmwdOut);
    rmwdController.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "B is an invalid input.\n" + "Invalid move. Play again. Invalid inputs for rmwd.\n"
        + "            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rmwdOut.toString());
  }

  @Test
  public void testRmwdInvalidRowIndex() {
    StringBuilder rmwdOut = new StringBuilder();
    Reader rmwd = new StringReader("rmwd 1 B 7 1");
    PyramidSolitaireTextualController rmwdController =
        new PyramidSolitaireTextualController(rmwd, rmwdOut);
    rmwdController.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "B is an invalid input.\n" + "Invalid move. Play again. Invalid inputs for rmwd.\n"
        + "            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rmwdOut.toString());
  }

  @Test
  public void testRmwdInvalidCardIndex() {
    StringBuilder rmwdOut = new StringBuilder();
    Reader rmwd = new StringReader("rmwd 1 7 B 1");
    PyramidSolitaireTextualController rmwdController =
        new PyramidSolitaireTextualController(rmwd, rmwdOut);
    rmwdController.playGame(model,model.getDeck(),false,7,1);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n"
        + "B is an invalid input.\n" + "Invalid move. Play again. Invalid inputs for rmwd.\n"
        + "            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n" + "Score: 185\n" + "Game quit!\n"
        + "State of game when quit:\n" + "            A♣\n" + "          2♣  3♣\n"
        + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n"
        + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥\n"
        + "Score: 185", rmwdOut.toString());
  }

  @Test
  public void testRmwdInvalidDrawIndexQuit() {
    StringBuilder rmwdOut = new StringBuilder();
    Reader rmwd = new StringReader("rmwd B Q 3 1");
    PyramidSolitaireTextualController rmwdController =
        new PyramidSolitaireTextualController(rmwd, rmwdOut);
    rmwdController.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n"
        + "B is an invalid input.\n" + "Game quit!\n" + "State of game when quit:\n"
        + "    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 21", rmwdOut.toString());
  }

  @Test
  public void testRmwdInvalidRowIndexQuit() {
    StringBuilder rmwdOut = new StringBuilder();
    Reader rmwd = new StringReader("rmwd 1 B Q 1");
    PyramidSolitaireTextualController rmwdController =
        new PyramidSolitaireTextualController(rmwd, rmwdOut);
    rmwdController.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n"
        + "B is an invalid input.\n" + "Game quit!\n" + "State of game when quit:\n"
        + "    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 21", rmwdOut.toString());
  }

  @Test
  public void testRmwdInvalidCardIndexQuit() {
    StringBuilder rmwdOut = new StringBuilder();
    Reader rmwd = new StringReader("rmwd 1 3 B Q");
    PyramidSolitaireTextualController rmwdController =
        new PyramidSolitaireTextualController(rmwd, rmwdOut);
    rmwdController.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n"
        + "B is an invalid input.\n" + "Game quit!\n" + "State of game when quit:\n"
        + "    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 21", rmwdOut.toString());
  }

  // dd test
  @Test
  public void testDd() {
    StringBuilder ddOut = new StringBuilder();
    Reader dd = new StringReader("dd 1");
    PyramidSolitaireTextualController ddController =
        new PyramidSolitaireTextualController(dd, ddOut);
    ddController.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n"
        + "Draw: 7♣\n" + "Score: 21\n" + "    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 8♣\n"
        + "Score: 21\n" + "Game quit!\n" + "State of game when quit:\n" + "    A♣\n" + "  2♣  3♣\n"
        + "4♣  5♣  6♣\n" + "Draw: 8♣\n" + "Score: 21", ddOut.toString());
  }

  @Test
  public void testDdInvalidGreaterIndex() {
    StringBuilder ddOut = new StringBuilder();
    Reader dd = new StringReader("dd 2");
    PyramidSolitaireTextualController ddController =
        new PyramidSolitaireTextualController(dd, ddOut);
    ddController.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n"
        + "Invalid move. Play again. Invalid input for dd.\n" + "    A♣\n" + "  2♣  3♣\n"
        + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n" + "Game quit!\n"
        + "State of game when quit:\n" + "    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 21", ddOut.toString());
  }

  @Test
  public void testDdInvalidNegativeIndex() {
    StringBuilder ddOut = new StringBuilder();
    Reader dd = new StringReader("dd -2");
    PyramidSolitaireTextualController ddController =
        new PyramidSolitaireTextualController(dd, ddOut);
    ddController.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n"
        + "Invalid move. Play again. Invalid input for dd.\n" + "    A♣\n" + "  2♣  3♣\n"
        + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n" + "Game quit!\n"
        + "State of game when quit:\n" + "    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 21", ddOut.toString());
  }

  @Test
  public void testDDInvalidDrawIndex() {
    StringBuilder ddOut = new StringBuilder();
    Reader dd = new StringReader("dd B 1");
    PyramidSolitaireTextualController ddController =
        new PyramidSolitaireTextualController(dd, ddOut);
    ddController.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n"
        + "B is an invalid input.\n" + "    A♣\n" + "  2♣  3♣\n"
        + "4♣  5♣  6♣\n" + "Draw: 8♣\n" + "Score: 21\n" + "Game quit!\n"
        + "State of game when quit:\n" + "    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 8♣\n"
        + "Score: 21", ddOut.toString());
  }

  @Test
  public void testDdInvalidDrawIndexQuit() {
    StringBuilder ddOut = new StringBuilder();
    Reader dd = new StringReader("dd B B Q");
    PyramidSolitaireTextualController ddController =
        new PyramidSolitaireTextualController(dd, ddOut);
    ddController.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n"
        + "B is an invalid input.\n" + "B is an invalid input.\n" + "Game quit!\n"
        + "State of game when quit:\n" + "    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 21", ddOut.toString());
  }

  @Test
  public void testGameOverWin() {
    StringBuilder winOut = new StringBuilder();
    Reader win = new StringReader("rmwd 11 1 1");
    PyramidSolitaireTextualController winController =
        new PyramidSolitaireTextualController(win, winOut);
    winController.playGame(model,model.getDeck(),false,1,11);
    assertEquals("A♣\n" + "Draw: 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣\n" + "Score: 1\n"
        + "You win!", winOut.toString());
  }

  @Test
  public void testGameOverLoss() {
    StringBuilder loseOut = new StringBuilder();
    Reader lose = new StringReader("dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1"
        + " dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1"
        + " dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1");
    PyramidSolitaireTextualController loseController =
        new PyramidSolitaireTextualController(lose, loseOut);
    loseController.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n"
        + "    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 8♣\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 9♣\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 10♣\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: J♣\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: Q♣\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: K♣\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: A♦\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 2♦\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 3♦\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 4♦\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 5♦\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 6♦\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♦\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 8♦\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 9♦\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 10♦\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: J♦\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: Q♦\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: K♦\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: A♥\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 2♥\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 3♥\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 4♥\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 5♥\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 6♥\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♥\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 8♥\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 9♥\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 10♥\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: J♥\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: Q♥\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: K♥\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: A♠\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 2♠\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 3♠\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 4♠\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 5♠\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 6♠\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♠\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 8♠\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 9♠\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 10♠\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: J♠\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: Q♠\n" + "Score: 21\n" + "    A♣\n"
        + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: K♠\n" + "Score: 21\n"
        + "Game over. Score: 21", loseOut.toString());
  }

  @Test
  public void testGameOverNotOver() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals(false,model.isGameOver());
  }

  @Test (expected = IllegalStateException.class)
  public void testGameOverNotStarted() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    model.isGameOver();
  }

  // New functionality tests for Relaxed rules
  @Test
  public void testRm2Relaxed() {
    StringBuilder rm2Out = new StringBuilder();
    Reader rm2 = new StringReader("rmwd 2 7 1 rm2 7 2 6 1");
    PyramidSolitaireTextualController rm2Controller =
        new PyramidSolitaireTextualController(rm2, rm2Out);
    rm2Controller.playGame(model,model.getDeck(),false,7,2);
    assertEquals("            A♣\n" + "          2♣  3♣\n" + "        4♣  5♣  6♣\n"
        + "      7♣  8♣  9♣  10♣\n" + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" + "Draw: 3♥, 4♥\n" + "Score: 185\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  3♦  4♦  5♦  6♦  7♦  8♦\n" + ".  10♦ J♦  Q♦  K♦  A♥  2♥\n"
        + "Draw: 3♥, 5♥\n" + "Score: 176\n" + "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  .  4♦  5♦  6♦  7♦  8♦\n" + ".   .  J♦  Q♦  K♦  A♥  2♥\n"
        + "Draw: 3♥, 5♥\n" + "Score: 163\n" + "Game quit!\n" + "State of game when quit:\n" +
        "            A♣\n"
        + "          2♣  3♣\n" + "        4♣  5♣  6♣\n" + "      7♣  8♣  9♣  10♣\n"
        + "    J♣  Q♣  K♣  A♦  2♦\n" + "  .  4♦  5♦  6♦  7♦  8♦\n" + ".   .  J♦  Q♦  K♦  A♥  2♥\n"
        + "Draw: 3♥, 5♥\n" + "Score: 163", rm2Out.toString());
  }

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
    StringBuilder rm1Out = new StringBuilder();
    Reader rm1 = new StringReader("q");
    PyramidSolitaireTextualController rm1Controller =
        new PyramidSolitaireTextualController(rm1, rm1Out);
    rm1Controller.playGame(model,model.getDeck(),false,3,1);
    assertEquals("    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n" + "Score: 21\n"
        + "Game quit!\n" + "State of game when quit:\n"
        + "    A♣\n" + "  2♣  3♣\n" + "4♣  5♣  6♣\n" + "Draw: 7♣\n"
        + "Score: 21", rm1Out.toString());
  }

  @Test
  public void testGameNotOverRelaxedRules() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 rmwd 1 4 3 dd 1 dd 1 dd 1 dd 1"
        + " dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1"
        + " dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,4,1);
    // Only valid move is a relaxed remove 2
    assertEquals(false,model.isGameOver());
  }

  @Test
  public void testGameOverRelaxedRules() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 rmwd 1 4 3 dd 1 dd 1 dd 1 dd 1"
        + " dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1"
        + " dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 rm2 4 2 3 2");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, out);
    controller.playGame(model,model.getDeck(),false,4,1);
    // Same as previous test, but after the relaxed remove, there are no more valid moves
    assertEquals(true,model.isGameOver());
  }

  @Test
  public void testGameOverLossFixing() {
    StringBuilder loseOut = new StringBuilder();
    Reader lose = new StringReader("dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1"
        + " dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 "
        + " dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1");
    PyramidSolitaireTextualController loseController =
        new PyramidSolitaireTextualController(lose, loseOut);
    loseController.playGame(model,model.getDeck(),false,4,1);
    assertEquals("      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n"
        + "Draw: J♣\n" + "Score: 55\n" +
        "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: Q♣\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: K♣\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: A♦\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 2♦\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 3♦\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 4♦\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 5♦\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 6♦\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 7♦\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 8♦\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 9♦\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 10♦\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: J♦\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: Q♦\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: K♦\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: A♥\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 2♥\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 3♥\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 4♥\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 5♥\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 6♥\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 7♥\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 8♥\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 9♥\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 10♥\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: J♥\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: Q♥\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: K♥\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: A♠\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 2♠\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 3♠\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 4♠\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 5♠\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 6♠\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 7♠\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 8♠\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 9♠\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: 10♠\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: J♠\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: Q♠\n"
        + "Score: 55\n"
        + "      A♣\n" + "    2♣  3♣\n" + "  4♣  5♣  6♣\n" + "7♣  8♣  9♣  10♣\n" + "Draw: K♠\n"
        + "Score: 55\n" + "Game over. Score: 55", loseOut.toString());
  }
}
