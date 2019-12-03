# Drinking philosophers

Code written by [Youssef Hussein](https://github.com/youssefmohamed552) and [Lusine Keshishyan](https://github.com/tado-mi).<br/>
<br/>
Following is an implementation of the [Drinking Philosophers](https://www.cs.utexas.edu/users/misra/scannedPdf.dir/DrinkingPhil.pdf) problem, as a solution of the [assignment](https://www.cs.rochester.edu/u/scott/courses/254/assignments/java.shtml) for the course **CSC 254** at the University of Rochester. It is made public to demonstrate a code sample by the authors, and for learning. We trust it will not be used to violate academic honesty policies.<br/>
<br/>
The provided [source code](https://www.cs.rochester.edu/courses/254/fall2019/assignments/java/Dining.java) has been modified and augmented. It was broken down to separate files for each class, concurrency has been implemented, graphics have been improved and it has been generalized from **Dining philosophers** problem to **Drinking philosophers** problem.

# Files

## Demo.java

The main class. Input is a terminal line argument with a filename that describes a [DAG](https://en.wikipedia.org/wiki/Directed_acyclic_graph), where vertices represent the Philosophers and edges represent their Forks. First line contains numbers `n` and `m`, where `n = |V|` and `m = |E|`. Lines starting with a `#` symbol are treated as comments and ignored in parsing.<br/>
<br/>
The `main` class initalizes a `Table` and putr together the GUI. It has not been modified much from the provided source code.<br/>
<br/>
In the current repository are included two test files:

* `Dining Philosophers.txt` is the description of the classical [Dining Philosophers](https://en.wikipedia.org/wiki/Dining_philosophers_problem) problem.
* `Drinking Philosophers.txt` is the description of a directed graph with `n = 10` and `m = 15`.

## Table.java

Reads from the provided file and essentially initializes a Graph. Assigns coordinates to each vertex such that they are equally distributed across a perimeter of a circle. The logic is closely following the source code. One major difference is that instead of hardcoding the case with 5 philosophers sharing exactly one fork with their right and left neighbors, the user can input any graph.

## Philosophers.java

The equivalent to a `Vertex` class from theory pov. Maintains an adjacency list of Forks, representing all the forks locks on which they'd need to acquire in order to start eating.

## Fork.java

The equivalent to an `Edge` class from theory pov. Has an instance variable `boolean lock` to implement proper concurrency. Also maintains pointers to its current owner (which could be  `null`), and to its possible owners, `u` and `v`.

# Run

There is an included `makefile` for your (and our) convenience.<br/>
<br/>
If `make` command doesn't work, please, make sure that the folder contains a directory named *bin*. Or, if you don't mind your `.class` messing up the directory containing  `.src`, you may just run  `javac *.java` command to compile.<br/>
<br/>
There are four useful commands included in `makefile`:

* `make dine-photo` will demonstrate the Dining Philosophers problem with photos of some famous philosophers.
* `make dine` will demonstrate the Dining Philosophers problem with cute little squares instead of photos.
* `make drink` will demonstrate a general case of the Dining Philosophers problem using the included test file.
* `make clean` will remove all the `*.class` files generated at compilation with the `make` command.
