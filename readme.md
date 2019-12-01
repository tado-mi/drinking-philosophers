# Drinking philosophers

Following is an implementation of the [Drinking Philosophers](https://www.cs.utexas.edu/users/misra/scannedPdf.dir/DrinkingPhil.pdf) problem, as a solution to the following [assignment](https://www.cs.rochester.edu/u/scott/courses/254/assignments/java.shtml). The code is made public for easier collaboration and (in future) as a sample of code. It **may not** be used for violating academic honesty policies.

* authors: Youssef Hussein, Lusine Keshishyan

This code has six main classes:
* **Demo**: the public, "main" class that demonstrates everything.
* **Philosopher**: active -- extends Thread
* **Fork**: passive
* **Table**: manages the philosophers and forks and their physical layout.
* **Coordinator**: provides mechanisms to suspend, resume, and reset the state of worker threads (philosophers).
* **UI**: manages graphical layout and button presses.

# Test cases
Included are two `.txt` files. First line contains two numbers `n` and `m`, where `n` is the number of Philosophers(: vertices), and `m` is the number of all the available forks(: edges).
* `Dining Philosophers.txt` is the description of the classical [Dining Philosophers](https://en.wikipedia.org/wiki/Dining_philosophers_problem) problem.
* `Drinking Philosophers.txt` is the description of a directed graph with `n = 10` and `m = 15`.

# Implementation of concurrency

* Concurency is implemented with locks ( = a boolean variable) stored in the Forks class.

# Run
There is an included `makefile`. If `make` command doesn't work, please, make sure that the folder contains a directory named *bin*. There are four useful commands included in `makefile`:
* `make dine-photo`: will demonstrate the Dining Philosophers problem with photos of some famous philosophers.
* `make dine`: will demonstrate the Dining Philosophers problem with cute little squares instead of photos.
* `make drink`: will demonstrate a general case of the Dining Philosophers problem = the Drinking Philosophers problem, with cute little squares representing Philosophers, and thick edges representing Forks.

# Extra Credit
## Challenge 1
Implement alternative anti-deadlock strategies.  Consider both deadlock prevention (designing the program so it never gets into a deadlock situation) and deadlock recovery (detecting deadlock and breaking it).

## Challenge 2
*Explore generalizations of the dining problem: [Drinking Philosophers](https://www.cs.utexas.edu/users/misra/scannedPdf.dir/DrinkingPhil.pdf).*<br/>
Completed. Philosophers act as vertices, and maintain an array list of forks, as an adjacency list. A philosopher doesn't start 'eating' until it possess locks on all the forks on their adjacency list. Demonstrated by the test case of `Drinking Philosophers.txt`, which contains 10 philosophers and 15 forks.<br/>
Updated the constructor of the Table class to read from an arbitrary .txt file describing a Directed Graph, instead of hardcoding the case of Dining philosophers.

## Challenge 3
*Draw prettier graphics.*<br/>
Completed.
* GUI is scalable
* states are represented with images
* each philosopher is assigned a unique color
* 'forks' are represented as edges between philosophers, and acquire color of the philosopher that possess it and is using it to 'drink'
* with make command `make dine-photo`, GUI comes with photos of 5 chosen philosophers

## Challenge 4
(Ambitious) Implement a transactional memory system that allows you to write simply
txBegin();
    // grab forks
txEnd();
and trust that the underlying implementation makes the code in the middle atomic while simultaneously avoiding deadlock and maximizing concurrency.  Your implementation will need to employ some sort of speculation.  See, for example, the DSTM system of Herlihy, Luchangco, Moir, and Scherer.
