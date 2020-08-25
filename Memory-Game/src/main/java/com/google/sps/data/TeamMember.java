package com.google.sps.data;

import com.google.appengine.api.users.*;

public class TeamMember{

	private String email;
	private String nickname;
	private String teamName;

    //necessary for objectify
    public TeamMember(){
        email = "";
        nickname = "";
        teamName = "";
    }

	public TeamMember(String email, String nickname, String teamName){
		this.email = email;
		this.nickname = nickname;
		this.teamName = teamName;
	}

	public String getEmail(){
		return email;
	}

	public String getNickname(){
		return nickname;
	}

	public String getTeamName(){
		return teamName;
	}

	@Override
	public boolean equals(Object o){
		if(o == null) return false;
		if(o == this) return true;

		if(!(o instanceof TeamMember)) return false;

		TeamMember that = (TeamMember) o;

		return that.nickname.equals(this.nickname);
	}

	@Override
	public int hashCode(){
		return nickname.hashCode();
	}

	@Override
	public String toString(){
		return "{Email: " + email + ", Nickname: " + nickname + ", TeamName: " + teamName + "}";
	}

}