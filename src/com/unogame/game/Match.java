/**
 * 
 */
package com.unogame.game;

import com.unogame.system.Player;
import com.unogame.tablegame.EffectsController;
import com.unogame.tablegame.PlayersManager;
import com.unogame.tablegame.Table;
import com.unogame.uno.UnoCard;

/**
 * @author Lawkesh
 *
 */
public class Match implements Game {

		private EffectsController eControl;
		private PlayersManager pControl;
		private Table table;
		private static Match match = null;
		private final static int N_CARDS_INI = 7;
		
		private Match(){
			this.table = Table.getInstance();
			this.pControl = PlayersManager.getInstance();
			this.eControl = new EffectsController(this.table,this.pControl);
		}

		
		public static Match getInstance(){
			if(match == null)
				match = new Match();
			
			return match;
		}
		
		/**
		 * Get prepare to start the game. 
		 */
		@Override
		public void init() {
			this.table.prepareTable();		
		}

		/**
		 * Starts the game distributing cards to all players.
		 */
		@Override
		public void start() {
			if(this.verifyNumPlayers() == false){
				System.out.println("Number of players is too low or too high.");
				return;
			}
			
			this.distributeCards();
			
			
		}
		
		/**
		 * The current player takes a card from the deck.
		 * @return true if the operation was successful, false otherwise.
		 */
		public boolean playerTakeCard(){
			Player cPlayer = this.pControl.getCurrent();
			UnoCard card = this.table.pullCard();
			
			return cPlayer.takeCard(card);
			
		}
		
		/**
		 * Verify if the number of players is a valid. To be valid, the game
		 * must contain at least two players and a maximum of ten.  
		 * @return true if the number of players is in the valid interval, false 
		 * otherwise.
		 */
		public boolean verifyNumPlayers(){
			if(this.pControl.getNumPlayers() <= 10 && 
					this.pControl.getNumPlayers() > 1)
				return true;
			
			return false;
		}
		
		/**
		 * Distribute 7 cards to all players in the game.
		 * @return true if the operation was successful, false otherwise.
		 */
		private boolean distributeCards(){
			Player p = this.pControl.getCurrent();
			int firstID = p.getID();
			int i;
			UnoCard card;

			
			do{
				for(i = 0; i < N_CARDS_INI; i++){
					card = this.table.pullCard();
					if(card == null)
						return false;
					
					p.takeCard(card);
				}
				
				pControl.rotate();
				p = this.pControl.getCurrent();
			}while(p.getID() != firstID);
			
			return true;
		}
		
		/**
		 * The current player plays the desired card. If the card requested
		 * is not in player's hand, than nothing is done and the failure is
		 * reported.
		 * @param name: the name of the card that will be played.
		 * @return true if the operation was successful, false otherwise.
		 */
		public boolean playerPlayCard(String name){
			Player cPlayer = this.pControl.getCurrent();
			UnoCard card = cPlayer.playCard(name);
			if(card == null)
				return false;
			
			if(this.table.pushCard(card) == false){
				cPlayer.takeCard(card);
				return false;
			} 
			return true;
		}
		
		/**
		 * Apply the effect of the last card played in the game.
		 */
		public void applyEffect(){
			UnoCard card = this.table.showTopCard();
			card.applyEffect(eControl);
			
		}
		
		/**
		 * Apply the effect of an color change card in the game.
		 * @param wildColor - the color choosed by the user.
		 */
		public boolean applyEffect(String wildColor){
			if(this.eControl.setWildColor(wildColor) == false)
				return false;
			
			this.applyEffect();
			return true;
		}
		
		/**
		 * Finish a player's turn. If the player has only one more card left,
		 * then it must signalizes with 'UNO'.
		 * @param advertUno true if the player says 'UNO', false otherwise.
		 */
		public void passTurn(boolean advertUno){
			if(advertUno){
				if(pControl.getCurrent().numCards() != 1){
					this.playerTakeCard();
					this.playerTakeCard();
				} else{
					this.announceUno();
				}	

			} else{
				if(pControl.getCurrent().numCards() == 1){
					this.playerTakeCard();
					this.playerTakeCard();
				}
			}
			
			pControl.rotate();
			
				
		}

		/**
		 * Verify if the current player have no more cards.
		 * @return true if the player has no more cards, false otherwise.
		 */
		public boolean isEmptyHand(){
			return (0 == pControl.getCurrent().numCards());
		}
		
		
		
		
		/**
		 * Show the final message informing the end of the game and the winner.
		 */
		@Override
		public void finish() {
			Player winner = pControl.lookForWinner();
			
			if(winner == null)
				System.out.println("\nNO WINNER IN THIS GAME. =(");
			else
				System.out.println("\nCONGRATULATIONS " + winner.getName()+ "nYOU DEFEATED " + pControl.getNumPlayers()	 + " OPONENTS.");
			
				 
		}
		
		/**
		 * No announce his/her uno)
		 */
		private void announceUno(){
			
			System.out.println("UNO");
			
		}
	}


