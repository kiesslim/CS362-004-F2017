/****************************************************************************
* cardtest2.c - Tests
* int adventurerCardEffect()
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
    printf("FAIL: %s\n", msg);
  }
}

//initialize game with hand and deck full of estates
void setup() {
  initializeGame(numPlayer, k,  seed, &currG);
  for(int i = 0; i < 5; i++) {
    currG.hand[0][i] = estate;
    currG.deck[0][i] = estate;
  }
  memcpy (&prevG, &currG, sizeof(struct gameState));
}

int treasureCount(struct gameState *g) {
  int treasure = 0;
  for(int i = 0; i < g->handCount[0]; i++) {
    if(g->hand[0][i] == copper) { treasure += 1; }
    if (g->hand[0][i] == silver) { treasure += 2; }
    if (g->hand[0][i] == gold) { treasure += 3; }
  }
  return treasure;
}

int treasureCountDeck(struct gameState *g) {
  int treasure = 0;
  for(int i = 0; i < g->deckCount[0]; i++) {
    if(g->deck[0][i] == copper) { treasure += 1; }
    if (g->deck[0][i] == silver) { treasure += 2; }
    if (g->deck[0][i] == gold) { treasure += 3; }
  }
  return treasure;
}


int main() {
  printf("\n\n----- TESTING ADVENTURER-----\n\n");
  printf("----- TESTING NUMBER OF TREASURE COINS IN HAND AFTER ADVENTURER PLAYED -----\n\n");
  for (int i = 0; i < 5; i++) {
    setup();
    // set 0... 5 coppers in deck
    printf("\n\nTEST Treasure in Deck =%d\n", i);
    for (int j=0; j < i; j++) {
      currG.deck[0][j] = copper;
    }
    cardEffect(adventurer, choice1, choice2, choice3, &currG, handPos, &bonus);
    //adventurerCardEffect(&currG);
    if (i == 0) {
      printf("treasureCount = %d\n", treasureCount(&currG));
      assertTrue(treasureCount(&currG), 0, "deck with no coppers, does not increase treasure count");
    } else if (i == 1) {
      printf("treasureCount = %d\n", treasureCount(&currG));
      assertTrue(treasureCount(&currG), 1, "deck of with 1 copper, adds 1 copper to hand");
    } else {
      printf("treasureCount = %d\n", treasureCount(&currG));
      assertTrue(treasureCount(&currG), 2, "deck with 2...n coppers, adds 2 coppers to hand");
    }
  }

  if (success) {
    printf("\n\nALL TESTS COMPLETED SUCCESSFULLY\n");
  } else {
    printf("\n\n^^^TESTS FAILED\n");
  }

  return 0;
}
