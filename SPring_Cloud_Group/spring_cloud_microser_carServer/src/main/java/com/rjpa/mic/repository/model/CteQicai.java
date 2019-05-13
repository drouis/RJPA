package com.rjpa.mic.repository.model;

import java.math.BigDecimal;
import java.util.Date;

public class CteQicai {
    private Integer id;

    private String quuid;

    private String qname;

    private BigDecimal price;

    private BigDecimal disssell;

    private String sellarea;

    private Integer maketype;

    private Integer usetype;

    private Integer presellstu;

    private Date preselldate;

    private Date selldate;

    private Integer sellstu;

    private Date sysdate;

    private Integer memoint;

    private String memostr;

    private Date sysDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuuid() {
        return quuid;
    }

    public void setQuuid(String quuid) {
        this.quuid = quuid == null ? null : quuid.trim();
    }

    public String getQname() {
        return qname;
    }

    public void setQname(String qname) {
        this.qname = qname == null ? null : qname.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDisssell() {
        return disssell;
    }

    public void setDisssell(BigDecimal disssell) {
        this.disssell = disssell;
    }

    public String getSellarea() {
        return sellarea;
    }

    public void setSellarea(String sellarea) {
        this.sellarea = sellarea == null ? null : sellarea.trim();
    }

    public Integer getMaketype() {
        return maketype;
    }

    public void setMaketype(Integer maketype) {
        this.maketype = maketype;
    }

    public Integer getUsetype() {
        return usetype;
    }

    public void setUsetype(Integer usetype) {
        this.usetype = usetype;
    }

    public Integer getPresellstu() {
        return presellstu;
    }

    public void setPresellstu(Integer presellstu) {
        this.presellstu = presellstu;
    }

    public Date getPreselldate() {
        return preselldate;
    }

    public void setPreselldate(Date preselldate) {
        this.preselldate = preselldate;
    }

    public Date getSelldate() {
        return selldate;
    }

    public void setSelldate(Date selldate) {
        this.selldate = selldate;
    }

    public Integer getSellstu() {
        return sellstu;
    }

    public void setSellstu(Integer sellstu) {
        this.sellstu = sellstu;
    }

    public Date getSysdate() {
        return sysdate;
    }

    public void setSysdate(Date sysdate) {
        this.sysdate = sysdate;
    }

    public Integer getMemoint() {
        return memoint;
    }

    public void setMemoint(Integer memoint) {
        this.memoint = memoint;
    }

    public String getMemostr() {
        return memostr;
    }

    public void setMemostr(String memostr) {
        this.memostr = memostr == null ? null : memostr.trim();
    }

    public Date getSysDate() {
        return sysDate;
    }

    public void setSysDate(Date sysDate) {
        this.sysDate = sysDate;
    }
}