package com.google.sps.data;

import java.util.*; 

public interface Chat{
    public ArrayList<Message> getAllMessages();
	public void addMessage(Message message);
}