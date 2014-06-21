package controllers;

import models.Logininfo;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.signup.*;

import java.sql.SQLException;

import static play.data.Form.form;

/**
 * Created with IntelliJ IDEA.
 * User: b4uloveme
 * Date: 1/26/14
 * Time: 6:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class Signup extends Controller {

    final static Form<Logininfo> signupForm = form(Logininfo.class);

    public static Result blank(){
        return ok (
                form.render(signupForm)
        );
    }

    public static Result submit(){
        Form<Logininfo> filledForm = signupForm.bindFromRequest();
        if(filledForm.field("email").valueOr("").isEmpty()){
            filledForm.reject("email","Please type your email");
        }
        // Check repeated password
        if(!filledForm.field("passwd").valueOr("").isEmpty()) {
            if(!filledForm.field("passwd").valueOr("").equals(filledForm.field("repeatPassword").value())) {
                filledForm.reject("repeatPassword", "Password don't match");
            }
        }
        if(filledForm.hasErrors()){
            return badRequest(form.render(filledForm));
        }else{
            Logininfo created  = filledForm.get();
                      try {
                          Logininfo.save(created);
                      }catch(SQLException ex){
                          filledForm.reject("email",ex.getMessage());
                        return badRequest(form.render(filledForm));
                      }
              Logininfo persistent = Logininfo.findByEmail(created.email);
            return ok("User find by Email: " + persistent.email + " passwd " + persistent.passwd + " has successfully sign up");
        }
    }

}
