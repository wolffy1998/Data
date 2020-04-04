package com.mango.rumen.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mango.rumen.module.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mango
 * @since 2020-01-20
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * mapper名称与XML映射文件名称一致
     * 方法名对应XML映射文件中的id属性值
     * 入参对应parameterType属性指定的全类名类型仅对传入一个参数有效，出参对应resultType属性值指定的全类名类型
     * 使用@Param注解后XML映射文件中不在需要指定入参，但是取值要#{employee.id}，而指定入参可以直接取值#{id}
     * 返回类型为基础数据类型或者其包装类以及String类型时不需要指定返回值类型
     * 可以设置实体类别名包路径，这样在返回类型可以使用以小写开头的类名设置入参和返回结果的类型
     * 别名冲突时可以使用@@Alias("新别名")指定新名称，也可以在XML中或者其他配置文件中指定详情参照官网
     * #{}和${}表达式的区别，${}是直接输出，而#{}采用占位符?预编译然后传值有利于防止SQL注入
     * 比如说select * from employee where id = ${id}，id可以输出为1 and args2 = value2这样，着就是SQL注入
     * 而使用占位符就只能传一个参数值进去
     * 两者各有各的用处比如说输入表名用${}，输入参数值#{}
     * mybatis-plus提供一系列注解解决了ORM映射
     * @TableName("employee") 绑定数据表
     * @TableId(value = "id", type = IdType.AUTO) 绑定主键并类型设置自增
     * @TableField(value = "createtime", fill = FieldFill.INSERT_UPDATE) 绑定字段 策略为插入或更新自动更新
     * 其他注解参展mybatis-plus官网
     * @param employee
     * @return
     */
    public Integer saveEmployee(Employee employee);

    /**
     * 除了XML映射文件，我们也可以使用注解
     * @param id
     * @return
     */
    @Select("select * from employee where id = #{id}")
    public Employee getEmployee(Integer id);

}
