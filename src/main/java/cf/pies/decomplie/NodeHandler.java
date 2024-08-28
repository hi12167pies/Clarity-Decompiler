package cf.pies.decomplie;

import me.kuwg.clarity.ast.ASTNode;

import java.util.Set;

public interface NodeHandler {
    Set<Class<?>> getSupportedNodes();
    void handle(ASTNode node, Code code);
}
