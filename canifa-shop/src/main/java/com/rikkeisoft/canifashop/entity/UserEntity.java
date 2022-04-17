package com.rikkeisoft.canifashop.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rikkeisoft.canifashop.base.BaseEntity;
import com.rikkeisoft.canifashop.common.enum_.GenderEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tbl_users")
public class UserEntity extends BaseEntity {

	@Column(length = 50, nullable = false, unique = true)
	private String username;

	@Column(name = "email", length = 255, nullable = false)
	private String email;

	@Column(name = "password", length = 255, nullable = false)
	private String password;

	@Column(name = "first_name", length = 45, nullable = true)
	private String firstName;

	@Column(name = "last_name", length = 45, nullable = true)
	private String lastName;

	@Column(name = "phone", length = 10, nullable = true)
	private String phone;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender", length = 10)
	private GenderEnum gender;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userEntity")
	private Set<OrderEntity> orderEntities;

	public void addOrderEntity(OrderEntity orderEntity) {
		this.orderEntities.add(orderEntity);
		orderEntity.setUserEntity(this);
	}

	public void deleteOrderEntity(OrderEntity orderEntity) {
		this.orderEntities.remove(orderEntity);
		orderEntity.setUserEntity(null);
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userEntity")
	private Set<UserAddressEntity> userAddressEntities;

	public void addUserAddressEntity(UserAddressEntity userAddressEntity) {
		this.userAddressEntities.add(userAddressEntity);
		userAddressEntity.setUserEntity(this);
	}

	public void deleteOrderEntity(UserAddressEntity userAddressEntity) {
		this.userAddressEntities.remove(userAddressEntity);
		userAddressEntity.setUserEntity(null);
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userEntities")
	private Set<RoleEntity> roleEntities;

	public void addRoleEntity(RoleEntity roleEntity) {
		this.roleEntities.add(roleEntity);
		roleEntity.getUserEntities().add(this);
	}

	public void deleteRoleEntity(RoleEntity roleEntity) {
		this.roleEntities.remove(roleEntity);
		roleEntity.getUserEntities().remove(this);
	}
}
