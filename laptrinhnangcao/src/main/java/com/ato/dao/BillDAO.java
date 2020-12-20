package com.ato.dao;

import com.ato.dao.common.HibernateDAO;
import com.ato.model.bo.Bill;
import com.ato.model.dto.BillDTO;
import com.ato.utils.ValidateUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public class BillDAO extends HibernateDAO<Bill, Long> {
    @Autowired
    private SessionFactory sessionFactory;
    public List<BillDTO> getAllAction(BillDTO obj){
        Session session = sessionFactory.openSession();
        StringBuilder sql = new StringBuilder("SELECT * FROM action " +
                "  where 1 = 1 ");
        if (StringUtils.isNotEmpty(obj.getCounter())) {
            sql.append(" and upper(name) LIKE upper(:name) escape '&'  ");
        }
        if (obj.getStatus() != null) {
            sql.append(" and status = :status ");
        }
        StringBuilder sqlCount = new StringBuilder("SELECT COUNT(*) FROM (");
        sqlCount.append(sql.toString());
        sqlCount.append(")");
        sqlCount.append(" as roles ");
        SQLQuery query = session.createSQLQuery(sql.toString());
        SQLQuery queryCount = session.createSQLQuery(sqlCount.toString());

        query.addScalar("id", new LongType());
        query.addScalar("idUser", new LongType());
        query.addScalar("heavyBuying", new IntegerType());
        query.addScalar("totalBuy", new LongType());
        query.addScalar("discount", new LongType());
        query.addScalar("counter", new StringType());
        query.addScalar("payment", new LongType());
        query.addScalar("receivingAddress", new StringType());
        query.addScalar("paymentMethod", new StringType());
        query.addScalar("buyingDate", new TimestampType());
        query.setResultTransformer(Transformers.aliasToBean(BillDTO.class));
        if (StringUtils.isNotEmpty(obj.getCounter())) {
            query.setParameter("name", "%" + ValidateUtils.validateKeySearch(obj.getCounter()) + "%");
            queryCount.setParameter("name", "%" + ValidateUtils.validateKeySearch(obj.getCounter()) + "%");
        }
        if (StringUtils.isNotEmpty(obj.getCounter())) {
            query.setParameter("code", "%" + ValidateUtils.validateKeySearch(obj.getCounter()) + "%");
            queryCount.setParameter("code", "%" + ValidateUtils.validateKeySearch(obj.getCounter()) + "%");
        }
        if (obj.getStatus() != null) {
            query.setParameter("status", obj.getStatus());
            queryCount.setParameter("status", obj.getStatus());
        }
        query.setFirstResult((obj.getPage().intValue())*obj.getPageSize().intValue());
        query.setMaxResults(obj.getPageSize().intValue());
        obj.setTotalRecord(((BigInteger)queryCount.uniqueResult()).intValue());
        return query.list();    }
}
