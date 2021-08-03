package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class for overriding executeCommand in order to carry out the remove with draw command (rmwd).
 */
public class RemoveWithDrawCommand extends AbstractCommand {

  public RemoveWithDrawCommand(PyramidSolitaireModel<?> model, Appendable out) {
    super(model, out);
  }

  @Override
  public void executeCommand(Scanner scan) throws QuitGame {
    List<Integer> coords = new ArrayList<>();
    coords.add(getInputValue(scan));
    coords.add(getInputValue(scan));
    coords.add(getInputValue(scan));
    try {
      model.removeUsingDraw(coords.get(0), coords.get(1), coords.get(2));
    } catch (IllegalArgumentException e) {
      ControllerUtil.append(out,"Invalid move. Play again. Invalid inputs for rmwd.\n");
    }
    coords.clear();
  }
}
