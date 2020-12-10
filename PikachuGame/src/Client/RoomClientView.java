/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import static Client.LoginClientView.user;
import Model.*;
import codepicachu.Algorithm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Dell M4800
 */
public class RoomClientView extends javax.swing.JFrame implements inReceiveMessage {

    static ListenServer listenServer = null;
    static User_Game user_Game = null;
    public ArrayList<User> listUser = new ArrayList<>();
    public String position = "";
    private Game game;

    public RoomClientView(ListenServer listenServer, User_Game user_Game, String position) {
        initComponents();
        setLocationRelativeTo(null);
        this.listenServer = listenServer;
        this.listenServer.receive = this;
        this.user_Game = user_Game;
        this.position = position;
        MenuUser.setText(user.getName());
        if (position.equals("bost")) {
            try {
                listenServer.SendMessage(new DataPackage(user, "requestGetUserOnline", new Gson().toJson(user)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            loadAllUserOnline(listUser);
            loadViewBost();
        } else {
            loadViewGuest();
        }

    }

    private void loadViewBost() {
        jLabel1.setVisible(false);
        nameBost.setText(user.getName());
        statusBost.setText("Ready");
        nameGuest.setVisible(false);
        stautsGuest.setVisible(false);
        if (user_Game.getGuest() != null) {
            nameGuest.setVisible(true);
            stautsGuest.setVisible(true);
            nameGuest.setText(user_Game.getGuest().getName());
            stautsGuest.setText("Waiting ..");

        }
        jLabel9.setText("MY ROOM " + user_Game.getRoom().getName());
    }

    private void loadViewGuest() {
        jLabel1.setVisible(true);
        nameBost.setText(user_Game.getHoster().getName());
        statusBost.setText("Ready");
        scrollPane1.setVisible(false);
        nameGuest.setVisible(true);
        stautsGuest.setVisible(true);
        nameGuest.setText(user.getName());
        stautsGuest.setText("Waiting ...");
        stautsGuest.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int invite = JOptionPane.showConfirmDialog(rootPane, "are you ready ?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                switch (invite) {
                    case JOptionPane.NO_OPTION:
                        break;
                    case JOptionPane.YES_OPTION:
                        stautsGuest.setText("Ready");
                        DataPackage requestReady = new DataPackage(user, "requestReady", new Gson().toJson(user_Game.getHoster()));
                        listenServer.SendMessage(requestReady);
                        break;
                    case JOptionPane.CLOSED_OPTION:
                        break;
                    default:
                        break;
                }
            }
        });

        jButton1.setVisible(
                false);
        btnPlay.setVisible(
                false);
        jLabel9.setText(
                "MY ROOM " + user_Game.getRoom().getName());
    }

    private void loadAllUserOnline(ArrayList<User> listUser) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (int i = 0; i < listUser.size(); i++) {
            listModel.addElement(listUser.get(i).getName());
        }
        JList friends = new JList<>(listModel);
        friends.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                int invite = JOptionPane.showConfirmDialog(rootPane, "Do you want to invite " + friends.getSelectedValue().toString() + " ?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                switch (invite) {
                    case JOptionPane.NO_OPTION:
                        break;
                    case JOptionPane.YES_OPTION:
                        User guest = listUser.get(friends.getSelectedIndex());
                        try {
                            DataPackage data = new DataPackage(user, "requestInvitePlay", (guest.getId() + "-" + user_Game.getRoom().getId()));
                            listenServer.SendMessage(data);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case JOptionPane.CLOSED_OPTION:
                        break;
                    default:
                        break;
                }
            }

        });
        scrollPane1.add(friends);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jMenuItem1 = new javax.swing.JMenuItem();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnPlay = new javax.swing.JButton();
        btnCancle = new javax.swing.JButton();
        nameBost = new javax.swing.JLabel();
        nameGuest = new javax.swing.JLabel();
        statusBost = new javax.swing.JLabel();
        stautsGuest = new javax.swing.JLabel();
        panel1 = new java.awt.Panel();
        scrollPane1 = new java.awt.ScrollPane();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuUser = new javax.swing.JMenu();
        btnUpdateProfile = new javax.swing.JMenuItem();
        btnLogOut = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pikachu");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 153, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Game Picachu");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("My Room");

        btnPlay.setText("PLAY");
        btnPlay.setName("btnPlay"); // NOI18N
        btnPlay.setOpaque(false);
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        btnCancle.setText("CANCLE");
        btnCancle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancleActionPerformed(evt);
            }
        });

        nameBost.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nameBost.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nameBost.setText("Name bost");
        nameBost.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        nameGuest.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nameGuest.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nameGuest.setText("Name Guest");

        statusBost.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusBost.setText("Ready");

        stautsGuest.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stautsGuest.setText("Ready");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jButton1.setText("REFRESH");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Click Waiting ... to Ready ");

        MenuUser.setText("user");

        btnUpdateProfile.setText("Update Profile");
        btnUpdateProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateProfileActionPerformed(evt);
            }
        });
        MenuUser.add(btnUpdateProfile);

        btnLogOut.setText("Logout");
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });
        MenuUser.add(btnLogOut);

        jMenuBar1.add(MenuUser);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnCancle)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(nameBost, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                                            .addComponent(statusBost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(143, 143, 143)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(stautsGuest, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(nameGuest, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(1, 1, 1))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)))
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCancle)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameBost)
                            .addComponent(nameGuest))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(statusBost)
                            .addComponent(stautsGuest))
                        .addGap(133, 133, 133)
                        .addComponent(jLabel1)
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPlay)
                            .addComponent(jButton1)))
                    .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateProfileActionPerformed
        UpdateProfileClientView editView = new UpdateProfileClientView(listenServer);
        editView.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnUpdateProfileActionPerformed

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        User infoUserNeedLogout = user;
        DataPackage requestLogout = new DataPackage(infoUserNeedLogout, "requestLogout", new Gson().toJson(user, User.class
        ));
        listenServer.SendMessage(requestLogout);
    }//GEN-LAST:event_btnLogOutActionPerformed

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        if (user_Game.getGuest() != null && statusBost.getText().equals("Ready") && stautsGuest.getText().equals("Ready")) {
            listenServer.SendMessage(new DataPackage(user, "requestCreateGame", new Gson().toJson(user_Game)));
        } else {
            JOptionPane.showMessageDialog(rootPane, "Let's invite your friends || your friends is not ready");
        }
    }//GEN-LAST:event_btnPlayActionPerformed

    private void btnCancleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancleActionPerformed
        if (user_Game.getGuest() != null) {
            listenServer.SendMessage(new DataPackage(user, "requestLeaveRoom", new Gson().toJson(user_Game)));
        } else {
            HomeClientView homeView = new HomeClientView(listenServer);
            homeView.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnCancleActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DataPackage data = new DataPackage(user_Game.getHoster(), "requestGetUserOnline", new Gson().toJson(user_Game.getHoster()));
        listenServer.SendMessage(data);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        listenServer.SendMessage(new DataPackage(user, "requestLeaveRoom", new Gson().toJson(user_Game)));
        listenServer.SendMessage(new DataPackage(user, "requestLogout", new Gson().toJson(user, User.class)));
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RoomClientView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RoomClientView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RoomClientView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RoomClientView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RoomClientView(null, null, "").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu MenuUser;
    private javax.swing.JButton btnCancle;
    private javax.swing.JMenuItem btnLogOut;
    private javax.swing.JButton btnPlay;
    private javax.swing.JMenuItem btnUpdateProfile;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JLabel nameBost;
    private javax.swing.JLabel nameGuest;
    private java.awt.Panel panel1;
    private java.awt.ScrollPane scrollPane1;
    private javax.swing.JLabel statusBost;
    private javax.swing.JLabel stautsGuest;
    // End of variables declaration//GEN-END:variables

    @Override
    public void ReceiveMessage(DataPackage dataPackage) throws IOException {
        switch (dataPackage.getKey()) {
            case "respondGetUserOnline":
                listUser = new Gson().fromJson(dataPackage.getValue(), new TypeToken<ArrayList<User>>() {
                }.getType());
                loadAllUserOnline(listUser);
                break;
            case "respondGuestLeaveRoomSuccess":
                if (dataPackage.getValue().equals("true")) {
                    HomeClientView homeView = new HomeClientView(listenServer);
                    homeView.setVisible(true);
                    this.dispose();
                }
                break;
            case "respondBostReloadWhenGuestLeaveRoom":
                User_Game reloadViewBost = new Gson().fromJson(dataPackage.getValue(), User_Game.class);
                new RoomClientView(listenServer, reloadViewBost, "bost").setVisible(true);
                this.dispose();
                break;
            case "respondToBostReloadRoom":
                User_Game myRoom = new Gson().fromJson(dataPackage.getValue(), User_Game.class);
                new RoomClientView(listenServer, myRoom, "bost").setVisible(true);
                this.dispose();
                break;
            case "respondLogout":
                if (dataPackage.getValue()
                        .equals("true")) {
                    new LoginClientView().setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "error");
                }

                break;
            case "respondReady":
                if (dataPackage.getValue().equals("true")) {
                    stautsGuest.setText("Ready");
                }
                break;
            case "respondInforGame":
                Game game = new Gson().fromJson(dataPackage.getValue(), Game.class);
                this.game = game;
                listenServer.SendMessage(new DataPackage(user, "requestMatrixGame", ""));
                break;
            case "respondGame":
                do{
                    Algorithm renderMaxtrix = new Gson().fromJson(dataPackage.getValue(), Algorithm.class);
                    if(this.game.getId() != 0){
                        this.dispose();
                        MyMain mm = new MyMain(renderMaxtrix, listenServer,this.user_Game,this.game);
                        break;
                    }
                }while(this.game.getId() != 0);
                break;
            case "respondResultGame":
                JOptionPane.showMessageDialog(rootPane, dataPackage.getValue());
                break;
            case "":
                break;
        }
    }

}
