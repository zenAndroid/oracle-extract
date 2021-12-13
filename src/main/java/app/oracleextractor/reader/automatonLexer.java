// Generated from C:/Users/zenAndroid/IdeaProjects/oracleExtractor/src/main/java/app/oracleextractor/reader\automaton.g4 by ANTLR 4.9.2
package app.oracleextractor.reader;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class automatonLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, TRIGGER=11, OUTPUT=12, WS=13, CHAR=14, NUM=15, STATE_NAME=16, 
		AUTOMATON_NAME=17, POINT_TO_INITIAL=18, CURRENT_FORM=19, CIRCULAR_SHAPE_STATES=20, 
		GRAPH_ORIENTATION=21;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "TRIGGER", "OUTPUT", "WS", "CHAR", "NUM", "STATE_NAME", "AUTOMATON_NAME", 
			"POINT_TO_INITIAL", "CURRENT_FORM", "CIRCULAR_SHAPE_STATES", "GRAPH_ORIENTATION"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'digraph'", "'{'", "'}'", "'->'", "'[label='", "'];'", "'\"'", 
			"'/'", "'INIT -> '", "';'", null, null, null, null, null, null, null, 
			"'node [shape=point] INIT;'", null, "'node [shape=circle];'", "'rankdir = LR;'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, "TRIGGER", 
			"OUTPUT", "WS", "CHAR", "NUM", "STATE_NAME", "AUTOMATON_NAME", "POINT_TO_INITIAL", 
			"CURRENT_FORM", "CIRCULAR_SHAPE_STATES", "GRAPH_ORIENTATION"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public automatonLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "automaton.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\27\u00cc\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\7\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3"+
		"\13\3\f\3\f\3\r\3\r\5\r[\n\r\3\16\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3"+
		"\21\6\21f\n\21\r\21\16\21g\3\22\6\22k\n\22\r\22\16\22l\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\3\26\2\2\27\3\3\5\4\7\5\t\6\13\7\r\b\17"+
		"\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+"+
		"\27\3\2\7\5\2\13\f\17\17\"\"\3\2c|\3\2\62;\4\2\62;c|\4\2C\\c|\2\u00ce"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\3-\3\2\2\2\5\65\3\2"+
		"\2\2\7\67\3\2\2\2\t9\3\2\2\2\13<\3\2\2\2\rD\3\2\2\2\17G\3\2\2\2\21I\3"+
		"\2\2\2\23K\3\2\2\2\25T\3\2\2\2\27V\3\2\2\2\31Z\3\2\2\2\33\\\3\2\2\2\35"+
		"`\3\2\2\2\37b\3\2\2\2!e\3\2\2\2#j\3\2\2\2%n\3\2\2\2\'\u0089\3\2\2\2)\u00a5"+
		"\3\2\2\2+\u00bc\3\2\2\2-.\7f\2\2./\7k\2\2/\60\7i\2\2\60\61\7t\2\2\61\62"+
		"\7c\2\2\62\63\7r\2\2\63\64\7j\2\2\64\4\3\2\2\2\65\66\7}\2\2\66\6\3\2\2"+
		"\2\678\7\177\2\28\b\3\2\2\29:\7/\2\2:;\7@\2\2;\n\3\2\2\2<=\7]\2\2=>\7"+
		"n\2\2>?\7c\2\2?@\7d\2\2@A\7g\2\2AB\7n\2\2BC\7?\2\2C\f\3\2\2\2DE\7_\2\2"+
		"EF\7=\2\2F\16\3\2\2\2GH\7$\2\2H\20\3\2\2\2IJ\7\61\2\2J\22\3\2\2\2KL\7"+
		"K\2\2LM\7P\2\2MN\7K\2\2NO\7V\2\2OP\7\"\2\2PQ\7/\2\2QR\7@\2\2RS\7\"\2\2"+
		"S\24\3\2\2\2TU\7=\2\2U\26\3\2\2\2VW\5\35\17\2W\30\3\2\2\2X[\5\35\17\2"+
		"Y[\5\37\20\2ZX\3\2\2\2ZY\3\2\2\2[\32\3\2\2\2\\]\t\2\2\2]^\3\2\2\2^_\b"+
		"\16\2\2_\34\3\2\2\2`a\t\3\2\2a\36\3\2\2\2bc\t\4\2\2c \3\2\2\2df\t\5\2"+
		"\2ed\3\2\2\2fg\3\2\2\2ge\3\2\2\2gh\3\2\2\2h\"\3\2\2\2ik\t\6\2\2ji\3\2"+
		"\2\2kl\3\2\2\2lj\3\2\2\2lm\3\2\2\2m$\3\2\2\2no\7p\2\2op\7q\2\2pq\7f\2"+
		"\2qr\7g\2\2rs\7\"\2\2st\7]\2\2tu\7u\2\2uv\7j\2\2vw\7c\2\2wx\7r\2\2xy\7"+
		"g\2\2yz\7?\2\2z{\7r\2\2{|\7q\2\2|}\7k\2\2}~\7p\2\2~\177\7v\2\2\177\u0080"+
		"\7_\2\2\u0080\u0081\7\"\2\2\u0081\u0082\7K\2\2\u0082\u0083\7P\2\2\u0083"+
		"\u0084\7K\2\2\u0084\u0085\7V\2\2\u0085\u0086\7=\2\2\u0086\u0087\3\2\2"+
		"\2\u0087\u0088\b\23\2\2\u0088&\3\2\2\2\u0089\u008a\5!\21\2\u008a\u008b"+
		"\7\"\2\2\u008b\u008c\7]\2\2\u008c\u008d\7u\2\2\u008d\u008e\7j\2\2\u008e"+
		"\u008f\7c\2\2\u008f\u0090\7r\2\2\u0090\u0091\7g\2\2\u0091\u0092\7?\2\2"+
		"\u0092\u0093\7$\2\2\u0093\u0094\7f\2\2\u0094\u0095\7q\2\2\u0095\u0096"+
		"\7w\2\2\u0096\u0097\7d\2\2\u0097\u0098\7n\2\2\u0098\u0099\7g\2\2\u0099"+
		"\u009a\7e\2\2\u009a\u009b\7k\2\2\u009b\u009c\7t\2\2\u009c\u009d\7e\2\2"+
		"\u009d\u009e\7n\2\2\u009e\u009f\7g\2\2\u009f\u00a0\7$\2\2\u00a0\u00a1"+
		"\7_\2\2\u00a1\u00a2\7=\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a4\b\24\2\2\u00a4"+
		"(\3\2\2\2\u00a5\u00a6\7p\2\2\u00a6\u00a7\7q\2\2\u00a7\u00a8\7f\2\2\u00a8"+
		"\u00a9\7g\2\2\u00a9\u00aa\7\"\2\2\u00aa\u00ab\7]\2\2\u00ab\u00ac\7u\2"+
		"\2\u00ac\u00ad\7j\2\2\u00ad\u00ae\7c\2\2\u00ae\u00af\7r\2\2\u00af\u00b0"+
		"\7g\2\2\u00b0\u00b1\7?\2\2\u00b1\u00b2\7e\2\2\u00b2\u00b3\7k\2\2\u00b3"+
		"\u00b4\7t\2\2\u00b4\u00b5\7e\2\2\u00b5\u00b6\7n\2\2\u00b6\u00b7\7g\2\2"+
		"\u00b7\u00b8\7_\2\2\u00b8\u00b9\7=\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00bb"+
		"\b\25\2\2\u00bb*\3\2\2\2\u00bc\u00bd\7t\2\2\u00bd\u00be\7c\2\2\u00be\u00bf"+
		"\7p\2\2\u00bf\u00c0\7m\2\2\u00c0\u00c1\7f\2\2\u00c1\u00c2\7k\2\2\u00c2"+
		"\u00c3\7t\2\2\u00c3\u00c4\7\"\2\2\u00c4\u00c5\7?\2\2\u00c5\u00c6\7\"\2"+
		"\2\u00c6\u00c7\7N\2\2\u00c7\u00c8\7T\2\2\u00c8\u00c9\7=\2\2\u00c9\u00ca"+
		"\3\2\2\2\u00ca\u00cb\b\26\2\2\u00cb,\3\2\2\2\6\2Zgl\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}