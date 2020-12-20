package com.ato.dao;

import com.ato.dao.common.HibernateDAO;
import com.ato.model.bo.Objects;
import com.ato.model.dto.ObjectsDTO;
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
public class ObjectsDAO extends HibernateDAO<Objects, Long> {
    @Autowired
    private SessionFactory sessionFactory;

    public List<ObjectsDTO> getRole(Long userID) {
        Session session = sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery
                (" SELECT DISTINCT\n" +
                        "    t1.id id,\n" +
                        "    t1.code code,\n" +
                        "    t1.name name,\n" +
                        "    t1.description AS description,\n" +
                        "    t1.status AS status,\n" +
                        "    t1.update_time AS updateTime,\n" +
                        "    t1.paren_id AS parenId,\n" +
                        "    t1.icon AS icon,\n" +
                        "    t1.path AS path\n" +
                        "FROM\n" +
                        "    (SELECT \n" +
                        "        sm.id id,\n" +
                        "            sm.code code,\n" +
                        "            sm.name name,\n" +
                        "            sm.description,\n" +
                        "            sm.status,\n" +
                        "            sm.update_time,\n" +
                        "            sm.paren_id,\n" +
                        "            sm.icon,\n" +
                        "            sm.path\n" +
                        "    FROM\n" +
                        "        object AS sm\n" +
                        "    INNER JOIN role_object AS srm ON sm.id = srm.id_object\n" +
                        "    INNER JOIN (SELECT \n" +
                        "        ur.id_role\n" +
                        "    FROM\n" +
                        "        role AS sr\n" +
                        "    INNER JOIN user_role AS ur ON sr.id = ur.id_role\n" +
                        "    INNER JOIN users AS su ON ur.id_user = su.id\n" +
                        "    WHERE\n" +
                        "        sr.status = 1 AND su.id = :userID) role_u ON role_u.id_role = srm.id_role\n" +
                        "    WHERE\n" +
                        "        sm.status = 1 and sm.type = 0 UNION SELECT \n" +
                        "        sm.id id,\n" +
                        "            sm.code code,\n" +
                        "            sm.name name,\n" +
                        "            sm.description,\n" +
                        "            sm.status,\n" +
                        "            sm.update_time,\n" +
                        "            sm.paren_id,\n" +
                        "            sm.icon,\n" +
                        "            sm.path\n" +
                        "    FROM\n" +
                        "        object AS sm\n" +
                        "    WHERE\n" +
                        "      sm.status = 1 and  sm.id IN (SELECT \n" +
                        "                paren_id\n" +
                        "            FROM\n" +
                        "                (SELECT \n" +
                        "                id,\n" +
                        "                    paren_id,\n" +
                        "                    CASE\n" +
                        "                        WHEN\n" +
                        "                            id IN (SELECT \n" +
                        "                                    sm.id\n" +
                        "                                FROM\n" +
                        "                                    object AS sm\n" +
                        "                                INNER JOIN role_object AS srm ON sm.id = srm.id_object\n" +
                        "                                INNER JOIN (SELECT \n" +
                        "                                    ur.id_role\n" +
                        "                                FROM\n" +
                        "                                    role AS sr\n" +
                        "                                INNER JOIN user_role AS ur ON sr.id = ur.id_role\n" +
                        "                                INNER JOIN users AS su ON ur.id_user = su.id\n" +
                        "                                WHERE\n" +
                        "                                    sr.status = 1 AND su.id = :userID) role_u ON role_u.id_role = srm.id_role)\n" +
                        "                        THEN\n" +
                        "                            @idlist \\:= CONCAT(IFNULL(@idlist, ''), ',', paren_id)\n" +
                        "                        WHEN FIND_IN_SET(id, @idlist) THEN @idlist \\:= CONCAT(@idlist, ',', paren_id)\n" +
                        "                    END AS checkId\n" +
                        "            FROM\n" +
                        "                object\n" +
                        "            ORDER BY id DESC) T " +
                        "            WHERE\n" +
                        "                checkId IS NOT NULL)) AS t1\n" +
                        "ORDER BY t1.id\n ");
        query.addScalar("id", new LongType());
        query.addScalar("code", new StringType());
        query.addScalar("name", new StringType());
        query.addScalar("description", new StringType());
        query.addScalar("status", new LongType());
        query.addScalar("updateTime", new TimestampType());
        query.addScalar("parenId", new LongType());
        query.addScalar("icon", new StringType());
        query.addScalar("path", new StringType());
        query.setParameter("userID", userID);
        query.setResultTransformer(Transformers.aliasToBean(ObjectsDTO.class));
        return query.list();
    }

    public List<ObjectsDTO> getRoleAction(Long userID) {
        Session session = sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery
                ("\n" +
                        "SELECT DISTINCT\n" +
                        "    t1.id as id,\n" +
                        "    t1.codeAction as codeAction,\n" +
                        "    t1.code as code,\n" +
                        "    t1.name as name,\n" +
                        "    t1.status AS status,\n" +
                        "    t1.paren_id AS parenId,\n" +
                        "    t1.icon AS icon,\n" +
                        "    t1.path AS path\n" +
                        "FROM\n" +
                        "    (SELECT DISTINCT\n" +
                        "        sm.id id,\n" +
                        "            a.code AS codeAction,\n" +
                        "            sm.code code,\n" +
                        "            sm.name name,\n" +
                        "            sm.status,\n" +
                        "            sm.paren_id,\n" +
                        "            sm.icon,\n" +
                        "            sm.path\n" +
                        "    FROM\n" +
                        "        object AS sm\n" +
                        "    INNER JOIN role_object AS srm ON sm.id = srm.id_object\n" +
                        "    INNER JOIN (SELECT \n" +
                        "        ur.id_role\n" +
                        "    FROM\n" +
                        "        role AS sr\n" +
                        "    INNER JOIN user_role AS ur ON sr.id = ur.id_role\n" +
                        "    INNER JOIN users AS su ON ur.id_user = su.id\n" +
                        "    WHERE\n" +
                        "        sr.status = 1 AND su.id = :userID) role_u ON role_u.id_role = srm.id_role\n" +
                        "    LEFT JOIN action AS a ON a.id = srm.id_action\n" +
                        "    WHERE\n" +
                        "        sm.status = 1 and sm.type = 0 and sm.paren_id in (select id from object where status = 1 ) " +
                        " UNION SELECT DISTINCT\n" +
                        "        sm.id id,\n" +
                        "            a.code AS codeAction,\n" +
                        "            sm.code code,\n" +
                        "            sm.name name,\n" +
                        "            sm.status,\n" +
                        "            sm.paren_id,\n" +
                        "            sm.icon,\n" +
                        "            sm.path\n" +
                        "    FROM\n" +
                        "        object AS sm\n" +
                        "    LEFT JOIN role_object AS srm ON sm.id = srm.id_object\n" +
                        "    LEFT JOIN action AS a ON a.id = srm.id_action\n" +
                        "    WHERE\n" +
                        "   sm.id IN (SELECT \n" +
                        "                paren_id\n" +
                        "            FROM\n" +
                        "                (SELECT \n" +
                        "                id,\n" +
                        "                    paren_id,\n" +
                        "                    CASE\n" +
                        "                        WHEN\n" +
                        "                            id IN (SELECT \n" +
                        "                                    sm.id\n" +
                        "                                FROM\n" +
                        "                                    object AS sm\n" +
                        "                                INNER JOIN role_object AS srm ON sm.id = srm.id_object\n" +
                        "                                INNER JOIN (SELECT \n" +
                        "                                    ur.id_role\n" +
                        "                                FROM\n" +
                        "                                    role AS sr\n" +
                        "                                INNER JOIN user_role AS ur ON sr.id = ur.id_role\n" +
                        "                                INNER JOIN users AS su ON ur.id_user = su.id\n" +
                        "                                WHERE\n" +
                        "                                    sr.status = 1 AND su.id = :userID) role_u ON role_u.id_role = srm.id_role)\n" +
                        "                        THEN\n" +
                        "                            @idlist \\:= CONCAT(IFNULL(@idlist, ''), ',', paren_id)\n" +
                        "                        WHEN FIND_IN_SET(id, @idlist) THEN @idlist \\:= CONCAT(@idlist, ',', paren_id)\n" +
                        "                    END AS checkId\n" +
                        "            FROM\n" +
                        "                object\n" +
                        "            ORDER BY id DESC) T " +
                        "            WHERE\n" +
                        "                checkId IS NOT NULL)) AS t1\n" +
                        "order BY t1.id ");
        query.addScalar("id", new LongType());
        query.addScalar("codeAction", new StringType());
        query.addScalar("code", new StringType());
        query.addScalar("name", new StringType());
        query.addScalar("status", new LongType());
        query.addScalar("parenId", new LongType());
        query.addScalar("icon", new StringType());
        query.addScalar("path", new StringType());
        query.setParameter("userID", userID);
        query.setResultTransformer(Transformers.aliasToBean(ObjectsDTO.class));
        return query.list();
    }

    public List<ObjectsDTO> getAllObjects(ObjectsDTO obj){
        Session session = sessionFactory.openSession();
        StringBuilder sql = new StringBuilder("SELECT id,paren_id parenId,name,code,description,status,icon,path,update_time updateTime FROM ShopQuanAo.object " +
                "  where 1 = 1 ");
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
        sqlCount.append(" as objects ");
        SQLQuery query = session.createSQLQuery(sql.toString());
        SQLQuery queryCount = session.createSQLQuery(sqlCount.toString());

        query.addScalar("id", new LongType());
        query.addScalar("parenId", new LongType());
        query.addScalar("name", new StringType());
        query.addScalar("code", new StringType());
        query.addScalar("description", new StringType());
        query.addScalar("status", new LongType());
        query.addScalar("icon", new StringType());
        query.addScalar("path", new StringType());
        query.addScalar("updateTime", new TimestampType());
        query.setResultTransformer(Transformers.aliasToBean(ObjectsDTO.class));
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

    public List<ObjectsDTO> getAllObjRoleAction(Long id) {
        Session session = sessionFactory.openSession();
        StringBuilder sql = new StringBuilder("SELECT DISTINCT\n" +
                "    a1.id, a1.paren_id parenId, a1.name, a1.code, a1.checked\n" +
                "FROM\n" +
                "    (SELECT DISTINCT\n" +
                "        object.id,\n" +
                "            object.paren_id,\n" +
                "            object.name,\n" +
                "            object.id code,\n" +
                "            CASE\n" +
                "                WHEN role_object.id_object IS NOT NULL THEN 1 \n" +
                "                ELSE 0\n" +
                "            END checked\n" +
                "    FROM\n" +
                "        object\n" +
                "    LEFT JOIN object_action AS oa ON object.id = oa.id_objects\n" +
                "    LEFT JOIN action ON action.id = oa.id_action\n" +
                "    LEFT JOIN role_object ON object.id = role_object.id_object and role_object.id_role = :idRole " +
                " UNION SELECT DISTINCT\n" +
                "        NULL,\n" +
                "            oa.id_objects,\n" +
                "            action.name,\n" +
                "            CASE\n" +
                "                WHEN oa.id_action IS NOT NULL THEN CONCAT(  oa.id_objects, '/', oa.id_action )\n" +
                "                ELSE action.id\n" +
                "            END code," +
                "            CASE\n" +
                "                WHEN\n" +
                "                    role_object.id_object IS NOT NULL\n" +
                "                        AND role_object.id_action IS NOT NULL \n" +
                "                THEN\n" +
                "                    1\n" +
                "                ELSE 0\n" +
                "            END checked\n" +
                "    FROM\n" +
                "        object_action AS oa\n" +
                "    INNER JOIN action ON action.id = oa.id_action\n" +
                "    LEFT JOIN role_object ON (action.id = role_object.id_action " +
                "    AND role_object.id_object = oa.id_objects and role_object.id_role = :idRole)" +
                " ) AS a1\n");
        SQLQuery query = session.createSQLQuery(sql.toString());
        query.addScalar("id", new LongType());
        query.addScalar("parenId", new LongType());
        query.addScalar("name", new StringType());
        query.addScalar("code", new StringType());
        query.addScalar("checked", new LongType());
        query.setResultTransformer(Transformers.aliasToBean(ObjectsDTO.class));
        query.setParameter("idRole", id);
        return query.list();
    }
}
