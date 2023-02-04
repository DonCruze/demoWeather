package com.example.demoWeather.models;

import com.example.demoWeather.enums.ERole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends BaseEntity{

	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;
	

	
}
