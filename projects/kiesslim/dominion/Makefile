CFLAGS = -Wall -fpic -coverage -lm

rngs.o: rngs.h rngs.c
	gcc -c rngs.c -g  $(CFLAGS)

dominion.o: dominion.h dominion.c rngs.o
	gcc -c dominion.c -g  $(CFLAGS)

#TODO: add Assignment3 Test Suite here, similar to above ^^^
unittest1: unittest1.c dominion.o rngs.o
	gcc -o unittest1 -g  unittest1.c dominion.o rngs.o $(CFLAGS)

runUT1: unittest1
	./unittest1 &> out.txt
	cat out.txt >> unittestresults.out
	gcov dominion.c -b >> unittestresults.out

runCleanUT1:
	make clean
	make unittest1
	./unittest1 &> out.txt
	cat out.txt >> unittestresultsCLEAN.out
	gcov dominion.c -b >> unittestresultsCLEAN.out

unittest2: unittest2.c dominion.o rngs.o
	gcc -o unittest2 -g  unittest2.c dominion.o rngs.o $(CFLAGS)

runUT2: unittest2
	./unittest2 &> out.txt
	cat out.txt >> unittestresults.out
	gcov dominion.c -b >> unittestresults.out

runCleanUT2:
	make clean
	make unittest2
	./unittest2 &> out.txt
	cat out.txt >> unittestresultsCLEAN.out
	gcov dominion.c -b >> unittestresultsCLEAN.out

unittest3: unittest3.c dominion.o rngs.o
	gcc -o unittest3 -g  unittest3.c dominion.o rngs.o $(CFLAGS)

runUT3: unittest3
	./unittest3 &> out.txt
	cat out.txt >> unittestresults.out
	gcov dominion.c -b >> unittestresults.out

runCleanUT3:
	make clean
	make unittest3
	./unittest3 &> out.txt
	cat out.txt >> unittestresultsCLEAN.out
	gcov dominion.c -b >> unittestresultsCLEAN.out

unittest4: unittest4.c dominion.o rngs.o
	gcc -o unittest4 -g  unittest4.c dominion.o rngs.o $(CFLAGS)

runUT4: unittest4
	./unittest4 &> out.txt
	cat out.txt >> unittestresults.out
	gcov dominion.c -b >> unittestresults.out

runCleanUT4:
	make clean
	make unittest4
	./unittest4 &> out.txt
	cat out.txt >> unittestresultsCLEAN.out
	gcov dominion.c -b >> unittestresultsCLEAN.out

cardtest1: cardtest1.c dominion.o rngs.o
	gcc -o cardtest1 -g  cardtest1.c dominion.o rngs.o $(CFLAGS)

runCT1: cardtest1
	./cardtest1 &> out.txt
	cat out.txt >> unittestresults.out
	gcov dominion.c -b >> unittestresults.out

runCleanCT1:
	make clean
	make cardtest1
	./cardtest1 &> out.txt
	cat out.txt >> unittestresultsCLEAN.out
	gcov dominion.c -b >> unittestresultsCLEAN.out

cardtest2: cardtest2.c dominion.o rngs.o
	gcc -o cardtest2 -g  cardtest2.c dominion.o rngs.o $(CFLAGS)

runCT2: cardtest2
	./cardtest2 &> out.txt
	cat out.txt >> unittestresults.out
	gcov dominion.c -b >> unittestresults.out

runCleanCT2:
	make clean
	make cardtest2
	./cardtest2 &> out.txt
	cat out.txt >> unittestresultsCLEAN.out
	gcov dominion.c -b >> unittestresultsCLEAN.out

cardtest3: cardtest3.c dominion.o rngs.o
	gcc -o cardtest3 -g  cardtest3.c dominion.o rngs.o $(CFLAGS)

runCleanCT3:
	make clean
	make cardtest3
	./cardtest3 &> out.txt
	cat out.txt >> unittestresultsCLEAN.out
	gcov dominion.c -b >> unittestresultsCLEAN.out

runCT3: cardtest3
	./cardtest3 &> out.txt
	cat out.txt >> unittestresults.out
	gcov dominion.c -b >> unittestresults.out

cardtest4: cardtest4.c dominion.o rngs.o
	gcc -o cardtest4 -g  cardtest4.c dominion.o rngs.o $(CFLAGS)

runCT4: cardtest4
	./cardtest4 &> out.txt
	cat out.txt >> unittestresults.out
	gcov dominion.c -b >> unittestresults.out
	cat dominion.c.gcov >> unittestresults.out
	rm out.txt

runCleanCT4:
	make clean
	make cardtest4
	./cardtest4 &> out.txt
	cat out.txt >> unittestresultsCLEAN.out
	gcov dominion.c -b >> unittestresultsCLEAN.out
	rm out.txt

#results found in unittestresults.out
#results contains cumulative coverage
runTests: runUT1 runUT2 runUT3 runUT4 runCT1 runCT2 runCT3 runCT4

#results found in unittestresultsCLEAN.out
#results contains individual coverage
runTestsWithClean: runCleanUT1 runCleanUT2 runCleanUT3 runCleanUT4 runCleanCT1 runCleanCT2 runCleanCT3 runCleanCT4

clean:
	rm -f *.o *.gcov *.gcda *.gcno *.so
	rm -f unittest1 unittest1.exe unittest2 unittest2.exe unittest3 unittest3.exe unittest4 unittest4.exe
	rm -f cardtest1 cardtest1.exe cardtest2 cardtest2.exe cardtest3 cardtest3.exe cardtest4 cardtest4.exe
	rm -rf *.dSYM
