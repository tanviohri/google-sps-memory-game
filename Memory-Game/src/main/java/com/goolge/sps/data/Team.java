package com.google.sps.data;

import com.google.appengine.api.users.*;
import java.util.ArrayList;

class Team implements Chat{

	private ArrayList<TeamMember> teamMembers;
	private String teamName;
	private int score;
	private ArrayList<Message> messages;

	public Team(String teamName){
		this.teamName = teamName;
		teamMembers = new ArrayList<TeamMember>();
		score = 0;
	}

	public int getScore(){
		return score;
	}

	public String getTeamName(){
		return teamName;
	}

	public ArrayList<TeamMember> getTeamMenbers(){
		return teamMembers;
	}

	public int getSize(){
		return teamMembers.size();
	}

	public ArrayList<Message> getAllMessage(){
		return messages;
	}

	public void addMessage(Message message){
		messages.add(message);
	}

	public void addTeamMember(TeamMember teamMember){
		teamMembers.add(teamMember);
	}

	public boolean isAdmin(TeamMember teamMember){
		if(teamMembers.isEmpty()){
			return false;
		}
		return teamMember.equals(teamMembers.get(0));
	}

	public boolean belongs(TeamMember teamMember){
		return teamMembers.contains(teamMember);
	}

	public void incrementScore(){
		score++;
	}


	@Override
	public boolean equals(Object o){
		if(o == null) return false;
		if(o == this) return true;
		if(!(o instanceof Team)) return false;

		Team that = (Team) o;

		if(this.teamMembers.equals(that.teamMembers) && this.score == that.score && this.teamName.equals(that.teamName) && this.messages.equals(that.messages)){
			return true;
		}else{
			return false;
		}
	}

	@Override 
    public int hashCode(){
		int result = 17;
		result = 31 * result + teamMembers.hashCode();
		result = 31 * result + score;
		result = 31 * result + teamName.hashCode();
		result = 31 * result + messages.hashCode();
		return result;
	}

	@Override 
    public String toString(){
		return "{TeamName: " + teamName + ", Score: " + score + ", TeamMembers: " + teamMembers.toString() + ", Messages: " + messages.toString() + "}";
	}

}