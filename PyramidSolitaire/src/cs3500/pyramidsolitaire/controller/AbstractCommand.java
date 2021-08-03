package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.Objects;
import java.util.Scanner;

/**
 * Abstract class for executing commands used in Pyramid Solitaire games.
 */
public abstract class AbstractCommand implements IPyramidSolitaireCommand {
  protected final PyramidSolitaireModel<?> model;
  protected final Appendable out;

  public AbstractCommand(PyramidSolitaireModel<?> model, Appendable out) {
    this.model = Objects.requireNonNull(model);
    this.out = Objects.requireNonNull(out);
  }

  /**
   * Gets the index value for commands.
   * @param scan user inputted command with move
   * @return the next index value in the inputted command
   */
  protected int getInputValue(Scanner scan) throws QuitGame {
    int value;
    String input = scan.next();
    while (true) {
      if (input.equalsIgnoreCase("q")) {
        throw new QuitGame();
      }
      try {
        value = Integer.parseInt(input) - 1;
        return value;
      } catch (NumberFormatException e) {
        ControllerUtil.append(out,input + " is an invalid input.\n");
      }
      input = scan.next();
    }
  }

  @Override
  public abstract void executeCommand(Scanner scan);
}
