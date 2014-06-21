package models;

import com.avaje.ebean.Ebean;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yishu4geek
 * Date: 1/20/14
 * Time: 9:13 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Logininfo extends Model{
    @Id
    public long id;
    @Column(unique =true)
    @Constraints.Required
    public String email;
    @Constraints.Required
    public String passwd;

    public static void save(Logininfo user) throws SQLException {
        //check if the user is existing in db
        if(user==null) return;
        Logininfo check = findByEmail(user.email);
        if(check!=null){
            throw new SQLException("User has existed");
        } else {
            user.save();
        }
    }

    public static Logininfo findById(long id){
        return Ebean.find(Logininfo.class, id);
    }

    public static Logininfo findByEmail(String curemail){
        List<Logininfo> logininfos= Ebean.find(Logininfo.class).where().eq("email",curemail).findList();
        return logininfos==null || logininfos.size()==0?null:logininfos.get(0);
    }

}
