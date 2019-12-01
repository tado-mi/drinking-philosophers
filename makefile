# variable for java compiler
JC = javac
D  = -d
CD = cd

# damage control
.SUFFIXES: .java .class

# target for creating .class from .java in format:
#	.original_extention.target_extention:
#		rule
.java.class:
	$(JC) $*.java $(D) bin

# macro for each java source file
CLASSES = \
	ResetException.java \
	Fork.java \
	Philosopher.java \
	Table.java \
	Coordinator.java \
	UI.java \
	Demo.java

# default target definition
default: classes

classes: $(CLASSES:.java=.class)

dine:
	$(CD) bin && java Demo '../Dining Philosophers.txt'

drink:
	$(CD) bin && java Demo '../Drinking Philosophers.txt'

clean:
	$(RM) bin/*.class
