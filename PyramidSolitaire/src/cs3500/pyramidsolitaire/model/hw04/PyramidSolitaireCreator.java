package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Factory class for games of Pyramid Solitaire that can create a
 * model of three different game variants: Basic, Relaxed, and MultiPyramid.
 */
public class PyramidSolitaireCreator {

  /**
   * Set of playable game types for Pyramid Solitaire.
   */
  public enum GameType {
    BASIC, RELAXED, MULTIPYRAMID
  }

  /**
   * Creates the desired model of Pyramid Solitaire.
   * @param type the desired game variant: Basic, Relaxed, or MultiPyramid.
   * @return a game model for the inputted type.
   */
  public static PyramidSolitaireModel create(GameType type) {
    switch (type) {
      case BASIC:
        return new BasicPyramidSolitaire();
      case RELAXED:
        return new RelaxedPyramidSolitaire();
      case MULTIPYRAMID:
        return new MultiPyramidSolitaire();
      default:
        return null;
    }
  }
}
