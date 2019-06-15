package vo;

import java.io.Serializable;

public class SysDictV implements Serializable {
    private static final long serialVersionUID = 4494010318058863302L;
    private int did;
    private String dcolumn;
    private String dpresent;
    private Integer dvalue;
    private String dmemo;

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getDcolumn() {
        return dcolumn;
    }

    public void setDcolumn(String dcolumn) {
        this.dcolumn = dcolumn;
    }

    public String getDpresent() {
        return dpresent;
    }

    public void setDpresent(String dpresent) {
        this.dpresent = dpresent;
    }

    public Integer getDvalue() {
        return dvalue;
    }

    public void setDvalue(Integer dvalue) {
        this.dvalue = dvalue;
    }

    public String getDmemo() {
        return dmemo;
    }

    public void setDmemo(String dmemo) {
        this.dmemo = dmemo;
    }
}
