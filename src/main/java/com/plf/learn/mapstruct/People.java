package com.plf.learn.mapstruct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author panlf
 * @date 2021/10/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class People {
    private Integer id;
    private String name;
    private Integer age;
    private Date birthday;
}
