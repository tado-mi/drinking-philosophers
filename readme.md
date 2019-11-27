# Drinking philosophers

Authors: Youssef Hussein, Lusine Keshishyan

This code has six main classes:
#  Dining
      The public, "main" class.
#  Philosopher
      Active -- extends Thread
#  Fork
      Passive
#  Table
      Manages the philosophers and forks and their physical layout.
#  Coordinator
      Provides mechanisms to suspend, resume, and reset the state of
      worker threads (philosophers).
#  UI
      Manages graphical layout and button presses.

# Assignment details

* Worked together with the project partner for discussion, but wrote code independently.

* I separated classes into files and made trivial modifications to the code to understand it better and have a more clear implementation.

* Concurency is implemented with locks ( = a boolean variable) stored in the Forks class.

# Run

There is an included `makefile`.
  make
  make run

## Extra credit 1
Implement alternative anti-deadlock strategies.  Consider both deadlock prevention (designing the program so it never gets into a deadlock situation) and deadlock recovery (detecting deadlock and breaking it).

## Extra credit 2
Explore generalizations of the dining problem: [Drinking Philosophers](https://www.cs.utexas.edu/users/misra/scannedPdf.dir/DrinkingPhil.pdf).

## Extra credit 3 [DONE]
Draw prettier graphics.
* Photos of some famous philosophers instead of blobs
* Scale-ability
* Colored messages of the state

## Extra credit 4
(Ambitious) Implement a transactional memory system that allows you to write simply
txBegin();
    // grab forks
txEnd();
and trust that the underlying implementation makes the code in the middle atomic while simultaneously avoiding deadlock and maximizing concurrency.  Your implementation will need to employ some sort of speculation.  See, for example, the DSTM system of Herlihy, Luchangco, Moir, and Scherer.
