package com.edu.xmu.rag.service;

import java.util.List;
import com.edu.xmu.rag.dao.bo.ChatData;

public interface IChatService {
    String toChat(String question);
    void save(String test);
    List<ChatData> search(List<List<Float>> search_vectors);
}
