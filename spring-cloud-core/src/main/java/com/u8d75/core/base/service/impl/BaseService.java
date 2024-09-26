package com.u8d75.core.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.u8d75.core.base.dao.BaseDao;
import com.u8d75.core.mybatis.Where;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BaseService
 *
 * @param <T>
 */
public abstract class BaseService<T> {

    /**
     * get dao
     *
     * @param <D> dao instance
     * @return dao
     */
    protected abstract <D extends BaseDao<T>> D getDao();

    /**
     * findById
     *
     * @param id primary key
     * @return T
     */
    public T findById(Long id) {
        return getDao().findById(id);
    }

    /**
     * findByIds
     *
     * @param ids primary key list
     * @return List<T>
     */
    public List<T> findByIds(List<Long> ids) {
        return getDao().findByIds(ids);
    }

    /**
     * findByParams
     *
     * @param params params
     * @return T
     */
    public T findByParams(Where<T> params) {
        return getDao().findByParams(params);
    }

    /**
     * findListByParams
     *
     * @param params params
     * @return List<T>
     */
    public List<T> findListByParams(Where<T> params) {
        return getDao().findListByParams(params);
    }

    /**
     * find page
     *
     * @param pageNum  pageNum
     * @param pageSize pageSize
     * @param params   params
     * @return PageInfo
     */
    public PageInfo<T> findPage(Integer pageNum, Integer pageSize, Where<T> params) {
        PageHelper.startPage(pageNum, pageSize); //NOSONAR
        return new PageInfo<>(getDao().findListByParams(params));
    }

    /**
     * countByParams
     *
     * @param params params
     * @return count
     */
    public long countByParams(Where<T> params) {
        return getDao().countByParams(params);
    }

    /**
     * deleteById
     *
     * @param id primary key
     * @return delete rows number
     */
    public int deleteById(@Param("id") Long id) {
        return getDao().deleteById(id);
    }

    /**
     * updateById
     *
     * @param domain database entity
     * @return update rows number
     */
    public int updateById(@Param("domain") T domain) {
        return getDao().updateById(domain);
    }

    /**
     * updateByIdSelective
     *
     * @param domain database entity
     * @return update rows number
     */
    public int updateByIdSelective(@Param("domain") T domain) {
        return getDao().updateByIdSelective(domain);
    }

    /**
     * insert
     *
     * @param domain database entity
     * @return insert rows number
     */
    public int insert(T domain) {
        return getDao().insert(domain);
    }

    /**
     * insertBatch
     *
     * @param list database entity list
     * @return insert rows number
     */
    public int insertBatch(@Param("domains") List<T> list) {
        return getDao().insertBatch(list);
    }
}
