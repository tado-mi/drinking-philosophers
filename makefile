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
	Dining.java \
	Test.java

# default target definition
default: classes

classes: $(CLASSES:.java=.class)

run:
	$(CD) bin && java Test

clean:
	$(RM) *.class
