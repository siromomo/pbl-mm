package fudan.pbl.mm.controller.response;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class UserInfoResponse {
    public int[] ageRange;
    public int[] ageRangeNum;
    public int[] genderRange;
    public int[] genderRangeNum;

    public UserInfoResponse(){
        ageRange = new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        ageRangeNum = new int[10];
        genderRange = new int[]{0, 1};
        genderRangeNum = new int[2];
    }
}
