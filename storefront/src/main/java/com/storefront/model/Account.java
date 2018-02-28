package com.storefront.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Praneeth.dodedu
 *
 */
@Entity
@Table(name = "account")
public @Data class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6262629019779169621L;

	@Id
	private long accountId;
	private String accountName;

}
