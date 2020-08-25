package com.google.sps.util;

public class Pair
{
	public int first; 
    public int second;

    //  For Objectify
    public Pair(){
        first = 0;
        second = 0;
    }

	public Pair(int first, int second){
		this.first = first;
		this.second = second;
	}

    public int getKey(){
        return first;
    }

    public int getValue(){
        return second;
    }

	@Override
	public boolean equals(Object o){

        if(o == null) return false;
        if(o == this) return true;
		if(!(o instanceof Pair)) return false;

        Pair that = (Pair) o;
        return (first == that.first && second == that.second);
	}

	@Override
	public int hashCode(){
		return 31 * first + second;
	}

	@Override
	public String toString(){
		return "{" + first + ", " + second + "}";
	}
}