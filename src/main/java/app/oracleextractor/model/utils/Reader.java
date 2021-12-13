package app.oracleextractor.model.utils;

import app.oracleextractor.reader.automatonLexer;
import app.oracleextractor.reader.automatonParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;

public class Reader {

    public static void main(String[] args) throws IOException {
        CharStream fileName = CharStreams.fromFileName("./testMach.txt");
        System.out.println(System.getProperty("user.dir")); // C:\Users\zenAndroid\IdeaProjects\oracleExtractor

        automatonLexer automatonLexer = new automatonLexer(fileName);
        CommonTokenStream commonTokenStream = new CommonTokenStream(automatonLexer);
        automatonParser automatonParser = new automatonParser(commonTokenStream);

        ParseTree parseTree = automatonParser.automatonGraph();

        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();

        parseTreeWalker.walk(new AutomatonListener(), parseTree);

        System.out.println(parseTree.toStringTree(automatonParser));
        //  for (int i = 0; i < parseTree.getChildCount(); i++) {
        //      System.out.println(parseTree.getChild(i));
        //      ParseTree el = parseTree.getChild(8);
        // }
    }
}