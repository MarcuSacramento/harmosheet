// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import java.awt.event.ActionEvent;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Component;
import harmotab.desktop.DesktopController;
import java.awt.Dialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import javax.swing.JDialog;

public class CountDownDialog extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private JButton m_cancelButton;
    private JLabel m_messageLabel;
    private float m_countdownSteps;
    private String m_message;
    private float m_countdownStepDuration;
    private CountdownPanel m_countDownPanel;
    private boolean m_cancelled;
    
    public CountDownDialog(final String title, final String label) {
        this.m_cancelButton = null;
        this.m_messageLabel = null;
        this.m_countdownSteps = 0.0f;
        this.m_message = null;
        this.m_countdownStepDuration = 0.0f;
        this.m_countDownPanel = null;
        this.m_cancelled = false;
        this.setTitle(title);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setLocationRelativeTo(DesktopController.getInstance().getGuiWindow());
        this.m_countDownPanel = new CountdownPanel();
        this.m_cancelButton = new JButton("Cancel");
        this.m_message = "<html>" + label + "</html>";
        (this.m_messageLabel = new JLabel()).setText(this.m_message.replaceAll("%COUNTDOWN%", "..."));
        final JPanel contentPane = (JPanel)this.getContentPane();
        contentPane.setLayout(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.add(this.m_countDownPanel, "West");
        contentPane.add(this.m_cancelButton, "East");
        contentPane.add(this.m_messageLabel, "Center");
        this.m_cancelButton.addActionListener(this);
        this.pack();
    }
    
    public boolean countDown(final float steps, final float stepDuration) {
        this.m_countdownSteps = steps;
        this.m_countdownStepDuration = stepDuration;
        this.m_cancelled = false;
        this.startCountDown();
        this.setVisible(true);
        return !this.m_cancelled;
    }
    
    @Override
    public void actionPerformed(final ActionEvent event) {
        if (event.getSource() == this.m_cancelButton) {
            this.m_cancelled = true;
        }
    }
    
    private void startCountDown() {
        final Thread thread = new Thread() {
            @Override
            public void run() {
                this.setPriority(1);
                final long countdownStartTimeMs = System.currentTimeMillis();
                final float countdownDurationSec = CountDownDialog.this.m_countdownStepDuration * CountDownDialog.this.m_countdownSteps;
                final long countdownEndTimeMs = countdownStartTimeMs + (long)(countdownDurationSec * 1000.0f);
                float currentStep = 0.0f;
                while (!CountDownDialog.this.m_cancelled && currentStep < CountDownDialog.this.m_countdownSteps) {
                    final long currentTimeMs = System.currentTimeMillis();
                    final long currentDurationMs = currentTimeMs - countdownStartTimeMs;
                    final float remainingTimeSec = (countdownEndTimeMs - currentTimeMs) / 1000.0f;
                    currentStep = currentDurationMs / 1000.0f / CountDownDialog.this.m_countdownStepDuration;
                    final float fraction = remainingTimeSec / countdownDurationSec;
                    final int value = (int)(CountDownDialog.this.m_countdownSteps - currentStep) + 1;
                    CountDownDialog.this.m_countDownPanel.setValue(String.valueOf(value), value, fraction);
                    CountDownDialog.this.m_messageLabel.setText(CountDownDialog.this.m_message.replaceAll("%COUNTDOWN%", String.valueOf((int)remainingTimeSec)));
                    yield();
                }
                CountDownDialog.this.setVisible(false);
            }
        };
        thread.start();
    }
}
