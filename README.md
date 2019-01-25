# nQueen-Solver
AI Solver for nQueen Problem

This program utilizes simulated annealing and genetic algorithm to solve the nQueen problem for a specified number of iterations. This iteration is preset to 550 to analyze the problem's solveability with the two approaches. To change the presets, modify the displaySolution method in Driver file. The format of the method is explained as follows.

displaySolution(size, maxGeneticIterations, temperature, testQuantity)
  - where size is an n x n board
  - maxGeneticIterations is the number used to end the genetic algorithm
  - temperature is utilized only for simulated annealing
  - testQuantity is the quanitity with which the algorithms are executed
  
 Additional parameters about the population and its evaluation can be modified in Search.java with population size, mutation rate, and fitness.
