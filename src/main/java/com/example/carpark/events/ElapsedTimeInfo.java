package com.example.carpark.events;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ElapsedTimeInfo {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElapsedTimeInfo.class);

    private BigDecimal price;
    private int elapsedHours;

    public ElapsedTimeInfo() {
        this.price = new BigDecimal(0);
        elapsedHours = 1;
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void showTicketInfo() {
        price = price.add(BigDecimal.valueOf(2));

        LOGGER.info("Your ticket price become: {}lv. You stayed {} hour/s"
                , price, elapsedHours++);
        if (price.equals(BigDecimal.valueOf(24))) {
            price = price.subtract(BigDecimal.valueOf(24));
        }
//        new InfoForTicket().timerShow();
//        new InfoForTicket();
    }


//@Component
//public class ElapsedTimeInfo implements ApplicationListener<ServletRequestHandledEvent> {
//
//    @Override
//    @Scheduled(cron = "*/10 * * * * *")
//    public void onApplicationEvent(ServletRequestHandledEvent event) {
//        System.out.println("edno");
//        new infoForTicket();
//    }
//
//    public class infoForTicket extends JFrame {
//
//        public infoForTicket() {
//            setDefaultCloseOperation(EXIT_ON_CLOSE);
//            setSize(300, 200);
//            setLocation(100, 100);
//            JLabel label1 = new JLabel();
//            label1.setText("Your ticket price become: {} You stayed {} hour/s");
//            label1.setBounds(0, 0, 200, 50);
//            add(label1);
//
//            setVisible(true);
//        }
//    }
}
