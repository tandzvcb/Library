// File: src/controller/ForgotPassController.java
package forgotPass.Controller;


import util.EmailUtil;
import user.dao.UserDao;
import user.dao.*;
import user.model.User;

public class ForgotPassController {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int OTP_LENGTH = 6;
    private UserDao userDao;

    public ForgotPassController() {
        this.userDao = new MysqlUserDao();
    }

    private String generateOTP() {
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            int index = (int) (Math.random() * CHARACTERS.length());
            otp.append(CHARACTERS.charAt(index));
        }
        return otp.toString();
    }

    public void forgotPass(String email) {
        EmailUtil emailUtil = new EmailUtil();
        String otp = generateOTP();
        emailUtil.sendEmail(email, "OTP", otp);

        User user = new User();
        user.setOtp(otp);
        userDao.updateOtp(user);
    }

    public boolean checkOTP(String email, String input) {
        String storedOtp = userDao.getOtpByEmail(email);
        return storedOtp != null && storedOtp.equals(input);
    }
}