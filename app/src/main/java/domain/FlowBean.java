package domain;

/**
 * Created by cuizehui on 2016/5/28.
 */
public class FlowBean {
    String apkName;
    long sendflow;
    long downflow;
    int  apkuid;

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public long getSendflow() {
        return sendflow;
    }

    public void setSendflow(long sendflow) {
        this.sendflow = sendflow;
    }

    public long getDownflow() {
        return downflow;
    }

    public void setDownflow(long downflow) {
        this.downflow = downflow;
    }

    public int getApkuid() {
        return apkuid;
    }

    public void setApkuid(int apkuid) {
        this.apkuid = apkuid;
    }

}
