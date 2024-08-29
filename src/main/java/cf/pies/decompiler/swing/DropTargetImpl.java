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
            // Accept the drop
            dtde.acceptDrop(DnDConstants.ACTION_COPY);

            // Check if the dropped data is a list of files
            if (dtde.getTransferable().isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                // Get the dropped files
                java.util.List<File> droppedFiles = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                // Process the files (e.g., display the file paths in the text area)
                File file = droppedFiles.get(0);
                if (!file.getName().endsWith(".cclr")) {
                    gui.textArea.setText("Expected cclr file.");
                } else {
                    gui.currentAst = new ASTLoader(file).load();
                    gui.astMode();
                }

                // Mark the drop as successful
                dtde.dropComplete(true);
            } else {
                dtde.dropComplete(false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            dtde.dropComplete(false);
        }
    }
}