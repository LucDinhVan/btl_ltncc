package com.ato.dao;

import com.ato.dao.common.HibernateDAO;
import com.ato.model.baseModel.Logs;
import com.ato.model.dto.LogsDTO;
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
public class LogsDAO extends HibernateDAO<Logs, Long> {
    @Autowired
    private SessionFactory sessionFactory;

    public List<LogsDTO> doSearch(LogsDTO obj) {
        Session session = sessionFactory.openSession();
        StringBuilder sql = new StringBuilder("SELECT id, user_impact userImpact,code_action codeAction, content,  impact_time impactTime, ip, name_client nameClient FROM logs " +
                "  where 1 = 1 ");
        if (StringUtils.isNotEmpty(obj.getUserImpact())) {
            sql.append(" and upper(user_impact) LIKE upper(:userImpact) escape '&'  ");
        }
        if (StringUtils.isNotEmpty(obj.getCodeAction())) {
            sql.append(" and upper(code_action) LIKE upper(:codeAction) escape '&'  ");
        }
        if (StringUtils.isNotEmpty(obj.getIp())) {
            sql.append(" and upper(ip) LIKE upper(:ip) escape '&'  ");
        }
        if (obj.getImpactTime() != null) {
            sql.append(" and DATE(impact_time) = :impactTime ");
        }
        sql.append(" order by id desc ");
        StringBuilder sqlCount = new StringBuilder("SELECT COUNT(*) FROM (");
        sqlCount.append(sql.toString());
        sqlCount.append(")");
        sqlCount.append(" as roles ");
        SQLQuery query = session.createSQLQuery(sql.toString());
        SQLQuery queryCount = session.createSQLQuery(sqlCount.toString());

        query.addScalar("id", new LongType());
        query.addScalar("userImpact", new StringType());
        query.addScalar("codeAction", new StringType());
        query.addScalar("content", new StringType());
        query.addScalar("impactTime", new TimestampType());
        query.addScalar("ip", new StringType());
        query.addScalar("nameClient", new StringType());
        query.setResultTransformer(Transformers.aliasToBean(LogsDTO.class));
        if (StringUtils.isNotEmpty(obj.getUserImpact())) {
            query.setParameter("userImpact", "%" + ValidateUtils.validateKeySearch(obj.getUserImpact()) + "%");
            queryCount.setParameter("userImpact", "%" + ValidateUtils.validateKeySearch(obj.getUserImpact()) + "%");
        }
        if (StringUtils.isNotEmpty(obj.getCodeAction())) {
            query.setParameter("codeAction", "%" + ValidateUtils.validateKeySearch(obj.getCodeAction()) + "%");
            queryCount.setParameter("codeAction", "%" + ValidateUtils.validateKeySearch(obj.getCodeAction()) + "%");
        }
        if (StringUtils.isNotEmpty(obj.getIp())) {
            query.setParameter("ip", "%" + ValidateUtils.validateKeySearch(obj.getIp()) + "%");
            queryCount.setParameter("ip", "%" + ValidateUtils.validateKeySearch(obj.getIp()) + "%");
        }
        if (obj.getImpactTime() != null) {
            query.setParameter("impactTime", obj.getImpactTime());
            queryCount.setParameter("impactTime", obj.getImpactTime());
        }
        query.setFirstResult((obj.getPage().intValue()) * obj.getPageSize().intValue());
        query.setMaxResults(obj.getPageSize().intValue());
        obj.setTotalRecord(((BigInteger) queryCount.uniqueResult()).intValue());
        return query.list();
    }
}
