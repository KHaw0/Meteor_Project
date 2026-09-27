import java.awt.*;
import java.awt.event.*;
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
    private boolean[] show;
    private boolean[] isBombing;

    public boolean isReady = false;

    MeteorSystem(Display display) {
        this.display = display;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                for (int i = 0; i < n; i++) {
<<<<<<< HEAD
                    if (show[i] &&  x >= posX[i] && x <= posX[i] + 50
                            && y >= posY[i] && y <= posY[i] + 50) {
                        show[i] = false;
                        isBombing[i] = true;
                        repaint();
                        break;
=======
                    if (x >= posX[i] && x <= posX[i] + 50
                            && y >= posY[i] && y <= posY[i] + 50) {
                        show[i] = false;
                        repaint();
>>>>>>> 2e9e822d7c517f59727c051e2f5f0dcb1f62a30f
                    }
                }
            }
        });

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

        frameCount.setVisible(!isReady);
        btnApply.addActionListener(e -> {
            if (!setMeteor(tfCount))
                return;
            loadImage();
            isReady = !isReady;

            for (MeteorLogic thread : meteorThread) {
                thread.start();
            }

            frameCount.setVisible(!isReady);
            display.setVisible(isReady);
        });

    }

    public boolean setMeteor(JTextField tfCount) {
        try {
            n = Integer.parseInt(tfCount.getText());
        } catch (Exception er) {
            JLabel err = new JLabel("กรุณากรอกตัวเลข!!!");
            err.setFont(new Font("Tahoma", Font.BOLD, 14));
            JOptionPane.showMessageDialog(frameCount, err, "ERROR", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        meteor = new Image[n];
        posX = new int[n];
        posY = new int[n];
        meteorThread = new MeteorLogic[n];
        show = new boolean[n];
        isBombing = new boolean[n];

        for (int i = 0; i < n; i++) {
            posX[i] = rn.nextInt(0, 535);
            posY[i] = rn.nextInt(0, 520);
            show[i] = true;
            String path = "/Image/meteor" + rn.nextInt(1, 6) + ".png";
            meteor[i] = new ImageIcon(getClass().getResource(path)).getImage();
            meteorThread[i] = new MeteorLogic(this, i);
        }
        return true;
    }

    public void loadImage() {
        bomb = new ImageIcon(getClass().getResource("/Image/bomb.png")).getImage();
        bg = new ImageIcon(getClass().getResource("/Image/background.png")).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(bg, 0, 0, 600, 600, this);

        for (int i = 0; i < meteor.length; i++) {
            if (show[i]) {
                g.drawImage(meteor[i], posX[i], posY[i], 50, 50, this);
            } else if (isBombing[i]) {
                g.drawImage(bomb, posX[i], posY[i],50, 50, this);
            }
        }
    }

    public int[] getPosX() {
        return posX;
    }

    public int[] getPosY() {
        return posY;
    }
<<<<<<< HEAD

    public boolean[] getShow() {
        return show;
    }

    public boolean[] getIsBombing(){
        return isBombing;
    }
=======
>>>>>>> 2e9e822d7c517f59727c051e2f5f0dcb1f62a30f
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

        do {
            this.dx = rn.nextBoolean() ? rn.nextInt(0, 4) : -rn.nextInt(0, 4);
            this.dy = rn.nextBoolean() ? rn.nextInt(0, 4) : -rn.nextInt(0, 4);
        } while (dx == 0 && dy == 0);
    }

    @Override
    public void run() {
        while (true) {
            if (!meteor.getShow()[id]) {
                meteor.getIsBombing()[id] = true;
                meteor.repaint();
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                meteor.getIsBombing()[id] = false;
                meteor.repaint();
                break; 
            }

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
