package io.github.ufedarovich.breadcrumb;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Breadcrumb component. It always contains root element.
 * This element and display label have to be passed as arguments to the constructor.
 *
 * The component allows to add breadcrumb listeners, which fired when some
 * item of the breadcrumb is clicked. Listeners receive data component of the selected item.
 *
 * @author Uladzimir Fedarovich
 * @see BreadcrumbListener
 * @since 12/18/2015
 */
public class Breadcrumb<T> extends JPanel {

    private final List<BreadcrumbListener<T>> listeners = new ArrayList<>();

    private final ActionListener itemListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            BreadcrumbItem<T> selected = (BreadcrumbItem<T>) e.getSource();

            // if last element clicked, nothing changed
            if (stack.isEmpty() || selected.getStep() == stack.peek().getStep())
                return;

            // go up until selected item
            while (selected.getStep() != stack.peek().getStep())
                stack.pop();

            for (BreadcrumbListener<T> listener : listeners)
                listener.actionPerformed(stack.peek().getData());

            rebuildBreadcrumb();
        }
    };

    private final MouseListener mouseListener = new MouseListener() {

        private ButtonModel selected;

        @Override
        public void mouseClicked(MouseEvent e) {
            selected = null;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            selected = null;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            selected = null;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            BreadcrumbItem<T> curr = (BreadcrumbItem<T>) e.getSource();
            if (buttonGroup.isSelected(curr.getModel()))
                return;
            selected = buttonGroup.getSelection();
            curr.setSelected(true);
            invalidate();
            revalidate();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (selected == null || buttonGroup.isSelected(selected))
                return;
            buttonGroup.setSelected(selected, true);
            invalidate();
            revalidate();
        }
    };

    private final Stack<BreadcrumbItem<T>> stack = new Stack<>();
    private ButtonGroup buttonGroup = new ButtonGroup();

    /**
     * Creates new breadcrumb component. Sets root element
     * @param label display label of the root item
     * @param data information, related to the root item
     */
    public Breadcrumb(String label, T data) {
        setLayout(new BreadcrumbLayout(150, 2, 9));
        append(label, data);
    }

    /**
     * Adds the specified breadcrumb listener to receive click event from
     * any breadcrumb item.
     * If breadCrumbListener is null, it will be ignored
     */
    public void addBreadcrumbListener(BreadcrumbListener<T> breadCrumbListener) {
        if (breadCrumbListener == null)
            return;
        listeners.add(breadCrumbListener);
    }

    /**
     * Appends new item at the end of the breadcrumb
     * @param label display label
     * @param data information, related to this item
     */
    public void append(String label, T data) {
        BreadcrumbItem<T> newItem = new BreadcrumbItem<>(label, data, stack.size());
        newItem.addActionListener(itemListener);
        newItem.addMouseListener(mouseListener);

        stack.push(newItem);
        buttonGroup.add(newItem);
        newItem.setSelected(true);

        this.add(newItem);
        this.revalidate();
        this.repaint();
    }

    private void rebuildBreadcrumb() {
        this.removeAll();
        buttonGroup = new ButtonGroup();

        for (BreadcrumbItem<T> item : stack) {
            buttonGroup.add(item);
            add(item);
        }

        this.revalidate();
        this.repaint();
    }
}