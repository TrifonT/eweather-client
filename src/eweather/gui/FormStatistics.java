/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eweather.gui;

import eweather.controllers.DataController;
import eweather.db.City;
import eweather.db.Weatherdata;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Trifon
 */
public class FormStatistics extends javax.swing.JDialog {

    List<City> cityList;

    /**
     * Creates new form FormCurrent
     *
     * @param parent
     * @param modal
     */
    public FormStatistics(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        cityList = DataController.getCities();
        initCombo();
        initTable();
        clearTable();
    }

    private void initTable() {
        this.jTableStats.getTableHeader().
                setPreferredSize(new Dimension(100, 40));
        this.jTableStats.setIntercellSpacing(new Dimension(20, 0));
        this.jTableStats.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    private void clearTable() {
        List<StatData> t = new ArrayList<>();
        StatTableModel wtm = new StatTableModel(t);
        jTableStats.setModel(wtm);
    }
    
    private String getCityName(Long cityId){
        String cityName = "";
        for(int i=0;i<cityList.size();i++){
            if(cityList.get(i).getId() == cityId ){
                cityName = cityList.get(i).getCityname();
                break;
            }
        }
        return cityName;
    }

    private void initCombo() {

        class ItemChangeListener implements ItemListener {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    clearTable();
                }
            }
        }
        jComboCities.addItemListener(new ItemChangeListener());
        for (int i = 0; i < cityList.size(); i++) {
            this.jComboCities.addItem(cityList.get(i).getCityname());
        }
    }

    private void showStats(List<StatData> statData) {
        StatTableModel model = new StatTableModel(statData);

        this.jTableStats.setModel(model);
        DefaultTableCellRenderer rr = new DefaultTableCellRenderer();
        rr.setHorizontalAlignment(JLabel.RIGHT);
        this.jTableStats.getColumnModel().getColumn(1).setCellRenderer(rr);
        this.jTableStats.getColumnModel().getColumn(3).setCellRenderer(rr);
        this.jTableStats.getColumnModel().getColumn(5).setCellRenderer(rr);
        this.jTableStats.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        TableColumnAdjuster tca = new TableColumnAdjuster(this.jTableStats);
        tca.setColumnDataIncluded(true);
        tca.adjustColumns();
    }

    private StatData getCityStatData(Long cityId) {
        StatData sdata = new StatData();

        sdata.MaxTemp = -1000f;
        sdata.MinTemp = 1000f;
        sdata.MaxDate = new Date();
        sdata.MinDate = sdata.MaxDate;

        List<Weatherdata> weatherdata = DataController.getAllWeatherdata(cityId);

        Float sum = 0f;
        for (int i = 0; i < weatherdata.size(); i++) {
            Weatherdata wd = weatherdata.get(i);
            if (wd.getTemp() > sdata.MaxTemp) {
                sdata.MaxTemp = wd.getTemp();
                sdata.MaxDate = wd.getDt();
            }
            if (wd.getTemp() < sdata.MinTemp) {
                sdata.MinTemp = wd.getTemp();
                sdata.MinDate = wd.getDt();
            }
            sum += wd.getTemp();
        }
        sum /= weatherdata.size();
        sdata.AveTemp = sum;
        sdata.CityName = getCityName(cityId);
        return sdata;
    }

    private Long getSelectedCityId() {

        int selIdx = this.jComboCities.getSelectedIndex() - 1;

        if (selIdx < 0 || selIdx >= cityList.size()) {
            JOptionPane.showMessageDialog(this,
                    "Δεν έχετε επιλέξει πόλη",
                    "Προειδοποίηση",
                    JOptionPane.WARNING_MESSAGE);
            return -1L;
        }
        return cityList.get(selIdx).getId();
    }

    private void showStats1City() {

        Long cityId = getSelectedCityId();
        if (cityId < 0) {
            return;
        }
        StatData statData = getCityStatData(cityId);
        List<StatData> listStatData = new ArrayList<>();
        listStatData.add(statData);
        showStats(listStatData);
    }

    private void showStatsAllCities() {
        List<StatData> listStatData = new ArrayList<>();

        cityList.forEach((City city) -> {
            StatData sdata = getCityStatData(city.getId());
            listStatData.add(sdata);
        });
        showStats(listStatData);
    }

    private void updatePrognosis() {
        DataController.updatePrognosisWeather();
        showStats1City();
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
        jTableStats = new javax.swing.JTable();
        jBTN1 = new javax.swing.JButton();
        jBTN2 = new javax.swing.JButton();
        jBTN3 = new javax.swing.JButton();
        jComboCities = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ο Καιρός Τώρα");

        jScrollPane1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jTableStats.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jTableStats.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableStats.setRowHeight(55);
        jScrollPane1.setViewportView(jTableStats);

        jBTN1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jBTN1.setText("Θερμοκρασία Min/Max");
        jBTN1.setToolTipText("Προβολή από την βάση δεδομένων των προβλέψεων 1ας ημέρας");
        jBTN1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTN1ActionPerformed(evt);
            }
        });

        jBTN2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jBTN2.setText("Θερμοκρασία ανά πόλη");
        jBTN2.setToolTipText("Πρόβλεψη καιρού 5 ημερών.");
        jBTN2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTN2ActionPerformed(evt);
            }
        });

        jBTN3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jBTN3.setText("Επιστροφή");
        jBTN3.setToolTipText("Επιστοφή στις επιλογές της κύριας φόρμας.");
        jBTN3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTN3ActionPerformed(evt);
            }
        });

        jComboCities.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Επιλέξτε μια πόλη ..." }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jBTN2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                    .addComponent(jBTN1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBTN3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboCities, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboCities, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(144, 144, 144)
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
        showStats1City();
    }//GEN-LAST:event_jBTN1ActionPerformed

    private void jBTN2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTN2ActionPerformed
        showStatsAllCities();
    }//GEN-LAST:event_jBTN2ActionPerformed

    private void jBTN3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTN3ActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jBTN3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBTN1;
    private javax.swing.JButton jBTN2;
    private javax.swing.JButton jBTN3;
    private javax.swing.JComboBox<String> jComboCities;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableStats;
    // End of variables declaration//GEN-END:variables
}
