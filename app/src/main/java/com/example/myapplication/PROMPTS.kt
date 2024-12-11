package com.example.myapplication

object PROMPTS {
	const val COUNTRY = "Korea"

	const val GENERATE_USER_PERSONA =
		"[age, gender, location, occupation, relationship, kids, education, health info, wealth info]\n\nGenerate user persona of $COUNTRY based user keeping all above criteria in mind.\nThen create top 10 detailed interests of user which are particular to his persona. It should not be generic. consider all info while generating a persona. DO not add any any additional info. DO not add and prefix, suffix to answer Just give the info asked"

	const val GENERATE_HISTORY =
		"Below is a user persona. generate chrome, youtube, instagram history of what your might have searched while typing on keyboard. generate 10 searches of each. do not include time log. do not group searches based on interest. Just generate very normal flow of searched just as a real world user would do. DO not add and prefix, suffix to answer Just give the info asked:\n"

	const val GENERATE_KEYWORDS =
		"Below is the History of user of whatever he/she has searched on respective app. Analyze it ang give top 5 topic keywords. keywords should be specific to user and should not be generic. keywords should be very detailed. Do not add any additional info. DO not add any prefix, suffix to answer. Just give the info asked:\n"

	const val GENERATE_INTERESTING_FACT =
		"Analyze the below Topic and give one short, very interesting and positive fact about this particular topic. It should be with respect to real world. The fact should be self containing. If it refers to an entity, it should contain that entity name. It should start like: \"Did you know? \" It should be 20-30 words and engaging to read. DO not add any prefix, suffix to answer. Just give the info asked:\n"

}