package domain;

/**黑名单数据库封装
 * Created by cuizehui on 2016/4/29.
 */
public class BlackmanBean {
    String Phonenumber;
    int mode;

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
