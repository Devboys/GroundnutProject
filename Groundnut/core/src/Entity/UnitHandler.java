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

    public Player[] getPlayers() {
        return players;
    }

    public Enemy[] getEnemies() {
        return enemies;
    }
}
