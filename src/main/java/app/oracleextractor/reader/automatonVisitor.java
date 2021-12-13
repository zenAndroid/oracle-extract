// Generated from C:/Users/zenAndroid/IdeaProjects/oracleExtractor/src/main/java/app/oracleextractor/reader\automaton.g4 by ANTLR 4.9.2
package app.oracleextractor.reader;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link automatonParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface automatonVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link automatonParser#automatonGraph}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAutomatonGraph(automatonParser.AutomatonGraphContext ctx);
	/**
	 * Visit a parse tree produced by {@link automatonParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatements(automatonParser.StatementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link automatonParser#trigger_output}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrigger_output(automatonParser.Trigger_outputContext ctx);
	/**
	 * Visit a parse tree produced by {@link automatonParser#initArrow}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitArrow(automatonParser.InitArrowContext ctx);
}