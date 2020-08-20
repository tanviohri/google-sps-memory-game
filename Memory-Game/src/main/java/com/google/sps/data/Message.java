package com.google.sps.data;

public class Message{

	private final TeamMember teamMember;
	private final long time;
	private final String text;

    //necessary for objectify
    public Message(){
        teamMember = new TeamMember();
        time = 0;
        text = "";
    }

	public Message(TeamMember teamMember, long time, String text){
		this.teamMember = teamMember;
		this.time = time;
		this.text = text;
	}

	public TeamMember getTeamMember(){
		return teamMember;
	}

	public long getTime(){
		return time;
	}

	public String getText(){
		return text;
	}

    public String getTeamName(){
        return teamMember.getTeamName();
    }

	@Override
	public boolean equals(Object o){
		if(o == null) return false;
		if(o == this) return true;
		if(!(o instanceof Message)) return false;

		Message that = (Message) o;

		if(this.teamMember.equals(that.teamMember) && this.time == that.time && this.text.equals(that.text)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public int hashCode(){
		int result = 17;
		result = 31 * result + teamMember.hashCode();
		result = 31 * result + (int)time;
		result = 31 * result + text.hashCode();
		return result;
	}

	@Override
	public String toString(){
		return "{TeamMember: " + teamMember.toString() + ", Time: " + time + ", Text: " + text + "}";
	}

}	