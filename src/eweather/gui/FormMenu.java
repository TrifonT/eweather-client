/* 
 *  � ������� ����� ����� ����� ��� �������� �������� 
 *  ��� ������ ��� ��������� �������� ���240 ���
 *  �������� ��� ���
 *  ������� ���
 *  ��������� ��������
 *  �������������� ������
 */
package eweather.gui;

import eweather.controllers.DataController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SplashScreen;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Trifon
 */
public class FormMenu extends javax.swing.JFrame {

    /**
     * Creates new form FormMenu
     */
    public FormMenu() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnMainWeatherNow = new javax.swing.JButton();
        btnMainPrognosis = new javax.swing.JButton();
        btnMainExit = new javax.swing.JButton();
        btnMainStatistics = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(640, 440));
        setMinimumSize(new java.awt.Dimension(640, 440));
        setPreferredSize(new java.awt.Dimension(640, 440));
        getContentPane().setLayout(null);

        btnMainWeatherNow.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnMainWeatherNow.setText("� ������ ����");
        btnMainWeatherNow.setMaximumSize(new java.awt.Dimension(300, 50));
        btnMainWeatherNow.setMinimumSize(new java.awt.Dimension(300, 50));
        btnMainWeatherNow.setPreferredSize(new java.awt.Dimension(300, 50));
        btnMainWeatherNow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMainWeatherNowActionPerformed(evt);
            }
        });
        getContentPane().add(btnMainWeatherNow);
        btnMainWeatherNow.setBounds(180, 80, 280, 40);

        btnMainPrognosis.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnMainPrognosis.setText("�������� ������");
        btnMainPrognosis.setMaximumSize(new java.awt.Dimension(300, 50));
        btnMainPrognosis.setMinimumSize(new java.awt.Dimension(300, 50));
        btnMainPrognosis.setPreferredSize(new java.awt.Dimension(300, 50));
        btnMainPrognosis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMainPrognosisActionPerformed(evt);
            }
        });
        getContentPane().add(btnMainPrognosis);
        btnMainPrognosis.setBounds(180, 150, 280, 40);

        btnMainExit.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnMainExit.setText("������");
        btnMainExit.setMaximumSize(new java.awt.Dimension(300, 50));
        btnMainExit.setMinimumSize(new java.awt.Dimension(300, 50));
        btnMainExit.setPreferredSize(new java.awt.Dimension(300, 50));
        btnMainExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMainExitActionPerformed(evt);
            }
        });
        getContentPane().add(btnMainExit);
        btnMainExit.setBounds(180, 290, 280, 40);

        btnMainStatistics.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnMainStatistics.setText("����������");
        btnMainStatistics.setMaximumSize(new java.awt.Dimension(300, 50));
        btnMainStatistics.setMinimumSize(new java.awt.Dimension(300, 50));
        btnMainStatistics.setPreferredSize(new java.awt.Dimension(300, 50));
        btnMainStatistics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMainStatisticsActionPerformed(evt);
            }
        });
        getContentPane().add(btnMainStatistics);
        btnMainStatistics.setBounds(180, 220, 280, 40);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/w.gif"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 640, 430);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnMainWeatherNowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMainWeatherNowActionPerformed
        this.setVisible(false);
        FormShowCurrent f = new FormShowCurrent(this, true);
        f.setVisible(true);
        this.setVisible(true);
    }//GEN-LAST:event_btnMainWeatherNowActionPerformed

    private void btnMainExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMainExitActionPerformed
        this.setVisible(false);
        System.exit(0);
    }//GEN-LAST:event_btnMainExitActionPerformed

    private void btnMainPrognosisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMainPrognosisActionPerformed
        this.setVisible(false);
        FormShowPrognosis f = new FormShowPrognosis(this, true);
        f.setVisible(true);
        this.setVisible(true);
    }//GEN-LAST:event_btnMainPrognosisActionPerformed

    private void btnMainStatisticsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMainStatisticsActionPerformed
        this.setVisible(false);
        FormStatistics f = new FormStatistics(this, true);
        f.setVisible(true);
        this.setVisible(true);
    }//GEN-LAST:event_btnMainStatisticsActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws InterruptedException {

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        splashInit();

        DataController.initCities();
        DataController.updateCurrentWeather();
        DataController.updatePrognosisWeather();


        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("SystemLookAndFeel".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormMenu f = new FormMenu();
                f.setIcons();
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setVisible(true);
            }
        });
    }

    private static SplashScreen mySplash;
    private static Rectangle2D splashTextArea;
    private static Rectangle2D splashProgressArea;
    private static Graphics splashGraphics;
    private static Font font;

    public static void splashText(String str) {
        if (mySplash != null && mySplash.isVisible()) {

            splashGraphics.setColor(Color.LIGHT_GRAY);

            splashGraphics.setColor(Color.WHITE);
            splashGraphics.drawString(str, (int) (splashTextArea.getX() + 150), (int) (splashTextArea.getY() + 40));

            mySplash.update();
        }
    }

    private static void splashInit() {
        mySplash = SplashScreen.getSplashScreen();
        if (mySplash != null) {
            Dimension ssDim = mySplash.getSize();
            int height = ssDim.height;
            int width = ssDim.width;

            splashTextArea = new Rectangle2D.Double(15., height * 0.88, width * .45, 32.);
            splashProgressArea = new Rectangle2D.Double(width * .55, height * .92, width * .4, 12);

            splashGraphics = mySplash.createGraphics();
            font = new Font("Dialog", Font.PLAIN, 14);
            splashGraphics.setFont(font);

            splashText("�������� ���������� ����� �� ����������� � ��������� ��� ���������");
        }
    }

    private void setIcons() {
        List<Image> lstImg = new ArrayList<>();
        ImageIcon i16 = new ImageIcon(getClass().getClassLoader().getResource("resources/wicon16.png"));
        ImageIcon i24 = new ImageIcon(getClass().getClassLoader().getResource("resources/wicon24.png"));
        ImageIcon i32 = new ImageIcon(getClass().getClassLoader().getResource("resources/wicon32.png"));
        lstImg.add(i16.getImage());
        lstImg.add(i24.getImage());
        lstImg.add(i32.getImage());
        this.setIconImages(lstImg);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMainExit;
    private javax.swing.JButton btnMainPrognosis;
    private javax.swing.JButton btnMainStatistics;
    private javax.swing.JButton btnMainWeatherNow;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

}
