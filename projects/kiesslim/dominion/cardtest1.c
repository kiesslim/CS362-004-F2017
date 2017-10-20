/****************************************************************************
* cardtest1.c - Tests
* int smithyCardEffect(struct gameState *state, int handPos)
* starts on line: 1300
*****************************************************************************/
#include "dominion.h"
#include "dominion_helpers.h"
#include <string.h>
#include <stdio.h>
#include "rngs.h"

struct gameState currG, prevG;
int numPlayer = 2;
int seed = 10;
int maxHandCount = 5;
int handPos = 0, choice1 = 0, choice2 = 0, choice3 = 0, bonus = 0;
int k[10] = {adventurer, gardens, embargo, village, minion, mine, cutpurse,
         sea_hag, tribute, smithy};
int success = 1;

void assertTrue(int result, int expected,  char* msg) {
  if(result == expected) {
    printf("PASS: %s\n", msg);
  } else {
    success = 0;
    printf("FAIL: only %d cards added to hand, expected 3\n", currG.handCount[0] - prevG.handCount[0] + 1);
  }
}

void setup() {
  initializeGame(numPlayer, k,  seed, &currG);
  for(int i = 0; i < 200; i++) {
    currG.deck[0][i] = 1;
  }
  currG.deckCount[0] = 200;
  memcpy (&prevG, &currG, sizeof(struct gameState));

  //printCounts(&currentGameState);
}

int main() {
  printf("\n\n----- TESTING SMITHY-----\n\n");
  setup();

  memcpy (&prevG, &currG, sizeof(struct gameState));
  while( currG.deckCount[0] > 0 ) {
    cardEffect(smithy, choice1, choice2, choice3, &currG, handPos, &bonus);
    //smithyCardEffect(&currG, handPos);
    assertTrue(currG.handCount[0], prevG.handCount[0] + 3 - 1, "3 cards added to hand");
    memcpy (&prevG, &currG, sizeof(struct gameState));
  }

  if (success) {
    printf("\n\nALL TESTS COMPLETED SUCCESSFULLY\n");
  } else {
    printf("\n\n^^^TESTS FAILED\n");
  }

}
