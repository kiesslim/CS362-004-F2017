CFLAGS = -Wall -fpic -coverage -lm

testme: testme.c
	gcc -o testme -g  testme.c $(CFLAGS)


clean:
	rm -f *.o *.gcov *.gcda *.gcno *.so
	rm -f testme testme.exe
	rm -rf *.dSYM
