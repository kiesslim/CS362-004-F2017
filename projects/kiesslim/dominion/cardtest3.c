/****************************************************************************
* cardtest3.c - Tests
* int salvagerCardEffect(struct gameState *state, int choice1, int handPos)
* starts on line:
*****************************************************************************/
#include "dominion.h"
#include "dominion_helpers.h"
#include <string.h>
#include <stdio.h>
#include "rngs.h"

struct gameState currG, prevG;
int numPlayer = 2;
int seed = 10;
int maxHandCount = 10;
int handPos = 0, choice1 = 0, choice2 = 0, choice3 = 0, bonus = 0;
int k[10] = {adventurer, gardens, salvager, village, minion, mine, cutpurse,
         sea_hag, tribute, smithy};
int success = 1;

void assertTrue(int result, int expected,  char* msg) {
  if(result == expected) {
    printf("PASS: %s\n", msg);
  } else {
    success = 0;
    printf("FAIL: %s\n", msg);
  }
}

//initialize game with hand and deck full of estates. each estate card is worth 2 coins
void setup() {
  initializeGame(numPlayer, k,  seed, &currG);
  for (int i =0; i < 10; i++) {
    currG.hand[0][i] = estate;
  }
  currG.handCount[0] = 10;
  memcpy (&prevG, &currG, sizeof(struct gameState));
}

void testSalvager() {
  printf("\n\n----- TESTING SALVAGER choice1 = %d -----\n\n", choice1);
  setup();
  int handPos = 0;
  while(currG.handCount[0] > 0) {
    cardEffect(salvager, choice1, choice2, choice3, &currG, handPos, &bonus);
    //salvagerCardEffect(&currG, choice1, handPos);
    assertTrue(currG.coins, prevG.coins + 2, "2 coins added to hand");
    assertTrue(currG.numBuys, prevG.numBuys + 1, "buys increased by 1");
    memcpy (&prevG, &currG, sizeof(struct gameState));
  }
}

int main() {
  choice1 = 0;
  testSalvager();
  choice1 = 1;
  testSalvager();

  if (success) {
    printf("\n\nALL TESTS COMPLETED SUCCESSFULLY\n");
  } else {
    printf("\n\n^^^TESTS FAILED\n");
  }

  return 0;
}
