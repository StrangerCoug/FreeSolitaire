/*
 * Copyright (c) 2019-2021, Jeffrey Hope
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.strangercoug.freesolitaire;

import com.github.strangercoug.freesolitaire.games.Canfield;
import com.github.strangercoug.freesolitaire.games.Clock;
import com.github.strangercoug.freesolitaire.games.FortyThieves;
import com.github.strangercoug.freesolitaire.games.FreeCell;
import com.github.strangercoug.freesolitaire.games.Golf;
import com.github.strangercoug.freesolitaire.games.Klondike;
import com.github.strangercoug.freesolitaire.games.LaBelleLucie;
import com.github.strangercoug.freesolitaire.games.Pyramid;
import com.github.strangercoug.freesolitaire.games.Scorpion;
import com.github.strangercoug.freesolitaire.games.Spider;
import com.github.strangercoug.freesolitaire.games.TriPeaks;
import com.github.strangercoug.freesolitaire.games.Yukon;
import java.util.Scanner;

/**
 *
 * @author Jeffrey Hope
 */
public class FreeSolitaire {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		boolean validInput = false, playAgain = false;
		String entry;
		Game game = null;
		Player player;
		
		while (true) {
			do {
				System.out.println("""
						Select game to play or type "Q" or "QUIT" to  quit:
						1. Baccarat
						2. Big Six
						3. Blackjack
						4. Craps
						5. Keno
						6. Poker
						7. Red Dog
						8. Roulette
						9. Video Poker
						10. Video Poker
						11. Video Poker
						12. Video Poker""");
				entry = input.nextLine();
				if (entry.equalsIgnoreCase("q") || entry.equalsIgnoreCase("quit")) {
					input.close();
					System.exit(0);
				}
				try {
					int gameSelected = Integer.parseInt(entry);
					if (gameSelected >= 1 && gameSelected <= 12)
						validInput = true;
					game = returnGame(gameSelected);
				}
				catch (NumberFormatException e) {
					System.out.println("Invalid input.");
				}
				catch (IllegalArgumentException e) {
					System.out.println("Invalid game number.");
				}
			} while (!validInput);
			
			validInput = false;
			
			System.out.print("Enter name of player: ");
			player = new Player(input.nextLine());

			System.out.println("Good luck, " + player.getName() + "!");
			
			do {
				game.play(player);
				validInput = false;
				do {
					System.out.print("Play again? (Y/N): ");
					char selection = input.nextLine().charAt(0);
					switch (selection) {
						case 'Y', 'y' -> {
							validInput = true;
							playAgain = true;
						}
						case 'N', 'n' -> {
							validInput = true;
							playAgain = false;
						}
						default -> {
							validInput = false;
							System.out.println("Invalid selection.");
						}
					}
				} while (!validInput);
			} while (playAgain);
		}
	}
	
	private static Game returnGame(int i) {
		return switch (i) {
			case 1 -> new Canfield();
			case 2 -> new Clock();
			case 3 -> new FortyThieves();
			case 4 -> new FreeCell();
			case 5 -> new Golf();
			case 6 -> new Klondike();
			case 7 -> new LaBelleLucie();
			case 8 -> new Pyramid();
			case 9 -> new Scorpion();
			case 10 -> new Spider();
			case 11 -> new TriPeaks();
			case 12 -> new Yukon();
			default -> throw new IllegalArgumentException();
		};
	}
}
