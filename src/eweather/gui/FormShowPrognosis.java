/* 
 *  Ο κώδικας αυτός είναι μέρος της ομαδικής εργασίας 
 *  στο πλαίσο της θεματικής ενότητας ΠΛΗ240 των
 *  φοιτητών του ΕΑΠ
 *  Παυλίδη Άρη
 *  Ταφραλίδη Νικόλαου
 *  Τριανταφυλλίδη Τρύφων
 */
package eweather.gui;

import eweather.controllers.DataController;
import eweather.db.City;
import eweather.db.Weatherdata;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
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
public class FormShowPrognosis extends javax.swing.JDialog {

    /**
     * Creates new form FormCurrent
     *
     * @param parent
     * @param modal
     */
    public FormShowPrognosis(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        cityList = DataController.getCities();
        initCombo();
        initTable();
        clearTable();
    }

    List<City> cityList;

    private void initTable() {
        this.jTablePrognosis.getTableHeader().
                setPreferredSize(new Dimension(100, 40));
        this.jTablePrognosis.setIntercellSpacing(new Dimension(20, 0));
        this.jTablePrognosis.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    private void clearTable() {
        List<Weatherdata> t = new ArrayList<>();
        WeatherTableModel wtm = new WeatherTableModel(t);
        jTablePrognosis.setModel(wtm);
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

    private void showPrognosis(List<Weatherdata> weatherdata) {
        WeatherTableModel model = new WeatherTableModel(weatherdata);

        this.jTablePrognosis.setModel(model);
        DefaultTableCellRenderer rr = new DefaultTableCellRenderer();
        rr.setHorizontalAlignment(JLabel.RIGHT);
        this.jTablePrognosis.getColumnModel().getColumn(4).setCellRenderer(rr);
        this.jTablePrognosis.getColumnModel().getColumn(5).setCellRenderer(rr);
        this.jTablePrognosis.getColumnModel().getColumn(6).setCellRenderer(rr);
        this.jTablePrognosis.getColumnModel().getColumn(7).setCellRenderer(rr);
        this.jTablePrognosis.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        TableColumnAdjuster tca = new TableColumnAdjuster(this.jTablePrognosis);
        tca.setColumnDataIncluded(true);
        tca.adjustColumns();
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

    private void showPrognosis1Day() {

        Long cityId = getSelectedCityId();
        if (cityId < 0) {
            return;
        }

        Date dt_start = new Date();
        List<Weatherdata> weatherdata
                = DataController.getPrognosis1Day(cityId, dt_start);

        showPrognosis(weatherdata);
    }

    private void showPrognosis5Days() {

        Long cityId = getSelectedCityId();
        if (cityId < 0) {
            return;
        }

        Date dt_start = new Date();
        List<Weatherdata> weatherdata
                = DataController.getPrognosis5Day(cityId, dt_start);

        List<Weatherdata> filteredwd = filterPrognosis(weatherdata);

        showPrognosis(filteredwd);
    }

    private List<Weatherdata> filterPrognosis(List<Weatherdata> list) {
        ArrayList<Weatherdata> result = new ArrayList<>();
        if (list.size() > 0) {
            Weatherdata first = list.get(0);
            Calendar fcal = Calendar.getInstance();
            fcal.setTime(first.getDt());
            int today = fcal.get(Calendar.DAY_OF_MONTH);
            result.add(first);
            for (int i = 1; i < list.size(); i++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(list.get(i).getDt());
                if ((cal.get(Calendar.HOUR_OF_DAY) == 12)
                        && (cal.get(Calendar.DAY_OF_MONTH) > today)) {
                    result.add(list.get(i));
                }
            }
        }
        return result;
    }

    private void updatePrognosis() {
        DataController.updatePrognosisWeather();
        showPrognosis1Day();
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
        jTablePrognosis = new javax.swing.JTable();
        btnPrognosis1Day = new javax.swing.JButton();
        btnPrognosis5Days = new javax.swing.JButton();
        btnPrognosisReturn = new javax.swing.JButton();
        jComboCities = new javax.swing.JComboBox<>();
        btnPrognosisRefresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ο Καιρός Τώρα");

        jScrollPane1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jTablePrognosis.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jTablePrognosis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTablePrognosis.setRowHeight(55);
        jScrollPane1.setViewportView(jTablePrognosis);

        btnPrognosis1Day.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPrognosis1Day.setText("Πρόβλεψη καιρού 1ας ημέρας");
        btnPrognosis1Day.setToolTipText("Προβολή από την βάση δεδομένων των προβλέψεων 1ας ημέρας");
        btnPrognosis1Day.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrognosis1DayActionPerformed(evt);
            }
        });

        btnPrognosis5Days.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPrognosis5Days.setText("Πρόβλεψη καιρού 5 ημερών.");
        btnPrognosis5Days.setToolTipText("Πρόβλεψη καιρού 5 ημερών.");
        btnPrognosis5Days.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrognosis5DaysActionPerformed(evt);
            }
        });

        btnPrognosisReturn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPrognosisReturn.setText("Επιστροφή");
        btnPrognosisReturn.setToolTipText("Επιστοφή στις επιλογές της κύριας φόρμας.");
        btnPrognosisReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrognosisReturnActionPerformed(evt);
            }
        });

        jComboCities.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Επιλέξτε μια πόλη ..." }));

        btnPrognosisRefresh.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPrognosisRefresh.setText("Ανανέωση πρόβλεψης καιρού");
        btnPrognosisRefresh.setToolTipText("Πρόβλεψη καιρού 5 ημερών.");
        btnPrognosisRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrognosisRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnPrognosis5Days, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPrognosis1Day, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPrognosisReturn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboCities, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPrognosisRefresh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
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
                        .addComponent(btnPrognosis1Day)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrognosis5Days)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrognosisRefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrognosisReturn)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrognosis1DayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrognosis1DayActionPerformed
        showPrognosis1Day();
    }//GEN-LAST:event_btnPrognosis1DayActionPerformed

    private void btnPrognosis5DaysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrognosis5DaysActionPerformed
        showPrognosis5Days();
    }//GEN-LAST:event_btnPrognosis5DaysActionPerformed

    private void btnPrognosisReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrognosisReturnActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnPrognosisReturnActionPerformed

    private void btnPrognosisRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrognosisRefreshActionPerformed
        updatePrognosis();
    }//GEN-LAST:event_btnPrognosisRefreshActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrognosis1Day;
    private javax.swing.JButton btnPrognosis5Days;
    private javax.swing.JButton btnPrognosisRefresh;
    private javax.swing.JButton btnPrognosisReturn;
    private javax.swing.JComboBox<String> jComboCities;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePrognosis;
    // End of variables declaration//GEN-END:variables
}
