/****************************************************************************
* unittest2.c - Tests
* int discardCard(int handPos, int currentPlayer, struct gameState *state, int trashFlag)
* starts on line: 1118
*****************************************************************************/
#include "dominion.h"
#include "dominion_helpers.h"
#include <string.h>
#include <stdio.h>
#include <assert.h>
#include "rngs.h"

struct gameState currentGameState, previousGameState;
int j;
int handPos;
int player;
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

void printCounts(struct gameState *g) {
  printf("deck=%d, discard=%d, hand=%d, playedCards=%d\n",
  g->deckCount[0], g->discardCount[0], g->handCount[0], g->playedCardCount);
}

void setup() {
  initializeGame(numPlayer, k,  seed, &currentGameState);
  for(int i =0; i < 5; i++) {
    currentGameState.playedCards[i] = 0;
  }
  memcpy (&previousGameState, &currentGameState, sizeof(struct gameState));
  handPos = currentGameState.handCount[0] - 1;
  j = 0;
  //printCounts(&currentGameState);
}

int main() {
  printf("\n\n----- TESTING DISCARDCARD-----\n\n");
  setup();
  // Test all cards moved to play pile
  printf("----- TEST: Discarding All Cards in Hand-----\n");
  while (currentGameState.handCount[player] > 0) {
    discardCard(handPos, player, &currentGameState, 0);
    assertTrue(
      currentGameState.hand[player][handPos], -1, "discared card removed from hand"
    );
    assertTrue(
      previousGameState.hand[player][handPos],
      currentGameState.playedCards[j],
      "discarded card appears in played cards in correct position"
    );
    memcpy(&previousGameState, &currentGameState, sizeof(struct gameState));
    handPos--;
    j++;
  }
  assertTrue(currentGameState.playedCardCount, j, "playedCardCount == 5");
  assertTrue(currentGameState.handCount[0], 0, "handCount == 0");

  printf("\n\n----- TEST: Trash Cards -----\n");
  setup();
  while (currentGameState.handCount[player] > 0) {
    discardCard(handPos, player, &currentGameState, 1);
    assertTrue(
      currentGameState.hand[player][handPos], -1, "discarded card removed from hand"
    );
    memcpy(&previousGameState, &currentGameState, sizeof(struct gameState));
    handPos--;
    j++;
  }
  assertTrue(currentGameState.playedCardCount, 0, "playedCardCount == 0");
  assertTrue(currentGameState.handCount[0], 0, "handCount == 0");

  printf("\n\n----- TEST: discarding Cards from middle of Hand @ HandPos = 1 -----\n");
  setup();
  handPos = 1;
  while (currentGameState.handCount[player] > handPos) {
    discardCard(handPos, player, &currentGameState, 0);
    int expected = previousGameState.hand[player][previousGameState.handCount[0] -1];;
    if (currentGameState.handCount[player] == handPos) {
      expected = -1;
    }
    assertTrue(
      currentGameState.hand[player][handPos],
      expected,
      "card removed from middle of deck replaced with last card in previous hand"
    );
    assertTrue(currentGameState.handCount[0], previousGameState.handCount[0] - 1, "hand count decreased");
    memcpy(&previousGameState, &currentGameState, sizeof(struct gameState));
  }

  if (success) {
    printf("\n\nALL TESTS COMPLETED SUCCESSFULLY\n");
  } else {
    printf("\n\n^^^TESTS FAILED\n");
  }

  return 0;
}
