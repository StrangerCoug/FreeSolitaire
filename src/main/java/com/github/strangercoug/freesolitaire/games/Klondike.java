/*
 * Copyright (c) 2019, Jeffrey Hope
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
package com.github.strangercoug.freesolitaire.games;

import com.github.strangercoug.freesolitaire.Game;
import com.github.strangercoug.freesolitaire.Player;
import com.github.strangercoug.freesolitaire.enums.CardRank;
import com.github.strangercoug.freesolitaire.enums.CardSuit;
import com.github.strangercoug.freesolitaire.objs.Card;
import com.github.strangercoug.freesolitaire.objs.Deck;
import java.util.ArrayList;

/**
 *
 * @author Jeffrey Hope <strangercoug@hotmail.com>
 */
public class Klondike extends Game {
    Deck deck;
    ArrayList<Card> stock; // cards are drawn from here
    ArrayList<Card> talon; // cards drawn from the stock go here
    ArrayList<ArrayList<Card>> foundations; // cards are built up here
    ArrayList<ArrayList<Card>> tableau; // cards are initially dealt here
    byte[] firstFaceUpCard;
    
	@Override
	public void play(Player player) {
		deck = new Deck();
        foundations = new ArrayList<>(CardSuit.values().length);
        for (CardSuit value : CardSuit.values()) {
            foundations.add(new ArrayList<>(CardRank.values().length));
        }
        
        tableau = new ArrayList<>(7);
        firstFaceUpCard = new byte[7];
        
        
        for (int i = 0; i < firstFaceUpCard.length; i++) {
            firstFaceUpCard[i] = (byte) i;
            tableau.add(new ArrayList<>(CardRank.values().length + i));
        }
	}
	
	/**
	 * If the topmost card of a tableau pile is face down, flip it face up.
	 * 
	 * @param column the column with the card to flip 
	 */
	public void flipCard(byte column) {
		if (firstFaceUpCard[column] > 0 &&
				foundations.get(column).size() == firstFaceUpCard[column] + 1)
			firstFaceUpCard[column]--;
	}
	
	public void drawFromTalon() {
		
	}
	
	/**
	 * Finds whether the top {@code cardsToMove} cards in {@code srcCol} can be
	 * legally moved to {@code destCol}. It does not automatically execute the move.
	 * 
	 * If {@destCol} is empty, then the bottommost card of the pile being moved must
	 * be a king. Otherwise, the top card of {@destCol} must be one rank higher than
	 * and the opposite color of the bottommost card of the pile being moved.
	 * 
	 * If any face-down cards are involved, the method returns false.
	 * 
	 * @param srcCol the column number to move cards from
	 * @param destCol the column number to move cards to
	 * @param cardsToMove the number of cards to move
	 * @return true if move is legal; false otherwise
	 */
	public boolean canMoveCards (ArrayList<Card> srcCol, ArrayList<Card> destCol,
			byte cardsToMove) {
		return false;
	}
	
	/**
	 * Finds whether the top card of a tableau column can be played to a foundation.
	 * 
	 * @param column the column number to play from
	 * @param foundation the foundation to play to
	 * @return true if the move is legal; false otherwise
	 */
	public boolean canPlayToFoundation (ArrayList<Card> column,
			ArrayList<Card> foundation) {
		if (column.isEmpty())
			return false;
		
		Card tableauCard = column.get(column.size()-1);
		if (foundation.isEmpty())
			return tableauCard.getRank() == CardRank.ACE;
		
		Card foundationCard = foundation.get(foundation.size()-1);
		return tableauCard.getSuit() == foundationCard.getSuit()
				&& tableauCard.getRank().ordinal()
				- foundationCard.getRank().ordinal() == 1;
	}
}