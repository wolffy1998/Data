package com.mango.spike.module.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author mango
 * @since 2020-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String image;

    private BigDecimal price;

    private String stock;

    private String detail;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
