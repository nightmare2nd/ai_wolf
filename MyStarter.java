package org.aiwolf.myAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.aiwolf.common.data.Player;
import org.aiwolf.common.data.Role;
import org.aiwolf.common.data.Team;
import org.aiwolf.common.net.GameSetting;
import org.aiwolf.kajiClient.player.KajiRoleAssignPlayer;
import org.aiwolf.server.AIWolfGame;
import org.aiwolf.server.net.DirectConnectServer;
import org.aiwolf.server.net.GameServer;

public class MyStarter {

  //参加エージェント数
  static protected int PLAYER_NUM = 15;

  //1回の実行で行うゲーム数
  static protected int GAME_NUM = 1;

  public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
   //村人側勝利数
   int villagerWinNum = 0;
   //人狼側勝利数
   int werewolfWinNum = 0;

   //指定したゲーム回数だけ以下を実行
   for(int i = 0;i<GAME_NUM;i++){
    List<Player> playerList = new ArrayList<Player>();

    //エージェント役職指定
    Map playerMap = new HashMap();
    //BasePlayerはVillagerに固定
    playerMap.put(new MyRoleAssignPlayer(), Role.SEER);
    ;
    for(int j=1;j<PLAYER_NUM;j++){				//以下サンプルプレイヤー
    	playerMap.put(new KajiRoleAssignPlayer(), null);
    	// playerMap.put(new SampleRoleAssignPlayer(), null);
      }


    //TCPを使わず直接対戦
    GameServer gameServer = new DirectConnectServer(playerMap);

    //ゲーム設定はデフォルト
    GameSetting gameSetting = GameSetting.getDefaultGame(PLAYER_NUM);

    //ゲーム開始
    AIWolfGame game = new AIWolfGame(gameSetting, gameServer);
    game.setRand(new Random(gameSetting.getRandomSeed()));
    game.start();

    //勝った方の勝利数を＋１
    if(game.getWinner() == Team.VILLAGER){
      villagerWinNum++;
    }else{
      werewolfWinNum++;
    }

    //最後
    System.out.println("村人側勝利:" + villagerWinNum + " 人狼側勝利" + werewolfWinNum);
    }
  }
}
