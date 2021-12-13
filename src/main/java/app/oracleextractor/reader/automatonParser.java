// Generated from C:/Users/zenAndroid/IdeaProjects/oracleExtractor/src/main/java/app/oracleextractor/reader\automaton.g4 by ANTLR 4.9.2
package app.oracleextractor.reader;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class automatonParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, TRIGGER=11, OUTPUT=12, WS=13, CHAR=14, NUM=15, STATE_NAME=16, 
		AUTOMATON_NAME=17, POINT_TO_INITIAL=18, CURRENT_FORM=19, CIRCULAR_SHAPE_STATES=20, 
		GRAPH_ORIENTATION=21;
	public static final int
		RULE_automatonGraph = 0, RULE_statements = 1, RULE_trigger_output = 2, 
		RULE_initArrow = 3;
	private static String[] makeRuleNames() {
		return new String[] {
			"automatonGraph", "statements", "trigger_output", "initArrow"
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

	@Override
	public String getGrammarFileName() { return "automaton.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public automatonParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class AutomatonGraphContext extends ParserRuleContext {
		public TerminalNode AUTOMATON_NAME() { return getToken(automatonParser.AUTOMATON_NAME, 0); }
		public InitArrowContext initArrow() {
			return getRuleContext(InitArrowContext.class,0);
		}
		public List<StatementsContext> statements() {
			return getRuleContexts(StatementsContext.class);
		}
		public StatementsContext statements(int i) {
			return getRuleContext(StatementsContext.class,i);
		}
		public AutomatonGraphContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_automatonGraph; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof automatonListener ) ((automatonListener)listener).enterAutomatonGraph(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof automatonListener ) ((automatonListener)listener).exitAutomatonGraph(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof automatonVisitor ) return ((automatonVisitor<? extends T>)visitor).visitAutomatonGraph(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AutomatonGraphContext automatonGraph() throws RecognitionException {
		AutomatonGraphContext _localctx = new AutomatonGraphContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_automatonGraph);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(8);
			match(T__0);
			setState(9);
			match(AUTOMATON_NAME);
			setState(10);
			match(T__1);
			setState(11);
			initArrow();
			setState(15);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==STATE_NAME) {
				{
				{
				setState(12);
				statements();
				}
				}
				setState(17);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(18);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementsContext extends ParserRuleContext {
		public List<TerminalNode> STATE_NAME() { return getTokens(automatonParser.STATE_NAME); }
		public TerminalNode STATE_NAME(int i) {
			return getToken(automatonParser.STATE_NAME, i);
		}
		public Trigger_outputContext trigger_output() {
			return getRuleContext(Trigger_outputContext.class,0);
		}
		public StatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof automatonListener ) ((automatonListener)listener).enterStatements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof automatonListener ) ((automatonListener)listener).exitStatements(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof automatonVisitor ) return ((automatonVisitor<? extends T>)visitor).visitStatements(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementsContext statements() throws RecognitionException {
		StatementsContext _localctx = new StatementsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statements);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(20);
			match(STATE_NAME);
			setState(21);
			match(T__3);
			setState(22);
			match(STATE_NAME);
			setState(23);
			match(T__4);
			setState(24);
			trigger_output();
			setState(25);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Trigger_outputContext extends ParserRuleContext {
		public TerminalNode TRIGGER() { return getToken(automatonParser.TRIGGER, 0); }
		public TerminalNode OUTPUT() { return getToken(automatonParser.OUTPUT, 0); }
		public Trigger_outputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_trigger_output; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof automatonListener ) ((automatonListener)listener).enterTrigger_output(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof automatonListener ) ((automatonListener)listener).exitTrigger_output(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof automatonVisitor ) return ((automatonVisitor<? extends T>)visitor).visitTrigger_output(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Trigger_outputContext trigger_output() throws RecognitionException {
		Trigger_outputContext _localctx = new Trigger_outputContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_trigger_output);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			match(T__6);
			setState(28);
			match(TRIGGER);
			setState(29);
			match(T__7);
			setState(30);
			match(OUTPUT);
			setState(31);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitArrowContext extends ParserRuleContext {
		public TerminalNode STATE_NAME() { return getToken(automatonParser.STATE_NAME, 0); }
		public InitArrowContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initArrow; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof automatonListener ) ((automatonListener)listener).enterInitArrow(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof automatonListener ) ((automatonListener)listener).exitInitArrow(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof automatonVisitor ) return ((automatonVisitor<? extends T>)visitor).visitInitArrow(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InitArrowContext initArrow() throws RecognitionException {
		InitArrowContext _localctx = new InitArrowContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_initArrow);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			match(T__8);
			setState(34);
			match(STATE_NAME);
			setState(35);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\27(\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\3\2\3\2\3\2\3\2\3\2\7\2\20\n\2\f\2\16\2\23\13\2\3\2"+
		"\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3"+
		"\5\3\5\2\2\6\2\4\6\b\2\2\2$\2\n\3\2\2\2\4\26\3\2\2\2\6\35\3\2\2\2\b#\3"+
		"\2\2\2\n\13\7\3\2\2\13\f\7\23\2\2\f\r\7\4\2\2\r\21\5\b\5\2\16\20\5\4\3"+
		"\2\17\16\3\2\2\2\20\23\3\2\2\2\21\17\3\2\2\2\21\22\3\2\2\2\22\24\3\2\2"+
		"\2\23\21\3\2\2\2\24\25\7\5\2\2\25\3\3\2\2\2\26\27\7\22\2\2\27\30\7\6\2"+
		"\2\30\31\7\22\2\2\31\32\7\7\2\2\32\33\5\6\4\2\33\34\7\b\2\2\34\5\3\2\2"+
		"\2\35\36\7\t\2\2\36\37\7\r\2\2\37 \7\n\2\2 !\7\16\2\2!\"\7\t\2\2\"\7\3"+
		"\2\2\2#$\7\13\2\2$%\7\22\2\2%&\7\f\2\2&\t\3\2\2\2\3\21";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}