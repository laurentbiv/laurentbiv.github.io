CC = gcc
CFLAGS = -W -Wall -g -std=c99

# Cible principale
all: testList testCell

# Création des exécutables de test
testList: list.o testList.o
	$(CC) -o testList list.o testList.o

testCell: list.o stack.o cell.o testCell.o
	$(CC) -o testCell list.o stack.o cell.o testCell.o -lm

# Compilation des fichiers objets
testList.o: testList.c list.h
	$(CC) $(CFLAGS) -c testList.c

testCell.o: testCell.c cell.h list.h stack.h
	$(CC) $(CFLAGS) -c testCell.c

list.o: list.c list.h
	$(CC) $(CFLAGS) -c list.c

stack.o: stack.c stack.h
	$(CC) $(CFLAGS) -c stack.c

cell.o: cell.c cell.h list.h stack.h
	$(CC) $(CFLAGS) -c cell.c

# Nettoyage
clear:
	rm -f *.o testList testCell

# Tests rapides
test: testList testCell
	./testList
	./testCell

# Débuggage
debug: testCell
	gdb ./testCell
