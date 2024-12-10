package com.example.myapplication

object PROMPTS {
	const val COUNTRY = "Korea"

	const val GENERATE_USER_PERSONA =
		"[age, gender, location, occupation, relationship, kids, education, health info, wealth info]\n\nGenerate user persona of $COUNTRY based user keeping all above criteria in mind.\nThen create top 10 detailed interests of user which are particular to his persona. It should not be generic. consider all info while generating a persona. DO not add any further detail. DO not add and prefix, suffix to answer Just give the info asked"

	const val GENERATE_HISTORY =
		"Below is a user persona. generate chrome, youtube, instagram history of what your might have searched while typing on keyboard. generate 10 searches of each. do not include time log. do not divide searched based on interest. Just generate very normal flow of searched just as a real world user would do. DO not add and prefix, suffix to answer Just give the info asked:\n"

}