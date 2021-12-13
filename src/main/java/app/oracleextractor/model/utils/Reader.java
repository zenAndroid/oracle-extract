package app.oracleextractor.model.utils;

import app.oracleextractor.model.Machine;
import app.oracleextractor.reader.automatonLexer;
import app.oracleextractor.reader.automatonParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Reader {
    public static Machine parseMachine(File file) throws IOException {
        Path filePath = file.toPath();
        CharStream fileName = CharStreams.fromPath(filePath);

        automatonLexer automatonLexer = new automatonLexer(fileName);
        CommonTokenStream commonTokenStream = new CommonTokenStream(automatonLexer);
        automatonParser automatonParser = new automatonParser(commonTokenStream);

        ParseTree parseTree = automatonParser.automatonGraph(); // Start this rule!

        ParseTreeWalker parseTreeWalker = new ParseTreeWalker(); // Tree walker!

        AutomatonListener automatonListener = new AutomatonListener(); // Tree listener!

        parseTreeWalker.walk(automatonListener, parseTree); // Walk the parseTree with the listener!

        return automatonListener.getParsedMachine();
    }
}
