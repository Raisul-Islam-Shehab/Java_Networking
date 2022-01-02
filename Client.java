import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;


public class Client extends JFrame implements ActionListener {

    JPanel p1;
    JTextField t1;
    JButton b1;
    static JButton b2;
    static JTextArea a1;
    static JTextArea a2;
    static Socket socket;
    static DataOutputStream ous;
    static DataInputStream ois;

    Client() {
        p1 = new JPanel();
        p1.setLayout(null);     // default border layout removed and set to null.
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 50);
        add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(5, 12, 30, 30);
        p1.add(l1);

        l1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel l2 = new JLabel(i6);
        l2.setBounds(40, 5, 40, 40);
        p1.add(l2);

        JLabel l3 = new JLabel("Client");
        l3.setFont(new Font("Font.SERIF", Font.BOLD, 15));
        l3.setForeground(Color.WHITE);
        l3.setBounds(85, 5, 100, 30);
        p1.add(l3);

        JLabel l4 = new JLabel("Active now");
        l4.setFont(new Font("Font.SERIF", Font.PLAIN, 10));
        l4.setForeground(Color.WHITE);
        l4.setBounds(85, 25, 100, 20);
        p1.add(l4);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel l5 = new JLabel(i9);
        l5.setBounds(335, 10, 30, 30);
        p1.add(l5);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel l6 = new JLabel(i12);
        l6.setBounds(380, 10, 30, 30);
        p1.add(l6);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 20, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel l7 = new JLabel(i15);
        l7.setBounds(425, 15, 10, 20);
        p1.add(l7);

        a1 = new JTextArea();
        a1.setBounds(5, 55, 220, 595);
        a1.setBackground(Color.CYAN);
        a1.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
        a1.setEditable(false);
        a1.setLineWrap(true);
        a1.setWrapStyleWord(true);
        add(a1);

        a2 = new JTextArea();
        a2.setBounds(225, 55, 220, 595);
        a2.setBackground(Color.CYAN);
        a2.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
        a2.setEditable(false);
        a2.setLineWrap(true);
        a2.setWrapStyleWord(true);
        add(a2);

        t1 = new JTextField();
        t1.setBounds(90, 655, 270, 40);
        t1.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        add(t1);

        b1 = new JButton("Send");
        b1.setBounds(365, 655, 80, 40);
        b1.setBackground(new Color(7, 94, 84));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        add(b1);

        b2 = new JButton("File");
        b2.setBounds(5, 655, 80, 40);
        b2.setBackground(new Color(7, 94, 84));
        b2.setForeground(Color.WHITE);
        b2.addActionListener(this);
        add(b2);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setSize(450, 700);
        setLocation(800, 50);
        setUndecorated(true);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String msg = t1.getText();
            if (!msg.isEmpty()) {
                a2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                a2.setText(a2.getText() + "\n" + msg);
                long u = (long) Math.ceil((double) msg.length() / 20);
                for (long i = 0; i < u; i++) {
                    a1.setText(a1.getText() + "\n");
                }
                ous.writeUTF(msg);
            }
            t1.setText("");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Client();


        try {
            socket = new Socket("127.0.0.1", 6001);

            ous = new DataOutputStream(socket.getOutputStream());
            ois = new DataInputStream(socket.getInputStream());
            while (true) {
                String sMsg = ois.readUTF();
                long z = (long) Math.ceil((double) sMsg.length() / 20);
                a1.setText(a1.getText() + "\n" + sMsg);
                for (long i = 0; i < z; i++) {
                    a2.setText(a2.getText() + "\n");
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();

        }

    }
}
