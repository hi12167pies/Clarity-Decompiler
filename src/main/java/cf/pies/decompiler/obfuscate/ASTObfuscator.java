package cf.pies.decompiler.obfuscate;

import cf.pies.decompiler.NodeError;
import me.kuwg.clarity.ast.AST;
import me.kuwg.clarity.ast.ASTNode;
import me.kuwg.clarity.ast.nodes.block.BlockNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ASTObfuscator {
    private final AST ast;
    private final Random random = new Random();
    private boolean obfuscateLines = true;
    private List<NodeError> errors = new ArrayList<>();

    public ASTObfuscator(AST ast) {
        this.ast = ast;
    }

    public void setObfuscateLines(boolean obfuscateLines) {
        this.obfuscateLines = obfuscateLines;
    }

    public List<NodeError> getErrors() {
        return errors;
    }

    /**
     * Obfuscates code useful to check errors after
     */
    public void obfuscate() {
        handleNode(ast.getRoot());
    }

    public void handleNode(ASTNode _node) {
        if (obfuscateLines) {
            _node.setLine(random.nextInt());
        }

        if (_node instanceof BlockNode) {
            BlockNode node = (BlockNode) _node;
            for (ASTNode children : node.getChildrens()) {
                handleNode(children);
            }
            return;
        }

        errors.add(new NodeError("Unknown node: " + _node, _node));
    }
}
