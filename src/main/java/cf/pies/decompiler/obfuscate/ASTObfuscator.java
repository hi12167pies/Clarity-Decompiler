package cf.pies.decompiler.obfuscate;

import cf.pies.decompiler.NodeError;
import cf.pies.decompiler.utils.Reflections;
import me.kuwg.clarity.ast.AST;
import me.kuwg.clarity.ast.ASTNode;
import me.kuwg.clarity.ast.nodes.block.BlockNode;
import me.kuwg.clarity.ast.nodes.clazz.ClassDeclarationNode;
import me.kuwg.clarity.ast.nodes.function.call.DefaultNativeFunctionCallNode;
import me.kuwg.clarity.ast.nodes.function.declare.FunctionDeclarationNode;
import me.kuwg.clarity.ast.nodes.variable.assign.VariableDeclarationNode;
import me.kuwg.clarity.ast.nodes.variable.assign.VariableReassignmentNode;
import me.kuwg.clarity.ast.nodes.variable.get.VariableReferenceNode;

import java.util.*;

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

    private Map<String, String> obfuscatedNames = new HashMap<>();

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

        if (_node instanceof ClassDeclarationNode) {
            ClassDeclarationNode node = (ClassDeclarationNode) _node;
            handleNode(node.getBody());
            for (ASTNode n : node.getConstructors()) {
                handleNode(n);
            }
            return;
        }

        if (_node instanceof FunctionDeclarationNode) {
            FunctionDeclarationNode node = (FunctionDeclarationNode) _node;
            handleNode(node.getBlock());
            for (ASTNode param : node.getParameterNodes()) {
                handleNode(param);
            }
            return;
        }

        if (_node instanceof VariableDeclarationNode) {
            VariableDeclarationNode node = (VariableDeclarationNode) _node;
            String newName = String.valueOf(random.nextInt());
            obfuscatedNames.put(node.getName(), newName);
            Reflections.setField(node, "name", newName);
            handleNode(node.getValue());
            return;
        }

        if (_node instanceof VariableReferenceNode) {
            VariableReferenceNode node = (VariableReferenceNode) _node;
            Reflections.setField(node, "name", obfuscatedNames.getOrDefault(node.getName(), node.getName()));
            return;
        }

        if (_node instanceof VariableReassignmentNode) {
            VariableReassignmentNode node = (VariableReassignmentNode) _node;
            Reflections.setField(node, "name", obfuscatedNames.getOrDefault(node.getName(), node.getName()));
            handleNode(node.getValue());
            return;
        }

        if (_node instanceof DefaultNativeFunctionCallNode) {
            DefaultNativeFunctionCallNode node = (DefaultNativeFunctionCallNode) _node;
            for (ASTNode param : node.getParams()) {
                handleNode(param);
            }
            return;
        }


        errors.add(new NodeError("Unknown node: " + _node, _node));
    }
}
