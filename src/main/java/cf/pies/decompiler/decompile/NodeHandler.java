package cf.pies.decompiler.decompile;

import me.kuwg.clarity.ast.ASTNode;

import java.util.Set;

public interface NodeHandler {
    /**
     * @return A list of nodes that the handler supports
     */
    Set<Class<?>> getSupportedNodes();

    /**
     * Handles a node and adds to the code the appropriate decompiled code
     */
    void handle(ASTNode node, CodeDecompiler code);
}
