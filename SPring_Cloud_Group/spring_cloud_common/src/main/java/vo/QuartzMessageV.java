package vo;

import vo.MessageAmpqV;

import java.io.Serializable;

public class QuartzMessageV extends MessageAmpqV implements Serializable {
    private static final long serialVersionUID = -6884186932882341189L;
    String cronName;
    String cronTriger;
    String cronClass;
    String cronTag;
    String cronExp;
    int cronScheduFlg;
    Long qutzTime;

    public String getCronName() {
        return cronName;
    }

    public void setCronName(String cronName) {
        this.cronName = cronName;
    }

    public String getCronTriger() {
        return cronTriger;
    }

    public void setCronTriger(String cronTriger) {
        this.cronTriger = cronTriger;
    }

    public String getCronClass() {
        return cronClass;
    }

    public void setCronClass(String cronClass) {
        this.cronClass = cronClass;
    }

    public String getCronTag() {
        return cronTag;
    }

    public void setCronTag(String cronTag) {
        this.cronTag = cronTag;
    }

    public String getCronExp() {
        return cronExp;
    }

    public void setCronExp(String cronExp) {
        this.cronExp = cronExp;
    }

    public int getCronScheduFlg() {
        return cronScheduFlg;
    }

    public void setCronScheduFlg(int cronScheduFlg) {
        this.cronScheduFlg = cronScheduFlg;
    }

    public Long getQutzTime() {
        return qutzTime;
    }

    public void setQutzTime(Long qutzTime) {
        this.qutzTime = qutzTime;
    }
}
