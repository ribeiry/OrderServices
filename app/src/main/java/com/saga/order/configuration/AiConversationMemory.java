package com.saga.order.configuration;

import java.util.LinkedList;

public class AiConversationMemory {

    private String summary;
    private final LinkedList<String> history;
    private final int maxHistory;

    public AiConversationMemory(int maxHistory) {
        this.history = new LinkedList<>();
        this.maxHistory = maxHistory;
    }

    public void saveSummary(String summary){
        this.summary = summary;
    }

    public boolean hasSummary(){
        return this.summary != null && !this.summary.isEmpty() ;

    }

    public  void  addInteraction(String interaction){
        history.add(interaction);
        if(history.size() > maxHistory){
            history.removeFirst();
        }
    }

    public String buildPrompt (String userQuestion){
        String historyBlock = String.join("\n",history);

        return """
        [SYSTEM]
        Você é um analista de vendas especializado em interpretar dados comerciais.

        [SUMMARY]
        %s

        [HISTORY]
        %s

        [USER]
        %s
        """.formatted(
                summary != null ? summary : "Nenhum resumo disponível.",
                historyBlock.isEmpty() ? "(sem histórico recente)" : historyBlock,
                userQuestion
        );
    }

    public void reset(){
        this.summary = null;
        this.history.clear();
    }
}
