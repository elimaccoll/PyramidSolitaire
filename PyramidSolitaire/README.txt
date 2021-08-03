Changes from previous assignment:

I added an abstract class, AbstractPyramidSolitaireModel which contains the code for 
BasicPyramidSolitaire and is overwritten where necessary for the other game variants.

Implemented the Command Design Pattern, which involved the addition of an abstract class called
AbstractCommand, as well as a class for each of the four commands that extends AbstractCommand.

Added a ControllerUtil class to handle appendalbe errors

PyramidSolitaireTextualController:
Added renderHelp helper method to handle the try catch for rendering the view

PyramidSolitaireTextualView:
- Fixed some spacing errors with removed cards

Added missing tests from previous assignment:
- testNullReadable
- testNullAppendable
- testCommandQuit (when a q or Q is in the spot for a command (rm1, rm2, etc.)

Any other changes/additions come from carrying out Assignment 4 instructions:
- MultiPyramidSolitaire
- RelaxedPyramidSolitaire
- PyramidSolitaireCreator
- PyramidSolitaire (main)
- Corresponding Test classes
