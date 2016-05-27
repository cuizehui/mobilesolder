package domain;

import android.graphics.drawable.Drawable;

/**
 * Created by cuizehui on 2016/5/21.
 */
public class ApkBean {
    String apkname;
     long apKsize;
     String packgename;

    public String getPackgename() {
        return packgename;
    }

    public void setPackgename(String packgename) {
        this.packgename = packgename;
    }

    public Boolean getApkflag() {
        return apkflag;
    }

    public void setApkflag(Boolean apkflag) {
        this.apkflag = apkflag;
    }

    Boolean apkflag;
    Drawable apkpicture;

    public Drawable getApkpicture() {
        return apkpicture;
    }

    public void setApkpicture(Drawable apkpicture) {
        this.apkpicture = apkpicture;
    }

    public long getApKsize() {
        return apKsize;
    }

    public void setApKsize(long apKsize) {
        this.apKsize = apKsize;
    }

    public String getApkname() {
        return apkname;
    }

    public void setApkname(String apkname) {
        this.apkname = apkname;
    }
}
