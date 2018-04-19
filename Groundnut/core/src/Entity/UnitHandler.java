package Entity;

public class UnitHandler {
    
    private Player[] players;
    private Enemy[] enemies;
    
    public UnitHandler(int numFriends, int numEnemies){
        players = new Player[numFriends];
        enemies = new Enemy[numEnemies];
        for(int i = 0; i < numFriends; i++){
            players[i] = new Player(150+i,150+i, 12f+i);
        }
        for(int i = 0; i < numEnemies; i++){
            enemies[i] = new Enemy(300+i,300+i, 6f+i);
        }
    }

    public void updateUnitPositions(Boolean[] commands){
//        if (commands[0]){
//            enemies[1].setMovingNorth(true);
//        }
//        if (commands[1]){
//            enemies[1].setMovingSouth(true);
//        }
//        if (commands[2]){
//            enemies[1].setMovingWest(true);
//        }
//        if (commands[3]){
//            enemies[1].setMovingEast(true);
//        }
//        if (!commands[0]){
//            enemies[1].setMovingNorth(false);
//        }
//        if (!commands[1]){
//            enemies[1].setMovingSouth(false);
//        }
//        if (!commands[2]){
//            enemies[1].setMovingWest(false);
//        }
//        if (!commands[3]){
//            enemies[1].setMovingEast(false);
//        }
    }

    public Player[] getPlayers() {
        return players;
    }

    public Enemy[] getEnemies() {
        return enemies;
    }
}
