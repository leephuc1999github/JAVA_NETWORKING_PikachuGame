package Client;

import static Client.LoginClientView.user;
import Model.DataPackage;
import Model.Game;
import Model.User;
import Model.User_Game;
import codepicachu.Algorithm;
import codepicachu.MyGraphics;
import com.google.gson.Gson;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class MyFrame extends JFrame implements ActionListener, Runnable {

    private static final long serialVersionUID = 1L;
    public int maxTime = 60;    // tg tran game
    private int time = 0;
    private int row = 10;
    private int col = 10;
    private int width = 800;
    private int height = 650;
    private JButton outGame;
    private JLabel lbScore;
    //private JProgressBar progressTime;
    private JLabel progressTime;
    private MyGraphics graphicsPanel;
    private JPanel mainPanel;
    private Algorithm algorithm;
    static ListenServer listenServer = null;
    private User_Game user_Game;
    private Game game;

    public MyFrame(Algorithm algorithm, ListenServer listenServer, User_Game user_Game, Game game) {
        this.algorithm = algorithm;
        this.listenServer = listenServer;
        this.user_Game = user_Game;
        this.game = game;
        mainPanel = createMainPanel();
        add(mainPanel);
        setTitle("Pikachu Game");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createGraphicsPanel(), BorderLayout.CENTER);
        panel.add(createControlPanel(), BorderLayout.EAST);
        panel.add(createStatusPanel(), BorderLayout.PAGE_END);
        return panel;
    }

    private JPanel createGraphicsPanel() {
        graphicsPanel = new MyGraphics(this, row, col, algorithm);
        JPanel panel = new JPanel(new GridBagLayout());
        // panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.gray);
        panel.add(graphicsPanel);
        return panel;
    }

    private JPanel createControlPanel() {
        lbScore = new JLabel("0");
        // lbTime = new JLabel("0");
        progressTime = new JLabel();
        progressTime.setText(time + "");
        outGame = new JButton("Out Game");
        outGame.addActionListener(this);
        // create panel container score and time
        JPanel panelLeft = new JPanel(new GridLayout(0, 1, 5, 5));
        panelLeft.add(new JLabel("Score:"));
        panelLeft.add(new JLabel("Time:"));

        JPanel panelCenter = new JPanel(new GridLayout(0, 1, 5, 5));
        panelCenter.add(lbScore);
        panelCenter.add(progressTime);

        JPanel panelScoreAndTime = new JPanel(new BorderLayout(5, 0));
        panelScoreAndTime.add(panelLeft, BorderLayout.WEST);
        panelScoreAndTime.add(panelCenter, BorderLayout.CENTER);

        // create panel container panelScoreAndTime and button new game
        JPanel panelControl = new JPanel(new BorderLayout(10, 10));
        panelControl.setBorder(new EmptyBorder(10, 3, 5, 3));
        panelControl.add(panelScoreAndTime, BorderLayout.CENTER);
        panelControl.add(outGame, BorderLayout.PAGE_END);
        //outGame.addActionListener(this);
        Icon icon = new ImageIcon(getClass().getResource("/icon/pokemon.png"));
//
        // use panel set Layout BorderLayout to panel control in top
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Status"));
        panel.add(panelControl, BorderLayout.PAGE_START);
        panel.add(new JLabel(icon), BorderLayout.CENTER);

        return panel;
    }

    // create status panel container author
    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(Color.lightGray);
        JLabel lbAuthor = new JLabel(user.getName());
        lbAuthor.setForeground(Color.blue);
        panel.add(lbAuthor);
        return panel;
    }

    // create a button
    private JButton createButton(String buttonName) {
        JButton btn = new JButton(buttonName);
        btn.addActionListener(this);
        return btn;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressTime.setText(time + "");
            if (time == maxTime) {
                listenServer.SendMessage(new DataPackage(user, "SendScore", this.game.getId() + "-" + this.lbScore.getText()));
                listenServer.SendMessage(new DataPackage(user, "requestGetResult", this.game.getId() + ""));
                System.out.println("send score from " + user.getName());
                this.dispose();
                RoomClientView roomClientView = new RoomClientView(listenServer, user_Game, user_Game.getHoster().getId() == user.getId() ? "bost" : "guest");
                roomClientView.setVisible(true);
                break;
            }
        }
    }

    public JButton getOutGame() {
        return outGame;
    }

    public void setOutGame(JButton outGame) {
        this.outGame = outGame;
    }

    public JLabel getLbScore() {
        return lbScore;
    }

    public void setLbScore(JLabel lbScore) {
        this.lbScore = lbScore;
    }

    public JLabel getProgressTime() {
        return progressTime;
    }

    public void setProgressTime(JLabel progressTime) {
        this.progressTime = progressTime;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(outGame)) {
            listenServer.SendMessage(new DataPackage(user, "SendScore", game.getId() + "-0"));
            listenServer.SendMessage(new DataPackage(user, "requestLeaveGame", new Gson().toJson(user_Game, User_Game.class)));
            this.dispose();
            RoomClientView roomClientView = new RoomClientView(listenServer, user_Game, user_Game.getHoster().getId() == user.getId() ? "bost" : "guest");
            roomClientView.setVisible(true);
        }
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                listenServer.SendMessage(new DataPackage(user, "SendScore", game.getId() + "-0"));
                listenServer.SendMessage(new DataPackage(user, "requestLeaveGame", ""));
                listenServer.SendMessage(new DataPackage(user, "requestLogout", new Gson().toJson(user, User.class)));
            }
        });
        
    }
   
}
