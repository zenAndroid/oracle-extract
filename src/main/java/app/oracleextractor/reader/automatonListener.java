// Generated from C:/Users/zenAndroid/IdeaProjects/oracleExtractor/src/main/java/app/oracleextractor/reader\automaton.g4 by ANTLR 4.9.2
package app.oracleextractor.reader;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link automatonParser}.
 */
public interface automatonListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link automatonParser#automatonGraph}.
	 * @param ctx the parse tree
	 */
	void enterAutomatonGraph(automatonParser.AutomatonGraphContext ctx);
	/**
	 * Exit a parse tree produced by {@link automatonParser#automatonGraph}.
	 * @param ctx the parse tree
	 */
	void exitAutomatonGraph(automatonParser.AutomatonGraphContext ctx);
	/**
	 * Enter a parse tree produced by {@link automatonParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(automatonParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link automatonParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(automatonParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link automatonParser#trigger_output}.
	 * @param ctx the parse tree
	 */
	void enterTrigger_output(automatonParser.Trigger_outputContext ctx);
	/**
	 * Exit a parse tree produced by {@link automatonParser#trigger_output}.
	 * @param ctx the parse tree
	 */
	void exitTrigger_output(automatonParser.Trigger_outputContext ctx);
	/**
	 * Enter a parse tree produced by {@link automatonParser#initArrow}.
	 * @param ctx the parse tree
	 */
	void enterInitArrow(automatonParser.InitArrowContext ctx);
	/**
	 * Exit a parse tree produced by {@link automatonParser#initArrow}.
	 * @param ctx the parse tree
	 */
	void exitInitArrow(automatonParser.InitArrowContext ctx);
}