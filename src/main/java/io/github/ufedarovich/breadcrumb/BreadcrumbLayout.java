package io.github.ufedarovich.breadcrumb;

import javax.swing.AbstractButton;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Insets;

/**
 * Breadcrumb layout, calculates breadcrumb items size base on the next rules:
 * 1. If the breadcrumb panel size bigger than size of all elements,
 * then all elements displayed with the max width.
 * 2. If the panel is less, then selected elements displayed with the max width,
 * remained width shared between all other items.
 *
 * Minimum size of every item = 30px.
 *
 * @author Uladzimir Fedarovich
 * @since 12/18/2015
 */
public class BreadcrumbLayout extends FlowLayout {

    private final int maxWidth;
    private final int gap;
    private final int shift;

    private static final int MIN_WIDTH = 30;
    private static final int ITEM_HEIGHT = 20;

    /**
     * @param maxWidth the max size of the item in the breadcrumb,
     *                 in case not all items could be placed in the panel, it uses only for selected item
     * @param gap gap between breadcrumb items
     * @param shift shift to the left between items
     */
    public BreadcrumbLayout(int maxWidth, int gap, int shift) {
        this.maxWidth = maxWidth;
        this.gap = gap;
        this.shift = shift;
    }

    @Override
    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();
        int x = insets.left;
        int y = insets.top;

        int remainingWidth = getRemainingWidth(parent);
        int componentWidth = Math.max(MIN_WIDTH, getComponentWidth(parent, remainingWidth));

        for (Component component : parent.getComponents()) {
            int width = componentWidth;
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                if (button.isSelected())
                    width = maxWidth;
            }
            component.setBounds(x, y, width, ITEM_HEIGHT);
            x += width + gap - shift;
        }
    }

    private int getRemainingWidth(Container parent) {
        int componentsCount = parent.getComponentCount();
        int gapsWidth = (componentsCount - 1) * gap;

        return parent.getWidth() - gapsWidth - maxWidth;
    }

    private int getComponentWidth(Container parent, int remainWidth) {
        int componentsCount = parent.getComponentCount();
        int div = componentsCount < 2 ? componentsCount : componentsCount - 1;
        return Math.min(maxWidth, remainWidth / div);
    }
}