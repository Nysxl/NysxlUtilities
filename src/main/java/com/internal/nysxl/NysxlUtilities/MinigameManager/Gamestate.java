    package com.internal.nysxl.NysxlUtilities.MinigameManager;

    public class Gamestate implements GamestateInterface{

        private String gameState;
        private Gamestate nextGameState;

        public Gamestate(String gameState, Gamestate nextGameState){
            this.gameState = gameState.toUpperCase();
            this.nextGameState = nextGameState; // This line is missing in your code.
        }

        @Override
        public Gamestate nextGameState() {
            return nextGameState;
        }

        public boolean isGameState(Gamestate gameState){
            return this.gameState.equalsIgnoreCase(gameState.getGameState());
        }

        public String getGameState() {
            return gameState;
        }

        public Gamestate getNextGameState() {
            return nextGameState;
        }
    }
