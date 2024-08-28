package cf.pies.decompiler;

import cf.pies.decompiler.node.*;
import me.kuwg.clarity.ast.AST;
import me.kuwg.clarity.compiler.ASTLoader;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ASTLoader loader = new ASTLoader(new File("tests/test.cclr"));

        try {
            AST ast = loader.load();

            Code code = new Code();

            code.addNodeHandlers(
                    new BlockNodeCreator(),
                    new FunctionNodeCreator(),
                    new NativeNodeCreator(),
                    new VariableNodeCreator(),
                    new BinaryExpressionCreator(),
                    new LiteralNodeCreator(),
                    new ClassNodeCreator()
            );

            code.handleNode(ast.getRoot());

//            System.out.println(ast);
            System.out.println(code.builder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
