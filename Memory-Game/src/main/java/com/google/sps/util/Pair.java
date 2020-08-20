package com.google.sps.util;

public class Pair<U, V>
{
	public final U first; 
    public final V second;

	public Pair(U first, V second){
		this.first = first;
		this.second = second;
	}

    public U getKey(){
        return first;
    }

    public V getValue(){
        return second;
    }

	@Override
	public boolean equals(Object o){

        if(o == null) return false;
        if(o == this) return true;
		if (getClass() != o.getClass()) return false;

		Pair<?, ?> that = (Pair<?, ?>) o;
		if(this.first.equals(that.first) && this.second.equals(that.second)){
            return true;
        }else{
            return false;
        }
	}

	@Override
	public int hashCode(){
		return 31 * first.hashCode() + second.hashCode();
	}

	@Override
	public String toString(){
		return "{" + first + ", " + second + "}";
	}
}