package com.lishen.pojo;

import java.io.Serializable;

public class SysUser implements Serializable {

    private int id;

    private String username;

    private String password;

    private String salt;

    private int locked;

   

    public String getCredentialsSalt() {

        return username + salt;

    }

 

	public int getId() {

		return id;

	}

 

	public void setId(int id) {

		this.id = id;

	}

 

	public String getUsername() {

		return username;

	}

 

	public void setUsername(String username) {

		this.username = username;

	}

 

	public String getPassword() {

		return password;

	}

 

	public void setPassword(String password) {

		this.password = password;

	}

 

	public String getSalt() {

		return salt;

	}

 

	public void setSalt(String salt) {

		this.salt = salt;

	}

 

	public int getLocked() {

		return locked;

	}

 

	public void setLocked(int locked) {

		this.locked = locked;

	}

 

}
