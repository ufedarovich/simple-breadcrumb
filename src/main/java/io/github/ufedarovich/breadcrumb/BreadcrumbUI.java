package io.github.ufedarovich.breadcrumb;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

/**
 * Represents UI for the breadcrumb item.
 * Builds proper shape for every item and handle mouse actions, based on created shape.
 *
 * @author Uladzimir Fedarovich
 * @since 12/18/2015
 */
public class BreadcrumbUI extends BasicButtonUI {

    private Shape shape;

    @Override
    protected void installListeners(AbstractButton b) {
        BasicButtonListener listener = new BasicButtonListener(b) {
            @Override
            public void mousePressed(MouseEvent e) {
                if (shape.contains(e.getX(), e.getY())) {
                    super.mousePressed(e);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (shape.contains(e.getX(), e.getY())) {
                    super.mouseEntered(e);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (shape.contains(e.getX(), e.getY())) {
                    super.mouseEntered(e);
                } else {
                    super.mouseExited(e);
                }
            }
        };
        b.addMouseListener(listener);
        b.addMouseMotionListener(listener);
        b.addFocusListener(listener);
        b.addPropertyChangeListener(listener);
        b.addChangeListener(listener);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Container parent = c.getParent();
        if (parent == null)
            return;

        Graphics2D graphics = (Graphics2D) g;
        BreadcrumbItem breadcrumbItem = (BreadcrumbItem) c;
        ButtonModel model = breadcrumbItem.getModel();
        shape = getShape(c);

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Color color = c.getBackground();
        if (breadcrumbItem.isRolloverEnabled() && model.isRollover() || breadcrumbItem.isSelected())
            color = Color.LIGHT_GRAY;

        graphics.setColor(color);
        graphics.fill(shape);

        graphics.setPaint(Color.GRAY);
        graphics.draw(shape);

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
        super.paint(graphics, c);
    }

    private Shape getShape(Component component) {

        Container parent = component.getParent();
        if (parent == null)
            return null;

        int width = component.getWidth() - 1;
        int height = component.getHeight() - 1;

        Path2D.Float p = new Path2D.Float();
        p.moveTo(0, 0);
        p.lineTo(width - 10, 0);
        p.lineTo(width, height / 2);
        p.lineTo(width - 10, height);
        p.lineTo(0, height);
        if (component != parent.getComponent(0))
            p.lineTo(10, height / 2);

        p.closePath();

        return AffineTransform.getTranslateInstance(0, 0).createTransformedShape(p);
    }
}
