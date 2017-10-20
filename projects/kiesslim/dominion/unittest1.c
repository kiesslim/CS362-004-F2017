/****************************************************************************
* unittest1.c - Tests
* int drawCard(int player, struct gameState *state)
* starts on line: 525
*****************************************************************************/
#include "dominion.h"
#include "dominion_helpers.h"
#include <string.h>
#include <stdio.h>
#include <assert.h>
#include "rngs.h"

int success = 1;

void assertTrue(int result, int expected,  char* msg) {
  if(result == expected) {
    printf("PASS: %s\n", msg);
  } else {
    success = 0;
    printf("FAIL: %s\n", msg);
  }
}

void testHandCountIncreasedBy1(struct gameState *prev, struct gameState *curr) {
    //verify 1 card added to hand (i.e. handCount + 1) && result == 1
    int result = curr->handCount[0] - prev->handCount[0];
    int expected = 1;
    assertTrue(result, expected, "handCount increased by 1");
}

void testReturnStatusOfSuccessfulDrawCard(int result) {
    //assert return value == 0
    assertTrue(result, 0, "drawCard() returned 0 on success");
}

void testReturnValueForEmptyDeckAndDiscard(int result) {
  assertTrue(result, -1, "drawCard() returns -1 when discardCount && deckCount == 0");
}

void testDeckCountDecreasedBy1(struct gameState *prev, struct gameState *curr) {
  //verify 1 card removed from deck (i.e. deckCount -1)
  int result = prev->deckCount[0] - curr->deckCount[0];
  assertTrue(result, 1, "deckCount decreased by 1");
}

void testCardAddedToHandIsCardRemovedFromDeck(struct gameState *prev, struct gameState *curr) {
  //verify card added to hand == card drawn from deck
  int newCardInHand = curr->hand[0][curr->handCount[0] - 1];
  int cardRemovedFromDeck = prev->deck[0][prev->deckCount[0] - 1];
  assertTrue(newCardInHand, cardRemovedFromDeck, "card added to hand matches card removed from deck");
}

void testDrawFromEmptyDeckAndEmptyDiscard(struct gameState *prev, struct gameState *curr) {
    //if deckCount == 0, before drawCard, verify discardCount, and deckCount && result == 1
    int result = drawCard(0, curr);
    assertTrue(curr->deckCount[0], 0, "deckCount ==  0");
    assertTrue(curr->discardCount[0], 0, "discardCount == 0");
    assertTrue(result, -1, "return status == -1");
    assertTrue(curr->handCount[0] - prev->handCount[0], 0, "handCount did not change");
    memcpy (prev, curr, sizeof(struct gameState));
}

void testDrawFromEmptyDeck(struct gameState* prev, struct gameState *curr) {
  int result = drawCard(0, curr);
  assertTrue(
    curr->deckCount[0],
    prev->discardCount[0] - 1,
    "deck contains all cards previously in discard, less 1"
  );
  testHandCountIncreasedBy1(prev, curr);
  testReturnStatusOfSuccessfulDrawCard(result);
  memcpy (prev, curr, sizeof(struct gameState));
}

void printCounts(struct gameState *g) {
  printf("deck=%d, discard=%d, hand=%d, playedCards=%d\n",
  g->deckCount[0], g->discardCount[0], g->handCount[0], g->playedCardCount);
  // int i;
  // for(i =0; i < 20; i++) {
  //   printf("deck[%d]=%d, ", i, g->deckCount[i]);
  // }
  // printf("\n");
  //
  // for(i =0; i < 20; i++) {
  //   printf("discard[%d]=%d, ", i, g->discardCount[i]);
  // }
  // printf("\n");
  //
  // for(i =0; i < 20; i++) {
  //   printf("hand[%d]=%d, ", i, g->handCount[i]);
  // }
  // printf("\n");
  //
  // for(i=0; i < 20; i++) {
  //   printf("playedCards[%d]=%d, ", i, g->playedCards[i]);
  // }
  // printf("\n");
}

void testDrawAllCardsFromDeck(struct gameState *prev, struct gameState *curr, int player) {
  int i = 0;
  //Draw Cards until deck is empty
  while (curr->deckCount[player] > 0) {
    i++;
    printf("Drawing Card %d:\n", i);
    int result = drawCard(player, curr);
    testHandCountIncreasedBy1(prev, curr);
    testDeckCountDecreasedBy1(prev, curr);
    testCardAddedToHandIsCardRemovedFromDeck(prev, curr);
    testReturnStatusOfSuccessfulDrawCard(result);
    memcpy (prev, curr, sizeof(struct gameState));
    printf("\n");
  }
  printf("\n");
}

int main() {
  printf("\n\n----- TESTING DRAWCARD-----\n\n");
  int seed = 30;
  int numPlayer = 2;

  int k[10] = {adventurer, gardens, embargo, village, minion, mine, cutpurse,
           sea_hag, tribute, smithy};

  struct gameState currentGameState, previousGameState;

  initializeGame(numPlayer, k,  seed, &currentGameState);
  memcpy (&previousGameState, &currentGameState, sizeof(struct gameState));
  printCounts(&currentGameState);

  int player = currentGameState.whoseTurn;
  printf("----- TEST: Drawing All Available Cards in Deck-----\n");
  testDrawAllCardsFromDeck(&previousGameState, &currentGameState, player);

  //draw card from empty deck & empty discard (ie all cards in hand)
  //pre-conditions: deckCount == 0, discardCount == 0, handCount == 10
  //post-conditions: deckCount == 0, discardCount == 0, handCount == 10
  printf("----- TEST: Draw from Empty Deck and Empty Discard -----\n");
  testDrawFromEmptyDeckAndEmptyDiscard(&previousGameState, &currentGameState);
  printf("\n\n");

  //move all cards from hand to discard
  int handPos = currentGameState.handCount[player] - 1;
  int i;
  for (i=0; i < 10; i++) {
    currentGameState.discard[player][i] = currentGameState.hand[player][i];
    currentGameState.hand[player][i] = -1;
    currentGameState.handCount[player]--;
    currentGameState.discardCount[player]++;
    handPos--;
    memcpy (&previousGameState, &currentGameState, sizeof(struct gameState));
  }

  //draw card from empty deck &  non-empty discard
  //pre-conditions: deckCount == 0, discardCount == 10, handCount == 0
  //post-conditions: deckCount == 9, discardCount == 0, handCount == 1
  printf("----- TEST: Draw One Card From Empty Deck but Non-Empty Discard -----\n");
  testDrawFromEmptyDeck(&previousGameState, &currentGameState);
  printf("\n\n");

  printf("----- TEST: Drawing All Available Cards in Deck-----\n");
  testDrawAllCardsFromDeck(&previousGameState, &currentGameState, player);

  if (success) {
    printf("ALL TESTS COMPLETED SUCCESSFULLY\n");
  } else {
    printf("^^^TESTS FAILED\n");
  }

  return 0;
}
