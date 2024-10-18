package forgotPass.Test;

import forgotPass.Controller.ForgotPassController;

public class TestForgot {
    public static void main(String[] args) {
        ForgotPassController forgotPassController = new ForgotPassController();
        System.out.println(forgotPassController.checkOTP("duongtuanminh2005@gmail.com","13OG96"));
    }
}
