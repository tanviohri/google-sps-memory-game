package com.google.sps.data;

import java.util.*; 

public interface Chat{
    public ArrayList<Message> getAllMessage();
	public void addMessage(Message message);
}