package ru.codebattle.client;

import java.io.IOException;
import java.net.URISyntaxException;


public class Main {

    private static final String SERVER_ADDRESS = "http://codebattle2020final.westeurope.cloudapp.azure.com/codenjoy-contest/board/player/9ww17uji79d0kti8ibj3?code=8680971381910322929";

    public static void main(String[] args) throws URISyntaxException, IOException {
        CodeBattleClient client = new CodeBattleClient(SERVER_ADDRESS);
        client.run(gameBoard -> new Algorithm03(gameBoard).move());

        System.in.read();

        client.initiateExit();
    }
}



