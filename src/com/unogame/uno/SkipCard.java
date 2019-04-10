package com.unogame.uno;

import com.unogame.tablegame.EffectsController;

public class SkipCard extends EspecialCard{

	public SkipCard(NormalCard.Color color){
		super(color, EspecialCard.Value.SKIP);
	}
	
	/**
	 * Rotates the game in order to produce the result of skipping a player
	 * in the end of the turn.
	 */
	@Override
	public void applyEffect(EffectsController ctrl){
		ctrl.applySkip();
	}
}