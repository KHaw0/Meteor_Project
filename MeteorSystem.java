import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import javax.swing.*;

public class MeteorSystem extends JPanel {

    Random rn = new Random();
    private Display display;
    private int n;
    private Image bg;
    private Image[] meteor;
    private int[] posX;
    private int[] posY;
    private Image bomb;
    private JFrame frameCount = new JFrame();
    private MeteorLogic[] meteorThread;
    private JLabel[] lblMeteor;
    private boolean[] show;

    public boolean isReady = false;

    MeteorSystem(Display display) {
        this.display = display;

        setFrameCount();
    }

    void setFrameCount() {
        frameCount.setTitle("Meteor Count");
        JLabel lblCount = new JLabel("Meteor Count:");
        JTextField tfCount = new JTextField(25);
        JButton btnApply = new JButton("Apply");
        frameCount.setSize(400, 150);
        frameCount.setLayout(new FlowLayout());
        frameCount.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameCount.setLocationRelativeTo(null);

        frameCount.add(lblCount);
        frameCount.add(tfCount);
        frameCount.add(btnApply);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                for (int i = 0; i < n; i++) {
                    if (x >= posX[i] && x <= posX[i] + 50
                            && y >= posY[i] && y <= posY[i] + 50) {
                        meteor[i] = bomb;
                        show[i] = false;
                        repaint();
                    }
                }
            }
        });

        frameCount.setVisible(!isReady);
        btnApply.addActionListener(e -> {
            setMeteor(tfCount);
            loadImage();
            isReady = !isReady;

            for (MeteorLogic thread : meteorThread) {
                thread.start();
            }

            frameCount.setVisible(!isReady);
            display.setVisible(isReady);
        });

    }

    public void setMeteor(JTextField tfCount) {
        try {
            n = Integer.parseInt(tfCount.getText());
        } catch (Exception er) {
            return;
        }

        meteor = new Image[n];
        posX = new int[n];
        posY = new int[n];
        lblMeteor = new JLabel[n];
        meteorThread = new MeteorLogic[n];
        show = new boolean[n];

        for (int i = 0; i < n; i++) {
            posX[i] = rn.nextInt(0, 535);
            posY[i] = rn.nextInt(0, 520);
            show[i] = true;
            String path = "/Image/meteor" + rn.nextInt(1, 6) + ".png";
            meteor[i] = new ImageIcon(getClass().getResource(path)).getImage();
            lblMeteor[i] = new JLabel();
            meteorThread[i] = new MeteorLogic(this, i);
            add(lblMeteor[i]);
        }
    }

    public void loadImage() {
        bomb = new ImageIcon(getClass().getResource("/Image/boom.png")).getImage();
        bg = new ImageIcon(getClass().getResource("/Image/background.png")).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(bg, 0, 0, 600, 600, this);

        for (int i = 0; i < meteor.length; i++) {
            if (show[i]) {
                g.drawImage(meteor[i], posX[i], posY[i], 50, 50, lblMeteor[i]);
            }
        }
    }

    public JLabel[] getLblMeteor() {
        return lblMeteor;
    }

    public int[] getPosX() {
        return posX;
    }

    public int[] getPosY() {
        return posY;
    }

}

class MeteorLogic extends Thread {

    private MeteorSystem meteor;
    private int id;
    private int dx;
    private int dy;

    Random rn = new Random();

    public MeteorLogic(MeteorSystem meteor, int id) {
        this.meteor = meteor;
        this.id = id;

        this.dx = rn.nextBoolean() ? rn.nextInt(0, 6) : -rn.nextInt(0, 6);
        this.dy = rn.nextBoolean() ? rn.nextInt(0, 6) : -rn.nextInt(0, 6);
    }

    @Override
    public void run() {
        while (true) {
            int cx = meteor.getPosX()[id];
            int cy = meteor.getPosY()[id];

            cx += dx;
            cy += dy;

            if (cx < 0) {
                cx = 0;
                dx = -dx;
                dx += 1;
            } else if (cx > 535) {
                cx = 535;
                dx = -dx;
                dx -= 1;
            }
            if (cy < 0) {
                cy = 0;
                dy = -dy;
                dy += 1;
            } else if (cy > 520) {
                cy = 520;
                dy = -dy;
                dy -= 1;
            }

            meteor.getPosX()[id] = cx;
            meteor.getPosY()[id] = cy;

            meteor.repaint();

            try {
                Thread.sleep(16);
            } catch (Exception e) {
            }
        }
    }
}
