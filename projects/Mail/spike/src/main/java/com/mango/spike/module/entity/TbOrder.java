package com.mango.spike.module.entity;

import java.math.BigDecimal;
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
public class TbOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Long goodsId;

    private String title;

    private BigDecimal price;

    private Integer number;

    /**
     * 0：未支付，1：已支付，2：已发货，3：已完成，4：已退款
     */
    private Integer type;


}
