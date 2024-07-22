package view.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import javax.swing.Timer;

public class Footer extends javax.swing.JPanel {

    public Footer() {
        initComponents();
        init();
    }

    private void updateTime(DateTimeFormatter format, String[] arrDate) {
        LocalTime now = LocalTime.now();
        String formatHours = now.format(format);
        hour.setText(formatHours);

        Calendar cld = Calendar.getInstance();
        String textDate = arrDate[cld.get(Calendar.DAY_OF_WEEK) - 1] + ": " + cld.get(Calendar.DATE) + "/" + (cld.get(Calendar.MONTH) + 1) + "/" + cld.get(Calendar.YEAR);
        date.setText(textDate);
    }

    private void init() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
        String[] arrDate = {"CN", "T.Hai", "T.Ba", "T.Tư", "T.Năm", "T.Sáu", "T.Bảy"};
        
        updateTime(format, arrDate);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime(format, arrDate);
            }

        });

//        bắt đầu chạy
        timer.start();
    }

//        @Override
//    protected void paintComponent(Graphics grphcs) {
//        Graphics2D g2 = (Graphics2D) grphcs;
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        GradientPaint gra = new GradientPaint(0, 0, new Color(220,237,250), getWidth(), 0, new Color(154,205, 244));
//        g2.setPaint(gra);
//        g2.fillRect(0, 0, getWidth(), getHeight());
//        super.paintComponent(grphcs);
//    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        hour = new javax.swing.JLabel();
        date = new javax.swing.JLabel();

        setOpaque(false);

        hour.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        hour.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        date.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        date.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(524, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(date, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                    .addComponent(hour, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(hour, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("50");
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel date;
    private javax.swing.JLabel hour;
    // End of variables declaration//GEN-END:variables
}
