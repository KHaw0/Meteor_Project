import javax.swing.*;

public class Display extends JFrame {

    Display() {
        setTitle("Meteor Project");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        Display display = new Display();
        MeteorSystem system = new MeteorSystem(display);

        system.setBounds(0, 0, 600, 600);

        display.add(system);
        int x = 0;
    }
}