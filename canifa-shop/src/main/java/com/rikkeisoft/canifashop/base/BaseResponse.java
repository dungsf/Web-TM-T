package com.rikkeisoft.canifashop.base;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class BaseResponse implements Serializable {

	@JsonProperty("id")
	private Long id;

	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private LocalDateTime createdAt;

	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private LocalDateTime updatedAt;
	
}
