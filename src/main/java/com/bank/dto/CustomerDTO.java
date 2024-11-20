package com.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
	private int customerId;
	private String name;
	private String email;
	private String phoneNumber;
	private String createdAt;
	private String updatedAt;
}
