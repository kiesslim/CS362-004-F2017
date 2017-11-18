/****************************************************************************
* unittest3.c - Tests
* int updateCoins(int player, struct gameState *state, int bonus)
* starts on line: 1190
*****************************************************************************/
#include "dominion.h"
#include "dominion_helpers.h"
#include <string.h>
#include <stdio.h>
#include <assert.h>
#include "rngs.h"

struct gameState currentGameState, previousGameState;
int bonus = 5;
int numPlayer = 3;
int seed = 10;
int maxHandCount = 5;
int k[10] = {adventurer, gardens, embargo, village, minion, mine, cutpurse,
         sea_hag, tribute, smithy};
int coppers[5];
int silvers[5];
int golds[5];
int success = 1;

void assertTrue(int result, int expected,  char* msg) {
  if(result == expected) {
    printf("PASS: %s\n", msg);
  } else {
    printf("FAIL: %s\n", msg);
    success = 0;
  }
}

void setup() {
  initializeGame(numPlayer, k,  seed, &currentGameState);
  for(int i =0; i < maxHandCount; i++) {
    coppers[i] = copper;
    silvers[i] = silver;
    golds[i] = gold;
  }
  memcpy (&previousGameState, &currentGameState, sizeof(struct gameState));
  //printCounts(&currentGameState);
}

int handCount(int player, struct gameState *g) {
  return g->handCount[player];
}

int main() {
  printf("\n\n----- TESTING UPDATECOINS-----\n\n");
  int bonus;
  setup();
  memcpy(currentGameState.hand[0], coppers, sizeof(int) * (maxHandCount));
  for(int i = 0; i < maxHandCount; i++) {
    bonus = i;
    updateCoins(0, &currentGameState, bonus);
    //printf("expectedCoins= %d, gameState.coins= %d", handCount(0, &currentGameState) * 1 + bonus, currentGameState.coins);
    assertTrue(currentGameState.coins, handCount(0, &currentGameState) * 1 + bonus, "hand full of coppers");
  }

  setup();
  memcpy(currentGameState.hand[0], silvers, sizeof(int) * (maxHandCount));
  for(int i = 0; i < maxHandCount; i++) {
    bonus = i;
    updateCoins(0, &currentGameState, bonus);
    //printf("expectedCoins= %d, gameState.coins= %d", handCount(0, &currentGameState) * 2 + bonus, currentGameState.coins);
    assertTrue(currentGameState.coins, handCount(0, &currentGameState) * 2 + bonus, "hand full of silvers");
  }

  setup();
  memcpy(currentGameState.hand[0], golds, sizeof(int) * (maxHandCount));
  for(int i =0; i < maxHandCount; i++) {
    bonus = i;
    updateCoins(0, &currentGameState, bonus);
    //printf("expectedCoins= %d, gameState.coins= %d", handCount(0, &currentGameState) * 3 + bonus, currentGameState.coins);
    assertTrue(currentGameState.coins, handCount(0, &currentGameState) * 3 + bonus, "hand full of golds");
  }


  if (success) {
    printf("\n\nALL TESTS COMPLETED SUCCESSFULLY\n");
  } else {
    printf("\n\n^^^TESTS FAILED\n");
  }

  return 0;
}
