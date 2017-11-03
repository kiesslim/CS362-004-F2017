/****************************************************************************
* randomcardtest1.c - Tests
* int salvagerCardEffect(struct gameState *state, int choice1, int handPos)
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
int maxHandCount = 10;
int handPos = 0, choice1 = 0, choice2 = 0, choice3 = 0, bonus = 0;
int k[10] = {adventurer, gardens, salvager, village, minion, mine, cutpurse,
         sea_hag, tribute, smithy};
int success = 1;

int getCardCost(int card) {
  int cost;
  switch(card) {
  case curse: cost = 0;
    break;
  case estate: cost = 2;
    break;
  case duchy: cost = 5;
    break;
  case province: cost = 8;
    break;
  case copper: cost = 0;
    break;
  case silver: cost = 3;
    break;
  case gold: cost = 6;
    break;
  case adventurer: cost = 6;
    break;
  case council_room: cost = 5;
    break;
  case feast: cost = 4;
    break;
  case gardens: cost = 4;
    break;
  case mine:  cost = 5;
    break;
  case remodel: cost = 4;
    break;
  case smithy: cost = 4;
    break;
  case village: cost = 3;
    break;
  case baron: cost = 4;
    break;
  case great_hall: cost = 3;
    break;
  case minion: cost = 5;
    break;
  case steward: cost = 3;
    break;
  case tribute: cost = 5;
    break;
  case ambassador: cost = 3;
    break;
  case cutpurse: cost = 4;
    break;
  case embargo: cost = 2;
    break;
  case outpost: cost = 5;
    break;
  case salvager: cost = 4;
    break;
  case sea_hag: cost = 4;
    break;
  case treasure_map: cost = 4;
    break;
  default: cost = -100;
  }
  return cost;
}

void assertTrue(int result, int expected,  char* msg) {
  if(result == expected) {
    //printf("PASS: %s\n", msg);
  } else {
    success = 0;
    printf("FAIL: %s\n", msg);
  }
}

void assertCoinsAdded(int result, int expected, int card) {
  if(result == expected) {
    //printf("PASS: %d coins added to hand\n", getCardCost(card));
  } else {
    success = 0;
    printf("FAIL: %d coins added to hand\n", getCardCost(card));
  }
}
//27 cards . rand range ==> 0-26
//initialize game with hand and deck full a randomly selected card
void setup(struct gameState *curr) {
  initializeGame(numPlayer, k,  seed, curr);
  for (int i =0; i < 500; i++) {
    int card = rand() % 27;
    curr->hand[0][i] = card;
  }
  curr->handCount[0] = 500;

}

void testSalvager() {
  printf("\n\n----- TESTING SALVAGER choice1 = %d -----\n\n", choice1);
  struct gameState currG, prevG;
  setup(&currG);
  memcpy (&prevG, &currG, sizeof(struct gameState));
  while(currG.handCount[0] > 0) {
    int handPos = rand() % currG.handCount[0];
    //printf("handPos = %d\n", handPos);
    //printf("handCount = %d\n", currG.handCount[0]);
    cardEffect(salvager, choice1, choice2, choice3, &currG, handPos, &bonus);
    //printf("card = %d\n", prevG.hand[0][choice1]);
    assertCoinsAdded(currG.coins, prevG.coins + getCardCost(prevG.hand[0][choice1]), prevG.hand[0][choice1]);
    assertTrue(currG.numBuys, prevG.numBuys + 1, "buys increased by 1");
    memcpy (&prevG, &currG, sizeof(struct gameState));
  }
}


int main() {
  srand(time(NULL));
  for(int i = 0; i < 5; i++) {
    int choice = rand() % 2;
    choice1 = choice;
    testSalvager();
  }


  if (success) {
    printf("\n\nALL TESTS COMPLETED SUCCESSFULLY\n");
  } else {
    printf("\n\n^^^TESTS FAILED\n");
  }

  return 0;
}
