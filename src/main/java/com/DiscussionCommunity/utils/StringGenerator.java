package com.DiscussionCommunity.utils;

import com.DiscussionCommunity.exception.custom.InternalServerException;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Component
public class StringGenerator {
    public String RandomOtpGenerator(){
      return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }

    public String UsernameGenerator(String name){
      String randomNumber = new DecimalFormat("00").format(new Random().nextInt(99));
      return name+Long.parseLong(randomNumber);
    }

    public String getCurrentTime(){
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return time.format(formatter);
    }

    public String getDiffBetweenTwoTime(String time1, String time2) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            Date date1 = format.parse(time1);
            Date date2 = format.parse(time2);
            long difference = date2.getTime() - date1.getTime();
            long diffMinutes = difference / (60 * 1000) % 60;
            return Long.toString(diffMinutes);
        }catch (Exception e){
            throw new InternalServerException("Error while calculating time difference");
        }
    }
}
