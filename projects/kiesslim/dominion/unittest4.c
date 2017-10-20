/****************************************************************************
* unittest3.c - Tests
* int isGameOver()
* starts on line:
*****************************************************************************/
#include "dominion.h"
#include "dominion_helpers.h"
#include <string.h>
#include <stdio.h>
#include <assert.h>
#include "rngs.h"

struct gameState game;
int numPlayer = 2;
int seed = 10;
int k[10] = {adventurer, gardens, embargo, village, minion, mine, cutpurse,
         sea_hag, tribute, smithy};
int success = 1;

void assertTrue(int result, int expected,  char* msg) {
  if(result == expected) {
    printf("PASS: %s\n", msg);
  } else {
    printf("FAIL: %s\n", msg);
    success = 0;
  }
}

int emptyKingdomPiles(struct gameState *g) {
  int count = 0;
  for (int i = 0; i < 10; i++) {
    if (g->supplyCount[k[i]] == 0) {
      count++;
    }
  }
  return count;
}

void assertGameNotOver(struct gameState *g) {
  if (g->supplyCount[province] == 0 || (emptyKingdomPiles(g) >= 3)) {
    //failed!
    assertTrue(1, 0, "game ended prematurely");
  } else {
    assertTrue(0,0, "game is not over");
  }
}

void assertGameOver(struct gameState *g) {
  if (g->supplyCount[province] == 0 || (emptyKingdomPiles(g) >= 3)) {
    assertTrue(0, 0, "game ended as expected");
  } else {
    assertTrue(0,0, "game continued, but expected game to end");
  }
}

void setup() {
  initializeGame(numPlayer, k,  seed, &game);
}


int main() {

  printf("\n\n----- TESTING ISGAMEOVER-----\n\n");
  setup();

  for(int i = 0; i < 3; i++) {
    setup();
    for(int j = 0; j < 10; j++) {
      game.supplyCount[k[j]] = 0;
      if(emptyKingdomPiles(&game) < 3) {
        assertTrue(isGameOver(&game), 0, "game, that should NOT be over, is not over");
      } else {
        assertTrue(isGameOver(&game), 1, "game, that should be over, is over");
      }
    }
  }

  if (success) {
    printf("\n\nALL TESTS COMPLETED SUCCESSFULLY\n");
  } else {
    printf("\n\n^^^TESTS FAILED\n");
  }

  return 0;
}
