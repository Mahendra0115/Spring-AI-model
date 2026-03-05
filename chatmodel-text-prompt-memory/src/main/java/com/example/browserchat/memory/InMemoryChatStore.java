package com.example.browserchat.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.messages.Message;

public class InMemoryChatStore {
	
  private static final Map <String,List<Message>> STORE = new HashMap<>();
  
   public static List<Message> getHistory(String sessionId){
	   return STORE.computeIfAbsent(sessionId, k-> new ArrayList<>());
	   
	   
   }
}
