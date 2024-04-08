# UBC CPSC210 Term project: Conway's Game of Life simulator

## What is this project about?
After reading a book about John Von Neumann, I got interested in cellular automata, especially in
[Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life). Despite having very simple rules, there
are many finite patterns with interesting behavior, such as spaceships, glider guns, and "The Eater".

As a result of this interest, I decided to implement a simulator of this game. I thought that doing this would be a
great way of challenging myself and consolidating my programming skills. In the future, I am considering to also
implement some games built on top of the Game of Life, but for now it is not a concrete part of my plan.

I expect this program to be used by students interested in cellular automata, like me, or other people who just want to
have fun experimenting with it. By interacting with this program, users might increase their interest in mathematics,
computer science, or other formal sciences.

## User stories
- As a user, I want to be able to generate an arbitrary number of generations to the game, which are computed 
programmatically ("Add arbitrary Xs to Y" condition).
- As a user, I want to be able to define arbitrary dimensions to the board (and therefore adding an 
arbitrary amount of cells to the game).
- As a user, I want to be able to visualize the cells on my game ("View a list of items" condition).
- As a user, I want to be able to set my own initial arrangement of cells.
- As a user, I want to be able to browse forward and backwards through the generations of my game.
- As a user, I want to be able to save the state of my Conway's Game Of Life, with all my generations included on it.
- As a user, I want to be able to load my Conway's Game Of Life in the state in which I previously saved it.
- As a user, I want to be able to list all of my Xs in my Y in a single panel.
- As a user, I want to be able to use this panel to move to any of my generations by clicking a button.
- As a user, I want to be able to create new generations or reset them from this panel. 

# Instructions for Grader
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by:
  1. Click on "New game".
  2. Select an arbitrary height and widths and click in on "Ok!".
  3. Draw an initial setup.
  4. Click on "Done".
  5. Click on "Next generation"; or go to "Generations", and click "New Generation" or "Reset generations".
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by...
  1. Click on "New game".
  2. Select an arbitrary height and widths and click in on "Ok!".
  3. Draw an initial setup.
  4. Click on "Done".
  5. Go to "Generations", and click on one button on the list.
- You can locate my visual component by:
  1. Looking in the main menu, where there is an image that represents the logo of my game.
- You can save the state of my application by:
  1. Click on "New game".
  2. Select an arbitrary height and widths and click in on "Ok!".
  3. Draw an initial setup.
  4. Click on "Done".
  5. Play as you like.
  6. When you want to save, click on "Save". The game will be saved.
- You can reload the state of my application by:
  1. Click on "Load game". The game will be reloaded.

# Phase 4 Task 2
```
Sat Apr 06 19:30:34 PDT 2024
Creating new generation at the end of generations.
Sat Apr 06 19:30:35 PDT 2024
Creating new generation at the end of generations.
Sat Apr 06 19:30:35 PDT 2024
Creating new generation at the end of generations.
Sat Apr 06 19:30:35 PDT 2024
Creating new generation at the end of generations.
Sat Apr 06 19:30:35 PDT 2024
Creating new generation at the end of generations.
Sat Apr 06 19:30:39 PDT 2024
Setting current generation to 1.
Sat Apr 06 19:30:47 PDT 2024
Resetting generations to first generation
Sat Apr 06 19:30:48 PDT 2024
Setting current generation to 1.
Sat Apr 06 19:30:49 PDT 2024
Creating new generation at the end of generations.
Sat Apr 06 19:30:49 PDT 2024
Creating new generation at the end of generations.
```

# Phase 4 Task 3
_(UML diagram is in the root directory of the project)._

I would try to remove the field in ui.gui.Menu of type ui.gui.GameOfLifeGui, as it seems to cause unnecessary coupling. Because of this coupling, the Menu class can only be used from the ui.gui.GameOfLifeGui class, being unable to be reused in other situations. Moreover, this seems to cut the "nice" top-bottom flow of information in the GUI component of the application (note that ui.gui.GameOfLifeGui would normally have a dependency arrow pointing to ui.gui.Menu, which was omitted as per the instructions), and it violates the Dependency Inversion (the "D" in SOLID). One refactoring option would be to create an interface implemented by ui.gui.GameOfLifeGui and make the field of ui.gui.Menu of that type. In this way, the code could be reused by any class that implements the new interface. This would make the code more reusable, but it would not make the flow exactly "top-bottom". Another option would be to transfer the responsability of managing the JFrame from the ui.gui.GameOfLifeGui class to the ui.gui.Menu class, and then remove the ui.gui.GameOfLifeGui field from ui.gui.Menu. This would make the flow "top-bottom" and reduce coupling, but it would also significantly reduce cohesion.

I would also try to remove the field of type model.GameOfLife from ui.cli.Menu's subtypes, as it seems to create a lot of repetition between such 4 classes, all of which have a GameOfLife field. It seems like the way in which the model.GameOfLife instance is returned by ui.cli.MainMenu and ui.cli.NewGameMenu might violate the Liskov Substitution Principle, too. One refactoring option would be to move this field to the ui.cli.Menu abstract class. This would remove repetition from the 4 classes. However, it would also add an unnecessary field when a model.GameOfLife is not needed. Another possible way to refactor this would be to try to remove the field from ui.cli.MainMenu and from ui.cli.NewGameMenu. After all, these two classes do not really represent instances of model.GameOfLife, but they have fields of this type so that they can be passed later to the other ui.cli.Menu subtypes. This could be potentially done changing the design pattern for the menus in ui.cli, so that they are similar to the menus in ui.gui (if you notice, ui.gui.MainMenu and ui.gui.NewGameMenu are analogous to ui.cli.MainMenu and ui.cli.NewGameMenu yet do not have fields of model.GameOfLife).
