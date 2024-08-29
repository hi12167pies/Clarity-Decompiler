package cf.pies.decompiler.swing;

import me.kuwg.clarity.compiler.ASTLoader;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;

public class DropTargetImpl implements DropTargetListener {
    private final SwingGui gui;
    public DropTargetImpl(SwingGui gui) {
        this.gui = gui;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        try {
            dtde.acceptDrop(DnDConstants.ACTION_COPY);

            if (dtde.getTransferable().isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                java.util.List<File> droppedFiles = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                File file = droppedFiles.get(0);
                if (!file.getName().endsWith(".cclr")) {
                    gui.textArea.setText("Expected cclr file.");
                } else {
                    gui.currentAst = new ASTLoader(file).load();
                    gui.setMenu(Menu.AST);
                }

                // Mark the drop as successful
                dtde.dropComplete(true);
            } else {
                dtde.dropComplete(false);
            }
        } catch (Exception ex) {
            gui.showError(ex);
            dtde.dropComplete(false);
        }
    }
}