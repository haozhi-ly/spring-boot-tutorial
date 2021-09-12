package com.ly.entity;

import io.protostuff.Tag;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Good {
    @Tag(1)
    private Integer id;
    /*@Tag(2)
    private String gname;*/
    @Tag(3)
    private String serialnumber;
    @Tag(4)
    private Double price;
    @Tag(5)
    private Integer stockNumber;
    @Tag(6)
    private Date createTime;

    public Good(Integer id, Integer stockNumber) {
        this.id = id;
        this.stockNumber = stockNumber;
    }
}