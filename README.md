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
