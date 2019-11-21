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
	Demo.java \
	Test.java

# default target definition
default: classes

classes: $(CLASSES:.java=.class)

run:
	$(CD) bin && java Demo

clean:
	$(RM) bin/*.class
