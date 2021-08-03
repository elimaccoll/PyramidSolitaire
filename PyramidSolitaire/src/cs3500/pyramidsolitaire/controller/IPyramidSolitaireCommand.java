package cs3500.pyramidsolitaire.controller;

import java.util.Scanner;

/**
 * Interface for AbstractCommand to facilitate executing commands in a game of Pyramid Solitaire.
 */
public interface IPyramidSolitaireCommand {
  /**
   * Executes the user entered command.  This is overwritten by each valid command format, rm1,
   * rm2, rmwd, and dd such that they carry out their described purpose in the PyramidSolitaireModel
   * interface.
   * @param scan User inputted command to extract index values from and execute command
   * @throws QuitGame Quits game if the command is q or Q was found in getInputValue
   */
  void executeCommand(Scanner scan);
}