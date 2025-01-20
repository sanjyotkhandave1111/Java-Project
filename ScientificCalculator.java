package Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScientificCalculator extends JFrame implements ActionListener {
    private JTextField textField;
    private String operator, operand1, operand2;
    private boolean operatorClicked;
    private JLabel runningLabelBottom;
    private int xPositionBottom;

    public ScientificCalculator() {
        operator = operand1 = operand2 = "";
        operatorClicked = false;

        // Set the frame properties
        setTitle("Scientific Calculator");
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(44, 62, 80)); // Dark blue background

        // Top panel for spacing and text field
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(new Color(44, 62, 80)); // Match the background color

        // Spacer panel for additional vertical space
        JPanel spacerPanel = new JPanel();
        spacerPanel.setBackground(new Color(44, 62, 80)); // Match the background
        spacerPanel.setPreferredSize(new Dimension(500, 30));
        topPanel.add(spacerPanel, BorderLayout.NORTH);

        // Create a text field for displaying the result
        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.BOLD, 24));
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setBackground(new Color(236, 240, 241)); // Light grey
        textField.setForeground(new Color(44, 62, 80)); // Dark blue text
        textField.setCaretColor(new Color(44, 62, 80)); // Dark blue caret
        textField.setBorder(BorderFactory.createLineBorder(new Color(41, 128, 185), 2)); // Blue border

        // Add hover effect to the text field
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                textField.setBorder(BorderFactory.createLineBorder(new Color(46, 204, 113), 3)); // Green border
            }

            @Override
            public void mouseExited(MouseEvent e) {
                textField.setBorder(BorderFactory.createLineBorder(new Color(41, 128, 185), 2)); // Reset to blue border
            }
        });

        topPanel.add(textField, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Create a panel for calculator buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 5, 10, 10));
        panel.setBackground(new Color(44, 62, 80)); // Dark blue background

        // Define button labels
        String[] buttonLabels = {
                "7", "8", "9", "/", "sqrt",
                "4", "5", "6", "*", "pow",
                "1", "2", "3", "-", "log",
                "0", ".", "=", "+", "sin",
                "C", "<-", "(", ")", "cos"
        };

        // Add buttons to the panel
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setBackground(new Color(52, 152, 219)); // Vibrant blue
            button.setForeground(Color.WHITE); // White text
            button.setBorder(BorderFactory.createLineBorder(new Color(41, 128, 185), 2));

            // Add hover effect to the button
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(new Color(46, 204, 113)); // Green background
                    button.setBorder(BorderFactory.createLineBorder(new Color(39, 174, 96), 2)); // Green border
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(new Color(52, 152, 219)); // Reset to blue background
                    button.setBorder(BorderFactory.createLineBorder(new Color(41, 128, 185), 2)); // Reset to blue border
                }
            });

            button.addActionListener(this);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);

        // Bottom panel for running text
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(null);
        bottomPanel.setBackground(new Color(44, 62, 80)); // Dark blue background
        add(bottomPanel, BorderLayout.SOUTH);

        // Running label at the bottom
        runningLabelBottom = new JLabel("Calculator");
        runningLabelBottom.setFont(new Font("Arial", Font.BOLD, 20));
        runningLabelBottom.setForeground(new Color(231, 76, 60)); // Red color
        bottomPanel.add(runningLabelBottom);

        // Set initial position for running text
        xPositionBottom = getWidth();
        runningLabelBottom.setBounds(xPositionBottom, 10, runningLabelBottom.getPreferredSize().width, 30);

        // Timer for running text at the bottom
        Timer timerBottom = new Timer(20, e -> {
            xPositionBottom -= 2; // Move left by 2 pixels
            if (xPositionBottom + runningLabelBottom.getWidth() < 0) {
                xPositionBottom = getWidth();
            }
            runningLabelBottom.setBounds(xPositionBottom, 10, runningLabelBottom.getPreferredSize().width, 30);
        });
        timerBottom.start();

        // Set frame size and behavior
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.charAt(0) >= '0' && command.charAt(0) <= '9' || command.equals(".")) {
            if (operatorClicked) {
                textField.setText(command);
                operatorClicked = false;
            } else {
                textField.setText(textField.getText() + command);
            }
        } else if (command.equals("C")) {
            textField.setText("");
            operand1 = operand2 = operator = "";
            operatorClicked = false;
        } else if (command.equals("<-")) {
            String text = textField.getText();
            if (text.length() > 0) {
                textField.setText(text.substring(0, text.length() - 1));
            }
        } else if (command.equals("=")) {
            operand2 = textField.getText();
            try {
                double result = 0;
                switch (operator) {
                    case "+":
                        result = Double.parseDouble(operand1) + Double.parseDouble(operand2);
                        break;
                    case "-":
                        result = Double.parseDouble(operand1) - Double.parseDouble(operand2);
                        break;
                    case "*":
                        result = Double.parseDouble(operand1) * Double.parseDouble(operand2);
                        break;
                    case "/":
                        result = Double.parseDouble(operand1) / Double.parseDouble(operand2);
                        break;
                    case "sqrt":
                        result = Math.sqrt(Double.parseDouble(operand1));
                        break;
                    case "pow":
                        result = Math.pow(Double.parseDouble(operand1), Double.parseDouble(operand2));
                        break;
                    case "log":
                        result = Math.log10(Double.parseDouble(operand1));
                        break;
                    case "sin":
                        result = Math.sin(Math.toRadians(Double.parseDouble(operand1)));
                        break;
                    case "cos":
                        result = Math.cos(Math.toRadians(Double.parseDouble(operand1)));
                        break;
                }
                textField.setText(String.valueOf(result));
                operand1 = String.valueOf(result);
                operand2 = operator = "";
                operatorClicked = true;
            } catch (Exception ex) {
                textField.setText("Error");
                operand1 = operand2 = operator = "";
            }
        } else {
            operand1 = textField.getText();
            operator = command;
            operatorClicked = true;
        }
    }

    public static void main(String[] args) {
        new ScientificCalculator();
    }
}