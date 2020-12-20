package com.ato.dao;

import com.ato.dao.common.HibernateDAO;
import com.ato.model.bo.Users;
import com.ato.model.dto.UsersDTO;
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
public class UsersDAO extends HibernateDAO<Users, Long> {
    @Autowired
    private SessionFactory sessionFactory;

    public List<UsersDTO> searchUsers(UsersDTO obj) {
        Session session = sessionFactory.openSession();
        StringBuilder sql = new StringBuilder("SELECT \n" +
                "    su.id,\n" +
                "    su.name,\n" +
                "    su.fullname,\n" +
                "    su.part_image as partImage,\n" +
                "    su.or_birth_user as orBirthUser,\n" +
                "    su.mail,\n" +
                "    su.phone,\n" +
                "    su.status,\n" +
                "    GROUP_CONCAT(role.id) as roleUser " +
                "FROM\n" +
                "    users AS su left join user_role \n" +
                "    on user_role.id_user = su.id\n" +
                "    left join role \n" +
                "    on role.id = user_role.id_role " +
                " where 1 = 1 ");
        if (StringUtils.isNotEmpty(obj.getName())) {
            sql.append(" and upper(su.name) LIKE upper(:name) escape '&'  ");
        }
        if (StringUtils.isNotEmpty(obj.getFullname())) {
            sql.append(" and upper(su.fullname) LIKE upper(:fullname) escape '&'  ");
        }
        if (StringUtils.isNotEmpty(obj.getMail())) {
            sql.append(" and upper(su.mail) LIKE upper(:mail) escape '&'  ");
        }
        if (StringUtils.isNotEmpty(obj.getPhone())) {
            sql.append(" and upper(su.phone) LIKE upper(:phone) escape '&'  ");
        }
        if (obj.getStatus() != null) {
            sql.append(" and su.status = :userStatus ");
        }
        sql.append(" group by su.id ");

        StringBuilder sqlCount = new StringBuilder("SELECT COUNT(*) FROM (");
        sqlCount.append(sql.toString());
        sqlCount.append(")");
        sqlCount.append(" as objects ");

        SQLQuery query = session.createSQLQuery(sql.toString());
        SQLQuery queryCount = session.createSQLQuery(sqlCount.toString());

        query.addScalar("id", new LongType());
        query.addScalar("name", new StringType());
        query.addScalar("fullname", new StringType());
        query.addScalar("partImage", new StringType());
        query.addScalar("orBirthUser", new TimestampType());
        query.addScalar("mail", new StringType());
        query.addScalar("phone", new StringType());
        query.addScalar("status", new LongType());
        query.addScalar("roleUser", new StringType());
        query.setResultTransformer(Transformers.aliasToBean(UsersDTO.class));
        if (StringUtils.isNotEmpty(obj.getName())) {
            query.setParameter("name", "%" + ValidateUtils.validateKeySearch(obj.getName()) + "%");
            queryCount.setParameter("name", "%" + ValidateUtils.validateKeySearch(obj.getName()) + "%");
        }
        if (StringUtils.isNotEmpty(obj.getFullname())) {
            query.setParameter("fullname", "%" + ValidateUtils.validateKeySearch(obj.getFullname()) + "%");
            queryCount.setParameter("fullname", "%" + ValidateUtils.validateKeySearch(obj.getFullname()) + "%");
        }
        if (StringUtils.isNotEmpty(obj.getMail())) {
            query.setParameter("mail", "%" + ValidateUtils.validateKeySearch(obj.getMail()) + "%");
            queryCount.setParameter("mail", "%" + ValidateUtils.validateKeySearch(obj.getMail()) + "%");
        }
        if (StringUtils.isNotEmpty(obj.getPhone())) {
            query.setParameter("phone", "%" + ValidateUtils.validateKeySearch(obj.getPhone()) + "%");
            queryCount.setParameter("phone", "%" + ValidateUtils.validateKeySearch(obj.getPhone()) + "%");
        }
        if (obj.getStatus() != null) {
            query.setParameter("userStatus", obj.getStatus());
            queryCount.setParameter("userStatus", obj.getStatus());
        }
        query.setFirstResult((obj.getPage().intValue())*obj.getPageSize().intValue());
        query.setMaxResults(obj.getPageSize().intValue());
        obj.setTotalRecord(((BigInteger)queryCount.uniqueResult()).intValue());
        return query.list();
    }

    public Users searchUsersId(String userName, String email, String resetKey) {
        Session session = sessionFactory.openSession();
        StringBuilder sql = new StringBuilder("SELECT \n" +
                "    su.id,\n" +
                "    su.name,\n" +
                "    su.fullname,\n" +
                "    su.pass,\n" +
                "    su.part_image as partImage,\n" +
                "    su.or_birth_user as orBirthUser,\n" +
                "    su.mail,\n" +
                "    su.phone,\n" +
                "    su.status,\n" +
                "    su.reset_key as resetKey,\n" +
                "    su.reset_date as resetDate,\n" +
                "    su.creator,\n" +
                "    su.creation_time as creationTime\n" +
                "FROM\n" +
                "    users AS su\n" +
                "WHERE\n" +
                "    1 = 1 AND su.status = 1");
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and su.name = :userName");
        }
        if (StringUtils.isNotEmpty(email)) {
            sql.append(" and su.mail = :email ");
        }
        if (StringUtils.isNotEmpty(resetKey)) {
            sql.append(" and su.reset_key = :resetKey");
        }
        SQLQuery query = session.createSQLQuery(sql.toString());
        query.addScalar("id", new LongType());
        query.addScalar("name", new StringType());
        query.addScalar("fullname", new StringType());
        query.addScalar("pass", new StringType());
        query.addScalar("partImage", new StringType());
        query.addScalar("orBirthUser", new TimestampType());
        query.addScalar("mail", new StringType());
        query.addScalar("phone", new StringType());
        query.addScalar("status", new LongType());
        query.addScalar("resetKey", new StringType());
        query.addScalar("resetDate", new TimestampType());
        query.addScalar("creator", new StringType());
        query.addScalar("creationTime", new TimestampType());
        query.setResultTransformer(Transformers.aliasToBean(Users.class));
        if (StringUtils.isNotEmpty(userName)) {
            query.setParameter("userName", userName);
        }
        if (StringUtils.isNotEmpty(email)) {
            query.setParameter("email", email);
        }
        if (StringUtils.isNotEmpty(resetKey)) {
            query.setParameter("resetKey", resetKey);
        }
        Users u = (Users) query.uniqueResult();
        session.close();
        return u;
    }

    public void updateResertKey(UsersDTO obj){
        Session session = sessionFactory.openSession();
        StringBuilder sql = new StringBuilder("" +
                "UPDATE `ShopQuanAo`.`users` SET `pass` = :pass, `reset_key` = :key, `reset_date` = :date WHERE (`id` = :id) ");
        SQLQuery query=  session.createSQLQuery(sql.toString());
        query.setParameter("id", obj.getId());
        query.setParameter("pass", obj.getPass());
        query.setParameter("key", obj.getResetKey());
        query.setParameter("date", obj.getResetDate());
        query.executeUpdate();
        session.close();
    }
}
