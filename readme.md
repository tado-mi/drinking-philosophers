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

## Extra credit

* Will implement better graphics.

* Will implement [Drinking Philosophers](https://www.cs.utexas.edu/users/misra/scannedPdf.dir/DrinkingPhil.pdf) and demonstrate on a general Directed Graph.
