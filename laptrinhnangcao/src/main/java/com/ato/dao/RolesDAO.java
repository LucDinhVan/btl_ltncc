package com.ato.dao;

import com.ato.dao.common.HibernateDAO;
import com.ato.model.bo.Role;
import com.ato.model.dto.RolesDTO;
import com.ato.utils.ValidateUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public class RolesDAO extends HibernateDAO<Role, Long> {
    @Autowired
    private SessionFactory sessionFactory;
    public List<RolesDTO> getAllRoles(RolesDTO obj){
        Session session = sessionFactory.openSession();
        StringBuilder sql = new StringBuilder("SELECT \n" +
                "    id, name, code, description, status, update_time updateTime\n" +
                "FROM\n" +
                "    role where 1 = 1 ");
        if (StringUtils.isNotEmpty(obj.getCode())) {
            sql.append(" and upper(code) LIKE upper(:code) escape '&'  ");
        }
        if (StringUtils.isNotEmpty(obj.getName())) {
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
        query.addScalar("name", new StringType());
        query.addScalar("code", new StringType());
        query.addScalar("description", new StringType());
        query.addScalar("status", new LongType());
        query.addScalar("updateTime", new TimestampType());
        query.setResultTransformer(Transformers.aliasToBean(RolesDTO.class));
        if (StringUtils.isNotEmpty(obj.getName())) {
            query.setParameter("name", "%" + ValidateUtils.validateKeySearch(obj.getName()) + "%");
            queryCount.setParameter("name", "%" + ValidateUtils.validateKeySearch(obj.getName()) + "%");
        }
        if (StringUtils.isNotEmpty(obj.getCode())) {
            query.setParameter("code", "%" + ValidateUtils.validateKeySearch(obj.getCode()) + "%");
            queryCount.setParameter("code", "%" + ValidateUtils.validateKeySearch(obj.getCode()) + "%");
        }
        if (obj.getStatus() != null) {
            query.setParameter("status", obj.getStatus());
            queryCount.setParameter("status", obj.getStatus());
        }
        query.setFirstResult((obj.getPage().intValue())*obj.getPageSize().intValue());
        query.setMaxResults(obj.getPageSize().intValue());
        obj.setTotalRecord(((BigInteger)queryCount.uniqueResult()).intValue());
        return query.list();
    }

//    public void insert(RolesDTO obj){
//        Session session = sessionFactory.openSession();
//        StringBuilder sql = new StringBuilder("" +
//                "INSERT INTO `ShopQuanAo`.`role` (`name`, `code`, `description`, `status`, `update_time`) VALUES (:name, :code, :description, :status, :update_time)");
//        SQLQuery query=  session.createSQLQuery(sql.toString());
//        query.setParameter("name", obj.getName());
//        query.setParameter("code", obj.getCode());
//        query.setParameter("description", obj.getDescription());
//        query.setParameter("status", obj.getStatus());
//        query.setParameter("update_time", obj.getUpdateTime());
//        query.executeUpdate();
//    }

//    public void updateRole(RolesDTO obj){
//        Session session = sessionFactory.openSession();
//        StringBuilder sql = new StringBuilder("" +
//                "UPDATE `ShopQuanAo`.`role` SET `name` = :name, `code` = :code, `description` = :description, `status` = :status,`update_time` = :update_time WHERE (`id` = :id) ");
//        SQLQuery query=  session.createSQLQuery(sql.toString());
//        query.setParameter("id", obj.getId());
//        query.setParameter("name", obj.getName());
//        query.setParameter("code", obj.getCode());
//        query.setParameter("description", obj.getDescription());
//        query.setParameter("status", obj.getStatus());
//        query.setParameter("update_time", obj.getUpdateTime());
//        query.executeUpdate();
//    }

    public void deleteRole(Long id){
        Session session = sessionFactory.openSession();
        StringBuilder sql = new StringBuilder("" +
                "DELETE FROM `ShopQuanAo`.`role` WHERE (`id` = :id) ");
        SQLQuery query=  session.createSQLQuery(sql.toString());
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
