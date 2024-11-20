package com.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDTO {
	private String userId;
	private int customerId;
	private String password;
	private String createdAt;
	private String updatedAt;
	private String ssn;
	private String name;
}
