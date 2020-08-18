package com.google.sps.data;

import java.util.*; 
import com.google.appengine.api.users.*;

class Game{

	private final int n = 5;
	private final int m = 6;

	private int[][] board = new int[n][m];
	private boolean[][] currentBoard = new boolean[n][m];

	private Team red;
	private Team blue;

	private HashMap<User, TeamMember> userToTeamMember;

	private ArrayList< Pair <Integer, Integer> > moves;

	// true -> red, false -> blue
	private boolean chance;

	private ArrayList<Message> messages;

	public Game(){

		// Shuffling the array
		List<Integer> arr = new ArrayList<>();

		for(int i = 1; i <= n * m / 2; i++){
			arr.add(i);
		}
		Collections.shuffle(arr);

		for(int i = 1; i <= n; i++){
			for(int j = 1; j <= m; j++){
				board[i][j] = arr.get(j - 1 + (i - 1) * m);
				currentBoard[i][j] = false;
			}
		}

		red = new Team("Red");
		blue = new Team("Blue");
		userToTeamMember = new HashMap<>();
		moves = new ArrayList<>();
		chance = true;
		messages = new ArrayList<>();

	}

	// Adding the new user to the team with less number of participants
	public void addUser(User user, String nickName){
		if(red.getSize() > blue.getSize()){
			TeamMember teamMember = new TeamMember(user, nickName, "Blue");
			blue.addTeamMember(teamMember);
			userToTeamMember.put(user, teamMember);
		}else{
			TeamMember teamMember = new TeamMember(user, nickName, "Red");
			red.addTeamMember(teamMember);
			userToTeamMember.put(user, teamMember);
		}
	}

	public ArrayList<Message> getAllMessage(){
		return messages;
	}

	public void addMessage(Message message){
		messages.add(message);
	}

	public int getNumberOfMoves(){
		return moves.size();
	}

	public void move(int row, int col){
		moves.add(new Pair <Integer, Integer> (row, col));
		currentBoard[row][col] = !currentBoard[row][col];
	}

	private int getTileId(Pair <Integer, Integer> p){
		return board[p.getKey()][p.getValue()];
	}

	public void undoMove(Pair <Integer, Integer> p){
		currentBoard[p.getKey()][p.getValue()] = !currentBoard[p.getKey()][p.getValue()];	
	}

    public void undoAllMoves(){
        for(Pair <Integer, Integer> p : moves){
            undoMove(p);
        }
        moves.clear();
    }

	public boolean checkForSameTiles(){
		return getTileId(moves.get(0)) == getTileId(moves.get(1));
	}

	public boolean getChance(){
		return chance;
	}

	public void flipChance(){
		chance = !chance;
	}

	@Override
	public boolean equals(Object o){
		if(o == null) return false;
		if(o == this) return true;
		if(!(o instanceof Game)) return false;

		Game that = (Game) o;

		if(this.board.equals(that.board) && this.currentBoard.equals(that.currentBoard) && this.red.equals(that.red) && this.blue.equals(that.blue) && this.userToTeamMember.equals(that.userToTeamMember) && this.moves.equals(that.moves) && this.chance == that.chance && this.messages.equals(that.messages)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public int hashCode(){
		int result = 17;
		result = 31 * result + board.hashCode();
		result = 31 * result + currentBoard.hashCode();
		result = 31 * result + red.hashCode();
		result = 31 * result + blue.hashCode();
		result = 31 * result + userToTeamMember.hashCode();
		result = 31 * result + moves.hashCode();
		result = 31 * result + (int)(chance ? 1 : 0);
		result = 31 * result + messages.hashCode();
		return result;
	}

	@Override
	public String toString(){
		return "{Board: " + board.toString() + ", CurrentBoard: " + currentBoard.toString() + ", Red: " + red.toString() + ", Blue: " + blue.toString() + ", UserToTeamMember: " + userToTeamMember.toString() + ", Moves: " + moves.toString() + ", Chance" + chance + ", Messages: " + messages.toString() + "}";
	}

}