package com.leyou.item.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author mango
 * @Since 2020/2/11 16:03
 **/
public interface BrandMapper extends BaseMapper<Brand> {

    /**
     * 操作的这个实体与Mapper泛型不同
     * 非通用SQL需自定义SQL语句
     * 单个单数指定入参（int,String等可省），多个参数使用注解
     * @param cid
     * @param bid
     * @return
     */
    @Insert("INSERT INTO tb_category_brand(category_id, brand_id) VALUES (#{cid},#{bid})")
    public Integer insertCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    /**
     * 左表和右表内连接，那么会把两张表满足条件的数据关联起来
     * @param cid
     * @return
     */
    @Select("SELECT * FROM tb_brand a INNER JOIN tb_category_brand b ON a.id = b.brand_id")
    List<Brand> selectBrandsByCid(Long cid);
}
