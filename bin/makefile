JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	CG_hw4.java 

MAIN = CG_hw4

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class