package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class for overriding executeCommand in order to carry out the discardDraw command (dd).
 */
public class DiscardDrawCommand extends AbstractCommand {

  public DiscardDrawCommand(PyramidSolitaireModel<?> model, Appendable out) {
    super(model, out);
  }

  @Override
  public void executeCommand(Scanner scan) throws QuitGame {
    List<Integer> coords = new ArrayList<>();
    coords.add(getInputValue(scan));
    try {
      model.discardDraw(coords.get(0));
    } catch (IllegalArgumentException e) {
      ControllerUtil.append(out,"Invalid move. Play again. Invalid input for dd.\n");
    }
    coords.clear();
  }
}
