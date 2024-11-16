# Makefile for compiling and running Java programs with JADE

# Variables
JAVAC=javac
JAVA=java
CLASSPATH=lib/jade.jar
SRC_DIR=myexamples
OUT_DIR=classes
MAIN_CLASS=AgenteMovil

# Targets
compile:
	$(JAVAC) -classpath $(CLASSPATH) -d $(OUT_DIR) $(SRC_DIR)/$(MAIN_CLASS).java

gui:
	$(JAVA) -cp $(CLASSPATH) jade.Boot -gui

agente:
	$(JAVA) -cp $(CLASSPATH):$(OUT_DIR) jade.Boot -gui
