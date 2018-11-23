package NavrhVzhledu;

public class DropApplet extends Applet implements DropTargetListener {
    // . . .
    private DropTarget dropTarget;
    // . . .

    public void init() {
        if (dropTarget == null) {
            dropTarget = new DropTarget(this, this);
        }
        // . . .
    }

    public void dragEnter(DropTargetDragEvent arg0) {
        // nothing
    }

    public void dragOver(DropTargetDragEvent arg0) {
        // nothing
    }

    public void dropActionChanged(DropTargetDragEvent arg0) {
        // nothing
    }

    public void dragExit(DropTargetEvent arg0) {
        // nothing
    }

    public void drop(DropTargetDropEvent evt) {
        final List result = new ArrayList();
        int action = evt.getDropAction();
        evt.acceptDrop(action);
        try {
            Transferable data = evt.getTransferable();
            DataFlavor flavors[] = data.getTransferDataFlavors();
            if (data.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                List<File> list = (List<File>) data.getTransferData(
                    DataFlavor.javaFileListFlavor);
                processFiles(list);
            }
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            evt.dropComplete(true);
            repaint();
        }
    }

    private void processFiles(List<File> files) throws IOException {
        // . . .
    }
}