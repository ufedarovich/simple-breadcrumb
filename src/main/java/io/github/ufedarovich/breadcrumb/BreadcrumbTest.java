package io.github.ufedarovich.breadcrumb;

import javax.swing.*;
import java.awt.*;

/**
 * @author Uladzimir Fedarovich
 * @since 12/18/2015
 */
public class BreadcrumbTest {

    public static void main(String[] args) {
        JFrame frame = new JFrame("BreadcrumbList");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Breadcrumb<String> bc = new Breadcrumb<>("First", "first data");
        bc.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        bc.addBreadcrumbListener(System.out::println);

        frame.getContentPane().add(bc, BorderLayout.NORTH);
        JPanel inputPanel = new JPanel();
        JTextField input = new JTextField(20);
        inputPanel.add(input);
        JButton button = new JButton("Append");
        button.addActionListener(e -> {
            bc.append(input.getText(), String.valueOf(System.currentTimeMillis()));
            input.setText("");
        });
        inputPanel.add(button);

        frame.getContentPane().add(inputPanel);
        frame.setSize(350, 150);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
