package domain;

import android.graphics.drawable.Drawable;

/**
 * Created by cuizehui on 2016/5/25.
 */
public class ProgressBean {
    private boolean pisChecked;
    private Drawable picon;
    private CharSequence pname;
    private String packName;
    private long pmemSize;

    public boolean isPisChecked() {
        return pisChecked;
    }

    public void setPisChecked(boolean pisChecked) {
        this.pisChecked = pisChecked;
    }

    public Drawable getPicon() {
        return picon;
    }

    public void setPicon(Drawable picon) {
        this.picon = picon;
    }

    public CharSequence getPname() {
        return pname;
    }

    public void setPname(CharSequence pname) {
        this.pname = pname;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public long getPmemSize() {
        return pmemSize;
    }

    public void setPmemSize(long pmemSize) {
        this.pmemSize = pmemSize;
    }

    public boolean isPisSystem() {
        return pisSystem;
    }

    public void setPisSystem(boolean pisSystem) {
        this.pisSystem = pisSystem;
    }

    private boolean pisSystem;
}
