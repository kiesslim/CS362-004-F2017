/****************************************************************************
* cardtest3.c - Tests
* int minionCardEffect(struct gameState *state, int choice1, int choice2, int handPos)
* int cardEffect(int card, int choice1, int choice2, int choice3, struct gameState *state, int handPos, int *bonus)
* starts on line:
*****************************************************************************/
#include "dominion.h"
#include "dominion_helpers.h"
#include <string.h>
#include <stdio.h>
#include "rngs.h"

struct gameState currG, prevG;
int numPlayer = 4;
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

int main() {
  printf("\n\n----- TESTING MINION choice1 = %d; choice2 = %d -----\n\n", 1, 0);
  setup();
  choice1 = 1;
  while(currG.handCount[0] > 0) {
    cardEffect(minion, choice1, choice2, choice3, &currG, handPos, &bonus);
    //minionCardEffect(&currG, 1, 0, handPos);
    assertTrue(currG.numActions, prevG.numActions + 1,  "number of Actions increased by 1");
    assertTrue(currG.coins, prevG.coins +2, "number of coins increased by 2");
    memcpy (&prevG, &currG, sizeof(struct gameState));
  }

  printf("\n\n----- TESTING MINION choice1 = %d; choice2 = %d -----\n\n", 0, 1);
  setup();
  choice1 = 0;
  choice2 = 1;
  for (int i = 0; i < 5; i++) {
    if (i < 3) {
      currG.hand[3][i] = estate;
    }
    currG.hand[1][i] = estate;
    currG.hand[2][i] = estate;
  }
  for (int i = 0; i < 3; i++) {
    currG.hand[3][i] = estate;
  }
  currG.handCount[1] = 5;
  currG.handCount[2] = 5;
  currG.handCount[3] = 3;

  cardEffect(minion, choice1, choice2, choice3, &currG, handPos, &bonus);
  //minionCardEffect(&currG, 0, 1, handPos);
  assertTrue(currG.numActions, prevG.numActions + 1,  "number of Actions increased by 1");
  assertTrue(currG.coins, prevG.coins , "number of coins did not increase");
  assertTrue(currG.handCount[0], 4, "handCount player1 == 4");
  assertTrue(currG.handCount[1], 4, "handCount player2 == 4");
  assertTrue(currG.handCount[2], 4, "handCount player3 == 3");

  memcpy (&prevG, &currG, sizeof(struct gameState));


  if (success) {
    printf("\n\nALL TESTS COMPLETED SUCCESSFULLY\n");
  } else {
    printf("\n\n^^^TESTS FAILED\n");
  }

  return 0;
}
