package hxy.dao;

import hxy.dao.model.BugPattern;
import org.rex.DB;
import org.rex.db.exception.DBException;

import java.util.List;

/**
 * @author eric
 * @program translate
 * @description
 * @date 2020/3/10
 */
public class BugPatternDao {

    public int insert(List<BugPattern> bugPatterns) {
        System.out.println("数据库开始插入数据");
        String sql = "insert into ct_two_level_details(defect_ename, for_explain, reason) values (#{bugPatthern}, #{shortDescription},  #{details})";
        int i = -1;
        try {
            for (BugPattern bugPattern :bugPatterns){
                int update = DB.update(sql, bugPattern);
                System.out.println(update);
//            DB.batchUpdate(sql, bugPatterns);
            }

        } catch (DBException e) {
            e.printStackTrace();
        }
        return i;
    }

}
