package com.google.sps.data;

import java.util.*; 
import com.google.appengine.api.users.*;
import com.googlecode.objectify.annotation.*;
import com.google.sps.util.Pair;

@Entity
public class Game implements Chat{

    @Id private Long id;

	private final int n = 6;
	private final int m = 5;

	@Serialize private int[][] board;
	@Serialize private boolean[][] currentBoard;

	private Team red;
	private Team blue;

	private ArrayList< TeamMember > userToTeamMember;

	private ArrayList< Pair > moves;

	// true -> red, false -> blue
	private Chance chance;

	private ArrayList<Message> messages;

	public Game(){

		// Shuffling the array
		List<Integer> arr = new ArrayList<>();

		for(int i = 0; i < n * m / 2; i++){
            arr.add(i);
            arr.add(i);
		}
		Collections.shuffle(arr);

        board = new int[n][m];
        currentBoard = new boolean[n][m];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < m; j++){
				board[i][j] = arr.get(j + i * m);
				currentBoard[i][j] = false;
			}
		}

		red = new Team("Red");
		blue = new Team("Blue");
		userToTeamMember = new ArrayList<>();
		moves = new ArrayList<>();
		chance = Chance.RED;
		messages = new ArrayList<>();

	}

    public Long getId(){
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

    public TeamMember getTeamMemberFromUser(String email){
        for(TeamMember teamMember: userToTeamMember){
            if(teamMember.getEmail().equals(email)) return teamMember;
        }
        return null;
    }

	// Adding the new user to the team with less number of participants
	public void addUser(String email, String nickName){
		if(red.getSize() > blue.getSize()){
			TeamMember teamMember = new TeamMember(email, nickName, "Blue");
			blue.addTeamMember(teamMember);
			userToTeamMember.add(teamMember);
		}else{
			TeamMember teamMember = new TeamMember(email, nickName, "Red");
			red.addTeamMember(teamMember);
			userToTeamMember.add(teamMember);
		}
	}

    @Override
	public ArrayList<Message> getAllMessages(){
		return messages;
	}

    public ArrayList<Message> getAllTeamMessages(TeamMember teamMember){
        if(teamMember.getTeamName().equals("Red")){
            return red.getAllMessages();
        }else{
            return blue.getAllMessages();
        }
    }

    @Override
	public void addMessage(Message message){
		messages.add(message);
	}

    public void addMessageInTeamChat(Message message){
        if(message.getTeamName().equals("Red")){
            red.addMessage(message);
        }else{
            blue.addMessage(message);
        }
    }

	public int getNumberOfMoves(){
		return moves.size();
	}

	public void move(int row, int col){
		moves.add(new Pair(row, col));
		currentBoard[row][col] = !currentBoard[row][col];
	}

	private int getTileId(Pair p){
		return board[p.getKey()][p.getValue()];
	}

	public void undoMove(Pair p){
		currentBoard[p.getKey()][p.getValue()] = !currentBoard[p.getKey()][p.getValue()];	
	}

    public void clearMoves(){
        moves.clear();
    }

    public void undoAllMoves(){
        for(Pair p : moves){
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

    public String getChanceAsString(){
        if(chance == Chance.RED){
            return "Red";
        }else{
            return "Blue";
        }
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
		return "{Id: " + id + " Board: " + Arrays.deepToString(board) + ", CurrentBoard: " + Arrays.deepToString(currentBoard) + ", Red: " + red.toString() + ", Blue: " + blue.toString() + ", UserToTeamMember: " + userToTeamMember.toString() + ", Moves: " + moves.toString() + ", Chance" + chance + ", Messages: " + messages.toString() + "}";
	}

}