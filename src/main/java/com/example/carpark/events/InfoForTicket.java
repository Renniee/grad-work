package com.example.carpark.events;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

public class InfoForTicket extends JFrame {

    public InfoForTicket() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocation(100, 100);
        JLabel label1 = new JLabel();
        label1.setText("Your ticket price become: {} You stayed {} hour/s");
        label1.setBounds(0, 0, 200, 50);
        add(label1);

        setVisible(true);
    }

    public void timerShow() {
        Timer timer = new Timer(5, null);
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                Calendar cal = Calendar.getInstance();

                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);
                int sec = cal.get(Calendar.SECOND);

                JOptionPane.showMessageDialog(null, "PopUp Success at " + new Date().toString());
                System.out.println("PopUp Success at " + new Date().toString());

            }
        };
//        timer.schedule(tt, 1000, 1000 * 5);
    }
}