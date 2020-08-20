package com.google.sps.data;

import java.util.*; 
import com.google.appengine.api.users.*;
import com.googlecode.objectify.annotation.*;
import com.google.sps.util.Pair;

@Entity
public class Game implements Chat{

    @Id private Long id;

	private final int n = 5;
	private final int m = 6;

	private int[][] board = new int[n][m];
	private boolean[][] currentBoard = new boolean[n][m];

	private Team red;
	private Team blue;

	private HashMap<User, TeamMember> userToTeamMember;

	private ArrayList< Pair <Integer, Integer> > moves;

	// true -> red, false -> blue
	private Chance chance;

	private ArrayList<Message> messages;

	public Game(){

		// Shuffling the array
		List<Integer> arr = new ArrayList<>();

		for(int i = 1; i <= n * m / 2; i++){
            arr.add(i);
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
		chance = Chance.RED;
		messages = new ArrayList<>();

	}

    public long getId(){
        return id;
    }

    public int[][] getBoard(){
        return board;
    }

    public boolean[][] getCurrentBoard(){
        return currentBoard;
    }

    public Team getRedTeam(){
        return red;
    }

    public Team getBlueTeam(){
        return blue;
    }

    public TeamMember getTeamMemberFromUser(User user){
        return userToTeamMember.get(user);
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

    public void clearMoves(){
        moves.clear();
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

    public void incrementScore(){
        if(chance == Chance.RED){
            red.incrementScore();
        }else{
            blue.incrementScore();
        }
    }

	public Chance getChance(){
		return chance;
	}

	public void flipChance(){
		if(chance == Chance.RED){
            chance = Chance.BLUE;
        }else{
            chance = Chance.RED;
        }
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
		result = 31 * result + (int)(chance == Chance.RED ? 1 : 0);
		result = 31 * result + messages.hashCode();
		return result;
	}

	@Override
	public String toString(){
		return "{Board: " + board.toString() + ", CurrentBoard: " + currentBoard.toString() + ", Red: " + red.toString() + ", Blue: " + blue.toString() + ", UserToTeamMember: " + userToTeamMember.toString() + ", Moves: " + moves.toString() + ", Chance" + chance + ", Messages: " + messages.toString() + "}";
	}

}