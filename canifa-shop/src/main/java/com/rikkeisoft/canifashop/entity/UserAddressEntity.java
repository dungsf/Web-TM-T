package com.rikkeisoft.canifashop.entity;
import com.rikkeisoft.canifashop.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_user_address")
public class UserAddressEntity extends BaseEntity {
    @Column(name = "city", nullable = false)
    private int city;
    @Column(name = "district", nullable = false)
    private int district;
    @Column(name = "ward", nullable = false)
    private int ward;
    @Column(name = "detail", length = 45, nullable = false)
    private String detail;
    @Column(name = "phone", length = 10, nullable = false)
    private String phone;
    @Column(name = "is_default", nullable = true)
    private boolean isDefault = Boolean.FALSE;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
