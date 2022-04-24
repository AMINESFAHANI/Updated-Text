package com.apolis.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
	private long id;
	private long deviceId;
	private Status status;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + (int) (id ^ (id >>> 32));
		
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
	
		if (id != other.getId())
			return false;
	
		return true;
	}

    	
	
}
