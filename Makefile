JAVAC=javac
SOURCES = $(wildcard *.java)
CLASSES = $(SOURCES:.java=.class)
MAIN = Main
JVM = java

all: $(CLASSES)

clean :
	rm -f *.class

%.class : %.java
	$(JAVAC) $<

run: $(MAIN).class
	$(JVM) $(MAIN)
