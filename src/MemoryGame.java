import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryGame extends JFrame implements ActionListener {
    private JButton[] buttons = new JButton[36];
    private int[] values = new int[36];
    private ArrayList<Integer> chosenIndices = new ArrayList<>();
    private int score = 0;

    public MemoryGame() {
        setTitle("Memory Matching Game");
        setLayout(new GridLayout(6, 6));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);

        // Grid'de kullanılacak sayıları oluştur ve karıştır
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 18; i++) {
            numbers.add(i);
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        // JButton'ları oluştur ve dinleyicileri ekle
        for (int i = 0; i < 36; i++) {
            values[i] = numbers.get(i);
            buttons[i] = new JButton("?");
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 24));
            buttons[i].addActionListener(this);
            add(buttons[i]);
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        int index = -1;

        // Hangi butona tıklandığını bul
        for (int i = 0; i < 36; i++) {
            if (clickedButton == buttons[i]) {
                index = i;
                break;
            }
        }

        if (chosenIndices.size() < 2 && !chosenIndices.contains(index)) {
            chosenIndices.add(index);
            buttons[index].setText(String.valueOf(values[index]));
        }

        if (chosenIndices.size() == 2) {
            int firstIndex = chosenIndices.get(0);
            int secondIndex = chosenIndices.get(1);

            if (values[firstIndex] == values[secondIndex]) {
                score++;
                chosenIndices.clear();
                if (score == 18) {
                    JOptionPane.showMessageDialog(this, "Tebrikler! Oyunu Kazandınız.");
                    System.exit(0);
                }
            } else {
                Timer timer = new Timer(1000, evt -> {
                    buttons[firstIndex].setText("?");
                    buttons[secondIndex].setText("?");
                    chosenIndices.clear();
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MemoryGame::new);
    }
}
