package Client;

import Model.DataPackage;
import Model.Game;
import Model.User_Game;
import codepicachu.Algorithm;
import java.io.IOException;
import javax.swing.JFrame;

public class MyMain extends JFrame implements inReceiveMessage{

    MyFrame frame;
    static ListenServer listenServer = null;
    private Game game;
    private User_Game user_Game;

    public MyMain(Algorithm algorithm, ListenServer listenServer,User_Game user_Game, Game game) {
        this.listenServer = listenServer;
        this.game = game;
        this.user_Game = user_Game;
        frame = new MyFrame(algorithm, listenServer,user_Game,game);

        MyTimeCount timeCount = new MyTimeCount();
        timeCount.start();
        new Thread(frame).start();
        
    }

    @Override
    public void ReceiveMessage(DataPackage dataPackage) throws IOException {
        
    }

    class MyTimeCount extends Thread {
        public MyTimeCount() {
        }

        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("loi");
                }
                frame.setTime(frame.getTime() + 1);
            }
        }

    }
}
