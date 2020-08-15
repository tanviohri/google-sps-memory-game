package com.google.sps.data;

class Message{

	private final TeamMember teamMember;
	private final long time;
	private final String text;

	public Message(TeamMember teamMember, long time, String text){
		this.teamMember = teamMember;
		this.time = time;
		this.text = text;
	}

	public getTeamMember(){
		return teamMember;
	}

	public getTime(){
		return time;
	}

	public getText(){
		return text;
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
	public toString(){
		return "{TeamMember: " + teamMember.toString + ", Time: " + time + ", Text: " + text + "}";
	}

}	