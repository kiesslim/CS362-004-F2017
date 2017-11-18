/****************************************************************************
* randomtestadventurer.c - Tests
* int adventurerCardEffect()
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
int maxDeckCount = MAX_DECK;
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
void setup(struct gameState *curr) {
  initializeGame(numPlayer, k,  seed, curr);
  curr->whoseTurn = 0;
  for(int i = 0; i < maxHandCount; i++) {
    curr->hand[0][i] = estate;
  }
  curr->handCount[0] = maxHandCount;

  for(int i = 0; i < maxDeckCount; i++) {
      curr->deck[0][i] = estate;
  }
  curr->deckCount[0] = maxDeckCount;
}

//insert n # treasures into hand randomly
void randomizeHand(int n, struct gameState *curr) {
  //insert n # treasures into hand randomly
  for(int i = 0; i < n; i++){
    int idx = rand() % 5;
    int coin = (rand() % (gold + 1 - copper)) + copper;
    curr->hand[0][idx] = coin;
  }
}

void randomizeDeck(int n, struct gameState *curr) {
  //insert n # treasures into deck randomly
  for(int i = 0; i < n; i++){
    int idx = rand() % maxDeckCount;
    int coin = (rand() % (6 + 1 - 4)) + 4;
    curr->deck[0][idx] = coin;
  }
}

int treasureCardCountHand(struct gameState *g) {
  int count = 0;
  for(int i = 0; i < g->handCount[0]; i++) {
    if(
      g->hand[0][i] == copper ||
      g->hand[0][i] == silver ||
      g->hand[0][i] == gold)
      { count++; }
  }

  return count;
}

int treasureCardCountDeck(struct gameState *g) {
  int count = 0;
  for(int i = 0; i < g->deckCount[0]; i++) {
    if(
      g->deck[0][i] == copper ||
      g->deck[0][i] == silver ||
      g->deck[0][i] == gold)
      { count++; }
  }
  return count;
}


int main() {
  srand(time(NULL));
  printf("\n\n----- RANDOM TESTING ADVENTURER-----\n\n");
  for (int i = 0; i < 1000; i++) {
    struct gameState currG, prevG;
    setup(&currG);
    memcpy (&prevG, &currG, sizeof(struct gameState));
    printf("\n\nTEST # %d\n\n", i+1);
    int nDeck = rand() % 10;
    int nHand = rand() % maxHandCount;
    printf("# treasure cards in deck = %i\n# treasure cards in hand = %i\n", nDeck, nHand);
    randomizeHand(nHand, &currG);
    randomizeDeck(nDeck, &currG);
    memcpy (&prevG, &currG, sizeof(struct gameState));

    cardEffect(adventurer, choice1, choice2, choice3, &currG, handPos, &bonus);
    printf("# adventurerCardEffect added %d treasure cards to hand\n", treasureCardCountHand(&currG)- treasureCardCountHand(&prevG));

    if (treasureCardCountDeck(&prevG) >= 2) {
      assertTrue(treasureCardCountHand(&currG),
          treasureCardCountHand(&prevG)+2,
          "deck with 2...n coppers, adds 2 coppers to hand");
    } else if (treasureCardCountDeck(&prevG) < 2 && treasureCardCountDeck(&prevG) > 0) {
      assertTrue(treasureCardCountHand(&currG),
          treasureCardCountHand(&prevG)+1,
          "deck with 1 treasure adds 1 treasure to hand");
    } else {
      printf("no treasure cards\nß");
      assertTrue(treasureCardCountHand(&currG),
          treasureCardCountHand(&prevG),
          "deck with no treasure does not increase treasure count");
    }
  }

  if (success) {
    printf("\n\nALL TESTS COMPLETED SUCCESSFULLY\n");
  } else {
    printf("\n\n^^^TESTS FAILED\n");
  }

  return 0;
}
