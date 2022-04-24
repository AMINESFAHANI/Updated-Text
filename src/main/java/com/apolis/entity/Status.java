package com.apolis.entity;

import lombok.Data;

@Data
public class Status {
  
	int authorisation;
	int training;
	int admin;
	@Override
	public String toString() {
		String auth;
		String adm;
		String train;
		
		
		if(authorisation==1)  auth="AuthorisedAdmin";
		else auth="AuthorisedOperator";
		if(admin==1) adm="DisabledOperator";
		else adm="DisabledAdmin";
		if(training==1) train="Trained";
		else train="Untrained";
		
		return "Status [authorisation=" + auth +  ", admin=" + adm + "]"+"[ training=" + training +"]";
	}
	
}
