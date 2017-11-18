/****************************************************************************
* randomcardtest1.c - Tests
* int treasure_mapCardEffect(struct gameState *state, int handPos)
* starts on line:
*****************************************************************************/
#include "dominion.h"
#include "dominion_helpers.h"
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "rngs.h"


int numPlayer = 2;
int seed = 10;
int maxHandCount = MAX_HAND;
int startDeckSize = 5;
int handPos = 0, choice1 = 0, choice2 = 0, choice3 = 0, bonus = 0;
int k[10] = {adventurer, gardens, salvager, village, minion, mine, cutpurse,
         sea_hag, tribute, smithy};
int success = 1;



void assertTrue(int result, int expected,  char* msg) {
  if(result == expected) {
    //printf("PASS: %s\n", msg);
  } else {
    success = 0;
    printf("FAIL: %s\n", msg);
  }
}


//initialize game with hand full of estate cards and n treasure_map cards
void setup(struct gameState *curr) {
  initializeGame(numPlayer, k,  seed, curr);
  int nTreasureCards = rand() % 25;

  for(int i = 0; i < maxHandCount; i++) {
    curr->hand[0][i] = estate;
  }

  for (int i = 0; i < nTreasureCards; i++) {
    int idx;
    do {
      idx = rand() % maxHandCount;
    } while (curr->hand[0][idx] == treasure_map);
    curr->hand[0][i] = treasure_map;
  }

  for(int i = 0; i < startDeckSize; i++) {
    curr->deck[0][i] = estate;
  }

  curr->handCount[0] = 500;
  curr->discardCount[0] = 0;
  curr->deckCount[0] = startDeckSize;
  curr->supplyCount[gold] = 20000;
}

int treasureCardCount(struct gameState *g) {
  int count = 0;
  for(int i = 0; i < g->handCount[0]; i++) {
    if(g->hand[0][i] ==  treasure_map) {
      count++;
    }
  }
  return count;
}

int findTreasureCard(struct gameState *g) {
  for(int i = 0; i < g->handCount[0]; i++) {
    if(g->hand[0][i] ==  treasure_map) {
      return i;
    }
  }
  return -1;
}

int goldsInDeck(struct gameState *g) {
  int count = 0;
  for(int i = 0; i < g->deckCount[0]; i++) {
    if(g->deck[0][i] == gold) {
      count++;
    }
  }
  return count;
}

void testTreasureMap(int i) {
  printf("\n\n----- TESTING TREASURE_MAP: Test #%d-----\n\n", i);
  struct gameState currG, prevG;
  setup(&currG);
  memcpy (&prevG, &currG, sizeof(struct gameState));

  int oneleft = 0;
  while((currG.handCount[0] > 0 || treasureCardCount(&currG)!=0) && oneleft < 5) {

    printf("treasureCards in Hand = %d\n", treasureCardCount(&currG));
    int handPos = findTreasureCard(&currG);
    if (handPos == -1) {
      break;
    }
    cardEffect(treasure_map, choice1, choice2, choice3, &currG, handPos, &bonus);

    if(treasureCardCount(&currG) == 1) {
      oneleft++;
    }

    printf("currDeckGolds = %d\n", goldsInDeck(&currG));
    printf("prevDeckGolds = %d\n", goldsInDeck(&prevG));
    if (treasureCardCount(&prevG) >= 2) {
      assertTrue(goldsInDeck(&currG) - goldsInDeck(&prevG), 4, "4 golds added to deck");
      assertTrue(currG.handCount[0], prevG.handCount[0]-2, "two cards removed from hand");
      assertTrue(currG.discardCount[0], 0, "treasure cards were trashed");
    } else {
      assertTrue(goldsInDeck(&currG) - goldsInDeck(&prevG), 0, "No golds added to deck");
      assertTrue(currG.handCount[0], prevG.handCount[0]-1, "one card removed from hand");
      assertTrue(currG.discardCount[0], 0, "treasure cards were trashed");
    }
    memcpy (&prevG, &currG, sizeof(struct gameState));
  }
}


int main() {
  srand(time(NULL));
  for(int i =0; i < 10; i++) {
      testTreasureMap(i);
  }

  if (success) {
    printf("\n\nALL TESTS COMPLETED SUCCESSFULLY\n");
  } else {
    printf("\n\n^^^TESTS FAILED\n");
  }

  return 0;
}
