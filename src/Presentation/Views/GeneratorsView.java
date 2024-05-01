package Presentation.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GeneratorsView extends JPanel {

    private ActionListener actionListener;

    public GeneratorsView(ActionListener listener) {
        this.actionListener = listener;
        init();
        //mount();
        setVisible(true);
    }

    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel generatorsPanel = new JPanel(new FlowLayout());
        generatorsPanel.add(new JLabel("Name"));
        generatorsPanel.add(new JLabel("Price"));
        generatorsPanel.add(new JLabel("Amount"));

        JPanel firstGenerator = new JPanel(new FlowLayout());
        JPanel secondGenerator = new JPanel(new FlowLayout());
        JPanel thirdGenerator = new JPanel(new FlowLayout());

        firstGenerator.add(new JLabel("Generator 1"));
        firstGenerator.add(new JLabel("100"));
        firstGenerator.add(new JLabel("0"));
        JButton buyButtonFirstGenerator = new JButton("Buy");
        buyButtonFirstGenerator.addActionListener(actionListener);
        buyButtonFirstGenerator.setActionCommand("buy1");
        firstGenerator.add(buyButtonFirstGenerator);


        secondGenerator.add(new JLabel("Generator 2"));
        secondGenerator.add(new JLabel("200"));
        secondGenerator.add(new JLabel("0"));
        JButton buyButtonSecondGenerator = new JButton("Buy");
        buyButtonSecondGenerator.setActionCommand("buy2");
        buyButtonSecondGenerator.addActionListener(actionListener);
        secondGenerator.add(buyButtonSecondGenerator);

        thirdGenerator.add(new JLabel("Generator 3"));
        thirdGenerator.add(new JLabel("300"));
        thirdGenerator.add(new JLabel("0"));
        JButton buyButtonThirdGenerator = new JButton("Buy");
        buyButtonThirdGenerator.setActionCommand("buy3");
        buyButtonThirdGenerator.addActionListener(actionListener);
        thirdGenerator.add(buyButtonThirdGenerator);

        add(generatorsPanel);
        add(firstGenerator);
        add(secondGenerator);
        add(thirdGenerator);
    }

    private void mount() {

    }
}
