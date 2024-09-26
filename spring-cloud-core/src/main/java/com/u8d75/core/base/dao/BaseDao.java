package com.u8d75.core.base.dao;


import com.u8d75.core.mybatis.BaseSqlProvider;
import com.u8d75.core.mybatis.Where;
import org.apache.ibatis.annotations.*;

import java.util.List;

import static com.u8d75.core.mybatis.SqlHelper.*;

/**
 * BaseDao
 *
 * @param <T>
 */
public interface BaseDao<T> {

    /**
     * findById
     *
     * @param id primary key
     * @return T
     */
    @SelectProvider(type = BaseSqlProvider.class, method = "findById")
    T findById(@Param(PK) Long id);

    /**
     * findByIds
     *
     * @param ids primary key list
     * @return List<T>
     */
    @SelectProvider(type = BaseSqlProvider.class, method = "findByIds")
    List<T> findByIds(@Param(PK) List<Long> ids);

    /**
     * findByParams
     *
     * @param params params
     * @return T
     */
    @SelectProvider(type = BaseSqlProvider.class, method = "findByParams")
    T findByParams(@Param(PARAMS) Where<T> params);

    /**
     * findListByParams
     *
     * @param params params
     * @return List<T>
     */
    @SelectProvider(type = BaseSqlProvider.class, method = "findListByParams")
    List<T> findListByParams(@Param(PARAMS) Where<T> params);

    /**
     * countByParams
     *
     * @param params params
     * @return count
     */
    @SelectProvider(type = BaseSqlProvider.class, method = "countByParams")
    long countByParams(@Param(PARAMS) Where<T> params);

    /**
     * deleteById
     *
     * @param id primary key
     * @return delete rows number
     */
    @DeleteProvider(type = BaseSqlProvider.class, method = "deleteById")
    int deleteById(@Param(PK) Long id);

    /**
     * updateById
     *
     * @param domain database entity
     * @return update rows number
     */
    @UpdateProvider(type = BaseSqlProvider.class, method = "updateById")
    int updateById(@Param(DOMAIN) T domain);

    /**
     * updateByIdSelective
     *
     * @param domain database entity
     * @return update rows number
     */
    @UpdateProvider(type = BaseSqlProvider.class, method = "updateByIdSelective")
    int updateByIdSelective(@Param(DOMAIN) T domain);

    /**
     * insert
     *
     * @param domain database entity
     * @return insert rows number
     */
    @InsertProvider(type = BaseSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = PK)
    int insert(@Param(DOMAIN) T domain);

    /**
     * insertBatch
     *
     * @param list database entity list
     * @return insert rows number
     */
    @InsertProvider(type = BaseSqlProvider.class, method = "insertBatch")
    @Options(useGeneratedKeys = true, keyProperty = PK)
    int insertBatch(@Param(DOMAIN) List<T> list);

}
