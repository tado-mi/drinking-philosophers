# Drinking philosophers problem

an attempt to implement [this](https://www.cs.utexas.edu/users/misra/scannedPdf.dir/DrinkingPhil.pdf).

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
