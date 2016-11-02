/**
 * Copyright 2015 George Belden
 * 
 * This file is part of DecipherEngine.
 * 
 * DecipherEngine is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DecipherEngine is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DecipherEngine. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.engine.fitness.cipherkey;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.engine.entities.CipherKeyGene;
import com.ciphertool.engine.fitness.FitnessEvaluatorTestBase;
import com.ciphertool.engine.fitness.cipherkey.CipherKeyWordGraphFitnessEvaluator;
import com.ciphertool.sherlock.dao.UniqueWordListDao;
import com.ciphertool.sherlock.entities.Word;

public class CipherKeyWordGraphFitnessEvaluatorTest extends FitnessEvaluatorTestBase {
	private static Logger								log			= LoggerFactory.getLogger(CipherKeyWordGraphFitnessEvaluatorTest.class);

	private static CipherKeyWordGraphFitnessEvaluator	fitnessEvaluator;

	private static CipherKeyChromosome					solution	= new CipherKeyChromosome();

	private static UniqueWordListDao					mockUniqueWordListDao;

	private static List<Word>							wordList	= new ArrayList<Word>();

	static {
		String[] wordArray = new String[] { "you", "I", "the", "to", "a", "it", "that", "and", "of", "What", "in", "me",
				"is", "we", "this", "he", "on", "for", "my", "your", "don", "have", "do", "No", "be", "know", "was",
				"not", "can", "are", "all", "with", "just", "get", "here", "but", "ll", "there", "so", "they", "right",
				"like", "out", "go", "she", "up", "about", "if", "him", "got", "Oh", "at", "now", "Come", "one", "How",
				"Well", "Yeah", "her", "want", "think", "good", "see", "Let", "did", "Why", "who", "as", "his", "will",
				"going", "from", "when", "back", "Okay", "Yes", "gonna", "time", "look", "take", "an", "man", "Where",
				"them", "would", "been", "some", "Hey", "tell", "or", "us", "had", "were", "say", "could", "didn",
				"really", "something", "down", "then", "little", "way", "our", "make", "too", "never", "by", "over",
				"more", "need", "mean", "very", "off", "Mr", "sorry", "give", "has", "Thank", "love", "said", "am",
				"people", "please", "sure", "any", "thing", "only", "because", "two", "should", "doing", "much", "sir",
				"Maybe", "help", "anything", "these", "God", "even", "night", "call", "talk", "nothing", "into",
				"first", "find", "wait", "put", "great", "thought", "day", "work", "life", "before", "better", "again",
				"still", "home", "guy", "won", "those", "than", "around", "other", "away", "new", "last", "uh", "ever",
				"stop", "keep", "told", "must", "things", "big", "after", "long", "does", "always", "their",
				"everything", "nice", "name", "money", "doesn", "guys", "feel", "believe", "Thanks", "old", "place",
				"fine", "kind", "isn", "Hello", "lot", "years", "made", "leave", "Hi", "girl", "hear", "father",
				"through", "every", "bad", "Listen", "remember", "three", "boy", "coming", "wrong", "might", "stay",
				"house", "may", "baby", "another", "Ok", "Dad", "gotta", "wanna", "wanted", "enough", "talking",
				"happened", "show", "course", "being", "care", "done", "getting", "mind", "left", "ask", "car",
				"understand", "mother", "which", "try", "shit", "hell", "Miss", "came", "wouldn", "own", "world",
				"guess", "next", "kill", "else", "dead", "trying", "someone", "real", "room", "morning", "huh", "hold",
				"ain", "woman", "yourself", "today", "looking", "wasn", "Mom", "friend", "move", "same", "job",
				"tonight", "went", "son", "best", "saw", "found", "pretty", "ready", "heard", "whole", "seen",
				"together", "fuck", "minute", "men", "head", "matter", "haven", "knew", "Excuse", "many", "idea",
				"without", "play", "family", "meet", "most", "run", "while", "wife", "once", "live", "somebody",
				"everybody", "used", "use", "myself", "took", "yet", "start", "called", "couldn", "kid", "tomorrow",
				"happy", "school", "problem", "watch", "bring", "fucking", "actually", "business", "says", "hope",
				"open", "already", "since", "looks", "sit", "Mrs", "cause", "alone", "hard", "wants", "stuff", "turn",
				"days", "friends", "until", "few", "kids", "honey", "Dr", "gone", "both", "door", "later", "saying",
				"such", "killed", "having", "face", "worry", "ago", "five", "second", "brother", "damn", "case",
				"thinking", "probably", "beautiful", "hand", "check", "year", "forget", "hit", "lost", "minutes",
				"crazy", "late", "phone", "Nobody", "end", "easy", "doctor", "Shut", "under", "part", "deal", "die",
				"soon", "four", "anyone", "pay", "happen", "true", "each", "supposed", "em", "eat", "Jack", "mine",
				"working", "town", "afraid", "drink", "exactly", "whatever", "hurt", "knows", "heart", "gave", "young",
				"everyone", "chance", "read", "makes", "number", "taking", "change", "anyway", "week", "married",
				"hands", "point", "police", "word", "fun", "wish", "bit", "aren", "game", "party", "set", "cut",
				"comes", "sleep", "shot", "anybody", "ass", "stand", "water", "boys", "trouble", "dear", "couple",
				"gets", "making", "eyes", "break", "story", "far", "times", "Um", "close", "means", "funny", "goes",
				"lady", "death", "asked", "walk", "fire", "hours", "hate", "gun", "rest", "person", "inside", "waiting",
				"different", "girls", "Captain", "least", "important", "Ah", "also", "line", "yours", "office",
				"dinner", "quite", "against", "fight", "side", "six", "half", "pick", "question", "ahead", "Michael",
				"cool", "women", "body", "high", "husband", "John", "reason", "almost", "dog", "buy", "truth", "met",
				"telling", "hot", "anymore", "behind", "started", "speak", "bed", "moment", "tried", "blood", "ma",
				"shall", "Daddy", "stupid", "along", "either", "though", "front", "sister", "Bye", "send", "welcome",
				"sometimes", "trust", "free", "book", "answer", "between", "children", "war", "Hurry", "fact",
				"brought", "bet", "clear", "its", "white", "glad", "daughter", "outside", "city", "bitch", "feeling",
				"black", "seems", "full", "till", "sick", "light", "shoot", "news", "lose", "wonderful", "months",
				"save", "hour", "country", "Jesus", "needs", "Wow", "able", "Frank", "perfect", "shouldn", "running",
				"child", "Whoa", "died", "order", "living", "sounds", "alive", "food", "gentlemen", "luck", "hair",
				"drive", "promise", "sex", "music", "ya", "power", "sort", "special", "serious", "street", "red",
				"dance", "hang", "touch", "team", "playing", "company", "George", "pull", "plan", "sweet", "ten",
				"coffee", "lucky", "sound", "safe", "date", "leaving", "parents", "President", "himself", "seem",
				"lives", "air", "taken", "York", "picture", "ladies", "Lord", "sent", "fast", "happens", "Perhaps",
				"catch", "ride", "win", "kidding", "top", "scared", "dream", "sign", "meeting", "sense", "beat",
				"control", "drop", "cold", "weeks", "darling", "figure", "king", "poor", "throw", "asking", "Joe",
				"write", "cannot", "suppose", "small", "human", "piece", "boss", "hospital", "Uncle", "past", "calling",
				"known", "follow", "Sam", "movie", "ha", "Christmas", "straight", "weren", "words", "clean", "kiss",
				"looked", "feet", "evening", "million", "lie", "felt", "moving", "certainly", "step", "learn", "fall",
				"Bill", "questions", "finally", "takes", "class", "quiet", "wonder", "Goodbye", "law", "become",
				"General", "worked", "rather", "possible", "goddamn", "unless", "mad", "absolutely", "tired", "murder",
				"road", "Mike", "eye", "except", "somewhere", "explain", "Charlie", "less", "none", "loved", "giving",
				"seeing", "Tom", "secret", "wear", "worth", "act", "careful", "quick", "handle", "pass", "early",
				"report", "state", "busy", "turned", "table", "wake", "works", "broke", "ball", "Major", "seven",
				"mouth", "marry", "meant", "fault", "lunch", "Al", "Lieutenant", "expect", "Hmm", "Mama", "future",
				"paper", "officer", "hotel", "buddy", "Agent", "thinks", "talked", "blue", "American", "mistake", "Tv",
				"David", "ones", "wedding", "clothes", "weird", "changed", "court", "floor", "watching", "building",
				"earth", "dude", "others", "longer", "finish", "forgot", "ship", "club", "attention", "eight", "worse",
				"pain", "Ben", "th", "sing", "blow", "choice", "ls", "Ray", "birthday", "stick", "relax", "yesterday",
				"honor", "Colonel", "smart", "boat", "plane", "month", "lovely", "given", "train", "fair", "worried",
				"Ooh", "needed", "sitting", "security", "cover", "across", "Paul", "bag", "terrible", "caught", "song",
				"spend", "horse", "ring", "sell", "return", "personal", "message", "system", "afternoon", "Bob", "hasn",
				"happening", "tough", "Christ", "Peter", "quit", "count", "box", "missed", "present", "charge", "kept",
				"information", "fool", "simple", "middle", "calm", "surprise", "forever", "decided", "dark", "anywhere",
				"miles", "swear", "land", "Mary", "missing", "cute", "lying", "master", "dress", "key", "strong", "fix",
				"interesting", "wearing", "Johnny", "strange", "rock", "voice", "cop", "window", "bar", "totally",
				"interested", "appreciate", "army", "paid", "short", "record", "bought", "card", "certain", "college",
				"evidence", "fly", "bank", "Besides", "completely", "ran", "cops", "test", "history", "finished",
				"born", "proud", "fish", "join", "lead", "smell", "near", "apartment", "enjoy", "letter", "situation",
				"trip", "Harry", "Mark", "store", "Yo", "amazing", "star", "Danny", "accident", "Il", "imagine", "Doc",
				"ought", "pleasure", "list", "rich", "calls", "Jimmy", "service", "entire", "difference", "judge",
				"ice", "lawyer", "fat", "Alright", "instead", "age", "station", "realize", "gold", "seat", "liked",
				"hundred", "summer", "dollars", "standing", "Angel", "mess", "America", "Chief", "killing", "radio",
				"hungry", "problems", "marriage", "brain", "soul", "forgive", "drunk", "Henry", "deep", "figured",
				"likes", "girlfriend", "folks", "slow", "private", "during", "Ed", "attack", "beer", "definitely",
				"stopped", "partner", "walking", "area", "dangerous", "offer", "scene", "third", "upset", "bus", "owe",
				"shoes", "driving", "English", "Richard", "group", "ln", "kick", "evil", "Joey", "joke", "fell",
				"truck", "teach", "green", "ground", "loves", "cash", "forward", "honest", "boyfriend", "park",
				"position", "single", "respect", "broken", "crime", "wrote", "public", "Max", "Mommy",
				"Congratulations", "grab", "art", "fighting", "favor", "upstairs", "wall", "force", "seconds", "jail",
				"push", "prove", "normal", "machine", "protect", "field", "spent", "feels", "speaking", "named", "jump",
				"saved", "starting", "nose", "hide", "church", "sun", "peace", "Professor", "Bobby", "share", "French",
				"Steve", "bullshit", "moved", "picked", "thousand", "Paris", "holding", "Billy", "fear", "using", "la",
				"tape", "Tony", "suit", "pictures", "Holy", "putting", "involved", "gas", "books", "relationship",
				"neither", "nine", "Pop", "rules", "bother", "especially", "nervous", "whether", "dying", "stuck",
				"round", "dirty", "cat", "breakfast", "idiot", "space", "lived", "prison", "carry", "James", "bastard",
				"cry", "smoke", "arm", "film", "government", "tree", "foot", "contact", "knock", "agree", "pardon",
				"gift", "gives", "South", "dreams", "Jim", "hat", "board", "sake", "sweetheart", "seriously", "North",
				"department", "patient", "awful", "sad", "wondering", "roll", "Robert", "beginning", "grand", "usually",
				"Sergeant", "killer", "laugh", "doubt", "listening", "upon", "double", "twice", "whose", "outta",
				"plenty", "guilty", "Jerry", "promised", "fired", "race", "crap", "chicken", "bathroom", "asshole",
				"spot", "reading", "orders", "weekend", "Detective", "action", "Sheriff", "eating", "glass", "type",
				"guns", "experience", "West", "obviously", "wine", "Luke", "press", "difficult", "lots", "Brown",
				"Nick", "rid", "sea", "arms", "flight", "staying", "arrest", "neck", "grow", "mention", "favorite",
				"wind", "sleeping", "notice", "admit", "extra", "within", "low", "impossible", "gay", "computer",
				"angry", "bunch", "blame", "pants", "visit", "clock", "tea", "fellow", "kitchen", "lay", "hole",
				"guard", "learned", "smile", "feelings", "fit", "pal", "bear", "often", "wild", "silly", "camera",
				"begin", "Ow", "reach", "beach", "Larry", "heaven", "lock", "leg", "quickly", "lights", "Kelly",
				"worst", "shooting", "played", "plans", "bucks", "suddenly", "writing", "track", "teacher",
				"ridiculous", "legs", "river", "dare", "burn", "Aunt", "raise", "Shh", "Rory", "decision", "surprised",
				"cross", "cost", "queen", "fresh", "innocent", "emergency", "dancing", "medical", "cell", "gotten",
				"seemed", "bigger", "States", "closed", "names", "walked", "bomb", "hanging", "note", "shop", "sweetie",
				"nuts", "band", "losing", "price", "steal", "waste", "client", "stole", "code", "crying", "pressure",
				"places", "dogs", "Rose", "dick", "accept", "further", "Aah", "excellent", "magic", "drinking", "keeps",
				"corner", "consider", "ourselves", "acting", "herself", "locked", "laughing", "address", "copy",
				"tells", "warm", "sold", "pregnant", "hall", "treat", "everywhere", "Van", "papers", "complete", "cup",
				"level", "ways", "passed", "witness", "eh", "taste", "motherfucker", "hardly", "camp", "keeping",
				"Charles", "keys", "beg", "Yep", "duty", "Ms", "interest", "tight", "bottle", "helping", "support",
				"Leo", "flying", "decide", "United", "St", "turns", "moon", "bottom", "hoping", "conversation", "San",
				"hero", "asleep", "Roger", "final", "continue", "East", "match", "apologize", "trial", "spirit",
				"willing", "chair", "risk", "study", "Amy", "possibly", "rain", "above", "cousin", "pulled", "cream",
				"dropped", "excited", "memory", "breathe", "enemy", "huge", "search", "drugs", "greatest", "beauty",
				"lately", "Tommy", "rule", "build", "choose", "cards", "Lee", "advice", "immediately", "teeth",
				"became", "victim", "coach", "Friday", "flowers", "showed", "crew", "Saturday", "driver", "Apparently",
				"heavy", "trick", "empty", "comfortable", "destroy", "brothers", "mission", "Plus", "apart", "pool",
				"dumb", "dressed", "helped", "knife", "checked", "Santa", "weapon", "restaurant", "shirt", "faith",
				"Gee", "simply", "dig", "size", "stars", "London", "movies", "necessary", "themselves", "blind",
				"credit", "center", "starts", "bridge", "practice", "closer", "discuss", "cars", "mister", "Grandma",
				"cook", "stage", "strike", "ticket", "animal", "bird", "leaves", "sight", "following", "somehow",
				"knowing", "drug", "career", "nature", "However", "Prince", "cake", "responsible", "famous", "nurse",
				"correct", "breath", "fucked", "games", "allowed", "sky", "bringing", "hearing", "singing", "account",
				"common", "due", "afford", "Chinese", "tie", "bright", "allow", "belong", "concerned", "escape",
				"suspect", "written", "file", "skin", "Jake", "madam", "fill", "operation", "desk", "Aye", "taught",
				"pack", "lied", "faster", "deserve", "Ted", "danger", "meat", "command", "stories", "tickets", "hiding",
				"paying", "perfectly", "Sunday", "whoever", "beyond", "Sarah", "Dave", "student", "Jane", "dry", "jury",
				"form", "main", "heads", "program", "Papa", "Martin", "Fred", "milk", "held", "horrible", "kinda",
				"feed", "de", "natural", "Grace", "battle", "breaking", "Phoebe", "ugly", "coat", "Although", "settle",
				"opinion", "Washington", "ho", "terrific", "According", "gentleman", "older", "Lucy", "loose",
				"noticed", "local", "lonely", "shame", "Otherwise", "shows", "large", "devil", "video", "speed",
				"military", "Chicago", "built", "shower", "oil", "opportunity", "chest", "horses", "biggest", "threw",
				"bite", "Eric", "Aw", "wash", "stone", "block", "records", "indeed", "weapons", "invited", "draw",
				"turning", "attorney", "pretend", "health", "Vegas", "balls", "heat", "manager", "guest", "loud",
				"fantastic", "itself", "cares", "shake", "lab", "numbers", "princess", "island", "easier", "color",
				"earlier", "bell", "naked", "suggest", "wet", "pig", "letting", "nowhere", "Merry", "animals", "Weli",
				"cheese", "ideas", "downstairs", "soldier", "monster", "several", "planet", "Dean", "fellas", "insane",
				"California", "Walter", "eggs", "spoke", "butt", "murdered", "view", "bloody", "lines", "opening",
				"insurance", "Pete", "split", "jealous", "bullet", "arrived", "character", "national", "screaming",
				"airport", "speech", "hook", "condition", "target", "finding", "serve", "Er", "incredible", "player",
				"signal", "sugar", "Helen", "total", "selling", "hill", "football", "page", "screw", "justice",
				"letters", "hurts", "project", "rough", "crowd", "meaning", "planning", "pair", "science", "sees",
				"usual", "Adam", "Emily", "sooner", "Commander", "ordered", "subject", "lies", "remind", "strength",
				"mail", "Dan", "paint", "freak", "bedroom", "neighborhood", "onto", "finger", "personally", "spell",
				"Tim", "ghost", "Majesty", "Peg", "Smith", "doctors", "fake", "release", "weight", "cheap", "market",
				"pray", "expecting", "unit", "signed", "falling", "throat", "lake", "nor", "Susan", "director",
				"realized", "agreed", "Phil", "truly", "brilliant", "cab", "powers", "candy", "Junior", "prepared",
				"legal", "pocket", "Scott", "aware", "roof", "Jason", "babe", "Brian", "Radar", "slept", "Bud",
				"responsibility", "mountain", "base", "ours", "firm", "England", "trade", "whom", "romantic", "fan",
				"liar", "training", "brings", "powerful", "language", "sending", "whenever", "purpose", "Whoo",
				"believed", "bless", "Nope", "pieces", "arrested", "noise", "suck", "fancy", "exciting", "genius",
				"forgotten", "introduce", "Annie", "rent", "familiar", "criminal", "doors", "proof", "vote", "com",
				"recognize", "stolen", "suicide", "weather", "drinks", "medicine", "issue", "lift", "followed", "Anna",
				"buried", "mood", "male", "among", "television", "nights", "regular", "opened", "someday", "stomach",
				"yellow", "ate", "County", "Buck", "nearly", "Crane", "scare", "village", "prepare", "matters",
				"monkey", "pizza", "assume", "heading", "sudden", "toast", "ears", "fella", "babies", "jacket", "Lane",
				"social", "thoughts", "travel", "sometime", "Monday", "property", "expected", "fingers", "bodies",
				"remain", "secretary", "funeral", "magazine", "sexual", "Senator", "jerk", "dating", "glasses",
				"freedom", "research", "Arthur", "add", "damage", "handsome", "repeat", "hired", "buying", "prefer",
				"society", "energy", "Alan", "crack", "chase", "vacation", "Mulder", "Carter", "divorce", "stayed",
				"Ally", "Jackson", "defense", "rat", "Grandpa", "Grant", "began", "picking", "checking", "goodness",
				"reasons", "post", "confused", "William", "Unfortunately", "surgery", "telephone", "contract", "safety",
				"tall", "fixed", "professional", "lesson", "assistant", "tiny", "points", "Freeze", "understood",
				"runs", "Thomas", "license", "model", "gate", "Margaret", "soft", "ear", "riding", "staff", "warning",
				"engine", "German", "planned", "map", "swim", "harm", "square", "silver", "Sydney", "brave", "access",
				"positive", "covered", "female", "someplace", "streets", "blew", "weak", "season", "Matt", "rush",
				"awesome", "snow", "spring", "champagne", "spread", "Bond", "mayor", "pounds", "demon", "winner",
				"Madame", "lips", "leader", "tongue", "bath", "permission", "showing", "Mm", "Monsieur", "Mexico",
				"Jones", "storm", "destroyed", "spare", "tour", "headed", "trees", "students", "ends", "bones",
				"burning", "appointment", "kicked", "mentioned", "Piper", "Claire", "score", "Angeles", "shoe",
				"Johnson", "ocean", "harder", "reality", "shape", "Brad", "Jeff", "Kim", "gang", "survive", "saving",
				"cos", "style", "farm", "shopping", "clearly", "sexy", "answers", "example", "growing", "laid", "gosh",
				"rings", "alarm", "plays", "screwed", "fortune", "schedule", "bleeding", "enter", "punch", "ended",
				"patients", "rights", "invite", "obvious", "charges", "interview", "touched", "affair", "parts",
				"Russian", "unbelievable", "wherever", "chocolate", "focus", "Sue", "borrow", "grade", "grew", "finds",
				"Cole", "investigation", "mate", "statement", "load", "painting", "throwing", "community", "Ross",
				"loss", "waited", "barely", "woods", "changes", "details", "yourselves", "exist", "toilet", "chances",
				"drove", "meal", "dump", "disappeared", "member", "shock", "discovered", "failed", "pie", "Carol",
				"crash", "Martha", "artist", "sat", "theory", "depends", "bags", "joy", "pleased", "ruin", "kissed",
				"traffic", "nonsense", "pink", "wise", "carrying", "burned", "Laura", "midnight", "shots", "deliver",
				"bread", "officers", "button", "dealing", "Mac", "original", "hated", "Eve", "source", "cases", "hung",
				"received", "Subtitles", "charming", "switch", "decent", "Nah", "below", "Texas", "desert", "Hollywood",
				"process", "expensive", "belongs", "particular", "higher", "moves", "breathing", "grandmother", "lower",
				"period", "pride", "dollar", "thousands", "witch", "soldiers", "tip", "jobs", "plant", "sports",
				"surely", "bust", "birth", "including", "joint", "Logan", "bull", "wire", "brains", "rise", "towards",
				"boring", "Karen", "ashamed", "Lt", "sisters", "section", "facts", "Carl", "clever", "smells",
				"honestly", "success", "garage", "connection", "filled", "physical", "complicated", "pulling", "regret",
				"closet", "loser", "France", "giant", "wheel", "parking", "policy", "Twenty", "stranger", "tear",
				"wood", "fate", "Maggie", "juice", "Lily", "governor", "Europe", "Knight", "tied", "faces", "awake",
				"fought", "Kitty", "coast", "pilot", "miracle", "aboard", "files", "lover", "based", "cigarette",
				"disgusting", "grateful", "mighty", "murderer", "garden", "watched", "wound", "Alice", "Sally", "Linda",
				"drag", "forced", "fourth", "Marie", "scream", "event", "woke", "actor", "row", "grave", "changing",
				"senior", "curious", "flat", "winter", "badly", "priest", "Rick", "scary", "shoulder", "super",
				"disease", "sword", "chick", "smoking", "closing", "offered", "concern", "talent", "garbage",
				"attitude", "mostly", "bone", "egg", "friendly", "recently", "basically", "engaged", "quarter", "thee",
				"rooms", "Amen", "passing", "swing", "available", "Louis", "Marshall", "bike", "birds", "knees", "slip",
				"hunt", "caused", "taxi", "stood", "likely", "object", "hates", "percent", "Pierce", "Japanese",
				"raised", "guests", "desperate", "dirt", "Navy", "pussy", "negative", "plate", "cooking", "data",
				"distance", "tank", "request", "golf", "hire", "knowledge", "ruined", "cow", "dawn", "falls", "pissed",
				"stock", "equipment", "Ann", "conference", "reports", "rescue", "claim", "Holmes", "sale", "audience",
				"silence", "Americans", "warn", "Hank", "mercy", "Jesse", "create", "Francisco", "proper", "universe",
				"baseball", "Harold", "Hercules", "soup", "British", "outfit", "slowly", "yard", "Drew", "Duke",
				"Jackie", "grown", "loving", "Valley", "Robin", "pure", "rate", "bro", "dies", "celebrate", "China",
				"piano", "Simon", "pills", "uniform", "stealing", "spending", "doll", "duck", "location", "returned",
				"amount", "Central", "healthy", "knocked", "pen", "reached", "walls", "steps", "younger", "attractive",
				"notes", "fail", "beast", "path", "poison", "wanting", "naturally", "happiness", "anytime", "Gary",
				"sucks", "Betty", "eventually", "channel", "elevator", "thy", "belt", "grandfather", "secure", "avoid",
				"penny", "laughs", "thief", "guards", "Bay", "bride", "pathetic", "mirror", "partners", "Thursday",
				"becomes", "dozen", "Ellen", "Kyle", "direction", "gorgeous", "direct", "odd", "theater", "Boston",
				"committed", "led", "march", "members", "Morgan", "official", "puts", "attacked", "effect", "tail",
				"treated", "Pa", "vision", "secrets", "dust", "talks", "trap", "wide", "honour", "sharp", "aside",
				"deck", "stairs", "guts", "extremely", "lousy", "unusual", "newspaper", "apple", "courage", "Tuesday",
				"terribly", "fishing", "piss", "University", "carefully", "hitting", "pulse", "writer", "edge",
				"illegal", "pity", "couch", "protection", "tests", "staring", "victims", "created", "screen", "appear",
				"winning", "precious", "studio", "windows", "kissing", "rob", "golden", "Wilson", "frightened", "Sandy",
				"owner", "royal", "Da", "intend", "considered", "parties", "Burns", "cast", "prisoner", "Frasier",
				"popular", "destiny", "robbery", "federal", "silent", "violence", "hearts", "mystery", "nerve",
				"circumstances", "library", "toward", "busted", "becoming", "rocks", "embarrassing", "Miller", "photo",
				"practically", "tower", "armed", "friendship", "maid", "shift", "wallet", "package", "flower", "range",
				"beating", "Elizabeth", "Cheers", "results", "rope", "steady", "cleaning", "Barbara", "exact", "image",
				"Maria", "turkey", "vehicle", "easily", "jungle", "nasty", "pot", "sensitive", "suffer", "millions",
				"remembered", "trash", "thou", "ambulance", "behavior", "nightmare", "prize", "per", "snake", "tears",
				"cancer", "families", "orange", "terms", "foreign", "media", "Donna", "wasting", "memories", "songs",
				"Spanish", "material", "Charlotte", "expert", "cutting", "advantage", "flesh", "rude", "disappointed",
				"Inspector", "committee", "guarantee", "signs", "Terry", "kinds", "punk", "downtown", "sandwich",
				"marks", "understanding", "Daniel", "mistakes", "political", "sweat", "cents", "panic", "performance",
				"plain", "boom", "stops", "Parker", "union", "seats", "cable", "fruit", "hundreds", "Mum", "Objection",
				"separate", "Kong", "ancient", "underwear", "Fox", "Lewis", "moments", "Cliff", "castle", "rolling",
				"setting", "delicious", "circle", "value", "bills", "Chuck", "glory", "miserable", "squad", "counting",
				"manage", "bowl", "victory", "zero", "embarrassed", "stands", "Willie", "creature", "basketball",
				"deny", "mixed", "continues", "route", "Bruce", "Nancy", "rare", "yelling", "holiday", "Andrew",
				"hidden", "ill", "directly", "helps", "progress", "remove", "wave", "gods", "authority", "chain",
				"emotional", "highly", "hunting", "wore", "shadow", "FALSE", "jumped", "gray", "estate", "skip",
				"whore", "horn", "agents", "appears", "basement", "Jess", "minds", "pleasant", "clients", "mile",
				"approach", "refuse", "disappear", "bug", "district", "rabbit", "speaks", "champion", "Jeez",
				"competition", "proceed", "stopping", "Watson", "Anne", "forces", "leading", "presence", "century",
				"cure", "Rita", "capable", "convinced", "swell", "warrant", "Wayne", "threat", "therefore", "Zack",
				"bury", "Ruth", "April", "diamond", "services", "shine", "bat", "Monica", "alert", "chip", "Edward",
				"Jenny", "transfer", "sentence", "thrown", "fabulous", "nation", "pushed", "butter", "Earl", "jokes",
				"reporter", "booth", "casino", "Josh", "Potter", "successful", "learning", "awfully", "possibility",
				"sand", "bow", "cage", "desire", "nigger", "wolf", "units", "wing", "exchange", "trapped", "bored",
				"pet", "thin", "drama", "rip", "series", "hills", "homework", "carried", "entirely", "zone",
				"explanation", "spy", "assure", "failure", "collect", "hits", "bang", "Joseph", "swimming", "launch",
				"print", "delivery", "fever", "Jordan", "journey", "useless", "photos", "kills", "sport", "Barry",
				"challenge", "loan", "Shore", "routine", "soda", "spoken", "Clark", "mask", "teaching", "Teli", "trunk",
				"leads", "passion", "purse", "result", "argue", "climb", "served", "Seth", "cats", "beef", "witnesses",
				"Barney", "recall", "wings", "cabin", "mental", "ships", "script", "article", "solid", "Vic",
				"education", "salt", "solve", "confidence", "frankly", "metal", "receive", "wounded", "agency", "anger",
				"escaped", "settled", "suffering", "detail", "pipe", "trace", "supper", "wins", "effort", "spit",
				"studying", "hug", "enemies", "treatment", "commit", "Dragon", "intelligence", "reputation", "troops",
				"custody", "Gimme", "ability", "site", "fifth", "palace", "trail", "pushing", "boots", "hop", "stays",
				"owns", "attempt", "houses", "lawyers", "mouse", "bout", "ease", "hurting", "stronger", "Chloe",
				"considering", "ordinary", "presents", "customers", "impressed", "laundry", "ripped", "treasure",
				"revenge", "Bravo", "odds", "tricks", "cowboy", "nuclear", "Rome", "motion", "Kirk", "mall", "virus",
				"forest", "Reverend", "June", "Noel", "sounded", "trained", "El", "scratch", "virgin", "breaks",
				"fifty", "potential", "Twelve", "defend", "contest", "Africa", "fashion", "plastic", "cap", "Mickey",
				"convince", "interrupt", "latest", "Lincoln", "issues", "Joan", "arrive", "cheer", "Catherine", "chose",
				"supply", "surveillance", "ignore", "mountains", "nail", "league", "vice", "Albert", "figures",
				"joking", "Stanley", "Thanksgiving", "cheating", "coincidence", "Coke", "favour", "loaded", "messages",
				"quality", "title", "Bunny", "division", "impression", "particularly", "reasonable", "Vincent", "tiger",
				"se", "therapy", "bastards", "museum", "minister", "steel", "bound", "slave", "standard", "wishes",
				"anniversary", "dreaming", "yell", "Florida", "firing", "reminds", "shy", "cruel", "Hunter", "walks",
				"Bible", "seek", "cancel", "chasing", "Las", "Pat", "prime", "former", "smooth", "socks", "Wednesday",
				"dates", "Judy", "modern", "surface", "curse", "lifetime", "role", "chosen", "eaten", "gym", "motel",
				"Christopher", "collection", "device", "enjoyed", "Russell", "heck", "pee", "Hong", "noon", "Jersey",
				"Previously", "blowing", "sons", "reward", "degrees", "Georgia", "lets", "bothering", "bars", "cameras",
				"dumped", "iron", "Mitch", "Devon", "express", "Saint", "cookies", "Janet", "sacrifice", "assignment",
				"tunnel", "highway", "guide", "insist", "slide", "Victor", "Oscar", "specific", "wrap", "cleaned",
				"prom", "wagon", "cigarettes", "lack", "defendant", "exercise", "Jean", "packed", "bullets", "cheat",
				"kit", "Marshal", "background", "ringing", "clue", "damned", "assault", "Walt", "concert", "Dorothy",
				"Fifteen", "Ld", "ranch", "suits", "temple", "designed", "planes", "vampire", "foolish", "agreement",
				"Daphne", "darkness", "flag", "tent", "rotten", "Pacey", "remains", "term", "alien", "patch", "provide",
				"touching", "Cooper", "snap", "Davis", "bail", "believes", "imagination", "actual", "incident",
				"liquor", "Molly", "released", "Sonny", "connected", "disaster", "fully", "mass", "comfort", "sec",
				"smiling", "border", "Francis", "fuel", "Thirty", "legend", "players", "crossed", "electric", "demand",
				"Donald", "opera", "circus", "current", "diamonds", "trauma", "Turtle", "Bundy", "enjoying", "laws",
				"neighbors", "prints", "salad", "argument", "Peggy", "describe", "Harper", "impressive", "starving",
				"neighbor", "nigga", "council", "fallen", "sink", "backup", "screams", "Avenue", "sneak", "trigger",
				"wipe", "events", "Norman", "tone", "Meg", "toy", "youth", "crush", "factory", "Felicity", "campaign",
				"grass", "instance", "Jen", "searching", "trusted", "Williams", "Bishop", "Dennis", "goal", "Ken",
				"tracks", "wasted", "messed", "asks", "cookie", "generous", "fairy", "violent", "average", "Benny",
				"crisis", "Harvey", "humor", "liberty", "suite", "opens", "rats", "slipped", "systems", "stake",
				"gambling", "managed", "nephew", "Admiral", "alcohol", "politics", "threatened", "begins", "gentle",
				"occasion", "Shirley", "network", "unhappy", "cleared", "charity", "confession", "explosion", "joined",
				"finest", "offense", "Wade", "headquarters", "judgment", "shout", "filthy", "surgeon", "tube", "hon",
				"kidnapped", "math", "operator", "personnel", "pin", "mix", "sucker", "alley", "dancer", "dealer",
				"humans", "chat", "depressed", "hoped", "reaction", "commercial", "mon", "underneath", "behave",
				"chill", "chips", "fantasy", "gloves", "steak", "version", "sides", "worrying", "design", "dropping",
				"experiment", "honeymoon", "struck", "blast", "identify", "arranged", "classic", "quarters", "Buster",
				"crystal", "delivered", "procedure", "spirits", "Goodnight", "whiskey", "divorced", "Jay", "perform",
				"prisoners", "response", "dope", "fence", "greater", "poker", "landing", "normally", "powder",
				"actress", "drawing", "protecting", "Vietnam", "gear", "rape", "advance", "locker", "suspicious",
				"Buzz", "Beth", "civil", "sin", "classes", "ending", "Le", "marrying", "Meanwhile", "Miami", "torture",
				"banks", "blown", "Christian", "personality", "selfish", "Teddy", "dough", "fed", "Lf", "confess",
				"deserves", "Walker", "warned", "ceremony", "clown", "Highness", "Rocky", "solution", "tries", "Connie",
				"helicopter", "costs", "prayer", "apology", "dressing", "forth", "invented", "Corporal", "hers", "Hm",
				"accepted", "Emma", "boxes", "entrance", "singer", "strip", "gather", "Pearl", "concentrate", "deputy",
				"instructions", "satellite", "uncomfortable", "daily", "nut", "stress", "tune", "guitar", "kicking",
				"merely", "Porter", "pretending", "sauce", "sighs", "valuable", "basic", "belly", "charm", "exit",
				"blows", "net", "Steven", "voices", "patrol", "pitch", "romance", "arrange", "Japan", "satisfied",
				"makeup", "Matthew", "teams", "surrender", "rub", "strangers", "whistle", "bum", "Fort", "kingdom",
				"visiting", "wives", "hip", "Anthony", "flew", "hood", "Ling", "diet", "dreamed", "junk", "patience",
				"earn", "flash", "lion", "Ralph", "frozen", "homicide", "robbed", "decisions", "gross", "holes",
				"badge", "Mel", "uses", "financial", "offering", "answered", "customer", "officially", "opposite",
				"soap", "beside", "privacy", "unknown", "anyhow", "painful", "represent", "lessons", "champ",
				"extraordinary", "pour", "reported", "testing", "hopes", "twins", "fascinating", "furniture",
				"meantime", "rice", "squeeze", "bend", "Valentine", "beats", "begging", "host", "blocks", "mysterious",
				"sore", "balance", "shark", "timing", "technology", "angle", "Dutch", "sets", "guilt", "wondered",
				"yards", "checks", "degree", "invitation", "knocking", "aim", "urgent", "movement", "Morris", "mud",
				"Chan", "Heh", "review", "freaking", "influence", "moron", "souls", "Warren", "Oliver", "product",
				"Scotch", "seal", "tap", "testimony", "Ace", "broad", "Anderson", "Chandler", "films", "skull",
				"status", "escort", "knee", "recording", "stretch", "territory", "Aaron", "affairs", "entered",
				"listened", "murders", "spin", "favourite", "angels", "noble", "relief", "sample", "shouting", "Gene",
				"hostage", "rifle", "skills", "Bo", "chicks", "tax", "deaf", "port", "sticking", "Foundation",
				"literally", "Patrick", "technically", "foul", "habit", "Harris", "pattern", "Ugh", "charged",
				"occurred", "removed", "beans", "confirm", "deeply", "lad", "option", "coward", "Yale", "benefit",
				"brief", "gifts", "awkward", "adult", "liver", "bombs", "drives", "flip", "holds", "jumping", "Diane",
				"ward", "counsel", "corn", "debt", "gal", "international", "testify", "traveling", "cared", "childhood",
				"cotton", "facility", "roses", "shown", "tragedy", "admire", "brush", "homes", "pro", "self", "towel",
				"Wally", "costume", "jet", "lightning", "Murphy", "bush", "prick", "anxious", "frame", "Harvard",
				"headache", "respond", "ghosts", "Marine", "Virginia", "washed", "flies", "Louise", "Palmer",
				"supplies", "wounds", "answering", "attend", "dessert", "options", "pays", "sacred", "afterwards",
				"clinic", "recommend", "rubber", "schools", "cave", "remote", "skinny", "thick", "brand", "Elaine",
				"culture", "Dallas", "fans", "Jonathan", "polite", "blonde", "claims", "painted", "theme", "aid",
				"conscience", "counter", "darn", "Mason", "Tyler", "oxygen", "pound", "tag", "Claus", "Griffin", "Jo",
				"machines", "religion", "religious", "combat", "construction", "audition", "Sherry", "Transcript",
				"blessed", "laughter", "Sammy", "sweater", "commission", "Travis", "dame", "Jewish", "shave",
				"temperature", "drill", "flow", "principal", "sail", "gain", "Germany", "Baker", "Brooklyn", "crown",
				"packing", "granted", "tradition", "wreck", "Bonnie", "Lawrence", "poem", "species", "unique", "zoo",
				"bugs", "pages", "similar", "tennis", "ad", "chop", "muscle", "sticks", "barn", "cherry", "leather",
				"Manny", "tooth", "counts", "cheering", "emperor", "jam", "Tokyo", "Ambassador", "confirmed", "Daisy",
				"intelligent", "moral", "Baron", "lap", "blah", "steam", "adventure", "Berlin", "punishment", "sheep",
				"exam", "gates", "penis", "demons", "gum", "Sarge", "suitcase", "bottles", "equal", "operate", "poetry",
				"struggle", "fools", "replace", "citizen", "freezing", "grabbed", "possession", "smaller", "thunder",
				"Western", "abandoned", "halfway", "pigs", "session", "Stephen", "wheels", "wicked", "heavens",
				"interests", "surrounded", "Burt", "digging", "fellows", "paradise", "motive", "palm", "cattle", "hut",
				"magnificent", "pit", "shell", "shove", "toys", "waves", "stable", "stink", "waiter", "basket",
				"nurses", "pile", "tracking", "bells", "determined", "fucker", "marvelous", "motor", "musical", "sire",
				"whip", "missile", "parent", "complex", "hooked", "monitor", "recent", "useful", "incredibly",
				"organization", "backwards", "Jill", "stroke", "accused", "engagement", "prevent", "chuckles", "goose",
				"Halloween", "messing", "rap", "temporary", "Howdy", "limit", "phones", "tapes", "appeal", "blade",
				"despite", "drawer", "propose", "superior", "Bernard", "blanket", "cried", "eleven", "identity",
				"maintain", "cigar", "delighted", "inches", "mob", "refused", "survived", "matches", "sack", "clerk",
				"properly", "activity", "appropriate", "award", "Gloria", "lamp", "parade", "profile", "resist",
				"Rusty", "scout", "shaking", "drank", "stepped", "suffered", "exhausted", "heroes", "Kenny", "comment",
				"dive", "fits", "hatch", "minor", "mistaken", "approaching", "landed", "authorities", "environment",
				"fighter", "handled", "Hiya", "medication", "beloved", "expression", "manners", "Stuart", "wears",
				"chamber", "Hawk", "effects", "Sookie", "swallow", "task", "capital", "catching", "sales", "chin",
				"conditions", "et", "lobby", "toe", "tub", "earned", "Edgar", "empire", "highest", "string",
				"description", "developed", "eats", "nap", "production", "pump", "stations", "actors", "con", "deeper",
				"inform", "beard", "passes", "related", "twist", "bid", "pole", "typical", "realise", "remarkable",
				"ye", "Bingo", "bitches", "complain", "creatures", "entertainment", "ex", "soccer", "insult", "Philip",
				"apply", "plates", "toes", "Abby", "happier", "Korea", "Seattle", "acts", "Catholic", "choices",
				"Colin", "Germans", "hammer", "producer", "sheets", "slap", "Troy", "neat", "rehearsal", "suspects",
				"breasts", "covering", "Leonard", "railroad", "rear", "Academy", "battery", "editor", "informed",
				"diner", "sailor", "Tha", "toss", "Broadway", "inch", "tire", "bump", "invisible", "lawn", "peanut",
				"Russia", "convention", "Dana", "fond", "phase", "purple", "quietly", "revolution", "excitement",
				"goods", "Hitler", "item", "Manhattan", "thus", "cheated", "episode", "forgetting", "pan", "passengers",
				"stinks", "thirsty", "tits", "coma", "cruise", "fooling", "pockets", "tend", "critical", "development",
				"Diego", "Spike", "Commissioner", "Mexican", "tragic", "attached", "graduate", "handy", "placed",
				"sharing", "Superman", "advise", "dismissed", "mothers", "signature", "suggestion", "accent", "Hollow",
				"labor", "robot", "scientist", "actions", "behalf", "entry", "buddies", "discussion", "generation",
				"helpful", "permanent", "servant", "assigned", "brass", "captured", "kidnapping", "permit", "terrorist",
				"widow", "dull", "July", "upper", "dime", "motherfuckers", "psychiatrist", "retired", "crawl",
				"discover", "fights", "joining", "Kansas", "vault", "Foster", "Hail", "lemon", "Magnum", "underground",
				"bargain", "Clay", "criminals", "Ewing", "loyal", "tale", "beaten", "combination", "Luther",
				"precisely", "tastes", "cells", "citizens", "employees", "Hawaii", "log", "tattoo", "Allen", "basis",
				"fields", "link", "relationships", "jazz", "needle", "Pam", "Buffalo", "idiots", "Marco", "Ruby",
				"sakes", "chef", "mistress", "racing", "attracted", "bacon", "Chairman", "dishes", "psychic", "Slim",
				"treating", "chapter", "farmer", "rocket", "sunshine", "unfortunate", "frog", "pill", "thumb", "comedy",
				"intended", "terrorists", "Hart", "operations", "shoulders", "weed", "wrapped", "beings", "boats",
				"cloud", "feeding", "owned", "tables", "threatening", "transferred", "Cohen", "dated", "dining",
				"explained", "hooker", "Kennedy", "policeman", "pops", "shocked", "undercover", "Hopefully", "Mount",
				"Boo", "chopper", "engineer", "existence", "fries", "hid", "individual", "industry", "picnic", "reckon",
				"serving", "Debbie", "Jacob", "lend", "loyalty", "shelter", "splendid", "workers", "carpet", "divine",
				"management", "Yup", "beneath", "introduced", "raped", "spoil", "Synchro", "troubles", "porn", "sheet",
				"Southern", "appearance", "ham", "lazy", "plot", "betrayed", "crimes", "employee", "medal", "returning",
				"dragged", "reporting", "entering", "hockey", "manner", "waitress", "destruction", "Ford", "Veronica",
				"Eagle", "entitled", "fingerprints", "visual", "Blake", "newspapers", "sends", "active", "denied",
				"discovery", "dish", "electricity", "puppy", "cuts", "perfume", "shadows", "unconscious", "operating",
				"temper", "theatre", "bears", "companies", "Graham", "pillow", "roommate", "stones", "talented",
				"teachers", "approve", "cock", "defeat", "elephant", "forms", "promises", "safer", "scientific",
				"alike", "asses", "assumed", "chemical", "handling", "photograph", "slut", "trailer", "attacks",
				"Greek", "heal", "absolute", "Diana", "killers", "practical", "rage", "serial", "Shakespeare", "shrink",
				"studied", "applause", "potato", "slightly", "exists", "Geez", "gin", "Halt", "necessarily",
				"paintings", "Announcer", "Blanche", "raising", "Turner", "whale", "assholes", "creep", "peaceful",
				"warden", "lovers", "Randy", "complaining", "dentist", "gig", "immediate", "straighten", "transport",
				"Wesley", "affect", "billion", "bitter", "boot", "Bureau", "creepy", "Gob", "helpless", "include",
				"laying", "rd", "reception", "ages", "annoying", "brandy", "luggage", "tools", "assuming", "conduct",
				"disturb", "nest", "potatoes", "safely", "function", "massage", "wisdom", "Arizona", "boarding",
				"Charley", "jewelry", "Patty", "produce", "thrilled", "analysis", "nails", "Nelson", "delay", "Harbor",
				"psycho", "Latin", "limited", "Murray", "register", "September", "Spain", "election", "reverse", "shed",
				"column", "fetch", "intention", "lick", "nerves", "Ouch", "profit", "assistance", "booze", "emotions",
				"extreme", "Heather", "Shawn", "verdict", "worker", "airplane", "bra", "Herr", "lame", "promotion",
				"Oops", "rhythm", "cocktail", "Corps", "everyday", "Phoenix", "rounds", "honored", "Lance", "protected",
				"stubborn", "von", "clothing", "concept", "pumpkin", "smarter", "torn", "waters", "comic", "grief",
				"computers", "deposit", "dignity", "Franklin", "Kay", "sixth", "suggesting", "Canada", "commitment",
				"Gotcha", "instant", "monsters", "bits", "Jefferson", "Jew", "Orleans", "passenger", "proved",
				"understands", "acted", "beers", "creative", "facing", "Holly", "lip", "salary", "strictly",
				"throughout", "tool", "banana", "Cathy", "eternal", "marked", "required", "rod", "tissue", "campus",
				"guessing", "Pope", "subway", "bowling", "laughed", "arguing", "Roberts", "confident", "engines",
				"Hector", "homeless", "paranoid", "barrel", "drawn", "lamb", "privilege", "require", "wizard", "Wong",
				"Carmen", "executive", "fund", "worries", "capture", "drown", "fleet", "lungs", "multiple", "separated",
				"statue", "traitor", "twisted", "consequences", "Forty", "Philadelphia", "begun", "Detroit",
				"discussed", "exposed", "kindly", "payment", "adorable", "bets", "compared", "countries", "Elvis",
				"goat", "measure", "pork", "tested", "candles", "collar", "effective", "Houston", "Mars", "accounts",
				"Hans", "injured", "lecture", "passport", "publicity", "Roman", "root", "shared", "blond", "dice",
				"fixing", "grounds", "parked", "parole", "witches", "believing", "celebrating", "salesman", "twin",
				"booked", "bothered", "clubs", "crashed", "nanny", "paperwork", "plug", "rug", "rumor", "Sidney", "Cal",
				"meetings", "rode", "tellin", "unlike", "freaked", "lean", "Narrator", "bee", "colors", "communication",
				"Coop", "cooperate", "Caroline", "levels", "nd", "spots", "worthy", "wrist", "conspiracy", "enormous",
				"misery", "obsessed", "punished", "reservation", "sunset", "announcement", "Austin", "curtain", "drops",
				"Kane", "Nicky", "suggested", "driven", "fooled", "identified", "jeep", "locate", "relieved", "Sharon",
				"specifically", "abuse", "ballet", "blues", "compliment", "magazines", "naughty", "Skipper", "stiff",
				"sum", "unable", "album", "burger", "random", "spotted", "tense", "Caesar", "concerns", "corporate",
				"happily", "August", "chickens", "gunshot", "impress", "injury", "intense", "Jews", "praying",
				"Rembrandt", "borrowed", "brick", "Leon", "organized", "priority", "questioning", "raw", "slight",
				"structure", "complaint", "fried", "snakes", "leak", "registered", "sucked", "warrior", "worm", "bleed",
				"corpse", "document", "dynamite", "glove", "louder", "sober", "spider", "survival", "vodka", "branch",
				"worn", "budget", "deserved", "envelope", "novel", "pistol", "relatives", "shortly", "fraud", "Shaw",
				"shotgun", "bucket", "clouds", "overnight", "application", "belonged", "experienced", "heels", "nicely",
				"Russians", "buildings", "daughters", "Excellency", "warehouse", "acid", "butler", "massive", "menu",
				"sits", "skirt", "stare", "inner", "scholarship", "signing", "causes", "Collins", "constant",
				"counselor", "Korean", "October", "provided", "solved", "tanks", "visitors", "centre", "Cleveland",
				"festival", "imagined", "scum", "Atlantic", "bachelor", "celebration", "chairs", "deadly", "depend",
				"examine", "oath", "practicing", "Romeo", "screwing", "ashes", "disturbed", "pencil", "trucks",
				"arrangements", "bounce", "causing", "confusing", "fridge", "internal", "ties", "unfair", "burden",
				"core", "heroin", "shining", "championship", "Columbo", "dummy", "graduation", "humble", "Mummy",
				"appeared", "deals", "dramatic", "explode", "observe", "photographs", "raining", "retire", "roast",
				"shooter", "silk", "Wha", "announce", "bartender", "differently", "fires", "ransom", "coin", "dock",
				"dresses", "execution", "gut", "investment", "necklace", "sealed", "seventh", "surprises", "suspected",
				"taxes", "bait", "bald", "hats", "lit", "Queens", "Tarzan", "absurd", "blank", "blessing", "demands",
				"humanity", "Reese", "copies", "directions", "fee", "filling", "grip", "hostages", "kidney",
				"scientists", "sworn", "ape", "atmosphere", "bench", "Pacific", "polish", "punish", "added", "connect",
				"cracked", "pancakes", "spray", "aliens", "floating", "gathered", "losers", "Marines", "porch", "tires",
				"develop", "sandwiches", "autopsy", "positions", "requires", "adults", "burnt", "daylight", "Lynn",
				"mortal", "pace", "plants", "quote", "sorts", "terrified", "Benjamin", "envy", "hallway", "relations",
				"bearing", "characters", "interfere", "Li", "mill", "niece", "signals", "Alexander", "cooked",
				"delicate", "en", "lesbian", "loses", "prayers", "scale", "smack", "stores", "strikes", "sweep",
				"therapist", "grows", "previous", "retreat", "towels", "awhile", "cent", "chart", "helmet",
				"investigate", "Louie", "scheduled", "aircraft", "arrangement", "farewell", "fathers", "fog", "physics",
				"Praise", "stabbed", "strategy", "succeed", "discussing", "Hudson", "Moscow", "cows", "impact",
				"sailing", "scan", "severe", "shorts", "tons", "Wells", "Burke", "chaos", "civilian", "maniac",
				"triple", "types", "cellar", "covers", "documents", "flush", "oldest", "owes", "danced", "independent",
				"studies", "vessel", "alternative", "Hallelujah", "height", "motherfucking", "reveal", "Stella", "deed",
				"meets", "Thompson", "volunteer", "Brandon", "Calvin", "debate", "glorious", "upside", "Amber", "error",
				"faint", "handed", "stinking", "worthless", "ladder", "groups", "physically", "telegram", "Adams",
				"arts", "gasps", "hears", "poet", "resources", "web", "whatsoever", "Willy", "amusing", "arrives",
				"Ash", "bonus", "Bree", "delightful", "Grey", "hint", "stunt", "closely", "fairly", "horror", "located",
				"picks", "rumors", "admitted", "codes", "fears", "Jeremy", "tips", "betray", "holidays",
				"investigating", "Madison", "rented", "snack", "Alliance", "conclusion", "faithful", "photographer",
				"popcorn", "wars", "magical", "penalty", "phrase", "population", "recovery", "Reed", "somewhat",
				"thieves", "tournament", "Dale", "damaged", "disturbing", "outer", "chew", "crawling", "described",
				"discipline", "Rex", "roads", "takin", "washing", "cart", "coffin", "explains", "flame", "perimeter",
				"profession", "wax", "kindness", "ultimate", "barking", "Bret", "cargo", "gently", "Hopper", "terror",
				"vampires", "Yankee", "associate", "certificate", "coroner", "diary", "recognized", "sock", "swamp",
				"breast", "broadcast", "closest", "Jock", "kings", "lighter", "preparing", "Russ", "stall", "appetite",
				"areas", "barbecue", "crowded", "fluid", "gamble", "hopeless", "hostile", "obey", "planted", "ritual",
				"technique", "various", "dug", "motorcycle", "Noah", "sins", "whack", "auction", "contrary", "Creek",
				"Jr", "weakness", "Doris", "journal", "leaders", "oven", "spiritual", "tender", "worlds", "dial",
				"storage", "Val", "advanced", "Cd", "grades", "Helio", "Marilyn", "models", "constantly", "embarrass",
				"fork", "Melissa", "quitting", "repair", "reunion", "accidentally", "apologies", "filed", "lipstick",
				"pepper", "cities", "cough", "proposal", "protest", "recovered", "rolls", "unexpected", "goddess",
				"hurricane", "mankind", "November", "returns", "stab", "Supreme", "waking", "Almighty", "infection",
				"swore", "visitor", "wrestling", "beam", "cease", "reads", "anyways", "buttons", "cannon", "colour",
				"conflict", "crossing", "deer", "flu", "Fortunately", "objects", "Ohio", "primary", "shirts", "shrimp",
				"cemetery", "balloon", "chemistry", "samples", "sexually", "excuses", "fame", "harmless", "stuffed",
				"throne", "tin", "drain", "electrical", "fold", "morgue", "pimp", "requested", "rig", "setup",
				"activities", "distant", "tan", "briefcase", "Clarence", "Evans", "petty", "symbol", "vicious", "beds",
				"Del", "sleepy", "vulnerable", "banging", "Comrade", "convenient", "forbid", "hack", "increase", "raid",
				"reporters", "seated", "solo", "tension", "burst", "curiosity", "display", "Eli", "scares", "slice",
				"threaten", "transmission", "butcher", "candidate", "communicate", "gallery", "invasion", "wee",
				"laser", "lunatic", "rank", "robe", "til", "Woody", "compete", "drum", "ducks", "Fisher", "kiddo",
				"limo", "reference", "regarding", "rolled", "scar", "senses", "spill", "spoiled", "values", "affection",
				"Arnold", "chased", "crushed", "disappoint", "madness", "nickel", "resistance", "retirement",
				"tremendous", "adopted", "Australia", "betting", "dizzy", "Granny", "software", "anonymous", "haircut",
				"lads", "recorded", "rising", "thrill", "approval", "determine", "fry", "horny", "inn", "instincts",
				"scenes", "sequence", "courtroom", "Harrison", "refrigerator", "alibi", "ceiling", "compare", "judges",
				"ribs", "sympathy", "wig", "bare", "cabinet", "cane", "civilization", "Delta", "doorbell", "indicate",
				"nearby", "pointing", "formal", "generally", "jar", "plague", "apples", "emotion", "Felix", "reminded",
				"tore", "tramp", "arrival", "footage", "freaks", "fur", "infected", "misunderstanding", "philosophy",
				"prior", "scores", "Sheila", "cocaine", "Denver", "depth", "illusion", "native", "phony", "qualified",
				"replaced", "residence", "smash", "tossed", "writes", "Brooks", "Cape", "convicted", "lung",
				"technical", "visions", "wished", "Congress", "fortunate", "Harmony", "mature", "perspective",
				"radiation", "sang", "Shelly", "sources", "sweating", "yacht", "Canyon", "genuine", "Kent", "larger",
				"Lori", "shield", "Sixteen", "strings", "deeds", "insisted", "lan", "Mademoiselle", "autograph",
				"dedicated", "judging", "Mick", "trains", "cocksucker", "doomed", "established", "minimum", "pad",
				"presume", "traditional", "Alpha", "Association", "courtesy", "Eastern", "Fletcher", "Julius", "kicks",
				"panties", "scandal", "slaves", "abandon", "allergic", "Andromeda", "Belle", "capacity",
				"consciousness", "Enterprise", "girlfriends", "improve", "instrument" };

		for (String wordString : wordArray) {
			wordList.add(new Word(wordString, 0));
		}

		solution.putGene("tri", new CipherKeyGene(solution, "i"));
		solution.putGene("lrbox", new CipherKeyGene(solution, "l"));
		solution.putGene("p", new CipherKeyGene(solution, "i"));
		solution.putGene("forslash", new CipherKeyGene(solution, "k"));
		solution.putGene("z", new CipherKeyGene(solution, "e"));
		solution.putGene("u", new CipherKeyGene(solution, "i"));
		solution.putGene("b", new CipherKeyGene(solution, "l"));
		solution.putGene("backk", new CipherKeyGene(solution, "i"));
		solution.putGene("o", new CipherKeyGene(solution, "n"));
		solution.putGene("r", new CipherKeyGene(solution, "g"));
		solution.putGene("pi", new CipherKeyGene(solution, "p"));
		solution.putGene("backp", new CipherKeyGene(solution, "e"));
		solution.putGene("x", new CipherKeyGene(solution, "o"));
		solution.putGene("w", new CipherKeyGene(solution, "e"));
		solution.putGene("v", new CipherKeyGene(solution, "b"));
		solution.putGene("plus", new CipherKeyGene(solution, "e"));
		solution.putGene("backe", new CipherKeyGene(solution, "c"));
		solution.putGene("g", new CipherKeyGene(solution, "a"));
		solution.putGene("y", new CipherKeyGene(solution, "u"));
		solution.putGene("f", new CipherKeyGene(solution, "s"));
		solution.putGene("circledot", new CipherKeyGene(solution, "e"));
		solution.putGene("h", new CipherKeyGene(solution, "t"));
		solution.putGene("boxdot", new CipherKeyGene(solution, "s"));
		solution.putGene("k", new CipherKeyGene(solution, "s"));
		solution.putGene("anchor", new CipherKeyGene(solution, "o"));
		solution.putGene("backq", new CipherKeyGene(solution, "m"));
		solution.putGene("m", new CipherKeyGene(solution, "h"));
		solution.putGene("j", new CipherKeyGene(solution, "f"));
		solution.putGene("carrot", new CipherKeyGene(solution, "n"));
		solution.putGene("i", new CipherKeyGene(solution, "t"));
		solution.putGene("tridot", new CipherKeyGene(solution, "s"));
		solution.putGene("t", new CipherKeyGene(solution, "o"));
		solution.putGene("flipt", new CipherKeyGene(solution, "r"));
		solution.putGene("n", new CipherKeyGene(solution, "e"));
		solution.putGene("q", new CipherKeyGene(solution, "f"));
		solution.putGene("d", new CipherKeyGene(solution, "n"));
		solution.putGene("fullcircle", new CipherKeyGene(solution, "t"));
		solution.putGene("horstrike", new CipherKeyGene(solution, "h"));
		solution.putGene("s", new CipherKeyGene(solution, "a"));
		solution.putGene("vertstrike", new CipherKeyGene(solution, "n"));
		solution.putGene("fullbox", new CipherKeyGene(solution, "l"));
		solution.putGene("a", new CipherKeyGene(solution, "w"));
		solution.putGene("backf", new CipherKeyGene(solution, "d"));
		solution.putGene("backl", new CipherKeyGene(solution, "a"));
		solution.putGene("e", new CipherKeyGene(solution, "e"));
		solution.putGene("l", new CipherKeyGene(solution, "t"));
		solution.putGene("backd", new CipherKeyGene(solution, "o"));
		solution.putGene("backr", new CipherKeyGene(solution, "r"));
		solution.putGene("backslash", new CipherKeyGene(solution, "r"));
		solution.putGene("fulltri", new CipherKeyGene(solution, "a"));
		solution.putGene("zodiac", new CipherKeyGene(solution, "d"));
		solution.putGene("backc", new CipherKeyGene(solution, "v"));
		solution.putGene("backj", new CipherKeyGene(solution, "x"));
		solution.putGene("box", new CipherKeyGene(solution, "y"));

		solution.setCipher(zodiac408);
	}

	@BeforeClass
	public static void setUp() {
		fitnessEvaluator = new CipherKeyWordGraphFitnessEvaluator();

		fitnessEvaluator.setGeneticStructure(zodiac408);

		mockUniqueWordListDao = mock(UniqueWordListDao.class);
		fitnessEvaluator.setWordListDao(mockUniqueWordListDao);
	}

	@Before
	public void resetMocks() {
		reset(mockUniqueWordListDao);
	}

	@Test
	public void testEvaluate_top1000() {
		fitnessEvaluator.setTop(1000);
		when(mockUniqueWordListDao.getTopWords(1000)).thenReturn(wordList.subList(0, 1000));
		fitnessEvaluator.init();

		log.info("top1000 fitness: " + fitnessEvaluator.evaluate(solution));
	}

	@Test
	public void testEvaluate_top5000() {
		fitnessEvaluator.setTop(5000);
		when(mockUniqueWordListDao.getTopWords(5000)).thenReturn(wordList);
		fitnessEvaluator.init();

		log.info("top5000 fitness: " + fitnessEvaluator.evaluate(solution));
	}
}
