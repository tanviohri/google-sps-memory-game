package com.google.sps.data;

import com.google.appengine.api.users.*;
import java.util.ArrayList;

public class Team implements Chat{

	private ArrayList<TeamMember> teamMembers;
	private String teamName;
	private int score;
	private ArrayList<Message> messages;

    //necessary for objectify
    public Team(){
        teamMembers = new ArrayList<>();
        teamName = "";
        score = 0;
        messages = new ArrayList<>();
    }

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

	public ArrayList<TeamMember> getTeamMembers(){
		return teamMembers;
	}

    public ArrayList<String> getAllTeamMemberNicknames(){
        ArrayList<String> allNicknames = new ArrayList<>();
        for(TeamMember teamMember: teamMembers){
            allNicknames.add(teamMember.getNickname());
        }
        return allNicknames;
    }

	public int getSize(){
		return teamMembers.size();
	}

    @Override
	public ArrayList<Message> getAllMessages(){
		return messages;
	}

    @Override
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