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

# Instructions for Grader
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by:
  1. Click on "New game".
  2. Select an arbitrary height and widths and clickin on "Ok!".
  3. Draw an initial setup.
  4. Click on "Done".
  5. Click on "Next generation"; or go to "Generations", and click "New Generation" or "Reset generations".
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by...
  1. Click on "New game".
  2. Select an arbitrary height and widths and clickin on "Ok!".
  3. Draw an initial setup.
  4. Click on "Done".
  5. Go to "Generations", and click on one button on the list.
- You can locate my visual component by:
  1. Looking in the main menu, where there is an image that represents the logo of my game.
- You can save the state of my application by:
  1. Click on "New game".
  2. Select an arbitrary height and widths and clickin on "Ok!".
  3. Draw an initial setup.
  4. Click on "Done".
  5. Play as you like.
  6. When you want to save, click on "Save". The game will be saved.
- You can reload the state of my application by:
  1. Click on "Load game". The game will be reloaded.

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

## Useful Notes
### Summary of the rules of Conway's game of Life
I decided to include this section in case you are not familiar enough with Conway's game of life (if you are interested,
I would recommend reading the Wikipedia article):
- The game is played in an arbitrarily sized board (or an infinite one), which is divided in 1x1 "cells".
- The setup is done by defining an arbitrary initial "generation" (i.e. board state).
- In each generation, each cell has two possible states: alive or dead.
- To progress to the next generation, the following transformations are made:
  - Any alive cell with <2 neighbors (orthogonally or diagonally adjacent alive cells) dies.
  - Any cell with 2 neighbors stays the same (it becomes alive if it is alive, and stays dead if it is dead).
  - Any cell with 3 neighbors becomes alive (or stays alive if it is already alive).
  - Any alive cell with >3 neighbors dies.
- The game can go on for an arbitrary number of generations.
