/*
 * Copyright (c) 2019-2021, Jeffrey Hope
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted (subject to the limitations in the disclaimer
 * below) provided that the following conditions are met:
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
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY
 * THIS LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.strangercoug.freesolitaire.objs;

import com.github.strangercoug.freesolitaire.enums.CardRank;
import com.github.strangercoug.freesolitaire.enums.CardSuit;
import java.util.Objects;

/**
 *
 * @param rank  the card's rank
 * @param suit  the card's suit
 *
 * @author Jeffrey Hope <strangercoug@hotmail.com>
 */
public record Card(CardRank rank, CardSuit suit) implements Comparable<Card> {
	private static final String[] rankNames = {"Ace", "Two", "Three", "Four", "Five", "Six",
		"Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
	private static final String[] suitNames = {"Clubs", "Diamonds", "Hearts", "Spades"};
	
	/**
	 * Checks whether this card outranks the card in the argument. This method,
	 * unlike {@code compareTo(Card o)}, ignores suit, eliminating some of the
	 * overhead from the {@code compareTo(Card o)} method.
	 * 
	 * @param other  the card to be compared to
	 * @return true if the object card is higher in rank than the argument card,
	 * false otherwise
	 */
	public boolean outranks(Card other) {
		if (other == null)
			throw new NullPointerException();
		
		return this.rank.ordinal() > other.rank().ordinal();
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 59 * hash + Objects.hashCode(this.rank);
		hash = 59 * hash + Objects.hashCode(this.suit);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		final Card other = (Card) obj;
		
		return this.rank == other.rank() && this.suit == other.suit();
	}
	
	/**
	 * This function is intended for sorting purposes. For simply determining if
	 * one card is higher in rank than another, {@code outranks(Card other)}
	 * should be called instead as this method also takes suits into account.
	 * <p>
	 * For the purposes of this method, if two cards are of different suits,
	 * then the card of the suit listed second in {@code CardSuit} is the
	 * higher card. If two cards have the same suit, then this card is
	 * higher than the card in the argument if {@code outranks(Card other)}
	 * would return true.
	 * 
	 * @param other  the card to be compared to
	 * @return a positive number if this card is higher than the card in the
	 * argument, a negative number if this card is lower than the card in the
	 * argument, and 0 if the two cards are identical
	 */
	@Override
	public int compareTo(Card other) {
		if (other == null)
			throw new NullPointerException();
		
		return (this.suit.ordinal() * CardRank.values().length +
				this.rank.ordinal()) - (other.suit().ordinal() *
				CardRank.values().length + other.rank().ordinal());
	}
	
	@Override
	public String toString() {
		return rankNames[rank.ordinal()] + " of " + suitNames[suit.ordinal()];
	}
}
