package io.github.ufedarovich.breadcrumb;

import javax.swing.JToggleButton;

/**
 * UI representation of the breadcrumb item.
 * Keeps item information
 *
 * @author Uladzimir Fedarovich
 * @since 12/18/2015
 */
public class BreadcrumbItem<T> extends JToggleButton {

    private final T data;
    private final int step;

    public BreadcrumbItem(String label, T data, int step) {
        super(label);
        setToolTipText(label);
        setUI(new BreadcrumbUI());
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        this.data = data;
        this.step = step;
    }

    public T getData() {
        return data;
    }

    public int getStep() {
        return step;
    }
}
