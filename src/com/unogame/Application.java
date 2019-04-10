package com.unogame;

import com.unogame.game.Match;
import com.unogame.interpreter.Interpreter;
import com.unogame.system.SignUpPlayers;

public class Application {

	public static void main(String[] args) {
		Interpreter inter = new Interpreter();	
		SignUpPlayers sign = new SignUpPlayers();
		Match game = Match.getInstance();
		
		
		System.out.println("---------------------------------------"
				+ "-----------------------------------------");
		System.out.println("\t\t\t\tUNO CARD GAME");
		System.out.println("---------------------------------------"
				+ "-----------------------------------------");
		game.init();					// Initiate the game.
		while(sign.sign() == false);	// Registers players.
		game.start();					// Start the game.
		inter.readCommands();			// Read the commands from users.
		game.finish();					// Finishes the game.
		
	}

}
