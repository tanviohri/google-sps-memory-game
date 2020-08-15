package com.google.sps.data;

import javafx.util.Pair; 
import java.util.ArrayList; 

class Game{

	private final int n = 5;
	private final int m = 6;

	private int[][] board = new int[n][m];
	private boolean[][] currentBoard = new boolean[n][m];

	private Team red;
	private Team blue;

	private HashMap<User, TeamMember> userToTeamMember;

	private ArrayList< Pair <int, int> > moves;

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
		if(red.getSize() > blue.getSize){
			TeamMember teamMember = new TeamMember(user, nickName, "Blue");
			blue.add(teamMember);
			userToTeamMember.put(user, teamMember);
		}else{
			TeamMember teamMember = new TeamMember(user, nickNamem "Red");
			red.add(teamMember);
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
		moves.add(new Pair <int, int> (row, col));
		currentBoard[row][col] = !currentBoard[row][col];
	}

	private getTileId(Pair <int, int> p){
		return board[p.getKey()][p.getValue()];
	}

	private undoMove(Pair <int, int> p){
		currentBoard[row][col] = !currentBoard[row][col];	
	}

	public int checkForSameTiles(){
		if(getTileId(moves.get(0)) == getTileId(moves.get(1))){
			moves.clear();
			return 1;
		}else{
			undoMove(moves.get(0));
			undoMove(moves.get(1));
			moves.clear();
			return 0;
		}
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

		if(this.board.equals(that.board) && this.currentBoard.equals(that.currentBoard) && this.red.equals(that.red) && this.blue.equals(that.blue) && this.userToTeamMember.equals(that.userToTeamMember) && this.moves.equals(that.moves) && this.chance.equals(that.chance) && this.messages.equals(that.messages)){
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
		result = 31 * result + chance;
		result = 31 * result + messages.hashCode();
		return result;
	}

	@Override
	public String toString(){
		return "{Board: " + board.toString() + ", CurrentBoard: " + currentBoard.toString() + ", Red: " + red.toString() + ", Blue: " + blue.toString() + ", UserToTeamMember: " + userToTeamMember.toString() + ", Moves: " + moves.toString() + ", Chance" + chance + ", Messages: " + messages.toString() + "}";
	}

}