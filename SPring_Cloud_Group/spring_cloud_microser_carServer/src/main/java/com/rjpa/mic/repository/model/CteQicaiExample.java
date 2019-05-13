package com.rjpa.mic.repository.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CteQicaiExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CteQicaiExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andQuuidIsNull() {
            addCriterion("quuid is null");
            return (Criteria) this;
        }

        public Criteria andQuuidIsNotNull() {
            addCriterion("quuid is not null");
            return (Criteria) this;
        }

        public Criteria andQuuidEqualTo(String value) {
            addCriterion("quuid =", value, "quuid");
            return (Criteria) this;
        }

        public Criteria andQuuidNotEqualTo(String value) {
            addCriterion("quuid <>", value, "quuid");
            return (Criteria) this;
        }

        public Criteria andQuuidGreaterThan(String value) {
            addCriterion("quuid >", value, "quuid");
            return (Criteria) this;
        }

        public Criteria andQuuidGreaterThanOrEqualTo(String value) {
            addCriterion("quuid >=", value, "quuid");
            return (Criteria) this;
        }

        public Criteria andQuuidLessThan(String value) {
            addCriterion("quuid <", value, "quuid");
            return (Criteria) this;
        }

        public Criteria andQuuidLessThanOrEqualTo(String value) {
            addCriterion("quuid <=", value, "quuid");
            return (Criteria) this;
        }

        public Criteria andQuuidLike(String value) {
            addCriterion("quuid like", value, "quuid");
            return (Criteria) this;
        }

        public Criteria andQuuidNotLike(String value) {
            addCriterion("quuid not like", value, "quuid");
            return (Criteria) this;
        }

        public Criteria andQuuidIn(List<String> values) {
            addCriterion("quuid in", values, "quuid");
            return (Criteria) this;
        }

        public Criteria andQuuidNotIn(List<String> values) {
            addCriterion("quuid not in", values, "quuid");
            return (Criteria) this;
        }

        public Criteria andQuuidBetween(String value1, String value2) {
            addCriterion("quuid between", value1, value2, "quuid");
            return (Criteria) this;
        }

        public Criteria andQuuidNotBetween(String value1, String value2) {
            addCriterion("quuid not between", value1, value2, "quuid");
            return (Criteria) this;
        }

        public Criteria andQnameIsNull() {
            addCriterion("qname is null");
            return (Criteria) this;
        }

        public Criteria andQnameIsNotNull() {
            addCriterion("qname is not null");
            return (Criteria) this;
        }

        public Criteria andQnameEqualTo(String value) {
            addCriterion("qname =", value, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameNotEqualTo(String value) {
            addCriterion("qname <>", value, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameGreaterThan(String value) {
            addCriterion("qname >", value, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameGreaterThanOrEqualTo(String value) {
            addCriterion("qname >=", value, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameLessThan(String value) {
            addCriterion("qname <", value, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameLessThanOrEqualTo(String value) {
            addCriterion("qname <=", value, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameLike(String value) {
            addCriterion("qname like", value, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameNotLike(String value) {
            addCriterion("qname not like", value, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameIn(List<String> values) {
            addCriterion("qname in", values, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameNotIn(List<String> values) {
            addCriterion("qname not in", values, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameBetween(String value1, String value2) {
            addCriterion("qname between", value1, value2, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameNotBetween(String value1, String value2) {
            addCriterion("qname not between", value1, value2, "qname");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<BigDecimal> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<BigDecimal> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andDisssellIsNull() {
            addCriterion("disssell is null");
            return (Criteria) this;
        }

        public Criteria andDisssellIsNotNull() {
            addCriterion("disssell is not null");
            return (Criteria) this;
        }

        public Criteria andDisssellEqualTo(BigDecimal value) {
            addCriterion("disssell =", value, "disssell");
            return (Criteria) this;
        }

        public Criteria andDisssellNotEqualTo(BigDecimal value) {
            addCriterion("disssell <>", value, "disssell");
            return (Criteria) this;
        }

        public Criteria andDisssellGreaterThan(BigDecimal value) {
            addCriterion("disssell >", value, "disssell");
            return (Criteria) this;
        }

        public Criteria andDisssellGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("disssell >=", value, "disssell");
            return (Criteria) this;
        }

        public Criteria andDisssellLessThan(BigDecimal value) {
            addCriterion("disssell <", value, "disssell");
            return (Criteria) this;
        }

        public Criteria andDisssellLessThanOrEqualTo(BigDecimal value) {
            addCriterion("disssell <=", value, "disssell");
            return (Criteria) this;
        }

        public Criteria andDisssellIn(List<BigDecimal> values) {
            addCriterion("disssell in", values, "disssell");
            return (Criteria) this;
        }

        public Criteria andDisssellNotIn(List<BigDecimal> values) {
            addCriterion("disssell not in", values, "disssell");
            return (Criteria) this;
        }

        public Criteria andDisssellBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("disssell between", value1, value2, "disssell");
            return (Criteria) this;
        }

        public Criteria andDisssellNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("disssell not between", value1, value2, "disssell");
            return (Criteria) this;
        }

        public Criteria andSellareaIsNull() {
            addCriterion("sellarea is null");
            return (Criteria) this;
        }

        public Criteria andSellareaIsNotNull() {
            addCriterion("sellarea is not null");
            return (Criteria) this;
        }

        public Criteria andSellareaEqualTo(String value) {
            addCriterion("sellarea =", value, "sellarea");
            return (Criteria) this;
        }

        public Criteria andSellareaNotEqualTo(String value) {
            addCriterion("sellarea <>", value, "sellarea");
            return (Criteria) this;
        }

        public Criteria andSellareaGreaterThan(String value) {
            addCriterion("sellarea >", value, "sellarea");
            return (Criteria) this;
        }

        public Criteria andSellareaGreaterThanOrEqualTo(String value) {
            addCriterion("sellarea >=", value, "sellarea");
            return (Criteria) this;
        }

        public Criteria andSellareaLessThan(String value) {
            addCriterion("sellarea <", value, "sellarea");
            return (Criteria) this;
        }

        public Criteria andSellareaLessThanOrEqualTo(String value) {
            addCriterion("sellarea <=", value, "sellarea");
            return (Criteria) this;
        }

        public Criteria andSellareaLike(String value) {
            addCriterion("sellarea like", value, "sellarea");
            return (Criteria) this;
        }

        public Criteria andSellareaNotLike(String value) {
            addCriterion("sellarea not like", value, "sellarea");
            return (Criteria) this;
        }

        public Criteria andSellareaIn(List<String> values) {
            addCriterion("sellarea in", values, "sellarea");
            return (Criteria) this;
        }

        public Criteria andSellareaNotIn(List<String> values) {
            addCriterion("sellarea not in", values, "sellarea");
            return (Criteria) this;
        }

        public Criteria andSellareaBetween(String value1, String value2) {
            addCriterion("sellarea between", value1, value2, "sellarea");
            return (Criteria) this;
        }

        public Criteria andSellareaNotBetween(String value1, String value2) {
            addCriterion("sellarea not between", value1, value2, "sellarea");
            return (Criteria) this;
        }

        public Criteria andMaketypeIsNull() {
            addCriterion("maketype is null");
            return (Criteria) this;
        }

        public Criteria andMaketypeIsNotNull() {
            addCriterion("maketype is not null");
            return (Criteria) this;
        }

        public Criteria andMaketypeEqualTo(Integer value) {
            addCriterion("maketype =", value, "maketype");
            return (Criteria) this;
        }

        public Criteria andMaketypeNotEqualTo(Integer value) {
            addCriterion("maketype <>", value, "maketype");
            return (Criteria) this;
        }

        public Criteria andMaketypeGreaterThan(Integer value) {
            addCriterion("maketype >", value, "maketype");
            return (Criteria) this;
        }

        public Criteria andMaketypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("maketype >=", value, "maketype");
            return (Criteria) this;
        }

        public Criteria andMaketypeLessThan(Integer value) {
            addCriterion("maketype <", value, "maketype");
            return (Criteria) this;
        }

        public Criteria andMaketypeLessThanOrEqualTo(Integer value) {
            addCriterion("maketype <=", value, "maketype");
            return (Criteria) this;
        }

        public Criteria andMaketypeIn(List<Integer> values) {
            addCriterion("maketype in", values, "maketype");
            return (Criteria) this;
        }

        public Criteria andMaketypeNotIn(List<Integer> values) {
            addCriterion("maketype not in", values, "maketype");
            return (Criteria) this;
        }

        public Criteria andMaketypeBetween(Integer value1, Integer value2) {
            addCriterion("maketype between", value1, value2, "maketype");
            return (Criteria) this;
        }

        public Criteria andMaketypeNotBetween(Integer value1, Integer value2) {
            addCriterion("maketype not between", value1, value2, "maketype");
            return (Criteria) this;
        }

        public Criteria andUsetypeIsNull() {
            addCriterion("usetype is null");
            return (Criteria) this;
        }

        public Criteria andUsetypeIsNotNull() {
            addCriterion("usetype is not null");
            return (Criteria) this;
        }

        public Criteria andUsetypeEqualTo(Integer value) {
            addCriterion("usetype =", value, "usetype");
            return (Criteria) this;
        }

        public Criteria andUsetypeNotEqualTo(Integer value) {
            addCriterion("usetype <>", value, "usetype");
            return (Criteria) this;
        }

        public Criteria andUsetypeGreaterThan(Integer value) {
            addCriterion("usetype >", value, "usetype");
            return (Criteria) this;
        }

        public Criteria andUsetypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("usetype >=", value, "usetype");
            return (Criteria) this;
        }

        public Criteria andUsetypeLessThan(Integer value) {
            addCriterion("usetype <", value, "usetype");
            return (Criteria) this;
        }

        public Criteria andUsetypeLessThanOrEqualTo(Integer value) {
            addCriterion("usetype <=", value, "usetype");
            return (Criteria) this;
        }

        public Criteria andUsetypeIn(List<Integer> values) {
            addCriterion("usetype in", values, "usetype");
            return (Criteria) this;
        }

        public Criteria andUsetypeNotIn(List<Integer> values) {
            addCriterion("usetype not in", values, "usetype");
            return (Criteria) this;
        }

        public Criteria andUsetypeBetween(Integer value1, Integer value2) {
            addCriterion("usetype between", value1, value2, "usetype");
            return (Criteria) this;
        }

        public Criteria andUsetypeNotBetween(Integer value1, Integer value2) {
            addCriterion("usetype not between", value1, value2, "usetype");
            return (Criteria) this;
        }

        public Criteria andPresellstuIsNull() {
            addCriterion("presellstu is null");
            return (Criteria) this;
        }

        public Criteria andPresellstuIsNotNull() {
            addCriterion("presellstu is not null");
            return (Criteria) this;
        }

        public Criteria andPresellstuEqualTo(Integer value) {
            addCriterion("presellstu =", value, "presellstu");
            return (Criteria) this;
        }

        public Criteria andPresellstuNotEqualTo(Integer value) {
            addCriterion("presellstu <>", value, "presellstu");
            return (Criteria) this;
        }

        public Criteria andPresellstuGreaterThan(Integer value) {
            addCriterion("presellstu >", value, "presellstu");
            return (Criteria) this;
        }

        public Criteria andPresellstuGreaterThanOrEqualTo(Integer value) {
            addCriterion("presellstu >=", value, "presellstu");
            return (Criteria) this;
        }

        public Criteria andPresellstuLessThan(Integer value) {
            addCriterion("presellstu <", value, "presellstu");
            return (Criteria) this;
        }

        public Criteria andPresellstuLessThanOrEqualTo(Integer value) {
            addCriterion("presellstu <=", value, "presellstu");
            return (Criteria) this;
        }

        public Criteria andPresellstuIn(List<Integer> values) {
            addCriterion("presellstu in", values, "presellstu");
            return (Criteria) this;
        }

        public Criteria andPresellstuNotIn(List<Integer> values) {
            addCriterion("presellstu not in", values, "presellstu");
            return (Criteria) this;
        }

        public Criteria andPresellstuBetween(Integer value1, Integer value2) {
            addCriterion("presellstu between", value1, value2, "presellstu");
            return (Criteria) this;
        }

        public Criteria andPresellstuNotBetween(Integer value1, Integer value2) {
            addCriterion("presellstu not between", value1, value2, "presellstu");
            return (Criteria) this;
        }

        public Criteria andPreselldateIsNull() {
            addCriterion("preselldate is null");
            return (Criteria) this;
        }

        public Criteria andPreselldateIsNotNull() {
            addCriterion("preselldate is not null");
            return (Criteria) this;
        }

        public Criteria andPreselldateEqualTo(Date value) {
            addCriterionForJDBCDate("preselldate =", value, "preselldate");
            return (Criteria) this;
        }

        public Criteria andPreselldateNotEqualTo(Date value) {
            addCriterionForJDBCDate("preselldate <>", value, "preselldate");
            return (Criteria) this;
        }

        public Criteria andPreselldateGreaterThan(Date value) {
            addCriterionForJDBCDate("preselldate >", value, "preselldate");
            return (Criteria) this;
        }

        public Criteria andPreselldateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("preselldate >=", value, "preselldate");
            return (Criteria) this;
        }

        public Criteria andPreselldateLessThan(Date value) {
            addCriterionForJDBCDate("preselldate <", value, "preselldate");
            return (Criteria) this;
        }

        public Criteria andPreselldateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("preselldate <=", value, "preselldate");
            return (Criteria) this;
        }

        public Criteria andPreselldateIn(List<Date> values) {
            addCriterionForJDBCDate("preselldate in", values, "preselldate");
            return (Criteria) this;
        }

        public Criteria andPreselldateNotIn(List<Date> values) {
            addCriterionForJDBCDate("preselldate not in", values, "preselldate");
            return (Criteria) this;
        }

        public Criteria andPreselldateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("preselldate between", value1, value2, "preselldate");
            return (Criteria) this;
        }

        public Criteria andPreselldateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("preselldate not between", value1, value2, "preselldate");
            return (Criteria) this;
        }

        public Criteria andSelldateIsNull() {
            addCriterion("selldate is null");
            return (Criteria) this;
        }

        public Criteria andSelldateIsNotNull() {
            addCriterion("selldate is not null");
            return (Criteria) this;
        }

        public Criteria andSelldateEqualTo(Date value) {
            addCriterionForJDBCDate("selldate =", value, "selldate");
            return (Criteria) this;
        }

        public Criteria andSelldateNotEqualTo(Date value) {
            addCriterionForJDBCDate("selldate <>", value, "selldate");
            return (Criteria) this;
        }

        public Criteria andSelldateGreaterThan(Date value) {
            addCriterionForJDBCDate("selldate >", value, "selldate");
            return (Criteria) this;
        }

        public Criteria andSelldateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("selldate >=", value, "selldate");
            return (Criteria) this;
        }

        public Criteria andSelldateLessThan(Date value) {
            addCriterionForJDBCDate("selldate <", value, "selldate");
            return (Criteria) this;
        }

        public Criteria andSelldateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("selldate <=", value, "selldate");
            return (Criteria) this;
        }

        public Criteria andSelldateIn(List<Date> values) {
            addCriterionForJDBCDate("selldate in", values, "selldate");
            return (Criteria) this;
        }

        public Criteria andSelldateNotIn(List<Date> values) {
            addCriterionForJDBCDate("selldate not in", values, "selldate");
            return (Criteria) this;
        }

        public Criteria andSelldateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("selldate between", value1, value2, "selldate");
            return (Criteria) this;
        }

        public Criteria andSelldateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("selldate not between", value1, value2, "selldate");
            return (Criteria) this;
        }

        public Criteria andSellstuIsNull() {
            addCriterion("sellstu is null");
            return (Criteria) this;
        }

        public Criteria andSellstuIsNotNull() {
            addCriterion("sellstu is not null");
            return (Criteria) this;
        }

        public Criteria andSellstuEqualTo(Integer value) {
            addCriterion("sellstu =", value, "sellstu");
            return (Criteria) this;
        }

        public Criteria andSellstuNotEqualTo(Integer value) {
            addCriterion("sellstu <>", value, "sellstu");
            return (Criteria) this;
        }

        public Criteria andSellstuGreaterThan(Integer value) {
            addCriterion("sellstu >", value, "sellstu");
            return (Criteria) this;
        }

        public Criteria andSellstuGreaterThanOrEqualTo(Integer value) {
            addCriterion("sellstu >=", value, "sellstu");
            return (Criteria) this;
        }

        public Criteria andSellstuLessThan(Integer value) {
            addCriterion("sellstu <", value, "sellstu");
            return (Criteria) this;
        }

        public Criteria andSellstuLessThanOrEqualTo(Integer value) {
            addCriterion("sellstu <=", value, "sellstu");
            return (Criteria) this;
        }

        public Criteria andSellstuIn(List<Integer> values) {
            addCriterion("sellstu in", values, "sellstu");
            return (Criteria) this;
        }

        public Criteria andSellstuNotIn(List<Integer> values) {
            addCriterion("sellstu not in", values, "sellstu");
            return (Criteria) this;
        }

        public Criteria andSellstuBetween(Integer value1, Integer value2) {
            addCriterion("sellstu between", value1, value2, "sellstu");
            return (Criteria) this;
        }

        public Criteria andSellstuNotBetween(Integer value1, Integer value2) {
            addCriterion("sellstu not between", value1, value2, "sellstu");
            return (Criteria) this;
        }

        public Criteria andSysdateIsNull() {
            addCriterion("sysDate is null");
            return (Criteria) this;
        }

        public Criteria andSysdateIsNotNull() {
            addCriterion("sysDate is not null");
            return (Criteria) this;
        }

        public Criteria andSysdateEqualTo(Date value) {
            addCriterion("sysDate =", value, "sysdate");
            return (Criteria) this;
        }

        public Criteria andSysdateNotEqualTo(Date value) {
            addCriterion("sysDate <>", value, "sysdate");
            return (Criteria) this;
        }

        public Criteria andSysdateGreaterThan(Date value) {
            addCriterion("sysDate >", value, "sysdate");
            return (Criteria) this;
        }

        public Criteria andSysdateGreaterThanOrEqualTo(Date value) {
            addCriterion("sysDate >=", value, "sysdate");
            return (Criteria) this;
        }

        public Criteria andSysdateLessThan(Date value) {
            addCriterion("sysDate <", value, "sysdate");
            return (Criteria) this;
        }

        public Criteria andSysdateLessThanOrEqualTo(Date value) {
            addCriterion("sysDate <=", value, "sysdate");
            return (Criteria) this;
        }

        public Criteria andSysdateIn(List<Date> values) {
            addCriterion("sysDate in", values, "sysdate");
            return (Criteria) this;
        }

        public Criteria andSysdateNotIn(List<Date> values) {
            addCriterion("sysDate not in", values, "sysdate");
            return (Criteria) this;
        }

        public Criteria andSysdateBetween(Date value1, Date value2) {
            addCriterion("sysDate between", value1, value2, "sysdate");
            return (Criteria) this;
        }

        public Criteria andSysdateNotBetween(Date value1, Date value2) {
            addCriterion("sysDate not between", value1, value2, "sysdate");
            return (Criteria) this;
        }

        public Criteria andMemointIsNull() {
            addCriterion("memoint is null");
            return (Criteria) this;
        }

        public Criteria andMemointIsNotNull() {
            addCriterion("memoint is not null");
            return (Criteria) this;
        }

        public Criteria andMemointEqualTo(Integer value) {
            addCriterion("memoint =", value, "memoint");
            return (Criteria) this;
        }

        public Criteria andMemointNotEqualTo(Integer value) {
            addCriterion("memoint <>", value, "memoint");
            return (Criteria) this;
        }

        public Criteria andMemointGreaterThan(Integer value) {
            addCriterion("memoint >", value, "memoint");
            return (Criteria) this;
        }

        public Criteria andMemointGreaterThanOrEqualTo(Integer value) {
            addCriterion("memoint >=", value, "memoint");
            return (Criteria) this;
        }

        public Criteria andMemointLessThan(Integer value) {
            addCriterion("memoint <", value, "memoint");
            return (Criteria) this;
        }

        public Criteria andMemointLessThanOrEqualTo(Integer value) {
            addCriterion("memoint <=", value, "memoint");
            return (Criteria) this;
        }

        public Criteria andMemointIn(List<Integer> values) {
            addCriterion("memoint in", values, "memoint");
            return (Criteria) this;
        }

        public Criteria andMemointNotIn(List<Integer> values) {
            addCriterion("memoint not in", values, "memoint");
            return (Criteria) this;
        }

        public Criteria andMemointBetween(Integer value1, Integer value2) {
            addCriterion("memoint between", value1, value2, "memoint");
            return (Criteria) this;
        }

        public Criteria andMemointNotBetween(Integer value1, Integer value2) {
            addCriterion("memoint not between", value1, value2, "memoint");
            return (Criteria) this;
        }

        public Criteria andMemostrIsNull() {
            addCriterion("memostr is null");
            return (Criteria) this;
        }

        public Criteria andMemostrIsNotNull() {
            addCriterion("memostr is not null");
            return (Criteria) this;
        }

        public Criteria andMemostrEqualTo(String value) {
            addCriterion("memostr =", value, "memostr");
            return (Criteria) this;
        }

        public Criteria andMemostrNotEqualTo(String value) {
            addCriterion("memostr <>", value, "memostr");
            return (Criteria) this;
        }

        public Criteria andMemostrGreaterThan(String value) {
            addCriterion("memostr >", value, "memostr");
            return (Criteria) this;
        }

        public Criteria andMemostrGreaterThanOrEqualTo(String value) {
            addCriterion("memostr >=", value, "memostr");
            return (Criteria) this;
        }

        public Criteria andMemostrLessThan(String value) {
            addCriterion("memostr <", value, "memostr");
            return (Criteria) this;
        }

        public Criteria andMemostrLessThanOrEqualTo(String value) {
            addCriterion("memostr <=", value, "memostr");
            return (Criteria) this;
        }

        public Criteria andMemostrLike(String value) {
            addCriterion("memostr like", value, "memostr");
            return (Criteria) this;
        }

        public Criteria andMemostrNotLike(String value) {
            addCriterion("memostr not like", value, "memostr");
            return (Criteria) this;
        }

        public Criteria andMemostrIn(List<String> values) {
            addCriterion("memostr in", values, "memostr");
            return (Criteria) this;
        }

        public Criteria andMemostrNotIn(List<String> values) {
            addCriterion("memostr not in", values, "memostr");
            return (Criteria) this;
        }

        public Criteria andMemostrBetween(String value1, String value2) {
            addCriterion("memostr between", value1, value2, "memostr");
            return (Criteria) this;
        }

        public Criteria andMemostrNotBetween(String value1, String value2) {
            addCriterion("memostr not between", value1, value2, "memostr");
            return (Criteria) this;
        }

        public Criteria andSysDateIsNull() {
            addCriterion("sys_date is null");
            return (Criteria) this;
        }

        public Criteria andSysDateIsNotNull() {
            addCriterion("sys_date is not null");
            return (Criteria) this;
        }

        public Criteria andSysDateEqualTo(Date value) {
            addCriterion("sys_date =", value, "sysDate");
            return (Criteria) this;
        }

        public Criteria andSysDateNotEqualTo(Date value) {
            addCriterion("sys_date <>", value, "sysDate");
            return (Criteria) this;
        }

        public Criteria andSysDateGreaterThan(Date value) {
            addCriterion("sys_date >", value, "sysDate");
            return (Criteria) this;
        }

        public Criteria andSysDateGreaterThanOrEqualTo(Date value) {
            addCriterion("sys_date >=", value, "sysDate");
            return (Criteria) this;
        }

        public Criteria andSysDateLessThan(Date value) {
            addCriterion("sys_date <", value, "sysDate");
            return (Criteria) this;
        }

        public Criteria andSysDateLessThanOrEqualTo(Date value) {
            addCriterion("sys_date <=", value, "sysDate");
            return (Criteria) this;
        }

        public Criteria andSysDateIn(List<Date> values) {
            addCriterion("sys_date in", values, "sysDate");
            return (Criteria) this;
        }

        public Criteria andSysDateNotIn(List<Date> values) {
            addCriterion("sys_date not in", values, "sysDate");
            return (Criteria) this;
        }

        public Criteria andSysDateBetween(Date value1, Date value2) {
            addCriterion("sys_date between", value1, value2, "sysDate");
            return (Criteria) this;
        }

        public Criteria andSysDateNotBetween(Date value1, Date value2) {
            addCriterion("sys_date not between", value1, value2, "sysDate");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}