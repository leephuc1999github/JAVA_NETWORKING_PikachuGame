package codepicachu;

import Model.User;


public class MyMain {
	MyFrame frame;
        private static User user;

	public MyMain(User user) {
                this.user = user;
		frame = new MyFrame(user);
		MyTimeCount timeCount = new MyTimeCount();
		timeCount.start();
		new Thread(frame).start();
                //frame.getLbScore().getText();  // hien thi diem só
                //System.out.println(frame.getLbScore().getText());
	}

	public static void main(String[] args) {
		MyMain main =  new MyMain(user);
	}

	class MyTimeCount extends Thread {

		public void run() {
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
                                        System.out.println("loi");
				}
				frame.setTime(frame.getTime() - 1);
				if (frame.getTime() == 0) {
					frame.showDialogNewGame(
							"Full time! Soccer of " + user.getUsername() +" : " + frame.getLbScore().getText() + "\nDo you want play again?", "Lose");
				}
			}
		}
	}
}
