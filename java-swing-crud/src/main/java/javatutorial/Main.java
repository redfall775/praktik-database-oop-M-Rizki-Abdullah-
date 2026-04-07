package javatutorial;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FormKaryawan().setVisible(true);
        });
    }
}