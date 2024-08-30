package cf.pies.decompiler.gui.menu.file;

import cf.pies.decompiler.gui.SwingGui;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;

public class FileSelectionDragListener implements DropTargetListener {
    private final SwingGui gui;
    private final FileSelectionMenu menu;
    private final JButton button;
    private final String defaultText;

    public FileSelectionDragListener(SwingGui gui, FileSelectionMenu menu, JButton button) {
        this.gui = gui;
        this.menu = menu;
        this.button = button;
        this.defaultText = button.getText();
    }

    @Override
    public void dragEnter(DropTargetDragEvent event) {
    }

    @Override
    @SuppressWarnings("unchecked")
    public void dragOver(DropTargetDragEvent event) {
        try {
            if (!event.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) return;
            event.acceptDrag(DnDConstants.ACTION_COPY);
            List<File> files = (List<File>) event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

            if (files.isEmpty()) {
                throw new IllegalStateException("Files is empty");
            }

            File file = files.get(0);
            button.setText("Select file: " + file.getName());
        } catch (Exception e) {
            gui.showError(e);
        }
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent event) {
    }

    @Override
    public void dragExit(DropTargetEvent event) {
        button.setText(defaultText);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void drop(DropTargetDropEvent event) {
        button.setText(defaultText);
        try {
            if (!event.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) return;
            event.acceptDrop(DnDConstants.ACTION_COPY);
            List<File> files = (List<File>) event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

            if (files.isEmpty()) {
                throw new IllegalStateException("Files is empty");
            }

            File file = files.get(0);
            menu.setFile(gui, file);
        } catch (Exception e) {
            gui.showError(e);
        }
    }
}
