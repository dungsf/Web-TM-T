package com.rikkeisoft.canifashop.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.Date;

public interface statisticalReponse {
    Integer getMonth();
    BigDecimal getTotal();

}

