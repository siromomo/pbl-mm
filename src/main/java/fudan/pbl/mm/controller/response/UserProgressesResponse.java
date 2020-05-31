package fudan.pbl.mm.controller.response;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class UserProgressesResponse {
    public Date[] timeRange;
    public int[] timeRangeNum;

    public UserProgressesResponse(){
        timeRange = new Date[5];
        timeRange[0] = strToDate("2020-03-01 00:00:00");
        timeRange[1] = strToDate("2020-04-01 00:00:00");
        timeRange[2] = strToDate("2020-05-01 00:00:00");
        timeRange[3] = strToDate("2020-06-01 00:00:00");
        timeRange[4] = strToDate("2020-07-01 00:00:00");
        timeRangeNum = new int[5];
    }

    public Date strToDate(String str){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            return formatter.parse(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
