package com.andrewq.planets;

import java.util.Random;

public class RandomQuotes {

	public String[] mQuotes = {
			"Only two things are infinite, the universe and human stupidity, and I'm not sure about the former. \n-Albert Einstein",
			"Our scientific power has outrun our spiritual power. We have guided missiles and misguided men. \n-Martin Luther King, Jr.",
			"Aerodynamically, the bumble bee shouldn't be able to fly, but the bumble bee doesn't know it so it goes on flying anyway. \n-Mary Kay Ash" };

	public String getQuotes() {

		String quote = "";

		Random randomGenerator = new Random(); // construct new random generator
		int randomNumber = randomGenerator.nextInt(mQuotes.length);
		quote = mQuotes[randomNumber];

		return quote;
	}

}