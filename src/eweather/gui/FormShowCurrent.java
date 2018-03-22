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
import eweather.db.City;
import eweather.db.Weatherdata;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import static org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.ELProperty;

/**
 *
 * @author Trifon
 */
public class FormShowCurrent extends javax.swing.JDialog {

    /**
     * Creates new form FormCurrent
     *
     * @param parent
     * @param modal
     */
    public FormShowCurrent(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        cityList = DataController.getCities();
        initList();
        initTable();
    }

    List<City> cityList;
    BindingGroup bG_list;

    private void initTable() {
        this.jTableCurrentWeather.getTableHeader().
                setPreferredSize(new Dimension(100, 40));
        this.jTableCurrentWeather.setIntercellSpacing(new Dimension(20, 0));
        this.jTableCurrentWeather.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    private void initList() {
        bG_list = new BindingGroup();
        org.jdesktop.swingbinding.JListBinding jListBinding
                = org.jdesktop.swingbinding.SwingBindings.
                        createJListBinding(READ, cityList, jListCities);
        ELProperty pr = ELProperty.create("${cityname}");
        jListBinding.setDetailBinding(pr);
        bG_list.addBinding(jListBinding);
        bG_list.bind();
    }

    List<Long> getSelectedCityIds() {
        List<Long> ids = new ArrayList<>();
        int[] selected = jListCities.getSelectedIndices();
        for (int i = 0; i < selected.length; i++) {
            ids.add(cityList.get(selected[i]).getId());
        }
        return ids;
    }

    void showWeatherNow() {
        List<Long> ids = getSelectedCityIds();
        List<Weatherdata> weatherdata = DataController.getWeatherdata(ids);
        WeatherTableModel model = new WeatherTableModel(weatherdata);

        this.jTableCurrentWeather.setModel(model);
        DefaultTableCellRenderer rr = new DefaultTableCellRenderer();
        rr.setHorizontalAlignment(JLabel.RIGHT);
        this.jTableCurrentWeather.getColumnModel().getColumn(4).setCellRenderer(rr);
        this.jTableCurrentWeather.getColumnModel().getColumn(5).setCellRenderer(rr);
        this.jTableCurrentWeather.getColumnModel().getColumn(6).setCellRenderer(rr);
        this.jTableCurrentWeather.getColumnModel().getColumn(7).setCellRenderer(rr);
        this.jTableCurrentWeather.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        TableColumnAdjuster tca = new TableColumnAdjuster(this.jTableCurrentWeather);
        tca.setColumnDataIncluded(true);
        tca.adjustColumns();
    }

    void updateWeather() {
        jTableCurrentWeather.setModel(new WeatherTableModel());

        DataController.updateCurrentWeather();
        showWeatherNow();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCurrentWeather = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListCities = new javax.swing.JList<>();
        jBTN1 = new javax.swing.JButton();
        jBTN2 = new javax.swing.JButton();
        jBTN3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("� ������ ����");

        jScrollPane1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jTableCurrentWeather.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jTableCurrentWeather.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableCurrentWeather.setIntercellSpacing(new java.awt.Dimension(5, 5));
        jTableCurrentWeather.setRowHeight(55);
        jScrollPane1.setViewportView(jTableCurrentWeather);

        jScrollPane2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jListCities.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jScrollPane2.setViewportView(jListCities);

        jBTN1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jBTN1.setText("������ ����");
        jBTN1.setToolTipText("������� ��� ��� ���� ��������� ��� ��� ��������� �������� �������� ��� ����������� ������");
        jBTN1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTN1ActionPerformed(evt);
            }
        });

        jBTN2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jBTN2.setText("��������");
        jBTN2.setToolTipText("��������� ��� ��� ��������� �������� �������� ��� ���� ��� ������ ��� �������� ��� �������� ����.");
        jBTN2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTN2ActionPerformed(evt);
            }
        });

        jBTN3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jBTN3.setText("���������");
        jBTN3.setToolTipText("�������� ���� �������� ��� ������ ������.");
        jBTN3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTN3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jBTN2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBTN1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(jBTN3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBTN1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBTN2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBTN3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jBTN1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTN1ActionPerformed
        showWeatherNow();
    }//GEN-LAST:event_jBTN1ActionPerformed

    private void jBTN2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTN2ActionPerformed
        updateWeather();
    }//GEN-LAST:event_jBTN2ActionPerformed

    private void jBTN3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTN3ActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jBTN3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBTN1;
    private javax.swing.JButton jBTN2;
    private javax.swing.JButton jBTN3;
    private javax.swing.JList<String> jListCities;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableCurrentWeather;
    // End of variables declaration//GEN-END:variables
}
