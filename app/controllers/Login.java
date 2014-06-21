package controllers;

import models.Logininfo;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login.*;

import static play.data.Form.form;

/**
 * Created with IntelliJ IDEA.
 * User: yishu4geek
 * Date: 1/20/14
 * Time: 9:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class Login extends Controller{

    final static Form<Logininfo> signinForm = form(Logininfo.class);

    public static Result blank(){
        return ok(
                form.render(signinForm)
        );
    }


    public static Result submit(){
      Form<Logininfo> filledForm = signinForm.bindFromRequest();
         if(filledForm.field("email").valueOr("").isEmpty()){
             filledForm.reject("email","Please type your email");
         }
        if(filledForm.hasErrors()){
            return badRequest(form.render(filledForm));
        }else{
            Logininfo created  = filledForm.get();

            Logininfo check = Logininfo.findByEmail(created.email);
            if(check == null || !check.passwd.equals(created.passwd)){
                filledForm.reject("email","Email or Password is invalid");
                return badRequest(form.render(filledForm));
            }

            return ok("User " + created.email + " has successfully logged in");
        }
    }


}
