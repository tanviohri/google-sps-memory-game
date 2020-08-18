package com.google.sps.data;

import com.google.appengine.api.users.*;

public class TeamMember{

	private final User user;
	private final String nickname;
	private final String teamName;

    //necessary for objectify
    public TeamMember(){
        user = null;
        nickname = "";
        teamName = "";
    }

	public TeamMember(User user, String nickname, String teamName){
		this.user = user;
		this.nickname = nickname;
		this.teamName = teamName;
	}

	public User getUser(){
		return user;
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
		return "{User: " + user.toString() + ", Nickname: " + nickname + ", TeamName: " + teamName + "}";
	}

}